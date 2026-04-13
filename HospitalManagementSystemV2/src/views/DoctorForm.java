package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.DoctorDAO;
import models.Doctor;

public class DoctorForm extends JFrame {

    private JTextField txtName, txtSpec, txtPhone, txtEmail, txtAvail;
    private JTable table;
    private DefaultTableModel model;

    private int selectedId = -1;

    public DoctorForm() {

        setTitle("Doctor Management");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 182, 193));

        JLabel title = new JLabel("🧑‍⚕️ Doctor Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(250, 10, 300, 30);
        panel.add(title);

        // INPUTS
        txtName = new JTextField();
        txtSpec = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtAvail = new JTextField();

        txtName.setBounds(150, 60, 200, 25);
        txtSpec.setBounds(150, 100, 200, 25);
        txtPhone.setBounds(150, 140, 200, 25);
        txtEmail.setBounds(150, 180, 200, 25);
        txtAvail.setBounds(150, 220, 200, 25);

        panel.add(new JLabel("Name:")).setBounds(50, 60, 100, 25);
        panel.add(txtName);

        panel.add(new JLabel("Specialty:")).setBounds(50, 100, 120, 25);
        panel.add(txtSpec);

        panel.add(new JLabel("Phone:")).setBounds(50, 140, 100, 25);
        panel.add(txtPhone);

        panel.add(new JLabel("Email:")).setBounds(50, 180, 100, 25);
        panel.add(txtEmail);

        panel.add(new JLabel("Availability:")).setBounds(50, 220, 120, 25);
        panel.add(txtAvail);

        // BUTTONS
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        addBtn.setBounds(420, 60, 120, 30);
        updateBtn.setBounds(420, 100, 120, 30);
        deleteBtn.setBounds(420, 140, 120, 30);

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        // TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Specialty", "Phone", "Email", "Availability"
        });

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 270, 700, 250);
        panel.add(sp);

        add(panel);

        loadDoctors();

        setVisible(true);

        // ACTIONS
        addBtn.addActionListener(e -> addDoctor());
        updateBtn.addActionListener(e -> updateDoctor());
        deleteBtn.addActionListener(e -> deleteDoctor());

        table.getSelectionModel().addListSelectionListener(e -> selectRow());
    }

    // ➕ ADD
    private void addDoctor() {

        Doctor d = new Doctor(
                txtName.getText(),
                txtSpec.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAvail.getText()
        );

        new DoctorDAO().addDoctor(d);
        loadDoctors();
    }

    // ✏ UPDATE
    private void updateDoctor() {

        if (selectedId == -1) return;

        Doctor d = new Doctor(
                selectedId,
                txtName.getText(),
                txtSpec.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAvail.getText()
        );

        new DoctorDAO().updateDoctor(d);
        loadDoctors();
    }

    // ❌ DELETE
    private void deleteDoctor() {

        if (selectedId == -1) return;

        new DoctorDAO().deleteDoctor(selectedId);
        loadDoctors();
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

    // 📌 SELECT ROW
    private void selectRow() {

        int row = table.getSelectedRow();

        if (row != -1) {

            selectedId = (int) table.getValueAt(row, 0);

            txtName.setText(table.getValueAt(row, 1).toString());
            txtSpec.setText(table.getValueAt(row, 2).toString());
            txtPhone.setText(table.getValueAt(row, 3).toString());
            txtEmail.setText(table.getValueAt(row, 4).toString());
            txtAvail.setText(table.getValueAt(row, 5).toString());
        }
    }

    public static void main(String[] args) {
        new DoctorForm();
    }
}