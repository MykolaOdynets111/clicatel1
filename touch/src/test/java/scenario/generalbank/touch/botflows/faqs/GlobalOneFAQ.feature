Feature: General Bank FAQ: "how do i get global one" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input            |expected response|
      |How to get global one? |It's easy and almost paperless just visit your nearest General Bank branch with the following: Identification document (for credit applications, you must be 18 years or older) Original proof of residential address (any of the approved documents in your name with your street address) Latest salary slip ( credit applications only) 3-month bank statement (credit applications only)|