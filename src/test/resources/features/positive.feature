Feature: Title of your feature

    @tag
  Scenario: Sample Scenario
    #Given user calls business component "sampleMethod" from location "page1"
    And user switches to page "sample"
    Given user calls business component "launchScript" from location "page1"
    
    
    
    
    @demotag
  Scenario Outline: Title of your scenario outline
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |