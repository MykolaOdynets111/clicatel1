@portal
@no_widget
@no_chatdesk
@sign_up
@close_account
Feature: Preferences

  Scenario: Verify default Tenant Preferences
    Given Portal Sign Up page is opened
    When I provide all info about new SignedUp AQA account and click 'Sign Up' button
    Then Login page is opened with a message that activation email has been sent
    And Activation ID record is created in DB
    When I use activation ID and opens activation page
    Then Page with a message "Your account has successfully been created!" is shown
    And Login into portal as an admin of SignedUp AQA account
    Then Portal Page is opened
    Then Landing pop up is shown
    And "Update policy" pop up is shown
    When Accept "Update policy" popup
    And Close landing popup
    Then Main portal page with welcome message is shown
    Then "Get started with Touch" button is shown
    When Click "Get started with Touch" button
    Then "Get started with Touch Go" window is opened
    When I try to create new SignedUp AQA tenant
    Then New SignedUp AQA tenant is created
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    Then All default values on Preferences page are correct
      | maximumChatsPerAgent         | 15     |
      | ticketExpiration             | 72     |
      | agentChatTimeout             | 0h 20m |
      | mediaFilesExpiration         | 90     |
      | InactivityTimeoutHours       | 2      |
      | pendingChatsAuto_closureTime | 8      |