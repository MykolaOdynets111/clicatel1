Feature: ABC ORCA :: Supervisor Desk

@agent_support_hours
@orca_api
@TestCaseId("https://jira.clickatell.com/browse/CCD-2848")
@Regression
Scenario: Supervisor desk:: Verify if supervisor can filter tickets by ticket status and apple channel filter option
Given Setup ORCA abc integration for Automation tenant
And Set agent support hours with day shift
When Send to agent message by ORCA
Given I open portal
And Login into portal as an admin of Automation account
And I select Touch in left menu and Supervisor Desk in submenu
When Agent select "Tickets" left menu option
And Agent search chat orca on Supervisor desk
Then Ticket from orca is present on All tickets filter page
And Select orca ticket checkbox
When Click 'Assign manually' button
Then 'Assign chat' window is opened
When I assign chat on Agent
And User select Assigned ticket type
Then Ticket from orca is present on Assigned filter page
When Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
Then Verify that only "apple-business-chat" tickets chats are shown