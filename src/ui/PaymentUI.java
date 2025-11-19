package ui;

import db.DBConnection;
import util.ResultSetTableModel;

import javax.swing.*;
import java.sql.*;

public class PaymentUI extends JFrame {
    private Connection conn;
    private JTable table;
    private JComboBox<Integer> rentalBox;
    private JTextField amountField, methodField;

    public PaymentUI() {
        conn = DBConnection.getConnection();
        setTitle("Payments");
        setSize(700,420);
        setLayout(null);

        JLabel lr = new JLabel("RentalID:"); lr.setBounds(20,20,80,25); add(lr);
        rentalBox = new JComboBox<>(); rentalBox.setBounds(100,20,140,25); add(rentalBox);

        JLabel la = new JLabel("Amount:"); la.setBounds(20,60,80,25); add(la);
        amountField = new JTextField(); amountField.setBounds(100,60,140,25); add(amountField);

        JLabel lm = new JLabel("Method:"); lm.setBounds(20,100,80,25); add(lm);
        methodField = new JTextField(); methodField.setBounds(100,100,140,25); add(methodField);

        JButton addBtn = new JButton("Add Payment"); addBtn.setBounds(100,140,120,30); add(addBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(230,140,90,30); add(refreshBtn);

        table = new JTable(); JScrollPane sp = new JScrollPane(table); sp.setBounds(260,20,410,340); add(sp);

        loadRentals(); loadData();

        addBtn.addActionListener(e -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Payment(PaymentID, RentalID, Amount, MethodUsed, PaymentDate) VALUES (PAYMENT_SEQ.nextval,?,?,?,SYSDATE)")) {
                ps.setInt(1, (Integer)rentalBox.getSelectedItem());
                ps.setDouble(2, Double.parseDouble(amountField.getText()));
                ps.setString(3, methodField.getText());
                ps.executeUpdate(); loadData();
            } catch(Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Insert failed"); }
        });

        refreshBtn.addActionListener(e -> { loadRentals(); loadData(); });

        setLocationRelativeTo(null); setVisible(true);
    }

    private void loadRentals() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT RentalID FROM Rental ORDER BY RentalID")) {
            rentalBox.removeAllItems();
            while (rs.next()) rentalBox.addItem(rs.getInt(1));
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void loadData() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT PaymentID, RentalID, Amount, MethodUsed, PaymentDate FROM Payment ORDER BY PaymentID")) {
            table.setModel(new ResultSetTableModel(rs));
        } catch(Exception e) { e.printStackTrace(); }
    }
}
