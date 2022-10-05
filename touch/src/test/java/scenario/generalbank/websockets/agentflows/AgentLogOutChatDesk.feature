@orca_api
Feature: Agent emoticons

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2910")
  @Regression
  @no_widget
  Scenario: CD :: Agent Desk :: Verify that the agent can Log out from the Agent Desk
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    When I login as agent of General Bank Demo
    Then Agent is logged out from the Agent Desk