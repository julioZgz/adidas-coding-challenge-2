@EMAIL_MODULE
Feature: Email Module

  @CREATE
  Scenario: Send email without emailRequestDTO
    When I send email
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Send email with empty emailRequestDTO
    Given I have a emailRequestDTO
    When I send email
    Then I will receive a ConstraintViolationException
    
  @CREATE
  Scenario: Create newsletter with valid emailRequestDTO
    Given I have a valid emailRequestDTO
    When I send email
    Then I will receive no error
    