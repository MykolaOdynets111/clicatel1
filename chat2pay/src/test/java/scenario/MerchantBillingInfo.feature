Feature: Widgets operations
# Operations for Merchant billing information

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4736")
  Scenario Outline: C2P Unity API :: Merchant's Billing Info :: GET /merchant-billing-info :: Get billing information for the widget
    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |
    Then User updates Merchant's Billing Info for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | i.email        | <i.email>        |
      | i.addressLine1 | <i.addressLine1> |
      | i.addressLine2 | <i.addressLine2> |
      | i.stateId      | <i.stateId>      |
      | i.countryId    | <i.countryId>    |
      | i.postalCode   | <i.postalCode>   |
      | i.city         | <i.city>         |
      | i.companyName  | <i.companyName>  |
      | i.phone        | <i.phone>        |
      | o.responseCode | <o.responseCode> |
      | o.email        | <i.email>        |
      | o.addressLine1 | <i.addressLine1> |
      | o.addressLine2 | <i.addressLine2> |
      | o.state        | <i.stateId>      |
      | o.country      | <i.countryId>    |
      | o.postalCode   | <i.postalCode>   |
      | o.city         | <i.city>         |
      | o.companyName  | <i.companyName>  |
      | o.phone        | <i.phone>        |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |
    Then User gets Merchant's Billing Info for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.email        | <i.email>        |
      | o.addressLine1 | <i.addressLine1> |
      | o.addressLine2 | <i.addressLine2> |
      | o.state        | <i.stateId>      |
      | o.country      | <i.countryId>    |
      | o.postalCode   | <i.postalCode>   |
      | o.city         | <i.city>         |
      | o.companyName  | <i.companyName>  |
      | o.phone        | <i.phone>        |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Examples:
      | i.widgetId   | i.email                  | i.addressLine1 | i.addressLine2 | i.stateId | i.countryId | i.postalCode | i.city   | i.companyName | i.phone    | o.responseCode | o.status  | o.errorMessage | o.errors                   |
      | valid        | merchantinfo@billing.com | Merchant       | BillingArea    | 3367      | 206         | ABC999       | CapeTown | MBIInc        | 6665557772 | 200            |           |                |                            |
      | skip_posting |                          |                |                |           |             |              |          |               |            | 404            | NOT_FOUND | URL /v2/widget | No billing info for widget |
      | non_existed  |                          |                |                |           |             |              |          |               |            | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id  |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4737")
  Scenario Outline: C2P Unity API :: Merchant's Billing Info :: POST /merchant-billing-info :: Create billing information for the widget
    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |
    Then User updates Merchant's Billing Info for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | i.email        | <i.email>        |
      | i.addressLine1 | <i.addressLine1> |
      | i.addressLine2 | <i.addressLine2> |
      | i.stateId      | <i.stateId>      |
      | i.countryId    | <i.countryId>    |
      | i.postalCode   | <i.postalCode>   |
      | i.city         | <i.city>         |
      | i.companyName  | <i.companyName>  |
      | i.phone        | <i.phone>        |
      | o.responseCode | <o.responseCode> |
      | o.email        | <i.email>        |
      | o.addressLine1 | <i.addressLine1> |
      | o.addressLine2 | <i.addressLine2> |
      | o.state        | <i.stateId>      |
      | o.country      | <i.countryId>    |
      | o.postalCode   | <i.postalCode>   |
      | o.city         | <i.city>         |
      | o.companyName  | <i.companyName>  |
      | o.phone        | <i.phone>        |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Examples:
      | i.widgetId  | i.email                  | i.addressLine1 | i.addressLine2 | i.stateId | i.countryId | i.postalCode | i.city   | i.companyName | i.phone    | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | valid       | merchantinfo@billing.com | Merchant       | BillingArea    | 3367      | 206         | ABC999       | CapeTown | MBIInc        | 6665557772 | 200            |           |                |                           |
      | non_existed | merchantinfo@billing.com | Merchant       | BillingArea    | 3367      | 206         | ABC999       | CapeTown | MBIInc        | 6665557772 | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4738")
  Scenario Outline: C2P Unity API :: Merchant's Billing Info :: DELETE /merchant-billing-info :: DELETE billing information for the widget
    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |
    Then User updates Merchant's Billing Info for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | i.email        | <i.email>        |
      | i.addressLine1 | <i.addressLine1> |
      | i.addressLine2 | <i.addressLine2> |
      | i.stateId      | <i.stateId>      |
      | i.countryId    | <i.countryId>    |
      | i.postalCode   | <i.postalCode>   |
      | i.city         | <i.city>         |
      | i.companyName  | <i.companyName>  |
      | i.phone        | <i.phone>        |
      | o.responseCode | <o.responseCode> |
      | o.email        | <i.email>        |
      | o.addressLine1 | <i.addressLine1> |
      | o.addressLine2 | <i.addressLine2> |
      | o.state        | <i.stateId>      |
      | o.country      | <i.countryId>    |
      | o.postalCode   | <i.postalCode>   |
      | o.city         | <i.city>         |
      | o.companyName  | <i.companyName>  |
      | o.phone        | <i.phone>        |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |
    Then User deletes Merchant's Billing Info for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Examples:
      | i.widgetId   | i.email                  | i.addressLine1 | i.addressLine2 | i.stateId | i.countryId | i.postalCode | i.city   | i.companyName | i.phone    | o.responseCode | o.status  | o.errorMessage | o.errors                   |
      | valid        | merchantinfo@billing.com | Merchant       | BillingArea    | 3367      | 206         | ABC999       | CapeTown | MBIInc        | 6665557772 | 200            |           |                |                            |
      | skip_posting |                          |                |                |           |             |              |          |               |            | 404            | NOT_FOUND | URL /v2/widget | No billing info for widget |
      | non_existed  |                          |                |                |           |             |              |          |               |            | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id  |
