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
  public void compareTo_Scenario_InstancesAreEqual() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Answer actualAnswer = new Answer("Answer");
    // When
    // Then
    Assert.assertEquals(expectedAnswer, actualAnswer);
  }

  @Test
  public void compareTo_Scenario_InstancesAreNotEqual() throws Exception
  {
    // Given
    Answer expectedAnswer = new Answer("Answer");
    Answer actualAnswer = new Answer("Different answer");
    // When
    // Then
    Assert.assertNotEquals(expectedAnswer, actualAnswer);
  }
}
