package same.alg;

import java.io.IOException;

public interface ITextFileSystem
{
    public ITextReader getReader(FileLine a_fileLine, char a_series)
        throws IOException;
	
	//TODO: Remove the a_fileName argument?
    public void returnReader(String a_fileName, char a_series, ITextReader a_reader)
        throws IOException;
	
    public void disconnect()
        throws IOException;
}
