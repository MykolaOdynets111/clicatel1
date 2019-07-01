#@without_tct
#Feature: OTP functionality
#  Scenario: Check Phone number and integration availability
#    Given I login as agent of Standard Billing
#    Given User with phone number select Standard Billing tenant
#    And Click chat icon
#    When User enter connect to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation request from touch
#    Then Agent see his phone number added into customer's profile
#    When Agent click on 'Send OTP' button in Customer 360
#    Then 'Verify phone' window is opened
#    And User's profile phone number displayed in 'Verify phone' input field
#    When Agent click on Send OTP button on 'Verify phone' window
#    Then 'Verify phone' window is closed
#    And Chat separator with OTP code displayed
#    And Message 'I have just sent...' with user phone number displayed in textarea
#    When Agent click on 'Re-send OTP' button in Customer 360
#    When Agent click on Send OTP button on 'Verify phone' window
#    And Chat separator with OTP code displayed
#    And Message 'I have just sent...' with user phone number displayed in textarea
#    And New OTP code is different from the previous one
#    When User refreshes the widget page
#    And Click chat icon
#    Then There is no OTP code response
#
#  Scenario: API calls for OTP
#    Given I login as agent of Standard Billing
#    Given User with phone number select Standard Billing tenant
#    When Agent send OTP message with API
#    And Click chat icon
#    When User enter connect to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation request from touch
#    Then Agent see his phone number added into customer's profile
#    Then Send SMS button is enabled on Chat header
#    And Verify button is displayed in Customer 360
#    And Re-send OTP button is displayed in Customer 360
#    When Agent click on 'Verify' button in Customer 360
#    Then 'Verified' label become visible
#    And SMS client-profile added into DB
#    And Verify button not displayed in Customer 360
#    And Re-send OTP button not displayed in Customer 360
#
#  Scenario: UI checks
#    Given I login as agent of Standard Billing
#    Given User with phone number select Standard Billing tenant
#    When Agent send OTP message with API
#    And Click chat icon
#    When User enter connect to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation request from touch
#    When Agent click on 'Verify' button in Customer 360
#    Then 'Verified' label become visible
#    And SMS client-profile added into DB
#    And Verify button not displayed in Customer 360
#    And Re-send OTP button not displayed in Customer 360
