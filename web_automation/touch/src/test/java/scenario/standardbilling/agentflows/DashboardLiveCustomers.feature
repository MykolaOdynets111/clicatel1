Feature: Dashboard: Live Customers

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
  Scenario: Verify if supervisor is able to switch between live customer and customer history
    When I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see live customers section
    When Admin click on Customers History on dashboard
    Then Admin should see customer history section