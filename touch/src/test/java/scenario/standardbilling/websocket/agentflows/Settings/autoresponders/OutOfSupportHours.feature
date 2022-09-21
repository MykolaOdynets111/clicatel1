@no_widget
@start_orca_server
Feature: Dashboard: Settings: Auto Responder

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1915")
  @Regression
  @agent_support_hours
  Scenario: CD :: Dashboard :: Settings :: Auto Responder :: Verify that customers receives the 'out of support hours' message when tries to reach agent out of business hours

    Given Setup ORCA whatsapp integration for Standard Billing tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds