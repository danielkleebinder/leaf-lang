package org.leaflang.analyzer.symbol

/**
 * Type symbols represent user defined custom types.
 */
class TypeSymbol(name: String,
                 val traits: List<TraitSymbol> = listOf(),
                 var fields: MutableList<VarSymbol> = arrayListOf(),
                 var functions: MutableList<ClosureSymbol> = arrayListOf(),
                 nestingLevel: Int = 0) : Symbol(name, nestingLevel) {

    /**
     * Checks if a field OR function with the given name already exists.
     */
    fun hasField(fieldName: String?): Boolean {
        return fields.any { it.name == fieldName } ||
                functions.any { it.name == fieldName }
    }

    override fun toString() = "TypeSymbol(name=$name, fields=$fields, functions=$functions)"
}

/**
 * Composes a new union type of the given types.
 */
fun composeType(vararg types: TypeSymbol): TypeSymbol {
    if (types.size == 1) return types[0]
    return TypeSymbol(
            name = "comp::" + types.joinToString(" | ") { it.name },
            traits = types.flatMap { it.traits },
            fields = types.flatMap { it.fields }.toMutableList(),
            functions = types.flatMap { it.functions }.toMutableList(),
            nestingLevel = types.map { it.nestingLevel }.maxOrNull() ?: 0)
}
