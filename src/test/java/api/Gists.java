package api;

import cucumber.api.java.ro.Si;

public class Gists {
    private static Gists ourInstance = new Gists();
    private String id;

    public static Gists getInstance() {
        return ourInstance;
    }

    private Gists() {
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return this.id;
    }
}
