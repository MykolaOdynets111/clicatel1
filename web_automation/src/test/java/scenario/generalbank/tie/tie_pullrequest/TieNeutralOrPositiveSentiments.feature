@tie
Feature: TIE should give NEUTRAL or POSITIVE sentiments

  Scenario Outline: Verify if TIE sentiment is NEUTRAL for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                                   |
      |Account balance                                                |
      |hi, how do i check my balance in the app?                      |
      |i lost my card                                                 |
      |trading hours                                                  |
      |Should I have proof of residential address?             |
      |How much interest will I earn on my savings?                   |
      |Do you offer business accounts?                                |
      |branch location                                                |
      |hello                                                          |
      |My staff want to bank with you. Can you help?                  |
      |If pay someone with another bank how long is the transfer time?|
      |Do you have a job for me?                                      |
      |How much interest will I earn on my savings                    |
      |I want to open an account.                                     |
      |OK, Thanks                                                     |
      |Are you open on Saturday?                                      |
      |Why is my card blocked?                                        |
      |Where can I find a branch?                                     |
      |hi                                                             |
      |Hi                                                             |
      |interest rate                                                  |