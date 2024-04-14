package stepdefination;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
   //path of feature file
   features = "src/test/resources/features",
   
   
   //path of step definition file
   glue = {"stepdefination"},
   
   tags ="@tag" ,
   
   plugin = {"pretty"},
   
   monochrome =true
   
   )


public class TestRunner {

}
