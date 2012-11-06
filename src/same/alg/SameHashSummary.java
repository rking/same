package same.alg;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

public class SameHashSummary implements IFragmentHashRegistry
{
    public interface IListener
    {
        void onPotentialMatchFound(FileLine a_fileLine1, FileLine a_fileLine2);
    }
    
    private /*final*/ IListener m_listener;
    
    private Hashtable /*<Integer, Vector<FileLine>>*/ m_linesByHash;
    private Vector /*<Vector<FileLine>>*/ m_potentialMatches;

    public SameHashSummary(IListener a_listener)
    {
        m_listener = a_listener;
        reset();
    }

    public void registerFragment(long a_hash, String a_fileName, int a_lineNumber)
    {
        Long key = new Long(a_hash);
        FileLine fileLine = new FileLine(a_fileName, a_lineNumber);
        Vector /*<FileLine>*/ lines = (Vector)m_linesByHash.get(key);
        if (lines == null)
        {
            lines = new Vector(2);
            m_linesByHash.put(key, lines);
        }
        else
        {
            for (Enumeration e = lines.elements(); e.hasMoreElements(); )
            {
                FileLine previousFileLine = (FileLine)e.nextElement();
                m_listener.onPotentialMatchFound(previousFileLine, fileLine);
            }
            if (lines.size() == 1)
            {
                m_potentialMatches.addElement(lines);
            }
        }
        lines.addElement(fileLine);
    }

    public Vector /*<Vector<FileLine>>*/ getPotentialMatches()
    {
        return m_potentialMatches;
    }

    public void reset()
    {
        m_linesByHash = new Hashtable();
        m_potentialMatches = new Vector();
    }
}
