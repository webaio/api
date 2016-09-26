@account
Feature: As I user i would like to manage my account
  Scenario: Configuring site to track using javascript tracker
    Given configured account with name "Weba"
    And logged as an user with admin role
    When I decide to create site with configuration
    | Weba main site | http://weba.io | Europe/Warsaw |
    Then I should see configured tracker with given javascript code
  Scenario: Adding new user to my account
    Given configured account with name "Weba"
    And logged as an user with admin role
    When I decide to create new account with read only role
    | test@weba.io  | test      | David      | Bowie      |
    Then I should see create user with username "test@weba.io"
  Scenario: Promoting existing user to admin role
    Given configured account with name "Weba"
    And logged as an user with admin role
    When I promote user "test@weba.io" to role admin
    Then I should see user "test@weba.io" as a admin
