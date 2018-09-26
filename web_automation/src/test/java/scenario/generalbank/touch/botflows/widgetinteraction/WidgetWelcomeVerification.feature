@smoke
Feature: Widget Welcome flow

  After widget opening and connection user should see basic info about tenant and welcome message and first card

  Scenario: User is shown info about tenant and welcome messages
    Given User select General Bank Demo tenant
    And Click chat icon
    Then User sees name of tenant: General Bank Demo and its short description in the header
#    And Welcome message with correct text is shown
#    And Welcome card with correct text and button "Chat to us" is shown
