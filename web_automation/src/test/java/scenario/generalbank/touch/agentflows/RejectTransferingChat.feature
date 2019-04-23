Feature: Transferring chat rejection

  Verification transfer chat rejection functionality

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify if agent is able to reject transfer chat
    When User enter connect to agent2 into widget input field
    Then Agent has new conversation request
    Given I login as second agent of General Bank Demo
    When First Agent click on new conversation
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, user name and following user's message: 'connect to agent2'
    When Second agent click "Reject transfer" button
    Then Agent receives incoming transfer with "Transfer rejected" header
    And Correct Rejected by field is shown for first agent
    When Agent click "Accept" button
    Then Agent has new conversation request
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent2' input



