Karvy Flows
Narrative:
We run different flows for karvy tenant and verify them

Scenario: Verify Contact T-Card Karvy flow
Given Open main user page
Given Select 'Karvy Private Wealth' tenant and open chat
When Click on Card-Button with text: 'Please contact me'
Then Verify that last Card has following data:
| type   | value         |
| title  | Please provide us with your contact detail and one of our representatives will be in contact to discuss investment opportunities.|
| button | Submit    |
Then Verify that last your request was: 'Please contact me'
When Provide Contact details:
|firstName|lastName|email         |phone|
|testFN   |testLN  |testk@test.com|111  |
Then Verify that last your request was: 'Submitted data:'
Then Verify that last response was: 'Thank you for your time, we'll get back to you shortly. If you still would prefer to talk to the next available agent, you can type "Agent" at anytime.'
When Type message to chat: '/end' and press Enter
When Close chat