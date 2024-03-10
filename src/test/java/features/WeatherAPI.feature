Feature: weathermap API 
Scenario Outline: A happy holidaymaker
Given I like to holiday in '<city>'
And I only like to holiday on '<day>'
When I look up the weather forecast
Then I receive the weather forecast
And the temperature is warmer than '10' degrees

Examples:
|city|day|
|Sydney|Monday|