package same.filter;

import same.alg.ISourceFilter;

/** TODO: Make sure that this filter ignores comment delimiters within String literals.
 */
public class JavaFilter implements ISourceFilter
{
    private boolean m_inMultiLineComment;

    public JavaFilter()
    {
        m_inMultiLineComment = false;
    }

    public String filter(String a_line)
    {
        a_line = clean(a_line);

        if (a_line.startsWith("package")
            || a_line.startsWith("import"))
        {
            return ""; //ignore.
        }

        if (a_line.equals("{")
            || a_line.equals("}"))
        {
            return ""; //ignore.  TODO: Do we really want this?
        }

        //This loop removes all comments from the line.
        int mlcStart = 0;
        int pos = 0;
        while (true)
        {
            if (m_inMultiLineComment)
            {
                int multiLineCommentEnd = a_line.indexOf("*/", pos);
                if (multiLineCommentEnd >= 0)
                {
                    a_line = a_line.substring(0, mlcStart) + a_line.substring(multiLineCommentEnd + 2);
                    m_inMultiLineComment = false;
                    pos = mlcStart;
                }
                else
                {
                    a_line = a_line.substring(0, mlcStart);
                    break;
                }
            }
            else
            {
                int singleLineCommentStart = a_line.indexOf("//", pos);
                int multiLineCommentStart = a_line.indexOf("/*", pos);
                if (singleLineCommentStart >= 0 &&
                    (multiLineCommentStart > singleLineCommentStart
                     || multiLineCommentStart < 0))
                {
                    //single line comment
                    a_line = a_line.substring(0, singleLineCommentStart);
                    break;
                }
                if (multiLineCommentStart < 0)
                {
                    break;
                }
                //multiline comment start
                m_inMultiLineComment = true;
                mlcStart = multiLineCommentStart;
                pos = mlcStart + 2;
            }
        }

        return a_line;
    }

    //TODO: Factor out to SourceFilter class
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
        JavaFilter result = new JavaFilter();
        result.m_inMultiLineComment = m_inMultiLineComment;
        return result;
    }
}
