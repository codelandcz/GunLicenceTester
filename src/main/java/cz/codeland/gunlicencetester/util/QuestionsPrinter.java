package cz.codeland.gunlicencetester.util;

import cz.codeland.gunlicencetester.Answer;
import cz.codeland.gunlicencetester.Exam;
import cz.codeland.gunlicencetester.Question;

public class QuestionsPrinter
{
  public static String printQuestion(Exam exam, int questionIndex)
    {
      StringBuilder result = new StringBuilder();

      final String EOL = System.getProperty("line.separator");
      Question question = exam.getQuestions().get(questionIndex - 1);
      result.append(questionIndex);
      result.append(". ");
      result.append(question.getText());
      result.append(EOL);
      char orderedIndex = 'a';
      for (Answer answer : question.getAnswers()) {
        result.append(" ");
        result.append(orderedIndex);
        result.append(") ");
        result.append(answer.getText());
        result.append(EOL);

        orderedIndex++;
      }

      return result.toString();
    }
}
