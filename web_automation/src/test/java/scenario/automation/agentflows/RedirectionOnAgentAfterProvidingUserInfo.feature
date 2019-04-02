@smoke
Feature: User should be asked personal info before redirecting to the agent mode agent

  @agent_mode
  Scenario: Verify if user is able to communicate with agent in "Agent" mode after providing info details
    Given I login as agent of Automation
    Given User select Automation tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Conversation area contains personal info as user's message
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'personal info' input