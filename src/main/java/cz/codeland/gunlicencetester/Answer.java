package cz.codeland.gunlicencetester;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer
{
  @Id
  @GeneratedValue
  private Long     id;
  private boolean  correct;
  private String   text;
  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  public Answer()
  {
  }

  public Answer(String text)
  {
    this(text, false);
  }

  public Answer(String text, boolean correct)
  {
    this.correct = correct;
    this.text = text;
  }

  public Question getQuestion()
  {
    return question;
  }

  public Answer setQuestion(Question question)
  {
    this.question = question;
    return this;
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

  public boolean isCorrect()
  {
    return correct;
  }

  public Answer setCorrect(boolean correct)
  {
    this.correct = correct;
    return this;
  }

  @Override
  public String toString()
  {
    return "{ Answer, text: " + this.getText() + ", correct: " + this.isCorrect() + "}";
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) {
      return true;
    }

    if(obj instanceof Answer) {
      Answer that = (Answer) obj;
      boolean sameText = this.getText().equals(that.getText());
      boolean sameCorrect = this.isCorrect() == that.isCorrect();

      return sameText && sameCorrect;
    }

    return false;
  }
}
