@orca_api
@Regression
@no_widget
Feature: Agent Details Info Pop Up Actions

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2910")
  Scenario: CD :: Agent Desk :: Verify that the agent can Log out from the Agent Desk
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    When I login as agent of General Bank Demo
    Then Agent is logged out from the Agent Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2451")
  Scenario: CD :: Agent Desk :: Verify viewing agent's details in information popup
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    When I login as agent of General Bank Demo
    Then Agent checks agent name initials are GBD
    When I click icon with GBD initials
    Then Agent checks agent details contain name GBD Main and email touchdemotenant2@gmail.com
    When Admin clicks "Profile Settings" button
    Then Agent views GBD agent first name, Main last name and touchdemotenant2@gmail.com email in Profile Settings