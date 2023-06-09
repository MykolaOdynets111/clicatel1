@portal
@no_widget
@no_chatdesk
@sign_up
Feature: Signing up the account

  Scenario: I should be able to sign up and activate new account
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
