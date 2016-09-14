package com.clickatell.tests;

import com.clickatell.models.ErrorMessage;
import com.clickatell.models.mc2.users.response.getallusers.User;
import com.clickatell.models.touch.tenant.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class TenantTests extends BaseTestClass {

    @BeforeClass
    public void beforeClass() {
        
    }
    @Test
    public void createNewTenant() {
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBeforeAddNew = tenantActions.getTenantsList().size();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        List<TenantResponse> tenantsListAfterAddNewTenant = tenantActions.getTenantsList();
        int amountTenantsAfterAddNew = tenantsListAfterAddNewTenant.size();
        Assert.assertEquals(amountTenantsBeforeAddNew + 1, amountTenantsAfterAddNew);
        Assert.assertTrue(tenantsListAfterAddNewTenant.contains(newTenant), "New tenant was not added to DB or it contains wrong data");
        Assert.assertTrue(tenantRequest.equals(tenantActions.getTenant(newTenant.getId(),TenantResponse.class)));
        //Verify get tenant request
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(),TenantResponse.class),newTenant);
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
        tenantsListAfterAddNewTenant = tenantActions.getTenantsList();
        // Verify that tenant was deleted after test
        Assert.assertEquals(amountTenantsBeforeAddNew, tenantsListAfterAddNewTenant.size());
    }
    //it is not actual not, nevertheless, it will be useful in future releases
    //@Test
    public void createNewTenantFromMC2() {
        int size1 = tenantActions.getTenantsList().size();
        User user = userActions.createUserAndLogin();
        int size2 = tenantActions.getTenantsList().size();

    }
    @Test
    public void createNewTenantWithoutAccount() {
        TenantRequest tenantRequest = new TenantRequest();
        tenantRequest.setAccountId(null);
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        List<TenantResponse> tenantsListAfterAddNewTenant = tenantActions.getTenantsList();
        Assert.assertTrue(tenantsListAfterAddNewTenant.contains(newTenant), "New tenant was not added to DB or it contains wrong data");
        //get tenant from DB
        TenantResponse tenant = tenantActions.getTenant(newTenant.getId(), TenantResponse.class);
        Assert.assertTrue(tenantRequest.equals(tenant));
        Assert.assertNotNull(tenant.getAccountId());
        //Verify get tenant request
        Assert.assertEquals(tenantActions.getTenant(newTenant.getId(),TenantResponse.class),newTenant);
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test
    public void addExistingTenant() {
        TenantRequest tenantRequest = new TenantRequest();
        String regExpErrorMessage="Tenant with .* already exists";
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        // Verify that when we add one more Tenant with same data we get errorMessage;
        Assert.assertTrue(tenantActions.createNewTenantInTouchSide(tenantRequest, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        /*
        post conditions
         */
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test
    public void updateTenantWithCorrectData() {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        Assert.assertTrue(tenantRequest.equals(tenantActions.getTenant(newTenant.getId(),TenantResponse.class)));
        // prepare new dada for update tenant
        TenantUpdateDto tenantUpdateDto = new TenantUpdateDto();
        tenantUpdateDto.setId(newTenant.getId());
        tenantUpdateDto.setCategory("New Bussines");
        tenantUpdateDto.setContactEmail("newFake@fake.perfectial.com");
        tenantUpdateDto.setDescription("New test description");
        tenantUpdateDto.setShortDescription("testShort");
        TenantResponse updatedTenant = tenantActions.updateTenant(tenantUpdateDto,TenantResponse.class);
        Assert.assertTrue(tenantUpdateDto.getCategory().equals(tenantUpdateDto.getCategory()));
        Assert.assertTrue(tenantUpdateDto.getContactEmail().equals(tenantUpdateDto.getContactEmail()));
        Assert.assertTrue(tenantUpdateDto.getDescription().equals(tenantUpdateDto.getDescription()));
        Assert.assertTrue(tenantUpdateDto.getShortDescription().equals(tenantUpdateDto.getShortDescription()));
        Assert.assertTrue(updatedTenant.equals(tenantActions.getTenant(newTenant.getId(),TenantResponse.class)));
        Assert.assertEquals(tenantActions.deleteTenant(updatedTenant.getId()), 200);

    }
    @Test
    public void updateNotExistingTenantWithCorrectData() {
        String regExpErrorMessage="Tenant with id .* not found";
        TenantUpdateDto tenantUpdateDto = new TenantUpdateDto();
        tenantUpdateDto.setId("fakeID0057003250015709bf1afe0208");
        tenantUpdateDto.setCategory("New Bussines");
        tenantUpdateDto.setContactEmail("newFake@fake.perfectial.com");
        tenantUpdateDto.setDescription("New test description");
        tenantUpdateDto.setShortDescription("testShort");
        Assert.assertTrue(tenantActions.updateTenant(tenantUpdateDto, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));

    }
    @Test
    public void deleteTenant() {
        String regExpErrorMessage="Tenant with id .* not found";
        TenantRequest tenantRequest = new TenantRequest();
        int amountTenantsBefore = tenantActions.getTenantsList().size();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        Assert.assertTrue(tenantActions.getTenant(newTenant.getId(),TenantResponse.class).equals(newTenant));
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
        Assert.assertEquals(amountTenantsBefore,tenantActions.getTenantsList().size());
        Assert.assertTrue(tenantActions.getTenant(newTenant.getId(), ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
    }
    @Test
    public void addColourToTenant() {
        String testColourName="test21";
        String testColourValue="test22";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        tenantActions.addColour(newTenant.getId(),testColourName,testColourValue,TenantColourDto.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getColoursForTenant(newTenant.getId()).contains(new TenantColourDto(testColourName,testColourValue)));
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test
    public void addColourWithTooBigLengthToTenant() {
        String regExpErrorMessage="could not execute statement; SQL \\[n/a\\]; nested exception is .*";
        String testColourName="test21";
        String testColourValue="test888888888";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        // verify that we get error message when we try to add to long colour
        Assert.assertTrue(tenantActions.addColour(newTenant.getId(),testColourName,testColourValue,ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }

    @Test
    public void deleteColourToTenant() {
        String testColourName="test21";
        String testColourValue="test22";
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        //add colour
        tenantActions.addColour(newTenant.getId(),testColourName,testColourValue,TenantColourDto.class);
        //Verify that new colour was added to tenant - we use get colour request for tenant
        Assert.assertTrue(tenantActions.getColoursForTenant(newTenant.getId()).contains(new TenantColourDto(testColourName,testColourValue)));
        //delete colour
        Assert.assertEquals(tenantActions.deleteColour(newTenant.getId(),testColourName), 200);
        //verify that deleted colour is not still available for tenant
        Assert.assertFalse(tenantActions.getColoursForTenant(newTenant.getId()).contains(new TenantColourDto(testColourName,testColourValue)));
        //delete colour in not existing tenant
        Assert.assertEquals(tenantActions.deleteColour("notReal",testColourName), 404);
        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test(dataProvider = "resourcesList")
    public void addNewResourceToTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(newTenant.getId(), resourceName, new File(file), Integer.class), 201);
        //get new resource and convert it to InputStream
        InputStream actualImage = tenantActions.getResourceAsInputStreamForTenant(newTenant.getId(), resourceName);
        InputStream expectedImage = new FileInputStream(new File(file));
        Assert.assertTrue(isEqualInputStreams(actualImage,expectedImage));

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test(dataProvider = "resourcesList")
    public void addNewResourceToNotExistTenant(String filePath, String resourceName) throws IOException {
        String regExpErrorMessage="Tenant with id .* not found";
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        //add new resource to not exist tenant
        Assert.assertEquals(tenantActions.addResource("000000005700325001571e3e40b80281", resourceName, new File(file), Integer.class), 404);
//        Assert.assertTrue(tenantActions.addResource(newTenant.getId(), resourceName, new File(file), ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test(dataProvider = "resourcesList")
    public void deleteResourceFromTenant(String filePath, String resourceName) throws IOException {
        String file = getFullPathToFile(filePath);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        //add new resource
        Assert.assertEquals(tenantActions.addResource(newTenant.getId(), resourceName, new File(file), Integer.class), 201);
        //delete new resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(newTenant.getId(),resourceName), 200);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test
    public void deleteNotExistingResourceFromTenant() throws IOException {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);

        //delete not existing resource from tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource(newTenant.getId(),"not_existing"), 200);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }
    @Test
    public void deleteNotExistingResourceFromNotExistingTenant() throws IOException {
        //delete not existing resource from not existing tenant
        /*to do  - we need to add verification of message not status code*/
        Assert.assertEquals(tenantActions.deleteResource("000000005700325001571e3e40b80281","not_existing"), 404);
    }
    @Test(dataProvider = "changeAddress")
    public void addNewAddressesToTenant(String tenantId, String addressId, Boolean positiveTest) {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
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
        int statusCode = tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId, addressId, gpsRequest);
        Assert.assertTrue((statusCode == 200) == positiveTest);

        //delete tenant
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);
    }




    private String getFullPathToFile(String pathToFile) {
        return Debug.class.getClassLoader().getResource(pathToFile).getPath();
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
