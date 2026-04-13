package views;

import javax.swing.*;
import java.awt.*;

import dao.UserDAO;
import models.User;

public class RegisterForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JTextField txtDoctorId;

    public RegisterForm() {

        setTitle("Register User");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 182, 193));

        JLabel u1 = new JLabel("Username:");
        u1.setBounds(50, 40, 100, 25);
        panel.add(u1);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 40, 180, 25);
        panel.add(txtUsername);

        JLabel u2 = new JLabel("Password:");
        u2.setBounds(50, 80, 100, 25);
        panel.add(u2);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 180, 25);
        panel.add(txtPassword);

        JLabel u3 = new JLabel("Role:");
        u3.setBounds(50, 120, 100, 25);
        panel.add(u3);

        cmbRole = new JComboBox<>(new String[]{"ADMIN", "DOCTOR"});
        cmbRole.setBounds(150, 120, 180, 25);
        panel.add(cmbRole);

        JLabel u4 = new JLabel("Doctor ID (if doctor):");
        u4.setBounds(20, 160, 150, 25);
        panel.add(u4);

        txtDoctorId = new JTextField();
        txtDoctorId.setBounds(150, 160, 180, 25);
        panel.add(txtDoctorId);

        JButton btn = new JButton("Register");
        btn.setBounds(150, 210, 120, 30);
        panel.add(btn);

        add(panel);

        btn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String role = cmbRole.getSelectedItem().toString();

        Integer doctorId = null;

        if (role.equals("DOCTOR") && !txtDoctorId.getText().isEmpty()) {
            doctorId = Integer.parseInt(txtDoctorId.getText());
        }

        User u = new User(username, password, role, doctorId);

        boolean success = new UserDAO().registerUser(u);

        JOptionPane.showMessageDialog(this,
                success ? "User Registered ✔" : "Failed ❌");
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}