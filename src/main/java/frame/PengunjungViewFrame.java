package frame;

import helpers.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class PengunjungViewFrame extends JFrame{
    private JPanel mainPanel;
    private JPanel cariPanel;
    private JScrollPane viewScrollPane;
    private JPanel buttonPanel;
    private JTextField cariTextField;
    private JButton cariButton;
    private JTable viewTable;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton cetakButton;
    private JButton tutupButton;

    public PengunjungViewFrame(){
        tutupButton.addActionListener(e -> {
            dispose();
        });
        batalButton.addActionListener(e -> {
            isiTable();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                isiTable();
            }
        });
        cariButton.addActionListener(e -> {
            if (cariTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Isi kata kunci pencarian",
                        "Validasi kata kunci kosong",
                        JOptionPane.WARNING_MESSAGE);
                cariTextField.requestFocus();
                return;
            }
            Connection c = Koneksi.getConnection();
            String keyword = "%" + cariTextField.getText() + "%";
            String searchSQL = "SELECT K.*,B.nama AS nama_alamat " +
                    "FROM pengunjung K " +
                    "LEFT JOIN alamat B ON K.alamat_id = B.id " +
                    "WHERE K.nama like ? OR B.nama like ?";
            try {
                PreparedStatement ps = c.prepareStatement(searchSQL);
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ResultSet rs = ps.executeQuery();
                DefaultTableModel dtm = (DefaultTableModel) viewTable.getModel();
                dtm.setRowCount(0);
                Object[] row = new Object[3];
                while (rs.next()) {
                    row[0] = rs.getInt("id");
                    row[1] = rs.getString("nama");
                    row[2] = rs.getString("nama_alamat");
                    dtm.addRow(row);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        hapusButton.addActionListener(e -> {
            int barisTerpilih = viewTable.getSelectedRow();
            if (barisTerpilih < 0){
                JOptionPane.showMessageDialog(null,"Pilih data dulu");
                return;
            }
            int pilihan = JOptionPane.showConfirmDialog(null,
                    "Yakin mau hapus?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION
            );
            if (pilihan == 0){
                TableModel tm = viewTable.getModel();
                int id = Integer.parseInt(tm.getValueAt(barisTerpilih,0).toString());
                Connection c = Koneksi.getConnection();
                String deleteSQL = "DELETE FROM pengunjung WHERE id = ?";
                try {
                    PreparedStatement ps = c.prepareStatement(deleteSQL);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        tambahButton.addActionListener(e -> {
            PengunjungInputFrame inputFrame = new PengunjungInputFrame();
            inputFrame.setVisible(true);
        });
        ubahButton.addActionListener(e -> {
            int barisTerpilih = viewTable.getSelectedRow();
            if (barisTerpilih < 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Pilih Data Dulu...");
                return;
            }
            TableModel tm = viewTable.getModel();
            int id = Integer.parseInt(tm.getValueAt(barisTerpilih,0).toString());
            PengunjungInputFrame inputFrame = new PengunjungInputFrame();
            inputFrame.setId(id);
            inputFrame.isiKomponen();
            inputFrame.setVisible(true);
        });
        isiTable();
        init();
    }

    public void init(){
        setContentPane(mainPanel);
        setTitle("Data Pengunjung Museum");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiTable(){
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT K.*,B.nama AS nama_alamat FROM pengunjung AS K " +
                "LEFT JOIN alamat AS B ON K.alamat_id = B.id";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);

            String header[] = {"Id","Nama Pengunjung","Alamat"};
            DefaultTableModel dtm = new DefaultTableModel(header,0);
            viewTable.setModel(dtm);

            Object[] row = new Object[3];
            while (rs.next()){
                row[0] = rs.getInt("id");
                row[1] = rs.getString("nama");
                row[2] = rs.getString("nama_alamat");
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
