package cz.codeland.gunlicensetester;

import java.util.ArrayList;
import java.util.List;

public class Exam
{
  private List<Question> questions = new ArrayList<>();

  public Exam addQuestion(Question question)
  {
    questions.add(question);
    return this;
  }

  public List<Question> getQuestions()
  {
    return questions;
  }
}
