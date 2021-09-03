package model;

/** The User class. This stores all User information and utility. */
public class User {

    private static int userID;
    private static String username;
    private static String password;


    /** Empty User constructor. */
    public User(){
        userID = 0;
        username = null;
        password = null;

    }
    /** User constructor. This creates a user object.
     * @param userID The user ID.
     * @param username The username.
     * @param password The password.
     */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /** The User user ID getter. This gets the user ID of the user.
     * @return The user ID.
     */
    public static int getUserID() {
        return userID;
    }

    /** The User user ID setter. This sets the user ID of the user.
     * @param userID The user ID to set. */
    public static void setUserID(int userID) {
        User.userID = userID;
    }

    /** The User username getter. This gets the username of the user.
     * @return The username.
     */
    public static String getUsername() {
        return username;
    }

    /** The User username setter. This sets the username of the user.
     * @param username The username to be set.
     */
    public static void setUsername(String username) {
        User.username = username;
    }

    /** The User password getter. This gets the password for the user.
     * @return The password.
     */
    public static String getPassword() {
        return password;
    }

    /** The User password setter. This sets the password for the user.
     * @param password The password to be set.
     */
    public static void setPassword(String password) {
        User.password = password;
    }
}


