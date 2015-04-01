package cz.codeland.gunlicensetester;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GunLicenseTester
{
  private static final Logger LOGGER = Logger.getLogger(GunLicenseTester.class.getName());

  public Properties loadConfiguration(String configurationPath, Properties defaultConfiguration)
  {
    LOGGER.info("Loading the configuration file \"" + configurationPath + "\".");

    prepareConfigurationFile(configurationPath, defaultConfiguration);
    Properties properties = readConfigurationFile(configurationPath, defaultConfiguration);

    return properties;
  }

  public void prepareConfigurationFile(String configurationPath, Properties defaultConfiguration)
  {
    LOGGER.info("Checking if configuration file exists.");

    File file = new File(configurationPath);
    try {
      if(!file.exists()) {
        createDefaultConfigurationFile(file, defaultConfiguration);
      }
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during creating the configuration file", e);
    }
  }

  public void createDefaultConfigurationFile(File file, Properties defaultConfiguration) throws IOException
  {
    LOGGER.info("Creating new configuration file: " + file.getPath());

    file.createNewFile();

    try {
      final FileOutputStream outputStream = new FileOutputStream(file.getPath());
      defaultConfiguration.store(outputStream, "GunLicenseTester Configuration");
      outputStream.close();
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during writing configuration file.", e);
    }
  }

  public Properties readConfigurationFile(String configurationPath, Properties defaultConfiguration)
  {
    LOGGER.info("Reading the configuration file: " + configurationPath);

    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(configurationPath));
    } catch(FileNotFoundException e) {
      LOGGER.log(Level.WARNING, "Configuration file is missing.", e);
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during reading the configuration file", e);
    }

    //TODO Test this
    for(String key : defaultConfiguration.stringPropertyNames()) {
      String defaultValue = defaultConfiguration.getProperty(key);
      properties.setProperty(key, properties.getProperty(key, defaultValue));
    }

    return properties;
  }

  public void generateDatabase(InputStream inputStreamQuestions, InputStream inputStreamAnswers)
  {
    LOGGER.info("Generating the database.");

    Database database = new Database();
    try {
      database.generateDatabase(inputStreamQuestions, inputStreamAnswers);
    } catch(IOException e) {
      LOGGER.log(Level.SEVERE, "IO problem during generating the database.", e);
    } catch(SQLException e) {
      LOGGER.log(Level.SEVERE, "Problem with SQL execution during generating the database.", e);
    }
  }
}
