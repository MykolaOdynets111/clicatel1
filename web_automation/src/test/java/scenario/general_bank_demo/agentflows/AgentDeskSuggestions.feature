@suggestions
Feature: Agent suggestions

  Background:
    Given AGENT_ASSISTANT tenant feature is set to true for General Bank Demo
    And I login as agent of General Bank Demo
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Agent should be able to work with suggestion
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When User enter trading hours into widget input field
    Then There is correct suggestion shown on user message "trading hours"
    And The suggestion for user message "trading hours" with the biggest confidence is added to the input field
    When Agent click send button
    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'trading hours' input
    When User enter interest rate into widget input field
    When Agent add additional info " Thanks for asking" to suggested message
    And Agent click send button
    Then User have to receive 'You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info. Thanks for asking' text response for his 'interest rate' input
    When User enter i lost my card into widget input field
    Then There is correct suggestion shown on user message "i lost my card"
    And The suggestion for user message "i lost my card" with the biggest confidence is added to the input field
    And Agent is able to delete the suggestion from input field and sent his own "Let me see what I can do" message
    Then User have to receive 'Let me see what I can do' text response for his 'i lost my card' input

  Scenario: Interaction with "Clear" and "Edit" suggestion buttons
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    When User enter trading hours into widget input field
    Then There is correct suggestion shown on user message "trading hours"
    Then The suggestion for user message "trading hours" with the biggest confidence is added to the input field
    And 'Clear' and 'Edit' buttons are shown
    When Agent click Edit suggestions button
    Then Agent is able to add " Please visit us."
    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us Please visit us.' text response for his 'trading hours' input
    When User enter i lost my card into widget input field
    Then There is correct suggestion shown on user message "i lost my card"
    When Agent click Clear suggestions button
    Then Message input field is cleared
    When Agent responds with please verify your identity to User
    Then User have to receive 'please verify your identity' text response for his 'i lost my card' input

  Scenario: Suggestions should be shown in descending order by confidence
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When User enter some question regarding balance into widget input field
    Then There is correct suggestion shown on user message "some question regarding balance" and sorted by confidence
    And The suggestion for user message "some question regarding balance" with the biggest confidence is added to the input field
