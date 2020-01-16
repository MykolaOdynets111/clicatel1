Feature: VM flow regarding Quote

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Quote" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
    Then User have to receive 'To apply for a credit card online, click on the link below and then click 'apply now'. https://www.virginmoney.co.za/credit-card/' text response for his '<user message>' input
    Examples:
      |user message                     |
      |Applying for one of our products |
      |I would like to apply            |
      |Quote                            |
      |Apply                            |
      |Where do I apply                 |
      |Where are your application forms |
      |Quote please                     |



