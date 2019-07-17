@portal
@no_widget
@no_chatdesk
@delete_agent_on_failure
Feature: Resetting agent password

  Background:
    Given New tenant is successfully created
    Given Second agent of Updating AQA is successfully created

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4734")
  Scenario: Manage agent :: Reset password
    Given I open portal
    And Login into portal as an admin of Updating AQA account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for created user
    When I click "Reset password" page action button
    Then Notification popup with text Reset password email has been sent to <email> is shown for admin
    And Confirmation reset password Email arrives
    When Second agent opens confirmation URL
    Then Second agent redirected to the "Set new password" page
    When Second Agent provides new password and click Login
    Then Notification popup with text Password has been changed successfully is shown for Second agent
    When Login as second agent
    Then Second Agent logs in successfully


