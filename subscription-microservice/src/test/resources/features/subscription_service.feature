@SUBCRIPTION_MODULE
Feature: Subscription Module

  @CREATE
  Scenario: Create subscription without subscriptionRequestDTO
    When I create subscription
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create subscription with empty subscriptionRequestDTO
    Given I have a subscriptionRequestDTO
    When I create subscription
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create subscription with valid subscriptionRequestDTO
    Given I have a valid subscriptionRequestDTO
    When I create subscription
    Then I will receive an id
    
  @UPDATE
  Scenario: Update subscription without id
    Given I have a valid subscriptionRequestDTO
    When I update subscription
    Then I will receive a ConstraintViolationException
    
  @UPDATE
  Scenario: Update subscription with invalid id
    Given I have an invalid id
    Given I have a valid subscriptionRequestDTO
    When I update subscription
    Then I will receive a NotFoundException
    
  @UPDATE
  Scenario: Update subscription with valid id and without subscriptionRequestDTO
    Given I have a valid id
    When I update subscription
    Then I will receive a ConstraintViolationException
    
  @UPDATE
  Scenario: Update subscription with valid id and valid subscriptionRequestDTO
    Given I have a valid id
    Given I have a valid subscriptionRequestDTO
    When I update subscription
    Then I will receive no error

  @GET
  Scenario: Get subscription without id
    When I get subscription
    Then I will receive a ConstraintViolationException
    
  @GET
  Scenario: Get subscription with invalid id
    Given I have an invalid id
    When I get subscription
    Then I will receive a NotFoundException
    
  @GET
  Scenario: Get subscription with valid id
    Given I have a valid id
    When I get subscription
    Then I will receive a subscription
    
	@SEARCH
  Scenario: Search subscriptions
    When I search subscriptions
    Then I will receive a page of subscriptions
      
	@SEARCH
  Scenario: Search subscriptions with page size
    Given I have a pageSize: 1
		When I search subscriptions
    Then I will receive a page of subscriptions with size: 1
    
	@DELETE
  Scenario: Remove subscription with invalid id
    Given I have an invalid id
    When I remove subscription
    Then I will receive a NotFoundException

	@DELETE
  Scenario: Remove subscription with valid id
    Given I have a valid id
    When I remove subscription
    Then I will receive no error
    