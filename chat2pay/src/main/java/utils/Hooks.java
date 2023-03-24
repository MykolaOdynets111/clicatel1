package utils;

import api.ApiHelperWidgets;
import io.cucumber.java.After;
import steps.GeneralSteps;

public class Hooks extends GeneralSteps {

    @After
    public void afterScenario() {
        logger.info("Chat 2 Pay: clean up the data");
        if (ApiHelperWidgets.getWidget(createdWidgetId.get()).statusCode() == 200) {
            ApiHelperWidgets.deleteWidget(createdWidgetId.get());
        }
        clearTestData();
    }
}