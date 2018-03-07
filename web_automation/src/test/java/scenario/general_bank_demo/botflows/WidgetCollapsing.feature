@collapsing
Feature: User should be able to collapse widget

  Scenario: Widget collapsing
    Given User select General Bank Demo tenant
    And Click chat icon
    When User enter hello into widget input field
    And User click close chat button
    Then Widget is collapsed