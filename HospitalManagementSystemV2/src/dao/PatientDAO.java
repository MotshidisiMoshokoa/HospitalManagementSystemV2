package dao;

import db.DBConnection;
import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient p) {

        String sql = "INSERT INTO patients (full_name, gender, dob, phone, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getFullName());
            stmt.setString(2, p.getGender());
            stmt.setString(3, p.getDob());
            stmt.setString(4, p.getPhone());
            stmt.setString(5, p.getAddress());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("INSERT ERROR: " + e.getMessage());
            return false;
        }
    }

    public List<Patient> getAllPatients() {

        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM patients";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getString("gender"),
                        rs.getString("dob"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }

        } catch (Exception e) {
            System.out.println("LOAD ERROR: " + e.getMessage());
        }

        return list;
    }

    public boolean updatePatient(Patient p) {

        String sql = "UPDATE patients SET full_name=?, gender=?, dob=?, phone=?, address=? WHERE patient_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getFullName());
            stmt.setString(2, p.getGender());
            stmt.setString(3, p.getDob());
            stmt.setString(4, p.getPhone());
            stmt.setString(5, p.getAddress());
            stmt.setInt(6, p.getPatientId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("UPDATE ERROR: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePatient(int id) {

        String sql = "DELETE FROM patients WHERE patient_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("DELETE ERROR: " + e.getMessage());
            return false;
        }
    }
}