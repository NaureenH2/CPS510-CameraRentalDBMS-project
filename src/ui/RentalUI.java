package ui;

import db.DBConnection;
import util.ResultSetTableModel;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalUI extends JFrame {
    private Connection conn;
    private JTable table;
    private JComboBox<Integer> custBox, equipBox;
    private JTextField startField, returnField, statusField;

    public RentalUI() {
        conn = DBConnection.getConnection();
        setTitle("Rentals");
        setSize(1000, 520);
        setLayout(null);

        JLabel lc = new JLabel("CustomerID:"); lc.setBounds(20,20,100,25); add(lc);
        custBox = new JComboBox<>(); custBox.setBounds(120,20,150,25); add(custBox);
        JLabel le = new JLabel("EquipmentID:"); le.setBounds(20,60,100,25); add(le);
        equipBox = new JComboBox<>(); equipBox.setBounds(120,60,150,25); add(equipBox);

        JLabel ls = new JLabel("Start (YYYY-MM-DD):"); ls.setBounds(20,100,150,25); add(ls);
        startField = new JTextField(); startField.setBounds(170,100,100,25); add(startField);

        JLabel lr = new JLabel("Return (YYYY-MM-DD):"); lr.setBounds(20,140,150,25); add(lr);
        returnField = new JTextField(); returnField.setBounds(170,140,100,25); add(returnField);

        JLabel st = new JLabel("Status:"); st.setBounds(20,180,80,25); add(st);
        statusField = new JTextField("Active"); statusField.setBounds(120,180,100,25); add(statusField);

        JButton addBtn = new JButton("Create Rental"); addBtn.setBounds(50,220,160,30); add(addBtn);
        JButton calcBtn = new JButton("Calc Total"); calcBtn.setBounds(220,220,120,30); add(calcBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(50,260,120,30); add(refreshBtn);

        table = new JTable(); JScrollPane sp = new JScrollPane(table); sp.setBounds(320,20,640,420); add(sp);

        loadComboBoxes(); loadData();

        addBtn.addActionListener(e -> {
            try (PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO Rental(RentalID, CustomerID, EquipmentID, Status, Start_Date, Return_Date, Total_Cost) VALUES (RENTAL_SEQ.nextval,?,?,?,?,?,?)")) {
                int cust = (Integer)custBox.getSelectedItem();
                int equip = (Integer)equipBox.getSelectedItem();
                Date sdate = Date.valueOf(startField.getText());
                Date rdate = (returnField.getText().isEmpty()) ? null : Date.valueOf(returnField.getText());
                ps.setInt(1,cust); ps.setInt(2,equip); ps.setString(3,statusField.getText());
                ps.setDate(4,sdate); ps.setDate(5,rdate);
                // simple cost calc: daily_rate * days (nullable)
                double total = computeTotal(equip, sdate, rdate);
                ps.setDouble(6, total);
                ps.executeUpdate(); loadData();
            } catch(Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Insert failed"); }
        });

        calcBtn.addActionListener(e -> {
            try {
                int equip = (Integer)equipBox.getSelectedItem();
                Date sdate = Date.valueOf(startField.getText());
                Date rdate = (returnField.getText().isEmpty()) ? Date.valueOf(LocalDate.now()) : Date.valueOf(returnField.getText());
                double tot = computeTotal(equip, sdate, rdate);
                JOptionPane.showMessageDialog(this,"Estimated Total: " + tot);
            } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Invalid dates"); }
        });

        refreshBtn.addActionListener(e -> { loadComboBoxes(); loadData(); });

        setLocationRelativeTo(null); setVisible(true);
    }

    private double computeTotal(int equipmentID, Date sdate, Date rdate) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT Daily_Rate FROM Equipment WHERE EquipmentID=?")) {
            ps.setInt(1, equipmentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double rate = rs.getDouble(1);
                    LocalDate s = sdate.toLocalDate();
                    LocalDate r = (rdate==null) ? LocalDate.now() : rdate.toLocalDate();
                    long days = ChronoUnit.DAYS.between(s, r);
                    if (days <= 0) days = 1;
                    return rate * (double) days;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }

    private void loadComboBoxes() {

        custBox.removeAllItems();
        equipBox.removeAllItems();

        // Load customers
        try (Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT CustomerID FROM Customer ORDER BY CustomerID")) {

            while (rs1.next()) {
                custBox.addItem(rs1.getInt("CustomerID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load equipment
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
             ResultSet rs = st.executeQuery("SELECT RentalID, CustomerID, EquipmentID, Status, Start_Date, Return_Date, Total_Cost FROM Rental ORDER BY RentalID")) {
            table.setModel(new ResultSetTableModel(rs));
        } catch(Exception e) { e.printStackTrace(); }
    }
}
