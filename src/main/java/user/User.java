package user;

public class User {

    private String username;
    private String password;

    /**
     * Constructor of class {@link User}.
     *
     * @param username username of user. Allowed object is {@link String}
     * @param password password of user. Allowed object is {@link String}
     */
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}
