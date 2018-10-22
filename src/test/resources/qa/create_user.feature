Feature: Create user
  Create a user on https://www.etsy.com/ and validate it


  Scenario: Create user
    Given webpage https://www.etsy.com/ is loaded
    When click on Register
    And fill in email address, first name and password
    Then user should be logged in

  Scenario: validate user
    Given webpage https://www.etsy.com/ is loaded
    When click Sign in
    And fill in email address, password
    Then user should be logged in

  Scenario: add stuff to cart
    Given webpage https://www.etsy.com/ is loaded
    When Search for Sketchbook
    And Sort results by price ascending
    And Validate items are sorted
    And Add most expensive item to cart
    And Search for turntable mat
    And Add any turntable mat to cart
    Then Validate cart content