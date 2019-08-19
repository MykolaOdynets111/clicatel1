Feature: Customer 360 from widget

  Scenario: User's info presence on chatdesk (touch user)
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Correct touch client details are shown
    And Tab with user info has "Customer 360" header

  @Issue("https://jira.clickatell.com/browse/TPLAT-4379")
  Scenario: Editing Customer 360 info (touch user)
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    And Click 'Edit' button in Customer 360 view
    When Fill in the form with new touch customer 360 info
    And Click 'Save' button in Customer 360 view
    Then Touch customer info is updated on backend
    And Correct touch client details are shown
    And New info is shown in left menu with chats
    And Customer name is updated in active chat header


