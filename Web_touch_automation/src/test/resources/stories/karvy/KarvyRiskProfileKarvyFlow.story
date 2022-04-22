Karvy Flows
Narrative:
We run different flows for karvy tenant and verify them

Scenario: Verify Selection risk profile Card in Karvy flow
Given Open main user page
Given Select 'Karvy Private Wealth' tenant and open chat
When Click on Card-Button with text: 'I would like product information'
When Click on Card-Button with text: 'Moderate'
Then Verify that last Card has following data:
| type   | value         |
| title  | Browse products by selecting a product category|
| button | Alternate Assets     |
| button | Debt    |
| button | Equity     |
| button | Real Estate   |
| button | Please contact me   |
Then Verify that last response was: 'We have various products to offer you based on your risk profile. However it’s best suggested that you talk to one of our Relationship Managers to know what is best suited for your needs.'
Then Verify that last your request was: 'Moderate'
When Type message to chat: '/end' and press Enter
When Click on Card-Button with text: 'I would like product information'
When Click on Card-Button with text: 'Aggressive'
Then Verify that last Card has following data:
| type   | value         |
| title  | Browse products by selecting a product category|
| button | Alternate Assets     |
| button | Debt    |
| button | Equity     |
| button | Real Estate   |
| button | Please contact me   |
Then Verify that last response was: 'We have various products to offer you based on your risk profile. However it’s best suggested that you talk to one of our Relationship Managers to know what is best suited for your needs.'
Then Verify that last your request was: 'Aggressive'
When Type message to chat: '/end' and press Enter
When Close chat