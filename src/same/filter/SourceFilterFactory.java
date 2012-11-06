package same.filter;

import java.io.BufferedReader;
import java.io.Reader;

import same.alg.ISourceFilterFactory;
import same.alg.ISourceFilter;

public class SourceFilterFactory implements ISourceFilterFactory
{
    private static SourceFilterFactory s_instance = new SourceFilterFactory();

    public static SourceFilterFactory instance()
    {
        return s_instance;
    }

    private SourceFilterFactory()
    {
        //do nothing
    }

    public ISourceFilter createFilter(String a_fileType)
    {
        if (a_fileType == null || a_fileType.equalsIgnoreCase("none"))
        {
            return new NoFilter();
        }
        if (a_fileType.equalsIgnoreCase("trim"))
        {
            return new TrimFilter();
        }
        else if (a_fileType.equalsIgnoreCase("baan"))
        {
            return new BaanFilter();
        }
        else if (a_fileType.equalsIgnoreCase("java"))
        {
            return new JavaFilter();
        }
        return null;
    }
}
