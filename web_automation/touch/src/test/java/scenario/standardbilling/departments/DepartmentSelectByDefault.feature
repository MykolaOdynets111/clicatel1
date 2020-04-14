@remove_dep
Feature: Departments: Select department by default

  Background:
    Given I open portal
    When Login into portal as an admin of Standard Billing account
    And New departments with AutomationDefault name AutomationDefaultDescription description and second agent is created
    When Turn off the Last Agent routing

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-20925")
  Scenario: Verify if supervisor can select departments for chats to be routed by default
    And I select Touch in left menu and Touch preferences in submenu
    When Click "Chat Desk" nav button
    And Select AutomationDefault department By Default
    When I launch chatdesk from portal
    Given User select Standard Billing tenant
    And Click chat icon
    And User enter connect to agent into widget input field
    Then User should see 'agents_away' text response for his 'connect to agent' input
    Given I login as second agent of Standard Billing
    Then Second agent has new conversation request


