Feature: using Background keyword

  Background:

    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "page1"

  @tag01
  Scenario: Sample Scenario
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "playwrightTesting"

  @tag02
  Scenario: Sample Scenario
    And user switches to page "sample"
    Given user calls business component "sampleMethod" from location "playwrightTesting"