Feature: Correct Category Product pairs

Scenario Outline: Retrieve proper category and product pairs
	Given My app is up and running
    When I search "<categoryName>" and "<productName>"
    Then API returns response with status code 200
	
Examples:
    | categoryName | productName |
    | category1234 | product1234 |
    | category5678 | product5678 |