package cz.codeland.gunlicencetester;

public class Answer
{
  private boolean correct;
  private String  text;

  public Answer(String text)
  {
    this(text, false);
  }

  public Answer(String text, boolean correct)
  {
    this.correct = correct;
    this.text = text;
  }

  public String getText()
  {
    return text;
  }

  public boolean isCorrect()
  {
    return correct;
  }

  public void setCorrect(boolean correct)
  {
    this.correct = correct;
  }
}
