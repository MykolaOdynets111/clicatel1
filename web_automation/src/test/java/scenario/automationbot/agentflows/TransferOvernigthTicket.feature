@agent_support_hours
Feature: Transfer overnight ticket

  Background:
    Given I login as agent of Automation Bot
    And Agent select "Tickets" filter option
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7385)
  Scenario: Verify transfer overnight ticket(Chat desk)
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    And Agent sees 'overnight' icon in this chat
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    And Conversation area contains out_of_support_hours to user message
    Given I login as second agent of Automation Bot
    And Agent transfers overnight ticket
    And Second Agent select "Tickets" filter option
    Then Second Agent has new conversation request
    When Second agent click on new conversation
    And Message that it is overnight ticket is shown for Second Agent
