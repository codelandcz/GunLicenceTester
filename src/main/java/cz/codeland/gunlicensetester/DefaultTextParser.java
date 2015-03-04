package cz.codeland.gunlicensetester;

import cz.codeland.gunlicensetester.util.SystemProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DefaultTextParser implements TextParser
{
  private static final Logger LOGGER   = Logger.getLogger(DefaultTextParser.class.getName());
  private static       String EOLregex = SystemProperty.EOL.replaceAll("\\\\", "\\");

  @Override
  public List<Question> parseText(String text)
  {
    text = textCorrections(text);

    String[] textQuestions = text.split(EOLregex + " " + EOLregex);
    List<Question> questions = new ArrayList<>();
    for(String textQuestion : textQuestions) {
      Question question = createQuestion(textQuestion);
      questions.add(question);
      LOGGER.info("Question parsed: " + question);
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

    String gapReplacement = " " + SystemProperty.EOL + " " + SystemProperty.EOL;
    String numberPrefixText = EOLregex;
    String numberPrefixReplacement = " " + SystemProperty.EOL + " " + SystemProperty.EOL;

    result = result
      .replaceAll(numberPrefixText + "46\\. ", numberPrefixReplacement + "46. ")
      .replaceAll(numberPrefixText + "72\\. ", numberPrefixReplacement + "72. ")
      .replaceAll(numberPrefixText + "110\\. ", numberPrefixReplacement + "110. ");

    result = result
      .replaceAll(" " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll(" " + EOLregex + " " + EOLregex + " " + EOLregex, gapReplacement)
      .replaceAll(" " + EOLregex + " " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll(" " + EOLregex + "  " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll("  " + EOLregex + " " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll("   " + EOLregex + " " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll(" " + EOLregex + " " + EOLregex + " " + EOLregex + "  " + EOLregex, gapReplacement)
      .replaceAll(" " + EOLregex + " " + EOLregex + " " + EOLregex + "  " + EOLregex, gapReplacement);

    return result;
  }
}
