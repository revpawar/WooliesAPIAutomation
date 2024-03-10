package steps;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;

public class StepDefinition {

	public static Response response;
	public static RequestSpecification inputRequest;
	  private List<String> forecastDates;
	
	  
//	  
	@Given("I like to holiday in {string}")
	public void SetWeatherEndpoint(String city) {
			RestAssured.baseURI="http://openweathermap.org/forecast16";
		RestAssured.authentication=RestAssured.basic("revpawar@gmail.com", "*****");
		 RestAssured.requestSpecification = RestAssured.given().queryParam("api_key", "c62a82ac41cb650d171657d8f2ea2aeb");
//		RestAssured.baseURI="https://api.openweathermap.org/data/2.5/weather?q=" + city;
		
		 //put into maps if required		 
//		 Map<String,String> queryParam=new HashMap<String,String>();
//		 queryParam.put("q",city);
//		 queryParam.put("appid", "c62a82ac41cb650d171657d8f2ea2aeb");
		 
		String cityNameAPI= RestAssured.given().param("q", city).param("appid", "c62a82ac41cb650d171657d8f2ea2aeb").when()
				.get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).extract()
				.path("name").toString();
		if(cityNameAPI!=null)
		{
		   Assert.assertEquals(cityNameAPI, city);
		}
		else
			Assert.assertNotEquals(cityNameAPI,city);
		
			
	}
	
	
	  @And("I only like to holiday on {string}")
	    public void verifyWeatherForecast(String day) {
		  
			String timezoneID= RestAssured.given().param("q", "Sydney").param("appid", "c62a82ac41cb650d171657d8f2ea2aeb").when()
					.get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).extract()
					.path("timezone").toString();
			System.out.print(timezoneID);
			
			 TimeZone timeZone = TimeZone.getTimeZone(timezoneID);

		        // Create a Calendar object and set the timezone
		        Calendar calendar = Calendar.getInstance(timeZone);

		        // Get the day of the week
		        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		        // Convert the day of the week to a string representation
		        String dayOfWeekString = getDayOfWeekString(dayOfWeek);
		        System.out.print("Today is ");

		    	System.out.print(dayOfWeekString);
		    	if(dayOfWeekString!=null)
				{
				   Assert.assertEquals(dayOfWeekString, day);
				}
				else
					Assert.assertNotEquals(dayOfWeekString,day);
//		 
	  }

@When("I look up the weather forecast")
public void getWeatherForecast()
{
	

	
	  

}


@When("I receive the weather forecast")
public void setWeatherForecast()
{
	//assertion done 
	RestAssured.given().param("q", "Sydney").param("appid", "c62a82ac41cb650d171657d8f2ea2aeb").when()
			.get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).statusCode(200).body(notNullValue());

}



@Then("the temperature is warmer than {string} degrees")
public void temperatureValidation(String temp) {
	
	Float temperature= RestAssured.given().param("q", "Sydney").param("appid", "c62a82ac41cb650d171657d8f2ea2aeb").when()
			.get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).extract()
			.path("main.temp");
	  System.out.println("Rev response start " );
	  System.out.println(temperature);
	  double celcius =kelvinToCelsius(temperature);
	   if (celcius > Integer.parseInt(temp)) {
		
		   Assert.assertTrue(true, "Temperature is greater than 10 degrees Celsius");
 
} else {
	 Assert.assertFalse(true, "Temperature is lesser than 10 degrees Celsius");
}


}



public static double kelvinToCelsius(double kelvin) {
    // Convert Kelvin to Celsius using the formula: Celsius = Kelvin - 273.15
    return kelvin - 273.15;
}


private static String getDayOfWeekString(int dayOfWeek) {
    switch (dayOfWeek) {
        case Calendar.SUNDAY:
            return "Sunday";
        case Calendar.MONDAY:
            return "Monday";
        case Calendar.TUESDAY:
            return "Tuesday";
        case Calendar.WEDNESDAY:
            return "Wednesday";
        case Calendar.THURSDAY:
            return "Thursday";
        case Calendar.FRIDAY:
            return "Friday";
        case Calendar.SATURDAY:
            return "Saturday";
        default:
            return "Unknown";
    }

}}
	
	
	

