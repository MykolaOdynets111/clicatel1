Feature: Default SMS integration items visibility check

  Background:
    Given I login as admin of Standard Billing
    Given I select Touch in left menu and Configure Touch in submenu
    And Enable the SMS integration
    Then Status of SMS integration is changed to "Active"
    And I select Touch in left menu and Launch Chat Desk in submenu
    Given User select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message

  Scenario: Send SMS button available if SMS integration enabled
    Then Send SMS button is disabled for the Agent
