package ui;

import db.DBConnection;
import util.ResultSetTableModel;

import javax.swing.*;
import java.sql.*;

public class EquipmentUI extends JFrame {
    private Connection conn;
    private JTable table;
    private JTextField nameField, brandField, rateField, condField, availField;

    public EquipmentUI() {
        conn = DBConnection.getConnection();
        setTitle("Equipment");
        setSize(900, 480);
        setLayout(null);

        JLabel ln = new JLabel("Name:"); ln.setBounds(20,20,80,25); add(ln);
        nameField = new JTextField(); nameField.setBounds(100,20,180,25); add(nameField);

        JLabel lb = new JLabel("Brand:"); lb.setBounds(20,60,80,25); add(lb);
        brandField = new JTextField(); brandField.setBounds(100,60,180,25); add(brandField);

        JLabel lr = new JLabel("Daily Rate:"); lr.setBounds(20,100,80,25); add(lr);
        rateField = new JTextField(); rateField.setBounds(100,100,180,25); add(rateField);

        JLabel lc = new JLabel("Condition:"); lc.setBounds(20,140,80,25); add(lc);
        condField = new JTextField(); condField.setBounds(100,140,180,25); add(condField);

        JLabel la = new JLabel("Availability:"); la.setBounds(20,180,80,25); add(la);
        availField = new JTextField(); availField.setBounds(100,180,180,25); add(availField);
        
        // JLabel la = new JLabel("Availability:"); la.setBounds(20,180,80,25); add(la);
        // String[] availOpt =  { "Available", "Unavailable"}; JComboBox<String> availBox = new JComboBox<>(availOpt); availBox.setBounds(100,180,180,25); add (availBox);


        JButton addBtn = new JButton("Add"); addBtn.setBounds(100,220,80,30); add(addBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(190,220,90,30); add(refreshBtn);
        JButton delBtn = new JButton("Delete"); delBtn.setBounds(290,220,80,30); add(delBtn);

        table = new JTable(); JScrollPane sp = new JScrollPane(table); sp.setBounds(400,20,460,380); add(sp);

        loadData();

        addBtn.addActionListener(e -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Equipment(EquipmentID, Name, Brand, Daily_Rate, Condition, Availability) VALUES (EQUIPMENT_SEQ.nextval,?,?,?,?,?)")) {
                ps.setString(1, nameField.getText());
                ps.setString(2, brandField.getText());
                ps.setDouble(3, Double.parseDouble(rateField.getText()));
                ps.setString(4, condField.getText());
                ps.setString(5, availField.getText());
                ps.executeUpdate(); loadData();
            } catch(Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Insert failed"); }
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select row"); return; }
            Object id = table.getValueAt(row,0);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Equipment WHERE EquipmentID=?")) {
                ps.setObject(1,id); ps.executeUpdate(); loadData();
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        refreshBtn.addActionListener(e -> loadData());

        setLocationRelativeTo(null); setVisible(true);
    }

    private void loadData() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT EquipmentID, Name, Brand, Daily_Rate, Condition, Availability FROM Equipment ORDER BY EquipmentID")) {
            table.setModel(new ResultSetTableModel(rs));
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
