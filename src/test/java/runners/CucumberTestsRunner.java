package runners;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/features"},
        glue = {"stepdefs"},
        plugin = {"pretty", "json:build/cucumber-reports/json-report/cucumber.json"})

public class CucumberTestsRunner {

    @AfterClass
    public static void tearDown() {

        File reportOutputDir = new File("build/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("build/cucumber-reports/json-report/cucumber.json");
        Configuration configuration = new Configuration(reportOutputDir, "API Tests");

        //Generates html test reports
        new ReportBuilder(jsonFiles, configuration).generateReports();
    }
}