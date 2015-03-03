package cz.codeland.gunlicencetester;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnswerTest
{
  @Before
  public void setUp() throws Exception
  {

  }

  @Test
  public void equals_Scenario_InstancesAreEqual() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Answer actualAnswer = new Answer("Answer");
    // When
    // Then
    Assert.assertEquals(expectedAnswer, actualAnswer);
  }

  @Test
  public void equals_Scenario_InstancesAreNotEqual() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Answer actualAnswer = new Answer("Different answer");
    // When
    // Then
    Assert.assertNotEquals(expectedAnswer, actualAnswer);
  }

  @Test
  public void equals_Scenario_SameInstance() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Answer actualAnswer = expectedAnswer;
    // When
    // Then
    Assert.assertEquals(expectedAnswer, actualAnswer);
  }

  @Test
  public void equals_Scenario_DifferentClasses() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Object differentClass = new Object();
    // When
    // Then
    Assert.assertNotEquals(expectedAnswer, differentClass);
  }
}
