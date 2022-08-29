@agent_info
@no_widget
Feature: Agent information should be present in the info popup

  Scenario: Viewing agent's details in information popup
    When I login as agent of General Bank Demo
    Then Icon and agent first name of General Bank Demo should be present
    When I click icon with agent initials
    Then I see agent of General Bank Demo info
    When Agent clicks "Profile Settings" button
    Then Agent of General Bank Demo info details is shown in profile window

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-114789")
  Scenario: Agent Desk :: Verify the agent name and date are displayed in the visual indicator in agent chat window
    Given I login as agent of Automation
    When Setup ORCA whatsapp integration for Automation tenant
    And Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Supervisor Desk in submenu
#    ToDo added wait till issue with filter will be resolved.
    And Wait for 10 second
    And Agent search chat ORCA on Supervisor desk
    Then ORCA request is shown on Supervisor Desk Live page
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Supervisor Desk Live chat header display "Auto Main" Agent name
    And Supervisor Desk Live chat header display date