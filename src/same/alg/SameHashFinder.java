package same.alg;

import same.util.IStringHashComputer;
import same.util.ShiftingArray;

public class SameHashFinder
{
    private final int m_minimalFragmentSize;
    private final IFragmentHashRegistry m_registry;
    private final ISourceFilterFactory m_filterFactory;
    private final String m_filterType;
    private final IStringHashComputer m_hashComputer;

    private String m_currentFileName;
    private int m_currentLineNumber;
    private ShiftingArray /*of Integer*/ m_lineNumbers;

    private long m_currentFragmentHash;
    private ShiftingArray /*of Long*/ m_lineHashes;

    public SameHashFinder(int a_minimalFragmentSize, IFragmentHashRegistry a_registry, ISourceFilterFactory a_filterFactory, String a_filterType, IStringHashComputer a_hashComputer)
    {
        m_minimalFragmentSize = a_minimalFragmentSize;
        m_registry = a_registry;
        m_filterFactory = a_filterFactory;
        m_filterType = a_filterType;
        m_hashComputer = a_hashComputer;
    }

    public void startFile(String a_fileName)
    {
        m_currentFileName = a_fileName;
        m_currentLineNumber = 0;
        m_lineNumbers = new ShiftingArray(m_minimalFragmentSize);
        m_currentFragmentHash = 0;
        m_lineHashes = new ShiftingArray(m_minimalFragmentSize); //initially all nulls
    }

    public void addLine(String a_line)
    {
        m_currentLineNumber++;
        if (a_line.length() == 0)
        {
            return;
        }
        m_lineNumbers.append(new Integer(m_currentLineNumber));

        Long oldestLineHashAsLong = (Long)m_lineHashes.getOldest();
        long oldestLineHash = (oldestLineHashAsLong == null ? 0 : oldestLineHashAsLong.longValue());
        m_currentFragmentHash = m_currentFragmentHash ^ oldestLineHash;

        long newLineHash = m_hashComputer.computeStringHash(a_line);
        m_currentFragmentHash = m_currentFragmentHash ^ newLineHash;
        m_lineHashes.append(new Long(newLineHash));

        if (!m_lineNumbers.isFull())
        {
            //current fragment is not of maximum size; ignoring
            return;
        }

        int fragmentFirstLineNumber = ((Integer)m_lineNumbers.getOldest()).intValue();
        m_registry.registerFragment(m_currentFragmentHash, m_currentFileName, fragmentFirstLineNumber);
    }
}
