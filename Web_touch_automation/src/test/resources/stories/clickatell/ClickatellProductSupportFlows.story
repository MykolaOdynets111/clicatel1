Feature: Clickatell flow
  We verify different clickatell flow

Scenario: Clickatell -> Product Support -> Clickatell Touch flow
Given Open main user page
Given Select Clickatell tenant and open chat
When Click on Card-Button with text: 'Product Support'
Then Verify that last your request was: 'Product Support'
Then Verify that last Card has following data:
      | type   | value                                          |
      | title  | Sure. Before we begin we need authenticate you |
      | button | Log In                                         |
When Login as user 'genbank@gmail.com' with password 'passw0rd'
Then Verify that last response was: 'Authentication successful'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Please, choose account below |
      | button | generalbank                  |
When Click on Card-Button with text: 'generalbank'
Then Verify that last your request was: 'generalbank'
Then Verify that last Card has following data:
      | type   | value                                                                                                    |
      | title  | Welcome, admin. We have the following products linked to your name, please select an account to continue |
      | button | Clickatell Touch                                                                                         |
      | button | Clickatell Engage                                                                                        |
      | button | Clickatell Secure                                                                                        |
      | button | Clickatell Connect                                                                                       |
When Click on Card-Button with text: 'Clickatell Touch'
Then Verify that last your request was: 'Clickatell Touch'
Then Verify that last Card has following data:
      | type   | value                                                    |
      | title  | Greate! Please select one of the following options below |
      | button | See Open Tickets                                         |
      | button | Open New Ticket                                          |
When Click on Card-Button with text: 'Open New Ticket'
Then Verify that last your request was: 'Open New Ticket'
Then Verify that last Card has following data:
      | type   | value                             |
      | title  | Please select the type of Support |
      | button | SMS Messaging                     |
      | button | API Integration                   |
      | button | Authentication                    |
      | button | Payment                           |
      | button | Pricing                           |
When Click on Card-Button with text: 'SMS Messaging'
Then Verify that last your request was: 'SMS Messaging'
Then Verify that last Card has following data:
      | type   | value      |
      | title  | New Ticket |
      | button | Create     |
      | button | Cancel     |
When Add new ticket:
      | subject     | description     | email                       | phone | priority |
      | testSubject | testDescription | testEmail@sink.sendgrid.net | 111   | high     |
Then Verify that card with number: '2' from the end contains ticket information:
      | subject     | description     |
      | testSubject | testDescription |
Then Verify that last response match with: 'We'll be in-contact with you within the next 24 hours. Please use the Service Request number \d{4,8} as reference.'
Then Verify that last Card has following data:
      | type   | value                                   |
      | title  | Is there anything else we can assist you with? |
      | button | Yes                                     |
      | button | That's all, thank you                   |
When Click on Card-Button with text: 'Yes'
Then Verify that last your request was: 'Yes'
Then Verify that last Card has following data:
      | type   | value                                 |
      | title  | Sure, what else we can help you with? |
      | button | Product Support                       |
      | button | Account Information                   |
      | button | FAQs                                  |
When Click on Card-Button with text: 'Product Support'
When Click on Card-Button with text: 'Clickatell Touch'
When Click on Card-Button with text: 'See Open Tickets'
Then Verify that last your request was: 'See Open Tickets'
Then Verify that list from card with number: '2' from the end contains new ticket added previously
Then Verify that last response match with: 'We'll be in-contact with you within the next 24 hours regarding the \d{1,4} ticket(s)*. Please use the Service Request number \d{4} as reference.'
Then Verify that last Card has following data:
      | type   | value                                   |
      | title  | Is there anything else we can assist you with? |
      | button | Yes                                     |
      | button | That's all, thank you                   |
When Click on Card-Button with text: 'That's all, thank you'
Then Verify that last Card has following data:
      | type   | value                       |
      | title  | Please rate your experience |
      | button | Submit                      |
When Rate your experience with rate: '4' and leave comments: 'Test'
Then Verify that last your request was: 'Your rate is: 4 Test'
Then Verify that last response was: 'Thanks for your feedback. Goodbye.'