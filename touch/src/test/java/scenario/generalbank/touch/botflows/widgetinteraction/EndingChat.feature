# ToDo: For now new session management is tested. Review after it is stabilized
# Feature: Chat session should be automatically ended after after bot's response
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Session should be terminated after bot's response
#    When User enter Account Balance into widget input field
#    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
#    And User session is ended
