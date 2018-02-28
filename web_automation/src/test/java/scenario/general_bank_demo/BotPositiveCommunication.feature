@smoke @parallel
Feature: General Bank POSITIVE flow

  Verification of basic communication between user and bot

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                       |expected response|
      |Account balance                                   | Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
      |hi, how do i check my balance in the app?         | Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
      |i lost my card                                    |When your card expires and is reissued, you will need to update the card details according to your new card. Alternately you can delete your old card details and load the new card.                                                                                    |
      |trading hours                                     |Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us|
      |What if I don't have proof of residential address?|If you're a South African citizen and an employed individual, pensioner or student, please download and print this Fica Declaration form . Complete it with your landlord, home owner, college residence manager, hostel manager or cohabitant. Then bring it and the other required documents to the branch to open your account: Identification document Latest salary slip (credit applications only) 3-month bank statement (credit applications only) Remember that if the declarer is your cohabitant, they must also provide an original proof of their residential address , except where he or she is an existing General Bank client. It's not a requirement for the declarer to accompany the client to the branch but the client must obtain a copy of the declarer's identification document. If the declarer is an existing General Bank client, it is not necessary to obtain the abovementioned documents. For a list of the approved documents and further information, please download the Fica flyer .|
      |How much interest will I earn on my savings?      |You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|
      |Why don't you offer business accounts?            |Hi [FIRST_NAME], we only offer accounts for Personal Banking; you can visit us for more info regarding our Personal Banking offering.                                                                                                                                                                                                                                                                                                                                          |
#      |branch location                                   |Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch.                                                                                                                                                                                                                                                                                                                          |
#      |hello                                             |Hello                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
#      |My staff want to bank with you. Can you help?     |We'll visit your workplace with a mobibank to help your staff get Global One facilities. They'll need to bring a South African ID book and an original proof of residential address with their full name and address on it. Contact us now to arrange it. We also offer a free financial skills programme for employee groups to help them manage their own money, which we'll gladly present at your workplace.                                                               |
#      |If pay someone with another bank how long is the transfer time?|Hi [FIRST_NAME], interbank transfers can take up to 48 working hours to reflect. (If message contains date that is longer than 48 hours this should be transferred to a consultant.)                                                                                                                                                                                                                                                                              |
#      |Do you have a job for me?                                      |Hi [FIRST_NAME], for information regarding vacancies and posts at General Bank you may visit us                                                                                                                                                                                                                                                                                                                                                                   |
#      |How much interest will I earn on my savings                    |You'll earn the highest interest on a transaction account  5.35% interest per year on any amount up to R10 000 is calculated daily and credited to your account monthly. If your balance is more than R10 000, a rate of 5.35% to 5.75% per year will apply to the full balance, depending on how much is in your account. To earn higher interest rates, consider opening a savings plan. Rates may change from time to time, so check our rates and fees for the latest info.|
#      |I want to open an account.                                     |You may visit your nearest General Bank branch with your ID and Proof of Residence to open a Savings Account. For more information on opening an account you can visit us. To open an account you will need to visit your nearest General Bank branch with your ID document and Proof of Residence. When in the branch be sure to get the cellphone banking app. It's the #BestWaytoBank.                                                                                      |
#      |OK, Thanks                                                     |Sure, no problem                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
#      |Are you open on Saturday?                                      |Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us                                                                                                                                                                                                    |
#      |Why is my card blocked?                                        |This is a safety mechanism to protect your account from unauthorised access. You have a maximum of 5 attempts before your Mobile Banking PIN is blocked. If your Mobile Banking PIN is blocked by accident, visit your nearest branch to reset your Mobile Banking PIN.|
#      |Where can I find a branch?                                     |Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch.                                                                                                                  |