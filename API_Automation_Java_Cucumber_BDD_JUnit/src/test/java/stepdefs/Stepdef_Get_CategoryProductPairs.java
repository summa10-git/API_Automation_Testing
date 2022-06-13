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

public class Stepdef_Get_CategoryProductPairs {

	Scenario _SCN;
	Scenario_Context context;
	
	String _cat;
	String _prod;
	
	int _product_id;
	
	public Stepdef_Get_CategoryProductPairs(Scenario_Context context) {
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
	
	@When("I search \"(.*)\" and \"(.*)\"$")
	public void i_search_categoryName_and_productName(String cat, String prod) {
		Response _RESPONSE = context.get_REQ_SPEC().when().get("/category/"+cat+"/product/"+prod); //Dependency Injection
		context.set_RESPONSE(_RESPONSE); //Dependency Injection
		_cat = cat;
		_prod = prod;
		_SCN.write("When GET Request is sent for category: "+_cat+" and product:"+_prod);
		
	}
	
	@Then("API returns response with status code {int}")
	public void api_returns_response_with_status_code(Integer status_code) {
		Response _RESPONSE = context.get_RESPONSE();
		_RESPONSE.then().assertThat().statusCode(status_code);
		_SCN.write("Status code returned as: "+ status_code);
		
	}
	
}	
