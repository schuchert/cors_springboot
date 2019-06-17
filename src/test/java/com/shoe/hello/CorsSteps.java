package com.shoe.hello;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class CorsSteps {

    private ResponseEntity<String> responseEntity;

    @Given("the system is running")
    public void theSystemIsRunning() {
        TestApplicationContext.getBean(HelloWorldController.class);
    }

    @When("making a non-core request")
    public void makingANonCoreRequest() {
        responseEntity = TestApplicationContext.restCall("greeting", HttpMethod.GET, String.class);
    }

    @Then("the result should be swell")
    public void theResultShouldBeSwell() {
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
