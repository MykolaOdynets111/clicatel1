Feature: VM flow regarding Quote

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Quote: Credit card" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, are you looking for an Insurance quote? Or would you like to apply for a credit card? text is shown on user <user message> message
    Then Card with a Are you looking for an Insurance quote? Or would you like to apply for a credit card? text is shown on user <user message> message
    And Card with a buttons Credit card; Insurance is shown on user <user message> message
    When User click Credit card button in the card on user message <user message>
    Then User have to receive ' To apply for a credit card online, click on the link below and then click 'apply now'. https://www.virginmoney.co.za/credit-card/' text response for his 'Credit card' input
    Examples:
      |user message                     |
      |Applying for one of our products |
      |I would like to apply            |
      |Quote                            |
      |Apply                            |
      |Where do I apply                 |
      |Where are your application forms |
      |Quote please                     |


  Scenario Outline: "Quote: Insurance" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, are you looking for an Insurance quote? Or would you like to apply for a credit card? text is shown on user <user message> message
    Then Card with a Are you looking for an Insurance quote? Or would you like to apply for a credit card? text is shown on user <user message> message
    And Card with a buttons Credit card; Insurance is shown on user <user message> message
    When User click Insurance button in the card on user message <user message>
#    Then User have to receive 'Hi ${firstName}, are you interested in a car, funeral or household insurance quote? Did you know you can get an online quote in under 10 minutes on our website by clicking on this link https://virg.in/JKP' text response for his 'Insurance' input
    Then User have to receive 'Are you interested in a car, funeral or household insurance quote? Did you know you can get an online quote in under 10 minutes on our website by clicking on this link https://virg.in/JKP' text response for his 'Insurance' input
    Examples:
      |user message                     |
      |Applying for one of our products |
      |I would like to apply            |
      |Quote                            |
      |Apply                            |
      |Where do I apply                 |
      |Where are your application forms |
      |Quote please                     |


