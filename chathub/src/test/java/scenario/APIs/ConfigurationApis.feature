Feature: Configuration API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-568")
  Scenario Outline: CH :: Public :: Configurations API : Activate configuration
    Given User is able to activate configuration for a provider
      | i.name               | <i.name>               |
      | i.clientSecret       | <i.clientSecret>       |
      | i.clientId           | <i.clientId>           |
      | i.host               | <i.host>               |
      | i.providerId         | <i.providerId>         |
      | i.type               | <i.type>               |
      | o.responseCode       | <o.responseCode>       |
      | o.errordescription   | <o.errordescription>   |
      | o.ID                 | <o.ID>                 |
      | o.type               | <o.type>               |
      | o.setupName          | <o.setupName>          |
      | o.createDate         | <o.createDate>         |
      | o.modifiedDate       | <o.modifiedDate>       |
      | o.authenticationLink | <o.authenticationLink> |
      | o.timeToExpire       | <o.timeToExpire>       |


    Examples:
      | i.name                              | i.clientSecret                                                   | i.clientId | i.host                                 | i.providerId                     | i.type     | o.responseCode | o.errordescription                                                         | o.ID              | o.type   | o.setupName                       | o.createDate  | o.modifiedDate | o.authenticationLink | o.timeToExpire |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 200            |                                                                            | [ConfigurationID] | [i.type] | [i.name]                          | [CurrentDate] | [CurrentDate]  | [link]               | 300            |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                                                                            | [ConfigurationID] | [i.type] | [i.name]                          | [CurrentDate] | [CurrentDate]  | [link]               | 300            |
      | CH_Test_AutoTester2023-Duplicate    | 86be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5          | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 400            | [i.name] is already present. Please enter a unique configuration name      |                   | SANDBOX  | CH_Test_UHTester2023-[randNumber] | Current Date  | Current Date   | Not empty            | 300            |
      | CH_Test_AutoTester2023-[randNumber] | f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a32974                | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                                                                            | [ConfigurationID] | [i.type] | [i.name]                          | [CurrentDate] | [CurrentDate]  | [link]               | 300            |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | WrongInput | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                                                                            | [ConfigurationID] | [i.type] | [i.name]                          | [CurrentDate] | [CurrentDate]  | [link]               | 300            |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https:/wrongInput.zendesk.com          | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                                                                            | [ConfigurationID] | [i.type] | [i.name]                          | [CurrentDate] | [CurrentDate]  | [link]               | 300            |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | f820c06ec8b62dfa0610e29a         | SANDBOX    | 404            | Provider not found                                                         |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | WrongInput | 404            | bad request                                                                |                   |          |                                   |               |                |                      |                |
      | NULL                                | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | bad request                                                                |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | NULL                                                             | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | NULL       | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | NULL                                   | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | NULL                             | PRODUCTION | 400            | providerId can't be null                                                   |                   |          |                                   |               |                |                      |                |
      | CH_Test_AutoTester2023-[randNumber] | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | NULL       | 404            | bad reuqest                                                                |                   |          |                                   |               |                |                      |                |



