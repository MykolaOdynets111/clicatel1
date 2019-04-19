@camunda
Feature: Busy message auto responder Bot mode


  Scenario: Busy message disabling for Bot mode tenant
    Given Taf agents_away is set to false for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter connect to Support into widget input field
    Then There is no agents_away response

  Scenario: Busy message disabling for Bot mode tenant
    Given Taf agents_away is set to true for Automation Bot tenant
    Given Taf agents_away message text is updated for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter connect to Support into widget input field
    Then Text response that contains "agents_away" is shown
