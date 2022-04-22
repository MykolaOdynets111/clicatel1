Karvy Flows
Narrative:
We run different flows for karvy tenant and verify them

Scenario: Open Karvy Chat
Given Open main user page
Given Select 'Karvy Private Wealth' tenant and open chat
Then Verify that last Card has following data:
      | type   | value                        |
      | title  | How can I help you today?|
      | button | I would like product information                 |
      | button | What is my risk profile|
      | button | Please contact me|
      | button | FAQs|

Scenario: Go through Karvy FAQs flow
When Click on Card-Button with text: 'FAQs'
Then Verify that card with number: '1' from the end is matching data:
| type   | value         |
| title  | Load more FAQs|
| button | More FAQs     |
Then Verify that last your request was: 'FAQs'
Then Verify that card with number: '2' from the end is matching data:
| type   | value         |
| title  | Do you guarantee returns\?|
When Open card with number: '2' from the end
Then Verify that card with number: '2' from the end has following data:
| type   | value         |
| title  | Do you guarantee returns?|
| info   | While we use sophisticated mathematical/statistical tools to allocate your investments among the various asset classes and model the growth in your investment portfolio, it is important to keep in mind the inherent variability in market returns in most asset classes. Thus, we do not guarantee any minimum returns but we do carry out appropriate simulations to provide adequate confidence on achievement of goals and targets.|
Then Verify that card with number: '3' from the end is matching data:
| type   | value         |
| title  | Does Karvy Private Wealth have its own mutual fund\?|
When Open card with number: '3' from the end
Then Verify that card with number: '3' from the end has following data:
| type   | value         |
| title  | Does Karvy Private Wealth have its own mutual fund?|
| info   | We do not have any mutual fund of our own and neither are we affiliated to any one Mutual Fund AMC. Therefore, we provide unbiased solutions on all mutual funds.|
Then Verify that card with number: '4' from the end is matching data:
| type   | value         |
| title  | What are the services you offer\?|
When Open card with number: '4' from the end
Then Verify that card with number: '4' from the end has following data:
| type   | value         |
| title  | What are the services you offer?|
| info   | We offer a solution that covers Comprehensive Financial Planning, Wealth Review and Investment Strategy, Retirement Planning through Goal-driven Investing, Risk Management & Insurance Planning, Property Purchase & Financing, ESOP Advisory, Equity & F&O Trading, Commodities Trading or any subset of this gamut of services. To learn about each of these services in detail visit our Wealth Management Service.|
Then Verify that card with number: '5' from the end is matching data:
| type   | value         |
| title  | How is Karvy Private Wealth different from others\?|
When Open card with number: '5' from the end
Then Verify that card with number: '5' from the end has following data:
| type   | value         |
| title  | How is Karvy Private Wealth different from others?|
| info   | We provide unbiased solutions across a wide range of products and services and help you plan for a more secure financial future.|
Then Verify that card with number: '6' from the end is matching data:
| type   | value         |
| title  | What is Wealth Management\?|
When Open card with number: '6' from the end
Then Verify that card with number: '6' from the end has following data:
| type   | value         |
| title  | What is Wealth Management?|
| info   | Wealth Management is the management of a client's wealth by a professional trained to get maximum value for the client's financial assets. An investment Management discipline, it incorporates various aggregated financial services such as financial planning, investment portfolio management, etc.|
When Go down to the bottom
When Click on Card-Button with text: 'More FAQs'
Then Verify that card with number: '1' from the end has following data:
| type   | value         |
| title  | That's all FAQs, do you want to:|
| button | Ask a Question   |
| button | Return to menu    |
Then Verify that last your request was: 'More FAQs'
Then Verify that card with number: '2' from the end is matching data:
| type   | value         |
| title  | Is my information kept confidential\?|
When Open card with number: '2' from the end
Then Verify that card with number: '2' from the end has following data:
| type   | value         |
| title  | Is my information kept confidential?|
| info   | Yes, completely! We are deeply committed to the confidentiality of your data. We have built in comprehensive processes to ensure that only your Wealth Manager has access to your data.|
Then Verify that card with number: '3' from the end is matching data:
| type   | value         |
| title  | What is your fee structure\?|
When Open card with number: '3' from the end
Then Verify that card with number: '3' from the end has following data:
| type   | value         |
| title  | What is your fee structure?|
| info   | Our fee structure is a combination of the fee charged for the comprehensive investment solutions provided and the commissions that we receive from all the companies in whose products you invest.|
Then Verify that card with number: '4' from the end is matching data:
| type   | value         |
| title  | What is the minimum level of investment required\?|
When Open card with number: '4' from the end
Then Verify that card with number: '4' from the end has following data:
| type   | value         |
| title  | What is the minimum level of investment required?|
| info   | There is no prescribed minimum level of investment that we require to start our relationship. However, to gain access to more sophisticated services and privileges, we may require a certain minimum commitment from you.|
When Go down to the bottom
When Click on Card-Button with text: 'Return to menu'
Then Verify that last Card has following data:
| type   | value                           |
| title  | Please choose a question below  |
| button | I would like product information|
| button | What is my risk profile         |
| button | Please contact me               |
| button | FAQs                            |
Then Verify that last your request was: 'Return to menu'
When Click on Card-Button with text: 'FAQs'
When Click on Card-Button with text: 'More FAQs'
When Click on Card-Button with text: 'Ask a Question'
Then Verify that last response was: 'Sure. Let me fetch an agent for you.' or 'Agent Karvy Admin has joined the conversation'
When Type message to chat: '/end' and press Enter
