package test.same;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsSame
{
    public static Test suite()
    {
        TestSuite result = new TestSuite();
        result.addTest(new TestSuite(test.same.util.ShiftingArrayTest.class));
        result.addTest(new TestSuite(test.same.filter.JavaFilterTest.class));
        result.addTest(new TestSuite(test.same.alg.SameHashSummaryTest.class));
        result.addTest(new TestSuite(test.same.alg.SameHashFinderTest.class));
        return result;
    }
}
