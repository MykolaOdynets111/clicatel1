@smoke
Feature: Agent should see active chat after page was refreshed

  Verification of basic communication between user and agent

  Background:
    Given I login as agent of General Bank Demo
    Given User opens General Bank Demo tenant page
    And Click chat icon

  Scenario: Verify user chat remains in chatdesk after refresh
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent refreshes the page
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with chat to support user's message
