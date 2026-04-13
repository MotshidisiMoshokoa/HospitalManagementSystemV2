package utils;

import javax.swing.*;

public class Notification {

    // SIMPLE POP-UP NOTIFICATION
    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // SUCCESS NOTIFICATION
    public static void success(String message) {
        JOptionPane.showMessageDialog(null, "✔ " + message, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ERROR NOTIFICATION
    public static void error(String message) {
        JOptionPane.showMessageDialog(null, "❌ " + message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // WARNING NOTIFICATION
    public static void warning(String message) {
        JOptionPane.showMessageDialog(null, "⚠ " + message, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }
}
