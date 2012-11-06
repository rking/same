package same.alg;

import java.io.IOException;

public class MatchFinder
{
    private final FileLine m_fileLine1;
    private final FileLine m_fileLine2;
    private final ITextFileSystem m_fileSystem;

    private Match m_match;

    public MatchFinder(FileLine a_fileLine1, FileLine a_fileLine2, ITextFileSystem a_fileSystem)
    {
        m_fileLine1 = a_fileLine1;
        m_fileLine2 = a_fileLine2;
        m_fileSystem = a_fileSystem;
    }

    public void run()
        throws IOException
    {
        int length = 0;
        FileFragment fragment1 = new FileFragment(m_fileLine1);
        FileFragment fragment2 = new FileFragment(m_fileLine2);
        ITextReader reader1 = m_fileSystem.getReader(m_fileLine1, '1');
        ITextReader reader2 = m_fileSystem.getReader(m_fileLine2, '2');
        while (true)
        {
            String line1;
            String filteredLine1 = null;
            int newLines1 = 0;
            do
            {
                line1 = reader1.readLine();
                if (line1 == null)
                {
                    break;
                }
                fragment1.addLine(line1);
                newLines1++;

                filteredLine1 = reader1.getLastLineFiltered();
            } while (filteredLine1.length() == 0);

            String line2;
            String filteredLine2 = null;
            int newLines2 = 0;
            do
            {
                line2 = reader2.readLine();
                if (line2 == null)
                {
                    break;
                }
                fragment2.addLine(line2);
                newLines2++;

                filteredLine2 = reader2.getLastLineFiltered();
            } while (filteredLine2.length() == 0);

            if (line1 == null || line2 == null || !filteredLine1.equals(filteredLine2))
            {
                fragment1.removeLines(newLines1);
                fragment2.removeLines(newLines2);
                break;
            }
            length++;
        }
        m_fileSystem.returnReader(m_fileLine1.getFileName(), '1', reader1);
        m_fileSystem.returnReader(m_fileLine2.getFileName(), '2', reader2);
        m_match = new Match(fragment1, fragment2, length);
    }

    public Match getResult()
    {
        return m_match;
    }
}
