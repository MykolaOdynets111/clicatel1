@no_widget
@Regression
@orca_api
Feature: CD :: Chat Desk :: Live Chat :: Chat Transferring

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2913")
  @setting_changes
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify that agent is able to transfer chat to other available Agents

    Given I login as second agent of General Bank Demo
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears for Agent
    When Agent open 'Transfer to' drop down
    Then Agent select an second agent in 'Transfer to' drop down
    And Agent complete 'Note' field
    And  Click on 'Transfer' button in pop-up

    And Agent can see 'Transferring chat...' message
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    And Second agent click "Reject transfer" button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2647")
  @setting_changes
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if transfer were rejected, "Transfer rejected" should be shown in roster view

    Given I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Reject transfer" button
    And Chat from orca channel is present for Agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2738")
  @setting_changes
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if chat transfer is accepted, "Successful transfer" should be shown in roster view

    Given I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Accept transfer" button
    And Second Agent should not see from user chat in agent desk from orca

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1271")
  Scenario: CD :: Agent Desk :: Live Chat :: Profile :: Verify that Agent2 can view edited User profile without refresh

    And I login as second agent of General Bank Demo
    And Agent invites GBD Second to conversation via internal comments
    And Agent edits User Profile with location Canada and clicks Save
    And Second Agent select "Tagged" left menu option
    And Second Agent has new conversation request
    And Second Agent click on new conversation request from orca
    Then Second Agent views User profile with no name Canada location no email without refreshing Agent Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2662")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if agent is able to transfer chat via "Transfer chat" button

    Given I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    And Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, orca and following user's message: 'connect to Support'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request

    And Second Agent should not see from user chat in agent desk from orca
    When Second Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message in it for second agent
    And Second agent responds with hello to User

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2961")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer chat :: Verify that agent A gets notification, that chat transfer was accepted by Agent B

    And I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Accept transfer" button
    And Chat from orca channel is present for Second Agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2950")
  Scenario: CD :: Agent Desk :: Live Chat :: Verify that agent should be able to choose available Agent for transferring chat

    And I login as second agent of General Bank Demo
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears for Agent
    When Agent open 'Transfer to' drop down
    Then Agent can see second agent in a transfer pop-up agents dropdown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1488")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer chat :: Verify if agent is able to transfer chats when (s)he has at least one flagged chat

    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    And Send 1 messages chat to agent by ORCA

    And I login as second agent of General Bank Demo
    When Agent click on new conversation request from orca
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Accept transfer" button
    Then Chat from orca channel is present for Second Agent