@Regression
@orca_api
Feature: Transferring flag chats

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1849")
  Scenario: CD:: SMS:: Transfer chat::Verify if Agent can't see "Transfer chat" button for flagged chat on the Pending tab

    Given I login as agent of Automation Bot
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent click 'Pending' chat button
    Then Agent receives pending message with orca user name
    When Agent select "Pending" left menu option
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    When Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    Then Agent checks "transfer chat" icon disappeared on the chat desk
    When Agent click 'Unflag chat' button
    Then Agent checks "transfer chat" icon appeared on the chat desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1807")
  @start_orca_server
  Scenario: CD::SMS::Flag Chat::Verify "Transfer chat" button is not shown on chat desk for flagged chat

    Given I login as agent of Automation Bot
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent click 'Flag chat' button
    And Agent sees 'flag' icon in this chat
    Then Agent checks "transfer chat" icon disappeared on the chat desk
    When Agent click 'Unflag chat' button
    Then Agent checks "transfer chat" icon appeared on the chat desk
    When I login as second agent of Automation Bot
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, orca and following user's message: 'connect to Support'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from orca user
    And Agent should not see from user chat in agent desk from orca
    When Second agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message in it for Second agent
    When Second agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds