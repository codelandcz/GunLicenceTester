package cz.codeland.gunlicencetester;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class DatabaseTest
{

  private Database       database;
  private SessionFactory sessionFactory;

  @Before
  public void setUp() throws Exception
  {
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    database = new Database();
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void generateDatabase_HibernateORM_CreatedTestDatabase() throws Exception
  {
    // Given
    final String absResourcePathQuestions = "/questionsTest.pdf";
    final String absResourcePathAnswers = "/answersTest.csv";

    InputStream inputStreamQuestions = this.getClass().getResourceAsStream(absResourcePathQuestions);
    InputStream inputStreamAnswers = this.getClass().getResourceAsStream(absResourcePathAnswers);

    List<Question> expectedQuestions = new LinkedList<>();
    Question expectedQuestion1 = new Question("Testovací otázka 1");
    Answer expectedAnswer1A = new Answer("Testovací odpověď a").setCorrect(true).setQuestion(expectedQuestion1);
    Answer expectedAnswer1B = new Answer("Testovací odpověď b").setQuestion(expectedQuestion1);
    Answer expectedAnswer1C = new Answer("Testovací odpověď c").setQuestion(expectedQuestion1);
    expectedQuestion1.addAnswer(expectedAnswer1A);
    expectedQuestion1.addAnswer(expectedAnswer1B);
    expectedQuestion1.addAnswer(expectedAnswer1C);
    Question expectedQuestion2 = new Question("Testovací otázka 2");
    Answer expectedAnswer2A = new Answer("Testovací odpověď a").setQuestion(expectedQuestion2);
    Answer expectedAnswer2B = new Answer("Testovací odpověď b").setCorrect(true).setQuestion(expectedQuestion2);
    Answer expectedAnswer2C = new Answer("Testovací odpověď c").setQuestion(expectedQuestion2);
    expectedQuestion2.addAnswer(expectedAnswer2A);
    expectedQuestion2.addAnswer(expectedAnswer2B);
    expectedQuestion2.addAnswer(expectedAnswer2C);
    Question expectedQuestion3 = new Question("Testovací otázka 3");
    Answer expectedAnswer3A = new Answer("Testovací odpověď a").setQuestion(expectedQuestion3);
    Answer expectedAnswer3B = new Answer("Testovací odpověď b").setQuestion(expectedQuestion3);
    Answer expectedAnswer3C = new Answer("Testovací odpověď c").setCorrect(true).setQuestion(expectedQuestion3);
    expectedQuestion3.addAnswer(expectedAnswer3A);
    expectedQuestion3.addAnswer(expectedAnswer3B);
    expectedQuestion3.addAnswer(expectedAnswer3C);
    expectedQuestions.add(expectedQuestion1);
    expectedQuestions.add(expectedQuestion2);
    expectedQuestions.add(expectedQuestion3);

    // When
    database.generateDatabase(inputStreamQuestions, inputStreamAnswers);

    Session session = sessionFactory.openSession();
    Transaction transaction = session.getTransaction();
    List actualQuestions;
    try {
      transaction.begin();
      actualQuestions = session.createQuery("from Question").list();
      transaction.commit();
    } catch(RuntimeException e) {
      transaction.rollback();
      throw e;
    } finally {
      session.close();
    }

    // Then
    Assert.assertEquals(expectedQuestions.size(), actualQuestions.size());

    for(int i = 0; i < actualQuestions.size(); i++) {
      Question expectedQuestion = expectedQuestions.get(i);
      Question actualQuestion = (Question) actualQuestions.get(i);

      Assert.assertTrue(expectedQuestion.equals(actualQuestion));
    }
  }
}
