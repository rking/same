package test.same.util;

import junit.framework.TestCase;

import same.util.*;

public class ShiftingArrayTest extends TestCase
{
    public ShiftingArrayTest(String a_name)
    {
        super(a_name);
    }

    public void test()
    {
        ShiftingArray sa = new ShiftingArray(2);
        sa.append("A");
        assertEquals(null, sa.getOldest());
        sa.append("B");
        assertEquals("A", sa.getOldest());
        sa.append("C");
        assertEquals("B", sa.getOldest());
    }
}
