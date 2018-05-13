package core;

import org.brunocvcunha.instagram4j.*;

public class InstagramUtility {

    Instagram4j instagram;

    public String login(String username, String password) {
        this.instagram = Instagram4j.builder().username(username).password(password).build();
        instagram.setup();
        try {
            return instagram.login().toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}
