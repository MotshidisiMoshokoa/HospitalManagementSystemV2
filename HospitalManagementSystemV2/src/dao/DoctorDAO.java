package dao;

import db.DBConnection;
import models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // ➕ ADD DOCTOR
    public boolean addDoctor(Doctor d) {

        String sql = "INSERT INTO doctors (full_name, specialty, phone, email, availability) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getFullName());
            stmt.setString(2, d.getSpecialty());
            stmt.setString(3, d.getPhone());
            stmt.setString(4, d.getEmail());
            stmt.setString(5, d.getAvailability());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Add doctor error: " + e.getMessage());
            return false;
        }
    }

    // 📄 GET ALL DOCTORS
    public List<Doctor> getAllDoctors() {

        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT * FROM doctors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialty"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("availability")
                ));
            }

        } catch (Exception e) {
            System.out.println("Load doctors error: " + e.getMessage());
        }

        return list;
    }

    // ✏ UPDATE DOCTOR
    public boolean updateDoctor(Doctor d) {

        String sql = "UPDATE doctors SET full_name=?, specialty=?, phone=?, email=?, availability=? WHERE doctor_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getFullName());
            stmt.setString(2, d.getSpecialty());
            stmt.setString(3, d.getPhone());
            stmt.setString(4, d.getEmail());
            stmt.setString(5, d.getAvailability());
            stmt.setInt(6, d.getDoctorId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Update doctor error: " + e.getMessage());
            return false;
        }
    }

    // ❌ DELETE DOCTOR
    public boolean deleteDoctor(int id) {

        String sql = "DELETE FROM doctors WHERE doctor_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Delete doctor error: " + e.getMessage());
            return false;
        }
    }
}