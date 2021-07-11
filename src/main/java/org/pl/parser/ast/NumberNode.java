package org.pl.parser.ast;


import java.math.BigDecimal;

/**
 * Single float value.
 */
public class NumberNode implements INode {
    public final BigDecimal value;

    public NumberNode(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NumberNode{val=" + value + '}';
    }
}
