@agent_to_user_conversation
@twitter
@without_tct

  Feature: Sending edited suggestion via Direct Messages

    Background:
      Given Login to twitter
      Given I login as agent of General Bank Demo
      Given Open twitter page of General Bank Demo
      Given Open direct message channel

    Scenario: Twitter: Sending edited suggestion
      When User sends twitter direct message regarding chat to support
      Then Agent has new conversation request from twitter user
      When Agent click on new conversation request from twitter
      Then Conversation area becomes active with chat to support message from twitter user
      When User sends twitter direct message regarding Do you have a job for me?
      Then There is correct suggestion shown on user message "Do you have a job for me?"
      And The suggestion for user message "Do you have a job for me?" with the biggest confidence is added to the input field
      When Agent add additional info "_Edited suggestion" to suggested message
      When Agent click send button
      Then User have to receive correct response "For information regarding vacancies and posts at General Bank you may visit us_Edited suggestion" on his message "Do you have a job for me?"