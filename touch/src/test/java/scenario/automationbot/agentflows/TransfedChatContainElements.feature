@Regression
@orca_api
Feature: Transferring chat, user info

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2761")
  Scenario: Transfer chat: All required fields in pop-up should be filled. Notification should have user's profile picture, channel and sentiment

    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When I login as second agent of General Bank Demo
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, orca and following user's message: 'connect to agent'
    Then Second Agent receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment for orca
