@skip
@twitter
Feature: Communication with bot via tweet

  Background:
    Given Login to twitter
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Twitter: Bot answers on user tweet
    When User sends tweet regarding "Where can I find a branch?"
    Then Bot's answer arrives to twitter
    And User has to receive "Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch." answer from the bot as a comment on his initial tweet Where can I find a branch?