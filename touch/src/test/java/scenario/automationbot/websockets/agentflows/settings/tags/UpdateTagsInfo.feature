@no_widget
@Regression
Feature: Dashboard: Settings: Chat Tags : Update tags info

  @orca_api
    @chat_preferences
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2764")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2779")
  Scenario Outline: CD :: <channelType> :: Close  Chat :: Verify if tag Usage updates when Agent adds tag to Note when closing at chat
    Given I login as Agent of Automation Bot
    And agentFeedback tenant feature is set to true for Automation Bot
    And Setup ORCA <channelType> integration for Automation Bot tenant
    When Send connect to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Agent click "End chat" button
    And End chat popup for Agent should be opened
    And Agent select Auto_Tag_2 tag
    And Agent click 'Close chat' button
    And I select Touch in left menu and Dashboard in submenu
    Then Navigate to Chat Tags page
    And Verify Last Used column data is updated for tag Auto_Tag_2
    Examples:
      | channelType | userType |
      | SMS         | sms      |
      | Whatsapp    | orca     |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2821")
  Scenario: CD :: Dashboard :: Configure :: Settings :: Chat Tags ::
  Verify if the edited tags are not delinked from linked chats

    Given I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page
    Then Rename tag Auto_Tag_2 to Auto_Tag_22 and verify column Usage value is unchanged
#    clean up
    And Rename Auto_Tag_22 tag to Auto_Tag_2