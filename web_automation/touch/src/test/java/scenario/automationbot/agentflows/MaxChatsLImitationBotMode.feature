@agent_session_capacity
Feature: Max chat functionality for Bot mode tenant

  Scenario: Verify chat limitation feature for Bot mode tenant
    Given Set session capacity to 0 for Automation Bot tenant
    Given I login as agent of Automation Bot
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then User have to receive 'agents_away' text response as a 1 response for his 'chat to agent' input
    Given Set session capacity to 100 for Automation Bot tenant
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'chat to agent' input




