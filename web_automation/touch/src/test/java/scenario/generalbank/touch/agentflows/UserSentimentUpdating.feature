@smoke
Feature: User on his demand should be redirected on the agent

  Verification of basic communication between user and agent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon
    Given I login as agent of General Bank Demo

  Scenario: User redirection to the Agent after negative message and storing it's sentiment
    When User enter Hate your banking into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with Hate your banking user's message
    Then Correct sentiment on Hate your banking user's message is stored in DB
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'Hate your banking' input
    When User enter how to check my balance? into widget input field
    Then Correct sentiment on how to check my balance? user's message is stored in DB
    When Agent closes chat
    Then Correct sentiment on how to check my balance? user's message is stored in DB


