package org.pl.analyzer;

import org.pl.parser.ast.INode;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer implements ISemanticAnalyzer {

    private List<SemanticError> errors;

    @Override
    public List<SemanticError> analyze(INode ast) {
        errors = new ArrayList<>(8);
        return errors;
    }
}
