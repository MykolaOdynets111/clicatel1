@agent_to_user_conversation
@facebook
@fb_dm
@without_tct
@suggestions
Feature: Sending edited suggestion back to Facebook user

  Background:
    Given AGENT_ASSISTANT tenant feature is set to true for General Bank Demo
    Given Login to fb
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Facebook: Sending edited suggestion via Messenger
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    When User sends message regarding Do you have a job for me?
    Then Conversation area contains can i open saving accounts? message from facebook user
    Then There is correct suggestion shown on user message "Do you have a job for me?"
    And The suggestion for user message "Do you have a job for me?" with the biggest confidence is added to the input field
    When Agent add additional info "_Edited suggestion" to suggested message
    When Agent click send button
    Then User have to receive the following on his message regarding Do you have a job for me?: "For information regarding vacancies and posts at General Bank you may visit us_Edited suggestion"