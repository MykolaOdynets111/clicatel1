@skip_for_demo1
@camunda
Feature: Welcome flow: changing lastVisit date in agent modePortalInboxSteps

  Scenario: Welcome message enabling for Agent mode tenant and shifting lastVisit date backwards
    Given User select Automation tenant
    Given Taf welcome_message is set to true for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    Then All session attributes are closed in DB
    And Update conversation and session dates to 24 hours
    When User refreshes the widget page
    And Click chat icon
    Then User should see 'welcome' text response for his 'chat to agent' input

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4404")
  Scenario: Welcome appearing for every closed chat in Agent mode
    Given User select Automation tenant
    Given Taf welcome_message is set to true for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    Then Agent should not see from user chat in agent desk
    And All session attributes are closed in DB
    When User refreshes the widget page
    And Click chat icon
    Then Welcome message with correct text is shown
