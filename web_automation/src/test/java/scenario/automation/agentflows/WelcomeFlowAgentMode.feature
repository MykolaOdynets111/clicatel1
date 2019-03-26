@camunda
Feature: Welcome flow: agent mode

  Scenario: Welcome message disabling for Agent mode tenant
    Given Taf welcome_message is set to false for Automation tenant
    Given User select Automation tenant
    And Click chat icon
    Then Welcome message is not shown

  Scenario: Welcome message text changing for Agent mode tenant
    Given Taf welcome_message is set to true for Automation tenant
    Given User select Automation tenant
    Given Taf welcome_message message text is updated for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown