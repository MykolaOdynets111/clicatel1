@no_widget
@Regression
Feature: Whatsapp Appium :: Chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-13084")
  Scenario: ChatDesk:: The header should have chatting using Appium whatsapp


    Given I login as Agent of WhatsAppFullFlowTenant

    When Setup appium whatsapp integration for orca1demo tenant

    And Send some new message message by Appium Whatsapp
    And Agent has new WhatsAppConversation request
    And Agent click on new conversation request from WhatsAppConversation
    And Conversation area becomes active with 1 user's message
    And Agent should see whatsapp integration icon in left menu with chat
    And Agent should see whatsapp icon in active chat header

    When Agent send Hello message
    Then Check received Hello message in Appium Whatsapp

    And User closes whatsapp integration
