Clikatell Flows
Narrative:
We run different flows for clickatell tenant and verify them

Scenario: Open Clickatell Chat
Given Open main user page
Given Select Clickatell tenant and open chat
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Hi! I'm Melissa and welcome to Clickatell. How can I help you today?|
      | button | Product Support                  |
      | button | Account Information|
      | button | FAQs|

Scenario: Go through Clickatell FAQs flow
When Click on Card-Button with text: 'FAQs'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
Then Verify that last your request was: 'FAQs'
When Click on Card-Button with text: 'How do I change my password for Clickatell Secure?'
Then Verify that last your request was: 'How do I change my password for Clickatell Secure?'
Then Verify that last response was: 'Once you've signed up for your account, you can log in and change your password under Account settings.'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
When Click on Card-Button with text: 'Can I define how many Secure Grid challenges I want to offer?'
Then Verify that last your request was: 'Can I define how many Secure Grid challenges I want to offer?'
Then Verify that last response was: 'Yes, this can be defined in the API call. The default setting is three challenges, but you can specify more challenges per transaction. However, we don't recommend more than three. In many use cases, one challenge is sufficient, meaning the user will receive a mobile Grid single screen with six icons to choose from.'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
When Click on Card-Button with text: 'How can I check the status of a Clickatell Secure authentication?'
Then Verify that last your request was: 'How can I check the status of a Clickatell Secure authentication?'
Then Verify that last response was: 'Click Summary to see the status of all requests for a specified timeframe. Click Authentications for a list of all authentications for a specified period of time, including the date, destination number, status (success or failure) and authentication type (Pin or Grid). You can also choose to view the details of specific authentications with the authentications report. Click on a user's phone number to generate a report for a specific destination (phone) number.'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
When Click on Card-Button with text: 'Do users need to download an app to use Clickatell Secure?'
Then Verify that last your request was: 'Do users need to download an app to use Clickatell Secure?'
Then Verify that last response was: 'No, an app download is not required. A phone simply needs to support SMS (for Secure Pin) or have a mobile browser installed (for Secure Grid).'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
When Click on Card-Button with text: 'How long is the test period for Clickatell Secure?'
Then Verify that last your request was: 'How long is the test period for Clickatell Secure?'
Then Verify that last response was: 'Once you sign up, you'll have one month (30 days) to test Clickatell Secure. During that time, you can test both Secure Pin and Secure Grid using free test credits without paying a subscription fee. After this time, you'll need to pay a monthly subscription fee to use Secure Grid. However, there is no monthly fee for Secure Pin. Note that if you don't have free test credits, you'll need to purchase credits in order to send authentication messages. This is why you may not have test credits.'
Then Verify that last Card has following data:
      | type   | value                                                             |
      | title  | Frequently Asked Questions                                        |
      | button | How do I change my password for Clickatell Secure?                |
      | button | Can I define how many Secure Grid challenges I want to offer?     |
      | button | How can I check the status of a Clickatell Secure authentication? |
      | button | Do users need to download an app to use Clickatell Secure?        |
      | button | How long is the test period for Clickatell Secure?                |
      | button | Ask a Question                                                    |
When Click on Card-Button with text: 'Ask a Question'
Then Verify that last your request was: 'Ask a Question'
Then Verify that last response was: 'Sure, ask away...'
When Type message to chat: '/end' and press Enter
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Hi! I'm Melissa and welcome to Clickatell. How can I help you today?|
      | button | Product Support                  |
      | button | Account Information|
      | button | FAQs|
