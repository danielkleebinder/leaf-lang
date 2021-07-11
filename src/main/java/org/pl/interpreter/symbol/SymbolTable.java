package org.pl.interpreter.symbol;

public class SymbolTable {

    private SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }
}
