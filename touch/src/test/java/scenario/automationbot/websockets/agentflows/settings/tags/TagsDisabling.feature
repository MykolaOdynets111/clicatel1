@no_chatdesk
@Regression
Feature: CD :: Dashboard :: Settings :: Chat Tags

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2847")
  @orca_api
  Scenario: CD :: Dashboard :: Settings :: Chat Tags :: Verify that agent is not able to select tags that are disabled

    Given I login as Agent of Automation Bot
    And Set false for Auto_Tag tag status
    And Setup ORCA Whatsapp integration for Automation Bot tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Agent click "End chat" button
    Then End chat popup for Agent should be opened
    Then Agent does not see the disabled tag Auto_Tag
    #clean up
    And Set true for Auto_Tag tag status
