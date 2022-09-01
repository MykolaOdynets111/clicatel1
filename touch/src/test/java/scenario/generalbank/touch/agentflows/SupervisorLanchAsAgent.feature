
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-41993")
  @Regression
  Scenario: Supervisor desk:: Verify if supervisor can launch as an agent from supervisor desk
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Agent is redirected to Supervisor page
    When Supervisor agent launch as agent
    Then Supervisor agent sees confirmation popup with "Launching agent view as a supervisor will make yourself available to chat to as an agent. Are you sure you want to do this?" message
    And Supervisor agent click launch in confirmation popup
    Then Agent is redirected to chatdesk page