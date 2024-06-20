Feature: Title of your feature

  @tag1
  Scenario: Sample Scenario
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "page1"

  @tag2
  Scenario: Sample Scenario
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "playwrightTesting"
    
    
    @demotag
  Scenario Outline: Title of your scenario outline
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |