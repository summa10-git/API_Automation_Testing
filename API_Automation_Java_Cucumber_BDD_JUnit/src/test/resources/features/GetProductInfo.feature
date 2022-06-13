

Feature: Fetch product info from my application
  I want to fetch all the products in my application utilising GET request

  @get
  Scenario: Get product info based on product id
    Given My app is up and running
    When I search product with id 1234
    Then API returns response with status code 200
    And Validate id is displayed as 1234
    And Validate type is displayed as "Type1"
    And Validate category is displayed as "Category1"


  