@agent_to_user_conversation
@facebook
@fb_dm
Feature: Customer 360 from fb

  Scenario: Facebook: Customer 360 from fb
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Correct fb dm client details are shown
    And Click 'Edit' button in Customer 360 view
    When Fill in the form with new fb dm customer 360 info
    And Click 'Save' button in Customer 360 view
    Then fb dm customer info is updated on backend
    And Correct fb dm client details are shown
    And New info is shown in left menu with chats
    And Empty image is not shown for chat with facebook user
    And Customer name is updated in active chat header

