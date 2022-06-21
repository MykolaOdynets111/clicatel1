@no_widget
Feature: HSMTemplate

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118464")
  Scenario: Chatdesk::HSM::Verify if an agent can initiate a chat by sending an HSM template to a WhatsApp via ORCA user
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    And agent click Whatsapp message icon button on the top bar
    And agent fill the customer contact number
    And choose the Template from dropdown
    And insert the Variable type
    Then agent click on send button
    And message delivered to Whatsapp via ORCA as a User
    And whatsapp ORCA user reply on message
    And Agent has new conversation request
    Then Agent click on new conversation
    Then conversation area become active

