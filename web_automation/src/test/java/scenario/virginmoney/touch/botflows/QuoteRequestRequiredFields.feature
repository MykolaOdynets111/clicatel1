Feature: Quote request: required user input fields

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario:  Quote request user info required field
    When User enter quote request into widget input field
    Then Card with a Sure! Please, tell us who we are chatting to? text is shown on user quote request message
    When User click 'Submit' button in the card after user message: quote request
    Then 3 field required errors is shown in personal info input card on user message: quote request
    When User fill in field Last Name with 'AQA' in card on user message: quote request
    And User click 'Submit' button in the card after user message: quote request
    Then 2 field required errors is shown in personal info input card on user message: quote request
    When User fill in field Contact Number with '54545645' in card on user message: quote request
    And User click 'Submit' button in the card after user message: quote request
    Then 1 field required error is shown in personal info input card on user message: quote request
    When User fill in field Email with 'aqa1@i.aqa' in card on user message: quote request
    And User click 'Submit' button in the card after user message: quote request
    Then No required field errors are shown in card on user message quote request
