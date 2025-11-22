
import javax.swing.*;

public class LoginUI extends JFrame {

    private JTextField UserField;
    private JPasswordField passField;

    public LoginUI() {

        setTitle("Login");
        setSize(350, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("UserName:");
        l1.setBounds(20, 30, 80, 25);
        add(l1);

        UserField = new JTextField();
        UserField.setBounds(100, 30, 200, 25);
        add(UserField);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(20, 70, 80, 25);
        add(l2);

        passField = new JPasswordField();
        passField.setBounds(100, 70, 200, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 110, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(e -> authenticate());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void authenticate() {
        String user = UserField.getText();
        String pass = new String(passField.getPassword());

        if (user.equals("Admin") && pass.equals("t31510")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
            new MainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }

}