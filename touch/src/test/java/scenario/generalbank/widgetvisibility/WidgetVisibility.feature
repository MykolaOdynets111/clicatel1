@widget_visibility
Feature: Widget visibility

  There should be a possibility to enable widget

  Scenario: Widget is visible when all hours visibility and all territories are selected
#    Given Following widget time availability for General Bank Demo is selected: all week
    Given All territories territory availability is applied for General Bank Demo
    When User select General Bank Demo tenant
    Then Chat icon is visible

#  Scenario: Widget is visible when some specific hours and all territories are selected
#    Given Following widget time availability for General Bank Demo is selected: this day
#    And All territories territory availability is applied
#    When User select General Bank Demo tenant
#    Then Chat icon is visible

  Scenario: Widget is visible when some specific hours and specific country are selected
#    Given Following widget time availability for General Bank Demo is selected: this day
    Given My territory territory availability is applied for General Bank Demo
    When User select General Bank Demo tenant
    Then Chat icon is visible

#  Scenario: Widget is visible when all hours and specific country are selected
#    Given Following widget time availability for General Bank Demo is selected: all week
#    And My territory territory availability is applied
#    When User select General Bank Demo tenant
#    Then Chat icon is visible