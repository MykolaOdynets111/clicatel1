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
    Then User see NPS survey form
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