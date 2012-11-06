package same.textui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Vector;

import same.SameVersion;
import same.alg.Same;
import same.alg.FileLine;
import same.alg.Match;
import same.alg.FileFragment;
import same.fs.SourceFileSystem;
import same.filter.SourceFilterFactory;

public class SameCLI implements Same.IListener
{
    private final Same m_dupLoc;
    private boolean m_noFilesSpecified;
    
    public static void main(String[] a_args)
    {
        new SameCLI().run(a_args);
    }
    
    public SameCLI()
    {
        m_dupLoc = new Same(this);
    }
    
    public void onStart()
    {
        System.out.println("Same starts.");
        System.out.println();
    }

    public void onStartPreprocessFile(String a_fileName)
    {
        System.out.print("Processing file " + a_fileName);
    }
    
    public void onPotentialMatchFound(FileLine a_fileLine1, FileLine a_fileLine2)
    {
        System.err.print("-");
//        System.err.println();
//        System.err.print("Potential match: " + a_fileLine1 + " and " + a_fileLine2);
    }

    public void onErrorPreprocessFile(String a_fileName, Throwable a_exception)
    {
        System.out.println(); //go to beginning of line
        System.out.println("Error while processing file " + a_fileName + ": " + a_exception);
        System.out.println("...skipping file and continuing");
    }
    
    public void onEndPreprocessFile(String a_fileName)
    {
        System.out.println(); //go to beginning of line
    }

    public void onEndPreprocess()
    {
        System.out.println();
    }
    
    public void onStartMatches()
    {
        System.out.println("Number of potential matches: " + m_dupLoc.getNumberOfPotentialMatches());
        System.out.println();
    }
    
    public void onTryMatch(FileLine a_fileLine1, FileLine a_fileLine2)
    {
        System.err.print("-");
//        System.err.println();
//        System.err.print("Comparison: " + a_fileLine1 + " and " + a_fileLine2);
    }
    
    public void onFoundMatch(Match a_match)
    {
        System.out.println();
        System.out.println("<<<<<<<<");
        printFragment(a_match.getFragment1());
        System.out.println("========");
        printFragment(a_match.getFragment2());
        System.out.println(">>>>>>>>");
    }
    
    public void onNoMatch(FileLine a_fileLine1, FileLine a_fileLine2)
    {
        //do nothing
    }

    public void onErrorMatch(FileLine a_fileLine1, FileLine a_fileLine2, Throwable a_exception)
    {
        //TODO: Print the file names & line numbers.
        System.out.println("Error while trying to find some match: " + a_exception);
        System.out.println("...skipping match and continuing");
    }
    
    public void onEndMatches()
    {
        System.out.println();
    }
    
    public void onErrorCleanUp(Throwable a_exception)
    {
        System.out.println("Error while closing all files: " + a_exception);
        System.out.println("...continuing");
    }

    public void onEnd()
    {
        printMatchSummary();
        printStatistics();
        System.out.println("Same ends.");
    }
    
    private void run(String[] a_args)
    {
        parseCommandLine(a_args);
        m_dupLoc.run();
    }
    
    private void parseCommandLine(String[] a_args)
    {
        m_noFilesSpecified = true;
        for (int i = 0; i < a_args.length; i++)
        {
            String arg = a_args[i];
            if (arg.equals("-m"))
            {
                m_dupLoc.setFragmentSize(Integer.valueOf(a_args[++i]).intValue());
                continue;
            }
            else if (arg.equals("-f"))
            {
                m_dupLoc.setFilterType(a_args[++i]);
                continue;
            }
            else if (arg.startsWith("@"))
            {
                readFileNamesFrom(arg.substring(1));
                continue;
            }
            addFile(arg);
        }
        if (m_noFilesSpecified)
        {
            usage();
        }
        m_dupLoc.setFileSystem(new SourceFileSystem(SourceFilterFactory.instance(), m_dupLoc.getFilterType()));
    }
    
    private void addFile(String a_fileName)
    {
        m_dupLoc.addFile(a_fileName);
        m_noFilesSpecified = false;
    }

    private void readFileNamesFrom(String a_fileName)
    {
        try
        {
            BufferedReader r = new BufferedReader(new FileReader(a_fileName));
            while (true)
            {
                String line = r.readLine();
                if (line == null)
                {
                    break;
                }

                line = line.trim();
                if (line.equals(""))
                {
                    continue; //skip empty lines
                }
                addFile(line.trim());
            }
        }
        catch (IOException ex)
        {
            System.err.println("Error during processing of parameter file " + a_fileName + ": " + ex);
            System.err.println("...skipping and continuing");
        }
    }

    private void usage()
    {
        //TODO: Get the defaults from m_dupLoc.
        System.err.println("Finds duplicate lines of source code across files.");
        System.err.println();
        System.err.println("Usage: same [-f filter] [-m minimalSize] filenames...");
        System.err.println("  -f filter       filter all files with this filter (none, trim, baan, java)");
        System.err.println("                    [default: none]");
        System.err.println("  -m minimalSize  minimal fragment length [default: 10]");
        System.err.println("  filenames...    the files to be checked for duplicates");
        System.err.println("                    @filename indicates a file that contains filenames,");
        System.err.println("                    one filename per line");
        System.err.println();
        System.err.println("Version " + SameVersion.getBuildName() + ", built on " + SameVersion.getBuildDate() + " at " + SameVersion.getBuildTime() + ".");
        System.err.println("Please send comments and bug reports to marnix@users.sourceforge.net.");
        System.exit(1);
    }

    private void printFragment(FileFragment a_fragment)
    {
        System.out.println("File: " + a_fragment.getFileName() + ", from line: " + a_fragment.getLineNumber());
        a_fragment.printOn(System.out);
    }

    private void printMatchSummary()
    {
        System.out.println("Match summary:");
        System.out.println();
        //TODO: Sort by frequency and/or length.
        Enumeration ee = m_dupLoc.getEquivalentMatches();
        for (Enumeration e = m_dupLoc.getEquivalentFileLines(); e.hasMoreElements(); )
        {
            Vector /*<FileLine>*/ fileLines = (Vector)e.nextElement();
            Match match = (Match)ee.nextElement();

            System.out.print("Frequency: " + fileLines.size() + ", length: " + match.getLength() + " (");
            String separator = "";
            for (Enumeration f = fileLines.elements(); f.hasMoreElements(); )
            {
                FileLine fileLine = (FileLine)f.nextElement();
                System.out.print(separator + fileLine.getFileName() + ":" + fileLine.getLineNumber());
                separator = ", ";
            }
            System.out.println(")");
        }
        System.out.println();
    }
    
    private void printStatistics()
    {
        System.out.println("Statistics:");
        System.out.println();
        System.out.println("Number of files: " + m_dupLoc.getNumberOfFiles());
        System.out.println("Total number of lines: " + m_dupLoc.getTotalNumberOfLines());
        System.out.println("Number of filtered lines: " + m_dupLoc.getRelevantNumberOfLines()
                           + " (" + percent(m_dupLoc.getRelevantNumberOfLines(), m_dupLoc.getTotalNumberOfLines())
                           + " of total)");
        System.out.println("Number of potential pairwise matches: " + m_dupLoc.getNumberOfPotentialMatches());
        System.out.println("Number of pairwise comparisons made: " + m_dupLoc.getNumberOfComparisons());
        int nMatches = m_dupLoc.getNumberOfEquivalentMatches();
        System.out.print("Number of matches found: " + nMatches);
        if (nMatches > 0)
        {
            System.out.print(" (1 per " + m_dupLoc.getTotalNumberOfLines() / nMatches + " lines, "
                               + "1 per " + m_dupLoc.getRelevantNumberOfLines() / nMatches + " filtered lines)");
        }
        System.out.println();
        int superfluous = m_dupLoc.getNumberOfSuperfluousLines();
        System.out.println("Number of superfluous lines (at most): " + superfluous
                           + " (" + percent(superfluous, m_dupLoc.getTotalNumberOfLines()) + " of lines, "
                           + percent(superfluous, m_dupLoc.getRelevantNumberOfLines()) + " of filtered lines)");
        System.out.println();
    }

    //TODO: Move this to a utility class.
    private static String percent(double a_num, double a_den)
    {
        //TODO: Improve this...
        if (a_den < 0.01)
        {
            return "<meaningless>%";
        }
        //TODO: Improve this...
        return String.valueOf(Math.round(a_num * 1000 / a_den) / (double)10) + "%";
    }
}
