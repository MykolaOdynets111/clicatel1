Feature: Dashboard: Customer History

  Scenario Outline: Dashboard: Verify if admin can open Customers History with <ratingType> customer survey
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled | true          |
      | ratingType    | <ratingType>  |
      | ratingScale   | <ratingScale> |
      | ratingIcon    | NUMBER        |
    And User select Standard Billing tenant
    And I login as admin of Standard Billing
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent see conversation area with connect to Support user's message
    When Agent closes chat
    Then User see <ratingType> survey form
    When Submit survey form with Automation rate comment and 5 rate
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see <ratingName>, Live Chats by Channel, Past Sentiment graphs
    Examples:
      | ratingType | ratingName            | ratingScale |
      | NPS        | Net Promoter Score    | ZERO_TO_TEN |
      | CSAT       | Customer Satisfaction | ONE_TO_FIVE |

  @no_chatdesk @TestCaseId("https://jira.clickatell.com/browse/TPORT-50385")
  Scenario: Dashboard: Verify if admin can filter Customers History report by channel and period
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Webchat channel and Past day period
    Then Admin see all graphs filtered by Web Chat channel and Past day period
    And Admin filter Customers History by Facebook channel and Past week period
    Then Admin see all graphs filtered by Facebook channel and Past week period
    And Admin filter Customers History by Twitter channel and Past 2 weeks period
    Then Admin see all graphs filtered by Twitter channel and Past 2 weeks period
    And Admin filter Customers History by WhatsApp channel and Past 3 weeks period
    Then Admin see all graphs filtered by WhatsApp channel and Past 3 weeks period
    And Admin filter Customers History by Apple Business Chat channel and Past 4 weeks period
    Then Admin see all graphs filtered by Apple Business Chat channel and Past 4 weeks period

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3760")
  Scenario: Dashboard:: Verify if Snapshot displays chats which are currently active
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see no live chats message in Live Chats by Channel
    And User select Standard Billing tenant
    When Click chat icon
    And User enter connect to agent into widget input field
    Then Admin should see Web Chat chart in Live Chats by Channel

  @no_chatdesk @TestCaseId("https://jira.clickatell.com/browse/TPORT-3760")
  Scenario: Customer History:: Past sentiment graph:: Verify if past sentiment graph is empty if no data is available
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Apple Business Chat channel and Past day period
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
    When Submit survey form with Automation rate comment and 8 rate
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
  Scenario:  Dashboard: Verify if admin can see the message "No data to report at the moment" if there is no available CSAT Score data per period
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Apple Business Chat channel and Past day period
    Then Admin see the message no data for Customer Satisfaction graph if there is no available data