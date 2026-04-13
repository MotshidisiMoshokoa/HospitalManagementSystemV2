package dao;

import db.DBConnection;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ➕ REGISTER USER
    public boolean registerUser(User u) {

        String sql = "INSERT INTO users (username, password, role, doctor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getRole());

            if (u.getDoctorId() == null) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, u.getDoctorId());
            }

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Register error: " + e.getMessage());
            return false;
        }
    }

    // 🔐 LOGIN
    public String getRole(String username, String password) {

        String sql = "SELECT role FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }

    // 👨‍⚕️ GET DOCTOR ID
    public int getDoctorId(String username, String password) {

        String sql = "SELECT doctor_id FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("doctor_id");
            }

        } catch (Exception e) {
            System.out.println("Doctor ID error: " + e.getMessage());
        }

        return -1;
    }

    // 📄 GET ALL USERS
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        (Integer) rs.getObject("doctor_id")
                ));
            }

        } catch (Exception e) {
            System.out.println("Load users error: " + e.getMessage());
        }

        return list;
    }

    // ✏ UPDATE USER
    public boolean updateUser(User u) {

        String sql = "UPDATE users SET username=?, password=?, role=?, doctor_id=? WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getRole());

            if (u.getDoctorId() == null) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, u.getDoctorId());
            }

            stmt.setInt(5, u.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
            return false;
        }
    }

    // ❌ DELETE USER
    public boolean deleteUser(int id) {

        String sql = "DELETE FROM users WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
            return false;
        }
    }
}