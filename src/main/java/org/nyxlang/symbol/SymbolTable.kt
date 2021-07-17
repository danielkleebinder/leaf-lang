package org.nyxlang.symbol

/**
 * Implementation of the symbol table specification. The [parent] symbol table
 * is used to allow scoping with the symbol table.
 */
class SymbolTable(override val name: String? = null,
                  override val parent: ISymbolTable? = null,
                  val withBuiltIns: Boolean = false) : ISymbolTable {

    private val symbols = hashMapOf<String, Symbol>()

    init {
        if (withBuiltIns) {
            define(BuiltInSymbol("number"))
            define(BuiltInSymbol("bool"))
            define(BuiltInSymbol("string"))
        }
    }

    override fun get(name: String): Symbol? {
        val symbol = getLocal(name)
        if (parent != null && symbol == null) {
            return parent.get(name)
        }
        return symbol
    }

    override fun getLocal(name: String) = symbols[name]
    override fun define(symbol: Symbol) = symbols.put(symbol.name, symbol)
    override fun remove(name: String) = symbols.remove(name)
    override fun toString() = """
            |SCOPE (SYMBOL TABLE)
            |===============================
            |Name  : $name
            |Parent: ${parent?.name}
            |-------------------------------
            |${
        symbols
                .map { "${"%8s".format(it.key)} -> ${it.value}\n" }
                .fold("") { acc, s -> acc + s }
    }
        """.trimMargin()
}