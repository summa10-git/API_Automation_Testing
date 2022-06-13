Feature: Fetch pricelist info in descending order from a product category of my application
  I want to fetch price list of all the products of given product category in descending order from my application utilising GET request

  @get
  Scenario: Get pricelist in descending order for given product category
    Given My app is up and running
    When I send request to fetch pricelist for category "category1"
    Then API returns pricelist in descending order
