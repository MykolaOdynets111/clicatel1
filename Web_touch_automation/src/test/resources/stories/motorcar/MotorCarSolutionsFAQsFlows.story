Motor Car Solutions Flows
Narrative:
We run different flows for Motor Car Solutions tenant and verify them

Scenario: Open Motor Car Solutions Chat
Given Open main user page
Given Select 'Motor Car Solutions' tenant and open chat
Then Verify that last Card has following data:
      | type   | value                                                                        |
      | title  | Hi! I'm Melissa and welcome to Motor Car Solutions. How can I help you today?|
      | button |The Car Needs a Service                                                       |
      | button | It's a Mechanical Problem                                                    |
      | button | It's a Electrical Problem                                                    |
      | button | FAQs                                                                         |

Scenario: Go through Motor Car Solutions FAQs flow
When Click on Card-Button with text: 'FAQs'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
Then Verify that last your request was: 'FAQs'
When Click on Card-Button with text: 'Where is the nearest Motor Car Solutions location?'
Then Verify that last your request was: 'Where is the nearest Motor Car Solutions location?'
Then Verify that last response was: 'You can quickly locate a Motor Car Solutions service center near you by clicking on the Find Your Jiffy Lube in the navigation bar on every screen.'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
When Click on Card-Button with text: 'How can I obtain a copy of my Motor Car Solutions service records?'
Then Verify that last your request was: 'How can I obtain a copy of my Motor Car Solutions service records?'
Then Verify that last response was: 'Sign up for the Motor Car Solutions Dashboard and enter the necessary information to see your service records from 2011 onward. The Dashboard will update automatically when you have new services performed and advise when your vehicle is due for another visit, based on your vehicle manufacturer's recommended maintenance for your specific vehicle.   Or you can simply fill out the Customer Feedback Form by selecting Request Service Records. Please be sure to provide your license plate (including the state), and we'll send your Motor Car Solutions service records directly to you.'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
When Click on Card-Button with text: 'How do I establish a fleet account?'
Then Verify that last your request was: 'How do I establish a fleet account?'
Then Verify that last response was: 'To access information on Fleet accounts, please visit the Fleet Services information page. Motor Car Solutions has a comprehensive Fleet Services program that can help you keep your vehicles on the road and your drivers productive.'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
When Click on Card-Button with text: 'Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?'
Then Verify that last your request was: 'Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?'
Then Verify that last response was: 'To find out more about joining the Motor Car Solutions team, please fill out the information form on our Become a Franchisee page, or call (800) 327-9532.'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
When Click on Card-Button with text: 'How do I find discounts in my local area?'
Then Verify that last your request was: 'How do I find discounts in my local area?'
Then Verify that last response was: 'Please contact your nearest Motor Car Solutions location using the Find Your Motor Car Solutions link at the top of every page.'
Then Verify that last Card has following data:
      | type   | value                                                                                   |
      | title  | Frequently Asked Questions                                                              |
      | button | Where is the nearest Motor Car Solutions location?                                      |
      | button | How can I obtain a copy of my Motor Car Solutions service records?                      |
      | button | How do I establish a fleet account?                                                     |
      | button | Whom do I contact to obtain information about becoming a Motor Car Solutions franchisee?|
      | button | How do I find discounts in my local area?                                               |
      | button | Ask a Question                                                                          |
When Click on Card-Button with text: 'Ask a Question'
Then Verify that last your request was: 'Ask a Question'
Then Verify that last response was: 'Sure, ask away...'
When Type message to chat: '/end' and press Enter
Then Verify that last Card has following data:
      | type   | value                                                                        |
      | title  | Hi! I'm Melissa and welcome to Motor Car Solutions. How can I help you today?|
      | button |The Car Needs a Service                                                       |
      | button | It's a Mechanical Problem                                                    |
      | button | It's a Electrical Problem                                                    |
      | button | FAQs                                                                         |
