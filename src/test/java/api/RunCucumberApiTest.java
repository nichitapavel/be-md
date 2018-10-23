package api;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
                plugin = {"pretty"},
                glue = {"api"}
        )
public class RunCucumberApiTest {
}