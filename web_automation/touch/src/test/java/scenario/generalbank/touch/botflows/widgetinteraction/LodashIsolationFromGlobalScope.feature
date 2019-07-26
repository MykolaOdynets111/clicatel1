@smoke
Feature: Lodash isolation (related to TWEB-679)

  @lodash
  Scenario: Function should be returned after sending window.lodash into browser console
    Given User opens General Bank Demo tenant page
    When I send window.lodash into browser console it returns function
    When I send underscore: '_' into browser console it returns 'undefined' type

