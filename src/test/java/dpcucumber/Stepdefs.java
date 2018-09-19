package dpcucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import cucumber.api.java.en.Then;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;


public class Stepdefs {
    Properties prop = new Properties();

    private Response create_res;
    private Response all_res;
    private Response delete_res;
    private  String id;


    @Given("^a user want to get all team members$")
    public void a_user_want_to_get_all_team_members() throws Exception {

        FileInputStream files = new FileInputStream("src\\test\\java\\config\\env.properties");
        prop.load(files);
    }
    @When("^a request is sent$")
    public void a_request_is_sent() throws Exception {
        RestAssured.baseURI= prop.getProperty("BookingsHost");
        this.all_res = given().
                when().get("/bookings/all");
    }

    @Then("^all team members should be returned$")
    public void all_team_members_should_be_returned() throws Exception {
        assertEquals(200,all_res.statusCode());
    }

    @Given("^a team member's record$")
    public void a_team_member_s_record() throws Exception {

        FileInputStream files = new FileInputStream("src\\test\\java\\config\\env.properties");
        prop.load(files);
    }

    @When("^request to create a new team members$")
    public void request_to_create_a_new_team_members() throws Exception {
        RestAssured.baseURI= prop.getProperty("BookingsHost");
        this.create_res = given().header("Content-Type","application/json").
                body(Payload.getCreateBooking()).
                when().post("/bookings/create");
    }

    @Then("^a new team members should be created$")
    public void a_new_team_members_should_be_created() throws Exception {
        String res = this.create_res.asString();

        JsonPath js = new JsonPath(res);
        //int count = js.getInt("size");

        ArrayList<String> ids = js.get("id");

        this.id = String.valueOf(ids.get(ids.size()-1));

        System.out.println(this.id);

        assertEquals(200,create_res.statusCode());
    }

    @Given("^a team members id$")
    public void a_team_members_id() throws Exception {
        FileInputStream files = new FileInputStream("src\\test\\java\\config\\env.properties");
        prop.load(files);
    }

    @When("^request to delete this team members$")
    public void request_to_delete_this_team_members() throws Exception {

        String id = "3";
        RestAssured.baseURI= prop.getProperty("BookingsHost");
        this.delete_res = given().pathParam("id",id).
                when().get("/bookings/delete/{id}");
    }

    @Then("^this team members record should be deleted$")
    public void this_team_members_record_should_be_deleted() throws Exception {
        assertEquals(200,delete_res.statusCode());
    }
}
