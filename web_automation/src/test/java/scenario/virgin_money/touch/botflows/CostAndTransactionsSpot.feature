Feature: VM flow regarding Cost or transactions fees (spot option)

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Cost or transactions fees: Spot" flow should work for "user message" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, which product are you enquiring about? Credit card or Virgin Money Spot? text is shown on user <user message> message
    Then Card with a Which product are you enquiring about? Credit card or Virgin Money Spot? text is shown on user <user message> message
    And Card with a buttons Credit card; Spot is shown on user <user message> message
    When User click Spot button in the card on user message <user message>
    Then User have to receive 'There are no fees on Virgin Money Spot for Peer-to-Peer transactions. You can send or receive money for free.' text response for his 'Spot' input

    Examples:
      |user message                             |
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


