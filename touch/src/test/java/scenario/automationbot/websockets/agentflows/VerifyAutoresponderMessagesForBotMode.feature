@no_chatdesk
@Regression
@orca_api
@start_orca_server
Feature: Auto responder massages: bot mode

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2497")
  Scenario: CD :: Dashboard :: Configure :: Settings :: Auto Responders :: Verify the welcome message text changing for Bot mode tenant

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And I login as agent of Automation Bot
    And Taf Connecting Agent message (Social Channels) is set to true for Automation Bot tenant
    When Send connect to agent message by ORCA
    Then Verify Orca returns Connecting Agent message (Social Channels) autoresponder during 40 seconds for agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2364")
  @support_hours
  Scenario: CD:: Dashboard:: Auto Responder:: Busy message enabling and editing for Bot mode tenant

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And Taf Agent Busy message is set to true for Automation Bot tenant
    And Set agent support hours for all week
    When Send connect to agent message by ORCA
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds for agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2348")
  @support_hours
  Scenario: CD:: Dashboard:: out_of_support_hours Agent message enabling and editing for Bot mode tenant

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And Taf Out of Support Hours message is set to true for Automation Bot tenant
    And Taf Out of Support Hours message message text is updated for Automation Bot tenant
    And Set agent support hours with day shift
    And Send connect to agent message by ORCA
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds for agent