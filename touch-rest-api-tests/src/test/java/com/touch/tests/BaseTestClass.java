package com.touch.tests;

import com.touch.actions.*;
import com.touch.models.EndPointsClass;
import com.touch.utils.reporter.CustomReport;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import com.touch.engines.RequestEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Listeners(value = CustomReport.class)
public class BaseTestClass {
    RequestEngine requestEngine = new RequestEngine();
    com.clickatell.engines.RequestEngine requestEngineMC2 = new com.clickatell.engines.RequestEngine();
    UserMC2Actions userActions = new UserMC2Actions(requestEngineMC2);
    UserProfileActions userProfileActions = new UserProfileActions(requestEngine);
    TenantActions tenantActions = new TenantActions(requestEngine);
    AgentActions agentActions = new AgentActions(requestEngine);
    DepartmentActions departmentActions = new DepartmentActions(requestEngine);
    ChatsActions chatsActions = new ChatsActions(requestEngine);


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
