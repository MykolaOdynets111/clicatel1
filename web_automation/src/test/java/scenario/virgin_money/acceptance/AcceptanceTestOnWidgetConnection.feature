@acceptance
Feature: MASTER CHECK: Widget Connection

  Scenario: Verify if widget is connected and user is able to proceed
    Given User select Virgin Money tenant
    And Click chat icon
    Then Widget is connected