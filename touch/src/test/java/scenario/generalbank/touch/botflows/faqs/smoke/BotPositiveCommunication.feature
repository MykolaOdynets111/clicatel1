@smoke
Feature: General Bank POSITIVE flow

  Verification of basic communication between user and bot

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                    |expected response|
      |Should I have proof of residential address?                    |If you're a South African citizen and an employed individual, pensioner or student, please download and print this Fica Declaration form . Complete it with your landlord, home owner, college residence manager, hostel manager or cohabitant. Then bring it and the other required documents to the branch to open your account: Identification document Latest salary slip (credit applications only) 3-month bank statement (credit applications only) Remember that if the declarer is your cohabitant, they must also provide an original proof of their residential address , except where he or she is an existing General Bank client. It's not a requirement for the declarer to accompany the client to the branch but the client must obtain a copy of the declarer's identification document. If the declarer is an existing General Bank client, it is not necessary to obtain the abovementioned documents. For a list of the approved documents and further information, please download the Fica flyer .|
      |My staff want to bank with you. Can you help?                  | We'll visit your workplace with a mobibank to help your staff get Global One facilities. They'll need to bring a South African ID book and an original proof of residential address with their full name and address on it. Contact us now to arrange it. We also offer a free financial skills programme for employee groups to help them manage their own money, which we'll gladly present at your workplace.                                                               |
      |If pay someone with another bank how long is the transfer time?| Interbank transfers can take up to 48 working hours to reflect. (If message contains date that is longer than 48 hours this should be transferred to a consultant.)                                                                                                                                                                                                                                                                              |
      |Do you have a job for me?                                      | For information regarding vacancies and posts at General Bank you may visit us                                                                                                                                                                                                                                                                                                                                                                   |
      |Thanks                                                         | You are welcome                                                                                                                                                                                                                                                                                                                                                                                                                                                        |

  Scenario Outline: Verify if user receives correct answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response with Trading Hours intent for his '<user input>' input
    Examples:
      | user input                                                    |expected response|
      |trading hours                                                  | Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us|
      |Are you open on weekends?                                      | Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us                                                                                                                                                                                                    |
