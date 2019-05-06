#@agent_mode
#@agent_session_capacity
#Feature: Chat console: Waiting customers
#
#  Scenario: Waiting customers counter in case agent unavailability
#    Given Set session capacity to 0 for Automation Bot tenant
#    Given I login as second agent of Automation Bot
#    Given User select Automation Bot tenant
#    And Click chat icon
#    And User enter connect to agent into widget input field
#    Given I open portal
#    And Login into portal as an admin of Automation Bot account
#    When I select Touch in left menu and Chat console in submenu
#    Given Set session capacity to 25 for Automation Bot tenant
#
#
#
#
