Feature: Agent emoticons

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1669")
  Scenario: Agent should be able to send emoticon to user
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    When Agent click on emoji icon
    And Agent response with emoticon to User
    Then User should see emoji response for his 'personal info' input
    And Sent emoji is displayed on chatdesk
