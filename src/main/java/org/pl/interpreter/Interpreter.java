package org.pl.interpreter;

import org.pl.interpreter.exception.InterpreterException;
import org.pl.interpreter.exception.VisitorException;
import org.pl.interpreter.symbol.SymbolTable;
import org.pl.interpreter.visitor.*;
import org.pl.parser.ast.INode;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements IInterpreter {

    private final SymbolTable globalSymbolTable = new SymbolTable(null);
    private final List<IVisitor> visitorsList = new ArrayList<>(32) {
        {
            add(new UnaryOperationVisitor());
            add(new BinaryOperationVisitor());
            add(new BoolVisitor());
            add(new NumberVisitor());
            add(new IfVisitor());
            add(new LoopVisitor());
            add(new StatementListVisitor());
            add(new VarAccessVisitor());
            add(new VarAssignVisitor());
            add(new VarDeclarationVisitor());
            add(new TypeVisitor());
        }
    };

    @Override
    public Object interpret(INode ast) {
        return evalNode(ast);
    }

    @Override
    public Object evalNode(INode node) {
        if (node == null) {
            return null;
        }

        List<InterpreterError> errors = new ArrayList<>(32);

        for (IVisitor visitor : visitorsList) {
            if (!visitor.matches(node)) {
                continue;
            }
            try {
                return visitor.visit(this, node);
            } catch (VisitorException e) {
                errors.add(new InterpreterError(e.getMessage()));
            }
        }

        if (errors.size() > 0) {
            throw new InterpreterException("The interpreter detected an error during runtime", errors);
        }

        return null;
    }

    @Override
    public SymbolTable getGlobalSymbolTable() {
        return globalSymbolTable;
    }
}
