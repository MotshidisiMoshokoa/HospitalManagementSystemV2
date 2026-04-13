package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorAvailabilityDAO {

    // ➕ ADD AVAILABILITY
    public boolean addAvailability(int doctorId, String date, String time) {

        String sql = "INSERT INTO doctor_availability (doctor_id, available_date, available_time) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, date);
            stmt.setString(3, time);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Availability error: " + e.getMessage());
            return false;
        }
    }

    // 📅 GET AVAILABLE SLOTS
    public List<String> getAvailableSlots(int doctorId, String date) {

        List<String> slots = new ArrayList<>();

        String sql = "SELECT available_time FROM doctor_availability WHERE doctor_id=? AND available_date=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slots.add(rs.getString("available_time"));
            }

        } catch (Exception e) {
            System.out.println("Availability load error: " + e.getMessage());
        }

        return slots;
    }

    // ✅ CHECK SINGLE SLOT
    public boolean isAvailable(int doctorId, String date, String time) {

        String sql = "SELECT * FROM doctor_availability WHERE doctor_id=? AND available_date=? AND available_time=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, date);
            stmt.setString(3, time);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Check error: " + e.getMessage());
            return false;
        }
    }
}