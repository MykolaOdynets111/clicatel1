Motor Car Solutions Flows
Narrative:
We run different flows for Motor Car Solutions tenant and verify them
                                                                  |

Scenario: Go through Motor Car Solutions Book a Service flow
Given Open main user page
Given Select 'Motor Car Solutions' tenant and open chat
When Click on Card-Button with text: 'The Car Needs a Service'
Then Verify that last your request was: 'The Car Needs a Service'
Then Verify that last Card has following data:
      | type   | value         |
      | title  | Please Sign in|
      | button | Log In        |
When Login as user 'genbank@gmail.com' with password 'passw0rd'
Then Verify that last your request was: 'Username: genbank@gmail.com Password: ********'
Then Verify that last response was: 'Authentication successful'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | Please, choose account below |
      | button | generalbank                  |
When Click on Card-Button with text: 'generalbank'
Then Verify that last your request was: 'generalbank'
Then Verify that last response was: 'Welcome, admin please select vehicle you would like to book for a service'
Then Verify that last Card has following data:
      | type   | value                                                |
      | title  | Is the information we have of your car still correct?|
      | button | Make the booking                                     |
      | button | Edit information                                     |
When Click on Card-Button with text: 'Make the booking'
Then Verify that last your request was: 'Make the booking'
Then Verify that last Card has following data:
      | type   | value                                                    |
      | title  | Please confirm your preferred dealership for the booking.|
      | button | Confirm dealership                                       |
      | button | Edit dealership                                          |
When Click on Card-Button with text: 'Confirm dealership'
Then Verify that last your request was: 'Confirm dealership'
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | We have the following available dates|
When Select day '3' in calender in card
Then Verify that last response was: 'Thank you for your confirmation. Here is a summary of your Booking.'
Then Verify that card with number: '2' from the end is matching data:
      | type   | value                     |
      | title  | Job Card: \d{6,7}         |
      | info   | Hyundai Accent 1.6 Sport  |
      | info   | Manufactured: 2014        |
      | info   | Last Reading: 34,000 KM   |
      | info   | Booking Date: Invalid date|
Then Verify that last Card has following data:
      | type   | value                                     |
      | title  | Would you like to add it to your calendar?|
      | button | Yes                                       |
      | button | No                                        |
When Click on Card-Button with text: 'Yes'
Then Verify that last your request was: 'Yes'
Then Verify that last response was: 'Done! Check your calendar!'
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

