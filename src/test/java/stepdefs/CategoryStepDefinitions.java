package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.Before;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import io.restassured.RestAssured;

import java.util.Map;
import static java.lang.Boolean.parseBoolean;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CategoryStepDefinitions {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://api.tmsandbox.co.nz";
        RestAssured.basePath = "/v1/Categories/6327/Details.json";
    }

    @Given("query a category with catelogue set as (.*)")
    public void query_a_category_with_catalogue(boolean catalogue) throws Exception {
        request = given().queryParam("catelogue", catalogue);
    }

    @When("a user retrieves the category by id")
    public void a_user_retrieves_the_category_by_id() throws Exception{
        response = request.when().get();
    }

    @Then("the status code is (\\d+)")
    public void verify_status_code(int statusCode) throws Exception{
        json = response.then().statusCode(statusCode);
    }

    @And("response includes the following")
    public void response_includes(Map<String,String> responseFields) throws Exception{
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if(StringUtils.isNumeric(field.getValue())){
                json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            }
            else if (parseBoolean(field.getValue())){
                json.body(field.getKey(), equalTo(true));
            }
            else {
                json.body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }

    @And("response includes the promotion name as (.*)")
    public void response_contains_in_any_order(String name, Map<String,String> responseFields) throws Exception{
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
                json.body("Promotions.find { it.Name == '" + name + "' }." + field.getKey(), containsString(field.getValue()) );
        }
    }
}