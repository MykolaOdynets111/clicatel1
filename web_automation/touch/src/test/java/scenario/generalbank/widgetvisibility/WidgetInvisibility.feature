@widget_visibility
Feature: Widget invisibility

  There should be a possibility to disable widget

  Scenario: Widget is invisible when all hours visibility are selected but excluded from User's country
    Given Following widget time availability for General Bank Demo is selected: all week
    And Widget is turned off for my country only
    When User select General Bank Demo tenant
    Then Chat icon is not visible

  Scenario: Widget is invisible when all hours visibility are selected but excluded from User's territory
    Given Following widget time availability for General Bank Demo is selected: all week
    And Widget is disabled for Europe territory but is enabled for Ukraine User's country
    When User select General Bank Demo tenant
    Then Chat icon is not visible

  Scenario: Widget is invisible when all territories visibility are selected but out of widget hours
    Given Following widget time availability for General Bank Demo is selected: wrong hours
    And All territories territory availability is applied
    When User select General Bank Demo tenant
    Then Chat icon is not visible