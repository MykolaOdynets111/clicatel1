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

  Scenario: Welcome message text changing for Bot mode tenant
    Given User select Automation tenant
    Given Taf welcome_message message text is updated for Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown