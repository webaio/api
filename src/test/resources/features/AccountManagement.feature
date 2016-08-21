@account
Feature: As I user i would like to manage account
  Scenario: Configure site to track using javascript tracker
    Given configured account with name "Weba"
    And logged as administrator
    When I decide to create account with configuration:
    | name           | url            | timezone      |
    | Weba main site | http://weba.io | Europe/Warsaw |
    Then I should see configured tracker with given javascript code
