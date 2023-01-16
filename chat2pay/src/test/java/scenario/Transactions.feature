# Created by modynets at 15.12.2022
Feature: Transaction execution
  # user can get the transaction link and is able to make payment (positive and negative)
  # user can cancel transaction link and is able to make payment (positive and negative)


  @TestCaseId("https://jira.clickatell.com/browse/C2P-4317")
  Scenario Outline: chat2pay-service :: POST /chat-2-pay :: User can get a payment link
    Given User is logged in to unity
    And User gets widgetId for UC form
    And User gets paymentGatewaySettingsId for widget
    And User gets application Id for widget
    And User gets activation key for widget
    And User sets data in the payment body
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

    Then User gets a correct payment link with status code 201
    Then The payment has success status code

    Examples:
      | channel | to           | currency | orderNumber | subTotalAmount | taxAmount | totalAmount | timestamp                    | departmentId | departmentName | returnPaymentLink | paymentReviewAutoReversal | transactionType |  |
      | sms     | 447938556403 | ZAR      | 001         | 100            | 0.0       | 100.0       | 2021-04-27T17:35:58.000+0000 | 567          | Sales          | true              | false                     | authorization   |  |


  @TestCaseId("https://jira.clickatell.com/browse/C2P-4317")
  Scenario Outline: chat2pay-service :: POST /chat-2-pay :: the error description should be saved when transaction fails on the Recipient page
    Given User is logged in to unity
    And User gets widgetId for UC form
    And User gets paymentGatewaySettingsId for widget
    And User gets application Id for widget
    And User gets activation key for widget
    And User sets data in the payment body
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

    Then User gets an error for payment link creation with status code 400


    Examples:
      | channel | to           | currency | orderNumber | subTotalAmount | taxAmount | totalAmount | timestamp                    | departmentId | departmentName | returnPaymentLink | paymentReviewAutoReversal | transactionType |  |
      | sms     | 447938556403 | ZAR      | 001         | 100            | 0.0       | 4004.0      | 2021-04-27T17:35:58.000+0000 | 567          | Sales          | true              | false                     | authorization   |  |


  @TestCaseId("https://jira.clickatell.com/browse/C2P-4594")
  Scenario Outline: c2p-Widget-Payment-Service :: POST /cancel (positive)
    Given User is logged in to unity
    And User gets widgetId for UC form
    And User gets paymentGatewaySettingsId for widget
    And User gets application Id for widget
    And User gets activation key for widget
    And User sets valid data in the payment body
    Then User gets a correct payment link with status code 201
    When User cancelling the payment link
      | i.paymentLinkRef      | <i.paymentLinkRef>      |
      | i.activationToken     | <i.activationToken>     |
      | o.responsecode        | <o.responsecode>        |
      | o.error               | <o.error>               |
      | o.paymentlinkref      | <o.paymentlinkref>      |
      | o.transactionStatus   | <o.transactionStatus>   |
      | o.transactionStatusId | <o.transactionStatusId> |
      | o.departmentID        | <o.departmentID>        |
      | o.departmentName      | <o.departmentName>      |
      | o.timestamp           | <o.timestamp>           |
      | o.message             | <o.message>             |
      | o.reasonCode          | <o.reasonCode>          |
      | o.reason              | <o.reason>              |
      | o.status              | <o.status>              |
      | o.path                | <o.path>                |
      | o.errors              | <o.errors>              |


    Examples:
      | i.paymentLinkRef | i.activationToken | o.responsecode | o.error | o.paymentlinkref                     | o.transactionStatus    | o.transactionStatusId | o.departmentID | o.departmentName | o.timestamp                   | o.message                                                                    | o.reasonCode | o.reason          | o.status    | o.path | o.errors                                                                                          |
      | valid            | TRUE              | 200            | 0       | c74d1942-23b1-4a75-8929-1ed21d35c800 | Payment Link Cancelled | 19                    | 567            | Sales            | 2021-09-06T10:46:18.618+00:00 | 0                                                                            | 0            | 0                 | 0           | 0      | 0                                                                                                 |
      | alreadyCancelled | TRUE              | 400            | 0       | 0                                    | 0                      | 0                     |                | 0                | 2021-09-06T10:46:18.618+00:01 | The payment link = c74d1942-23b1-4a75-8929-1ed21d35c800 is already cancelled | 1            | already_cancelled | BAD_REQUEST | 0      | 0                                                                                                 |
#      | expired          | TRUE              | 400            | 0       | 0                                    | 0                      | 0                     |                | 0                | 2021-09-06T10:46:18.618+00:02 | The payment link = c207c0f9-da7b-45a1-bdf0-8086d5d8d732 expired              | 2            | expired           | BAD_REQUEST | 0      | 0                                                                                                 |
      | nonexisted       | TRUE              | 404            | 0       | 0                                    | 0                      | 0                     |                | 0                | 2021-09-06T10:46:18.618+00:04 | URL /api/v2/cancel/f2e8612a-819d-4080-972a-85c13d7652924as34                 | 0            | 0                 | NOT_FOUND   | 0      | There is no such transaction with transaction reference f2e8612a-819d-4080-972a-85c13d7652924as34 |







