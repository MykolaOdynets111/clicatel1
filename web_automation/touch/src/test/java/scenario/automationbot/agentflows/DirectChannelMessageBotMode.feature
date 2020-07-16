Feature: Direct message auto responder

  @no_chatdesk
  Scenario: Direct message resetting for Bot mode tenant
    Given Taf contact_us_message message text is updated for Automation Bot tenant
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    When Wait for auto responders page to load
    And Agent click expand arrow for Enable Direct channel message auto responder
    And Click "Reset to default" button for Enable Direct channel message auto responder
    Then contact_us_message is reset on backend