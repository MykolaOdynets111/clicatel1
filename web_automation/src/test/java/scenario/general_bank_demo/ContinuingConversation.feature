#Feature: Continuing conversation
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: User should be able to continue conversation after the first message
#    When User enter Are you open on Sunday? into widget input field
##    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'Are you open on Sunday?' input
#    And Card with a buttons Yes; No, that's all is shown on user Are you open on Sunday? message
#    When User select Yes in the card
#    Then User have to receive 'Sure, no problem' text response for his 'Yes' input
#    And Card with a button Chat to Support is shown on user Yes message
#    When User enter payment balance into widget input field