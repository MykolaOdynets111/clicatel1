@no_widget
Feature: Chat2Pay ::Chat2PayCustomerClosedChat

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118471")
  Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    And click on C2P Payment extension Option of Live Customer from left side menu
    And Agent Click on Chat to Pay popup option button from chat section
    And Agent Click on Send Chat to Pay 1321 and Link 122323 OrderNumber
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca