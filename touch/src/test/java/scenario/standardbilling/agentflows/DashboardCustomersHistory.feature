@no_widget
@Regression
Feature: Dashboard: Customer History

  @off_rating_whatsapp
  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1456")
  Scenario Outline: CD:: Dashboard: Dashboard-Customers_Overview:: Verify if admin can open Customers History with <surveyType> customer survey
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
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1486")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2437")
  Scenario Outline:  CD:: Survey:: Verify " no data to show now" should be shown in the CSAT column against the agent.
    When I open portal
    And I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | <channelTypeFilter>  | Past day  |
    Then Admin is able to see No data to report at the moment in the Customer Satisfaction against the agent
    And Admin see the message no data for Customer Satisfaction gauge if there is no available data
    Examples:
      | channelTypeFilter   |
      | SMS                 |
      | Apple Business Chat |
      | WhatsApp            |
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2955")
  @skip
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

  @no_chatdesk
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2338")
  Scenario: Dashboard:: Verify that if supervisor selects 'past day' date filter, reports should be displayed hourly
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