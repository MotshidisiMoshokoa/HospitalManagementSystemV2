package dao;

import db.DBConnection;
import models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // ➕ ADD APPOINTMENT
    public boolean addAppointment(Appointment a) {

        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) return false;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, a.getPatientId());
            stmt.setInt(2, a.getDoctorId());
            stmt.setString(3, a.getAppointmentDate());
            stmt.setString(4, a.getAppointmentTime());
            stmt.setString(5, a.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Add Appointment Error: " + e.getMessage());
            return false;
        }
    }

    // 📄 GET ALL APPOINTMENTS
    public List<Appointment> getAllAppointments() {

        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Load Error: " + e.getMessage());
        }

        return list;
    }

    // 🔄 UPDATE STATUS (THIS FIXES YOUR ERROR)
    public boolean updateStatus(int id, String status) {

        String sql = "UPDATE appointments SET status=? WHERE appointment_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Update Status Error: " + e.getMessage());
            return false;
        }
    }

    // 📅 GET BOOKED SLOTS
    public List<String> getBookedSlots(int doctorId, String date) {

        List<String> slots = new ArrayList<>();

        String sql = "SELECT appointment_time FROM appointments WHERE doctor_id=? AND appointment_date=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(rs.getString("appointment_time"));
            }

        } catch (Exception e) {
            System.out.println("Booked slots error: " + e.getMessage());
        }

        return slots;
    }
}