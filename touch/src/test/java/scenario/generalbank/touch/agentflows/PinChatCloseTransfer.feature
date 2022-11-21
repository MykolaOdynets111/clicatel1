@agent_mode
@orca_api
@start_orca_server
Feature: Flagged chat is disabled to close and to transfer

  Verification of basic pin chat functionality

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message


  @Issue("https://jira.clickatell.com/browse/TPORT-26904")
  Scenario: Agent receives error message when tries to close the flagged chat
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    When Agent click "End chat" button without window loading
    Then Agent receives 'pin' error message
    When Agent click 'Unflag chat' button
    Then Agent do not see 'flag' icon in this chat
    Then Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to agent' input

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2922")
  @Regression
  Scenario: CD :: Agent Desk :: Live Chat :: Flag Chat :: Verify "Transfer chat" button is not shown on chat desk for flagged chat
    When Agent click 'Flag chat' button
    Then Agent can not click 'Transfer chat' button
    Then Agent click 'Unflag chat' button
    Given I login as second agent of General Bank Demo
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from orca user
    And Agent should not see from user chat in agent desk from orca
    When Second agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message in it for Second agent
    When Second agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds

