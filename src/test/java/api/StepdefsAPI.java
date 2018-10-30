package api;


import cucumber.api.java.bs.A;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;


import static org.junit.Assert.assertNotNull;


public class StepdefsAPI {
    private ApiTest apitTest;

    /*
    =========================================================
                          API TESTING
    =========================================================
    */


    /*===================== Create a gist =================*/
    @When("^creating a gist$")
    public void create_a_gist() {
        new ApiTest().postGist();
    }

    @Then("^gist id was returned$")
    public void check_a_gist() {
        //assertNotNull(Gists.getInstance().getID());
        //new ApiTest().validateGist();
        //new ApiTest().deleteGist();
    }

    /*==================== Validate a gist ================*/
    @When("^getting a gist$")
    public void get_gist(){
        //new ApiTest().postGist();
        new ApiTest().validateGist();
    }

    @Then("^should be valid$")
    public void validate_gist() {
        //new ApiTest().validateGist();
        //new ApiTest().deleteGist();
    }

    /*================= Delete a gist =====================*/
    @When("^deleting a gist$")
    public void delete_gist() {
        new ApiTest().deleteGist();
    }

    @Then("^gist should not be found$")
    public void gist_not_found() {
        //new ApiTest().getDeletedGist();
    }

}
