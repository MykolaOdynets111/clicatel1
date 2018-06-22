@tie
Feature: Draft test to verify DB connection

  Scenario: I can connect to testing db
    Then I can connect to DB: testing, mc2

  Scenario: I can connect to integration db
    Then I can connect to DB: integration, mc2

  Scenario: I can connect to dev db
    Then I can connect to DB: dev, mc2

  Scenario: I can connect to qa db
    Then I can connect to DB: qa, mc2

  Scenario: I can connect to demo db
    Then I can connect to DB: demo, mc2