package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.AppointmentDAO;
import models.Appointment;
import utils.Notification;

public class DoctorDashboard extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private int doctorId;
    private String username;

    private Timer autoRefreshTimer;
    private boolean lastPendingState = false;

    public DoctorDashboard(String username, int doctorId) {

        this.username = username;
        this.doctorId = doctorId;

        setTitle("Doctor Dashboard (LIVE)");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color pink = new Color(255, 182, 193);
        Color darkPink = new Color(255, 105, 180);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(pink);

        // TITLE
        JLabel title = new JLabel("🧑‍⚕️ Doctor Dashboard (LIVE)");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(260, 20, 400, 30);
        panel.add(title);

        // INFO
        JLabel lbl = new JLabel("Doctor: " + username + " | ID: " + doctorId);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setBounds(50, 60, 400, 25);
        panel.add(lbl);

        // TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Appointment ID",
                "Patient ID",
                "Doctor ID",
                "Date",
                "Time",
                "Status"
        });

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 100, 780, 300);
        panel.add(sp);

        // BUTTONS
        JButton btnComplete = new JButton("Completed");
        JButton btnCancel = new JButton("Cancelled");
        JButton btnRefresh = new JButton("Refresh");

        btnComplete.setBounds(150, 420, 150, 40);
        btnCancel.setBounds(320, 420, 150, 40);
        btnRefresh.setBounds(490, 420, 150, 40);

        btnComplete.setBackground(darkPink);
        btnCancel.setBackground(darkPink);
        btnRefresh.setBackground(Color.WHITE);

        btnComplete.setForeground(Color.WHITE);
        btnCancel.setForeground(Color.WHITE);

        panel.add(btnComplete);
        panel.add(btnCancel);
        panel.add(btnRefresh);

        add(panel);

        // LOAD DATA
        loadAppointments();

        // BUTTON ACTIONS
        btnComplete.addActionListener(e -> updateStatus("COMPLETED"));
        btnCancel.addActionListener(e -> updateStatus("CANCELLED"));
        btnRefresh.addActionListener(e -> {
            loadAppointments();
            checkLiveNotifications();
        });

        setVisible(true);

        startAutoRefresh();
        checkLiveNotifications();
    }

    // LOAD DATA
    private void loadAppointments() {

        AppointmentDAO dao = new AppointmentDAO();
        List<Appointment> list = dao.getAllAppointments();

        model.setRowCount(0);

        for (Appointment a : list) {

            if (a.getDoctorId() == doctorId) {

                model.addRow(new Object[]{
                        a.getAppointmentId(),
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getAppointmentDate(),
                        a.getAppointmentTime(), // FIXED
                        a.getStatus()
                });
            }
        }
    }

    // UPDATE STATUS
    private void updateStatus(String status) {

        int row = table.getSelectedRow();

        if (row == -1) {
            Notification.warning("Please select an appointment first!");
            return;
        }

        int appointmentId = (int) table.getValueAt(row, 0);

        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.updateStatus(appointmentId, status);

        if (success) {
            Notification.success("Appointment marked " + status);
            loadAppointments();
            checkLiveNotifications();
        } else {
            Notification.warning("Update failed!");
        }
    }

    // LIVE NOTIFICATIONS
    private void checkLiveNotifications() {

        boolean hasPending = false;

        for (int i = 0; i < table.getRowCount(); i++) {

            String status = table.getValueAt(i, 5).toString(); // FIXED INDEX

            if (status.equalsIgnoreCase("PENDING")) {
                hasPending = true;
                break;
            }
        }

        if (hasPending && !lastPendingState) {
            Notification.warning("New pending appointment received!");
        }

        lastPendingState = hasPending;
    }

    // AUTO REFRESH
    private void startAutoRefresh() {

        autoRefreshTimer = new Timer(5000, e -> {
            loadAppointments();
            checkLiveNotifications();
        });

        autoRefreshTimer.start();
    }

    @Override
    public void dispose() {

        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }

        super.dispose();
    }

    public static void main(String[] args) {
        new DoctorDashboard("doctor1", 1);
    }
}