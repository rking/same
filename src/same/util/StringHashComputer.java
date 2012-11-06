package same.util;

public class StringHashComputer implements IStringHashComputer
{
    private static final StringHashComputer s_instance = new StringHashComputer();

    public static StringHashComputer instance()
    {
        return s_instance;
    }

    private StringHashComputer()
    {
        //do nothing
    }

    public long computeStringHash(String a_line)
    {
        long result = 0;
        //TODO: What is the quickest way to go through a string?
        int s = a_line.length();
        for (int i = 0; i < s; i++)
        {
            int c = a_line.charAt(i);
            result = ((result<<23) | (result>>>41));
            result = result ^ c;
        }
        return result;
    }
}
