@smoke
Feature: General Bank positive flow

  Background:
    Given User select General Bank Demo
    And Click chat icon

  Scenario: Verify if user receives answer on account balance message
    When User enter account balance into widget input field
    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input

