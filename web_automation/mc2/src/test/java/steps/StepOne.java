package steps;

import io.qameta.allure.Step;

public class StepOne {

    @Step("Create int")
    public int createInt(){
        return 1;
    }
}
