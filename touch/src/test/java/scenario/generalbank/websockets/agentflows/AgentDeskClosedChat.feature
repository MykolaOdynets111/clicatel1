@no_widget
@orca_api
@Regression
Feature: Closed chats :: Chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2015")
  Scenario: CD:: Agent Desk :: Verify if agent can view visual indicators in the conversation area: AGENT_RECEIVE_CHAT; FLAG_CHAT; UNFLAG_CHAT; CLOSE_CHAT
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send Hello message by ORCA
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    Then Agent checks visual indicator with text This chat has been assigned to GBD Main is shown during 2 seconds
    When Agent click 'Flag chat' button
    Then Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    When Agent click 'Unflag chat' button
    Then Agent checks visual indicator with text This chat has been un-flagged by GBD Main is shown during 2 seconds
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    Then Agent checks visual indicator with text Chat closed by GBD Main is shown during 2 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1517")
  Scenario: CD :: Agent Desk :: Closed Chat :: Notes :: Verify that an agent can add notes to closed chats
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send Hello message by ORCA
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    And Agent adds a note text Live Chat Test Note Message with Jira link https://livechatdummy.com and Ticket Number 662210
    Then Agent sees note text Live Chat Test Note Message with Jira link https://livechatdummy.com/ and Ticket Number 662210

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2949")
  Scenario: CD :: Agent Desk :: Live Chat :: Verify that chat gets removed from roster menu when agent closed chat

    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    Then Agent has new conversation request
    And Agent click on new conversation request from orca
    And Conversation area becomes active with chat to agent user's message

    When Agent closes chat
    Then Agent should not see from user chat in agent desk from orca

    When Agent select "Closed" left menu option
    Then Verify closed chat is present for orca

    When Open closed chat orca
    Then Verify if End Chat message autoresponder message is shown



