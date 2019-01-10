#@smoke
#Feature: User should be able to continue previously started chat
#
#  Background:
#    Given User opens General Bank Demo tenant page for user testing_aqaTestUser
#    And Click chat icon
#
#  Scenario: User should be able to continue previously started conversation
#    When User enters message regarding account balance into widget input field
#    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his question regarding 'account balance'