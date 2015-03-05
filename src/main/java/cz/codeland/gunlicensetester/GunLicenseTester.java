package cz.codeland.gunlicensetester;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GunLicenseTester
{
  private static final Logger LOGGER = Logger.getLogger(GunLicenseTester.class.getName());

  public Properties loadConfiguration(String configurationPath)
  {
    LOGGER.info("Loading the configuration file \"" + configurationPath + "\".");

    prepareConfigurationFile(configurationPath);
    Properties properties = readConfigurationFile(configurationPath);

    return properties;
  }

  public void prepareConfigurationFile(String configurationPath)
  {
    LOGGER.info("Checking if configuration file exists.");

    File file = new File(configurationPath);
    try {
      if(!file.exists()) {
        createDefaultConfigurationFile(file);
      }
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during creating the configuration file", e);
    }
  }

  public void createDefaultConfigurationFile(File file) throws IOException
  {
    LOGGER.info("Creating new configuration file: " + file.getPath());

    file.createNewFile();

    Properties properties = new Properties();
    String inputPathQuestions = properties.getProperty("InputPathQuestions", "/questions.pdf").trim();
    properties.setProperty("InputPathQuestions", inputPathQuestions);

    String inputPathAnswers = properties.getProperty("InputPathAnswers", "/answers.csv").trim();
    properties.setProperty("InputPathAnswers", inputPathAnswers);

    try {
      properties.store(new FileOutputStream(file.getPath()), "GunLicenseTester Configuration");
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during writing configuration file.", e);
    }
  }

  public Properties readConfigurationFile(String configurationPath)
  {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(configurationPath));
    } catch(FileNotFoundException e) {
      LOGGER.log(Level.WARNING, "Configuration file is missing.", e);
    } catch(IOException e) {
      LOGGER.log(Level.WARNING, "IO problem during reading the configuration file", e);
    }
    return properties;
  }

  private void generateDatabase()
  {
    LOGGER.info("Generating the database.");
    final String absResourcePathQuestions = "/questions.pdf";
    final String absResourcePathAnswers = "/answers.csv";
    InputStream inputStreamQuestions = this.getClass().getResourceAsStream(absResourcePathQuestions);
    InputStream inputStreamAnswers = this.getClass().getResourceAsStream(absResourcePathAnswers);

    SessionFactory sessionFactory;
    Session session;
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    session = sessionFactory.openSession();

    Database database = new Database(session);
    try {
      database.generateDatabase(inputStreamQuestions, inputStreamAnswers);
    } catch(IOException e) {
      LOGGER.log(Level.SEVERE, "IO problem during generating the database.", e);
    } catch(SQLException e) {
      LOGGER.log(Level.SEVERE, "Problem with SQL execution during generating the database.", e);
    }
  }
}
