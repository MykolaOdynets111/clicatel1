@smoke
Feature: Bot answers regarding interest and account

  Verification of basic communication between user and bot

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                       |expected response|
      |Why don't you offer business accounts?            |Hi [FIRST_NAME], we only offer accounts for Personal Banking; you can visit us for more info regarding our Personal Banking offering.                                                                                                                                                                                                                                                                                                                                          |
      |How much interest will I earn on my savings       |You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|
      |I want to open an account.                        |You may visit your nearest General Bank branch with your ID and Proof of Residence to open a Savings Account. For more information on opening an account you can visit us. To open an account you will need to visit your nearest General Bank branch with your ID document and Proof of Residence. When in the branch be sure to get the cellphone banking app. It's the #BestWaytoBank.                                                                                      |
      |interest rate                                     |You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|