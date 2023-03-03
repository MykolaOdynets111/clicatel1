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