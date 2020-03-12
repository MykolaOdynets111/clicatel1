@agent_mode
Feature: Chat handling in out of support hours for for Bot mode tenant

  Background:
    Given Taf out_of_support_hours is set to true for Automation Bot tenant
    Given I login as agent of Automation Bot
    And Agent select "Tickets" left menu option
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon


  @agent_support_hours
  Scenario: Verify overnight ticket is created on chatdesk for Bot mode tenant
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    And Agent sees 'overnight' icon in this chat
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    And Conversation area contains out_of_support_hours to user message

  @agent_support_hours
  Scenario: Verify out_of_support_hours message shown for Bot mode tenant user
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User should see 'out_of_support_hours' text response for his 'chat to agent' input

  @agent_support_hours
  Scenario: Overnight ticket is replaced by active chat when support ours is valid (Bot mode tenant)
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User should see 'out_of_support_hours' text response for his 'chat to agent' input
    When Set agent support hours for all week
    When User enter chat to support into widget input field
    And Overnight ticket is removed from agent chatdesk
    And Agent select "Live Chats" left menu option
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Conversation area contains out_of_support_hours to user message
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'chat to support' input



