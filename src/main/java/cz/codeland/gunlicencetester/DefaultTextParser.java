package cz.codeland.gunlicencetester;

import java.util.ArrayList;
import java.util.List;

public class DefaultTextParser implements TextParser
{
  private static String EOL     = "\\r\\n";
  private static String EOLtext = "\r\n";

  @Override
  public List<Question> parseText(String text)
  {
    text = textCorrections(text);

    String[] textQuestions = text.split(EOL + " " + EOL);
    List<Question> questions = new ArrayList<>();
    for (String textQuestion : textQuestions) {
      Question question = createQuestion(textQuestion);
      questions.add(question);
    }

    return questions;
  }

  private Question createQuestion(String text)
  {
    String[] questionAndAnswers = text.split("[a-c]\\) ");
    Question question = new Question(clean(questionAndAnswers[0]));
    question.addAnswer(new Answer(clean(questionAndAnswers[1])).setQuestion(question));
    question.addAnswer(new Answer(clean(questionAndAnswers[2])).setQuestion(question));
    question.addAnswer(new Answer(clean(questionAndAnswers[3])).setQuestion(question));
    return question;
  }

  private String clean(String text)
  {
    String result = text
      .trim()
      .replaceAll("^\\d+\\.", "")
      .trim()
      .replaceAll(",$", "")
      .replaceAll("\\.$", "")
      .replaceAll("“", "\"");

    return result;
  }

  private String textCorrections(String extractedText)
  {
    String result = extractedText;

    String gapReplacement = " " + EOLtext + " " + EOLtext;
    String numberPrefixText = EOL;
    String numberPrefixReplacement = " " + EOLtext + " " + EOLtext;

    result = result
      .replaceAll(numberPrefixText + "46\\. ", numberPrefixReplacement + "46. ")
      .replaceAll(numberPrefixText + "72\\. ", numberPrefixReplacement + "72. ")
      .replaceAll(numberPrefixText + "110\\. ", numberPrefixReplacement + "110. ");

    result = result
      .replaceAll(" " + EOL + "  " + EOL, gapReplacement)
      .replaceAll(" " + EOL + " " + EOL + " " + EOL, gapReplacement)
      .replaceAll(" " + EOL + " " + EOL + "  " + EOL, gapReplacement)
      .replaceAll(" " + EOL + "  " + EOL + "  " + EOL, gapReplacement)
      .replaceAll("  " + EOL + " " + EOL + "  " + EOL, gapReplacement)
      .replaceAll("   " + EOL + " " + EOL + "  " + EOL, gapReplacement)
      .replaceAll(" " + EOL + " " + EOL + " " + EOL + "  " + EOL, gapReplacement)
      .replaceAll(" " + EOL + " " + EOL + " " + EOL + "  " + EOL, gapReplacement);

    return result;
  }
}
