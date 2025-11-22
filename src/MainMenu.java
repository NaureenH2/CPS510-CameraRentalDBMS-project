import javax.swing.*;
import ui.CustomerUI;
import ui.EquipmentUI;
import ui.RentalUI;
import ui.PaymentUI;
import ui.DamageUI;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Rental Management System");
        setSize(420, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton customerBtn = new JButton("Manage Customers"); customerBtn.setBounds(100, 30, 200, 40);
        JButton equipmentBtn = new JButton("Manage Equipment"); equipmentBtn.setBounds(100, 80, 200, 40);
        JButton rentalBtn = new JButton("Manage Rentals"); rentalBtn.setBounds(100, 130, 200, 40);
        JButton paymentBtn = new JButton("Manage Payments"); paymentBtn.setBounds(100, 180, 200, 40);
        JButton damageBtn = new JButton("Damage Reports"); damageBtn.setBounds(100, 230, 200, 40);

        add(customerBtn); add(equipmentBtn); add(rentalBtn); add(paymentBtn); add(damageBtn);

        customerBtn.addActionListener(e -> new CustomerUI());
        equipmentBtn.addActionListener(e -> new EquipmentUI());
        rentalBtn.addActionListener(e -> new RentalUI());
        paymentBtn.addActionListener(e -> new PaymentUI());
        damageBtn.addActionListener(e -> new DamageUI());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
