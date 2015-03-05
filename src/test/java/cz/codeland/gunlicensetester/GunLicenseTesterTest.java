package cz.codeland.gunlicensetester;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

public class GunLicenseTesterTest
{
  private String configurationPath;

  @Before
  public void setUp() throws Exception
  {
    configurationPath = "GunLicenseTesterTest.cfg";
    File file = new File(configurationPath);
  }

  @Test
  public void loadConfiguration_CheckIfNewConfigurationFileIsCreated_CreatesNewConfigurationFile() throws Exception
  {
    // Given
    GunLicenseTester tester = new GunLicenseTester();

    // When
    tester.loadConfiguration(configurationPath);

    // Then
    File file = new File(configurationPath);
    Assert.assertTrue(file.exists());
  }

  @Test
  public void loadConfiguration_LoadConfiguration_ConfigurationFileIsCreatedWithDefaultValues() throws Exception
  {
    // Given
    GunLicenseTester tester = new GunLicenseTester();

    // When
    final Properties properties = tester.loadConfiguration(configurationPath);

    // Then
    Assert.assertEquals("/questions.pdf", properties.getProperty("InputPathQuestions"));
  }
}
