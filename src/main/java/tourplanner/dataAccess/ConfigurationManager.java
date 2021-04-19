package tourplanner.dataAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    public static String GetConfigPropertyValue(String propertyName) throws IOException {
        Properties properties = new Properties();
        String fileName = "config/config.txt";

        InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(fileName);

        if(inputStream != null){
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        }

        throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
    }
}
