Feature: Agent Assist and Feedback limitation for tenant with Standard plan

  Background:
    Given New tenant is successfully created
    Given New tenant is successfully upgraded
    Given I login as agent of Standard AQA
    Given User select Standard AQA tenant
    And Click chat icon

  Scenario: Agent assist should be turned on and Feedback should be turned off for Standard Touch Go plan
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
    When User enter what is your support hours? into widget input field
#    Then There is correct suggestion shown on user message "what is your support hours?"
#    When Agent click send button
#    Then User have to receive '' text response for his 'what is your support hours?' input
    When Agent click "End chat" button
    Then Agent Feedback popup is not shown
#    ToDo: Temporary disabling the verification because curretly deployed TPLAT version on qa env has outdated taf messages API
#    Then User have to receive 'exit' text response for his 'what is your support hours?' input
