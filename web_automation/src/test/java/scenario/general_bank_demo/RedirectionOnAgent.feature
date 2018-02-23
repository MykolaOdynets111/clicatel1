@smoke
Feature: User on his demand is redirected on the agent

  Verification of basic communication between user and agent

  Background:
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user is able to communicate with agent by typing "<user_message>" into widget
    When User enter <user_message> into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with <user_message> user's message in it
    And There is no more than one from user message
    And There is no from agent response added by default for <user_message> user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his '<user_message>' input

    Examples:
      |user_message    |
      |connect to agent|
      |chat to support |

  Scenario: Verify if user is able to communicate with agent by selecting "Chat to Support" in Welcome card
    Given Welcome card with a button "Chat to Support" is shown
    When User select Chat to Support option from Welcome card
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with Chat to Support user's message in it
    And There is no from agent response added by default for Chat to Support user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'Chat to Support' input

  Scenario: Verify if user is able to communicate with agent by selecting "Chat to Support" in touch menu
    When User click Touch button
    Then "Chat to Support" is shown in touch menu
    When User select "Chat to Support" from touch menu
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with Chat to Support user's message in it
    And There is no from agent response added by default for Chat to Support user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'Chat to Support' input