Feature: Closing session

  Scenario: All session attributes should be closed in Data Base
    In session table session should have "TERMINATED" status and ended date not null
    In chat_agent_history table item by session id should have ended date
    In conversation table conversation should have "0" in active column
    Given I login as agent of General Bank Demo
    When User select General Bank Demo tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Agent closes chat
    Then Agent should not see from user chat in agent desk
    And Last visit date is saved to DB after 2 minutes
    Then All session attributes are closed in DB