package stepdefs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Stepdef_Get_Request_Price {
	
	Scenario _SCN;
	//ValidatableResponse _VALIDATABLE_RESP;
	Scenario_Context context;
	
	String _categoty;
	
	public Stepdef_Get_Request_Price(Scenario_Context context) {
		this.context = context;
		
	}
	
	//Hooks
		@Before
		public void SetUp(Scenario s) {
			this._SCN = s;
			_SCN.write("Scenario started:"+_SCN.getName());
		}
		
		@Given("My app is up and running")
		public void my_app_is_up_and_running() {
		    try {
		    	Properties prop = new Properties();
		    	FileInputStream input;
		    	input = new FileInputStream("src/test/resources/config.properties");
		    	prop.load(input);
		    	String baseUri = prop.getProperty("base_uri");
		    	RequestSpecification _REQ_SPEC = given().baseUri(baseUri);
		    	context.set_REQ_SPEC(_REQ_SPEC); //Dependency Injection
		    	_SCN.write("API base uri:"+baseUri);
		    	
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		}
		
		@When("I send request to fetch pricelist for category {String}")
		public void i_send_request_to_fetch_pricelist_for_category(String category) {
			Response _RESPONSE = context.get_REQ_SPEC().when().get("/pricelist/"+category); //Dependency Injection
			context.set_RESPONSE(_RESPONSE); //Dependency Injection
			_categoty = category;
			_SCN.write("When GET Request is sent to fetch pricelist for category: "+category);
			
		}
	
		@Then("API returns pricelist in descending order")
		public void api_returns_pricelist_in_descending_order() {
			Response _RESPONSE = context.get_RESPONSE();

			List<Float> list = _RESPONSE.jsonPath().getList("category.price");
			_SCN.write("Price returned:"+list.toString());
			
			// Check for sorting in descending order
			boolean isSortedDesc = true;
			
			for (int i=1;i<list.size();i++) {
				if(list.get(i-1).compareTo(list.get(i))<0) {
					isSortedDesc = false;
					break;
				}
			}
			
			if (isSortedDesc ==true) {
				
				_SCN.write("Price returned in descending order");
				org.hamcrest.MatcherAssert.assertThat(true, is(true));
			}
			else {
				
				_SCN.write("Price NOT returned in descending order");
				org.hamcrest.MatcherAssert.assertThat(false, is(true));
			}
		}
	
	

}
