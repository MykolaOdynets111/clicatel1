@no_widget
Feature: Chat2Pay ::Chat2PayPaymentConfirmation

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118473")
  Scenario: ChatDesk:: Agent Initiate to Live Chat
    Given I login as agent of General Bank Demo
    Then Agent click on Live Customers on dashboard
    Then Admin should see WhatsApp chart in Live Chats by Channel
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment Option
    And Agent Click on Chat to Pay popup option button chat section
    And Agent Click on Send Chat to Pay 1321 and Link 122323 OrderNumber
    And Agent closes the chat from agent desk
    Then Agent should not see from user chat in agent desk
    Then Verify Chat is not closed from the chat panel when agent click on closed chat option
    And Agent notify "Cannot close chat" notification modal open
    And Agent click on option "Move to Pending" from notification
    Then Verify Move to pending chat can be seen in the pending tab