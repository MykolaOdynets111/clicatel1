@Regression
Feature: Chat console: Overview tab

  @skip
  Scenario: Dashboard:: Active Live chats counter
    Given User select General Bank Demo tenant
    And Click chat icon
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    Given I login as second agent of General Bank Demo
    When User enter connect to agent into widget input field
    Then Second agent has new conversation request
    Then Customer engaging with an Agent counter shows correct live chats number
    And Average chats per Agent is correct


  @TestCaseId("https://jira.clickatell.com/browse/CCD-1698")
  Scenario: CD:: SMS:: API:: PUT:: Update SMS Orca channel chat survey config with API
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Total Agents online widget shows correct number
    When I login as second agent of General Bank Demo
    Then Total Agents online widget value increased on 1