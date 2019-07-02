@smoke
Feature: User on his demand should be redirected on the agent

  Verification of basic communication between user and agent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon
    Given I login as agent of General Bank Demo

#  Welcome card is not showing anymore
#  Scenario: Verify if user is able to communicate with agent by selecting "Chat to us" in Welcome card
#    Given Welcome card with a button "Chat to us" is shown
#    When User select Chat to us option from Welcome card
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with Chat to us user's message in it
#    And There is no from agent response added by default for Chat to us user message
#    And There is no suggestions on user's input <user_message>
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his 'Chat to us' input


# Touch button is hidden for now
#  Scenario: Verify if user is able to communicate with agent by selecting "Chat to us" in touch menu
#    When User click Touch button
#    Then "Chat to us" is shown in touch menu
#    When User select "Chat to us" from touch menu
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with Chat to us user's message in it
#    And There is no from agent response added by default for Chat to us user message
#    And There is no suggestions on user's input Chat to us
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his 'Chat to us' input

  Scenario: User redirection to the Agent after negative message and storing it's sentiment
    When User enter Hate your banking into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with Hate your banking user's message
    Then Correct sentiment on Hate your banking user's message is stored in DB
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'Hate your banking' input
    When User enter how to check my balance? into widget input field
    Then Correct sentiment on how to check my balance? user's message is stored in DB
    When Agent closes chat
    Then Correct sentiment on how to check my balance? user's message is stored in DB


