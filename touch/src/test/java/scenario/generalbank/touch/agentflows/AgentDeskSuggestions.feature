@suggestions
Feature: Agent suggestions

  Background:
    Given User select General Bank Demo tenant
    Given AGENT_ASSISTANT tenant feature is set to true for General Bank Demo
    And I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Agent should be able to work with suggestion
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When User enter trading hours into widget input field
    Then There is correct suggestion shown on user message "trading hours"
    And The suggestion for user message "trading hours" with the biggest confidence is added to the input field
    And Agent click send button
    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'trading hours' input
    When User enter Do you have a job for me? into widget input field
    Then There is correct suggestion shown on user message "Do you have a job for me?"
    When Agent is able to add " Thanks for asking"
    Then User have to receive 'For information regarding vacancies and posts at General Bank you may visit us Thanks for asking' text response for his 'Do you have a job for me?' input
    When User enter i lost my card into widget input field
    Then There is correct suggestion shown on user message "i lost my card"
    And The suggestion for user message "i lost my card" with the biggest confidence is added to the input field
    And Agent is able to delete the suggestion from input field and sends his own "Let me see what I can do" message
    Then User have to receive 'Let me see what I can do' text response for his 'i lost my card' input

  Scenario: Interaction with "Clear" and "Edit" suggestion buttons
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When User enter trading hours into widget input field
    Then There is correct suggestion shown on user message "trading hours"
    Then The suggestion for user message "trading hours" with the biggest confidence is added to the input field
    And 'Clear' button is shown
    Then Agent is able to add " Please visit us."
    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us Please visit us.' text response for his 'trading hours' input
    When User enter i lost my card into widget input field
    Then There is correct suggestion shown on user message "i lost my card"
    When Agent click Clear suggestions button
    Then Message input field is cleared
    When Agent responds with please verify your identity to User
    Then User have to receive 'please verify your identity' text response for his 'i lost my card' input
