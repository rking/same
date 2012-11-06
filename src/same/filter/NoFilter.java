package same.filter;

import same.alg.ISourceFilter;

public class NoFilter implements ISourceFilter
{
    public String filter(String a_line)
    {
        return a_line;
    }
    
    public ISourceFilter copy()
    {
        return this;
    }
}
