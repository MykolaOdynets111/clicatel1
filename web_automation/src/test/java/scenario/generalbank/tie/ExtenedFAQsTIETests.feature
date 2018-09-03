@tie
Feature: TIE tests regarding FAQs sentiment, intent and answer

  Scenario Outline: Verify if TIE sentiment is NEUTRAL and 1 intent is returned for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for General Bank Demo tenant
    And TIE returns 1 intent: "<intent>" on '<user_message>' for General Bank Demo tenant
    And TIE returns <expected_answer> answer for General Bank Demo tenant <intent> intent

    Examples:
      |user_message                                                         |intent                                                  |expected_answer|
#      |If I am not from South Africa and want to open a General Bank account|can i open an account if i m not from south africa      |If you're not from South Africa and you want to open a General Bank account , we can help you under any of the following conditions: You have a valid passport You have a valid permanent residence permit You have a temporary work permit Find out more here .|
#      |Is my card information stored within the MasterPass wallet?          |is my card information stored within the masterpass wallet|No, as soon as your card information has been entered into the wallet, your card number is automatically replaced by a token. This token will be used for transactions, keeping your card information private and secure at all times.                        |
      |How can I receive my prepaid electricity token?                      |how will i receive my prepaid electricity token           |It'll be displayed on your cellphone screen and will also be sent to you by SMS.                                                                                                                                                                              |
      |How to get global one?                                               |how do i get global one                                   |It's easy and almost paperless just visit your nearest General Bank branch with the following: Identification document (for credit applications, you must be 18 years or older) Original proof of residential address (any of the approved documents in your name with your street address) Latest salary slip ( credit applications only) 3-month bank statement (credit applications only)|
#      |How much does General Bank MasterPass app cost?                      |how much does general bank masterpass app cost            |There are no fees or charges to clients to use the MasterPass app.                                                                                                                                                                                                                                                                                                                          |
#      |Why was my PIN blocked after I entered it incorrectly a few times?   |why was my pin blocked after i entered it incorrectly a few times|This is a safety mechanism to protect your account from unauthorised access. You have a maximum of 5 attempts before your Mobile Banking PIN is blocked. If your Mobile Banking PIN is blocked by accident, visit your nearest branch to reset your Mobile Banking PIN.                                                                                                              |
#      |I haven't used my account for a while. Why can't I access it?        |i haven t used my account for a while. why can t i access it     |All inactive accounts become dormant after 12 months. This means that the account will be frozen for security reasons and no further transactions can be made. To prevent this, simply transact on your account at least once every 12 months. If you'd like to reactivate a dormant account, visit your nearest branch with your ID book and an original proof of residential address .|
      |What are my tax obligations if I pay tax in another country?         |what are my tax obligations if i pay tax in another country      |If you are liable to pay tax in a country other than South Africa we need your foreign tax number. General bank is required by law to obtain certain international tax information from all our clients. The information will be reported to the relevant countries through the South African Revenue Services (SARS), based on the United States Foreign Account Tax Compliance Act (FATCA) and the Organisation for Economic Co-operation and Development (OECD) Common Reporting Standard for the Automatic Exchange of Financial Account Information. New and existing clients are required to confirm all countries other than South Africa where they are a resident for tax purposes or liable to pay tax. Clients must provide their tax identification number for the listed countries. If you are unsure of any information, please contact a professional tax consultant.|
#      |I haven't used my account for a while. Why can't I access it?        |i ve forgotten my mobile banking pin                             |To reset your Mobile Banking PIN, visit your nearest branch with an identification document or Global One card .                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      |I lost/deleted the SMS with my electricity token. Can I view it again?|i lost deleted the sms with my electricity token can i view it again|You can only view the last electricity token that you bought.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
      |How long must I wait until I can use Mobile Banking?                  |how long must i wait until i can use mobile banking                 |There is no wait at all. Once you have successfully registered at one of our branches you can start using Mobile Banking immediately.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
#      |Need some help or assistance with the General Bank MasterPass app?    |need some help or assistance with the general bank masterpass app   |Contact us on our 24hr channels: T 0860 10 20 43 F +27 21 941 0770 E ClientCare@Generalbank.co.za                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |




















