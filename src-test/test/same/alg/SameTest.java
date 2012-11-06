package test.same.alg;

import junit.framework.TestCase;

import same.alg.*;

import java.io.StringReader;
import java.util.Vector;

public class SameTest
	extends TestCase
	implements ITextFileSystem, ITextReader, Same.IListener
{
	private Vector /*<String>*/ m_lines;
	private int m_lineNumber;
	
    public SameTest(String a_name)
    {
        super(a_name);
    }

	public void setUp()
	{
		m_lines = new Vector();
		addLine(2);
		addLine(3);
		addLine(10);
		addLine(1);
		addLine(2);
		addLine(3);
		addLine(11);
		addLine(1);
		addLine(2);
		addLine(3);
		m_lineNumber = 0;
	}
	
	private void addLine(int a_value)
	{
		m_lines.addElement(new String(new char[]{(char)(96 + a_value)}));
	}
	
	public void test()
	{
		Same s = new Same(this);
		s.setFileSystem(this);
		s.addFile("unused");
		s.setFragmentSize(2);
		s.run();
	}
	
	public void disconnect()
	{
		//do nothing
	}
	
	public void returnReader(String a_fileName, char a_series, ITextReader a_reader)
	{
		//do nothing
	}
	
	public ITextReader getReader(FileLine a_fileLine, char a_series)
	{
		m_lineNumber = a_fileLine.getLineNumber() - 1;
		return this;
	}

	public int getLineNumber()
	{
		return m_lineNumber;
	}

	public String getLastLine()
	{
		if (getLineNumber() <= 0 || getLineNumber() > m_lines.size())
		{
			return null;
		}
		return (String)m_lines.elementAt(getLineNumber() - 1);
	}

	public String readLineFiltered()
	{
		return readLine();
	}

	public String readLine()
	{
		if (getLineNumber() <= m_lines.size())
		{
			m_lineNumber++;
		}		return getLastLine();
	}

	public String getLastLineFiltered()
	{
		return getLastLine();
	}

	public void onNoMatch(FileLine p1, FileLine p2)
	{
		//do nothing
	}

	public void onErrorMatch(FileLine p1, FileLine p2, Throwable p3)
	{
		//do nothing
	}

	public void onPotentialMatchFound(FileLine p1, FileLine p2)
	{
		//do nothing
	}

	public void onEndPreprocess()
	{
		//do nothing
	}

	public void onEndPreprocessFile(String p1)
	{
		//do nothing
	}

	public void onFoundMatch(Match p1)
	{
		//do nothing
	}

	public void onEndMatches()
	{
		//do nothing
	}

	public void onStartPreprocessFile(String p1)
	{
		//do nothing
	}

	public void onErrorPreprocessFile(String p1, Throwable p2)
	{
		//do nothing
	}

	public void onStartMatches()
	{
		//do nothing
	}

	public void onTryMatch(FileLine p1, FileLine p2)
	{
		//do nothing
	}

	public void onEnd()
	{
		//do nothing
	}

	public void onErrorCleanUp(Throwable p1)
	{
		//do nothing
	}

	public void onStart()
	{
		//do nothing
	}
}
