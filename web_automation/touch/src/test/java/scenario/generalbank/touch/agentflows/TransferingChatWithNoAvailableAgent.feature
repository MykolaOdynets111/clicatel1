Feature: Transfer chat with no Agents available

  Verification of basic transfer chat functionality

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1676")
  Scenario: Transfer chat :: If there is no Agents available, after user opened dropdown menu, - "There is no available online agents ."
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When First Agent click on new conversation
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears
    When Select 'Transfer to' drop down
    Then Agent sees 'There is no available online agents .'
    And Button 'Transfer chat' is not active


