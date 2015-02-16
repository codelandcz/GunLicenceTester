package cz.codeland.gunlicencetester;

import java.util.List;

public interface TextParser
{
  public List<Question> parseText(String text);
}
