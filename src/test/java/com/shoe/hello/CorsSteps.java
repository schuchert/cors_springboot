package com.shoe.hello;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class CorsSteps {
    private TestApplicationContext server;

    private ResponseEntity<String> responseEntity;

    @Given("the system is running")
    public void theSystemIsRunning() {
        if(server == null)
            server = new TestApplicationContext();
    }

    @When("making a non-core request")
    public void makingANonCoreRequest() {
        responseEntity = server.restCall("greeting", HttpMethod.GET, String.class);
    }

    @Then("the result should be swell")
    public void theResultShouldBeSwell() {
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
