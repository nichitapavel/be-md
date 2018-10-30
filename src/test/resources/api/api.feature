Feature: API
  QA code challenge/API:
  - create a gist
  - get a gist and validate it's json scheme
  - delete a gist


  Scenario: Create a gist
    When creating a gist
    Then gist id was returned

  Scenario: Validate a gist
    When getting a gist
    Then should be valid

  Scenario: Delete a gist
    When deleting a gist
    Then gist should not be found
