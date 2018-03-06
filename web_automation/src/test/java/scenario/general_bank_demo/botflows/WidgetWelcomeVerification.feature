@smoke
Feature: Widget Welcome flow

  After widget opening and connection user should see basic info about tenant and welcome message and first card

  Scenario: User is shown info about tenant and welcome messages
    Given User select General Bank Demo tenant
    And Click chat icon
    Then User sees name of tenant: General Bank Demo and its short description in the header
    And Welcome message Hi. What can I help you with? is shown
    And Welcome card with text Simply type in the 'send a message' box or select an option below and button "Chat to Support" is shown