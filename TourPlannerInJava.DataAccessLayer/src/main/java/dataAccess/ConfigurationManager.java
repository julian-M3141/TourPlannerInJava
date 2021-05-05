package dataAccess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static Logger logger;
    public static String GetConfigPropertyValue(String propertyName) throws IOException {
        Properties properties = new Properties();
        String fileName = "config/config.txt";

        InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(fileName);

        if(inputStream != null){
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        }
        logger = LogManager.getLogger(ConfigurationManager.class);
        logger.error("Config file '"+fileName+"' not found");
        throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
    }
}
