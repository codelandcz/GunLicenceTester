package cz.codeland.gunlicencetester.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  format = {"pretty", "html:target/cucumber-html-report", "html:target/cucumber-html-report"}
)
public class RunCukesTest
{
}