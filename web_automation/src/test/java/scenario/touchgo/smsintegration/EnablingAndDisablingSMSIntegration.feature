#@portal
#@no_chatdesk
#Feature: Admin should be able to enable and disable SMS integration
#
#  Scenario: Switch OFF and ON SMS integration
#    When I open portal
#    And Login into portal as an admin of Standard Billing account
#    When I select Touch in left menu and Configure Touch in submenu
#    And Enable the SMS integration
#    Then Status of SMS integration is changed to "Active"
#    When Disable the SMS integration
#    Then Status of SMS integration is changed to "Not Active"