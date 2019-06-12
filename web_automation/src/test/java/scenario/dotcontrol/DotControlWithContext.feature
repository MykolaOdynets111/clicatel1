#@no_widget
#@dot_control
#@start_server
#Feature: Creating .Control integration and sending messages from user with context
#
#  Scenario: Sending user's data in init call
#    Given Create .Control integration for General Bank Demo tenant
#    Given I login as agent of General Bank Demo
#    When Send parameterized init call with context correct response is returned
#    And Send chat to agent message for .Control
#    Then Agent has new conversation request from dotcontrol user
#    When Agent click on new conversation request from dotcontrol
##    Then Correct dotcontrol client details are shown
##    Then Correct dotcontrol client details sent via init call are shown