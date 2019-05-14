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


  @no_chatdesk
  Scenario: Reset to default Welcome message auto responder (Agent mode)
    Given Taf welcome_message is set to true for Automation tenant
    Given Taf welcome_message message text is updated for Automation tenant
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Auto responders" nav button
    When Wait for auto responders page to load
    And Agent click expand arrow for Welcome message auto responder
    And Click "Reset to default" button for Welcome message auto responder
    Then welcome_message is reset on backend
    Given User select Automation tenant
    And Click chat icon
    Then Welcome message with correct text is shown