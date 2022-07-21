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
    When When Agent open c2p form
    And Agent fill c2p form with orderNumber 45545, price 10 and send
    When Wait for 360 second
    Then Agent get 'payment link expired' update is sent to agent desk by C2P

