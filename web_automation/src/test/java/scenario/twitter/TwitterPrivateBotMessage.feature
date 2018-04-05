@twitter
Feature: Communication with bot via messages

  Background:
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Bot answers on user messages
    When User sends twitter direct message "account balance"
