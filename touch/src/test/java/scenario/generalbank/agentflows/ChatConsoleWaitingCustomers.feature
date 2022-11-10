@orca_api
@no_widget
@Regression
Feature: Chat console: Waiting customers

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Save Chats waiting in queue counter value

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2674")
  @setting_changes
  @skip
  Scenario: Dashboard:: Chats waiting in a queue increases in case no session capacity
    Given I login as second agent of General Bank Demo
#    And User enter connect to agent into widget input field
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Chats waiting in queue widget value increased on 1

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2586")
  Scenario: CD::Dashboards:: Chats waiting in a queue increase in case no agents online

    When Setup ORCA whatsapp integration for General Bank Demo tenant
    And Send connect to agent message by ORCA
    And Admin refreshes the page
    Then Chats waiting in queue widget value increased on 1

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2508")
  @second_agent_availability
  Scenario: CD:: Dashboard:: Chats waiting in a queue increase in case agent not available
    And admin changes status to: Unavailable
    And I login as second agent of General Bank Demo
    When Chats waiting in queue widget value set to 0
    And Save Chats waiting in queue counter value
    And Second agent changes status to: Unavailable
    And Setup ORCA whatsapp integration for General Bank Demo tenant
    And Send connect to agent message by ORCA
    Then Chats waiting in queue widget value increased on 1