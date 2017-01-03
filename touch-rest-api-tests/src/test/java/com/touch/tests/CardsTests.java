package com.touch.tests;

import com.touch.models.touch.cards.ListCardsPlatformsResponseV4;
import com.touch.models.touch.cards.PlatformDtoV4;
import com.touch.models.touch.cards.TouchCardResponseV3;
import com.touch.models.touch.cards.TouchCardResponseV4;
import com.touch.models.touch.tenant.TenantResponseV5;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class CardsTests extends BaseTestClass {
    String token;
    TenantResponseV5 testTenant;

    @BeforeClass
    public void beforeClass() {
        token = getToken();
//        testTenant = tenantActions.createNewTenantInTouchSide(new TenantRequest(), token, TenantResponseV5.class);
//        add new test card
        cardsActions.addCard("web", "testCard", "testDescription", "tenantId", "200", "200", new File(getFullPathToFile("cards/test-navigation-card")), token);
    }

    @Test(dataProvider = "getCardListOptions")
    public void getCardsList(String name, String platform, int code, boolean positiveTest) {
        if (name.equals("test"))
//            name=testCard.getCardName();
            name = "testCard";
        if (platform.equals("test"))
//            platform = testCard.getPlatforms().get(0).getPlatform();
            platform = "web";
        Response response = cardsActions.getAllCards(name, platform, token);
        Assert.assertEquals(response.getStatusCode(), code);
        Assert.assertEquals(!response.as(ListCardsPlatformsResponseV4.class).getCards().isEmpty(), positiveTest);
    }

    @Test(dataProvider = "addNewCardOptions")
    public void addNewCard(String cardName, String platform, String description, String tenantId, String width, String height, int status) {
        Response response = cardsActions.addCard(platform, cardName, description, tenantId, width, height, new File(getFullPathToFile("cards/test-navigation-card")), token);
        Assert.assertEquals(response.getStatusCode(), status);

//        if(status==201){
//            //        TODO we need to add steps with verification that new card wa added successful
//            Assert.assertTrue(false);
//        }

    }


    @Test
    public void getCardAndVerifyContext() throws IOException {
        //add new test card
        cardsActions.addCard("web", "testCard", "testDescription", "testTenantId", "200", "200", new File(getFullPathToFile("cards/test-navigation-card")), token);
//        get test card data
        Response response = cardsActions.getCard("testCard", "web", "", token);
        InputStream  newCardInputStream = response.asInputStream();
        InputStream expectedInputStream = new FileInputStream(new File(getFullPathToFile("cards/test-navigation-card")));
//        verify that new card contains same data which was added when we create it
        Assert.assertTrue(isEqualInputStreams(newCardInputStream, expectedInputStream));
    }

    @Test(dataProvider = "getCardOptions")
    public void getCard(String name, String platform, String version, int status) throws IOException {
//        get test card data
        Response response = cardsActions.getCard(name, platform, version, token);
//        verify status code of response when we try to get card by name and other parameters
        Assert.assertEquals(response.getStatusCode(), status);
    }


    @Test
    public void deleteCard() {
        //add new test card
        cardsActions.addCard("web", "test", "testDescription", "testTenantId", "200", "200", new File(getFullPathToFile("cards/test-navigation-card")), token);
        List<TouchCardResponseV4> cards = cardsActions.getAllCards("test", "web", token).as(ListCardsPlatformsResponseV4.class).getCards();
        for (PlatformDtoV4 platform : cards.get(0).getPlatforms()) {
            Assert.assertEquals(cardsActions.deleteCard(cards.get(0).getCardName(), platform.getPlatform(), platform.getVersion(), token).getStatusCode(), 200);
        }
    }

    @Test(dataProvider = "deleteCardsOptions")
    public void deleteCardWithWrongData(String cardName, String platform, String version, int statusCode) {
        Assert.assertEquals(cardsActions.deleteCard(cardName, platform, version, token).getStatusCode(), statusCode);
    }

    @Test (dataProvider = "updateCardOptions")
    public void updateCardInfo(String name, String platform, String version, String width, String height, int statusCode) {

Assert.assertEquals(cardsActions.updateCardInfo(name, platform, version, width, height,token).getStatusCode(),statusCode);
//        if (status == 201) {
//            //        TODO we need to add steps with verification that new card wa added successful
//            Assert.assertTrue(false);
//        }

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
    private static Object[][] updateCardOptions() {
        return new Object[][]{
                {"testCard", "web", "", "", "", 200},
                {"testCard", "android", "", "", "", 200},
                {"testCard", "ios", "", "", "", 200},
                {"1111112", "web",  "", "", "", 404},
                {"testCard", "test", "", "", "", 404},
                {"testCard", "web",  "1", "", "", 200},
                {"testCard", "web",  "test", "", "", 500},
                {"testCard", "web", "1", "300", "", 200},
                {"testCard", "web", "1", "300", "300", 200},
                {"testCard", "web", "1", "", "300", 200},
                {"testCard", "web",  "", "", "300", 200},
                {"testCard", "web",  "1", "300", "test", 400},
                {"testCard", "web",  "1", "test", "300", 400},
        };
    }
    @DataProvider
    private static Object[][] deleteCardsOptions() {
        return new Object[][]{
                {"testCard", "web", "11111", 404},
                {"testCard", "test", "1", 404},
                {"notExistingCard", "web", "1", 404},
                {"", "", "", 405}

        };
    }
    @DataProvider
    private static Object[][] addNewCardOptions() {
        return new Object[][]{
                {"testCard", "web", "", "", "", "", 201},
                {"testCard", "android", "", "", "", "", 201},
                {"testCard", "ios", "", "", "", "", 201},
                {"111", "web", "", "", "", "", 201},
                {"testCard", "test", "", "", "", "", 500},
                {"testCard", "web", "test", "", "", "", 201},
                {"testCard", "web", "test", "tenantId", "", "", 201},
                {"testCard", "web", "test", "tenantId", "300", "", 201},
                {"testCard", "web", "test", "tenantId", "300", "300", 201},
                {"testCard", "web", "test", "tenantId", "", "300", 201},
                {"testCard", "web", "test", "", "", "300", 201},
                {"testCard", "web", "test", "tenantId", "300", "test", 400},
                {"testCard", "web", "test", "tenantId", "test", "300", 400},


        };
    }

    @DataProvider
    private static Object[][] getCardListOptions() {
        return new Object[][]{
                {"test", "test", 200, true},
                {"test", "", 200, true},
                {"notExist", "", 404, false},
                {"", "notExist", 404, false},
                {"notExist", "notExist", 404, false},
                {"11", "", 404, false},
                {"", "11", 404, false},
                {"11", "11", 404, false}
        };
    }

    @DataProvider
    private static Object[][] getCardOptions() {
        return new Object[][]{
                {"testCard", "", "", 200},
                {"testCard", "", "1", 200},
                {"testCard", "111", "rrr", 500},
                {"testCard", "", "1111", 404},
                {"testCard", "web", "1111", 404},
                {"testCard", "not_exist", "1", 404}

        };
    }

    @AfterClass
    public void afterClass() {
        deleteTestCard("testCard", "web");
        deleteTestCard("111", "web");
    }

    private void deleteTestCard(String cardName, String platformName) {
        List<TouchCardResponseV4> cards = cardsActions.getAllCards(cardName, platformName, token).as(ListCardsPlatformsResponseV4.class).getCards();
        if (!cards.isEmpty()) {
            for (PlatformDtoV4 platform : cards.get(0).getPlatforms()) {
                cardsActions.deleteCard(cards.get(0).getCardName(), platform.getPlatform(), platform.getVersion(), token);
            }
        }
    }
}
