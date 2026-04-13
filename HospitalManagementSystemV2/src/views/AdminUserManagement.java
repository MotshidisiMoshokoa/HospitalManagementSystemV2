package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.UserDAO;
import models.User;

public class AdminUserManagement extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminUserManagement() {

        setTitle("Admin User Management");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 182, 193));

        // TITLE
        JLabel title = new JLabel("👑 Admin User Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(260, 20, 400, 30);
        panel.add(title);

        // TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Username", "Password", "Role", "Doctor ID"
        });

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 80, 750, 300);
        panel.add(sp);

        // BUTTONS
        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn = new JButton("Delete");
        JButton editBtn = new JButton("Edit User");

        refreshBtn.setBounds(200, 410, 120, 40);
        deleteBtn.setBounds(340, 410, 120, 40);
        editBtn.setBounds(480, 410, 120, 40);

        panel.add(refreshBtn);
        panel.add(deleteBtn);
        panel.add(editBtn);

        add(panel);

        // LOAD DATA
        loadUsers();

        // ACTIONS
        refreshBtn.addActionListener(e -> loadUsers());

        deleteBtn.addActionListener(e -> deleteUser());

        editBtn.addActionListener(e -> openEditForm());

        setVisible(true);
    }

    // 📄 LOAD USERS
    private void loadUsers() {

        UserDAO dao = new UserDAO();
        List<User> list = dao.getAllUsers();

        model.setRowCount(0);

        for (User u : list) {

            model.addRow(new Object[]{
                    u.getUserId(),
                    u.getUsername(),
                    u.getPassword(),
                    u.getRole(),
                    u.getDoctorId()
            });
        }
    }

    // ❌ DELETE USER
    private void deleteUser() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user!");
            return;
        }

        int id = (int) table.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this user?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean ok = new UserDAO().deleteUser(id);

            JOptionPane.showMessageDialog(this,
                    ok ? "Deleted ✔" : "Delete failed ❌");

            loadUsers();
        }
    }

    // ✏ OPEN EDIT FORM
    private void openEditForm() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user!");
            return;
        }

        User u = new User(
                (int) table.getValueAt(row, 0),
                table.getValueAt(row, 1).toString(),
                table.getValueAt(row, 2).toString(),
                table.getValueAt(row, 3).toString(),
                table.getValueAt(row, 4) == null
                        ? null
                        : Integer.parseInt(table.getValueAt(row, 4).toString())
        );

        new EditUserForm(u).setVisible(true);
    }

    public static void main(String[] args) {
        new AdminUserManagement();
    }
}