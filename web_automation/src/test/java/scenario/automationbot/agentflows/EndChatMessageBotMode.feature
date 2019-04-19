@camunda
Feature: End chat flow: bot mode

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot


  Scenario: End chat message disabling for Bot mode tenant
    Given Taf start_new_conversation is set to false for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    Given I login as agent of Automation Bot
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then There is no exit response

  Scenario: End chat message enabling and editing for Bot mode tenant
    Given Taf start_new_conversation is set to true for Automation Bot tenant
    Given Taf start_new_conversation message text is updated for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    Given I login as agent of Automation Bot
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then Text response that contains "exit" is shown
