@no_widget
@Regression
Feature: CD :: Chat Desk :: Live Chat :: Chat Transfer

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2295")
  @setting_changes
  @orca_api
  Scenario: CD :: Chat Desk :: Live Chat :: Chat Transfer :: Verify the agent with max available chats is not displayed in transfer pop-up

    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And maxChatsPerAgent tenant feature is set to 1 for General Bank Demo
    When I login as second agent of General Bank Demo
    And Send 1 messages chat to agent by ORCA
    When Second agent click on new conversation request from touch
    When Second agent click on 'Transfer' chat
    Then Transfer chat pop up appears for Second agent
    When Second agent open 'Transfer to' drop down
    Then Second agent should not see first agent in a transfer pop-up agents dropdown
    And Close Transferring window for Second agent