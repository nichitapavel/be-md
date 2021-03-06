Feature: QA
  QA code challenge:
    - create and validate user
    - add products and validate cart


  Scenario Outline: Create and validate user
    Given webpage https://www.etsy.com/ is loaded
    When click on Register
    And fill in <email>, <user> and <password>
    Then <user> should be logged in

  Examples:
    | user | email | password |
    | "daniel" | "hiasjhbdqlaksjdasc@yahoo.com" | "mEMA6K7kVe7H" |


  Scenario Outline: Validate user
    Given webpage https://www.etsy.com/ is loaded
    When click Sign in
    And fill in <email> and <password>
    Then <user> should be logged in

  Examples:
    | user | email | password |
    | "daniel" | "hiasjhbdqlaksjdasc@yahoo.com" | "mEMA6K7kVe7H" |


  Scenario Outline: Add products and validate cart
    Given webpage https://www.etsy.com/ is loaded
    When Search for <product 1>
    And Sort results by price ascending
    And Validate items are sorted
    And Add most expensive product to cart
    When Search for <product 2>
    And Add any product to cart
    Then Validate cart content

  Examples:
    | product 1 | product 2 |
    | "Sketchbook" | "turntable mat" |
