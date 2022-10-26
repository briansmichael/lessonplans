@LessonPlans
Feature: LessonPlans
  As a user
  I want to interact with lesson plans
  So that I might know what I need to learn

  Scenario Outline: Create a new lesson plan
    Given I am an authenticated user
    And I have a lesson plan
    And The lesson plan has title with <number> characters
    And The lesson plan has summary with <number> characters
    When I submit the lesson plan
    Then I should receive a success response

    Examples:
      | number |
      | 255    |
      | 1      |
      | 25     |
      | 52     |
      | 250    |
      | 205    |

  Scenario Outline: Create a lesson plan without required data
    Given I am an authenticated user
    And I have a lesson plan
    And The lesson plan has title with <number> characters
    When I submit the lesson plan
    Then I should receive an InvalidPayloadException

    Examples:
      | number |
      | 0      |
      | 256    |

  Scenario: Get a lesson plan
    Given I am an authenticated user
    And A lesson plan exists
    When I get the lesson plan
    Then I should receive a success response
    And A lesson plan should be received

  Scenario: Update an existing lesson plan
    Given I am an authenticated user
    And A lesson plan exists
    And The lesson plan has title with 50 characters
    And The lesson plan has summary with 45 characters
    When I submit the lesson plan for update
    Then I should receive a success response

  Scenario: Delete a lesson plan
    Given I am an authenticated user
    And A lesson plan exists
    When I delete the lesson plan
    Then I should receive a success response
    And The lesson plan should be removed

  Scenario Outline: Create a lesson plan as an unauthenticated user
    Given I have a lesson plan
    And The lesson plan has title with <number> characters
    And The lesson plan has summary with <number> characters
    When I submit the lesson plan
    Then I should receive an unauthenticated response

    Examples:
      | number |
      | 15     |

  Scenario: Get a lesson plan as an unauthenticated user
    Given A lesson plan exists
    When I get the lesson plan
    Then I should receive an unauthenticated response

  Scenario: Update an existing lesson plan as an unauthenticated user
    Given A lesson plan exists
    And The lesson plan has title with 50 characters
    And The lesson plan has summary with 45 characters
    When I submit the lesson plan for update
    Then I should receive an unauthenticated response

  Scenario: Delete a lesson plan as an unauthenticated user
    Given A lesson plan exists
    When I delete the lesson plan
    Then I should receive an unauthenticated response

  Scenario: Get all lesson plans as an unauthenticated user
    Given A lesson plan exists
    When I get all lesson plans
    Then I should receive an unauthenticated response

  Scenario: Get all lesson plans
    Given I am an authenticated user
    And A lesson plan exists
    When I get all lesson plans
    Then I should receive a success response

