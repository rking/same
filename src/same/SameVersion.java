package same;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class SameVersion
{
    private static String getBuildProperty(String a_key, String a_default)
    {
        try
        {
            String result = null;
            InputStream is = SameVersion.class.getResourceAsStream("same-info.properties");
            if (is != null)
            {
                Properties p = new Properties();
                p.load(is);
                result = p.getProperty(a_key);
            }
            return (result == null ? a_default : result);
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public static String getBuildDate()
    {
        return getBuildProperty("same.build.date", "<no date found>");
    }

    public static String getBuildTime()
    {
        return getBuildProperty("same.build.time", "<no time found>");
    }

    public static String getBuildName()
    {
        return getBuildProperty("same.build.name", "<no version found>");
    }

    private SameVersion()
    {
        //not reached
    }
}
