package cz.codeland.gunlicencetester;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question
{
  @Id
  @GeneratedValue
  private Long   id;
  private String text;
  @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private List<Answer> answers = new ArrayList<>();

  public Question(String text)
  {
    this.text = text;
  }

  public List<Answer> getAnswers()
  {
    return answers;
  }

  public void setAnswers(List<Answer> answers)
  {
    this.answers = answers;
  }

  public void addAnswer(Answer answer)
  {
    answer.setQuestion(this);
    getAnswers().add(answer);
  }

  public void removeAnswer(Answer answer)
  {
    getAnswers().remove(answer);
  }

  public Long getId()
  {
    return id;
  }

  private void setId(Long id)
  {
    this.id = id;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  @Override
  public String toString()
  {
    StringBuffer stringBuffer = new StringBuffer();

    for (Answer answer : this.answers) {
      stringBuffer.append(answer + ", ");
    }

    return "{ Question, text: " + getText() + ", Answers: [" + stringBuffer + "] }";
  }
}
