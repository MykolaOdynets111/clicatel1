@facebook
Feature:  Bot answering on user's post

  Scenario: Bot should answer with comment on users's post
    Given Open General Bank Demo page
    When User makes post message with text account balance
    And Click "View Post" button
    Then User is sown "expected responce" on his message