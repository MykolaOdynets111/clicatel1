# Tests are obsolete because according to TPLAT-2547 and TPLAT-2283 session is automatically ended after
# bot's response and automatically started with user new message
# ToDo: add test on automatic session ending

#Feature: User should be able to end chat
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: User should be able to end chat by clicking button in touch menu
#    When User enter Account Balance into widget input field
#    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
#    When User click Touch button
#    Then "End chat" is shown in touch menu
#    When User select "End chat" from touch menu
#    Then User have to receive 'Thank you. Chat soon!' text response for his 'End chat' input
#
#  Scenario: User should be able to end chat using widget header End chat button
#    When User enter residential address into widget input field
#    Then User have to receive 'If you're a South African citizen and an employed individual, pensioner or student, please download and print this Fica Declaration form . Complete it with your landlord, home owner, college residence manager, hostel manager or cohabitant. Then bring it and the other required documents to the branch to open your account: Identification document Latest salary slip (credit applications only) 3-month bank statement (credit applications only) Remember that if the declarer is your cohabitant, they must also provide an original proof of their residential address , except where he or she is an existing General Bank client. It's not a requirement for the declarer to accompany the client to the branch but the client must obtain a copy of the declarer's identification document. If the declarer is an existing General Bank client, it is not necessary to obtain the abovementioned documents. For a list of the approved documents and further information, please download the Fica flyer .' text response for his 'residential address' input
#    And "End chat" button is shown in widget's header
#    When User click "End chat" button in widget's header
#    Then User have to receive 'Thank you. Chat soon!' text response for his 'End chat' input