package test.same.alg;

import junit.framework.TestCase;

import same.alg.*;
import same.util.IStringHashComputer;
import same.filter.NoFilter;

public class SameHashFinderTest
    extends TestCase
    implements IFragmentHashRegistry, ISourceFilterFactory, IStringHashComputer
{
    private SameHashFinder m_dhlf;
    private String m_currentFileName;

    private boolean m_isFragmentexpected = false;
    private long m_expectedHash;
    private int m_expectedLineNumber;

    public SameHashFinderTest(String a_name)
    {
        super(a_name);
    }

    public void registerFragment(long a_hash, String a_fileName, int a_lineNumber)
    {
        assert(m_isFragmentexpected);
        assertEquals(m_expectedHash, a_hash);
        assertEquals(m_currentFileName, a_fileName);
        assertEquals(m_expectedLineNumber, a_lineNumber);

        m_isFragmentexpected = false;
    }

    public void setUp()
    {
        m_dhlf = new SameHashFinder(2, this, this, "testFilter", this);
    }

    public void test()
    {
        m_dhlf.startFile("testFile");
        m_currentFileName = "testFile";
        m_dhlf.addLine("1-One.");
        expectFragment(1 ^ 2, 1);
        m_dhlf.addLine("2-Two.");
        expectFragment(2 ^ 3, 2);
        m_dhlf.addLine("3-Three.");
        expectFragment(3 ^ 1, 3);
        m_dhlf.addLine("1-One.");
        m_dhlf.addLine("");
        expectFragment(1 ^ 2, 4);
        m_dhlf.addLine("2-Two.");
        expectFragment(2 ^ 3, 6);
        m_dhlf.addLine("3-Three.");
    }

    private void expectFragment(int a_expectedHash, int a_expectedLineNumber)
    {
        m_isFragmentexpected = true;
        m_expectedHash = a_expectedHash;
        m_expectedLineNumber = a_expectedLineNumber;
    }

    /** For these tests, we ignore the file type, and always create a nop filter.
     */
    public ISourceFilter createFilter(String a_fileType)
    {
        return new NoFilter();
    }

    public long computeStringHash(String a_line)
    {
        int p = a_line.indexOf('-');
        long result = Long.valueOf(a_line.substring(0, p)).intValue();
        return result;
    }
}
