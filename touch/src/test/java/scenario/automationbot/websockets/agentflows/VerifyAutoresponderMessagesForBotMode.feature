@no_chatdesk
@Regression
@orca_api
@start_orca_server
Feature: CD :: Dashboard :: Configure :: Settings :: Auto Responders

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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2538")
  Scenario Outline: CD :: Dashboard :: Changing agent auto responders, save it and check if it saved on backend

    Given I login as Agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    Then Set message: <newMessage> into: <autoresponder> field and verify it's updated
    When Click "Reset to default" button for <autoresponder> auto responder
    Then The <autoresponder> message was reset
    Examples:
      | newMessage      | autoresponder                                        |
      | New message     | Connect Agent message                                |
      | New message     | Directing to Agent (Social Channels)                 |
      | New message     | Session Timeout message (Web Chat)                   |
      | New message     | Web Chat Greeting message                            |
      | New message     | Connecting Agent message                             |
      | New message     | Connecting Agent message (Social Channels)           |
      | New message     | Agent Busy message                                   |
      | New message     | Out of Support Hours message                         |
      | New message     | End Chat message                                     |
      | New message     | Direct Channel message                               |
      | //end or //stop | End Chat and Opt-Out Keywords message (All Channels) |