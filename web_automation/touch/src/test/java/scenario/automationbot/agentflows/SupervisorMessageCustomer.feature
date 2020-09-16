@agent_support_hours
@no_widget
@dot_control
Feature: Supervisor desk

#  Background:
#    And User select Automation Bot tenant
#    Given Set agent support hours with day shift
#    Given I open portal
#    And Login into portal as an admin of Automation Bot account
#    When I select Touch in left menu and Supervisor Desk in submenu
#    And Click chat icon

  Background:
    Given Create .Control integration for Automation Bot and adapter: fbmsg
    Given Set agent support hours with day shift
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7404")
  Scenario: Supervisor desk:: verify if Supervisor is able to send message to customer via message customer option
#    When User enter connect to Support into widget input field
#    Then User should see 'out_of_support_hours' text response for his 'connect to Support' input
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    When Agent select dotcontrol ticket
    And Click on Massage Customer button
    Then Message Customer Window is opened
    When Supervisor send hello to agent trough adapter:fbmsg chanel
#    Then User should see 'hello' text response for his 'connect to Support' input