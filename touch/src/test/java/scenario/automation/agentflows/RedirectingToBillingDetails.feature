@portal
@no_widget
@no_chatdesk
@touch_go

Feature: Admin of tenant with Starter plan should be redirected on the billing details page while buying new plan or configuring payable integration

  Background:
    When I open portal
    And Login into portal as an admin of Automation account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4743")
  Scenario: Admin should be redirected to the Billing Details when they are not added
    When Admin clicks 'Upgrade' button
    Then 'Billing Not Setup' pop up is shown
    When Admin clicks 'Setup Billing' button
    Then Billing Details page is opened

#commented according to Amrit
@skip
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5969")
  Scenario: Billing Details modal window should be displayed for Twitter integration if there is no billing info added
    When Admin select Touch in left menu and Configure Touch in submenu
    When Click 'Configure' button for Twitter integration
    Then 'Billing Not Setup' pop up is shown
    When Close 'Billing not setup' modal window
    Then 'Billing Not Setup' pop up is not shown
