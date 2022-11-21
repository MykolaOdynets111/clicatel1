@agent_availability
@start_orca_server
@orca_api
@Regression
Feature: Agent availability

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2882")
  Scenario: CD:: Agent Desk:: Changing agent's availability is displayed correctly
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
    When Agent responds with hello to User
    And Agent click "End chat" button
    Then Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk from orca
    And Agent changes status to: Unavailable
    When Send connect to Support message by ORCA
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds for agent
    Then Agent should not see from user chat in agent desk from orca
    When Agent changes status to: Available
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
    And Agent clear input and send a new message how can I help you?
    And Verify Orca returns how can I help you? response during 40 seconds