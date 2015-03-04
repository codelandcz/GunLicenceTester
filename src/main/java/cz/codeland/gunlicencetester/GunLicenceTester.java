package cz.codeland.gunlicencetester;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GunLicenceTester
{
  private static final Logger LOGGER = Logger.getLogger(GunLicenceTester.class.getName());
  private final SessionFactory sessionFactory;
  private final Session        session;

  public GunLicenceTester()
  {
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    session = sessionFactory.openSession();
  }

  public static void main(String[] args)
  {
    GunLicenceTester gunLicenceTester = new GunLicenceTester();
    gunLicenceTester.generateDatabase();

  }

  private void generateDatabase()
  {
    LOGGER.info("Generating the database.");
    final String absResourcePathQuestions = "/questions.pdf";
    final String absResourcePathAnswers = "/answers.csv";
    InputStream inputStreamQuestions = this.getClass().getResourceAsStream(absResourcePathQuestions);
    InputStream inputStreamAnswers = this.getClass().getResourceAsStream(absResourcePathAnswers);

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
