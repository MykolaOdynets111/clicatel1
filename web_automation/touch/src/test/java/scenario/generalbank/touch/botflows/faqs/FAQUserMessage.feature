Feature: General Bank FAQ fow should be started when user types "fag"

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Verify if user can proceed with faq flow after his "faq" message
    When User enter faq into widget input field
    Then Card with a Select FAQ category text is shown on user faq message
    And Card with a buttons containing FAQ categories is shown after user faq input
    When User select random category in the card on user message faq
    Then Correct response is shown in the widget for selected category
