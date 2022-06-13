package stepdefs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
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

public class stepdef_GET_Request_Product {
	
	//RequestSpecification _REQ_SPEC;
	//Response _RESPONSE;
	//ValidatableResponse _VALIDATABLE_RESP;
	Scenario _SCN;
	Scenario_Context context;
	
	int _product_id;
	
	public stepdef_GET_Request_Product(Scenario_Context context) {
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
	
	@When("I search product with id {int}")
	public void i_search_product_with_id(Integer id) {
		Response _RESPONSE = context.get_REQ_SPEC().when().get("/products/"+id); //Dependency Injection
		context.set_RESPONSE(_RESPONSE); //Dependency Injection
		_product_id = id;
		_SCN.write("When GET Request is sent for product id: "+_product_id);
		
	}
	
	@Then("API returns response with status code {int}")
	public void api_returns_response_with_status_code(Integer status_code) {
		Response _RESPONSE = context.get_RESPONSE();
		_RESPONSE.then().assertThat().statusCode(status_code);
		_SCN.write("Status code returned as: "+ status_code);
		
	}
	
	@Then("Validate id is displayed as {int}")
	public void validate_id_is_displayed_as(Integer id) {
		Response _RESPONSE = context.get_RESPONSE();
		_RESPONSE.then().body("id", equalTo(id));
		_SCN.write("Product Id validated");
	
	    
	}
	
	@Then("Validate type is displayed as {string}")
	public void validate_type_is_displayed_as(String type) {
		Response _RESPONSE = context.get_RESPONSE();
		_RESPONSE.then().body("type", equalTo(type));
		_SCN.write("Type displayed as:"+type);
	}

	@Then("Validate category is displayed as {string}")
	public void validate_category_is_displayed_as(String category) {
		Response _RESPONSE = context.get_RESPONSE();
		_RESPONSE.then().body("categories.name[0]", equalTo(category));
		_SCN.write("Category displayed as:"+category); 
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
