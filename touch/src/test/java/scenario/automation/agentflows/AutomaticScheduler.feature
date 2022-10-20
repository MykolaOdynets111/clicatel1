@no_widget
@no_chatdesk
@setting_changes
Feature: Automatic Scheduler

 @TestCaseId("https://jira.clickatell.com/browse/TPORT-3783")
  Scenario: Touch preference :: Verify if agent can enable/disable Automatic Scheduler
   Given I open portal
   And Login into portal as an admin of Automation account
   When I select Touch in left menu and Dashboard in submenu
   And Navigate to Preferences page
   When click off/on 'Automatic Scheduler'
   Then On backend AUTOMATIC_SCHEDULER status is updated for Automation
   When click off/on 'Automatic Scheduler'
   Then On backend AUTOMATIC_SCHEDULER status is updated for Automation





