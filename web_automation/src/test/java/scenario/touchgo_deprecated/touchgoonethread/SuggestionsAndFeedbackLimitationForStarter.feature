Feature: Agent Assist and Feedback limitation for tenant with Starter plan

  Background:
    Given I login as agent of Starter AQA
    Given User select Starter AQA tenant
    And Click chat icon

  Scenario: Agent assist and Feedback should be turned off for Starter
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
    When User enter how to check account balance? into widget input field
    Then Conversation area contains how to check account balance? user's message
    And Suggestions are not shown
    When Agent click "End chat" button
    Then Agent Feedback popup is not shown
#    ToDo: Temporary disabling the verification because curretly deployed TPLAT version on qa env has outdated taf messages API
#    Then User have to receive 'exit' text response for his 'how to check account balance?' input
