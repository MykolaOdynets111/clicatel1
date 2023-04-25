@no_widget
@ProdScenario
Feature: Whatsapp Appium :: Chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-13084")
  Scenario: ChatDesk:: The header should have chatting using Appium whatsapp

    Given I login as Agent of WhatsAppFullFlowTenant
    When Setup appium whatsapp integration for orca1demo tenant
    And Send Test message 1 from User message by Appium Whatsapp
    And Agent has new WhatsAppConversation request
    And Agent click on new conversation request from WhatsAppConversation
    Then Conversation area becomes active with Test message 1 from User user's message
    And Agent should see whatsapp integration icon in left menu with chat
    And Agent should see whatsapp icon in active chat header
    When Agent send Test message 1 from Agent message
    Then Check received Test message 1 from Agent message in Appium Whatsapp
    When Send Test message 2 from User message by Appium Whatsapp
    Then Conversation area becomes active with Test message 2 from User user's message
    When Agent send Test message 2 from Agent message
    Then Check received Test message 2 from Agent message in Appium Whatsapp
    And User closes whatsapp integration
