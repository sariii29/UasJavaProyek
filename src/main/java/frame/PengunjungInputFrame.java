package frame;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import helpers.ComboBoxItem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class PengunjungInputFrame extends JFrame{
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField namaTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JComboBox alamatComboBox;
    private JRadioButton lakiLakiRadioButton;
    private JRadioButton perempuanRadioButton;
    private JTextField emailTextField;
    private DatePicker tanggalKunjunganDatePicker;

    private ButtonGroup jenisKelaminButtonGroup;
    private int id;

    public void setId(int id){
        this.id = id;
    }

    public PengunjungInputFrame(){
        batalButton.addActionListener(e -> {
            dispose();
        });
        simpanButton.addActionListener(e -> {
            String nama = namaTextField.getText();
            if (nama.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Isi kata kunci pencarian",
                        "Validasi kata kunci kosong",
                        JOptionPane.WARNING_MESSAGE);
                namaTextField.requestFocus();
                return;
            }
            ComboBoxItem item = (ComboBoxItem) alamatComboBox.getSelectedItem();
            int alamatId = item.getValue();
            if (alamatId == 0){
                JOptionPane.showMessageDialog(null,
                        "Pilih Alamat",
                        "Validasi Combobox",
                        JOptionPane.WARNING_MESSAGE);
                alamatComboBox.requestFocus();
                return;
            }
            String jenisKelamin = "";
            if (lakiLakiRadioButton.isSelected()){
                jenisKelamin = "L";
            }
            else if (perempuanRadioButton.isSelected()){
                jenisKelamin = "P";
            } else {
                JOptionPane.showMessageDialog(null,
                        "Pilih Jenis Kelamin",
                        "Validasi Data Kosong",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String Email = emailTextField.getText();
            if (!Email.contains("@") || !Email.contains(".")) {
                JOptionPane.showMessageDialog(null,
                        "Isi dengan Email valid",
                        "Validasi Email",
                        JOptionPane.WARNING_MESSAGE);
                emailTextField.requestFocus();
                return;
            }
            String tanggalKunjungan = tanggalKunjunganDatePicker.getText();
            if (tanggalKunjungan.equals("")){
                JOptionPane.showMessageDialog(null,
                        "Isi Tanggal Kunjungan",
                        "Validasi Data Kosong",
                        JOptionPane.WARNING_MESSAGE);
                tanggalKunjunganDatePicker.requestFocus();
                return;
            }
                Connection c = Koneksi.getConnection();
                PreparedStatement ps;
            try {
                if (id == 0) {
                    String cekSQL = "SELECT * FROM pengunjung WHERE nama = ? AND id != ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data yang anda masukkan suda ada");
                    }else {
                        String insertSQL = "INSERT INTO pengunjung (id, nama, alamat_id, jenis_kelamin, " +
                                "email, tanggal_kunjungan) " +
                                "VALUES (NULL, ?, ?, ?, ?, ?)";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, nama);
                        ps.setInt(2, alamatId);
                        ps.setString(3, jenisKelamin);
                        ps.setString(4, Email);
                        ps.setString(5, tanggalKunjungan);
                        ps.executeUpdate();
                        dispose();
                    }
                } else {
                    String updateSQL = "UPDATE pengunjung SET nama = ?, alamat_id = ?, jenis_kelamin = ?, " +
                            "email = ?, tanggal_kunjungan = ? WHERE id = ?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, alamatId);
                    ps.setString(3, jenisKelamin);
                    ps.setString(4, Email);
                    ps.setString(5, tanggalKunjungan);
                    ps.setInt(6, id);
                    ps.executeUpdate();
                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        kustomisasiKomponen();
        init();
    }

    public void init(){
        setContentPane(mainPanel);
        setTitle("Input Data Pengunjung");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiKomponen(){
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM pengunjung WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                namaTextField.setText(rs.getString("nama"));
                int alamatId = rs.getInt("alamat_id");
                for (int i = 0; i < alamatComboBox.getItemCount(); i++) {
                    alamatComboBox.setSelectedIndex(i);
                    ComboBoxItem item = (ComboBoxItem) alamatComboBox.getSelectedItem();
                    if (alamatId == item.getValue()) {
                        break;
                    }
                }
                String jenisKelamin = rs.getString("jenis_kelamin");
                if (jenisKelamin != null) {
                    if (jenisKelamin.equals("L")) {
                        lakiLakiRadioButton.setSelected(true);
                    } else if (jenisKelamin.equals("P")) {
                        perempuanRadioButton.setSelected(true);
                    }
                }
                emailTextField.setText(rs.getString("email"));
                tanggalKunjunganDatePicker.setText(rs.getString("tanggal_kunjungan"));
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void kustomisasiKomponen() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM alamat ORDER BY nama";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            alamatComboBox.addItem(new ComboBoxItem(0, "Pilih Alamat"));
            while (rs.next()) {
                alamatComboBox.addItem(new ComboBoxItem(
                        rs.getInt("id"),
                        rs.getString("nama")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        jenisKelaminButtonGroup = new ButtonGroup();
        jenisKelaminButtonGroup.add(lakiLakiRadioButton);
        jenisKelaminButtonGroup.add(perempuanRadioButton);

        DatePickerSettings dps = new DatePickerSettings();
        dps.setFormatForDatesCommonEra("yyyy-MM-dd");
        tanggalKunjunganDatePicker.setSettings(dps);
    }
}
