@skip_for_demo1
@camunda
Feature: Welcome flow: changing lastVisit date in agent mode

  Scenario: Welcome message enabling for Agent mode tenant and shifting lastVisit date backwards
    Given User select Automation tenant
    Given Taf welcome_message is set to true for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    Then Last visit date is saved to DB after 2 minutes
    When Last visit date is changed to minus 12 hours
    When User refreshes the widget page
    And Click chat icon
    Then User should see 'welcome' text response for his 'chat to agent' input

  Scenario: Welcome not appearing withing 12 hours twice in Agent mode
    Given User select Automation tenant
    Given Taf welcome_message is set to true for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    Then Last visit date is saved to DB after 2 minutes
    When User refreshes the widget page
    And Click chat icon
    Then User should not receive 'welcome' message after his 'chat to agent' message in widget
