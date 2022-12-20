@no_widget
@Regression
Feature: Bulk chat left menu actions

  Background:
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
    And Update survey management chanel whatsapp settings by ip for Standard Billing
      | ratingEnabled | false        |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6288")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify that if the bulk checkbox is disabled for the blocked chat ( //stop)
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //stop message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    Then Agent sees checkbox is disabled for the blocked chat

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6286")
  Scenario: CD:: Agent Desk:: : Verify if Agent is able to select only maximum 15 chats while sending the bulk message
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    Then Agent checks number of checked bulk checkboxes is 15
    And Agent checks error is displayed if selected more then 15 chats

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6613")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that notification message pop-up on selecting bulk messages and navigating to another tab using 'Continue' button
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    And Agent select "Live Chats" left menu option
    Then Agent checks notification message You have selected chat(s) that will be unselected should you deactivate the bulk message mode. should appear
    When Agent select Continue button
    And Agent checks current tab selected in left menu is Live tab
    And Agent select "Closed" left menu option
    Then Agent checks all chats from the previous tab should get deselected

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6614")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that clicking on "Wait, I want to stay here" or 'X' means user would stay on the same tab with selected items stay selected
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    And Agent select "Live Chats" left menu option
    Then Agent checks notification message You have selected chat(s) that will be unselected should you deactivate the bulk message mode. should appear
    When Agent select Wait and Stay button
    Then Agent checks current tab selected in left menu is Closed tab
    And Agent checks customer should stay on the same tab and all selected items stay selected
    When Agent select "Live Chats" left menu option
    Then Agent checks notification message You have selected chat(s) that will be unselected should you deactivate the bulk message mode. should appear
    And Agent select cross button wait and stay dialog
    And Agent checks current tab selected in left menu is Closed tab
    And Agent checks customer should stay on the same tab and all selected items stay selected

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6619")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that selecting "Don't show this message again" checkbox, didn't show it again while switching to another tab using bulk message
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    And Agent select "Live Chats" left menu option
    Then Agent checks notification message You have selected chat(s) that will be unselected should you deactivate the bulk message mode. should appear
    When Agent select Don't show this message again checkbox
    And Agent select Continue button
    Then Agent checks current tab selected in left menu is Live tab
    When Agent select "Closed" left menu option
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    When Agent select "Live Chats" left menu option
    And Agent checks current tab selected in left menu is Live tab
    When Agent select "Closed" left menu option
    Then Agent checks all chats from the previous tab should get deselected

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6641")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that while selecting bulk messages Agent sees the total number of selected chats in the right-hand panel and revert back to default view once bulk message is sent
    When Send 2 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Agent click the bulk message icon
    Then Agent checks selected chats should be shown in the (0) Live Chats Have Been Selected message on the right pane
    When Agent checks number of checked bulk checkboxes is 2
    And Agent sees number of checked checkboxes is 2
    Then Agent checks selected chats should be shown in the (2) Live Chats Have Been Selected message on the right pane
    And Agent clear input and type Bulk message, check send button gets enabled
    When Agent send Hello this is bulk message message
    Then Agent checks all chats from the previous tab should get deselected

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6643")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that the maximum chat selected for bulk messages is 15, post that remaining chats checkboxes gets disabled
    When Send 2 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    Then Agent sees number of checked checkboxes is 2
    When Send 1 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    Then Agent sees number of checked checkboxes is 2
    And Agent select "Closed" left menu option
    Then Agent checks notification message You have selected chat(s) that will be unselected should you deactivate the bulk message mode. should appear
    When Agent select Continue button
    And Agent checks current tab selected in left menu is Closed tab
    And Agent select "Live Chats" left menu option
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 3
    Then Agent sees number of checked checkboxes is 3

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6650")
  Scenario: CD :: Agent Desk :: Live Chat / Closed Chat :: Bulk Messages :: Verify that hovering over Bulk Messages icon displays a tooltip
    When Send 1 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Agent hover over "Bulk chat" button and see Select Bulk Messages message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6656")
    @start_orca_server
  Scenario: CD :: Agent Desk :: Closed Chat :: Bulk Messages :: Verify that free text message is delivered for the selected closed bulk chat
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 2
    And Agent send Hello this is bulk message message
    And Agent click on new conversation request from orca
    Then Conversation area contains Hello this is bulk message to user message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5714")
  @start_orca_server
  Scenario: CD :: Agent Desk :: Verify that if Agent is able to see the sent bulk message as a part of chat in the chat window
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 1
    And Agent send Hello this is bulk message message
    And Agent click on new conversation request from orca
    Then Conversation area contains Hello this is bulk message to user message
    When Send //end message by ORCA
    And Agent should not see from user chat in agent desk from orca
    And Agent select "Closed" left menu option
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area contains Hello this is bulk message to user message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6017")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that selecting Bulk Messages icon result into showing Checkboxes
    When Send 1 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with chat to agent user's message in it for agent
    And Agent click the bulk message icon
    And Agent checks number of checked bulk checkboxes is 1
    Then Agent checks Bulk Messages section should get displayed on the right side with header Send Bulk Message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5795")
  Scenario: CD:: Agent Desk:: Verify if Agent is able to select and send bulk message to all chats from different channel(SMS, ABC, WA) from live chat
    When Send 1 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Setup ORCA sms integration for Standard Billing tenant
    When Send 1 messages chat to agent by ORCA
    And Setup ORCA abc integration for Standard Billing tenant
    When Send 1 messages chat to agent by ORCA
    And Agent click the bulk message icon
    When Agent checks number of checked bulk checkboxes is 3
    And Agent sees number of checked checkboxes is 3
    When Agent send Hello this is bulk message message
    Then Agent checks all chats from the previous tab should get deselected
    And Agent click on new conversation request from orca
    And Conversation area contains Hello this is bulk message to user message
    And Agent checks left menu is having 3 chats with latest message Hello this is bulk message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5792")
  Scenario: CD :: Agent Desk :: Verify that if Supervisor is able to see the sent bulk message as a part of chat in the chat window
    When Send 1 messages chat to agent by ORCA
    And Agent has new conversation request from orca user
    And Agent click the bulk message icon
    When Agent checks number of checked bulk checkboxes is 1
    And Agent sees number of checked checkboxes is 1
    When Agent send Hello this is bulk message message
    And Agent checks all chats from the previous tab should get deselected
    And Agent click on new conversation request from orca
    Then Conversation area contains Hello this is bulk message to user message
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor can see orca live chat with Hello this is bulk message message from agent
    When I select Touch in left menu and Agent Desk in submenu
    And Agent click on new conversation request from orca
    And Agent closes chat
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Agent search chat orca on Supervisor desk
    And Supervisor opens closed chat
    Then Supervisor can see orca live chat with Hello this is bulk message message from agent
