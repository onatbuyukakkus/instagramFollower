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
     * @param receivedInput Array of {@link User} objects, each user has username and password
     * @return Message describing the outcome. Possible object is {@link String}
     */
    public static String addUser(String[] receivedInput) {
        if (!userStorage.containsKey(receivedInput[1])) {
            User newUser = new User(receivedInput[1], receivedInput[2]);
            userStorage.put(receivedInput[1], newUser);
            return("New user created.");
        } else {
            return("User already exists.");
        }
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
    public static User getUserFromCpr(String username) {
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
