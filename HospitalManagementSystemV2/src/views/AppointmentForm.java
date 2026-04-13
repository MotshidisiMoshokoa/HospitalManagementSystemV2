package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import dao.PatientDAO;
import dao.DoctorDAO;
import dao.AppointmentDAO;
import dao.DoctorAvailabilityDAO;
import models.Patient;
import models.Doctor;
import models.Appointment;

public class AppointmentForm extends JFrame {

    private JComboBox<String> cmbPatients;
    private JComboBox<String> cmbDoctors;
    private JComboBox<String> cmbTime;
    private JTextField txtDate;

    public AppointmentForm() {

        setTitle("📅 Book Appointment");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color pink = new Color(255, 182, 193);
        Color darkPink = new Color(255, 105, 180);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(pink);

        // TITLE
        JLabel title = new JLabel("📅 Book Appointment");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(150, 20, 250, 30);
        panel.add(title);

        // PATIENT
        JLabel pLbl = new JLabel("Patient:");
        pLbl.setBounds(50, 80, 100, 25);
        panel.add(pLbl);

        cmbPatients = new JComboBox<>();
        cmbPatients.setBounds(150, 80, 250, 25);
        panel.add(cmbPatients);

        // DOCTOR
        JLabel dLbl = new JLabel("Doctor:");
        dLbl.setBounds(50, 120, 100, 25);
        panel.add(dLbl);

        cmbDoctors = new JComboBox<>();
        cmbDoctors.setBounds(150, 120, 250, 25);
        panel.add(cmbDoctors);

        // DATE
        JLabel dateLbl = new JLabel("Date:");
        dateLbl.setBounds(50, 160, 100, 25);
        panel.add(dateLbl);

        txtDate = new JTextField("YYYY-MM-DD");
        txtDate.setBounds(150, 160, 250, 25);
        panel.add(txtDate);

        // TIME SLOT
        JLabel timeLbl = new JLabel("Time:");
        timeLbl.setBounds(50, 200, 100, 25);
        panel.add(timeLbl);

        cmbTime = new JComboBox<>();
        cmbTime.setBounds(150, 200, 250, 25);

        cmbTime.addItem("09:00");
        cmbTime.addItem("10:00");
        cmbTime.addItem("11:00");
        cmbTime.addItem("12:00");
        cmbTime.addItem("14:00");
        cmbTime.addItem("15:00");

        panel.add(cmbTime);

        // BUTTON
        JButton btnBook = new JButton("Book Appointment 💗");
        btnBook.setBounds(150, 260, 180, 40);
        btnBook.setBackground(darkPink);
        btnBook.setForeground(Color.WHITE);

        panel.add(btnBook);

        add(panel);

        loadPatients();
        loadDoctors();

        btnBook.addActionListener(e -> bookAppointment());

        setVisible(true);
    }

    // LOAD PATIENTS
    private void loadPatients() {

        PatientDAO dao = new PatientDAO();
        List<Patient> list = dao.getAllPatients();

        for (Patient p : list) {
            cmbPatients.addItem(p.getPatientId() + " - " + p.getFullName());
        }
    }

    // LOAD DOCTORS
    private void loadDoctors() {

        DoctorDAO dao = new DoctorDAO();
        List<Doctor> list = dao.getAllDoctors();

        for (Doctor d : list) {
            cmbDoctors.addItem(d.getDoctorId() + " - " + d.getFullName());
        }
    }

    // BOOK APPOINTMENT
    private void bookAppointment() {

        try {

            if (cmbPatients.getSelectedItem() == null ||
                cmbDoctors.getSelectedItem() == null ||
                txtDate.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            String patientStr = cmbPatients.getSelectedItem().toString();
            String doctorStr = cmbDoctors.getSelectedItem().toString();

            int patientId = Integer.parseInt(patientStr.split(" - ")[0]);
            int doctorId = Integer.parseInt(doctorStr.split(" - ")[0]);

            String date = txtDate.getText();
            String time = cmbTime.getSelectedItem().toString();

            Appointment a = new Appointment(0, patientId, doctorId, date, time, "PENDING");

            boolean success = new AppointmentDAO().addAppointment(a);

            if (success) {
                JOptionPane.showMessageDialog(this, "Appointment booked ✔");
            } else {
                JOptionPane.showMessageDialog(this, "Time slot already taken ❌");
            }

        } catch (Exception ex) {
            System.out.println("BOOK ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    private void loadAvailableTimeSlots() {

        try {

            cmbTime.removeAllItems();

            if (cmbDoctors.getSelectedItem() == null) return;

            String doctorStr = cmbDoctors.getSelectedItem().toString();
            int doctorId = Integer.parseInt(doctorStr.split(" - ")[0]);

            String date = txtDate.getText();

            if (date.isEmpty()) return;

            DoctorAvailabilityDAO availDAO = new DoctorAvailabilityDAO();
            AppointmentDAO appDAO = new AppointmentDAO();

            List<String> available = availDAO.getAvailableSlots(doctorId, date);
            List<String> booked = appDAO.getBookedSlots(doctorId, date);

            for (String slot : available) {
                if (!booked.contains(slot)) {
                    cmbTime.addItem(slot);
                }
            }

            if (cmbTime.getItemCount() == 0) {
                cmbTime.addItem("No available slots");
            }

        } catch (Exception e) {
            System.out.println("Slot load error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AppointmentForm();
    }
}