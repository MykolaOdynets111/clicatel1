Feature: General Bank FAQ: "How much does General Bank MasterPass app cost?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                      |expected response|
      |How much does General Bank MasterPass app cost?  |There are no fees or charges to clients to use the MasterPass app.|
      |Need some help or assistance with the General Bank MasterPass app?| Contact us on our 24hr channels: T 0860 10 20 43 F +27 21 941 0770 E ClientCare@Generalbank.co.za|