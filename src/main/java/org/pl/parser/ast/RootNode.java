package org.pl.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * The root node contains a bunch of statements at the programs root level.
 */
public class RootNode implements INode {
    public final List<INode> statements = new ArrayList<>(8);

    @Override
    public String toString() {
        return "RootNode{" +
                "statements=" + statements +
                '}';
    }
}
