@tie
Feature: VM: TIE sentiments and intent for Cost or transactions fees questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "cost or transactions fees" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                             |
      |What are your transaction fees           |
      |What fees do you charge                  |
      |How much does it cost to swipe the card  |
      |What are the charges for using the card  |
      |What are your costs                      |
      |How much does a transaction cost         |
      |What are all your fees                   |
      |Do you have fees                         |
      |What fees apply on the card              |
      |How much is it to use the card overseas  |
      |what is the cost of an atm withdrawl?    |

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "cost or transactions fees credit card" on '<user_message>' for Virgin Money tenant
    Examples:
      |user_message             |
#      |Credit Card              | To be confirmed because we have separate intent credit card
      |virgin money credit card |
      |card                     |


  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "cost or transactions fees spot" on '<user_message>' for Virgin Money tenant
    Examples:
      |user_message       |
      |Spot               |
      |Spot app           |
      |Virgin Money spot  |
