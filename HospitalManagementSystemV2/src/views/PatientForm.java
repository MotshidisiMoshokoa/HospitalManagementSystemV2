package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.PatientDAO;
import models.Patient;

public class PatientForm extends JFrame {

    private JTextField txtName, txtDob, txtPhone, txtAddress;
    private JComboBox<String> cbGender;

    private JTable table;
    private DefaultTableModel model;

    private int selectedPatientId = -1;

    public PatientForm() {

        setTitle("Hospital System - Patients");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color pink = new Color(255, 182, 193);
        Color darkPink = new Color(255, 105, 180);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(pink);

        JLabel title = new JLabel("💗 Patient Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(240, 10, 300, 30);
        panel.add(title);

        // Fields
        txtName = new JTextField();
        txtDob = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(50, 60, 100, 25);
        txtName.setBounds(150, 60, 200, 25);

        JLabel l2 = new JLabel("Gender:");
        l2.setBounds(50, 100, 100, 25);
        cbGender.setBounds(150, 100, 200, 25);

        JLabel l3 = new JLabel("DOB:");
        l3.setBounds(50, 140, 100, 25);
        txtDob.setBounds(150, 140, 200, 25);

        JLabel l4 = new JLabel("Phone:");
        l4.setBounds(50, 180, 100, 25);
        txtPhone.setBounds(150, 180, 200, 25);

        JLabel l5 = new JLabel("Address:");
        l5.setBounds(50, 220, 100, 25);
        txtAddress.setBounds(150, 220, 200, 25);

        panel.add(l1); panel.add(txtName);
        panel.add(l2); panel.add(cbGender);
        panel.add(l3); panel.add(txtDob);
        panel.add(l4); panel.add(txtPhone);
        panel.add(l5); panel.add(txtAddress);

        // Buttons
        JButton addBtn = new JButton("Add 💗");
        JButton updateBtn = new JButton("Update 💖");
        JButton deleteBtn = new JButton("Delete 🗑");

        addBtn.setBounds(400, 60, 120, 30);
        updateBtn.setBounds(400, 100, 120, 30);
        deleteBtn.setBounds(400, 140, 120, 30);

        addBtn.setBackground(darkPink);
        updateBtn.setBackground(new Color(255, 160, 180));
        deleteBtn.setBackground(Color.RED);

        addBtn.setForeground(Color.WHITE);
        updateBtn.setForeground(Color.WHITE);
        deleteBtn.setForeground(Color.WHITE);

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        // Table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Gender", "DOB", "Phone", "Address"});

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 300, 650, 200);
        panel.add(sp);

        // Actions
        addBtn.addActionListener(e -> addPatient());
        updateBtn.addActionListener(e -> updatePatient());
        deleteBtn.addActionListener(e -> deletePatient());

        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {
                selectedPatientId = Integer.parseInt(model.getValueAt(row, 0).toString());

                txtName.setText(model.getValueAt(row, 1).toString());
                cbGender.setSelectedItem(model.getValueAt(row, 2).toString());
                txtDob.setText(model.getValueAt(row, 3).toString());
                txtPhone.setText(model.getValueAt(row, 4).toString());
                txtAddress.setText(model.getValueAt(row, 5).toString());
            }
        });

        add(panel);

        loadPatients();
    }

    private void addPatient() {

        Patient p = new Patient(0,
                txtName.getText(),
                cbGender.getSelectedItem().toString(),
                txtDob.getText(),
                txtPhone.getText(),
                txtAddress.getText());

        PatientDAO dao = new PatientDAO();

        if (dao.addPatient(p)) {
            JOptionPane.showMessageDialog(this, "Added 💗");
            loadPatients();
        }
    }

    private void updatePatient() {

        if (selectedPatientId == -1) return;

        Patient p = new Patient(selectedPatientId,
                txtName.getText(),
                cbGender.getSelectedItem().toString(),
                txtDob.getText(),
                txtPhone.getText(),
                txtAddress.getText());

        new PatientDAO().updatePatient(p);
        loadPatients();
    }

    private void deletePatient() {

        if (selectedPatientId == -1) return;

        new PatientDAO().deletePatient(selectedPatientId);
        loadPatients();
    }

    private void loadPatients() {

        PatientDAO dao = new PatientDAO();
        List<Patient> list = dao.getAllPatients();

        model.setRowCount(0);

        for (Patient p : list) {
            model.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFullName(),
                    p.getGender(),
                    p.getDob(),
                    p.getPhone(),
                    p.getAddress()
            });
        }
    }

    public static void main(String[] args) {
        new PatientForm().setVisible(true);
    }
}