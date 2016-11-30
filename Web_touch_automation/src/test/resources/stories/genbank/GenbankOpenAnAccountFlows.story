Genbank Flows
Narrative:
We run different flows for genbank tenant and verify them

Scenario: Genbank Open an Account flow
Given Open main user page
Given Select Genbank tenant and open chat
When Click on Card-Button with text: 'Open an Account'
Then Verify that last your request was: 'Open an Account'
Then Verify that last Card has following data:
      | type   | value                                                                                                                                                                |
      | title  | If you'd like to open a Global One facility or just want to find out a bit more about it, please complete the form below and we'll get back to you as soon as we can.|
      | button | Submit                                                                                                                                                               |
When Open account:
      |name    |surname    |email                      |phone|
      |testName|testSurname|testEmail@sink.sendgrid.net|11111|
Then Verify that last response match with: 'Thank you for submiting. We'll be in contact with you within next 24 hours. Please use the Service Request number \d{4,6} as reference.'
Then Verify that last Card has following data:
      | type   | value                                         |
      | title  | Is there anything else we can assist you with?|
      | button | Yes                                           |
      | button | That's all, thank you                         |
When Click on Card-Button with text: 'Yes'
Then Verify that last your request was: 'Yes'
Then Verify that last Card has following data:
      | type   | value                                |
      | title  | Sure, what else we can help you with?|
      | button | Open an Account                      |
      | button | Lost Bank Card                       |
      | button | FAQs                                 |
