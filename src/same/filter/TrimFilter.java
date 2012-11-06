package same.filter;

import same.alg.ISourceFilter;

public class TrimFilter implements ISourceFilter
{
    public TrimFilter()
    {
        //do nothing
    }

    public String filter(String a_line)
    {
        return a_line.trim();
    }
    
    public ISourceFilter copy()
    {
        return this;
    }
}
