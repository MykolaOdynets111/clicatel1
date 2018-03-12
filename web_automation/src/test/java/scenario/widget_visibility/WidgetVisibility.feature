#@widget_visibility
#Feature: Widget visibility
#
#  There should be a possibility to enable / disable widget
#
#  Scenario: Widget is visible when all hours and all territories options are selected
#    Given All week and all territories availability selected for General Bank Demo tenant
#    Given Friday, from 13:00 to 12:00 and all territories availability selected for General Bank Demo tenant
#
#    When User select General Bank Demo tenant
#    Then Chat icon is not visible