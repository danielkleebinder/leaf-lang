package org.pl.interpreter;

import org.jetbrains.annotations.NotNull;
import org.pl.interpreter.exception.InterpreterException;
import org.pl.interpreter.exception.VisitorException;
import org.pl.interpreter.memory.ActivationRecord;
import org.pl.interpreter.memory.IActivationRecord;
import org.pl.interpreter.visitor.*;
import org.pl.parser.ast.INode;
import org.pl.symbol.ISymbolTable;
import org.pl.symbol.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements IInterpreter {

    private IActivationRecord globalMemory = new ActivationRecord(null);
    private ISymbolTable globalSymbolTable = new SymbolTable(null);
    private List<IVisitor> visitorsList = new ArrayList<>(32) {
        {
            add(new ProgramVisitor());
            add(new UnaryOperationVisitor());
            add(new BinaryOperationVisitor());
            add(new BoolVisitor());
            add(new NumberVisitor());
            add(new IfVisitor());
            add(new LoopVisitor());
            add(new NativeVisitor());
            add(new StatementListVisitor());
            add(new VarAccessVisitor());
            add(new VarAssignVisitor());
            add(new VarDeclareVisitor());
            add(new TypeVisitor());
        }
    };

    @NotNull
    @Override
    public Object interpret(@NotNull INode ast) {
        return interpret(ast, null);
    }

    @NotNull
    @Override
    public Object interpret(@NotNull INode ast, ISymbolTable symbolTable) {
        if (symbolTable != null) {
            globalSymbolTable = symbolTable;
        }
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

    @NotNull
    @Override
    public IActivationRecord getGlobalMemory() {
        return globalMemory;
    }

    @NotNull
    @Override
    public ISymbolTable getSymbolTable() {
        return globalSymbolTable;
    }
}
