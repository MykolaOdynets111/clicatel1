@smoke
Feature: Bot answers regarding account

  Verification of communication between user and bot regarding account

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                       |expected response|
      |Do you offer business accounts?                   |We only offer accounts for Personal Banking; you can visit us for more info regarding our Personal Banking offering.                                                                                                                                                                                                                                                                                                                                          |
      |I want to open an account.                        |You may visit your nearest General Bank branch with your ID and Proof of Residence to open a Savings Account. For more information on opening an account you can visit us. To open an account you will need to visit your nearest General Bank branch with your ID document and Proof of Residence. When in the branch be sure to get the cellphone banking app.                                                                                      |
