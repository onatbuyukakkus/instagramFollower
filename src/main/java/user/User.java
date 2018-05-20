package user;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<String> followers;
    private List<String> followings;


    public User() {}

    /**
     * Constructor of class {@link User}.
     *
     * @param username username of user. Allowed object is {@link String}
     * @param password password of user. Allowed object is {@link String}
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.followers = new ArrayList<String>();
        this.followings = new ArrayList<String>();
    }

    /**
     * Get the username of user.
     * @return {@link String}
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes the username to the given param
     * @param username allowed value {@link String}
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of user.
     * @return {@link String}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the password to the given param
     * @param password allowed value {@link String}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the followers of user.
     * @return {@link List<String>}
     */
    public List<String> getFollowers() {
        return followers;
    }

    /**
     * Changes the followers to the given param
     * @param followers allowed value {@link List<String>}
     */
    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    /**
     * Get the followings of user.
     * @return {@link List<String>}
     */
    public List<String> getFollowings() {
        return followings;
    }

    /**
     * Changes the followers to the given param
     * @param followings allowed value {@link List<String>}
     */
    public void setFollowings(List<String> followings) {
        this.followings = followings;
    }

}
