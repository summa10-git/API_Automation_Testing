package apiCucumberProject.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
				glue = "stepdefs",
				//tags = {"@Get","@Post"},
				plugin = {"pretty", 
						"html:test_output/cucumberreport",
						"json:test_output/json_report/report.json"} // this report is required by Jenkin's 'cucumber report' plug in to generate report on Jenkins dashboard
		)
public class RunnerTest {

}
