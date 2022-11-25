@no_widget
@Regression
Feature: CD :: Chat Desk :: Live Chat :: Chat Transfer

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2295")
  @setting_changes
  @orca_api
  Scenario: CD :: Chat Desk :: Live Chat :: Chat Transfer :: Verify the agent with max available chats is not displayed in transfer pop-up

    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And maxChatsPerAgent tenant feature is set to 1 for General Bank Demo
    When I login as second agent of General Bank Demo
    And Send 1 messages chat to agent by ORCA
    When Second agent click on new conversation request from orca
    When Second agent click on 'Transfer' chat
    Then Transfer chat pop up appears for Second agent
    When Second agent open 'Transfer to' drop down
    Then Second agent should not see first agent in a transfer pop-up agents dropdown
    And Close Transferring window for Second agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2913")
  @setting_changes
  @orca_api
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify that agent is able to transfer chat to other available Agents

    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message

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
  @orca_api
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if transfer were rejected, "Transfer rejected" should be shown in roster view

    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message

    Given I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Reject transfer" button
    And Chat from orca channel is present in the Live Chat list

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2738")

  @setting_changes
  @orca_api
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if chat transfer is accepted, "Successful transfer" should be shown in roster view

    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message

    Given I login as second agent of General Bank Demo
    When Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    When Second agent click "Accept transfer" button
    And Chat from orca channel is absent in chats list