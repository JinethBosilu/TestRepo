package Session;

public class SessionManager {
    private static String currentUsername;

    public static void setUsername(String username) {
        currentUsername = username;
    }

    public static String getUsername() {
        return currentUsername;
    }
}
