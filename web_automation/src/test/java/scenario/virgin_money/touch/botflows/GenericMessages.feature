Feature: VM flow regarding General messages

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: General messages flow should work for "<user message>" user message
    When User enter <user message> into widget input field
    Then User have to receive 'Hi ${firstName}. Is there something we can help you with?' text response for his '<user message>' input
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
    Then User have to receive 'Hi ${firstName}. Virgin Money in partnership with Capitec have affordable personal loans. Just click on the link below and complete the form to apply. https://loans.virginmoney.co.za/' text response for his '<user message>' input
    Examples:
      |user message        |
      |Loan queries        |
      |Easy Money          |
      |Loan                |
      |Do you offer loans  |
      |Personal loan       |
      |Borrow money        |
      |Need money          |

