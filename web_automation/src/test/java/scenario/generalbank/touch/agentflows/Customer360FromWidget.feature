Feature: Customer 360 from widget

#  Scenario: User's info presence on chatdesk (touch user)
#    Given I login as agent of General Bank Demo
#    Given User select General Bank Demo tenant
#    And Click chat icon
#    When User enter chat to support into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Correct touch client details are shown

  Scenario: Editing Customer 360 info (touch user)
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Message chat to support shown like last message in left menu with chat
    Then Valid icon for webchat integration are shown in left menu with chat
    And Click 'Edit' button in Customer 360 view
    When Fill in the form with new touch customer 360 info
    And Click 'Save' button in Customer 360 view
    Then Touch customer info is updated on backend
    And Correct touch client details are shown
    And New info is shown in left menu with chats
    And Customer name is updated in active chat header


