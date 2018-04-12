# Tests are obsolete because according to TPLAT-2547 and TPLAT-2283
# ToDo: add test on creating new session after clicking "Start chat" button

#Feature: User should be able to restart chat with "Start chat"  button
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Restarting chat with "Start chat" button
#    When User enter Trading Hours into widget input field
#    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'Trading Hours' input
#    And User enter End chat into widget input field
#    Then User have to receive 'Thank you. Chat soon!' text response for his 'End chat' input
#    And "Start chat" button is shown in widget's header
#    When User click "Start chat" button in widget's header
#    Then Card with a welcome text is shown after user End chat input
#    And Card with a buttons Chat to us is shown after user End chat input
#    And Welcome back message with correct text is shown after user's input 'End chat'
