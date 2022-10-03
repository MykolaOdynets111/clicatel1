@no_chatdesk
@Regression
Feature: Welcome flow: bot mode

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2497")
  @start_orca_server
  Scenario: CD :: Dashboard :: Configure :: Settings ::
  Auto Responders :: Verify the welcome message text changing for Bot mode tenant

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And Taf Connecting Agent message (Social Channels) is set to true for Automation Bot tenant
    And I login as agent of Automation Bot
    And Send connect to agent message by ORCA
    When Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Verify message Connecting Agent message (Social Channels) is present for agent during 40 seconds
