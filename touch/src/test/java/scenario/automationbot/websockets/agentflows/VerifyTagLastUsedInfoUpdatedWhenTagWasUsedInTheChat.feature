@no_widget
@Regression
Feature: Dashboard: Settings: Chat Tags : Last Used info

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2764")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Chat Tags ::
  Verify if tag last used updates when agent adds tag to note when closing at chat

    Given I login as Agent of Automation Bot
    And Setup ORCA Whatsapp integration for Automation Bot tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent click "End chat" button
    Then End chat popup for Agent should be opened
    And Agent select Auto_Tag_2 tag
    And Agent click 'Close chat' button
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Chat Tags page
    And Verify Last Used column data is updated for tag Auto_Tag_2