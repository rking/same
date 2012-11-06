package test.same.alg;

import junit.framework.TestCase;

import same.alg.*;

import java.util.Vector;

public class SameHashSummaryTest extends TestCase
    implements SameHashSummary.IListener
{
    public SameHashSummaryTest(String a_name)
    {
        super(a_name);
    }

    public void test()
    {
        SameHashSummary dhls = new SameHashSummary(this);
        dhls.registerFragment(627, "file1", 33);
        dhls.registerFragment(442, "file1", 45);
        dhls.registerFragment(627, "file2", 21);

        Vector potentialMatches = new Vector();
            Vector potentialMatch = new Vector();
            potentialMatch.addElement(new FileLine("file1", 33));
            potentialMatch.addElement(new FileLine("file2", 21));
        potentialMatches.addElement(potentialMatch);

        assertEqualMatches(potentialMatches, dhls.getPotentialMatches());
    }

    private void assertEqualMatches(Vector a_expected, Vector a_actual)
    {
        assertEquals("expected equal Vector sizes", a_expected.size(), a_actual.size());
        for (int i = 0; i < a_expected.size(); i++)
        {
            Vector expected = (Vector)a_expected.elementAt(i);
            Vector actual = (Vector)a_actual.elementAt(i);

            assertEquals("expected equal nested Vector sizes at position " + i, expected.size(), actual.size());
            for (int j = 0; j < expected.size(); j++)
            {
                assertEquals("expected equal nested Vector element at position " + i + "->" + j, expected.elementAt(j), actual.elementAt(j));
            }
        }
    }
    
    public void onPotentialMatchFound(FileLine a_fileLine1, FileLine a_fileLine2)
    {
        //TODO: test this.
    }
}
