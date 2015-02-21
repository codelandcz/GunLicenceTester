package cz.codeland.gunlicencetester;

import java.util.ArrayList;
import java.util.List;

public class Question
{
  private String text;
  private List<Answer> answers = new ArrayList<>();

  public Question(String text)
  {
    this.text = text;
  }

  public List<Answer> getAnswers()
  {
    return answers;
  }

  public Question addAnswer(Answer answer)
  {
    answers.add(answer);
    return this;
  }

  public String getText()
  {
    return text;
  }

  @Override
  public String toString()
  {
    StringBuffer stringBuffer = new StringBuffer();

    for (Answer answer : getAnswers()) {
      stringBuffer.append(answer + ", ");
    }

    return "{ Question, text: " + getText() + ", Answers: [" + stringBuffer + "] }";
  }
}
