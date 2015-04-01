package cz.codeland.gunlicensetester;

import cz.codeland.gunlicensetester.util.DAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class GunLicenseTesterTest
{
  private String     configurationPath;
  private Properties defaultConfiguration;

  @Before
  public void setUp() throws Exception
  {
    configurationPath = "GunLicenseTesterTest.cfg";

    defaultConfiguration = new Properties();
    defaultConfiguration.setProperty("InputPathQuestions", "/questionsTest.pdf");
    defaultConfiguration.setProperty("InputPathAnswers", "/answersTest.csv");
    DAO.beginTransaction();
  }

  @After
  public void tearDown() throws Exception
  {
    DAO.rollbackTransaction();
    DAO.close();
  }

  @Test
  public void loadConfiguration_CheckIfNewConfigurationFileIsCreated_CreatesNewConfigurationFile() throws Exception
  {
    // Given
    GunLicenseTester tester = new GunLicenseTester();

    // When
    tester.loadConfiguration(configurationPath, defaultConfiguration);

    // Then
    File file = new File(configurationPath);
    Assert.assertTrue(file.exists());
  }

  @Test
  public void loadConfiguration_LoadConfiguration_ConfigurationFileIsCreatedWithDefaultValues() throws Exception
  {
    // Given
    GunLicenseTester tester = new GunLicenseTester();

    // When
    final Properties properties = tester.loadConfiguration(configurationPath, defaultConfiguration);

    // Then
    Assert.assertEquals("/questionsTest.pdf", properties.getProperty("InputPathQuestions"));
  }

  @Test
  public void generateDatabase_Scenario_ExpectedBehavior() throws Exception
  {
    // Given
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

    GunLicenseTester gunLicenseTester = new GunLicenseTester();
    final Properties properties = gunLicenseTester.loadConfiguration(configurationPath, defaultConfiguration);
    InputStream inputStreamQuestions = this.getClass().getResourceAsStream(properties.getProperty("InputPathQuestions"));
    InputStream inputStreamAnswers = this.getClass().getResourceAsStream(properties.getProperty("InputPathAnswers"));

    // When
    gunLicenseTester.generateDatabase(inputStreamQuestions, inputStreamAnswers);

    // Then
    List actualQuestions = DAO.getSession().createQuery("from Question").list();
    for(int i = 0; i < actualQuestions.size(); i++) {
      Question actualQuestion = (Question) actualQuestions.get(i);
      Question expectedQuestion = expectedQuestions.get(i);
      Assert.assertEquals(expectedQuestion, actualQuestion);
    }
  }
}
