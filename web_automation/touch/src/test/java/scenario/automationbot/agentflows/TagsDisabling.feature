@agent_feedback
Feature: Tags

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation bot
    Given User select Automation bot tenant
    Given I open portal
    And Login into portal as an admin of Automation bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page

  Scenario: Verify if agent is not able to select tags that are disabled
    And Create chat tag
    And Wait for 1 second
    #wait added because save tags take time and no spinner was added
    And Disable tag
    When I login as second agent of Automation bot
    And Click chat icon
    And User enter connect to Support into widget input field
    Then Second agent has new conversation request
    When Second agent click on new conversation request from touch
    When Second agent click "End chat" button
    Then End chat popup for second agent should be opened
    Then Second agent does not see the disabled tag