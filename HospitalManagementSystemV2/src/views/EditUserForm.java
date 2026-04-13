package views;

import javax.swing.*;
import java.awt.*;

import dao.UserDAO;
import models.User;

public class EditUserForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JTextField txtDoctorId;

    private int userId;

    public EditUserForm(User u) {

        this.userId = u.getUserId();

        setTitle("Edit User");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 182, 193));

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(50, 40, 100, 25);
        panel.add(l1);

        txtUsername = new JTextField(u.getUsername());
        txtUsername.setBounds(150, 40, 180, 25);
        panel.add(txtUsername);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(50, 80, 100, 25);
        panel.add(l2);

        txtPassword = new JPasswordField(u.getPassword());
        txtPassword.setBounds(150, 80, 180, 25);
        panel.add(txtPassword);

        JLabel l3 = new JLabel("Role:");
        l3.setBounds(50, 120, 100, 25);
        panel.add(l3);

        cmbRole = new JComboBox<>(new String[]{"ADMIN", "DOCTOR"});
        cmbRole.setBounds(150, 120, 180, 25);
        cmbRole.setSelectedItem(u.getRole());
        panel.add(cmbRole);

        JLabel l4 = new JLabel("Doctor ID:");
        l4.setBounds(50, 160, 100, 25);
        panel.add(l4);

        txtDoctorId = new JTextField(
                u.getDoctorId() == null ? "" : u.getDoctorId().toString()
        );
        txtDoctorId.setBounds(150, 160, 180, 25);
        panel.add(txtDoctorId);

        JButton save = new JButton("Save Changes");
        save.setBounds(150, 210, 140, 30);
        panel.add(save);

        add(panel);

        save.addActionListener(e -> updateUser());

        setVisible(true);
    }

    private void updateUser() {

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String role = cmbRole.getSelectedItem().toString();

        Integer doctorId = null;

        if (!txtDoctorId.getText().isEmpty()) {
            doctorId = Integer.parseInt(txtDoctorId.getText());
        }

        User u = new User(userId, username, password, role, doctorId);

        boolean ok = new UserDAO().updateUser(u);

        JOptionPane.showMessageDialog(this,
                ok ? "Updated ✔" : "Update Failed ❌");

        this.dispose();
    }
}
