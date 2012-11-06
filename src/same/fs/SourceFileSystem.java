package same.fs;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

import java.io.FileReader;
import java.io.IOException;

import same.alg.ITextFileSystem;
import same.alg.ITextReader;
import same.alg.ISourceFilterFactory;
import same.alg.ISourceFilter;
import same.alg.FileLine;

public class SourceFileSystem implements ITextFileSystem
{
    private static final int BUFFER_SIZE = 16384; //TODO: Experiment with this value.
    
    private final Hashtable /*String->SourceReader*/ m_secondHandReaders = new Hashtable();
    private final ISourceFilterFactory m_filterFactory;
    private final String m_filterType;

    public SourceFileSystem(ISourceFilterFactory a_filterFactory, String a_filterType)
    {
        m_filterFactory = a_filterFactory;
        m_filterType = a_filterType;
    }

    public ITextReader getReader(FileLine a_fileLine, char a_series)
        throws IOException
    {
        String key = a_fileLine.getFileName() + a_series;
        SourceReader result = (SourceReader)m_secondHandReaders.get(key);
        if (result == null)
        {
            System.err.print('>');
            result = new SourceReader(new FileReader(a_fileLine.getFileName()), createFilter(), BUFFER_SIZE);
        }
        else
        {
            m_secondHandReaders.remove(key);
            if (result.getLineNumber() + 1 > a_fileLine.getLineNumber())
            {
                boolean resetSucceeded = true;
                try
                {
                    result.reset(); //go back to the previous mark().
                    resetSucceeded = (result.getLineNumber() + 1 <= a_fileLine.getLineNumber());
                    if (!resetSucceeded)
                    {
                        System.err.print('<');
                    }
                }
                catch (IOException ex)
                {
                    System.err.print('#'); //Failed reset: remove from cache, and open new one.
                    resetSucceeded = false;
                }
                if (!resetSucceeded)
                {
                    m_secondHandReaders.remove(key);
                    result.close();
                    result = new SourceReader(new FileReader(a_fileLine.getFileName()), createFilter(), BUFFER_SIZE);
                }
            }
        }
        if (result.getLineNumber() + 1 > a_fileLine.getLineNumber())
        {
            throw new RuntimeException("trying to go to line number " + a_fileLine.getLineNumber() + " while we are at " + (result.getLineNumber() + 1));
        }
        while (result.getLineNumber() + 1 < a_fileLine.getLineNumber())
        {
            result.readLine();
        }
        result.mark(BUFFER_SIZE);
        return result;
    }

    //TODO: Remove the first two arguments, by putting them in SourceReader as attributes.
    public void returnReader(String a_fileName, char a_series, ITextReader a_reader)
        throws IOException
    {
        SourceReader reader = (SourceReader)a_reader;
        
        SourceReader lnr = (SourceReader)m_secondHandReaders.get(a_fileName);
        if (lnr != null)
        {
            //already a second hand reader for this file.
            reader.close();
            return;
        }
        m_secondHandReaders.put(a_fileName + a_series, reader);
    }

    public void disconnect()
        throws IOException
    {
        closeAllSecondHandReaders();
        m_secondHandReaders.clear();
    }
    
    private void closeAllSecondHandReaders()
        throws IOException
    {
        for (Enumeration e = m_secondHandReaders.elements(); e.hasMoreElements(); )
        {
            SourceReader lnr = (SourceReader)e.nextElement();

            lnr.close();
        }
    }
    
    private ISourceFilter createFilter()
    {
        return m_filterFactory.createFilter(m_filterType);
    }
}
