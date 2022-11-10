@orca_api
@Regression
Feature: Transfer chat with no Agents available

  Background:
    Given I login as agent of General Bank Demo

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2956")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer chat :: If there is no Agents available, after user opened dropdown menu, - "current chat assignment"  should be displayed against the current agent
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears for agent
    When Select 'Transfer to' drop down
    Then Agent is shown as current chat assignment and disabled for selection
    And Button 'Transfer chat' is not active


