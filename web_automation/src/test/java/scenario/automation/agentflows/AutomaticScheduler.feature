@no_widget
@no_chatdesk
Feature: Automatic Scheduler

 @TestCaseId("https://jira.clickatell.com/browse/TPORT-3783")
  Scenario: Touch preference :: Verify if agent can enable/disable Automatic Scheduler
   Given I open portal
   And Login into portal as an admin of Automation account
   When I select Touch in left menu and Touch preferences in submenu
   And Click "Chat Desk" nav button
   When click off/on 'Automatic Scheduler'
   Then On backend AUTOMATIC_SCHEDULER status is updated for Automation
   When click off/on 'Automatic Scheduler'
   Then On backend AUTOMATIC_SCHEDULER status is updated for Automation





