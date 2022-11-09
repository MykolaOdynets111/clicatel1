@orca_api
@start_orca_server
@Regression
Feature: Canceling chat transfer

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2465")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify the agent is able to cancel the transferred chat
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
    When First Agent click 'Cancel transfer' button
    Then Second agent has not see incoming transfer pop-up
    Then Conversation area becomes active with connect to agent user's message in it for first agent
    When Agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds

