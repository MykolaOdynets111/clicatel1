package com.clickatell.tests;

import com.clickatell.actions.*;
import com.clickatell.actions.mc2.UserActions;
import com.clickatell.engines.RequestEngine;
import com.clickatell.engines.RequestEngineMC2;
import com.clickatell.models.EndPointsClass;
import com.clickatell.utils.reporter.CustomReport;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Listeners(value = CustomReport.class)
public class BaseTestClass {
    RequestEngine requestEngine = new RequestEngine();
    RequestEngineMC2 requestEngineMC2 = new RequestEngineMC2();
    UserActions userActions = new UserActions(requestEngineMC2);
    TenantActions tenantActions = new TenantActions(requestEngine);


    @BeforeSuite(alwaysRun = true)

    public void beforeSuite() {

    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        Response response = requestEngine.getRequest(EndPointsClass.APP_CONFIG_PROFILE);
        List<String> lines = new ArrayList<String>();
        lines.add(response.getBody().jsonPath().getString("version"));
        // lines.add("   " + response.getBody().jsonPath().getString("profile"));
        Path file = Paths.get(".VERSION");
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
