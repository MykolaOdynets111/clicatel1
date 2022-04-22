package com.touch.tests;

import com.touch.models.touch.integration.IntegrationResponseV6;
import com.touch.models.touch.integration.ListIntegrationResponseV6;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class BusinessBlpTests extends BaseTestClass {

    @Test(dataProvider = "getIntegrations")
    public void getIntegrationList(String artifactName, String integrationName, String type, String listStatus, int statusCode) {

        Response response = integrationActions.getIntegrationsList(artifactName, integrationName, type, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<IntegrationResponseV6> integrations = response.as(ListIntegrationResponseV6.class).getIntegrations();
            if (listStatus.equals("NotEmpty"))
                Assert.assertTrue(!integrations.isEmpty());
            else
                Assert.assertTrue(integrations.isEmpty());
        }
    }

    @Test(dataProvider = "addIntegration")
    public void addNewBusinessBlp(String fileName, String integrationType, int statusCode) {
        File file = null;
        if (fileName != null && fileName.equals("correct")) {
            fileName = "echo1.war";
            file = new File(getFullPathToFile("TenantResources/" + fileName));
        }
        if (fileName != null && fileName.equals("wrong_ext")) {
            fileName = "echo1.war1";
            file = new File(getFullPathToFile("TenantResources/" + fileName));
        }
        Response response = integrationActions.addNewIntegrationItem(integrationType, file, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            IntegrationResponseV6 integrationItem = response.as(IntegrationResponseV6.class);
            Assert.assertEquals(integrationItem.getArtifactName(), fileName);
            Assert.assertEquals(integrationItem.getIntegrationName(), fileName.split("\\.")[0]);
            Assert.assertEquals(integrationItem.getType().toString(), integrationType);
            Assert.assertEquals(integrationActions.deleteIntegrationItem(integrationItem.getIntegrationName(), testToken).getStatusCode(), 204);
        }

    }

    @Test(dataProvider = "deleteItems")
    public void deleteBusinessBlpItem(String name, int statusCode) {
        if (name != null && name.equals("correct")) {
            name = "echo1";
            integrationActions.addNewIntegrationItem("TALEND", new File(getFullPathToFile("TenantResources/echo1.war")), testToken);
        }
        Response response = integrationActions.deleteIntegrationItem(name, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }



    @Test(dataProvider = "callData")
    public void callActionsInIntegration(String name, String action, String params, int statusCode) {
        Assert.assertEquals(integrationActions.callGivenAction(name, action, params).getStatusCode(), statusCode);
    }

    @DataProvider
    private static Object[][] getIntegrations() {
        return new Object[][]{
                {"", "", "", "NotEmpty", 200},
                {"echo.war", "echo", "TALEND", "NotEmpty", 200},
                {"echo.war", "", "TALEND", "NotEmpty", 200},
                {"echo.war", "echo", "", "NotEmpty", 200},
                {"echo.war", "echo", "test", "NotEmpty", 400},
                {"echo.war", "test", "TALEND", "", 200},
                {"echo.war", "22/11/2016", "TALEND", "", 200},
                {null, null, null, "NotEmpty", 200},
                {"echo.war", null, "TALEND", "NotEmpty", 200},
                {"echo.war", "echo", null, "NotEmpty", 200}
        };
    }

    @DataProvider
    private static Object[][] addIntegration() {
        return new Object[][]{
                {"correct", "TALEND", 200},
                {"correct", "TALEND", 200},
                {"correct", "SOAP", 200},
                {"correct", "REST", 200},
                {"correct", "test", 400},
                {"correct", "", 500},
                {"wrong_ext", "TALEND", 400},
                {"wrong_ext", "SOAP", 400},
                {"wrong_ext", "REST", 400},
                {null, "TALEND", 400},
                {null, null, 400},
        };
    }

    @DataProvider
    private static Object[][] deleteItems() {
        return new Object[][]{
                {"correct", 204},
                {"test", 404},
                {"", 405}
        };
    }


    @DataProvider
    private static Object[][] callData() {
        return new Object[][]{
                {"echo","echo","{}", 200},
                {"echo","echo","", 200},
                {"echo","test","", 500},
                {"test","echo","", 500},
                {"echo","","", 404},
                {"","","", 405},
        };
    }
    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }


}
