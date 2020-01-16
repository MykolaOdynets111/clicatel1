@skip_for_demo1
@camunda
Feature: Welcome flow: changing lastVisit date in bot mode

  Scenario: Welcome message enabling for Bot mode tenant and shifting lastVisit date backwards
    Given User select Automation Bot tenant
    Given Taf welcome_message is set to true for Automation Bot tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation Bot
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    And Update conversation and session dates to 24 hours
    When User refreshes the widget page
    And Click chat icon
    Then User should see 'welcome' text response for his 'chat to agent' input

  Scenario: Welcome not appearing withing 24 hours twice in Bot mode
    Given User select Automation Bot tenant
    Given Taf welcome_message is set to true for Automation Bot tenant
    And Click chat icon
    Then Welcome message with correct text is shown
    Given I login as agent of Automation Bot
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent closes chat
    When User refreshes the widget page
    And Click chat icon
    Then User should not receive 'welcome' message after his 'chat to agent' message in widget
