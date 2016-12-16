package com.touch.tests;

import com.clickatell.models.users.response.getallusers.User;
import com.touch.models.ErrorMessage;
import com.touch.models.mc2.AccountInfoResponse;
import com.touch.models.touch.tenant.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class TenantTests extends BaseTestClass {
String token;
    TenantResponseV5 testTenant;
    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(),token, TenantResponseV5.class);
    }


    @Test
    public void createNewTenant() {
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBeforeAddNew = tenantActions.getTenantsList(token).size();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest,token, TenantResponseV5.class);
        List<TenantResponseV5> tenantsListAfterAddNewTenant = tenantActions.getTenantsList(token);
        int amountTenantsAfterAddNew = tenantsListAfterAddNewTenant.size();
        Assert.assertEquals(amountTenantsBeforeAddNew + 1, amountTenantsAfterAddNew);
        Assert.assertTrue(tenantsListAfterAddNewTenant.contains(newTenant), "New tenant was not added to DB or it contains wrong data");
        //Verify get tenant request
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(), token, TenantResponseV5.class), newTenant);
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
        tenantsListAfterAddNewTenant = tenantActions.getTenantsList(token);
        // Verify that tenant was deleted after test
        Assert.assertEquals(amountTenantsBeforeAddNew, tenantsListAfterAddNewTenant.size());
    }

    //it is not actual now, nevertheless, it will be useful in future releases
    //@Test
    public void createNewTenantFromMC2() {
        int size1 = tenantActions.getTenantsList(token).size();
        User userAndLogin = userActions.createUserAndLogin();
        int size2 = tenantActions.getTenantsList(token).size();

    }

    @Test
    public void createNewTenantWithoutAccount() {
        TenantRequest tenantRequest = new TenantRequest();
        String email = tenantRequest.getMc2AccountRequest().getEmail();
        String firstName = tenantRequest.getMc2AccountRequest().getFirstName();
        String lastName = tenantRequest.getMc2AccountRequest().getLastName();
        String password = tenantRequest.getMc2AccountRequest().getPassword();
        tenantRequest.setAccountId(null);
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest,token, TenantResponseV5.class);
        List<TenantResponseV5> tenantsListAfterAddNewTenant = tenantActions.getTenantsList(token);
        Assert.assertTrue(tenantsListAfterAddNewTenant.contains(newTenant), "New tenant was not added to DB or it contains wrong data");
        //get tenant from DB
        TenantResponseV5 tenant = tenantActions.getTenant(newTenant.getId(),token, TenantResponseV5.class);
        //add generated accountId to our request to verify other fields
        tenantRequest.setAccountId(newTenant.getAccountId());
        Assert.assertEquals(tenantRequest,tenant);
        Assert.assertNotNull(tenant.getAccountId());
        //Verify get tenant request
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(),token, TenantResponseV5.class), newTenant);
        //activate new user in mc2
        userActions.signUpAndLoginWitthNewUser(newTenant.getAccountId(), newTenant.getTenantOrgName(), email, firstName, lastName, password);
        // get admin token
        String mc2AdminToken = userActions.loginAsAdminUserAndReturnToken();
        // get account in mc2 and verify that information is correct 
        AccountInfoResponse accountInfo = userActions.getAccountInfo(newTenant.getAccountId(), mc2AdminToken);
        Assert.assertEquals(accountInfo,new AccountInfoResponse(newTenant.getTenantOrgName(),"ACTIVE" , newTenant.getAccountId(), null));

        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void addExistingTenant() {
        TenantRequest tenantRequest = new TenantRequest();
        String regExpErrorMessage = "Tenant with .* already exists";
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest,token, TenantResponseV5.class);
        // Verify that when we add one more Tenant with same data we get errorMessage;
        Assert.assertTrue(tenantActions.createNewTenantInTouchSide(tenantRequest,token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void updateTenantWithCorrectData() {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest,token, TenantResponseV5.class);
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(), token, TenantResponseV5.class),newTenant);
        // prepare new dada for update tenant
        TenantUpdateDtoV5 tenantUpdateDto = new TenantUpdateDtoV5();
        TenantUpdateDtoV5 updatedTenant = tenantActions.updateTenant(newTenant.getId(), tenantUpdateDto, token, TenantUpdateDtoV5.class);
        Assert.assertTrue(tenantUpdateDto.getCategory().equals(tenantUpdateDto.getCategory()));
        Assert.assertTrue(tenantUpdateDto.getContactEmail().equals(tenantUpdateDto.getContactEmail()));
        Assert.assertTrue(tenantUpdateDto.getDescription().equals(tenantUpdateDto.getDescription()));
        Assert.assertTrue(tenantUpdateDto.getShortDescription().equals(tenantUpdateDto.getShortDescription()));
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);

    }

    @Test
    public void updateNotExistingTenantWithCorrectData() {
        String regExpErrorMessage = "Tenant with id .* not found";
        TenantUpdateDtoV5 tenantUpdateDto = new TenantUpdateDtoV5();
        Assert.assertTrue(tenantActions.updateTenant("not_existing_tenant",tenantUpdateDto, token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));

    }

    @Test
    public void deleteTenant() {
        String regExpErrorMessage = "Tenant with id .* not found";
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBefore = tenantActions.getTenantsList(token).size();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(), token, TenantResponseV5.class),newTenant);
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
        Assert.assertEquals(amountTenantsBefore, tenantActions.getTenantsList(token).size());
        Assert.assertTrue(tenantActions.getTenant(newTenant.getId(), token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
    }

    @Test
    public void deleteNotExistingTenant() {
        Assert.assertEquals(tenantActions.deleteTenant("not_existing_tenant", token), 404);
    }

    @Test
    public void addColourToTenant() {
        String testColourName = "test21";
        String testColourValue = "test22";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        tenantActions.addColour(newTenant.getId(), testColourName, testColourValue, token, TenantColour.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getColoursForTenant(newTenant.getId(), token).contains(new TenantColour(testColourName, testColourValue)));
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void addColourWithTooBigLengthToTenant() {
        String regExpErrorMessage = "could not execute statement; SQL \\[n/a\\]; nested exception is .*";
        String testColourName = "test21";
        String testColourValue = "test888888888";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        // verify that we get error message when we try to add to long colour
        Assert.assertTrue(tenantActions.addColour(newTenant.getId(), testColourName, testColourValue, token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void deleteColourToTenant() {
        String testColourName = "test21";
        String testColourValue = "test22";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        //add colour
        tenantActions.addColour(newTenant.getId(), testColourName, testColourValue, token, TenantColour.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getColoursForTenant(newTenant.getId(), token).contains(new TenantColour(testColourName, testColourValue)));
        //delete colour
        Assert.assertEquals(tenantActions.deleteColour(newTenant.getId(), testColourName, token), 200);
        //verify that deleted colour is not still available for tenant
        Assert.assertFalse(tenantActions.getColoursForTenant(newTenant.getId(), token).contains(new TenantColour(testColourName, testColourValue)));
        //delete colour in not existing tenant
        Assert.assertEquals(tenantActions.deleteColour("notReal", testColourName, token), 404);
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test(dataProvider = "resourcesList")
    public void addNewResourceToTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(newTenant.getId(), resourceName, new File(file), token, Integer.class), 201);
        //get new resource and convert it to InputStream
        InputStream actualImage = tenantActions.getResourceAsInputStreamForTenant(newTenant.getId(), resourceName, token);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test(dataProvider = "resourcesList")
    public void addNewResourceToNotExistTenant(String filePath, String resourceName) throws IOException {
        String regExpErrorMessage = "Tenant with id .* not found";
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        //add new resource to not exist tenant
        Assert.assertEquals(tenantActions.addResource("000000005700325001571e3e40b80281", resourceName, new File(file), token, Integer.class), 404);
//        Assert.assertTrue(tenantActions.addResource(newTenant.getChatroomJid(), resourceName, new File(file), ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test(dataProvider = "resourcesList")
    public void deleteResourceFromTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(newTenant.getId(), resourceName, new File(file), token, Integer.class), 201);
        //delete new resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(newTenant.getId(), resourceName, token), 200);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void deleteNotExistingResourceFromTenant() throws IOException {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);

        //delete not existing resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(newTenant.getId(), "not_existing", token), 200);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void deleteNotExistingResourceFromNotExistingTenant() throws IOException {
        //delete not existing resource from not existing tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource("000000005700325001571e3e40b80281", "not_existing", token), 404);
    }

    @Test(dataProvider = "changeAddress")
    public void addNewAddressesToTenant(String tenantId, String addressId, Boolean positiveTest) {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        // add new address to tenant
        /*

        TO DO
        we need update this test cases when response body will be added to that type requests
        */
        GpsRequest gpsRequest = new GpsRequest(10f, 10f);
        if (tenantId.isEmpty())
            tenantId = newTenant.getId();
        if (addressId.isEmpty())
            addressId = newTenant.getTenantAddresses().get(0).getId();
        int statusCode = tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId, addressId, gpsRequest, token);
        Assert.assertTrue((statusCode == 200) == positiveTest);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId(), token), 200);
    }

    @Test
    public void addNewCommonFlow() throws IOException {
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
// Verify that new common flow was added successful
        Assert.assertEquals(tenantActions.addCommonFlow(new File(file), token), 201);
        boolean isCommonFlowAdded = false;
        for (FlowResponse flow : tenantActions.getAllCommonFlows(token).getFlows()) {
            if (flow.getFileName().equals(fileName)) {
                isCommonFlowAdded = true;
                break;
            }
        }

        Assert.assertTrue(isCommonFlowAdded);
//        //get new common flow and convert it to InputStream
        InputStream actualImage = tenantActions.getCommonFlowAsInputStream(fileName, token);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));
//
//        //delete common flow
        Assert.assertEquals(tenantActions.deleteCommonFlow(fileName, token), 200);
    }

    @Test
    public void deleteCommonFlow() throws IOException {
// try to delete not existing common flow
// TODO        we need to add verification of error message when response for deleting will for each cases
        Assert.assertEquals(tenantActions.deleteCommonFlow("notexisting", token), 404);
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
//        add common flow
        tenantActions.addCommonFlow(new File(file), token);
//        //delete common flow
        Assert.assertEquals(tenantActions.deleteCommonFlow(fileName, token), 200);
    }

    @Test
    public void getNotExistingCommonFlow() throws IOException {
        String regExpErrorMessage = "Flow with name .* not found.";
        String actualErrorMessage = tenantActions.getCommonFlow("notexisting", token, ErrorMessage.class).getErrorMessage();
        Assert.assertTrue(actualErrorMessage.matches(regExpErrorMessage));
    }

    @Test
    public void addNewTenantFlow() throws IOException {

        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
//        try to create tenant flow with not existing tenant
        Assert.assertEquals(tenantActions.addTanentFlow("notExisting", new File(file), token), 404);
//        create tenant for test further test case
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
// Verify that new tenant flow was added successful
        String tenantId = newTenant.getId();
        Assert.assertEquals(tenantActions.addTanentFlow(tenantId, new File(file), token), 201);
        boolean isCommonFlowAdded = false;
        for (FlowResponse flow : tenantActions.getAllTanentFlows(tenantId, token).getFlows()) {
            if (flow.getFileName().equals(fileName)) {
                isCommonFlowAdded = true;
                break;
            }
        }

        Assert.assertTrue(isCommonFlowAdded);
//        //get new common flow and convert it to InputStream
        InputStream actualImage = tenantActions.getTanentFlowAsInputStream(tenantId, fileName, token);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage, expectedImage));
//
//        //delete common flow
        Assert.assertEquals(tenantActions.deleteTanentFlow(tenantId, fileName, token), 200);
        Assert.assertEquals(tenantActions.deleteTenant(tenantId, token), 200);
    }

    @Test
    public void deleteTenantFlow() throws IOException {
        String fileName = "testclickatell";
        String file = getFullPathToFile("TenantResources/" + fileName);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        String tenantId = newTenant.getId();
//      add tenant flow
        tenantActions.addTanentFlow(tenantId, new File(file), token);
// try to delete not existing tenant flow with not existing tenant
// TODO        we need to add verification of error message when response for deleting will for each cases
        Assert.assertEquals(tenantActions.deleteTanentFlow("notexisting", "notexisting", token), 404);
//   try to delete not existing tenant flow in existing tenant
        Assert.assertEquals(tenantActions.deleteTanentFlow(tenantId, "notexisting", token), 404);
//        try to delete existing tenant flow in not existing tenant
        Assert.assertEquals(tenantActions.deleteTanentFlow("notexisting", fileName, token), 404);
//  try to delete tenant flow from tenant without any flows
        tenantRequest = new TenantRequest();
        TenantResponseV5 tenant2 = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        Assert.assertEquals(tenantActions.deleteTanentFlow(tenant2.getId(), fileName, token), 404);

//     delete tenant flow
        Assert.assertEquals(tenantActions.deleteTanentFlow(tenantId, fileName, token), 200);
        Assert.assertEquals(tenantActions.deleteTenant(tenantId, token), 200);
        Assert.assertEquals(tenantActions.deleteTenant(tenant2.getId(), token), 200);
    }


    @Test
    public void getNotExistingTenantFlow() throws IOException {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 tenant2 = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        String teantId = tenant2.getId();
        String notExistingFlow = "notexisting";
        String regExpErrorMessage = "Flow with name " + notExistingFlow + ", tenant id " + teantId + " not found.";
        Assert.assertTrue(tenantActions.getTanentFlow(teantId, notExistingFlow, token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        Assert.assertEquals(tenantActions.deleteTenant(teantId, token), 200);
    }

    @Test
    public void getNearestTenants() throws IOException {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponseV5 tenant1 = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        tenantRequest = new TenantRequest();
        TenantResponseV5 tenant2 = tenantActions.createNewTenantInTouchSide(tenantRequest, token, TenantResponseV5.class);
        String tenantId1 = tenant1.getId();
        String tenantId2 = tenant2.getId();
        GpsRequest gpsRequest1 = new GpsRequest(60f, 60f);
        GpsRequest gpsRequest2 = new GpsRequest(70f, 70f);
        String addressId1 = tenant1.getTenantAddresses().get(0).getId();
        String addressId2 = tenant2.getTenantAddresses().get(0).getId();
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId1, addressId1, gpsRequest1, token);
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId2, addressId2, gpsRequest2, token);
        TenantResponseV5 ten1 = tenantActions.getTenant(tenantId1, token, TenantResponseV5.class);
        TenantResponseV5  ten2 = tenantActions.getTenant(tenantId2, token, TenantResponseV5.class);
        List<TenantResponseV5> nearestTenantsList = tenantActions.getNearestTenantsList(gpsRequest1.getLat().toString(), gpsRequest1.getLng().toString(), "1500000", token);
        List<TenantResponseV5> newTenantsList = Arrays.asList(ten1, ten2);
        Assert.assertTrue(nearestTenantsList.containsAll(newTenantsList), "New tenets are not in nearest tenants list");
        Assert.assertEquals(tenantActions.deleteTenant(tenantId1, token), 200);
        Assert.assertEquals(tenantActions.deleteTenant(tenantId2, token), 200);
    }

    @Test
    public void addBussinesHoursForAddress(){
        String addressId = testTenant.getTenantAddresses().get(0).getId();
        BusinessHourRequest businessHourRequest = new BusinessHourRequest(BusinessHourRequest.DayOfWeekEnum.THURSDAY, "10:00", "19:00");
//        add new bussines hours
        Response addBussinesHoursResponce = tenantActions.addBussinesHoursForAddress(testTenant.getId(), addressId, businessHourRequest, token);
//        Verify status code and that new Bussines hours have been added to tenant address
        Assert.assertEquals(addBussinesHoursResponce.getStatusCode(),200);
        Assert.assertTrue(tenantActions.getBussinesHoursFromAddress(testTenant.getId(),addressId, token, ListAddressBusinessHoursResponse.class).getAddressBusinessHours().contains(addBussinesHoursResponce.as(AddressBusinessHourResponse.class)));

    }
//    @Test
//    public void getBussinesHoursForAddressByNotExistingTenant(){
//        String addressId = testTenant.getTenantAddresses().get(0).getId();
//        Assert.assertTrue(tenantActions.getBussinesHoursFromAddress("not_existing",addressId, token, ErrorMessage.class).getErrorMessage().matches("Tenant with id .* not found"));
//
//    }
//    @Test
//    public void getBussinesHoursForAddressByNotExistingAddress(){
//        Assert.assertTrue(tenantActions.getBussinesHoursFromAddress(testTenant.getId(),"not_existing", token, ErrorMessage.class).getErrorMessage().matches("Address with id .* not found"));
//
//    }
//    @Test
//    public void updateBussinesHoursForAddress(){
//
//
//    }
//    @Test
//    public void deleteBussinesHoursForAddress(){
//
//    }
//    @Test
//    public void updateBussinesHoursForAddressWithWrongData(){
//
//    }
//    @Test
//    public void addBussinesHoursForTenant(){
//
//    }
//    @Test
//    public void updateBussinesHoursForTenant(){
//
//    }
//
//    @Test
//    public void updateBussinesHoursForTenantWithWrongData(){
//
//    }
//    @Test
//    public void addFaqs(){
//        TenantFaqRequest faq = new TenantFaqRequest("test question", "test answer");
//        // add new faq
//        Response response = tenantActions.addFAQs(testTenant.getId(), faq, token);
////        verify that we get correct status code and new faq was added to tenant
//        Assert.assertEquals(response.getStatusCode(),201);
//        Assert.assertTrue(tenantActions.getFAQs(testTenant.getId(),token, TenantFaqRequest.class).equals(""));
//
//
//
//
//    }
//    @Test
//    public void addAndDeleteAndUpdateFaqsWithWrongData(){
//
//    }
//    @Test
//    public void updateFaqs(){
//
//    }
//    @Test
//    public void deleteFaqs(){
//
//    }
//    @Test
//    public void addTags(){
//
//    }
//    @Test
//    public void deleteTags(){
//
//    }
//    @Test
//    public void deleteAndUpdateTagsWithWrongData(){
//
//    }

    @AfterClass
    public void afterClass() {
        List<TenantResponseV5> tenantsList = tenantActions.getTenantsList(token);
        for (TenantResponseV5 tenant : tenantsList) {
            if (tenant.getTenantOrgName().contains("Test") || tenant.getTenantOrgName().contains("test"))
                tenantActions.deleteTenant(tenant.getId(), token);
        }
    }

    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }

    private boolean isEqualInputStreams(InputStream i1, InputStream i2) throws IOException {

        try {
            // do the compare
            while (true) {
                int fr = i1.read();
                int tr = i2.read();

                if (fr != tr)
                    return false;

                if (fr == -1)
                    return true;
            }

        } finally {
            if (i1 != null)
                i1.close();
            if (i2 != null)
                i2.close();
        }
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

}
