package cz.codeland.gunlicensetester.util;

import cz.codeland.gunlicensetester.Answer;
import cz.codeland.gunlicensetester.Question;
import cz.codeland.gunlicensetester.QuestionStyle;

import java.util.List;

public class QuestionsPrinter
{
  private static final String correctAnswerMark = "*";

  public static String questionsToString(List<Question> questions, QuestionStyle questionStyle)
  {
    StringBuilder result = new StringBuilder();

    int questionNumber = 1;
    for(Question question : questions) {
      result
        .append(questionToString(questionNumber, question, questionStyle))
        .append(SystemProperty.EOL);
      questionNumber++;
    }

    return result.toString();
  }

  public static String questionToString(int questionNumber, Question question, QuestionStyle questionStyle)
  {
    StringBuilder result = new StringBuilder();

    result
      .append(questionNumber)
      .append(". ")
      .append(question.getText())
      .append(SystemProperty.EOL);
    result.append(answersToString(question.getAnswers(), questionStyle));

    return result.toString();
  }

  public static String answersToString(List<Answer> answers, QuestionStyle questionStyle)
  {
    StringBuilder result = new StringBuilder();

    char answerLetter = 'a';
    for(Answer answer : answers) {
      result
        .append(answerToString(answerLetter, answer, questionStyle))
        .append(SystemProperty.EOL);
      answerLetter++;
    }

    return result.toString();
  }

  public static String answerToString(char answerLetter, Answer answer, QuestionStyle questionStyle)
  {
    StringBuilder result = new StringBuilder();

    result.append(answerLetter)
      .append(") ");
    if(answer.isCorrect() && questionStyle == QuestionStyle.LEARNING) {
      result
        .append(correctAnswerMark)
        .append(" ");
    }
    result.append(answer.getText());
    if(answer.isCorrect() && questionStyle == QuestionStyle.LEARNING) {
      result
        .append(" ")
        .append(correctAnswerMark);
    }

    return result.toString();
  }
}
