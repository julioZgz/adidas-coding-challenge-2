@NEWSLETTER_MODULE
Feature: NewsLetter Module

  @CREATE
  Scenario: Create newsletter without newsLetterRequestDTO
    When I create newsletter
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create newsletter without campaignYear
    Given I have a newsLetterRequestDTO
    When I create newsletter
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create newsletter with invalid campaignYear
    Given I have a newsLetterRequestDTO
    Given I have an invalid campaignYear
    When I create newsletter
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create newsletter with valid campaignYear
    Given I have a newsLetterRequestDTO
    Given I have a valid campaignYear
    When I create newsletter
    Then I will receive an id
    
  @UPDATE
  Scenario: Update newsletter without id
    Given I have a newsLetterRequestDTO
    Given I have a valid campaignYear
    When I update newsletter
    Then I will receive a ConstraintViolationException
    
  @UPDATE
  Scenario: Update newsletter with invalid id
    Given I have an invalid id
    Given I have a newsLetterRequestDTO
    Given I have a valid campaignYear
    When I update newsletter
    Then I will receive a NotFoundException
    
  @UPDATE
  Scenario: Update newsletter with valid id and without newsLetterRequestDTO
    Given I have a valid id
    When I update newsletter
    Then I will receive a ConstraintViolationException
    
  @UPDATE
  Scenario: Update newsletter with valid id and invalid campaignYear
    Given I have a valid id
    Given I have a newsLetterRequestDTO
    Given I have an invalid campaignYear
    When I update newsletter
    Then I will receive a ConstraintViolationException
    
  @UPDATE
  Scenario: Update newsletter with valid id and valid campaignYear
    Given I have a valid id
    Given I have a newsLetterRequestDTO
    Given I have a valid campaignYear
    When I update newsletter
    Then I will receive no error

  @GET
  Scenario: Get newsletter without id
    When I get newsletter
    Then I will receive a ConstraintViolationException
    
  @GET
  Scenario: Get newsletter with invalid id
    Given I have an invalid id
    When I get newsletter
    Then I will receive a NotFoundException
    
  @GET
  Scenario: Get newsletter with valid id
    Given I have a valid id
    When I get newsletter
    Then I will receive a newsletter
    
	@SEARCH
  Scenario: Search newsletters
    When I search newsletters
    Then I will receive a page of newsletters
      
	@SEARCH
  Scenario: Search newsletters with page size
    Given I have a pageSize: 1
		When I search newsletters
    Then I will receive a page of newsletters with size: 1
    
	@DELETE
  Scenario: Remove newsletter with invalid id
    Given I have an invalid id
    When I remove newsletter
    Then I will receive a NotFoundException

	@DELETE
  Scenario: Remove newsletter with valid id
    Given I have a valid id
    When I remove newsletter
    Then I will receive no error
    