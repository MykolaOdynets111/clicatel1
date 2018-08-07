Feature: VM flow regarding Claims and premiums

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Claims and premiums: Funeral" flow should work for "user message" user message
    When User enter <user message> into widget input field
    Then Card with a Hi ${firstName}, are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    And Card with a buttons Funeral, Auto or household is shown on user <user message> message
    When User click Funeral button in the card on user message <user message>
    Then User have to receive 'The simple answer is no. We don't increase your premiums after you claim. In fact they may go down if we reduce the premium for the deceased life’s premium or change the plan after the claim. There are a number of things that can affect your premiums. You can find more info in your policy wording document (section 3, page 11). This is the docment we sent to you when you first became a customer.' text response for his 'Funeral' input

    Examples:
      |user message                             |
      |Do premiums go up after I claim          |
      |What happens to premiums after I claim   |
      |How much do premiums go up after I claim |
      |How does a claim change my premium       |


  Scenario Outline: "Claims and premiums: Auto or household" flow should work for "user message" user message
    When User enter <user message> into widget input field
    Then Card with a Hi ${firstName}, are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    And Card with a buttons Funeral, Auto or household is shown on user <user message> message
    When User click Auto or household button in the card on user message <user message>
    Then User have to receive 'Your premiums may go up if you claim. But don't worry, we'll give you a call or send you a notification by email before making any changes to your policy.' text response for his 'Auto or household' input

    Examples:
      |user message                             |
      |Do premiums go up after I claim          |
      |What happens to premiums after I claim   |
      |How much do premiums go up after I claim |
      |How does a claim change my premium       |


