package cz.codeland.gunlicensetester;

import java.util.List;

public interface TextParser
{
  public List<Question> parseText(String text);
}
