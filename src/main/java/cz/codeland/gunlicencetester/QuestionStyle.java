package cz.codeland.gunlicencetester;

public enum QuestionStyle
{
  // With question number, marked correct answer, all questions in one time
  LEARNING,
  // No question number, no marked correct answer, one question in one time
  TRAINING,
  // With question number, no marked correct answer, filtered questions in one time
  TESTING
}
