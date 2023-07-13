package gen.drazhev.ewallet.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	private final Properties properties;

	public ConfigFileReader() {
		BufferedReader reader;
		String propertyFilePath = "src/main/resources/application.properties";
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Properties file not found at " + propertyFilePath);
		}
	}

	public String getPropertyValue(String property) {
		String propertyValue = properties.getProperty(property);
		if (propertyValue != null) {
			return propertyValue;
		} else {
			throw new RuntimeException("The provided property name is not specified in the property file.");
		}
	}

}