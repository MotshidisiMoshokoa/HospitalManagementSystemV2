package dao;

import db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class SessionDAO {

    // LOGIN TRACK
    public void logLogin(String username, String role) {

        String sql = "INSERT INTO user_sessions (username, role, login_time) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, role);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Login log error: " + e.getMessage());
        }
    }

    // LOGOUT TRACK
    public void logLogout(String username) {

        String sql = "UPDATE user_sessions SET logout_time=? WHERE username=? AND logout_time IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, username);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Logout log error: " + e.getMessage());
        }
    }
}