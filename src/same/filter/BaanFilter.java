package same.filter;

import same.alg.ISourceFilter;

public class BaanFilter implements ISourceFilter
{
    private boolean m_inDllUsage;

    public BaanFilter()
    {
        m_inDllUsage = false;
    }

    public String filter(String a_line)
    {
        if (a_line.startsWith("^"))
        {
            a_line = a_line.substring(1);
        }
        int commentStart = a_line.indexOf('|');
        if (commentStart >= 0)
        {
            a_line = a_line.substring(0, commentStart);
        }
        a_line = clean(a_line).toLowerCase();
        if (m_inDllUsage)
        {
            if (a_line.equals("enddllusage"))
            {
                m_inDllUsage = false;
            }
            return "";
        }
        if (a_line.equals("dllusage"))
        {
            m_inDllUsage = true;
            return "";
        }
        return a_line;
    }

    private String clean(String a_string)
    {
        StringBuffer result = new StringBuffer(120);
        int s = a_string.length();
        for (int i = 0; i < s; i++)
        {
            char c = a_string.charAt(i);
            if (!Character.isWhitespace(c))
            {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    public ISourceFilter copy()
    {
        BaanFilter result = new BaanFilter();
        result.m_inDllUsage = m_inDllUsage;
        return result;
    }
}
