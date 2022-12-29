# Created by modynets at 15.12.2022
Feature: Transaction execution
  # user can get the transaction link and is able to make payment (positive and negative)

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

    Then User gets a correct payment link
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

    Then User gets an error for payment link creation


    Examples:
      | channel | to           | currency | orderNumber | subTotalAmount | taxAmount | totalAmount | timestamp                    | departmentId | departmentName | returnPaymentLink | paymentReviewAutoReversal | transactionType |  |
      | sms     | 447938556403 | ZAR      | 001         | 100            | 0.0       | 4004.0      | 2021-04-27T17:35:58.000+0000 | 567          | Sales          | true              | false                     | authorization   |  |








