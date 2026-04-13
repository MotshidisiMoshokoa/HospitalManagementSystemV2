package views;

import javax.swing.*;
import java.awt.*;

import dao.UserDAO;
import dao.SessionDAO;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {

        setTitle("Hospital Login");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 💗 COLORS
        Color pink = new Color(255, 182, 193);
        Color darkPink = new Color(255, 105, 180);

        // PANEL
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(pink);

        // TITLE
        JLabel title = new JLabel("💗 Hospital Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(110, 20, 250, 30);
        panel.add(title);

        // USERNAME
        JLabel u1 = new JLabel("Username:");
        u1.setBounds(50, 80, 100, 25);
        panel.add(u1);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 180, 25);
        panel.add(txtUsername);

        // PASSWORD
        JLabel u2 = new JLabel("Password:");
        u2.setBounds(50, 120, 100, 25);
        panel.add(u2);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 180, 25);
        panel.add(txtPassword);

        // LOGIN BUTTON
        JButton loginBtn = new JButton("Login 💗");
        loginBtn.setBounds(150, 180, 120, 40);
        loginBtn.setBackground(darkPink);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        panel.add(loginBtn);

        // ADD PANEL TO FRAME (IMPORTANT)
        add(panel);

        // ACTION
        loginBtn.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        UserDAO dao = new UserDAO();
        SessionDAO session = new SessionDAO();

        String role = dao.getRole(username, password);

        if (role == null) {
            JOptionPane.showMessageDialog(this, "Invalid login ❌");
            return;
        }

        // LOG SESSION
        session.logLogin(username, role);

        JOptionPane.showMessageDialog(this, "Login Success: " + role);

        this.dispose();

        if (role.equals("ADMIN")) {
            new Dashboard();
        } 
        else if (role.equals("DOCTOR")) {

            int doctorId = dao.getDoctorId(username, password);

            if (doctorId != -1) {
                new DoctorDashboard(username, doctorId);
            }
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}