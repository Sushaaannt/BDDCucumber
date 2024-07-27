Feature: Title of your feature

  @tag1
  Scenario: Sample Scenario
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "page1"

  @tag2
  Scenario: Sample Scenario
    //And user switches to page "sample"
    Given user calls business component "userNavigatesToUrlUsingChromeBrowser" from location "GenericSteps"
    
    
    @demotag
  Scenario Outline: Title of your scenario outline

    Given user navigates to <url> using chrome browser


    Examples: 
      | url  |
      | https://www.amazon.in/ |