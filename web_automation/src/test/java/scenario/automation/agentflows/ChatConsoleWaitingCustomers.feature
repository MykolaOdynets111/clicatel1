Feature: Chat console: Waiting customers

  Background:
    Given User select Automation Bot tenant
    And Click chat icon
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Chat console in submenu
    And Save Customers waiting for response pre-test widget value

  @agent_session_capacity
  Scenario: Waiting customers counter in case no session capacity
    Given Set session capacity to 0 for Automation Bot tenant
    Given I login as second agent of Automation Bot
    And User enter connect to agent into widget input field
    Then User have to receive 'agents_away' text response for his 'connect to agent' input
    Then Customers waiting for response widget value increased on 1


  @no_chatdesk
  Scenario: Waiting customers counter in case no agents online
    And User enter connect to agent into widget input field
    Then Text response that contains "agents_away" is shown
    Then Customers waiting for response widget value increased on 1


  @second_agent_availability
  Scenario: Waiting customers counter in case agent not available
    Given I login as second agent of Automation Bot
    And Second agent changes status to: Unavailable
    And User enter connect to agent into widget input field
    Then User have to receive 'agents_away' text response for his 'connect to agent' input
    Then Customers waiting for response widget value increased on 1





