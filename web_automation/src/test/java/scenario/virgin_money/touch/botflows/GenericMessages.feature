Feature: VM flow regarding General messages

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: General messages flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName} is there something we can help you with?' text response for his '<user message>' input
    Then User have to receive 'Is there something we can help you with?' text response for his '<user message>' input
    Examples:
      |user message       |
      |Thumbs up emoticon |
      |Emoji              |
      |More               |
      |More info          |
      |like to know more  |
      |ok                 |
      |Need help          |
      |Hi                 |
      |Hello              |

  Scenario Outline: General messages flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName} sorry not right now but watch this space ;) can we perhaps offer you a credit card instead? Click on the link below and then click 'apply now'. https://www.virginmoney.co.za/credit-card/' text response for his '<user message>' input
    Then User have to receive 'Sorry not right now but watch this space ;) can we perhaps offer you a credit card instead? Click on the link below and then click 'apply now'. https://www.virginmoney.co.za/credit-card/' text response for his '<user message>' input
    Examples:
      |user message        |
      |Loan queries        |
      |Easy Money          |
      |Loan                |
      |Do you offer loans  |
      |Personal loan       |
      |Borrow money        |
      |Need money          |

