package api;


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


    /*=======================================================*/
    @When("^creating a gist$")
    public void create_a_gist() {
        new ApiTest().postGist();
    }

    @Then("^gist id was returned$")
    public void check_a_gist() {
        assertNotNull(Gists.getInstance().getID());
        new ApiTest().validateGist();
        new ApiTest().deleteGist();
    }

    /*=======================================================*/
    @When("^getting a gist$")
    public void get_gist(){
        new ApiTest().postGist();
        new ApiTest().getGist();
    }

    @Then("^should be valid$")
    public void validate_gist() {
        new ApiTest().validateGist();
        new ApiTest().deleteGist();
    }

    /*=======================================================*/
    @When("^deleting a gist$")
    public void delete_gist() {
        new ApiTest().postGist();
        new ApiTest().deleteGist();
    }

    @Then("^gist should not be found$")
    public void gist_not_found() {
        new ApiTest().getDeletedGist();
    }

}
