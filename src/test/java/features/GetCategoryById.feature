Feature: Get Category by ID

  Scenario: User calls API to get a Category
    Given query a category with catelogue set as false
    When a user retrieves the category by id
    Then the status code is 200
    And response includes the following
      | Name 	 		    | Carbon credits 			|
      | CanRelist		    | true			            |
    And response includes the promotion name as Gallery
      | Description 			| 2x larger image			|
