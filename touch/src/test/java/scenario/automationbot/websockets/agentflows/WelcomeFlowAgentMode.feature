@camunda
Feature: Welcome flow: agent mode

  Scenario: Welcome message disabling for Agent mode tenant
    Given Taf welcome_message is set to false for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    Then Welcome message is not shown

  Scenario: Welcome message text changing for Agent mode tenant
    Given Taf welcome_message is set to true for Automation Bot tenant
    Given User select Automation tenant
    Given Taf welcome_message message text is updated for Automation Bot tenant
    And Click chat icon
    Then Welcome message with correct text is shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2477")
  @no_chatdesk
  @Regression
  @start_orca_server
  @skip
  Scenario: CD :: Dashboard :: Settings :: Reset to default Welcome message auto responder (Agent mode)
    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And Taf Connecting Agent message (Social Channels) is set to true for Automation Bot tenant
    And Taf Connecting Agent message (Social Channels) message text is updated for Automation Bot tenant
    And I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Auto Responders page
    And Wait for auto responders page to load
    And Agent click expand arrow for Connecting Agent message (Social Channels) auto responder
    And Click "Reset to default" button for Connecting Agent message (Social Channels) auto responder
    When Connecting Agent message (Social Channels) is reset on backend
    And Send connect to agent message by ORCA
    Then Verify Orca returns Connecting Agent message (Social Channels) autoresponder during 40 seconds for agent