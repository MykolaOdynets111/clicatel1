Feature: VM flow regarding credit card

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Credit card" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName}, did you know you can apply for a card online by clicking on the link below and then clicking 'apply now'. https://www.virginmoney.co.za/credit-card/' text response for his '<user message>' input
    Then User have to receive 'Did you know you can apply for a card online by clicking on the link below and then clicking 'apply now'.\nhttps://www.virginmoney.co.za/credit-card/' text response for his '<user message>' input
    Examples:
      |user message            |
      |Apply for a credit card |
      |I want a card           |
      |apply for a credit card |



