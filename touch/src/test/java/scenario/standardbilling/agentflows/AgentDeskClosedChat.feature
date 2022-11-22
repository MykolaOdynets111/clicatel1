@no_widget
@Regression
Feature: Closed chat left menu actions

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