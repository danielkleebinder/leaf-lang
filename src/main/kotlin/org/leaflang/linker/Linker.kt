package org.leaflang.linker

import org.leaflang.RuntimeOptions
import org.leaflang.error.*
import org.leaflang.lexer.ILexer
import org.leaflang.lexer.source.FileSource
import org.leaflang.lexer.source.ISource
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.ProgramNode
import org.leaflang.parser.ast.StatementListNode
import org.leaflang.parser.ast.UseNode
import java.io.File

/**
 * Concrete linker implementation.
 */
class Linker(private val lexer: ILexer,
             private val parser: ILeafParser,
             override var errorHandler: IErrorHandler? = null) : ILinker {

    private val alreadyLoaded = hashSetOf<String>()

    override fun link(ast: INode): INode {
        if (ast is ProgramNode) {
            link(ast.statements)
        }
        if (ast is StatementListNode) {
            val newStatementList = arrayListOf<INode>()
            ast.statements
                    .forEach {
                        if (it !is UseNode) {
                            newStatementList.add(it)
                            return@forEach
                        }
                        it.loadFiles
                                .mapNotNull { file -> loadSourceFile(it, file) }
                                .map { source -> lexer.tokenize(source) }
                                .mapNotNull { tokens -> parser.parse(tokens) }
                                .forEach { subTree ->

                                    // Recursively link used files as well. Each file loaded might have more files
                                    // to load. Those are linked here.
                                    val linkedTree = link(subTree)

                                    // Unroll the program node and the statement list node to improve performance. This
                                    // is some sort of optimization mechanism that inlines code more directly and thus
                                    // saves interpretation steps.
                                    when (linkedTree) {
                                        is ProgramNode -> newStatementList.addAll(linkedTree.statements.statements)
                                        is StatementListNode -> newStatementList.addAll(linkedTree.statements)
                                        else -> newStatementList.add(linkedTree)
                                    }
                                }
                    }
            ast.statements = newStatementList
        }
        return ast
    }

    /**
     * Loads another source file.
     */
    private fun loadSourceFile(use: UseNode, fileName: String): ISource? {
        var file = File(fileName)

        // Check if the file was already loaded and linked
        if (alreadyLoaded.contains(file.absolutePath)) {
            if (RuntimeOptions.debug) println("File is already linked (skip): \"${file.canonicalPath}\"")
            return null
        }
        alreadyLoaded.add(file.absolutePath)

        // Resolve the file name by using a relative path
        if (!file.isAbsolute) {
            file = File(use.cwd).resolve(fileName)
        }

        // The file does not exist. The linking process will abort and the program never execute.
        if (!file.exists()) {
            errorHandler?.abort(AnalysisError(ErrorCode.SOURCE_FILE_NOT_FOUND, fromNode(use), ErrorType.LINKER, "Source file \"$fileName\" not found"))
            return null
        }

        // Read the entire file at once
        if (RuntimeOptions.debug) println("Linking file: \"${file.canonicalPath}\"")
        return FileSource(file)
    }
}