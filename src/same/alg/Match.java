package same.alg;

public class Match
{
    private final FileFragment m_fragment1;
    private final FileFragment m_fragment2;
    private final int m_length;

    public Match(FileFragment a_fragment1, FileFragment a_fragment2, int a_length)
    {
        m_fragment1 = a_fragment1;
        m_fragment2 = a_fragment2;
        m_length = a_length;
    }

    public FileFragment getFragment1()
    {
        return m_fragment1;
    }

    public FileFragment getFragment2()
    {
        return m_fragment2;
    }

    public int getLength()
    {
        return m_length;
    }

    public boolean contains(FileLine a_fileLine1, FileLine a_fileLine2)
    {
        return
            (m_fragment1.contains(a_fileLine1) && m_fragment2.contains(a_fileLine2))
            || (m_fragment1.contains(a_fileLine2) && m_fragment2.contains(a_fileLine1));
    }

    public void clearFragments()
    {
        m_fragment1.clear();
        m_fragment2.clear();
    }
}
