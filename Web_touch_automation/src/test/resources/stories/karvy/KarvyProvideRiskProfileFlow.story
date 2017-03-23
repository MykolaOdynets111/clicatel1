Karvy Flows
Narrative:
We run different flows for karvy tenant and verify them

Scenario: Verify Selection risk profile Card in Karvy flow
Given Open main user page
Given Select 'Karvy Private Wealth' tenant and open chat
When Click on Card-Button with text: 'What is my risk profile'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 1 of 7|
| info  | How many family members in your household?|
| info | 1 |
| info | 2 |
| info | 3 |
| info | 4 |
| info | More than 4 |
| button | Submit |
Then Verify that last your request was: 'What is my risk profile'
When Click on Radio-Button with text: '4'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 2 of 7|
| info  | Is your spouse earning?|
| info | Yes |
| info | No |
| button | Submit |
Then Verify that last your request was: '4'
When Click on Radio-Button with text: 'Yes'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 3 of 7|
| info  | Family expenses per year is:|
| info | 0 – 2,00,000|
| info | 2,00,000 – 5,00,000 |
| info | 5,00,000 – 10,00,000 |
| info | > 10,00,000 |
| button | Submit |
Then Verify that last your request was: 'Yes'
When Click on Radio-Button with text: '2,00,000 – 5,00,000'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 4 of 7|
| info  | Which out of these assets are owned by you:|
| info | Self–occupied house|
| info | Additional house|
| info | Two wheeler |
| info | Four wheeler |
| info | Fixed deposits |
| info | Mutual funds|
| info | Shares |
| info | PPF |
| button | Submit |
Then Verify that last your request was: '2,00,000 – 5,00,000'
When Click on Radio-Button with text: 'PPF'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 5 of 7|
| info  | How much financial protection in form of insurance would you leave for your family?|
| info | Rs. 20,00,000 – Rs. 50,00,000|
| info | Rs. 50,00,000 – Rs. 1,00,00,000|
| info | Rs. 1,00,00,000 – Rs. 3,00,00,000 |
| info | Rs. 3,00,00,000 – Rs. 5,00,00,000 |
| info | > Rs. 5,00,00,000|
| button | Submit |
Then Verify that last your request was: 'PPF'
When Click on Radio-Button with text: '> Rs. 5,00,00,000'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 6 of 7|
| info  | Which out of the following would you like to address?|
| info | Ensure retirement|
| info | Provide for child’s education costs|
| info | Buy a House|
| info | Provide for child’s Marriage|
| info | Achieve growth in investments|
| info | Protect income - death/disability|
| info | Reduce Housing/Other Loans|
| info | Reduce Credit Card liability|
| info | Assets are passed to dependants|
| info | Reduce Income-tax|
| info | Protect Income/Assets from Inflation|
| button | Submit |
Then Verify that last your request was: '> Rs. 5,00,00,000'
When Click on Radio-Button with text: 'Protect Income/Assets from Inflation'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Question 7 of 7|
| info  | In case the market falls, what would you do?|
| info | I would remove my money from market linked instruments|
| info | I would not panic and leave my money invested|
| info | I would buy more market linked instruments |
| button | Submit |
Then Verify that last your request was: 'Protect Income/Assets from Inflation'
When Click on Radio-Button with text: 'I would buy more market linked instruments'
When Click on Card-Button with text: 'Submit'
Then Verify that last Card has following data:
| type   | value         |
| title  | Thank you for completing the risk analysis questionnaire. Based on your feedback your risk profile is indicated as Aggressive|
| button | Browse Products|
| button | View Summary of answers|
| button | Please contact me|
Then Verify that last your request was: 'I would buy more market linked instruments'
When Click on Card-Button with text: 'View Summary of answers'
Then Verify that last Card has following data:
| type   | value         |
| title  | Your risk profile summary|
| info | How many family members in your household?|
| info | Is your spouse earning?|
| info | Family expenses per year is|
| info | Which out of these assets are owned by you:|
| info | How much financial protection in form of insurance would you leave for your family?|
| info | Which out of the following would you like to address?|
| info | In case the market falls, what would you do?|
| button | Browse Products |
| button | Please contact me |
Then Verify that last your request was: 'View Summary of answers'
When Click on Card-Button with text: 'Please contact me'
Then Verify that last Card has following data:
| type   | value         |
| title  | Please provide us with your contact detail and one of our representatives will be in contact to discuss investment opportunities.|
| button | Submit    |
When Type message to chat: '/end' and press Enter
When Close chat