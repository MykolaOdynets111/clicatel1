package steps.portalsteps;

import agentpages.supervisor.uielements.SupervisorClosedChatsTable;
import agentpages.uielements.FilterMenu;
import apihelper.ApiHelper;
import apihelper.ApiHelperSupportHours;
import datamanager.Tenants;
import datamanager.jacksonschemas.TenantChatPreferences;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import datamanager.jacksonschemas.supportHours.SupportHoursMapping;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AbstractAgentSteps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LeftMenuSteps extends AbstractAgentSteps {

    @Given("^(.*) has no active chats$")
    public void closeActiveChats(String agent) {
        ApiHelper.closeActiveChats(agent);
        getAgentHomePage(agent).getLeftMenuWithChats().waitForAllChatsToDisappear(4);
    }

    @Given("^(.*) checks left menu is having (.*) chats with latest message (.*)$")
    public void verifyChatsCountLeftMenuWithMessageText(String agent, int expectedCount, String expectedMessageText){
        Assert.assertEquals(getLeftMenu(agent).getLeftMenuMessageTexts(expectedMessageText).size(), expectedCount,
                "Chats doesn't contain expected text: " + expectedMessageText);
    }

    @Then("^(.*) has (?:new|old) (.*) (?:request|shown)(?: from (.*) user|)$")
    public void verifyIfAgentReceivesConversationRequest(String agent, String chatType, String integration) {
        if (integration == null) {
            if (ConfigManager.isWebWidget()) {
                integration = "touch";
            } else {
                integration = "ORCA";
            }
        }

        String userName = getUserName(integration);

        boolean isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName, 30);

        Map settingResults = new HashMap<String, Object>();

        if (!isConversationShown) {
            settingResults = verifyAndUpdateChatSettings(chatType);
            isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName, 30);
        }

        Assert.assertTrue(isConversationShown,
                "There is no new conversation request on Agent Desk (Client ID: " + userName + ")\n" +
//                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n" +
                        "sessionsCapacity: " + settingResults.get("sessionCapacityUpdate") + " and was: " + settingResults.get("sessionCapacity") + " before updating \n" +
                        "Support hours: " + settingResults.get("supportHoursUpdated") + "\n" +
                        "and was:" + settingResults.get("supportHours") + " before changing\n"
        );
    }

    public Map<String, Object> verifyAndUpdateChatSettings(String type) {
        Map<String, Object> results = new HashMap<String, Object>();

        int agentMaxChatsPerAgent = 0;
        List<SupportHoursMapping> supportHours = null;
        List<SupportHoursMapping> supportHoursUpdated = null;
        TenantChatPreferences prefs = ApiHelper.getTenantChatPreferences();
        agentMaxChatsPerAgent = prefs.getMaxChatsPerAgent();
        results.put("sessionCapacity", agentMaxChatsPerAgent);

        if (agentMaxChatsPerAgent < 50) {
            ApiHelper.updateChatPreferencesParameter(TenantChatPreferences.getDefaultTenantChatPreferences());
            results.put("sessionCapacityUpdate", 50);
        }

        supportHours = ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent(Tenants.getTenantUnderTestOrgName()).getAgentMapping();
        results.put("supportHours", supportHours);

        if (!type.equalsIgnoreCase("ticket")) {
            if (supportHours.get(0).getDays().size() < 7
                    && supportHours.get(0).getStartWorkTime().equals("00:00")
                    && supportHours.get(0).getEndWorkTime().equals("23:59")) {
                Response resp = ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
                supportHoursUpdated = resp.getBody().as(GeneralSupportHoursItem.class).getAgentMapping();
                results.put("supportHoursUpdated", supportHoursUpdated);
            } else {
                results.put("supportHoursUpdated", "were not updated because \"All week\" was set");
            }
        } else {
            results.put("supportHoursUpdated", "were not updated because it is ticket");
        }

        return results;
    }

    @Then("^(.*) click the bulk message icon$")
    public void bulkMessageButtonClick(String agent) {
        getLeftMenu(agent).clickBulkButton();
    }

    @Then("^(.*) checks number of checked bulk checkboxes is (.*)$")
    public void bulkMessageCheckboxesClickAndCheck(String agent, int checkedBulkChats) {
        if (checkedBulkChats > 5) {
            Assert.assertTrue(getLeftMenu(agent).bulkPanelElementsClick(checkedBulkChats), "Required checked checkboxes count is incorrect");
        } else {
            Assert.assertTrue(getLeftMenu(agent).bulkPanelElementsClickWithoutScroll(checkedBulkChats), "Required checked checkboxes count is incorrect");
        }
    }

    @Then("^(.*) sees checkbox is (.*) for the blocked chat$")
    public void isBulkButtonInPanelDisabled(String agent, String disability) {
        Assert.assertTrue(getLeftMenu(agent).isBulkPanelEnabled(disability));
    }

    @Then("^(.*) sees number of checked checkboxes is (.*)")
    public void isBulkCheckboxChecked(String agent, int numberOfCheckedBulkChats) {
        Assert.assertTrue(getLeftMenu(agent).isNumberOfCheckedChats(numberOfCheckedBulkChats),
                "Number of checked bulk chats count is not the same");
    }

    @Then("^(.*) has new conversation request within (.*) seconds$")
    public void verifyIfAgentReceivesConversationRequest(String agent, int timeout) {
        Assert.assertTrue(getLeftMenu(agent).isNewWebWidgetRequestIsShown(timeout),
                "There is no new conversation request on Agent Desk (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() + "\n");
    }

    @Then("(.*) sees 'overnight' icon in this chat")
    public void verifyOvernightIconShown(String agent) {
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconShown(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Overnight icon is not shown for overnight ticket. \n clientId: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
    }

    @Then("^Overnight ticket is removed from (.*) chatdesk$")
    public void checkThatOvernightTicketIsRemoved(String agent) {
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconRemoved(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Overnight ticket is still shown");
    }

    @Then("^(.*) select \"(.*)\" left menu option$")
    public void selectFilterOption(String agent, String option) {
        getLeftMenu(agent).getLMHeader().selectChatsMenu(option);
        getLeftMenu(agent).waitForConnectingDisappear(5, 10);
    }

    @Then("^(.*) checks current tab selected in left menu is (.*) tab")
    public void checkCurrentSelectedTab(String agent, String currentTab) {
        Assert.assertTrue(getLeftMenu(agent).getCurrentSelectedTabText().equalsIgnoreCase(currentTab),
                "Current tab selected is not correct");
    }

    @Then("^(.*) checks all chats from the previous tab should get deselected")
    public void checksBulkChatsUnSelected(String agent) {
        Assert.assertTrue(!getLeftMenu(agent).getBulkChatsButtonSelectedStatus().contains("active"),
                "Bulk chats are still not deselected");
    }

    @Then("^(.*) checks customer should stay on the same tab and all selected items stay selected")
    public void checksBulkChatsSelected(String agent) {
        Assert.assertTrue(getLeftMenu(agent).getBulkChatsButtonSelectedStatus().contains("active"),
                "Bulk chats are still not deselected");
    }

    @Then("^(.*) select Continue button$")
    public void selectFilterOption(String agent) {
        getLeftMenu(agent).clickContinueButton();
    }

    @Then("^(.*) select Wait and Stay button$")
    public void selectWaitAndStayButton(String agent) {
        getLeftMenu(agent).clickWaitAndStayButton();
    }

    @Then("^(.*) clicks close filter button$")
    public void clickCloseFilterButton(String agent) {
        getLeftMenu(agent).clickCloseButton();
    }

    @Then("^(.*) opens filter menu$")
    public void openFilterMenuAgentDesk(String agent) {
        getLeftMenu(agent).openFilterMenu();
    }

    @When("^(.*) filter Live Chants with (.*) channel, (.*) sentiment and flagged is (.*)$")
    public void setLiveChatsFilter(String agent, String channel, String sentiment, boolean flagged) {
        getLeftMenu(agent).applyTicketsFilters(channel.trim(), sentiment.trim(), flagged);
    }

    @When("^(.*) click on filter button$")
    public void setLiveChatsFilter(String agent) {
        getLeftMenu(agent).openFilterMenu();
    }

    @Then("^(.*) see channel, sentiment(?: and flagged|, flagged and dates) as filter options for (.*)$")
    public void verifyFilterOptions(String agent, String chatType) {
        FilterMenu filterMenu = getLeftMenu(agent).getFilterMenu();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(filterMenu.expandChannels().getDropdownOptions(), Arrays.asList("WhatsApp", "SMS", "Apple Business Chat"),
                "Channel dropdown has incorrect options");
        filterMenu.expandChannels();
        softAssert.assertEquals(filterMenu.expandSentiment().getDropdownOptions(), Arrays.asList("Positive", "Neutral", "Negative"),
                "Sentiment dropdown has incorrect options");
        filterMenu.expandSentiment();
        if (!chatType.contains("live")) {
            softAssert.assertTrue(filterMenu.isStartDateIsPresent(), "Start day option should be present in " + chatType + " filters");
            softAssert.assertTrue(filterMenu.isEndDateIsPresent(), "End day option should be present in " + chatType + " filters");
        }
        softAssert.assertAll();
    }

    @When("(.*) remove Chat Filter$")
    public void removeFilter(String agent) {
        getLeftMenu(agent).removeFilter();
    }

    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent) {
        // verifyChatRemovedFromChatDesk(String agent, String social) can be used instead of this
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(20, userName),
                "Conversation request is not removed from Agent Desk (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")"
        );
    }


    @Then("^(.*) should not see from user chat in agent desk from (.*)$")
    public void verifyChatRemovedFromChatDesk(String agent, String social) {
        String userName = getUserName(social);
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(20, userName),
                "Conversation request is not removed from Agent Desk (Client ID: " + userName + ")");
    }

    @When("^(.*) click on (?:new|last opened) conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String agent, String socialChannel) {
        if (!ConfigManager.isWebWidget() && socialChannel.equalsIgnoreCase("touch")) {
            socialChannel = "orca";
        }
        getLeftMenu(agent).openChatByUserName(getUserName(socialChannel));
    }

    @When("^(.*) click on new conversation$")
    public void acceptUserConversation(String ordinalAgentNumber) {
        getLeftMenu(ordinalAgentNumber).openNewConversationRequestByAgent();
    }

    @And("^Empty image is not shown for chat with (.*) user$")
    public void verifyEmptyImgNotShown(String customerFrom) {
        String user = "";
        if (customerFrom.equalsIgnoreCase("facebook")) user = socialaccounts.FacebookUsers.getLoggedInUserName();
        Assert.assertTrue(getLeftMenu("main").isProfileIconNotShown(),
                "Image is not updated in left menu with chats. \n");
    }

    @Then("^Message (.*) shown like last message in left menu with chat$")
    public void verifyLastMessageInLeftMenu(String customerMsg) {
        Assert.assertEquals(getLeftMenu("main").getActiveChatLastMessage(), customerMsg,
                "Last message in left menu with chat as not expected. \n");
    }

    @Then("^(.*) should see (.*) integration icon in left menu with chat$")
    public void verifyImageMessageInLeftMenu(String agent, String adapter) {
        assertThat(getLeftMenu(agent).getChatIconName())
                .as(format("Image should have name %s", adapter))
                .isEqualTo(adapter);
    }

    @Then("^The (.*) time set for last message in left menu with chat for (.*)$")
    public void verifyTheMessageArrivedTime(String messageTime, String agent) {
        assertThat(getLeftMenu(agent).getActiveChatReceivingTime())
                .as("Chat receiving time in left menu as not expected.")
                .contains(messageTime);
    }

    @Then("^Valid sentiment icon are shown for (.*) message in left menu with chat$")
    public void verifyIconSentimentForLastMessageInLeftMenu(String message) {
        Assert.assertTrue(getLeftMenu("main").isValidIconSentimentForActiveChat(message),
                "Image in last message in left menu for sentiment as not expected. \n");
    }


    @And("^(.*) search ticket with a customer name \"([^\"]*)\"$")
    public void agentTypesACustomerNameBlaBlaOnTheSearchField(String agent, String userName) {
        getLeftMenu(agent).searchTicket(userName);
    }

    @Then("^(.*) receives an error message \"([^\"]*)\"$")
    public void agentReceivesAnErrorMessage(String agent, String errorMessage) {
        Assert.assertEquals(getLeftMenu(agent).getNoResultsFoundMessage(), errorMessage,
                "Wrong no results found error message found");
    }

    @And("^(.*) click on search button in left menu$")
    public void agentClickOnSearchButtonInLeftMenu(String agent) {
        getLeftMenu(agent).clickOnSearchButton();
    }

    @And("^(.*) types a customer name \"([^\"]*)\" on the search field$")
    public void agentTypesACustomerNameOnTheSearchField(String agent, String userName) {
        getLeftMenu(agent).inputUserNameIntoSearch(userName);
    }


    @Then("(.*) tab is displayed first for (.*)")
    public void verifyThatChatsTabIsDisplayedFirst(String tabName, String agent) {
        Assert.assertTrue(getLeftMenu(agent)
                .getLMHeader().getFirstElementName().equals(
                        tabName), tabName + " should be on of first place");
    }

    @Then("^Filter \"(.*)\" is selected by default$")
    public void filterIsSelectedByDefault(String filterName) {
        Assert.assertEquals(getLeftMenu("main").getSupervisorAndTicketsPart().getFilterByDefaultName(), filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Verify (.*) ticket types are available$")
    public void verifyTicketTypes(List<String> ticketTypes) {
        Assert.assertEquals(getLeftMenu("main").getSupervisorAndTicketsPart().getFilterNames(), ticketTypes, "Ticket types are different");
    }

    @When("^(.*) select (.*) filter on Left Panel$")
    public void selectTicketType(String agent, String type) {
        getLeftMenu(agent).getSupervisorAndTicketsPart().selectFilter(type);
    }

    @Then("^Live chats (.*) filter has correct name and correct chats number$")
    public void verifyAgentNameOnLiveChatFilter(String agent) {
        String agentName = getAgentName(agent);
        int numberOfChats = ApiHelper.getActiveChatsByAgent(agent).jsonPath().getList("content").size();
        Assert.assertEquals(getLeftMenu("main").getSupervisorAndTicketsPart()
                .getLiveChatsNumberForAgent(agentName), numberOfChats, "Number of chats on Supervisor desk is different from Agent desk chats");
    }

    @When("Verify {string} display as default")
    public void verifyDisplayDefault(String liveChats) {
        Assert.assertEquals(getLeftMenu("main").getSupervisorAndTicketsPart().getFirstFilterName(), liveChats, "All chats text is not selected default");
    }

    @Then("Verify that live chats available are shown")
    public void verifyThatLiveChatAvailableAreShown() {
        Assert.assertTrue(getLeftMenu("main").getSupervisorAndTicketsPart().isLiveChatsInfoDisplayed(),
                "Live chat not displayed ");
    }

    @Then("^Verify closed chat is present for (.*)$")
    public void verifyClosedChatIsPresent(String chanel) {
        Assertions.assertThat(getSupervisorDeskPage().getClosedChatNames())
                .as("Closed chat should be present!")
                .contains(getUserName(chanel));
    }

    @Then("^Open closed chat (.*)$")
    public void openClosedChat(String chanel) {
        SupervisorClosedChatsTable closedChatsTable = getSupervisorDeskPage().getSupervisorClosedChatsTable();
        String chatName = getUserName(chanel);

        if (closedChatsTable.isClosedChatPresent(chatName)) {
            closedChatsTable.openClosedChat(chatName);
        }
    }
}

