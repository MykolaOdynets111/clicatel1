@camunda
@no_chatdesk
Feature: Busy message auto responder Bot mode

  Scenario: Busy message disabling for Bot mode tenant
    Given Taf agents_away is set to false for Automation Bot tenant
    Given Set agent support hours for all week
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter connect to Support into widget input field
    Then There is no agents_away response

  Scenario: Busy message resetting to default for Bot mode tenant
    Given Taf agents_away message text is updated for Automation Bot tenant
    Given Set agent support hours for all week
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    When Wait for auto responders page to load
    And Agent click expand arrow for Agent Busy message auto responder
    And Click "Reset to default" button for Agent Busy message auto responder
    Then agents_away is reset on backend
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter connect to Support into widget input field
    Then Text response that contains "agents_away" is shown
