Feature: Dashboard: Live Customers

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-36675")
  Scenario: Verify if supervisor can hover over on Live chats per channel to see the number of live chats per channel
    When I login as admin of Standard Billing
    And Agent has no active chats
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see no live chats message in Live Chats by Channel
    And User select Standard Billing tenant
    When Click chat icon
    And User enter connect to agent into widget input field
    Then Admin should see Web Chat chart in Live Chats by Channel
    And Verify admin can see number of live chats per channel when hover over web chat

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-36673")
  Scenario: Verify if supervisor can hover over on General Sentiment to see the number of sentiments there are per channel
    Given User select Standard Billing tenant
    When I login as admin of Standard Billing
    And Click chat icon
    And User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Verify admin can see number of sentiments when hover over web chat under General sentiment per channel

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-36671")
  @Regression
  Scenario: Verify if supervisor is able to switch between live customer and customer history
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see live customers section
    When Admin click on Customers History on dashboard
    Then Admin should see customer history section

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-36674")
  Scenario: Verify if supervisor can hover over on Attended Vs Unattended to see the number of chats that are attended by agent or not
    Given User select Standard Billing tenant
    When I login as admin of Standard Billing
    And Click chat icon
    And User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Verify admin can see number of attended vs unattended chats when hover over web chat