@skip_for_demo1
@agent_mode
@support_hours
Feature: Chat handling in out of support hours for Agent mode tenant

  Background:
    Given I login as agent of Automation
    And Agent select "Tickets" left menu option
    Given Set agent support hours with day shift
    Given User select Automation tenant
    And Click chat icon

  Scenario: Verify overnight ticket is created on chatdesk for Agent mode tenant
    When User enter chat to agent into widget input field
    Then Agent has new ticket request
    And Agent sees 'overnight' icon in this chat
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    And Conversation area contains out_of_support_hours to user message

  Scenario: Verify out_of_support_hours message shown for Agent mode tenant user
    When User enter chat to agent into widget input field
    Then Agent has new ticket request
    Then User should see 'out_of_support_hours' text response for his 'chat to agent' input


  Scenario: Overnight ticket is replaced by active chat when support ours is valid (Agent mode tenant)
    When User enter chat to agent into widget input field
    Then Agent has new ticket request
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
