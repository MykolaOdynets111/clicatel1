Feature: General Bank FAQ: "I lost/deleted the SMS with my electricity token. Can I view it again?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                        |expected response|
      |I lost/deleted the SMS with my electricity token. Can I view it again? |You can only view the last electricity token that you bought.|