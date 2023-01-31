Feature: Get widgets for an account

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4710")
  Scenario Outline: C2P :: GET :: Get Chat 2 Pay all widgets configuration

    Given User is logged in to unity
    And User gets the Widget configuration
      | activationKey      | <i.activationKey>      |
      | responseCode       | <o.responseCode>       |
      | widget.status      | <o.widget.status>      |
      | widget.type        | <o.widget.type>        |
      | widget.name        | <o.widget.name>        |
      | widget.id          | <o.widget.id>          |
      | widget.accountId   | <o.widget.accountId>   |
      | widget.environment | <o.widget.environment> |
      | last               | <o.last>               |
      | totalPages         | <o.totalPages>         |
      | totalElements      | <o.totalElements>      |
      | first              | <o.first>              |
      | sort.empty         | <o.sort.empty>         |
      | sort.unsorted      | <o.sort.unsorted>      |
      | sort.sorted        | <o.sort.sorted>        |
      | size               | <o.size>               |
      | number             | <o.number>             |
      | numberOfElements   | <o.numberOfElements>   |
      | empty              | <o.empty>              |
      | error              | <o.error>              |
      | path               | <o.path>               |

    Examples:
      | i.activationKey                  | o.responseCode | o.widget.status | o.widget.type | o.widget.name | o.widget.id                      | o.widget.accountId               | o.widget.environment | o.last | o.totalPages | o.totalElements | o.first | o.sort.empty | o.sort.unsorted | o.sort.sorted | o.size | o.number | o.numberOfElements | o.empty | o.error      | o.path         |
      | e9ac1ace130a465a8fb6f8eea293589e | 200            | CONFIGURED      | CHAT_TO_PAY   | UC            | 2c9aca4484b41ef60184bf442eca001f | 7f8fed5d44d646a49f1aa7fd40ab6641 | SANDBOX              | true   | 1            | 3               | true    | true         | true            | false         | 20     | 0        | 3                  | false   |              |                |
      | e9ac1ace130a465a8fb6f8eea293589e | 200            | CONFIGURED      | CHAT_TO_PAY   | SA            | 2c9ac9a584c206e90184cf130011003b | 7f8fed5d44d646a49f1aa7fd40ab6641 | SANDBOX              | true   | 1            | 3               | true    | true         | true            | false         | 20     | 0        | 3                  | false   |              |                |
      | e9ac1ace130a465a8fb6f8eea293589e | 200            | NOT_CONFIGURED  | CHAT_TO_PAY   | " "           | 2c9aca99850b073801850d97d24000ac | 7f8fed5d44d646a49f1aa7fd40ab6641 | SANDBOX              | true   | 1            | 3               | true    | true         | true            | false         | 20     | 0        | 3                  | false   |              |                |
      | test                             | 401            |                 |               |               |                                  |                                  |                      |        |              |                 |         |              |                 |               |        |          |                    |         | Unauthorized | /v2/widget/all |
      | " "                              | 401            |                 |               |               |                                  |                                  |                      |        |              |                 |         |              |                 |               |        |          |                    |         | Unauthorized | /v2/widget/all |