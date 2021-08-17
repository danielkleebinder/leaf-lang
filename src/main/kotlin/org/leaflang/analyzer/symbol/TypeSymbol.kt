package org.leaflang.analyzer.symbol

/**
 * Type symbols represent user defined custom types.
 */
class TypeSymbol(name: String,
                 val traits: List<TraitSymbol> = listOf(),
                 var fields: MutableList<VarSymbol> = arrayListOf(),
                 nestingLevel: Int = 0) : Symbol(name, nestingLevel) {
    override fun toString() = "TypeSymbol(name=$name, fields=$fields)"
}

/**
 * Composes a new union type of the given types.
 */
fun composeType(vararg types: TypeSymbol) = TypeSymbol(
        name = types.joinToString(" | ") { it.name },
        traits = types.flatMap { it.traits },
        fields = types.flatMap { it.fields }.toMutableList(),
        nestingLevel = types.map { it.nestingLevel }.maxOrNull() ?: 0)
