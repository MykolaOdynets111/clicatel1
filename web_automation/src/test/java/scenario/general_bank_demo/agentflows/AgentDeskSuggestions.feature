#Commented out due to TPORT-1757 story
#Feature: Agent suggestions
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given User profile for generalbank is created
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Agent should be able to work with suggestion
#    When User enter Chat to Support into widget input field
#    Then Agent has new conversation request
#    And Agent click on new conversation
#    When User enter trading hours into widget input field
#    Then There is correct suggestion shown on user message "trading hours"
#    And The suggestion for user message "trading hours" with the biggest confidence is added to the input field
#    When Agent click send button
#    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'trading hours' input
#    When User enter interest rate into widget input field
#    When Agent add additional info " Thanks for asking" to suggested message
#    And Agent click send button
#    Then User have to receive 'You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info. Thanks for asking' text response for his 'interest rate' input
#    When User enter check account balance into widget input field
#    Then There is correct suggestion shown on user message "check account balance"
#    And The suggestion for user message "check account balance" with the biggest confidence is added to the input field
#    And Agent is able to delete the suggestion from input field and sent his own "Let me see what I can do" message
#    Then User have to receive 'Let me see what I can do' text response for his 'check account balance' input
#
#  Scenario: Suggestions should be shown in descending order by confidence
#    When User enter Chat to Support into widget input field
#    Then Agent has new conversation request
#    And Agent click on new conversation
#    When User enter some question regarding card into widget input field
#    Then There is correct suggestion shown on user message "some question regarding card" and sorted by confidence
#    And The suggestion for user message "some question regarding card" with the biggest confidence is added to the input field
