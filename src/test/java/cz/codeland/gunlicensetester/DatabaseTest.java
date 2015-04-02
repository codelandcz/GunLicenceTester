package cz.codeland.gunlicensetester;

import cz.codeland.gunlicensetester.db.Database;
import cz.codeland.gunlicensetester.model.Answer;
import cz.codeland.gunlicensetester.model.Question;
import cz.codeland.gunlicensetester.db.DAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseTest
{
  private static final Logger LOGGER = Logger.getLogger(DatabaseTest.class.getName());
  private Database database;

  @Before
  public void setUp() throws Exception
  {
    database = new Database();
    DAO.beginTransaction();
  }

  @After
  public void tearDown() throws Exception
  {
    DAO.rollbackTransaction();
    DAO.close();
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

    List actualQuestions;
    actualQuestions = DAO.getSession().createQuery("from Question").list();

    // Then
    Assert.assertEquals(expectedQuestions.size(), actualQuestions.size());

    for(int i = 0; i < actualQuestions.size(); i++) {
      Question expectedQuestion = expectedQuestions.get(i);
      Question actualQuestion = (Question) actualQuestions.get(i);
      LOGGER.info("Comparing: " + expectedQuestion + " with " + actualQuestion);

      Assert.assertEquals(expectedQuestion, actualQuestion);
    }
  }
}
