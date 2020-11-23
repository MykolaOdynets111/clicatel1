Feature: Dashboard: Customer History

  Scenario: Dashboard: Verify if admin can open Customers History with NPS customer survey
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled | true        |
      | ratingType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |
    And User select Standard Billing tenant
    And I login as second agent of Standard Billing
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Second Agent has new conversation request
    When Second Agent click on new conversation request from touch
    Then Second Agent see conversation area with connect to Support user's message
    When Second Agent click "End chat" button
    Then End chat popup for second agent should be opened
    When Second Agent click 'Close chat' button
    Then User see NPS survey form
    When Submit survey form with Automation rate comment and 5 rate
    And I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Net Promoter Score, Live Chats by Channel, Past Sentiment graphs


  Scenario: Dashboard: Verify if admin can open Customers History with CSAT customer survey
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled | true        |
      | ratingType    | CSAT         |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    And User select Standard Billing tenant
    And I login as second agent of Standard Billing
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Second Agent has new conversation request
    When Second Agent click on new conversation request from touch
    Then Second Agent see conversation area with connect to Support user's message
    When Second Agent click "End chat" button
    Then End chat popup for second agent should be opened
    When Second Agent click 'Close chat' button
    Then User see CSAT survey form
    When Submit survey form with Automation rate comment and 5 rate
    And I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Customer Satisfaction, Live Chats by Channel, Past Sentiment graphs