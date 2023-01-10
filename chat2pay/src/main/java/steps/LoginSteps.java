package steps;

import api.clients.ApiHelperChat2Pay;
import io.cucumber.java.en.Given;

public class LoginSteps {

    @Given("^User is logged in to unity$")
    public void logInToUnity() {
        ApiHelperChat2Pay.logInToUnity();
    }
}
