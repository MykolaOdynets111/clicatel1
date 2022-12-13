@no_widget
@orca_api
@Regression
Feature: Pending chats :: Chatdesk

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send 2 messages pending chats check by ORCA
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    And Agent click 'Pending On' chat button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2092")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify that flagged pending chat can become a flagged live chat when agent unmarks a pending chat
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Agent click 'Pending On' chat button
    Then Agent click on new conversation request from orca
    And Agent checks visual indicator with text This chat has been marked as live chat by GBD Main is shown during 2 seconds
    And Agent click 'Unflag chat' button
    And Agent checks visual indicator with text This chat has been un-flagged by GBD Main is shown during 2 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2028")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify if flagged pending chat can become a flagged live chat when a pending chat becomes a live chat after User message
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Send send me to live section message by ORCA
    And Agent gets pending to live chat dialog with header Chat moved to live
    And Agent clicks on go to chat button
    And Agent checks visual indicator with text The user has responded, the chat has now been marked as live chat is shown during 2 seconds
    And Agent click 'Unflag chat' button
    And Agent checks visual indicator with text This chat has been un-flagged by GBD Main is shown during 2 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2023")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify if Agent can filter flagged chat on the Pending tab
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Agent filter closed chats with no channel, no sentiment and flagged is true
    Then Agent sees 'flag' icon in this chat
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    Then Agent click 'Pending On' chat button
    And Agent click on new conversation request from orca
    And Agent click 'Unflag chat' button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2008")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify that Agent can't close the Pending chat with flag mark
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    Then Agent hover over "Exit chat" button and see You do not have the ability to close the chat when it has been flagged message
    When Agent click 'Unflag chat' button
    And Agent click 'Pending On' chat button
    And Wait for 1 second
    Then Agent checks current tab selected in left menu is Live tab

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1990")
  Scenario: CD :: Agent Desk :: Pending Chat :: Transfer chat :: Verify if Agent can't see "Transfer chat" button for flagged chat on the Pending tab
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Wait for 2 second
    Then Agent checks "transfer chat" icon disappeared on the chat desk
    When Agent click 'Unflag chat' button
    And Wait for 2 second
    Then Agent checks "transfer chat" icon appeared on the chat desk
    And Agent click 'Pending On' chat button
    And Wait for 1 second
    Then Agent checks current tab selected in left menu is Live tab

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2022")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify if chat is moved from Pending to Live chat due to customer activity, it is considered as new activity in Live chat
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Send send me to live section message by ORCA
    And Agent gets pending to live chat dialog with header Chat moved to live
    And Agent clicks on go to chat button
    And Agent click on new conversation request from orca
    Then Agent checks as per sorting preference selected, the chat is at 1 index of chats section for orca user
    And Agent click 'Unflag chat' button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1977")
  Scenario: CD :: Agent Desk :: Pending Chat :: Notes :: Verify if agent can add notes to Pending Chat
    Then Agent receives pending message with orca user name
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent adds a note text Live Chat Test Note Message with Jira link https://livechatdummy.com and Ticket Number 662210
    Then Agent sees note text Live Chat Test Note Message with Jira link https://livechatdummy.com/ and Ticket Number 662210
    And Agent click 'Pending On' chat button

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1975")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify if Agent can unflag "Pending chat"
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    And Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    And Agent checks visual indicator with text This chat has been flagged by GBD Main is shown during 2 seconds
    And Agent click 'Unflag chat' button
    Then Agent checks visual indicator with text This chat has been un-flagged by GBD Main is shown during 2 seconds
    When Agent click 'Pending On' chat button
    Then Agent click on new conversation request from orca
    And Agent checks visual indicator with text This chat has been marked as live chat by GBD Main is shown during 2 seconds