package cz.codeland.gunlicensetester.extract;

import cz.codeland.gunlicensetester.model.Question;

import java.util.List;

public interface TextParser
{
  public List<Question> parseText(String text);
}
