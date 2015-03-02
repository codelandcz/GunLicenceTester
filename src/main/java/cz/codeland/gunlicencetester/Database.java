package cz.codeland.gunlicencetester;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database
{
  private static final Logger LOGGER = Logger.getLogger(DefaultPDFTextExtractor.class.getName());
  private final SessionFactory sessionFactory;

  public Database()
  {
    Configuration configuration = new Configuration().configure();
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();

    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  }

  public void generateDatabase(InputStream inputStreamQuestions, InputStream inputStreamAnswers) throws IOException, SQLException
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
      }

    } catch(IOException e) {
      LOGGER.log(Level.SEVERE, "IO problem with reading the input stream of answers.", e);
    }

    return questions;
  }

  private void persistQuestions(List<Question> questions) throws SQLException
  {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.getTransaction();

    try {
      transaction.begin();
      for(Question question : questions) {
        session.save(question);
      }
      transaction.commit();
    } catch(RuntimeException e) {
      transaction.rollback();
      throw e;
    } finally {
      session.close();
    }
  }

}
