package cz.codeland.gunlicensetester;

import org.junit.Assert;
import org.junit.Test;

public class QuestionTest
{
  @Test
  public void equals_Scenario_SameQuestions() throws Exception
  {
    // Given
    Question expectedQuestion = new Question("Question");
    expectedQuestion.addAnswer(new Answer("Answer 1"));
    expectedQuestion.addAnswer(new Answer("Answer 2"));
    expectedQuestion.addAnswer(new Answer("Answer 3"));

    Question actualQuestion = new Question("Question");
    actualQuestion.addAnswer(new Answer("Answer 1"));
    actualQuestion.addAnswer(new Answer("Answer 2"));
    actualQuestion.addAnswer(new Answer("Answer 3"));
    // When
    // Then
    Assert.assertEquals(expectedQuestion, actualQuestion);
  }

  @Test
  public void equals_SameAnswersButDifferentQuestion_QuestionsNotEquals() throws Exception
  {
      // Given
      Question expectedQuestion = new Question("Question 1");
      expectedQuestion.addAnswer(new Answer("Answer 1"));
      expectedQuestion.addAnswer(new Answer("Answer 2"));
      expectedQuestion.addAnswer(new Answer("Answer 3"));

      Question actualQuestion = new Question("Question 2");
      actualQuestion.addAnswer(new Answer("Answer 1"));
      actualQuestion.addAnswer(new Answer("Answer 2"));
      actualQuestion.addAnswer(new Answer("Answer 3"));
      // When
      // Then
      Assert.assertNotEquals(expectedQuestion, actualQuestion);
    }

  @Test
  public void equals_SameAnswersButDifferentPositions_DifferentAnswer() throws Exception
  {
    // Given
    Question expectedQuestion = new Question("Question");
    expectedQuestion.addAnswer(new Answer("Answer 1"));
    expectedQuestion.addAnswer(new Answer("Answer 3"));
    expectedQuestion.addAnswer(new Answer("Answer 2"));

    Question actualQuestion = new Question("Question");
    actualQuestion.addAnswer(new Answer("Answer 1"));
    actualQuestion.addAnswer(new Answer("Answer 2"));
    actualQuestion.addAnswer(new Answer("Answer 3"));
    // When
    // Then
    Assert.assertNotEquals(expectedQuestion, actualQuestion);
  }
}
