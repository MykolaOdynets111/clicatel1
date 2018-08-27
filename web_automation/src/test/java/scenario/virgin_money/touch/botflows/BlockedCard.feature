Feature: VM flow regarding Blocked Card

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Blocked Card" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName}. Your security is very important to us. We will block a card from doing further transactions if our fraud department notices suspicious activity on the card. Many of these blocks happen automatically, so you may try and do a transaction before we can tell you about the block.' text response for his '<user message>' input
    Then User have to receive 'Your security is very important to us. We will block a card from doing further transactions if our fraud department notices suspicious activity on the card. Many of these blocks happen automatically, so you may try and do a transaction before we can tell you about the block.' text response for his '<user message>' input
    Examples:
      |user message                                                                               |
      |Why are clients not being notified when their cards have been put on “hold” or “blocked”?  |
      |Why did you block my card                                                                  |
      |Why wasn't I notified when my card was blocked                                             |
      |What notifications do you give for block card                                              |
      |Why is my card on hold?                                                                    |


