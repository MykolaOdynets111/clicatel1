Feature: Sanity filter on agent's messages

  Background:
    Given I login as agent of General Bank Demo
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Verify if user is able to communicate with agent by typing "<user_message>" into widget
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with chat to support user's message in it
    When Agent responds with fuck off to User
    Then 'Profanity not allowed' pop up is shown
    When Agent closes 'Profanity not allowed' popup
    When Agent sends a new message how can I help you? to User
    Then User have to receive 'how can I help you?' text response for his 'chat to support' input
