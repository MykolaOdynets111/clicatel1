Feature: Quote request Auto Insurance flow

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario:  Quote request -> Auto Insurance, not South Africa citizen flow
    When User enter quote request into widget input field
    Then Card with a Hi ${firstName}. Sure! Please, tell us who we are chatting to? text is shown on user quote request message
    When User submit card with personal information after user's message: quote request
    Then Card with a Hi ${firstName_qr}. Would you like a quote on car or funeral insurance? text is shown after user personal info input
    And Card with a buttons Auto insurance; Funeral insurance is shown after user personal info input
    When User click Auto insurance button in the card on user message personal info
    Then Card with a For the next step we need to confirm a few things: text is shown after user Auto insurance input
    Then Text 'Have you ever been sequestrated or liquidated (meaning the court has had to sell your stuff to pay off your debts)?' is shown above the buttons in the card on user Auto insurance input above
    And Card with a buttons Yes; No is shown on user Auto insurance message
    When User click No button in the card on user message Auto insurance
    Then Card with a For the next step we need to confirm a few things: text is shown after user No input
    Then Text 'Have you or any of the main drivers made more than 3 claims in the past 12 months?' is shown above the buttons in the card on user No input above
    And Card with a buttons Yes; No is shown on user No message
    When User click No button in the card on user message No
    Then Card with a We're almost done. One last thing: text is shown after user No input
    Then Text 'Do you, and anyone you intend to insure on this policy, have a valid SA ID and are you a South African citizen?' is shown above the buttons in the card on user No input above
    And Card with a buttons Yes; No is shown on user No message
    When User click No button in the card on user message No
    Then User have to receive 'Oh no. We can only insure South African citizens' text response for his 'No' input

  Scenario:  Quote request -> Auto Insurance, South Africa citizen flow
    When User enter Quote Request into widget input field
    Then Card with a Hi ${firstName}. Sure! Please, tell us who we are chatting to? text is shown on user Quote Request message
    When User submit card with personal information after user's message: Quote Request
    Then Card with a Hi ${firstName_qr}. Would you like a quote on car or funeral insurance? text is shown after user personal info input
    And Card with a buttons Auto insurance; Funeral insurance is shown after user personal info input
    When User click Auto insurance button in the card on user message personal info
    Then Card with a For the next step we need to confirm a few things: text is shown after user Auto insurance input
    Then Text 'Have you ever been sequestrated or liquidated (meaning the court has had to sell your stuff to pay off your debts)?' is shown above the buttons in the card on user Auto insurance input above
    And Card with a buttons Yes; No is shown on user Auto insurance message
    When User click No button in the card on user message Auto insurance
    Then Card with a For the next step we need to confirm a few things: text is shown after user No input
    Then Text 'Have you or any of the main drivers made more than 3 claims in the past 12 months?' is shown above the buttons in the card on user No input above
    And Card with a buttons Yes; No is shown on user No message
    When User click No button in the card on user message No
    Then Card with a We're almost done. One last thing: text is shown after user No input
    Then Text 'Do you, and anyone you intend to insure on this policy, have a valid SA ID and are you a South African citizen?' is shown above the buttons in the card on user No input above
    And Card with a buttons Yes; No is shown on user No message
    When User click Yes button in the card on user message No
    Then User have to receive 'Thanks for sharing your info. We will call you in the next X minutes with a quote. Or if you prefer, you can quote online at https://www.virginmoneyinsurance.co.za/red-hot/red-hot' text response for his 'Yes' input

  Scenario:  Quote request -> Auto Insurance, more than 3 claims in the past 12 months flow
    When User enter Quote request into widget input field
    Then Card with a Hi ${firstName}. Sure! Please, tell us who we are chatting to? text is shown on user Quote request message
    When User submit card with personal information after user's message: Quote request
    Then Card with a Hi ${firstName_qr}. Would you like a quote on car or funeral insurance? text is shown after user personal info input
    And Card with a buttons Auto insurance; Funeral insurance is shown after user personal info input
    When User click Auto insurance button in the card on user message personal info
    Then Card with a For the next step we need to confirm a few things: text is shown after user Auto insurance input
    Then Text 'Have you ever been sequestrated or liquidated (meaning the court has had to sell your stuff to pay off your debts)?' is shown above the buttons in the card on user Auto insurance input above
    And Card with a buttons Yes; No is shown on user Auto insurance message
    When User click No button in the card on user message Auto insurance
    Then Card with a For the next step we need to confirm a few things: text is shown after user No input
    Then Text 'Have you or any of the main drivers made more than 3 claims in the past 12 months?' is shown above the buttons in the card on user No input above
    And Card with a buttons Yes; No is shown on user No message
    When User click Yes button in the card on user message No
    Then User have to receive 'Sorry you are not eligible for a quote. Please call us on 0861 50 6070 for more info' text response for his 'Yes' input

  Scenario:  Quote request -> Auto Insurance, sequestrated or liquidated flow
    When User enter quote request into widget input field
    Then Card with a Hi ${firstName}. Sure! Please, tell us who we are chatting to? text is shown on user quote request message
    When User submit card with personal information after user's message: quote request
    Then Card with a Hi ${firstName_qr}. Would you like a quote on car or funeral insurance? text is shown after user personal info input
    And Card with a buttons Auto insurance; Funeral insurance is shown after user personal info input
    When User click Auto insurance button in the card on user message personal info
    Then Card with a For the next step we need to confirm a few things: text is shown after user Auto insurance input
    Then Text 'Have you ever been sequestrated or liquidated (meaning the court has had to sell your stuff to pay off your debts)?' is shown above the buttons in the card on user Auto insurance input above
    And Card with a buttons Yes; No is shown on user Auto insurance message
    When User click Yes button in the card on user message Auto insurance
    Then User have to receive 'Sorry you are not eligible for a quote. Please call us on 0861 50 6070 for more info' text response for his 'Yes' input

