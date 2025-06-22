package model;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String gmail;
    private String password;

    public User(String firstName, String lastName, String username, String gmail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gmail = gmail;
        this.password = password;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getGmail() { return gmail; }
    public String getPassword() { return password; }
}
