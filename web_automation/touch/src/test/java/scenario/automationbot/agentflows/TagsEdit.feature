@agent_feedback
Feature: Edit tag name

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation bot
    Given User select Automation bot tenant
    Given I open portal
    And Login into portal as an admin of Automation bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page

  Scenario: verify when a supervisor edits a tag that the chats/tickets associated with the tag also be edited and has the new name.
    And Create chat tag
    And I login as second agent of Automation bot
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Second agent has new conversation request
    When Second agent click on new conversation request from touch
    When Second agent click "End chat" button
    Then End chat popup for second agent should be opened
    Then Second agent select precreated tag
    Then Second agent type Note:CheckTTagEdit, Link:TagEdit, Number:14544 for CRM ticket
    When Second agent click 'Close chat' button
    Then Second agent should not see from user chat in agent desk
    Then CRM ticket is created on backend with correct information
    When Update chat tag
    Then CRM ticket is created on backend with correct information