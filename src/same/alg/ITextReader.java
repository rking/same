package same.alg;

import java.io.IOException;

//TODO: Rename to ISourceReader, or to AbstractSourceReader.
public interface ITextReader
{
    public String readLine()
        throws IOException;
    public String readLineFiltered()
        throws IOException;
    public String getLastLine();
    public String getLastLineFiltered();
    public int getLineNumber();
}
