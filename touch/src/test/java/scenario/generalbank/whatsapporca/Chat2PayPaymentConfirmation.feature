@no_widget
Feature: Chat2Pay ::Chat2PayPaymentConfirmation

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105082")
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify that the agent gets an options to mark the chat as pending when agent tries to close a chat where payment is not conluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When When Agent open c2p form
    And Agent fill c2p form with orderNumber 43333, price 9 and send
    When Agent sees C2P link with 43333 number in chat body
    And Agent click "End chat" button without window loading
    When Agent get "Cannot close chat" notification modal open
    And Agent click on option "Move to Pending" from notification
    When Agent select "Pending" left menu option
    Then Agent has new conversation request from orca user