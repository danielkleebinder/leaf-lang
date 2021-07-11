package org.pl.analyzer;

import org.pl.parser.ast.INode;

import java.util.List;

/**
 * The semantic analyzer is used to traverse a given abstract syntax tree
 * and look for semantic program errors that will inevitably lead to failure
 * if fed to an interpreter.
 */
public interface ISemanticAnalyzer {

    /**
     * Analyses a given abstract syntax tree (AST) and returns a list of errors. The
     * list is empty if no errors are found.
     *
     * @param ast Abstract syntax tree.
     * @return List of semantic errors.
     */
    List<SemanticError> analyze(INode ast);
}
