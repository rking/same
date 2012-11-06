package same.fs;

import java.io.Reader;
import java.io.LineNumberReader;
import java.io.IOException;

import same.alg.ITextReader;
import same.alg.ISourceFilter;

public class SourceReader extends LineNumberReader implements ITextReader
{
    private ISourceFilter m_filter;
    
    private ISourceFilter m_savedFilter = null;
    private String m_lastLine = null;
    private String m_lastLineFiltered = null;
    
    public SourceReader(Reader a_reader, ISourceFilter a_filter)
    {
        super(a_reader);
        m_filter = a_filter;
    }

    //TODO: Try to get rid of this constructor
    public SourceReader(Reader a_reader, ISourceFilter a_filter, int a_bufferSize)
    {
        super(a_reader, a_bufferSize);
        m_filter = a_filter;
    }
    
    private void doReadLine()
        throws IOException
    {
        m_lastLine = super.readLine();
        m_lastLineFiltered = (m_lastLine == null ? null : m_filter.filter(m_lastLine));
    }
    
    public String readLine()
        throws IOException
    {
        doReadLine();
        return m_lastLine;
    }
    
    public String readLineFiltered()
        throws IOException
    {
        doReadLine();
        return m_lastLineFiltered;
    }

    public String getLastLine()
    {
        return m_lastLine;
    }

    public String getLastLineFiltered()
    {
        return m_lastLineFiltered;
    }
    
    public void mark(int a_lookAhead)
        throws IOException
    {
        super.mark(a_lookAhead);
        m_savedFilter = m_filter.copy();
    }
    
    public void reset()
        throws IOException
    {
        super.reset();
        m_filter = m_savedFilter;
    }
}
