@no_widget
Feature: Dashboard: Settings: Business Profile : Logo

  Background:
    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Upload: photo for tenant

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1931")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: To verify that Incorrect/random favicon image
  should not appear when logo image is removed from Business Details

    Then Verify Logo is uploaded
    When Delete 'Company Logo'
    Then Verify Logo is deleted

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2643")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify that the logo can be set up for tenant

    Then Verify Logo is uploaded
    When Agent click 'Save changes' button
    Then Tenant logo is shown on Chat desk
#    cleanup
    When Delete 'Company Logo'
    Then Verify Logo is deleted
    And Agent click 'Save changes' button



