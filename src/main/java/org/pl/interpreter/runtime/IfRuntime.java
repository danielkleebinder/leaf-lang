package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.IfCase;
import org.pl.parser.ast.IfNode;

public class IfRuntime implements IRuntime<IfNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, IfNode node) {
        for (IfCase ifCase : node.cases) {
            var conditionResult = interpreter.interpret(ifCase.condition);
            if (conditionResult instanceof Boolean && (boolean) conditionResult) {
                return interpreter.interpret(ifCase.caseBody);
            }
        }
        if (node.elseCase != null) {
            return interpreter.interpret(node.elseCase);
        }
        return null;
    }
}
