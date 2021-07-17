package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;
import org.nyxlang.interpreter.exception.InterpreterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpreterBoolTest extends TestSuit {

    @Test
    void shouldCompareTrueAndFalseKeywords() {
        var result = execute("true == false");
        assertEquals(getFalseList(), result);

        result = execute("true == true");
        assertEquals(getTrueList(), result);

        result = execute("true && true");
        assertEquals(getTrueList(), result);

        result = execute("false && true");
        assertEquals(getFalseList(), result);

        result = execute("false || true");
        assertEquals(getTrueList(), result);

        result = execute("false || (1 == 2)");
        assertEquals(getFalseList(), result);
    }


    @Test
    void shouldComparePositiveNumbers() {
        var result = execute("1<2");
        assertEquals(getTrueList(), result);

        result = execute("1>2");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCompareNumbers() {
        var result = execute("-1<2");
        assertEquals(getTrueList(), result);

        result = execute("1>-2");
        assertEquals(getTrueList(), result);
    }

    @Test
    void shouldNegate() {
        var result = execute("!(-1<2)");
        assertEquals(getFalseList(), result);

        result = execute("!(1>-2)");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCheckEquality() {
        var result = execute("3928==3928");
        assertEquals(getTrueList(), result);

        result = execute("-392==392");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCheckNonEquality() {
        var result = execute("3927!=3928");
        assertEquals(getTrueList(), result);

        result = execute("-392!=392");
        assertEquals(getTrueList(), result);

        result = execute("392!=392");
        assertEquals(getFalseList(), result);

        result = execute("true!=false");
        assertEquals(getTrueList(), result);
    }

    @Test
    void shouldCompareWithLogicalAnd() {
        var result = execute("(1<2)&&(1==1)");
        assertEquals(getTrueList(), result);

        result = execute("(1>2)&&(1==1)");
        assertEquals(getFalseList(), result);

        result = execute("(1>2)&&(1==2)");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCompareWithLogicalOr() {
        var result = execute("(1<2)||(1==1)");
        assertEquals(getTrueList(), result);

        result = execute("(1>2)||(1==1)");
        assertEquals(getTrueList(), result);

        result = execute("(1>2)||(1==2)");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCompareWithGreaterEquals() {
        var result = execute("2>=2");
        assertEquals(getTrueList(), result);

        result = execute("3>=2");
        assertEquals(getTrueList(), result);

        result = execute("1>=2");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldCompareWithLessEquals() {
        var result = execute("2<=2");
        assertEquals(getTrueList(), result);

        result = execute("3<=2");
        assertEquals(getFalseList(), result);

        result = execute("1<=2");
        assertEquals(getTrueList(), result);
    }

    @Test
    void shouldErrorForInvalidBoolLogic() {
        assertThrows(InterpreterException.class, () -> execute("true - false"));
        assertThrows(InterpreterException.class, () -> execute("-false"));
    }

    @Test
    void shouldHaveCorrectPrecedenceForEquals() {
        var result = execute("1 < 2 == true");
        assertEquals(getTrueList(), result);

        result = execute("true == 1 < 2");
        assertEquals(getTrueList(), result);
    }
}
