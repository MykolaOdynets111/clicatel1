Feature: Transferring chat

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5955")
  Scenario: Transfer :: Chat Desk should cancel "transfer offer"
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    Given I login as second agent of General Bank Demo
    When First Agent click on new conversation
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming transfer" header
    And Second agent can see transferring agent name, user name and following user's message: 'connect to agent'
    When First Agent click 'Cancel transfer' button
    Then Second agent has not see incoming transfer pop-up
    Then Conversation area becomes active with connect to agent user's message in it for first agent
    When First agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input

