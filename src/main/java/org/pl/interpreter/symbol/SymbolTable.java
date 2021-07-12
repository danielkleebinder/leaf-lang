package org.pl.interpreter.symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private SymbolTable parent;
    private Map<String, Object> symbols = new HashMap<>();

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public Object get(String identifier) {
        var result = symbols.get(identifier);
        if (parent != null && result == null) {
            return parent.get(identifier);
        }
        return result;
    }

    public void set(String identifier, Object value) {
        symbols.put(identifier, value);
    }

    public void remove(String identifier) {
        symbols.remove(identifier);
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "parent=" + parent +
                ", symbols=" + symbols +
                '}';
    }
}
