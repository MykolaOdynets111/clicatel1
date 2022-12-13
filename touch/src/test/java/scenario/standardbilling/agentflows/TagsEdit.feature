@Regression
Feature: Tags

  Background:
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2781")
  Scenario: CD :: Dashboard :: Settings :: Chat Tags :: Verify if Admin is able to cancel editing an existing tag
    When Create chat tag
    And Click the pencil icon to edit the tag
    Then Cancel editing a tag
    And Existing TagName is not changed

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2837")
  @setting_changes
  Scenario: CD:: Supervisor Desk:: Chat_Tags:: verify when a supervisor edits a tag that the chats/tickets associated with the tag also be edited and has the new name.
    And Create chat tag
    And I login as second agent of Standard Billing
    When Setup ORCA whatsapp integration for Standard Billing tenant
    And agentFeedback tenant feature is set to true for Standard Billing
    And Send connect to agent message by ORCA
    And Second agent has new conversation request from orca user
    And Second agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message in it for Second agent
    And Second agent click "End chat" button
    And End chat popup for second agent should be opened
    And Second agent select precreated tag
    And Second agent click 'Close chat' button
    And Second Agent should not see from user chat in agent desk from orca
    When Update chat tag
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Agent search chat orca on Supervisor desk
    Then WA chat show the correct tag name

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2843")
  Scenario:CD:: Dashboard:: Settings :: Chat Tags:: Verify if a supervisor can create and save tag
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page
    When Create chat tag
    Then Tag is created
