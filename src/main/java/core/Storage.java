package core;

import java.util.HashMap;

import user.User;

/**
 * This class is used to store users, merchants and barcodes.
 *
 */
public final class Storage {

    private static final Storage INSTANCE = new Storage();
    private static HashMap<String, User> userStorage;

    /**
     * Constructor
     */
    private Storage() {
        userStorage = new HashMap<String, User>();         /* Storage containing all users */
    }

    /**
     * Adding user to database/hash map storage of users.
     * If new user, the user is added to storage, if user already exists, nothing happens.
     * @param username Possible object is {@link String}
     * @param password Possible object is {@link String}
     * @return Message describing the outcome. Possible object is {@link String}
     */
    public static String addUser(String username, String password) {
        if (!userStorage.containsKey(username)) {
            User newUser = new User(username, password);
            userStorage.put(username, newUser);
            return("new user created");
        } else {
            return("user already exists");
        }
    }

    /**
     * Checking user in database/hash map storage of users.
     * If user exist, return true, else return false.
     * @param username Possible object is {@link String}
     * @param password Possible object is {@link String}
     * @return Outcome. Possible object is {@link Boolean}
     */
    public static boolean checkUser(String username, String password) {
        return (userStorage.containsKey(username) && userStorage.get(username).getPassword().equals(password));
    }

    /**
     * Returning instance of Storage.
     * @return Possible object is {@link Storage}
     */
    public static Storage getInstance() {
        return INSTANCE;
    }

    /**
     * Getting a user from the user storage by associated username.
     * @param username The username associated with the specific {@link User} object
     * @return Possible object is {@link Storage}
     */
    public static User getUserFromUsername(String username) {
        return userStorage.get(username);
    }

    /**
     * Get full storage of current users.
     * @return Possible object is {@link Storage}
     */
    public static HashMap<String, User> getUserStorage() {
        return userStorage;
    }

    /**
     * Clear all storages.
     * Method used for testing purposes in order to reset all storages to be empty.
     */
    public static void clear() {
        userStorage.clear();
    }
}
