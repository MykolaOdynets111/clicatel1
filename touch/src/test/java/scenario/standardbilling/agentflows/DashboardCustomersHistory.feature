@no_widget
@Regression
Feature: Dashboard: Customer History

  @no_chatdesk
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2427")
  Scenario: CD:: Dashboard: Verify if admin can filter Customers History report by channel and period
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | WhatsApp   | Past day  |
      | WhatsApp   | Past week  |
      | WhatsApp   | Past 2 weeks   |
      | WhatsApp   | Past 3 weeks    |
      | WhatsApp   | Past 4 weeks   |
      | Apple Business Chat   | Past day  |
      | Apple Business Chat   | Past week   |
      | Apple Business Chat   | Past 2 weeks   |
      | Apple Business Chat   | Past 3 weeks   |
      | Apple Business Chat   | Past 4 weeks   |
      | SMS   | Past day   |
      | SMS   | Past week   |
      | SMS   | Past 2 weeks   |
      | SMS   | Past 3 weeks   |
      | SMS   | Past 4 weeks   |

  @no_chatdesk
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2338")
  Scenario: CD::Dashboard:: Verify that if supervisor selects 'past day' date filter, reports should be displayed hourly
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Past day period
    Then All reports in graphs should be breakdown hourly

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1796")
  Scenario Outline: CD :: Dashboard :: Customer Overview :: Customer History :: Verify if customer satisfaction odometer for CSAT score and graph are presented correct values
    Given I login as agent of Standard Billing
    When I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by SMS channel
    And Admin filter Customers History by channel and period
      | <channelTypeFilter> | Past week |
    Then Admin is able to see the CSAT scale having down scale as 0% and upscale as 100%
    And Admin is able to see the y axis CSAT scale having down scale as 0 and upscale as 5
    Examples:
      | channelTypeFilter   |
      | Apple Business Chat |
      | WhatsApp            |
      | SMS                 |