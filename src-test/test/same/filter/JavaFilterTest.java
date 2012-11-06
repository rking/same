package test.same.filter;

import junit.framework.TestCase;

import same.filter.*;

public class JavaFilterTest extends TestCase
{
    public JavaFilterTest(String a_name)
    {
        super(a_name);
    }

    public void test()
    {
        JavaFilter jf = new JavaFilter();
        assertEquals("", jf.filter("   \t   \t"));
        assertEquals("", jf.filter("/*/"));
        assertEquals("test", jf.filter("test*/t es t"));
        assertEquals("test", jf.filter("test//*blabla"));
        assertEquals("test", jf.filter("t/*of Vector*/e/**/s/**//**/t//123"));
    }
}
