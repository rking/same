package same.alg;

import java.util.Vector;
import java.util.Enumeration;

import java.io.PrintStream;

public class FileFragment
{
    private final FileLine m_startingPoint;
    private Vector /*<String>*/ m_lines;
    private int m_size;

    public FileFragment(FileLine a_fileLine)
    {
        m_startingPoint = a_fileLine;
        m_lines = new Vector();
        m_size = 0;
    }

    public FileLine getStartingPoint()
    {
        return m_startingPoint;
    }

    public String getFileName()
    {
        return m_startingPoint.getFileName();
    }

    public int getLineNumber()
    {
        return m_startingPoint.getLineNumber();
    }

    public boolean contains(FileLine a_fileLine)
    {
        return
            a_fileLine.getFileName().equals(getFileName())
            && a_fileLine.getLineNumber() >= getLineNumber()
            && a_fileLine.getLineNumber() < getLineNumber() + m_size;
    }

    public void addLine(String a_line)
    {
        m_lines.addElement(a_line);
        m_size++;
    }

    public void removeLines(int a_number)
    {
        m_size -= a_number;
        m_lines.setSize(m_size);
    }

    public void printOn(PrintStream a_stream)
    {
        if (m_lines == null)
        {
            a_stream.println("[cleared to reduce memory consumption]");
            return;
        }
        for (Enumeration e = m_lines.elements(); e.hasMoreElements(); )
        {
            String line = (String)e.nextElement();
            a_stream.println(line);
        }
    }

    public void clear()
    {
        m_lines = null;
    }

    public int getSize()
    {
        return m_size;
    }
}
