@remove_dep
@off_survey_management
Feature: Last agent activate

  Background:
    Given Off survey configuration for Standard Billing
    Given I open portal
    When Login into portal as an admin of Standard Billing account
    And Turn off the Last Agent routing
    When Turn off the Default department

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-20917")
  Scenario: Verify if a returning customer can be assigned to the last agent that they spoke to
    And I select Touch in left menu and Touch preferences in submenu
    When Click "Chat Desk" nav button
    And Activate Last Agent routing
    When I launch chatdesk from portal
    Given User select Standard Billing tenant
    And Click chat icon
    And User enter connect to agent into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Given I login as second agent of Standard Billing
    When Agent closes chat
    Then Agent should not see from user chat in agent desk
    And User enter connect to agent into widget input field
    Then Second agent should not see from user chat in agent desk
    And Agent has new conversation request