@tie
Feature: VM: TIE sentiments and intent for Quote questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "quote" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                     |
      |Applying for one of our products |
      |I would like to apply            |
      |Quote                            |
      |Apply                            |
      |Where do I apply                 |
      |Where are your application forms |
      |Quote please                     |
