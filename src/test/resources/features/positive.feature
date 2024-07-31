Feature: Title of your feature

  @tag1
  Scenario: Sample Scenario for tag1
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "Page1"

  @tag2
  Scenario: Sample Scenario for tag2
    And user switches to page "sample"
    Given user calls business component "sampleMethod2" from location "automateUsingPlaywright"

    @demotag
  Scenario Outline: Title of your scenario outline

    Given user navigates to <url> using chrome browser


    Examples: 
      | url  |
      | https://www.amazon.in/ |