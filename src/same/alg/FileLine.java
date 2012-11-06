package same.alg;

public class FileLine
{
    private final String m_fileName;
    private final int m_lineNumber;

    public FileLine(String a_fileName, int a_lineNumber)
    {
        m_fileName = a_fileName;
        m_lineNumber = a_lineNumber;
    }

    public String getFileName()
    {
        return m_fileName;
    }

    public int getLineNumber()
    {
        return m_lineNumber;
    }

    public boolean equals(Object a_object)
    {
        if (a_object == null || !(a_object instanceof FileLine))
        {
            return false;
        }
        FileLine that = (FileLine)a_object;
        return this.m_lineNumber == that.m_lineNumber && this.m_fileName.equals(that.m_fileName);
    }

    //NOTE: This is not a very good hash code probably.  But it is not used currently.
    public int hashCode()
    {
        return m_lineNumber ^ m_fileName.hashCode();
    }

    public String toString()
    {
        return "FileLine[" + m_fileName + "," + m_lineNumber + "]";
    }
}
