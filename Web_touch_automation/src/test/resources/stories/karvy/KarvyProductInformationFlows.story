Karvy Flows
Narrative:
We run different flows for karvy tenant and verify them

Scenario: Go through Product Information Karvy flow
Given Open main user page
Given Select 'Karvy Private Wealth' tenant and open chat
When Click on Card-Button with text: 'I would like product information'
Then Verify that last Card has following data:
| type   | value         |
| title  | Please tell us your risk profile|
| button | Conservative     |
| button | Moderate    |
| button | Aggressive     |
| button | I'm not sure    |
Then Verify that last your request was: 'I would like product information'
When Click on Card-Button with text: 'Conservative'
Then Verify that last Card has following data:
| type   | value         |
| title  | Browse products by selecting a product category|
| button | Alternate Assets     |
| button | Debt    |
| button | Equity     |
| button | Real Estate   |
| button | Please contact me   |
Then Verify that last response was: 'We have various products to offer you based on your risk profile. However it’s best suggested that you talk to one of our Relationship Managers to know what is best suited for your needs.'
Then Verify that last your request was: 'Conservative'
When Click on Card-Button with text: 'Alternate Assets'
Then Verify that last Card has following data:
| type   | value         |
| title  | Have a look at our Alternate Assets products|
| info | Private Equity Funds     |
| info | Venture Capital Funds    |
| info | Angel Funds     |
| info | High-yield debt  |
| info | Real estate fund (diversified)  |
| info | Real Estate High Yield Debt  |
| info | Gold ETFs |
| info | Capital Protected Equity Linked Debentures |
| info | Non Capital Protected Equity Linked Debentures|
| info | Hedge Funds|
| button | I want to see other categories|
| info | Content displayed in this app is for information purposes only.|
Then Verify that last your request was: 'Alternate Assets'
When Open row details for row with title: 'Private Equity Funds' in last card
Then Verify that last Card has following data:
| type   | value         |
| info | Private Equity Funds     |
| info | Private Equity funds are funds which make collective investments in private companies which are not listed on stock exchanges. These funds typically have long investment horizons.|
| button | I'm interested in this product     |
When Click on Card-Button with text: 'I'm interested in this product'
Then Verify that last Card has following data:
| type   | value         |
| title  | Please provide us with your contact detail and one of our representatives will be in contact to discuss investment opportunities.|
| button | Submit    |
Then Verify that last your request was: 'Private Equity Funds'
When Provide Contact details:
|firstName|lastName|email         |phone|
|testFN   |testLN  |testk@test.com|111  |
Then Verify that last your request was: 'Submitted data:'
Then Verify that last response was: 'Thank you for your time, we'll get back to you shortly. If you still would prefer to talk to the next available agent, you can type "Agent" at anytime.'
Then Verify that last Card has following data:
| type   | value         |
| title| Is there anything else we can assist you with?     |
| button | Yes please    |
| button | No thanks    |
When Click on Card-Button with text: 'No thanks'
Then Verify that last your request was: 'No thanks'
Then Verify that card with number: '2' from the end has following data:
| type   | value        |
| title  | Chat summary |
Then Verify that last Card has following data:
| type   | value                       |
      | title  | Please rate your experience |
      | button | Submit                      |
When Rate your experience with rate: '4' and leave comments: 'Test'
Then Verify that last your request was: '♥♥♥♥'
Then Verify that last response was: 'Thanks for your feedback. Goodbye.'
When Close chat

Scenario: Verify Product Information T-Card in Karvy flow
When Close chat if chat is open
Given Select 'Karvy Private Wealth' tenant and open chat
When Click on Card-Button with text: 'I would like product information'
When Click on Card-Button with text: 'Conservative'
When Click on Card-Button with text: 'Debt'
Then Verify that last Card has following data:
| type   | value         |
| title  | Have a look at our Debt products|
| info | Government Securities    |
| info | Tax Free Bonds   |
| info | Debt mutual funds|
| info | Corporate secured Non Convertible Debentures|
| info | High Yield Debt (PMS)|
| info | Corporate Fixed Deposits|
| button | I want to see other categories|
| info | Content displayed in this app is for information purposes only.|
Then Verify that last your request was: 'Debt'
When Click on Card-Button with text: 'I want to see other categories'
When Click on Card-Button with text: 'Equity'
Then Verify that last Card has following data:
| type   | value         |
| title| Have a look at our Equity products|
| info | Large-cap MFs   |
| info | Large-cap equity  |
| info | Large-cap PMS|
| info | Mid-cap MFs|
| info | Small cap MFs|
| info | Mid-cap Equity|
| info | Small cap equity|
| info | Mid-cap PMS|
| info | Small cap PMS|
| info | Short-term trading|
| info | Intra-day trading|
| info | Derivatives|
| info | International Equity|
| button | I want to see other categories|
| info | Content displayed in this app is for information purposes only.|
Then Verify that last your request was: 'Equity'
When Click on Card-Button with text: 'I want to see other categories'
When Click on Card-Button with text: 'Real Estate'
Then Verify that last Card has following data:
| type   | value         |
| title| Have a look at our Real estate products (offered through Karvy Realty India Limited)|
| info | Primary Ready Commercial  |
| info | Primary Residential|
| info | Secondary Residential|
| info | Primary under construction commercial|
| info | Primary under construction residence|
| info | Secondary Commercial|
| info | Preleased commercial|
| button | I want to see other categories|
| info | Content displayed in this app is for information purposes only.|
Then Verify that last your request was: 'Real Estate'
When Click on Card-Button with text: 'I want to see other categories'
When Type message to chat: '/end' and press Enter
When Close chat