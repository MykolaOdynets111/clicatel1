@no_widget
@Regression
Feature: Bulk chat left menu actions

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6288")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify that if the bulk checkbox is disabled for the blocked chat ( //stop)
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6643")
  Scenario: CD :: Agent Desk :: Live Chat :: Bulk Messages :: Verify that the maximum chat selected for bulk messages is 15, post that remaining chats checkboxes gets disabled
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
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