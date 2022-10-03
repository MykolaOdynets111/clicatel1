@support_hours
Feature: Transfer overnight ticket

  Background:
    Given I login as agent of Automation Bot
    And Agent select "Tickets" left menu option
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7385)
  Scenario: Verify transfer overnight ticket(Chat desk)
    When User enter chat to agent into widget input field
    Then Agent has new ticket request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    Given I login as second agent of Automation Bot
    And Agent transfers overnight ticket
    And Second Agent select "Tickets" left menu option
    Then Second Agent has new ticket request
    When Second agent click on new conversation
    And Message that it is overnight ticket is shown for Second Agent
    And First Agent of General Bank Demo is logged in
    And First agent should not see from user chat in agent desk
