package ui;

import db.DBConnection;
import util.ResultSetTableModel;

import javax.swing.*;
import java.sql.*;

public class DamageUI extends JFrame {
    private Connection conn;
    private JTable table;
    private JComboBox<Integer> rentalBox, equipBox;
    private JTextField descField, costField;

    public DamageUI() {
        conn = DBConnection.getConnection();
        setTitle("Damage Reports");
        setSize(900,420);
        setLayout(null);

        JLabel lr = new JLabel("RentalID:"); lr.setBounds(20,20,80,25); add(lr);
        rentalBox = new JComboBox<>(); rentalBox.setBounds(100,20,120,25); add(rentalBox);

        JLabel le = new JLabel("EquipmentID:"); le.setBounds(20,60,80,25); add(le);
        equipBox = new JComboBox<>(); equipBox.setBounds(100,60,120,25); add(equipBox);

        JLabel ld = new JLabel("Desc:"); ld.setBounds(20,100,80,25); add(ld);
        descField = new JTextField(); descField.setBounds(100,100,200,25); add(descField);

        JLabel lc = new JLabel("Cost:"); lc.setBounds(20,140,80,25); add(lc);
        costField = new JTextField(); costField.setBounds(100,140,120,25); add(costField);

        JButton addBtn = new JButton("Add Report"); addBtn.setBounds(100,180,120,30); add(addBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(230,180,90,30); add(refreshBtn);

        table = new JTable(); JScrollPane sp = new JScrollPane(table); sp.setBounds(340,20,520,350); add(sp);

        loadBoxes(); loadData();

        addBtn.addActionListener(e -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO DamageReport(ReportID,RentalID,EquipmentID,Description,Cost,ReportDate) " +
                            "VALUES (damage_seq.nextval,?,?,?,?,SYSDATE)")) {

                ps.setInt(1, (Integer) rentalBox.getSelectedItem());
                ps.setInt(2, (Integer) equipBox.getSelectedItem());
                ps.setString(3, descField.getText());
                ps.setDouble(4, Double.parseDouble(costField.getText()));

                ps.executeUpdate();
                loadData();

                descField.setText("");
                costField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Insert failed");
            }
        });
        
        refreshBtn.addActionListener(e -> { loadBoxes(); loadData(); });

        setLocationRelativeTo(null); setVisible(true);
    }

    private void loadBoxes() {
        rentalBox.removeAllItems();
        equipBox.removeAllItems();

        try (Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT RentalID FROM Rental ORDER BY RentalID")) {

            while (rs1.next()) {
                rentalBox.addItem(rs1.getInt("RentalID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT EquipmentID FROM Equipment ORDER BY EquipmentID")) {

            while (rs2.next()) {
                equipBox.addItem(rs2.getInt("EquipmentID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM DamageReport ORDER BY ReportDate DESC")) {
            table.setModel(new ResultSetTableModel(rs));
        } catch(Exception e){ e.printStackTrace(); }
    }
}
