@agent_feedback
Feature: Interaction with Tags in CRM tickets (agent mode)

  Background:
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    And agentFeedback tenant feature is set to true for Automation Bot
    And User select Automation Bot tenant
    And I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page

  Scenario: All tags available for the test tenant in the drop down
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then All tags for tenant is available in the dropdown

   Scenario: All tags available for the test tenant in the drop down after reopen "Close chat" window
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Cancel' button
    Then Conversation area contains connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then All tags for tenant is available in the dropdown
    When Agent click 'Close chat' button

  @skip
  Scenario: Agent can search and select tag, selected tag is added into the Tags field, save CRM ticket and tag is saved
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent can search tag and select tag, selected tag added in tags field
    Then Agent type Note:CheckTags, Link:http://CheckTags.com, Number:12345 for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then CRM ticket is created on backend with correct information

  @skip
  Scenario: Agent can select 2 tags and save the ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent add 2 tag
    Then Agent type Note:Note from automation test), Link:http://NoteTextLink.com, Number:12345 for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then CRM ticket is created on backend with correct information

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2934")
    @skip
  Scenario: Agent can select, then delete tags and the tags is not saved in created CRM ticket
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    Then Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    #And Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent add 2 tag
    Then Agent delete all tags
    #Then Agent type Note:Note from automation test), Link:http://NoteTextLink.com, Number:12345 for CRM ticket for ORCA
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk from ORCA
    #Then CRM ticket is created on backend with correct information
