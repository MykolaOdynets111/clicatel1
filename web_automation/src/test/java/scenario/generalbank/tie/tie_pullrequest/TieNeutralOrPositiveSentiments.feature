@tie
Feature: TIE should give NEUTRAL or POSITIVE sentiments

  Scenario Outline: Verify if TIE sentiment is NEUTRAL and 1 intent is returned for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for General Bank Demo tenant
    And TIE returns 1 intent: "<intent>" on '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                                   |intent                                                  |
      |Account balance                                                |balance check                                           |
      |hi, how do i check my balance in the app?                      |balance check                                           |
      |i lost my card                                                 |what happens if my card is lost or stolen or has expired|
      |trading hours                                                  |trading hours                                           |
      |Should I have proof of residential address?                    |what if i don t have proof of residential address       |
      |How much interest will I earn on my savings?                   |how much interest will i earn on my savings             |
      |Do you offer business accounts?                                |business account                                        |
      |branch location                                                |where can i find a branch                               |
      |hello                                                          |hello                                                   |
      |My staff want to bank with you. Can you help?                  |my staff want to bank with you can you help             |
      |If pay someone with another bank how long is the transfer time?|transfer time                                           |
      |Do you have a job for me?                                      |vacancies                                               |
      |How much interest will I earn on my savings                    |how much interest will i earn on my savings             |
      |I want to open an account.                                     |open account                                            |
      |OK, Thanks                                                     |thank you                                               |
      |Are you open on Saturday?                                      |trading hours                                           |
      |Why is my card blocked?                                        |why was my pin blocked after i entered it incorrectly a few times|
      |Where can I find a branch?                                     |where can i find a branch                                        |
      |hi                                                             |hello                                                            |
      |Hi                                                             |hello                                                            |
      |interest                                                       |how much interest will i earn on my savings                      |
      |how to check balance at home?                                  |balance check                                                    |