Feature: Virgin Money FAQ fow should be started when user types "fag"

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  @Issue("https://jira.clickatell.com/browse/TPLAT-3607")
  Scenario: Verify if user can proceed with faq flow after his "faq" message
    When User enter faq into widget input field
    Then Card with a Hi ${firstName}. Select FAQ category text is shown on user faq message
    And Card with a buttons containing FAQ categories is shown after user faq input
    When User select random category in the card on user message faq
    Then Correct response is shown in the widget for selected category
