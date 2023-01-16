Feature: Provider API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-622")
  Scenario Outline: CH :: Admin API : GET /admin/endpoints should return all available endpoints for a provider
    Given User is able to get all endpoint detail for Provider
      | i.providerID   | <i.providerID>   |
      | i.versionID    | <i.versionID>    |
      | o.errorMessage | <o.errorMessage> |
      | o.responseCode | <o.responseCode> |
      | o.ID_0         | <o.ID_0>         |
      | o.name_0       | <o.name_0>       |
      |  o.ID_1        | <o.ID_1>         |
      |  o.name_1      | <o.name_1>       |
      |  o.ID_2        | <o.ID_2>         |
      |  o.name_2      | <o.name_2>       |
      |  o.ID_3        | <o.ID_3>         |
      |  o.name_3      | <o.name_3>       |
      |  o.ID_4        | <o.ID_4>         |
      |  o.name_4      | <o.name_4>       |
      |  o.ID_5        | <o.ID_5>         |
      |  o.name_5      | <o.name_5>       |
      |  o.ID_6        | <o.ID_6>         |
      |  o.name_6      | <o.name_6>       |

    Examples:
      | i.providerID                     | i.versionID|o.responseCode|o.errorMessage                 |o.ID_0                          |o.name_0  |o.ID_1                            |o.name_1      |o.ID_2                                 |o.name_2|o.ID_3                          |o.name_3                      |o.ID_4                           |o.name_4               |o.ID_5                           |o.name_5  |o.ID_6                           |o.name_6        |
      | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0     |    200       |                               |0184f82f654d988ff09d6d08e8499c3a|Create lead|0184f82f654de2b793510630141e9990 |Search customer| 0184f82f654e445ba7e2e46ddb638085     |Get TAGS|0184f82f654eb488be883c9412a2e22c|Get User Custom Fields - LEADS| 0184f82f654ec9cdb6eed670afdd7e36|Retrieve account details|0184f82f654ef40f88d3929be7ee05c7|Get customer|0184f82f654ef7950075689898286775|Update lead    |
      | 0184f828214f6b7a03c711284b2b8e39 | v1         |    404       |Specification version not found|                                |           |                                 |               |                                      |         |                               |                               |                                 |                         |                         |                 |                                |               |
      #| 0184f828214f6b7a                | v1         |    404       |Provider              not found|                                |           |                                 |               |                                      |         |                               |                               |                                 |                         |                         |                 |                                |               |
      #| 0184f828214f6b7a                | v1         |    404       |Provider and specification not found|                           |           |                                 |               |                                      |         |                               |                               |                                 |                         |                         |                 |                                |               |
      #|                                 | v1         |    404       |Provider not found             |                                |           |                                 |               |                                      |         |                               |                               |                                 |                         |                         |                 |                                |               |
      | 0184f828214f6b7a03c711284b2b8e39 |            |    404       |Specification version not found|                                |           |                                 |               |                                      |         |                               |                               |                                 |                         |                         |                 |                                |               |
