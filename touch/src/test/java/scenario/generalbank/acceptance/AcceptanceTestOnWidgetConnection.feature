@acceptance
Feature: MASTER CHECK: Widget Connection

  Scenario: Verify if widget is connected and user is able to proceed
    Given Widget for General Bank Demo is turned on
    Given User select General Bank Demo tenant
    And Click chat icon
    Then Widget is connected