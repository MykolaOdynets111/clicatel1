# Created by modynets at 15.12.2022
Feature: Transaction execution
  # user can get the transaction link and is able to make payment (positive and negative)
  # user can cancel transaction link and is able to make payment (positive and negative)

  Background:
    Given User is logged in to unity
    And User gets widgetId for UC form
    And User gets paymentGatewaySettingsId for widget
    And User gets application Id for widget
    And User gets activation key for widget

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4699")
  Scenario Outline: chat2pay-service :: POST /chat-2-pay :: User can get a payment link
    Given User sets data in the payment body
      | channel                   | <i.channel>                   |
      | to                        | <i.to>                        |
      | currency                  | <i.currency>                  |
      | orderNumber               | <i.orderNumber>               |
      | subTotalAmount            | <i.subTotalAmount>            |
      | taxAmount                 | <i.taxAmount>                 |
      | totalAmount               | <i.totalAmount>               |
      | timestamp                 | <i.timestamp>                 |
      | departmentId              | <i.departmentId>              |
      | departmentName            | <i.departmentName>            |
      | returnPaymentLink         | <i.returnPaymentLink>         |
      | paymentReviewAutoReversal | <i.paymentReviewAutoReversal> |
      | transactionType           | <i.transactionType>           |

    Then User gets a correct payment link with status code 201 and <o.transactionStatus>
    Then The payment has success status code

    Examples:

      | i.channel | i.to         | i.currency | i.orderNumber | i.subTotalAmount | i.taxAmount | i.totalAmount | i.timestamp                  | i.departmentId | i.departmentName | i.returnPaymentLink | i.paymentReviewAutoReversal | i.transactionType | o.transactionStatus           |
      | sms       | 447938556403 | ZAR        | 1             | 100              | 0           | 100           | 2021-04-27T17:35:58.000+0000 | 567            | Sales            | TRUE                | FALSE                       | authorization     | PAYMENT_LINK_SENT             |
      | none      | 447938556403 | ZAR        | 1             | 100              | 0           | 100           | 2021-04-27T17:35:58.000+0001 | 568            | Sales            | TRUE                | FALSE                       | authorization     | PAYMENT_LINK_REQUEST_RECEIVED |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4703")
  Scenario Outline: chat2pay-service :: POST /chat-2-pay :: User gets an error for a payment link creation
    Given User sets data in the payment body
      | channel                   | <channel>                   |
      | to                        | <to>                        |
      | currency                  | <currency>                  |
      | orderNumber               | <orderNumber>               |
      | subTotalAmount            | <subTotalAmount>            |
      | taxAmount                 | <taxAmount>                 |
      | totalAmount               | <totalAmount>               |
      | timestamp                 | <timestamp>                 |
      | departmentId              | <departmentId>              |
      | departmentName            | <departmentName>            |
      | returnPaymentLink         | <returnPaymentLink>         |
      | paymentReviewAutoReversal | <paymentReviewAutoReversal> |
      | transactionType           | <transactionType>           |

    Then User gets an error for payment link creation
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |
    Examples:
      | channel | to           | currency | orderNumber | subTotalAmount | taxAmount | totalAmount | timestamp                    | departmentId | departmentName | returnPaymentLink | paymentReviewAutoReversal | transactionType | o.responseCode | o.errorMessage | o.errors                                                 |  |
      | sms     | 447938556403 | ZAR      | 001         | 100            | 0.0       | 4004.0      | 2021-04-27T17:35:58.000+0000 | 567          | Sales          | true              | false                     | authorization   | 400            | Request failed | Total amount is not equal to the sum of tax and subtotal |  |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4594")
  Scenario Outline: c2p-Widget-Payment-Service :: POST /cancel :: user can cancel the created payment link
    Given User sets valid data in the payment body
    Then User gets a correct payment link with status code 201 and PAYMENT_LINK_SENT
    When User cancelling the payment link
      | i.paymentLinkRef    | <i.paymentLinkRef>    |
      | o.responseCode      | <o.responseCode>      |
      | o.transactionStatus | <o.transactionStatus> |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Examples:
      | i.paymentLinkRef | o.responseCode | o.transactionStatus    | o.errorMessage      | o.errors                                                |
      | valid            | 200            | Payment Link Cancelled | 0                   |                                                         |
      | alreadyCancelled | 400            | 0                      | already cancelled   |                                                         |
      | nonExisted       | 404            | 0                      | URL /api/v2/cancel/ | There is no such transaction with transaction reference |
      | expired          | 400            | 0                      | expired             |                                                         |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4688")
  Scenario Outline: c2p-Widget-Payment-Service :: POST /order-receipt :: user can receive the order
    Given User sets valid data in the payment body
    Then User gets a correct payment link with status code 201 and PAYMENT_LINK_SENT
    When user receives the order to email
      | i.receiptLinkRef    | <i.receiptLinkRef>    |
      | o.responseCode      | <o.responseCode>      |
      | o.transactionStatus | <o.transactionStatus> |
      | o.errors            | <o.errors>            |
      | o.errorMessage      | <o.errorMessage>      |

    Examples:
      | i.receiptLinkRef |  | o.responseCode | o.transactionStatus | o.errors                                                | o.errorMessage            |
      | valid            |  | 201            | RECEIPT_LINK_SENT   |                                                         |                           |
#      | sentfailed       |  | 400            | RECEIPT_LINK_SENT_FAILED |                                                         |                           |
      | nonExisted       |  | 404            | 0                   | There is no such transaction with transaction reference | URL /api/v2/order-receipt |