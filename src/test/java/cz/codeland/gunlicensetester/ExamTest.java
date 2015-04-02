package cz.codeland.gunlicensetester;

import cz.codeland.gunlicensetester.model.Answer;
import cz.codeland.gunlicensetester.model.Question;
import cz.codeland.gunlicensetester.util.QuestionStyle;
import cz.codeland.gunlicensetester.util.QuestionsPrinter;
import cz.codeland.gunlicensetester.util.SystemProperty;
import org.junit.Assert;
import org.junit.Test;

public class ExamTest
{
  @Test
  public void printQuestion_printFirstQuestionFromTheExam_printFirstQuestionWithOrderedListOfAnswers() throws Exception
  {
    // Given
    String textQ = "Pro účely zákona o zbraních se rozumí držením zbraně nebo střeliva mít";
    String textA = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí bez souhlasu vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo,";
    String textB = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, nebo zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo,";
    String textC = "zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru.";
    Question question = new Question(textQ);
    question.addAnswer(new Answer(textA));
    final Answer answerB = new Answer(textB);
    answerB.setCorrect(true);
    question.addAnswer(answerB);
    question.addAnswer(new Answer(textC));
    Exam exam = new Exam();
    exam.addQuestion(question);
    // When
    String examText = QuestionsPrinter.questionToString(1, question, QuestionStyle.EXAM);
    // Then
    String expected =
      "1. Pro účely zákona o zbraních se rozumí držením zbraně nebo střeliva mít" + SystemProperty.EOL +
        "a) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí bez souhlasu vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo," + SystemProperty.EOL +
        "b) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, nebo zbraň nenabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru a uloženou v uzavřeném obalu za účelem jejího přemístění z místa na místo," + SystemProperty.EOL +
        "c) zbraň nebo střelivo uvnitř bytových nebo provozních prostor nebo uvnitř zřetelně ohraničených nemovitostí se souhlasem vlastníka nebo nájemce uvedených prostor nebo nemovitostí, zbraň nabitou náboji v zásobníku, nábojové schránce, nábojové komoře hlavně nebo nábojových komorách válce revolveru." + SystemProperty.EOL;
    Assert.assertEquals(expected, examText);
  }
}
