Clikatell Flows
Narrative:
We run different flows for clickatell tenant and verify them

Scenario: Clickatell Account Balance flow
Given Open main user page
Given Select Clickatell tenant and open chat
When Click on Card-Button with text: 'Account Information'
Then Verify that last your request was: 'Account Information'
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
      | type   | value                                                               |
      | title  | Greate! Please select one of the options below in order to continue |
      | button | Account Balance                                                     |
      | button | Account Status                                                      |
      | button | Edit or Update Account                                              |
When Click on Card-Button with text: 'Account Balance'
Then Verify that last your request was: 'Account Balance'
Then Verify that card with number: '2' from the end has following data:
      | type  | value                                                                            |
      | title | Welcome, admin. We have the following account information linked to your profile |
      | info  | Credit Balance                                                                   |
      | info  | EUR 0.0                                                                          |
Then Verify that last Card has following data:
      | type   | value                                  |
      | title  | Would you like to Top-up your account? |
      | button | Yes                                    |
      | button | No                                     |
When Click on Card-Button with text: 'No'
Then Verify that last your request was: 'No'
Then Verify that last response was: 'No problem. Remember, you can top-up at anytime you like, 24/7'
Then Verify that last Card has following data:
      | type   | value                                          |
      | title  | Is there anything else we can assist you with? |
      | button | Yes                                            |
      | button | That's all, thank you                          |
When Click on Card-Button with text: 'That's all, thank you'
Then Verify that last your request was: 'That's all, thank you'
Then Verify that Account Information card is available
Then Verify that last Card has following data:
      | type   | value                       |
      | title  | Please rate your experience |
      | button | Submit                      |
When Rate your experience with rate: '4' and leave comments: 'Test'
Then Verify that last your request was: 'Your rate is: 4 Test'
Then Verify that last response was: 'Thanks for your feedback. Goodbye.'