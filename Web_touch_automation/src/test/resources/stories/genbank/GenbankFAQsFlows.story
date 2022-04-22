Genbank Flows
Narrative:
We run different flows for genbank tenant and verify them

Scenario: Open Genbank Chat
Given Open main user page
Given Select Genbank tenant and open chat
Then Verify that last Card has following data:
      | type   | value                                                                 |
      | title  | Hi! I'm Melissa and welcome to General Bank. How can I help you today?|
      | button | Open an Account                                                       |
      | button | Lost Bank Card                                                        |
      | button | FAQs                                                                  |

Scenario: Go through Genbank FAQs flow
When Click on Card-Button with text: 'FAQs'
Then Verify that last Card has following data:
      | type   | value                                            |
      | title  | Frequently Asked Questions                       |
      | button | What is a cash advance?                          |
      | button | What is a Merchant Checkout Fee?                 |
      | button | Is this checkout fee being collected by the bank?|
      | button | How will I know about the checkout fee?          |
      | button | What is a cashier's check?                       |
      | button | Ask a Question                                   |
Then Verify that last your request was: 'FAQs'
When Click on Card-Button with text: 'What is a cash advance?'
Then Verify that last your request was: 'What is a cash advance?'
Then Verify that last response was: 'A cash advance is a short-term loan obtained with a credit card (Visa, Master Card, or DISCOVER). Cash advance fees may apply.'
Then Verify that last Card has following data:
       | type   | value                                            |
       | title  | Frequently Asked Questions                       |
       | button | What is a cash advance?                          |
       | button | What is a Merchant Checkout Fee?                 |
       | button | Is this checkout fee being collected by the bank?|
       | button | How will I know about the checkout fee?          |
       | button | What is a cashier's check?                       |
       | button | Ask a Question                                   |
When Click on Card-Button with text: 'What is a Merchant Checkout Fee?'
Then Verify that last your request was: 'What is a Merchant Checkout Fee?'
Then Verify that last response was: 'A legal settlement now allows merchants to charge consumers a checkout fee (also referred to as a surcharge) when consumers use a credit card for payment. This fee is not permitted on debit or prepaid cards (even if the credit option is chosen for a debit card payment). The effective date of the settlement that allows merchants to charge this fee was January 27, 2013.'
Then Verify that last Card has following data:
      | type   | value                                            |
      | title  | Frequently Asked Questions                       |
      | button | What is a cash advance?                          |
      | button | What is a Merchant Checkout Fee?                 |
      | button | Is this checkout fee being collected by the bank?|
      | button | How will I know about the checkout fee?          |
      | button | What is a cashier's check?                       |
      | button | Ask a Question                                   |
When Click on Card-Button with text: 'Is this checkout fee being collected by the bank?'
Then Verify that last your request was: 'Is this checkout fee being collected by the bank?'
Then Verify that last response was: 'No. Banks are not collecting the fee and have no control over the fees charged by merchants. We recommend all cardholders look for merchant notices or ask merchants if they will charge a checkout fee before submitting payment.'
Then Verify that last Card has following data:
      | type   | value                                            |
      | title  | Frequently Asked Questions                       |
      | button | What is a cash advance?                          |
      | button | What is a Merchant Checkout Fee?                 |
      | button | Is this checkout fee being collected by the bank?|
      | button | How will I know about the checkout fee?          |
      | button | What is a cashier's check?                       |
      | button | Ask a Question                                   |
When Click on Card-Button with text: 'How will I know about the checkout fee?'
Then Verify that last your request was: 'How will I know about the checkout fee?'
Then Verify that last response was: 'Merchants are required to disclose the fee at the entrance to their location (web site or storefront) in advance of accepting payment. The fee must also be disclosed on the payment receipt. This applies to online transactions and point of sale transactions.'
Then Verify that last Card has following data:
      | type   | value                                            |
      | title  | Frequently Asked Questions                       |
      | button | What is a cash advance?                          |
      | button | What is a Merchant Checkout Fee?                 |
      | button | Is this checkout fee being collected by the bank?|
      | button | How will I know about the checkout fee?          |
      | button | What is a cashier's check?                       |
      | button | Ask a Question                                   |
When Click on Card-Button with text: 'What is a cashier's check?'
Then Verify that last your request was: 'What is a cashier's check?'
Then Verify that last response was: 'A cashier's check is check issued by General Bank and signed by an authorized employee. Cashier's checks can be issued for any amount and are good indefinitely. The fee for a cashier's check varies, so ask your financial center for details.'
Then Verify that last Card has following data:
      | type   | value                                            |
      | title  | Frequently Asked Questions                       |
      | button | What is a cash advance?                          |
      | button | What is a Merchant Checkout Fee?                 |
      | button | Is this checkout fee being collected by the bank?|
      | button | How will I know about the checkout fee?          |
      | button | What is a cashier's check?                       |
      | button | Ask a Question                                   |
When Click on Card-Button with text: 'Ask a Question'
Then Verify that last your request was: 'Ask a Question'
Then Verify that last response was: 'Sure, ask away...'
When Type message to chat: '/end' and press Enter
Then Verify that last Card has following data:
      | type   | value                                                                 |
      | title  | Hi! I'm Melissa and welcome to General Bank. How can I help you today?|
      | button | Open an Account                                                       |
      | button | Lost Bank Card                                                        |
      | button | FAQs                                                                  |
