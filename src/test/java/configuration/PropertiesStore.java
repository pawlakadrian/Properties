package configuration;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public enum PropertiesStore {

    BROWSER("browser"),
    ENVIRONMENT("environment");
    private String value;
    private String propertyKey;
    public static final String CONFIG_PROPERTIES = "config.properties";
    public static Properties properties = null;

    private PropertiesStore(String key) {
        this.value = this.retriveValue(key);
        this.propertyKey = key;
    }

    private String retriveValue(String key) {
        return System.getProperty(key) != null ? System.getProperty(key) : getValueFromConfigFile(key);
    }

    private String getValueFromConfigFile(String key) {
        if (properties == null) {
            properties = loadConfigFile();
        }
        Object objFromFile = properties.get(key);
        return objFromFile != null ? Objects.toString(objFromFile) : null;
    }

    private Properties loadConfigFile() {
        Properties copy = null;
        try {
            InputStream configFileStream = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");

            try {
                Properties properties = new Properties();
                properties.load(configFileStream);
                copy = properties;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (configFileStream != null) {
                    try {
                        configFileStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copy;
    }

    public boolean isSpecified() {
        return StringUtils.isNotEmpty(this.value);
    }

    public String getValue() {
        return this.retriveValue(this.propertyKey);
    }

    public int getIntValue() {
        return Integer.parseInt(this.retriveValue(this.propertyKey));
    }
}
