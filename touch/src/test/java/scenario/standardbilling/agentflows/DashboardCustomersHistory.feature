@no_widget
Feature: Dashboard: Customer History

  @off_survey_management
    @off_rating_whatsapp
    @orca_api
    @start_orca_server
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1456")
  Scenario Outline: Dashboard: Verify if admin can open Customers History with <surveyType> customer survey
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
    And Update survey management chanel whatsapp settings by ip for Standard Billing
      | ratingEnabled | true          |
      | surveyType    | <surveyType>  |
      | ratingScale   | <ratingScale> |
      | ratingIcon    | NUMBER        |
    And Send connect to agent message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    Then Agent closes chat
    And Send 5 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see <ratingName>,Chats by Channel,Past Sentiment graphs
    Examples:
      | surveyType | ratingName            | ratingScale |
      | NPS        | Net Promoter Score    | ZERO_TO_TEN |
      | CSAT       | Customer Satisfaction | ONE_TO_FIVE |

  @no_chatdesk
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2427")
  @Regression
  Scenario: Dashboard: Verify if admin can filter Customers History report by channel and period
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

  @no_chatdesk @TestCaseId("https://jira.clickatell.com/browse/CCD-2437")
  Scenario: Customer History:: Past sentiment graph:: Verify if past sentiment graph is empty if no data is available
    When I open portal
    And I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | WhatsApp   | Past day  |
    Then Admin see the message no data for Past Sentiment graph if there is no available data

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45620")
  Scenario: Dashboard:: Verify that supervisor can check average CSAT surveys per selected duration of time and specific channel
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled | true       |
      | ratingType    | CSAT       |
      | ratingScale   | ONE_TO_TEN |
      | ratingIcon    | NUMBER     |
    And User select Standard Billing tenant
    And I login as admin of Standard Billing
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent see conversation area with connect to Support user's message
    When Agent closes chat
    Then User see CSAT survey form
    When Submit survey form with no comment and 8 rate
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Webchat channel
    Then Admin is able to see Customer Satisfaction graphs
    Then Admin is able to see the average CSAT survey response converted to 0-10
    And Admin filter Customers History by Past 4 weeks period
    Then Admin is able to see Customer Satisfaction graphs
    Then Admin is able to see the average CSAT survey response converted to 0-10

  @no_chatdesk @TestCaseId("https://jira.clickatell.com/browse/TPORT-50386")
  Scenario: Dashboard: Verify if admin can see the message "No data to report at the moment" if there is no available CSAT Score data per period
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Apple Business Chat channel and Past day period
    Then Admin see the message no data for Customer Satisfaction graph if there is no available data

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2955")
  Scenario: Dashboard:: Verify that if NPS surveys are categorize as Passives if webchat user chooses between 7 – 8
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled | true        |
      | ratingType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |
    And User select Standard Billing tenant
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Net Promoter Score graphs
    And Admin save the percentage for passives from NPS
    When I login as second agent of Standard Billing
    And Click chat icon
    And User enter connect to Support into widget input field
    Then Second agent has new conversation request
    When Second agent click on new conversation request from touch
    Then Second agent see conversation area with connect to Support user's message
    When Second agent closes chat
    Then User see NPS survey form
    When Submit survey form with no comment and 7 rate
    And Wait for 60 second
    And Agent refresh current page
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Net Promoter Score graphs
    And Admin see the percentage for passives from NPS is increased

  @no_chatdesk @TestCaseId("https://jira.clickatell.com/browse/TPORT-45595")
  Scenario: Dashboard:: Verify that if supervisor selects 'past day' date filter, reports should be displayed hourly
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Past day period
    Then All reports in graphs should be breakdown hourly

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1816")
  @Regression
  Scenario: CD:: SMS:: Customers Overview :: Verify if Supervisor can see SMS channel in the Customer History tab in Dashboard
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by SMS channel

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1796")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1819")
    @Regression
  Scenario Outline: CD:: Survey:: CSAT:: Dashboard:: Verify if customer satisfaction odometer for CSAT score is presented as 0% to 100% scale
    Given I login as agent of Standard Billing
    When I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | <channelTypeFilter> | Past week |
    Then Admin is able to see the CSAT scale having down scale as 0% and upscale as 100%
    And Admin is able to see the y axis CSAT scale having down scale as 0 and upscale as 5
    Examples:
      | channelTypeFilter   |
      | Apple Business Chat |
      | WhatsApp            |
      | SMS                 |

