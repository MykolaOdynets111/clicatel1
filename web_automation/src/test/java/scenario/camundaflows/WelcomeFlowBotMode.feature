@camunda
Feature: Welcome flow: bot mode

  Scenario: Welcome message disabling for Bot mode tenant
    Given User select Automation Bot tenant
    Given Taf welcome_message is set to false for Automation Bot tenant
    And Click chat icon
    Then Welcome message is not shown

  Scenario: Welcome message enabling for Bot mode tenant
    Given User select Automation Bot tenant
    Given Taf welcome_message is set to true for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown
#    When User enter trading hours into widget input field
#    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'trading hours' input
#    And Last visit date is saved to DB after 2 minutes
#    When User refreshes the widget page
#    And Click chat icon
#    Then Welcome message is not shown
#    When Last visit date is changed to minus 12 hours
#    And User refreshes the widget page
#    And Click chat icon
#    Then Welcome message with correct text is shown

  Scenario: Welcome message text changing for Bot mode tenant
    Given User select Automation tenant
    Given Taf welcome_message message text is updated for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown