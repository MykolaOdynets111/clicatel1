@smoke
Feature: Bot answers regarding card

  Verification of basic communication between user and bot

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                       |expected response|
      |i lost my card                                    |When your card expires and is reissued, you will need to update the card details according to your new card. Alternately you can delete your old card details and load the new card.                                                                                    |
      |hi, how do i check my balance in the app?         | Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
      |Why is my card blocked?                                        |This is a safety mechanism to protect your account from unauthorised access. You have a maximum of 5 attempts before your Mobile Banking PIN is blocked. If your Mobile Banking PIN is blocked by accident, visit your nearest branch to reset your Mobile Banking PIN.|
