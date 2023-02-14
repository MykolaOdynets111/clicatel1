package utils;

import api.clients.ApiHelperWidgets;
import io.cucumber.java.After;
import steps.GeneralSteps;

public class Hooks extends GeneralSteps {

    @After
    public void afterScenario() {
        if (ApiHelperWidgets.getWidget(createdWidgetId.get()).statusCode() == 200){
            ApiHelperWidgets.deleteWidget(createdWidgetId.get());
        }
        clearTestData();
    }
}