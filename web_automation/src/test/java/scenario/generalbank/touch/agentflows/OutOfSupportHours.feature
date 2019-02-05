@skip_for_demo1
@agent_mode
Feature: Chat handling in out of support hours


  @agent_support_hours
  Scenario: Verify overnight ticket is created on chatdesk for Agent mode tenant
    Given I login as agent of Automation
    Given Set agent support hours with day shift
    Given User select Automation tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    And Agent sees 'overnight' icon in this chat
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    And Conversation area contains out_of_support_hours to user message

  @agent_support_hours
  Scenario: Verify overnight ticket is created on chatdesk for Bot mode tenant
    Given I login as agent of Automation Bot
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    And Agent sees 'overnight' icon in this chat
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with chat to agent user's message
    And Message that it is overnight ticket is shown for Agent
    And Conversation area contains out_of_support_hours to user message

  @agent_support_hours
  Scenario: Verify out_of_support_hours message shown for Agent mode tenant user
    Given I login as agent of Automation
    Given Set agent support hours with day shift
    Given User select Automation tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User have to receive 'out_of_support_hours' text response for his 'chat to agent' input

  @agent_support_hours
  Scenario: Verify out_of_support_hours message shown for Bot mode tenant user
    Given I login as agent of Automation Bot
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User have to receive 'out_of_support_hours' text response for his 'chat to agent' input

  @agent_support_hours
  Scenario: Verify message shown for Agent mode tenant user
    Given I login as agent of Automation
    Given Set agent support hours with day shift
    Given User select Automation tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User have to receive 'out_of_support_hours' text response for his 'chat to agent' input
    When Set agent support hours for all week
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Overnight ticket is removed from agent chatdesk
    When Agent click on new conversation request from touch
    And Conversation area contains out_of_support_hours to user message
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'chat to support' input

  @agent_support_hours
  Scenario: Verify message shown for Bot mode tenant user
    Given I login as agent of Automation Bot
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then User have to receive 'out_of_support_hours' text response for his 'chat to agent' input
    When Set agent support hours for all week
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Overnight ticket is removed from agent chatdesk
    When Agent click on new conversation request from touch
    And Conversation area contains out_of_support_hours to user message
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'chat to support' input



