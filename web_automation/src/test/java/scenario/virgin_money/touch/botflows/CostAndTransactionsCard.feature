Feature: VM flow regarding Cost or transactions fees (card option)

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Cost or transactions fees: Card" flow should work for "user message" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, which product are you enquiring about? Credit card or Virgin Money Spot? text is shown on user <user message> message
    Then Card with a Which product are you enquiring about? Credit card or Virgin Money Spot? text is shown on user <user message> message
    And Card with a buttons Credit card; Spot is shown on user <user message> message
    When User click Credit card button in the card on user message <user message>
    Then User have to receive 'The Virgin Money credit card is a no fees card. So we don't charge you any recurring fees for owning the card. We also don't charge you for swiping the card when making local purchases.\n\nThese are the only fees that apply:\nOnce-off initiation fee: R166.45\nATM enquiries and mini statements: R7.57\nATM withdrawals (local): R24.21\nATM cash deposits: R35.31\nCheque and cash deposits at any Absa branch: R60.53\nCash withdrawals at any Absa branch: R61.54\nLost card administration fee R166.45 (we may charge a replacement card fee or lost card administration fee at our own discretion)\nCard replacement fee R166.45 (we may charge a replacement card fee or lost card administration fee at our own discretion)\nNotifyMe: R0.55\nCurrency conversion fee: 2.75% (of the Rand value of the transaction) applies to any transaction outside of South Africa\nCashback at point-of-sale: R4.54' text response for his 'Credit card' input
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


