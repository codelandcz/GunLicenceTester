package cz.codeland.gunlicencetester.util;

import cz.codeland.gunlicencetester.Answer;
import cz.codeland.gunlicencetester.Question;
import cz.codeland.gunlicencetester.QuestionStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class QuestionsPrinterTest
{

  private List<Question> expectedQuestions;

  @Before
  public void setUp() throws Exception
  {
    // Given
    expectedQuestions = new LinkedList<>();
    Question expectedQuestion1 = new Question("Test question 1");
    Answer expectedAnswer1A = new Answer("Test answer a").setCorrect(true).setQuestion(expectedQuestion1);
    Answer expectedAnswer1B = new Answer("Test answer b").setQuestion(expectedQuestion1);
    Answer expectedAnswer1C = new Answer("Test answer c").setQuestion(expectedQuestion1);
    expectedQuestion1.addAnswer(expectedAnswer1A);
    expectedQuestion1.addAnswer(expectedAnswer1B);
    expectedQuestion1.addAnswer(expectedAnswer1C);
    Question expectedQuestion2 = new Question("Test question 2");
    Answer expectedAnswer2A = new Answer("Test answer a").setQuestion(expectedQuestion2);
    Answer expectedAnswer2B = new Answer("Test answer b").setCorrect(true).setQuestion(expectedQuestion2);
    Answer expectedAnswer2C = new Answer("Test answer c").setQuestion(expectedQuestion2);
    expectedQuestion2.addAnswer(expectedAnswer2A);
    expectedQuestion2.addAnswer(expectedAnswer2B);
    expectedQuestion2.addAnswer(expectedAnswer2C);
    Question expectedQuestion3 = new Question("Test question 3");
    Answer expectedAnswer3A = new Answer("Test answer a").setQuestion(expectedQuestion3);
    Answer expectedAnswer3B = new Answer("Test answer b").setQuestion(expectedQuestion3);
    Answer expectedAnswer3C = new Answer("Test answer c").setCorrect(true).setQuestion(expectedQuestion3);
    expectedQuestion3.addAnswer(expectedAnswer3A);
    expectedQuestion3.addAnswer(expectedAnswer3B);
    expectedQuestion3.addAnswer(expectedAnswer3C);
    expectedQuestions.add(expectedQuestion1);
    expectedQuestions.add(expectedQuestion2);
    expectedQuestions.add(expectedQuestion3);
  }

  @Test
  public void questionsToString_TrainingQuestions_StringRepresentationOfTheGivenQuestions() throws Exception
  {
    String actual = QuestionsPrinter.questionsToString(expectedQuestions, QuestionStyle.TRAINING);
    String expected =
      "1. Test question 1" + SystemProperty.EOL
        + "a) Test answer a" + SystemProperty.EOL
        + "b) Test answer b" + SystemProperty.EOL
        + "c) Test answer c" + SystemProperty.EOL
        + SystemProperty.EOL +
        "2. Test question 2" + SystemProperty.EOL
        + "a) Test answer a" + SystemProperty.EOL
        + "b) Test answer b" + SystemProperty.EOL
        + "c) Test answer c" + SystemProperty.EOL
        + SystemProperty.EOL +
        "3. Test question 3" + SystemProperty.EOL
        + "a) Test answer a" + SystemProperty.EOL
        + "b) Test answer b" + SystemProperty.EOL
        + "c) Test answer c" + SystemProperty.EOL
        + SystemProperty.EOL;
    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void questionsToString_LearningQuestions_StringRepresentationOfTheGivenQuestionsWithMarkedAnswers() throws Exception
  {
    String actual = QuestionsPrinter.questionsToString(expectedQuestions, QuestionStyle.LEARNING);
    String expected =
      "1. Test question 1" + SystemProperty.EOL
        + "a) * Test answer a *" + SystemProperty.EOL
        + "b) Test answer b" + SystemProperty.EOL
        + "c) Test answer c" + SystemProperty.EOL
        + SystemProperty.EOL +
        "2. Test question 2" + SystemProperty.EOL
        + "a) Test answer a" + SystemProperty.EOL
        + "b) * Test answer b *" + SystemProperty.EOL
        + "c) Test answer c" + SystemProperty.EOL
        + SystemProperty.EOL +
        "3. Test question 3" + SystemProperty.EOL
        + "a) Test answer a" + SystemProperty.EOL
        + "b) Test answer b" + SystemProperty.EOL
        + "c) * Test answer c *" + SystemProperty.EOL
        + SystemProperty.EOL;
    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void questionToString_LearningQuestion_StringRepresentationOfAQuestionWithMarkedAnswer() throws Exception
  {
    // When
    String actual = QuestionsPrinter.questionToString(1, expectedQuestions.get(0), QuestionStyle.LEARNING);
    String expected = "1. Test question 1" + SystemProperty.EOL
      + "a) * Test answer a *" + SystemProperty.EOL
      + "b) Test answer b" + SystemProperty.EOL
      + "c) Test answer c" + SystemProperty.EOL;

    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void answersToString_LearningAnswers_StringRepresentationOfTheAnswersWithMarkedCorrectAnswer() throws Exception
  {
    // When
    String actual = QuestionsPrinter.answersToString(expectedQuestions.get(0).getAnswers(), QuestionStyle.LEARNING);
    String expected = "a) * Test answer a *" + SystemProperty.EOL
      + "b) Test answer b" + SystemProperty.EOL
      + "c) Test answer c" + SystemProperty.EOL;
    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void answersToString_TrainingAnswers_StringRepresentationOfTheAnswers() throws Exception
  {
    // When
    String actual = QuestionsPrinter.answersToString(expectedQuestions.get(0).getAnswers(), QuestionStyle.TRAINING);
    String expected = "a) Test answer a" + SystemProperty.EOL
      + "b) Test answer b" + SystemProperty.EOL
      + "c) Test answer c" + SystemProperty.EOL;
    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void answerToString_LearningAnswer_ReturnsAStringRepresentationWithMarkedCorrectAnswer() throws Exception
  {
    // When
    String actual = QuestionsPrinter.answerToString('a', expectedQuestions.get(0).getAnswers().get(0), QuestionStyle.LEARNING);
    String expected = "a) * Test answer a *";
    // Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void answerToString_LearningAnswer_ReturnsAStringRepresentationOfAnAnswer() throws Exception
  {
    // When
    String actual = QuestionsPrinter.answerToString('b', expectedQuestions.get(0).getAnswers().get(1), QuestionStyle.LEARNING);
    String expected = "b) Test answer b";
    // Then
    Assert.assertEquals(expected, actual);
  }
}
