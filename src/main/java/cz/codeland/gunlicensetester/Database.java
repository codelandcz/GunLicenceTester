package cz.codeland.gunlicensetester;

import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Database
{
  private static final Logger LOGGER = Logger.getLogger(DefaultPDFTextExtractor.class.getName());
  private final Session session;

  public Database(Session session)
  {
    this.session = session;
  }

  public void generateDatabase(InputStream inputStreamQuestions, InputStream inputStreamAnswers) throws IOException, SQLException, RuntimeException
  {
    DefaultPDFTextExtractor extractor = new DefaultPDFTextExtractor();
    String extractedText = extractor.extract(inputStreamQuestions, 2);

    DefaultTextParser parser = new DefaultTextParser();
    List<Question> questions = parser.parseText(extractedText);

    questions = markCorrectAnswers(questions, inputStreamAnswers);

    persistQuestions(questions);
  }

  private List<Question> markCorrectAnswers(List<Question> questions, InputStream inputStreamAnswers)
  {
    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamAnswers))) {
      int[] answers = new int[questions.size()];

      int i = 0;
      String line;
      while((i < questions.size()) && ((line = reader.readLine()) != null)) {
        int parsedAnswer = Integer.parseInt(line);
        if(0 < parsedAnswer && parsedAnswer < 4) {
          answers[i] = parsedAnswer - 1;
          i++;
        }
      }

      for(i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        question.getAnswers().get(answers[i]).setCorrect(true);
        LOGGER.info("Question: " + question.getText() + ", answer marked as correct: " + question.getAnswers().get(i));
      }

    } catch(IOException e) {
      LOGGER.log(Level.SEVERE, "IO problem with reading the input stream of answers.", e);
    }

    return questions;
  }

  private void persistQuestions(List<Question> questions)
  {
    for(Question question : questions) {
      LOGGER.info("Persisting question: " + question);
      session.save(question);
    }
  }

}
