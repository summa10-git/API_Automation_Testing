package stepdefs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Scenario_Context {
	
	private RequestSpecification _REQ_SPEC;
	private Response _RESPONSE;
	
	public RequestSpecification get_REQ_SPEC() {
		return _REQ_SPEC;		
	}
	
	public void set_REQ_SPEC(RequestSpecification _REQ_SPEC) {
		this._REQ_SPEC	= _REQ_SPEC;
	}

	public Response get_RESPONSE() {
		return _RESPONSE;		
	}
	
	public void set_RESPONSE(Response _RESPONSE) {
		this._RESPONSE	= _RESPONSE;
	}

}
