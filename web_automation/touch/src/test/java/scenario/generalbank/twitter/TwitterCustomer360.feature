@agent_to_user_conversation
@twitter
Feature: Profile info for twitter user

  Background:
    Given Login to twitter
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Twitter: Customer 360 info for twitter user
    When User sends twitter direct message regarding to support
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Correct twitter dm client details are shown
    And Click 'Edit' button in Profile
    When Fill in the form with new twitter dm customer 360 info
    And Click 'Save' button in Profile
    Then twitter dm customer info is updated on backend
    And Correct twitter dm client details are shown
    And New info is shown in left menu with chats
    And Customer name is updated in active chat header