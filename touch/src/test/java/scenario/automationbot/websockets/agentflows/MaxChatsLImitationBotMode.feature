@no_widget
@Regression
@setting_changes
Feature: Max chat functionality for Bot mode tenant

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2546")
  @start_orca_server
  @orca_api
  Scenario: Verify chat limitation feature for Bot mode tenant

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And maxChatsPerAgent tenant feature is set to 1 for Automation Bot
    When I login as agent of Automation Bot
    And Send 2 messages chat to agent by ORCA
    Then Verify agent has 1 conversation requests from Whatsapp integration

    When maxChatsPerAgent tenant feature is set to 50 for Automation Bot
    And Send 2 messages chat to agent by ORCA
    Then Verify agent has 4 conversation requests from Whatsapp integration
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Verify Orca returns Connecting Agent message (Social Channels) autoresponder during 40 seconds for agent