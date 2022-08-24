Feature: General Bank FAQ: "I've forgotten my Mobile Banking PIN?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                        |expected response|
      |I've forgotten my Mobile Banking PIN? | To reset your Mobile Banking PIN, visit your nearest branch with an identification document or Global One card .|