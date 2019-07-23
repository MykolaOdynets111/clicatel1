Feature: General Bank FAQ: "Is my card information stored within the MasterPass wallet?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                  |expected response|
      |Is my card information stored within the MasterPass wallet?  | Hi ${firstName}. No, as soon as your card information has been entered into the wallet, your card number is automatically replaced by a token. This token will be used for transactions, keeping your card information private and secure at all times.|