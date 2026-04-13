package views;

import javax.swing.*;
import java.awt.*;

import dao.PatientDAO;
import dao.DoctorDAO;
import dao.AppointmentDAO;

public class Dashboard extends JFrame {

    private JLabel lblPatients;
    private JLabel lblDoctors;
    private JLabel lblAppointments;

    public Dashboard() {

        setTitle("Hospital Management Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color pink = new Color(255, 182, 193);
        Color darkPink = new Color(255, 105, 180);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(pink);

        // TITLE
        JLabel title = new JLabel("💗 Hospital Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(270, 20, 400, 40);
        panel.add(title);

        // CARD 1 - PATIENTS
        JPanel card1 = createCard("Patients", 80, darkPink);
        lblPatients = (JLabel) card1.getComponent(1);
        panel.add(card1);

        // CARD 2 - DOCTORS
        JPanel card2 = createCard("Doctors", 330, darkPink);
        lblDoctors = (JLabel) card2.getComponent(1);
        panel.add(card2);

        // CARD 3 - APPOINTMENTS
        JPanel card3 = createCard("Appointments", 580, darkPink);
        lblAppointments = (JLabel) card3.getComponent(1);
        panel.add(card3);

        // BUTTONS
        JButton openPatients = new JButton("Patients");
        openPatients.setBounds(150, 320, 150, 40);
        openPatients.setBackground(darkPink);
        openPatients.setForeground(Color.WHITE);

        JButton openDoctors = new JButton("Doctors");
        openDoctors.setBounds(370, 320, 150, 40);
        openDoctors.setBackground(darkPink);
        openDoctors.setForeground(Color.WHITE);

        JButton openAppointments = new JButton("Appointments");
        openAppointments.setBounds(590, 320, 150, 40);
        openAppointments.setBackground(darkPink);
        openAppointments.setForeground(Color.WHITE);

        panel.add(openPatients);
        panel.add(openDoctors);
        panel.add(openAppointments);

        openPatients.addActionListener(e -> new PatientForm().setVisible(true));
        openDoctors.addActionListener(e -> new DoctorForm().setVisible(true));
        openAppointments.addActionListener(e -> new AppointmentForm().setVisible(true));

        add(panel);

        loadStats();

        setVisible(true);
    }

    // CARD DESIGN
    private JPanel createCard(String title, int x, Color color) {

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBounds(x, 100, 220, 140);

        JLabel t = new JLabel(title);
        t.setBounds(60, 10, 150, 25);
        t.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel value = new JLabel("0");
        value.setBounds(90, 60, 100, 40);
        value.setFont(new Font("Arial", Font.BOLD, 30));
        value.setForeground(color);

        card.add(t);
        card.add(value);

        return card;
    }

    // LOAD LIVE STATS
    private void loadStats() {

        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        lblPatients.setText(String.valueOf(patientDAO.getAllPatients().size()));
        lblDoctors.setText(String.valueOf(doctorDAO.getAllDoctors().size()));
        lblAppointments.setText(String.valueOf(appointmentDAO.getAllAppointments().size()));
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}