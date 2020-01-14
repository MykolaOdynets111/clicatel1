@smoke
Feature: Bot answers regarding interest

  Verification of communication between user and bot regarding interest

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response with how much interest will i earn on my savings intent for his '<user input>' input
    Examples:
      | user input                                       |expected response|
      |How much interest will I earn on my savings       | You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|
#ToDo: find another text input that will not conflict with another tie intents
#      |interest                                          |Hi ${firstName}. You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|