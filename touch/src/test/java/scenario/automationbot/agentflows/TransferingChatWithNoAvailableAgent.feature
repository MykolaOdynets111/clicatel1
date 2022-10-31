Feature: Transfer chat with no Agents available

  Verification of basic transfer chat functionality

  Background:
    Given User select Automation Bot tenant
    Given I login as agent of Automation Bot
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1676")
  @Issue("https://jira.clickatell.com/browse/TPORT-26668")
  Scenario: Transfer chat :: If there is no Agents available, after user opened dropdown menu, - "current chat assignment"
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When First Agent click on new conversation
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears for agent
    When Select 'Transfer to' drop down
    Then Agent is shown as current chat assignment and disabled for selection
    And Button 'Transfer chat' is not active


