Feature: General Bank FAQ: "I haven't used my account for a while. Why can't I access it?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                        |expected response|
      |I haven't used my account for a while. Why can't I access it? |All inactive accounts become dormant after 12 months. This means that the account will be frozen for security reasons and no further transactions can be made. To prevent this, simply transact on your account at least once every 12 months. If you'd like to reactivate a dormant account, visit your nearest branch with your ID book and an original proof of residential address .|