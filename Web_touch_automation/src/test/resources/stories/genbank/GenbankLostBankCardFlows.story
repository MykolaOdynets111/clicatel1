Genbank Flows
Narrative:
We run different flows for genbank tenant and verify them

Scenario: Genbank Open an Account flow
Given Open main user page
Given Select Genbank tenant and open chat
When Click on Card-Button with text: 'Lost Bank Card'
Then Verify that last your request was: 'Lost Bank Card'
Then Verify that last Card has following data:
      | type   | value                                                                            |
      | title  | Sorry to hear that. Before we can stop your card we need to verify your identity |
      | button | Log In                                                                           |
When Login as user 'genbank@gmail.com' with password 'passw0rd'
Then Verify that last your request was: 'Username: genbank@gmail.com Password: ********'
Then Verify that last response was: 'Authentication successful'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Please, choose account below |
      | button | generalbank                  |
When Click on Card-Button with text: 'generalbank'
Then Verify that last your request was: 'generalbank'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Welcome, admin and thank you for verification. We have 3 cards on record, please select the card you would like to stop |
      | button | Block                  |
When Select credit card which is '2' in card and block it
Then Verify that last response was: 'We have blocked your card'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | When did you loose your card?|
When Select day '3' in calender in card
Then Verify that last response match with: 'Thank you for confirming. We have blocked your card and issued new card for you. It will be despatched in the next 24 hours. Please use the Service Request number : \d{4,5} to track.'
Then Verify that last Card has following data:
      | type   | value                                          |
      | title  | Is there anything else we can assist you with? |
      | button | Yes                                            |
      | button | That's all, thank you                          |
When Click on Card-Button with text: 'That's all, thank you'
Then Verify that last your request was: 'That's all, thank you'
Then Verify that last Card has following data:
      | type   | value                       |
      | title  | Please rate your experience |
      | button | Submit                      |
When Rate your experience with rate: '4' and leave comments: 'Test'
Then Verify that last your request was: 'Your rate is: 4 Test'
Then Verify that last response was: 'Thanks for your feedback. Goodbye.'






