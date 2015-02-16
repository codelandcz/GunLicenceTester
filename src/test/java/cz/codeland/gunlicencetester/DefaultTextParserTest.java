package cz.codeland.gunlicencetester;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DefaultTextParserTest
{
  @Test
  public void parseText_Scenario_ExpectedBehavior() throws Exception
  {
    // Given
    String textQ = "Pro účely zákona o zbraních se rozumí držením zbraně nebo střeliva mít";
    String textA = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí bez souhlasu vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo";
    String textB = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, nebo zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo";
    String textC = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru";
    List<Question> expected = new ArrayList<>();
    Question question = new Question(textQ);
    question.addAnswer(new Answer(textA));
    question.addAnswer(new Answer(textB).setCorrect(true));
    question.addAnswer(new Answer(textC));
    expected.add(question);

    String text = "1. Pro účely zákona o zbraních se rozumí držením zbraně nebo střeliva mít \n" +
      "a) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených \n" +
      "nemovitostí bez souhlasu vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň \n" +
      "nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových \n" +
      "komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na \n" +
      "místo, \n" +
      "b) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených \n" +
      "nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, nebo \n" +
      "zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo \n" +
      "nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího \n" +
      "přemístění z místa na místo, \n" +
      "c) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených \n" +
      "nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň \n" +
      "nabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových \n" +
      "komorách válce revolveru. \n" +
      " ";
    TextParser parser = new DefaultTextParser();
    // When
    List<Question> actual = parser.parseText(text);
    // Then
    Assert.assertEquals(expected.get(0).getText(),actual.get(0).getText());
    Assert.assertEquals(expected.get(0).getAnswers().get(0).getText(), actual.get(0).getAnswers().get(0).getText());
    Assert.assertEquals(expected.get(0).getAnswers().get(1).getText(), actual.get(0).getAnswers().get(1).getText());
    Assert.assertEquals(expected.get(0).getAnswers().get(2).getText(), actual.get(0).getAnswers().get(2).getText());
  }
}
