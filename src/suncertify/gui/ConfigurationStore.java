package suncertify.gui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This class backups the configuration parameters of the application. The
 * parameters are stored in the suncertify.properties file located in the
 * working directory. This class implements singleton pattern.
 * */
public class ConfigurationStore {
	
	public static final String			PORT			= "1234";
	public static final String			DB_FILE_PATH	= "db-1x1.db";
	public static final String			HOST			= "LOCALHOST";
	
	private static ConfigurationStore	instance;
	
	private static File					file			= new File("./suncertify.properties");
	private final Properties			configurationProperties;
	
	private ConfigurationStore() throws ApplicationException {
		FileReader fileReader = null;
		configurationProperties = new Properties();
		try {
			if (file.exists()) {
				if (file.canRead()) {
					fileReader = new FileReader(file);
					configurationProperties.load(fileReader);
				} else {
					throw new ApplicationException(
							"Could not able to read the properties file: ./suncertify.properties");
				}
			}
			
		} catch (IOException e) {
			throw new ApplicationException(e.getLocalizedMessage());
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				throw new ApplicationException(e.getLocalizedMessage());
			}
		}
	}
	
	public static synchronized ConfigurationStore getInstance() throws ApplicationException {
		if (instance == null) {
			instance = new ConfigurationStore();
		}
		return instance;
	}
	
	public String getParameter(String key) {
		return configurationProperties.getProperty(key);
	}
	
	public void setParameter(String key, String value) throws ApplicationException {
		if (key != null && value != null) {
			configurationProperties.setProperty(key, value);
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(file);
				if (file.canWrite()) {
					configurationProperties.store(fileWriter, "Configurations for Urlybird Application");
				} else {
					throw new ApplicationException("Could not able to write to the configuration file");
				}
			} catch (Exception e) {
				throw new ApplicationException("Could not able to write  to the configuration file");
			} finally {
				try {
					if (fileWriter != null) {
						fileWriter.close();
					}
				} catch (IOException e) {
					throw new ApplicationException("Could not able to write to the configuration file");
				}
			}
		} else {
			throw new ApplicationException("Configuration parameters should never be null.");
		}
	}
	
}
