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
  @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Answer> answers = new ArrayList<>();

  public Question()
  {
  }

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
    StringBuilder stringBuffer = new StringBuilder();

    for(Answer answer : this.answers) {
      stringBuffer.append(answer).append(", ");
    }

    return "{ Question, text: " + getText() + ", Answers: [" + stringBuffer + "] }";
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) {
      return true;
    }

    if(obj instanceof Question) {
      Question that = (Question) obj;

      boolean sameTexts = this.getText().equals(that.getText());
      boolean sameSizeAnswers = this.getAnswers().size() == that.getAnswers().size();
      if(!sameSizeAnswers || !sameTexts) {
        return false;
      }

      boolean sameAnswers = true;
      for(int i = 0; sameAnswers && i < answers.size(); i++) {
        Answer thisAnswer = answers.get(i);
        Answer thatAnswer = that.getAnswers().get(i);
        sameAnswers = thisAnswer.equals(thatAnswer);
      }

      return sameAnswers;
    }

    return false;
  }
}
