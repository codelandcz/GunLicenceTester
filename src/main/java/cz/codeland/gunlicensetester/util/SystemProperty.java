package cz.codeland.gunlicensetester.util;

import java.util.Properties;

public class SystemProperty
{
  public static final String     EOL                = System.getProperty("line.separator");
  public static final String     CONFIGURATION_PATH = "GunLicenseTester.cfg";
  public static final Properties DEFAULT_PROPERTIES = new Properties();

  static {
    DEFAULT_PROPERTIES.setProperty("InputPathQuestions", "/questions.pdf");
    DEFAULT_PROPERTIES.setProperty("InputPathAnswers", "/answers.csv");
  }
}
