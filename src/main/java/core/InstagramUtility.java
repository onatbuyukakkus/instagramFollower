package core;

import org.brunocvcunha.instagram4j.*;

public class InstagramUtility {

    public static String login(String username, String password) {
        Instagram4j instagram = Instagram4j.builder().username(username).password(password).build();
        instagram.setup();
        try {
            return instagram.login().toString();
        }
        catch (Exception e) {
           return e.toString();
        }
    }
}
