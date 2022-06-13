package stepdefs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class stepdefs_Post_Request {
	
	//RequestSpecification _REQ_SPEC;
	//Response _RESPONSE;
	//ValidatableResponse _VALIDATABLE_RESP;
	
	Scenario _SCN;
	
	String unique_cat_name;
	String unique_cat_id;
	Scenario_Context context;
	
	public stepdefs_Post_Request(Scenario_Context context) {
		
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
		    	context.set_REQ_SPEC(_REQ_SPEC);
		    	_SCN.write("API base uri:"+baseUri);
		    	
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		}
		
		@When("I submit post request to create category with unique category name and id")
		public void i_submit_post_request_with_unique_name_and_id() {
			// Body
			JSONObject body_json = new JSONObject();
			unique_cat_name = GetRandomString(5);
			unique_cat_id = GetRandomString(4);
			body_json.put("name", unique_cat_name);
			body_json.put("id", unique_cat_id);
			
			// Header
		
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			
			
			Response _RESPONSE = context.get_REQ_SPEC().headers(headers).body(body_json).when().post("/categories");
			context.set_RESPONSE(_RESPONSE);
		}
		
		
		@Then("API returns response with status code {int}")
		public void api_returns_response_with_status_code(Integer status_code) {
			Response _RESPONSE = context.get_RESPONSE();
			_RESPONSE.then().assertThat().statusCode(status_code);
		}
			
		@Then("A new category is created in the system")
		public void new_category_is_created_in_the_system() {
			Response _RESPONSE = context.get_RESPONSE();
			_RESPONSE.then().assertThat().body("name", equalTo(unique_cat_name));
			_RESPONSE.then().assertThat().body("id", equalTo(unique_cat_id));
		}
		
		// To generate random key			
		public String GetRandomString(int n) {
			// Lower limit for lower case letter
			int lower_limit = 97;
			
			// Upper limit for lower case letter
			int upper_limit = 122;
			
			Random random = new Random();
			
			// Creating a string buffer to store the result
			StringBuffer r = new StringBuffer(n);
			
			for (int i=0; i<n; i++) {
				
				// taking a random value between 97 & 122
				int nextRandomChar = lower_limit
						+(int)(random.nextFloat()*(upper_limit - lower_limit+1));
				
				r.append((char)nextRandomChar);
				
				
			}
			// Return the resultant string
			return r.toString();
			
		}
		
		@After 
		public void afterHook(Scenario s) {
			Response _RESPONSE = context.get_RESPONSE();
			this._SCN = s;
			if (_RESPONSE==null) {
				
				_SCN.write("No response received");
			}
			else {
				_SCN.write("Response:"+_RESPONSE.asString());
			}
		}

}
