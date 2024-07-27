package apiAutomation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {

    private final Properties prop = new Properties();

    public PropertiesReader() {
        try {
            String propertiesFilePath = System.getProperty("user.dir") + "/src/test/resources/url.properties";
            InputStream inputStream = Files.newInputStream(Paths.get(propertiesFilePath));
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEndPointUrl(String endpoint) {
        return prop.getProperty("base_url") + prop.getProperty(endpoint);
    }

}