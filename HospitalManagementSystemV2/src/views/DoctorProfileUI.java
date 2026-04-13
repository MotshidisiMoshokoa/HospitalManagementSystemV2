package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.DoctorDAO;
import models.Doctor;

public class DoctorProfileUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public DoctorProfileUI() {

        setTitle("Doctor Profile Management");
        setSize(850, 520);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 182, 193));

        JLabel title = new JLabel("👨‍⚕️ Doctor Profile System");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(250, 20, 400, 30);
        panel.add(title);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Specialty", "Phone", "Email", "Availability"
        });

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 80, 750, 300);
        panel.add(sp);

        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete");

        refresh.setBounds(250, 410, 120, 40);
        delete.setBounds(420, 410, 120, 40);

        panel.add(refresh);
        panel.add(delete);

        add(panel);

        loadDoctors();

        refresh.addActionListener(e -> loadDoctors());
        delete.addActionListener(e -> deleteDoctor());

        setVisible(true);
    }

    // 📄 LOAD
    private void loadDoctors() {

        DoctorDAO dao = new DoctorDAO();
        List<Doctor> list = dao.getAllDoctors();

        model.setRowCount(0);

        for (Doctor d : list) {

            model.addRow(new Object[]{
                    d.getDoctorId(),
                    d.getFullName(),
                    d.getSpecialty(),
                    d.getPhone(),
                    d.getEmail(),
                    d.getAvailability()
            });
        }
    }

    // ❌ DELETE
    private void deleteDoctor() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select doctor first!");
            return;
        }

        int id = (int) table.getValueAt(row, 0);

        boolean ok = new DoctorDAO().deleteDoctor(id);

        JOptionPane.showMessageDialog(this,
                ok ? "Deleted ✔" : "Failed ❌");

        loadDoctors();
    }

    public static void main(String[] args) {
        new DoctorProfileUI();
    }
}
