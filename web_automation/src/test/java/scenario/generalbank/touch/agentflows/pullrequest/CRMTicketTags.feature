@agent_feedback
Feature: Interaction with CRM tickets

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo

  Scenario: All tie tags available for the test tenant in the drop down
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then All tags for tenant is available in the dropdown
    When Agent click 'Close chat' button

  Scenario: All tie tags available for the test tenant in the drop down after reopen "End chat" window
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Cancel' button
    Then Conversation area contains connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then All tags for tenant is available in the dropdown
    When Agent click 'Close chat' button


  Scenario: Agent can search and selected tag, selected tag is added into the Tags field, save CRM ticket and tag is saved
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then Agent can search tag and select tag, selected tag added in tags field
    Then Agent type Note:CheckTags, Link:CheckTags, Number:12345 for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information

  Scenario: Agent can select 2 tags and save the ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then Agent add 2 tag
    Then Agent type Note:"Note from automation test)", Link:"Note text Link", Number:"12345" for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information

  Scenario: Agent can select, then unselect tag and the tag is not saved in created CRM ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then Agent add 2 tag
    Then Agent delete all tags
    Then Agent type Note:"Note from automation test)", Link:"Note text Link", Number:"12345" for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information


