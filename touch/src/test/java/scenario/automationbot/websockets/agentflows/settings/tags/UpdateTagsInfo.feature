@no_widget
@Regression
Feature: Dashboard: Settings: Chat Tags : Update tags info

    @TestCaseId("https://jira.clickatell.com/browse/CCD-2764")
    @orca_api
    @setting_changes
    Scenario: CD :: Whatsapp :: Close Chat :: Verify if tag Usage updates when Agent adds tag to Note when closing at chat

    Given I login as Agent of Automation Bot
    And Setup ORCA Whatsapp integration for Automation Bot tenant
    And agentFeedback tenant feature is set to true for Automation Bot
    And Off whatsapp survey configuration for Automation Bot
    When Send connect to agent message by ORCA
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent click "End chat" button
    Then End chat popup for Agent should be opened
    And Agent select Auto_Tag tag
    And Agent click 'Close chat' button
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Chat Tags page
    And Verify Last Used column data is updated for tag Auto_Tag

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2779")
  @orca_api
  @setting_changes
    Scenario: CD :: SMS :: Close Chat :: Verify if tag Usage updates when Agent adds tag to Note when closing at chat

    Given I login as Agent of Automation Bot
    And agentFeedback tenant feature is set to true for Automation Bot
    And Setup ORCA SMS integration for Automation Bot tenant
    And And Off sms survey configuration for Automation Bot
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from sms user
    When Agent click on new conversation request from sms
    And Agent click "End chat" button
    And End chat popup for Agent should be opened
    And Agent select Auto_Tag_3 tag
    And Agent click 'Close chat' button
    Then Second Agent should not see from user chat in agent desk from ORCA
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Chat Tags page
    Then Verify Last Used column data is updated for tag Auto_Tag_3

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2821")
  Scenario: CD :: Dashboard :: Configure :: Settings :: Chat Tags :: Verify if the edited tags are not delinked from linked chats

    Given I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page
    Then Rename tag Auto_Tag_2 to Auto_Tag_22 and verify column Usage value is unchanged
#    clean up
    And Rename Auto_Tag_22 tag to Auto_Tag_2