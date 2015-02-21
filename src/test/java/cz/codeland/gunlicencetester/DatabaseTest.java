package cz.codeland.gunlicencetester;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class DatabaseTest
{

  private Database database;

  @Before
  public void setUp() throws Exception
  {
    database = new Database();
  }

  @After
  public void tearDown() throws Exception
  {
    database.close();
  }

  @Test
  public void generateDatabase_Scenario_ExpectedBehavior() throws Exception
  {
    // Given
    final String absResourcePathQuestions = "/questions.pdf";
    final String absResourcePathAnswers = "/answersTest.csv";

    List<Question> expectedQuestions = new LinkedList<>();
    Question expectedQuestion = new Question("Q1");
    expectedQuestion.addAnswer(new Answer("A11"));
    expectedQuestion.addAnswer(new Answer("A12"));
    expectedQuestion.addAnswer(new Answer("A13"));
    expectedQuestions.add(expectedQuestion);

    expectedQuestion = new Question("Q2");
    expectedQuestion.addAnswer(new Answer("A21"));
    expectedQuestion.addAnswer(new Answer("A22"));
    expectedQuestion.addAnswer(new Answer("A23"));
    expectedQuestions.add(expectedQuestion);

    expectedQuestion = new Question("Q3");
    expectedQuestion.addAnswer(new Answer("A31"));
    expectedQuestion.addAnswer(new Answer("A32"));
    expectedQuestion.addAnswer(new Answer("A33"));
    expectedQuestions.add(expectedQuestion);

    InputStream inputStreamQuestions = this.getClass().getResourceAsStream(absResourcePathQuestions);
    InputStream inputStreamAnswers = this.getClass().getResourceAsStream(absResourcePathAnswers);

    // When
    database.generateDatabase(inputStreamQuestions, inputStreamAnswers);

    // Then
    List<Question> actualQuestions = new LinkedList<>();
    ResultSet dbResultQuestions = database.getConnection().createStatement().executeQuery("SELECT * FROM question");
    PreparedStatement statement = database.getConnection().prepareStatement("SELECT * FROM answer WHERE question_id = ? ORDER BY id");
    while (dbResultQuestions.next()) {
      Question actualQuestion = new Question(dbResultQuestions.getString("question"));

      statement.setLong(1, dbResultQuestions.getLong("id"));
      ResultSet dbResultAnswers = statement.executeQuery();
      while (dbResultAnswers.next()) {
        Answer actualAnswer = new Answer(dbResultAnswers.getString("answer"));
        actualAnswer.setCorrect(dbResultAnswers.getBoolean("correct"));
        actualQuestion.addAnswer(actualAnswer);
      }

      actualQuestions.add(actualQuestion);
    }

    Assert.assertTrue(actualQuestions.get(0).getAnswers().get(1).isCorrect());
    Assert.assertTrue(actualQuestions.get(1).getAnswers().get(0).isCorrect());
    Assert.assertTrue(actualQuestions.get(2).getAnswers().get(2).isCorrect());
  }
}
