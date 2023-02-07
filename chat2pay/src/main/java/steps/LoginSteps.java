package steps;

import api.clients.ApiHelperChat2Pay;
import io.cucumber.java.en.Given;

import static datamanager.UnityClients.DEV_CHAT_2_PAY_USER;
import static datamanager.UnityClients.QA_C2P_USER;

public class LoginSteps extends GeneralSteps {

    @Given("^User is logged in to unity$")
    public void logInToUnity() {
        ApiHelperChat2Pay.logInToUnity(DEV_CHAT_2_PAY_USER);
    }

    @Given("^QA User is logged in to unity$")
    public void qaLogInToUnity() {
        ApiHelperChat2Pay.logInToUnity(QA_C2P_USER);
    }
}
