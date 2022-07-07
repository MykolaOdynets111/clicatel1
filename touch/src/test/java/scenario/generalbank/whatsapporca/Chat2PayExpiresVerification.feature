@no_widget
Feature: Whatsapp ORCA :: Chatdesk


  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-106604")
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify if payment link expires after 5 mins
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment extension Option
    And Agent Click on Chat to Pay popup option button from chat section
    And Agent Click on Send Chat to Pay 1321 and Link 122323 OrderNumber
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    And Send //end message by ORCA
