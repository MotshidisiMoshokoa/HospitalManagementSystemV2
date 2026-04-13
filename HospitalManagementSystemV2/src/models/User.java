package models;

public class User {

    private int userId;
    private String username;
    private String password;
    private String role;
    private Integer doctorId;

    public User(int userId, String username, String password, String role, Integer doctorId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.doctorId = doctorId;
    }

    public User(String username, String password, String role, Integer doctorId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.doctorId = doctorId;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public Integer getDoctorId() { return doctorId; }
}