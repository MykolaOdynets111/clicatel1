package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.Message;
import com.touch.models.touch.tenant.*;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class TenantTests extends BaseTestClass {





    @Test
    public void addExistingTenant() {
        TenantRequest testTenantRequest = new TenantRequest();
        testTenantRequest.setAccountId(null);
        testTenantRequest.setTenantOrgName("AutoVerificationTenant");
        testTenantRequest.setContactEmail("automationTenant@sink.sendgrid.net");
        testTenantRequest.setDescription("automation");
        testTenantRequest.setShortDescription("auto verification");
        testTenantRequest.setTenantName("AutoVerificationTenant");
        Mc2AccountRequest mc2Account = new Mc2AccountRequest();
        mc2Account.setFirstName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.firstName"));
        mc2Account.setLastName(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.lastName"));
        mc2Account.setEmail(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"));
        mc2Account.setPassword(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
//        testTenantRequest.setMc2AccountRequest(mc2Account);
        String regExpErrorMessage = "Tenant with .* already exists";
        // Verify that when we add one more Tenant with same data we get errorMessage;
        Assert.assertTrue(tenantActions.createNewTenantInTouchSide(testTenantRequest, testToken, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
    }

    @Test
    public void updateTenantWithCorrectData() {

        // prepare new dada for update tenant
        TenantUpdateDtoV5 tenantUpdateDto = new TenantUpdateDtoV5();
        tenantUpdateDto.setContactEmail("automationTenant@sink.sendgrid.net");
        tenantUpdateDto.setTenantOrgName("AutoVerificationTenant");
        tenantActions.updateTenant(testTenant.getId(), tenantUpdateDto, testToken, TenantUpdateDtoV5.class);
        Assert.assertTrue(tenantUpdateDto.getCategory().equals(tenantUpdateDto.getCategory()));
        Assert.assertTrue(tenantUpdateDto.getContactEmail().equals(tenantUpdateDto.getContactEmail()));
        Assert.assertTrue(tenantUpdateDto.getDescription().equals(tenantUpdateDto.getDescription()));
        Assert.assertTrue(tenantUpdateDto.getShortDescription().equals(tenantUpdateDto.getShortDescription()));
    }

    @Test
    public void updateNotExistingTenantWithCorrectData() {
        TenantUpdateDtoV5 tenantUpdateDto = new TenantUpdateDtoV5();
        Assert.assertTrue(tenantActions.updateTenant("not_existing_tenant", tenantUpdateDto, testToken, ErrorMessage.class).getErrorMessage().matches("Not authorized"));

    }


    @Test
    public void deleteNotExistingTenant() {
        Assert.assertEquals(tenantActions.deleteTenant("not_existing_tenant", testToken), 401);
    }

    @Test
    public void addPropertyToTenant() {
        String testColourName = "test21";
        String testColourValue = "test22";
        tenantActions.addProperty(testTenant.getId(), testColourName, testColourValue, testToken, TenantProperties.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getPropertiesForTenant(testTenant.getId(), testToken).contains(new TenantProperties(testColourName, testColourValue)));
//        delete colour
        Assert.assertEquals(tenantActions.deleteColour(testTenant.getId(), testColourName, testToken), 200);
    }

    @Test
    public void deletePropertyToTenant() {
        String testColourName = "test211";
        String testColourValue = "test221";
        //add colour
        tenantActions.addProperty(testTenant.getId(), testColourName, testColourValue, testToken, TenantProperties.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getPropertiesForTenant(testTenant.getId(), testToken).contains(new TenantProperties(testColourName, testColourValue)));
        //delete colour
        Assert.assertEquals(tenantActions.deleteColour(testTenant.getId(), testColourName, testToken), 200);
        //verify that deleted colour is not still available for tenant
        Assert.assertFalse(tenantActions.getPropertiesForTenant(testTenant.getId(), testToken).contains(new TenantProperties(testColourName, testColourValue)));
        //delete colour in not existing tenant
        Assert.assertEquals(tenantActions.deleteColour("notReal", testColourName, testToken), 401);
    }

    @Test(dataProvider = "resourcesList")
    public void addNewResourceToTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(testTenant.getId(), resourceName, new File(file), testToken, Integer.class), 201);
        //get new resource and convert it to InputStream
        InputStream actualImage = tenantActions.getResourceAsInputStreamForTenant(testTenant.getId(), resourceName, testToken);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));
    }

    @Test(dataProvider = "resourcesList")
    public void addNewResourceToNotExistTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        //add new resource to not exist tenant
        Assert.assertEquals(tenantActions.addResource("000000005700325001571e3e40b80281", resourceName, new File(file), testToken, Integer.class), 401);
//        Assert.assertTrue(tenantActions.addResource(newTenant.getChatroomJid(), resourceName, new File(file), ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));

    }

    @Test(dataProvider = "resourcesList")
    public void deleteResourceFromTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(testTenant.getId(), resourceName, new File(file), testToken, Integer.class), 201);
        //delete new resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(testTenant.getId(), resourceName, testToken), 200);
    }

    @Test
    public void deleteNotExistingResourceFromTenant() throws IOException {
        //delete not existing resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(testTenant.getId(), "not_existing", testToken), 200);
    }

    @Test
    public void deleteNotExistingResourceFromNotExistingTenant() throws IOException {
        //delete not existing resource from not existing tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource("000000005700325001571e3e40b80281", "not_existing", testToken), 401);
    }

    @Test(dataProvider = "changeAddress")
    public void addNewAddressesToTenant(String tenantId, String addressId, Boolean positiveTest) {
        // add new address to tenant
        /*

        TO DO
        we need update this test cases when response body will be added to that type requests
        */
        TenantAddressForUpdate tenantAddressForUpdate = new TenantAddressForUpdate();
        tenantAddressForUpdate.setLat(10L);
        tenantAddressForUpdate.setLng(10L);
        if (tenantId.isEmpty())
            tenantId = testTenant.getId();
        if (addressId.isEmpty())
            addressId = testTenant.getTenantAddresses().get(0).getId();
        int statusCode = tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId, addressId, tenantAddressForUpdate, testToken);
        Assert.assertTrue((statusCode == 200) == positiveTest);
    }

    @Test
    public void addNewCommonFlow() throws IOException {
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
// Verify that new common flow was added successful
        Assert.assertEquals(tenantActions.addCommonFlow(new File(file), testToken), 201);
        boolean isCommonFlowAdded = false;
        for (FlowResponse flow : tenantActions.getAllCommonFlows(testToken).getFlows()) {
            if (flow.getFileName().equals(fileName)) {
                isCommonFlowAdded = true;
                break;
            }
        }

        Assert.assertTrue(isCommonFlowAdded);
//        //get new common flow and convert it to InputStream
        InputStream actualImage = tenantActions.getCommonFlowAsInputStream(fileName, testToken);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));
//
//        //delete common flow
        Assert.assertEquals(tenantActions.deleteCommonFlow(fileName, testToken), 200);
    }

    @Test
    public void deleteCommonFlow() throws IOException {
// try to delete not existing common flow
// TODO        we need to add verification of error message when response for deleting will for each cases
        Assert.assertEquals(tenantActions.deleteCommonFlow("notexisting", testToken), 404);
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
//        add common flow
        tenantActions.addCommonFlow(new File(file), testToken);
//        //delete common flow
        Assert.assertEquals(tenantActions.deleteCommonFlow(fileName, testToken), 200);
    }

    @Test
    public void getNotExistingCommonFlow() throws IOException {
        String regExpErrorMessage = "Flow with name .* not found.";
        String actualErrorMessage = tenantActions.getCommonFlow("notexisting", testToken, ErrorMessage.class).getErrorMessage();
        Assert.assertTrue(actualErrorMessage.matches(regExpErrorMessage));
    }

    @Test
    public void addNewTenantFlow() throws IOException {

        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
//        try to create tenant flow with not existing tenant
        Assert.assertEquals(tenantActions.addTenantFlow("notExisting", new File(file), testToken), 401);
//        create tenant for test further test case
// Verify that new tenant flow was added successful
        String tenantId = testTenant.getId();
        Assert.assertEquals(tenantActions.addTenantFlow(tenantId, new File(file), testToken), 201);
        boolean isCommonFlowAdded = false;
        for (FlowResponse flow : tenantActions.getAllTenantFlows(tenantId, testToken).getFlows()) {
            if (flow.getFileName().equals(fileName)) {
                isCommonFlowAdded = true;
                break;
            }
        }

        Assert.assertTrue(isCommonFlowAdded);
//        //get new common flow and convert it to InputStream
        InputStream actualImage = tenantActions.getTenantFlowAsInputStream(tenantId, fileName, testToken);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));
//
//        //delete flow
        Assert.assertEquals(tenantActions.deleteTenantFlow(tenantId, fileName, testToken), 200);
    }

    @Test
    public void deleteTenantFlow() throws IOException {
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
        String tenantId = testTenant.getId();
//      add tenant flow
        tenantActions.addTenantFlow(tenantId, new File(file), testToken);
// try to delete not existing tenant flow with not existing tenant
// TODO        we need to add verification of error message when response for deleting will for each cases
        Assert.assertEquals(tenantActions.deleteTenantFlow("notexisting", "notexisting", testToken), 401);
//   try to delete not existing tenant flow in existing tenant
        Assert.assertEquals(tenantActions.deleteTenantFlow(tenantId, "notexisting", testToken), 404);
//        try to delete existing tenant flow in not existing tenant
        Assert.assertEquals(tenantActions.deleteTenantFlow("notexisting", fileName, testToken), 401);
        //     delete tenant flow
        Assert.assertEquals(tenantActions.deleteTenantFlow(tenantId, fileName, testToken), 200);
//  try to delete tenant flow from tenant without any flows
        Assert.assertEquals(tenantActions.deleteTenantFlow(testTenant.getId(), fileName, testToken), 404);
    }


    @Test
    public void getNotExistingTenantFlow() throws IOException {
        String teantId = testTenant.getId();
        String notExistingFlow = "notexisting";
        String regExpErrorMessage = "Flow with name " + notExistingFlow + ", tenant id " + teantId + " not found.";
        Assert.assertTrue(tenantActions.getTenantFlow(teantId, notExistingFlow, testToken, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
    }

    @Test
    public void getNearestTenants() throws IOException {
        String testToken2 = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        TenantResponseV5 tenant1 = testTenant;
        TenantResponseV5 tenant2 = tenantActions.getTenant(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id"),testToken2,TenantResponseV5.class);
        String tenantId1 = tenant1.getId();
        String tenantId2 = tenant2.getId();
        TenantAddressForUpdate tenantAddressForUpdate1 = new TenantAddressForUpdate();
        tenantAddressForUpdate1.setLat(60L);
        tenantAddressForUpdate1.setLng(60L);
        TenantAddressForUpdate tenantAddressForUpdate2 = new TenantAddressForUpdate();
        tenantAddressForUpdate2.setLat(70L);
        tenantAddressForUpdate2.setLng(70L);
        String addressId1 = tenant1.getTenantAddresses().get(0).getId();
        String addressId2 = tenant2.getTenantAddresses().get(0).getId();
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId1, addressId1, tenantAddressForUpdate1, testToken);
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId2, addressId2, tenantAddressForUpdate2, testToken2);
        TenantResponseV5 ten1 = tenantActions.getTenant(tenantId1, testToken, TenantResponseV5.class);
        TenantResponseV5 ten2 = tenantActions.getTenant(tenantId2, testToken2, TenantResponseV5.class);
        List<TenantResponseV5> nearestTenantsList = tenantActions.getNearestTenantsList(tenantAddressForUpdate1.getLat().toString(), tenantAddressForUpdate1.getLng().toString(), "1500000", accessTestToken);
        List<TenantResponseV5> newTenantsList = Arrays.asList(ten1, ten2);
        Assert.assertTrue(nearestTenantsList.containsAll(newTenantsList), "New tenants are not in nearest tenants list");
    }

    @Test
    public void addBussinesHoursForAddress() {
        String addressId = testTenant.getTenantAddresses().get(0).getId();
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "10:00", "19:00");
//        add new bussines hours
        Response addBussinesHoursResponce = tenantActions.addBusinessHoursForAddress(testTenant.getId(), addressId, businessHourRequest, testToken);
//        Verify status code and that new Bussines hours have been added to tenant address
        Assert.assertEquals(addBussinesHoursResponce.getStatusCode(), 200);
        Assert.assertTrue(tenantActions.getBusinessHoursFromAddress(testTenant.getId(), addressId, testToken, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().contains(addBussinesHoursResponce.as(AddressBusinessHourResponse.class)));

    }

    @Test
    public void getBussinesHoursForAddressByNotExistingTenant() {
        String addressId = testTenant.getTenantAddresses().get(0).getId();
        Assert.assertTrue(tenantActions.getBusinessHoursFromAddress("not_existing", addressId, testToken, ErrorMessage.class).getErrorMessage().matches("Not authorized"));

    }

    @Test
    public void getBussinesHoursForAddressByNotExistingAddress() {
        Assert.assertTrue(tenantActions.getBusinessHoursFromAddress(testTenant.getId(), "not_existing", testToken, ErrorMessage.class).getErrorMessage().matches("Address with id .* not found"));

    }

    @Test
    public void updateBussinesHoursForAddress() {
        String addressId = testTenant.getTenantAddresses().get(0).getId();
        String hoursId = tenantActions.getBusinessHoursFromAddress(testTenant.getId(), addressId, testToken, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().get(0).getId();
        AddressBusinessHourRequest businessHourRequest = new AddressBusinessHourRequest();
        businessHourRequest.setStartWorkTime("11:00");
        Response response = tenantActions.updateBusinessHoursForAddress(testTenant.getId(), addressId, hoursId, businessHourRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(businessHourRequest.getStartWorkTime(), response.as(AddressBusinessHourResponse.class).getStartWorkTime());
    }

    @Test
    public void deleteBussinesHoursForAddress() {
        String addressId = testTenant.getTenantAddresses().get(0).getId();
        String hoursId = tenantActions.getBusinessHoursFromAddress(testTenant.getId(), addressId, testToken, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().get(0).getId();
        Assert.assertEquals(tenantActions.deleteBusinessHoursForAddress(testTenant.getId(), addressId, hoursId, testToken), 200);

    }

    @Test(dataProvider = "businessHoursFalseCombination")
    public void updateBussinesHoursForAddressWithWrongData(String tenantId, String addressId, String hoursId, int status, String message) {
        if (tenantId.equals("correct")) {
            tenantId = testTenant.getId();
        }
        if (addressId.equals("correct")) {
            addressId = testTenant.getTenantAddresses().get(0).getId();
        }
        if (hoursId.equals("correct")) {
            hoursId = tenantActions.getBusinessHoursFromAddress(testTenant.getId(), testTenant.getTenantAddresses().get(0).getId(), testToken, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().get(0).getId();
        }
        AddressBusinessHourRequest businessHourRequest = new AddressBusinessHourRequest();
        Response response = tenantActions.updateBusinessHoursForAddress(tenantId, addressId, hoursId, businessHourRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), status);

    }

    @Test
    public void addBussinesHoursForTenant() {
        deleteAllBussinesHoursForTenenat(testTenant.getId(), testToken);
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "10:00", "19:00");
        Response response = tenantActions.addBusinessHoursForTenant(testTenant.getId(), businessHourRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.as(BusinessHourDtoIdV5.class), businessHourRequest);
        Response getBussinesHoursResponse = tenantActions.getBusinessHoursFromTenant(testTenant.getId(), testToken);
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(getBussinesHoursResponse.as(ListTenantBusinessHoursResponse.class).getBusinessHours().contains(businessHourRequest));
        deleteAllBussinesHoursForTenenat(testTenant.getId(), testToken);
    }
    private void deleteAllBussinesHoursForTenenat(String tenant, String token){
        List<BusinessHourDtoIdV5> businessHours = tenantActions.getBusinessHoursFromTenant(testTenant.getId(), testToken).as(ListTenantBusinessHoursResponse.class).getBusinessHours();
        for(BusinessHourDtoIdV5 hours: businessHours){
            tenantActions.deleteBussinesHoursForTenant(tenant,hours.getId(),token);
        }
    }

    @Test
    public void updateBusinessHoursForTenant() {
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "11:00", "19:00");
        String hoursId = tenantActions.addBusinessHoursForTenant(testTenant.getId(), businessHourRequest, testToken).as(BusinessHourDtoIdV5.class).getId();
        businessHourRequest.setStartWorkTime("13:00");
        Response response = tenantActions.updateBusinessHoursForTenant(testTenant.getId(), hoursId, businessHourRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.as(BusinessHourDtoIdV5.class), businessHourRequest);
        deleteAllBussinesHoursForTenenat(testTenant.getId(), testToken);

    }

    //
    @Test
    public void updateBusinessHoursForTenantWithNotExistTenant() {
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "11:00", "19:00");
        Response response = tenantActions.updateBusinessHoursForTenant("not_exist", tenantActions.getBusinessHoursFromAddress(testTenant.getId(), testTenant.getTenantAddresses().get(0).getId(), testToken, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().get(0).getId(), businessHourRequest, token);
        Assert.assertEquals(response.getStatusCode(), 401);

    }

    @Test
    public void updateBusinessHoursForTenantWithNotExistBusinessHoursId() {
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "11:00", "19:00");
        Response response = tenantActions.updateBusinessHoursForTenant(testTenant.getId(), "not_exist", businessHourRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), 404);

    }

    @Test
    public void addFaqs() {
        TenantFaqRequest faq = new TenantFaqRequest("test question", "test answer");
        // add new faq
        Response response = tenantActions.addFAQs(testTenant.getId(), faq, testToken);
//        verify that we get correct status code and new faq was added to tenant
        Assert.assertEquals(response.getStatusCode(), 201);
        TenantFaqResponse faqResponse = response.as(TenantFaqResponse.class);
        Assert.assertEquals(faq, faqResponse);
        Assert.assertTrue(tenantActions.getFAQs(testTenant.getId(), testToken, ListTenantFaqsResponse.class).getFaqs().contains(response.as(TenantFaqResponse.class)));
        //        delete faqs
        Assert.assertEquals(tenantActions.deleteFAQs(testTenant.getId(), faqResponse.getId(), testToken).getStatusCode(), 200);

    }

    @Test
    public void addAndDeleteAndUpdateFaqsWithWrongData() {
        TenantFaqRequest faq = new TenantFaqRequest("test question", "test answer");
//        add new faq with not existing tenantId
        Response response = tenantActions.addFAQs("not_existing", faq, testToken);
        Assert.assertEquals(response.getStatusCode(), 401);
//
        TenantFaqResponse faqResponse = tenantActions.addFAQs(testTenant.getId(), faq, testToken).as(TenantFaqResponse.class);
        String faqId = faqResponse.getId();
//        delete faqs with not existing tenantId
        response = tenantActions.deleteFAQs("not_existing", faqId, testToken);
        Assert.assertEquals(response.getStatusCode(), 401);
//        update faqs with not existing tenantId
        response = tenantActions.updateFAQ("not_existing", faqId, faq, testToken);
        Assert.assertEquals(response.getStatusCode(), 401);
//        update faqs with not existing faqsId
        response = tenantActions.updateFAQ(testTenant.getId(), "not_existing", faq, testToken);
        Assert.assertEquals(response.getStatusCode(), 500);
    }
    @Test
    public void deleteAllFaqsForTestTenant(){
        List<TenantFaqResponse> faqs = tenantActions.getFAQs(testTenant.getId(), testToken, ListTenantFaqsResponse.class).getFaqs();
        for(TenantFaqResponse faq : faqs) {
            Response response = tenantActions.deleteFAQs(testTenant.getId(), faq.getId(), testToken);
            Assert.assertTrue(response.statusCode()==200);
        }
    }

    @Test
    public void updateFaqs() {
        TenantFaqRequest faq = new TenantFaqRequest("test question", "test answer");
        TenantFaqResponse faqResponse = tenantActions.addFAQs(testTenant.getId(), faq, testToken).as(TenantFaqResponse.class);
        String faqId = faqResponse.getId();
        faq = new TenantFaqRequest("test question1", "test answer1");
//        update faq
        Response response = tenantActions.updateFAQ(testTenant.getId(), faqId, faq, testToken);
        Assert.assertEquals(response.getStatusCode(), 202);
        Assert.assertEquals(faq, response.as(TenantFaqResponse.class));
        Assert.assertTrue(tenantActions.getFAQs(testTenant.getId(), testToken, ListTenantFaqsResponse.class).getFaqs().contains(response.as(TenantFaqResponse.class)));
//        delete faqs
        Assert.assertEquals(tenantActions.deleteFAQs(testTenant.getId(), faqId, testToken).getStatusCode(), 200);

    }

    @Test
    public void deleteFaqs() {
        TenantFaqRequest faq = new TenantFaqRequest("test question", "test answer");
        TenantFaqResponse faqResponse = tenantActions.addFAQs(testTenant.getId(), faq, testToken).as(TenantFaqResponse.class);
        String faqId = faqResponse.getId();
//        delete faqs
        Response response = tenantActions.deleteFAQs(testTenant.getId(), faqId, testToken);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void addTags() {
        TenantTagRequest tenantTagRequest = new TenantTagRequest("test");
//        add tag
        Response response = tenantActions.addTag(testTenant.getId(), tenantTagRequest, testToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.as(Message.class).getMessage(), "Tag added.");
//        get available tag for test tenant
        Response getTagsResponse = tenantActions.getTAGs(testTenant.getId(), testToken);
        Assert.assertEquals(getTagsResponse.getStatusCode(), 200);
        Assert.assertTrue(getTagsResponse.as(ListTenantTagsResponse.class).getTenantTags().contains(tenantTagRequest.getTag()));
//        delete tag
        Response deleteResponse = tenantActions.deleteTAGs(testTenant.getId(), tenantTagRequest.getTag(), testToken);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
    }

    @Test
    public void deleteTags() {
        TenantTagRequest tenantTagRequest = new TenantTagRequest("test");
//        add tag
        tenantActions.addTag(testTenant.getId(), tenantTagRequest, testToken);
//        delete tag
        Response deleteResponse = tenantActions.deleteTAGs(testTenant.getId(), tenantTagRequest.getTag(), testToken);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
        Assert.assertEquals(deleteResponse.as(Message.class).getMessage(), "Tag removed.");
        Response getTagsResponse = tenantActions.getTAGs(testTenant.getId(), testToken);
        Assert.assertFalse(getTagsResponse.as(ListTenantTagsResponse.class).getTenantTags().contains(tenantTagRequest.getTag()));

    }

    @Test
    public void deleteTagsWithWrongData() {
        TenantTagRequest tenantTagRequest = new TenantTagRequest("test");
        //        add tag
        tenantActions.addTag(testTenant.getId(), tenantTagRequest, testToken);
        //        delete tag for not existing tenant
        Response response = tenantActions.deleteTAGs("not_existing", tenantTagRequest.getTag(), testToken);
        Assert.assertEquals(response.getStatusCode(), 401);
        Assert.assertTrue(response.as(ErrorMessage.class).getErrorMessage().matches("Not authorized"));
        //        delete not existing tag for test tenant
        response = tenantActions.deleteTAGs(testTenant.getId(), "not_existing", testToken);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.as(ErrorMessage.class).getErrorMessage().matches("Tag .+ not found"));
//        delete tag
        Response deleteResponse = tenantActions.deleteTAGs(testTenant.getId(), tenantTagRequest.getTag(), testToken);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
    }

    @Test(dataProvider = "getTBot")
    public void getTBotForTenant(String tenantId, int statusCode) {
        if(tenantId.equals("correct"))
            tenantId=TestingEnvProperties.getPropertyByName("touch.tenant.genbank.id");
        Response response = tenantActions.getTenantTBot(tenantId, testToken);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if(statusCode==200){
            TenantTbotResponseV5 tbot = response.as(TenantTbotResponseV5.class);
        }

    }
    @Test(dataProvider = "getConfig")
    public void getConfigForTenant(String tenantId, int statusCode) {
        if(tenantId.equals("correct"))
            tenantId=TestingEnvProperties.getPropertyByName("touch.tenant.genbank.id");
        Response response = tenantActions.getTenantConfig(tenantId, getToken(TestingEnvProperties.getPropertyByName("touch.user.admin.login"), TestingEnvProperties.getPropertyByName("touch.user.admin.password")));
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if(statusCode==200){
            TenantConfig tenantConfig = response.as(TenantConfig.class);
        }

    }  @Test(dataProvider = "deleteConfig")
    public void deleteConfigForTenant(String tenantId, int statusCode) {
        //use correct tenant if we need one
        //add new configuration
        if(tenantId.equals("correct")) {
            tenantId = testTenant.getId();
            TenantConfig tenantConfig = new TenantConfig();
            ArrayList<String> ccList = new ArrayList<>();
            ccList.add(StringUtils.generateRandomString(10) + "@sink.sendgrid.net");
            tenantConfig.setCc(ccList);
            tenantConfig.setTimezone("GMT+5:30");
            tenantConfig.setAgentInactivityTimeoutPreferredSec(100);
            tenantConfig.setAgentInactivityTimeoutWarnSec(100);
            tenantConfig.setAgentWaitClientTimeoutMin(100);
            tenantConfig.setUserWaitingTimeOfferMsg(100);
            tenantConfig.setAgentOfferTimeoutSec(100);
            tenantConfig.setUserWaitingTimeOfferCard(100);
            tenantConfig.setClientOfferTimeoutSec(100);
            tenantConfig.setTbotWaitClientTimeoutMin(100);
            tenantConfig.setShowSmCardTimeoutMin(100);
            tenantConfig.setPrimaryEmail(StringUtils.generateRandomString(10) + "@sink.sendgrid.net");
            tenantActions.updateConfig(tenantId, tenantConfig, accessTestToken);
        }
        Response response = tenantActions.deleteTenantConfig(tenantId, testToken);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if(statusCode==200){
            TenantConfig tenantConfig =  tenantActions.getTenantConfig(tenantId, testToken).as(TenantConfig.class);
            Assert.assertTrue(tenantConfig.getCc().isEmpty());
            Assert.assertNull(tenantConfig.getPrimaryEmail());
        }

    }
    @Test(dataProvider = "updateConfig")
    public void addConfigForTenant(String tenantId, int agentInTimeout, String primaryEmail, String cc, String mode, String welcomeMessage, int welcomeMessageTimer,int statusCode) {
        if(tenantId.equals("correct"))
            tenantId=testTenant.getId();
        if(primaryEmail.equals("correct"))
            primaryEmail= StringUtils.generateRandomString(10) + "@sink.sendgrid.net";
        if(cc.equals("correct"))
            cc= StringUtils.generateRandomString(10) + "@sink.sendgrid.net";
        TenantConfig tenantConfig = new TenantConfig();
        ArrayList<String> ccList = new ArrayList<>();
        ccList.add(cc);
        tenantConfig.setCc(ccList);
        tenantConfig.setPrimaryEmail(primaryEmail);
        tenantConfig.setMode(mode);
        tenantConfig.setTimezone("GMT+5:30");
        tenantConfig.setAgentInactivityTimeoutMaxSec(agentInTimeout);
        tenantConfig.setAgentInactivityTimeoutPreferredSec(100);
        tenantConfig.setAgentInactivityTimeoutWarnSec(100);
        tenantConfig.setAgentWaitClientTimeoutMin(100);
        tenantConfig.setUserWaitingTimeOfferMsg(100);
        tenantConfig.setAgentOfferTimeoutSec(100);
        tenantConfig.setUserWaitingTimeOfferCard(100);
        tenantConfig.setClientOfferTimeoutSec(100);
        tenantConfig.setTbotWaitClientTimeoutMin(100);
        tenantConfig.setShowSmCardTimeoutMin(100);
        tenantConfig.setWelcomeMessage(welcomeMessage);
        tenantConfig.setWelcomeMessageTimer(welcomeMessageTimer);
        Response response = tenantActions.updateConfig(tenantId, tenantConfig, testToken);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if(statusCode==200){
            TenantConfig tenantConfigResponse = response.as(TenantConfig.class);
            Assert.assertTrue(tenantConfigResponse.getCc().contains(cc));
            Assert.assertEquals(tenantConfigResponse.getPrimaryEmail(),primaryEmail);
            Assert.assertEquals(tenantConfigResponse.getWelcomeMessage(), welcomeMessage);
            Assert.assertTrue(tenantConfigResponse.getAgentInactivityTimeoutMaxSec()==agentInTimeout);
        }

    }

    @AfterClass
    public void afterClass() {
        token = getToken();
        removeAllTestTenants(token);
    }

    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }

    @DataProvider
    private static Object[][] getTBot() {
        return new Object[][]{
                {"correct", 200},
                {"test", 404},
                {"11", 404},
                {"", 401},
        };
    }
    @DataProvider
    private static Object[][] getConfig() {
        return new Object[][]{
                {"correct", 200},
                {"test", 401},
                {"11", 401},
                {"", 401},
        };
    }
    @DataProvider
    private static Object[][] deleteConfig() {
        return new Object[][]{
                {"correct", 204},
                {"test", 401},
                {"11", 401},
                {"", 401},
        };
    }
    @DataProvider
    private static Object[][] updateConfig() {
        return new Object[][]{
                {"correct",30,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5, 200},
                {"correct",30,"correct","correct", "AGENT", StringUtils.generateRandomString(5), 5,200},
                {"correct",9,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5,200},
                {"correct",10,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5,200},
                {"test",30,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5,401},
                {"11",30,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5,401},
                {"",30,"correct","correct", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"correct",30,"test","correct", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"correct",30,"correct","test", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"correct",30,"11","correct", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"correct",30,"correct","11", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"test",30,"test","test", "BOT", StringUtils.generateRandomString(5), 5,400},
                {"test",30,"test","test", null, StringUtils.generateRandomString(5), 5,400},
                {"test",30,"test","test", StringUtils.generateRandomString(5), StringUtils.generateRandomString(5), 5,400}
        };
    }
    @DataProvider
    private static Object[][] resourcesList() {
        return new Object[][]{
                {"TenantResources/tenant_logo.jpg", "new_logo"},
                {"TenantResources/bg_chat_image.jpg", "new_bg_chat_image"}
        };
    }


    @DataProvider
    private static Object[][] changeAddress() {
        return new Object[][]{
                {"", "", true},
                {"0000000057003250015703cc06cc1111", "", false},
                {"", "0000000057003250015703cc06cc1111", false},
                {"0000000057003250015703cc06cc1111", "0000000057003250015703cc06cc1111", false}
        };
    }

    @DataProvider
    private static Object[][] businessHoursFalseCombination() {
        return new Object[][]{
                {"", "", "", 405, "HTTP 405 Method Not Allowed"},
                {"correct", "", "", 405, "HTTP 405 Method Not Allowed"},
                {"correct", "correct", "", 405, "HTTP 405 Method Not Allowed"},
                {"correct", "", "correct", 404, "HTTP 404 Not Found"},
                {"correct", "test", "correct", 404, "Address with id .+ not found"},
                {"correct", "test", "test", 404, "Address with id .+ not found"},
                {"test", "test", "test", 401, "Not allowed"}
        };
    }



}
