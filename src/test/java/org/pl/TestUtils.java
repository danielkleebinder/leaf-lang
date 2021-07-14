package org.pl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Contains utils for unit testing.
 */
public class TestUtils {

    /**
     * A list with one entry which is null.
     */
    public static final List<Object> nullList = new ArrayList<>(2) {{
        add(null);
    }};

    /**
     * A list with one entry which is true.
     */
    public static final List<Object> trueList = new ArrayList<>(2) {{
        add(true);
    }};

    /**
     * A list with one entry which is false.
     */
    public static final List<Object> falseList = new ArrayList<>(2) {{
        add(false);
    }};

    /**
     * A list with zero entry.
     */
    public static final List<Object> emptyList = Collections.emptyList();
}
