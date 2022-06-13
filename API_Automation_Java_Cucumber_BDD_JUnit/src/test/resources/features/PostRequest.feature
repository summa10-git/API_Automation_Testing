@Post
Feature: Sample post request

  @Post
  Scenario: Create new category with category name and id
    Given My application is up and running
    When I submit post request to create category with unique category name and id
    Then The API returns the response with status code 201
    And A new category is created in the system
