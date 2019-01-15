Feature: General Bank FAQ: "How will I receive my prepaid electricity token?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                      |expected response|
      |How can I receive my prepaid electricity token?  |Hi ${firstName}. It'll be displayed on your cellphone screen and will also be sent to you by SMS.|