@no_widget
@Regression
Feature: Dashboard: Settings: Business Profile : Logo

  Background:
    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Upload: photo for tenant

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1931")
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: To verify that Incorrect/random favicon image should not appear when logo image is removed from Business Details

    Then Verify Logo is uploaded
    When Delete 'Company Logo'
    Then Verify Logo is deleted

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2643")
  @delete_tenant_logo
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify that the logo can be set up for tenant

    Then Verify Logo is uploaded
    When Agent click 'Save changes' button
    Then Tenant logo is shown on Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2488")
  @delete_tenant_logo
  Scenario: CD:: Dashboard:: Adding tenant new image

    Then Verify Logo is uploaded
    When Agent click 'Save changes' button
    Then Tenant logo is shown on Chat Desk
    When I select Touch in left menu and Agent Desk in submenu
    Then Tenant logo is shown on Chat Desk