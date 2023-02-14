@orca_api
@no_widget
@Regression
@start_orca_server
Feature: Dashboard: Settings: Auto Responder

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1915")
  @support_hours
  Scenario: CD :: Dashboard :: Settings :: Auto Responder :: Verify that customers receives the 'out of support hours' message when tries to reach agent out of business hours

    Given Setup ORCA whatsapp integration for Standard Billing tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds for agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2400")
  Scenario: CD :: Dashboard :: Auto-responder :: Connecting Agent message with Agent name for Social Channels

    Given I login as Agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    Then Set message: hello ${agentName} into: Connecting Agent message (Social Channels) field and verify it's updated
#   clean up
    When Click "Reset to default" button for Connecting Agent message (Social Channels) auto responder
    Then The Connecting Agent message (Social Channels) message was reset

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2498")
  @support_hours
  Scenario: CD :: Dashboard :: Verify out_of_support_hours message shown for Agent mode tenant user

    Given Setup ORCA whatsapp integration for Standard Billing tenant
    And Set agent support hours with day shift
    And Send to agent message by ORCA

    When I login as agent of Standard Billing
    And I select Touch in left menu and Supervisor Desk in submenu
    Then Agent select "Tickets" left menu option
    When Agent search chat orca on Supervisor desk
    And Supervisor clicks on first ticket
    Then Verify if Out of Support Hours message autoresponder message is shown