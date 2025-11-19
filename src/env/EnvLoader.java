package env;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(".env");
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Could not load .env file");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}