package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CustomerUI extends JFrame {
    private Connection conn;
    private JTable table;
    private JTextField nameField, emailField, phoneField;

    public CustomerUI() {
        conn = DBConnection.getConnection();
        setTitle("Customers");
        setSize(800, 450);
        setLayout(null);

        JLabel lblName = new JLabel("Name:"); lblName.setBounds(20,20,80,25); add(lblName);
        nameField = new JTextField(); nameField.setBounds(100,20,200,25); add(nameField);

        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(20,60,80,25); add(lblEmail);
        emailField = new JTextField(); emailField.setBounds(100,60,200,25); add(emailField);

        JLabel lblPhone = new JLabel("Phone:"); lblPhone.setBounds(20,100,80,25); add(lblPhone);
        phoneField = new JTextField(); phoneField.setBounds(100,100,200,25); add(phoneField);

        JButton addBtn = new JButton("Add"); addBtn.setBounds(100,140,90,30); add(addBtn);
        JButton delBtn = new JButton("Delete"); delBtn.setBounds(210,140,90,30); add(delBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(100,180,200,30); add(refreshBtn);

        table = new JTable();
        JScrollPane sp = new JScrollPane(table); sp.setBounds(330,20,430,360); add(sp);

        loadData();

        addBtn.addActionListener(e -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Customer(CustomerID, Name, Email, Phone) VALUES (CUSTOMER_SEQ.nextval, ?, ?, ?)")) {
                if (nameField.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Name required"); return; }
                ps.setString(1, nameField.getText());
                ps.setString(2, emailField.getText());
                ps.setString(3, phoneField.getText());
                ps.executeUpdate();
                loadData();
            } catch (SQLException ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Insert failed"); }
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            Object id = table.getValueAt(row,0);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Customer WHERE CustomerID=?")) {
                ps.setObject(1, id); ps.executeUpdate(); loadData();
            } catch(SQLException ex){ ex.printStackTrace(); }
        });

        refreshBtn.addActionListener(e -> loadData());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadData() {
        String query = "SELECT * FROM Customer ORDER BY CustomerID";
        try (Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query)) {

            // Create table model
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("CustomerID");
            model.addColumn("Name");
            model.addColumn("Email");
            model.addColumn("Phone");

            // Fill rows
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("CustomerID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone")
                });
            }

            table.setModel(model); // update JTable

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data");
        }
    }
}
