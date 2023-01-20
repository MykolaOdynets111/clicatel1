@support_hours
@Regression
@no_widget
@orca_api
Feature: SMS ORCA :: Supervisor Desk Tickets

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Set agent support hours with day shift

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6056")
  Scenario: CD:: Supervisor Desk:: Tickets:: Supervisor_Desk-Tickets-Closed:: Verify if Supervisor is able to apply filter in the closed ticket tab
    Given Setup ORCA sms integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Update survey management chanel sms settings by ip for General Bank Demo
      | ratingEnabled | false        |
    And Send to agent message by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat sms on Supervisor desk
    And Agent see tickets from sms on Unassigned filter page
    And Agent closed ticket for sms
    And Agent select Closed filter on Left Panel
    And Agent clears search field and filters on Supervisor desk
    And Agent verify ticket is present for sms for 2 seconds
    And Agent select "SMS" in Chanel container and click "Apply filters" button
    Then Agent verify that only "sms" channel tickets chats are shown
    When Admin filter by 0 year 0 month and 0 days ago start date and 0 year 0 month and 0 days ago end date
    Then Agent verify ticket is present for sms for 2 seconds
    And Agent verify that only "sms" channel tickets chats are shown
    And Agent verify that only "Today" date tickets are shown in start date column
    And Agent verify that only "Today" date tickets are shown in end date column