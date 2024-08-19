package com.skillstorm;

import io.cucumber.java.en.*;

import org.junit.jupiter.api.Assertions.*;

public class GivenThenWhenTemplate {

    @Given("an example scenario")
    public void anExampleScenario() {
        System.out.println("we made it here 1");
    }

    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
    }

    @Then("the scenario passes")
    public void theScenarioPasses() {
    }

}
