/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rental_dvd_yoga;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class Rental_Form extends javax.swing.JFrame {

    /**
     * Creates new form Rental_Form
     */
    private final DefaultTableModel tableModel;
    Object[] data = new Object[4];
    String Jenis, NamaLaptop;
    int nomor = 1000;

    public Rental_Form() {
        initComponents();

        // memanggil fungsi untuk emmunculkan tanggal
        tanggal();
        NomorSewa();
        setLocationRelativeTo(null);
        tableModel = (DefaultTableModel) Tabledata.getModel();

        // unutk menambahkan item di combobox dengan array
        String[] JenisLaptop = {"Laptop Gaming", "Laptop Notebook"};
        for (String jenislaptop : JenisLaptop) {
            txtjenis.addItem(jenislaptop);
        }

        // memanggil fungsi untuk menampilkan deposit
        NilaiDepo();

    }

    // fungsi untuk menampilkan nomor transaaksi
    public void tanggal() {
        Date skrng = new Date();
        DateFormat kode = new SimpleDateFormat("ddMMyyyy");
        DateFormat tgl = new SimpleDateFormat("dd/MM/yyyy");
        String tanggal = tgl.format(skrng);
        String Kode = kode.format(skrng);
        txtpinjam.setText(tanggal);
        String kodetransaksi = "TRS-" + Kode;

        txttransaksi.setText(kodetransaksi);
    }

    public void NomorSewa() {
        nomor++;
        String nomorsewa = "SEWA-" + nomor;

        txtnomor.setText(nomorsewa);
    }

    public void inputtanggal() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            Date date = format.parse(txtkembali.getText());

            Date tglpinjam = format.parse(txtkembali.getText());
            Date tglkembali = format.parse(txtpinjam.getText());

            long awal = tglpinjam.getTime();
            long akhir = tglkembali.getTime();
            long lama;
            lama = awal - akhir;

            long lama2 = TimeUnit.MILLISECONDS.toDays(lama);
            txtlama.setText(String.valueOf(lama2));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Masukan Tanggal Sesuai " + "Format dd/mm/yyyy", "Pesan Error", JOptionPane.INFORMATION_MESSAGE);
            txtkembali.setText("");
            txtkembali.requestFocus();
        }
    }

    public void NilaiDenda() {
        int lama = Integer.parseInt(txtlama.getText());
        if (lama >= 1 && lama <= 5) {
            txtdenda.setText(Integer.toString(0));
        } else if (lama >= 5 && lama <= 10) {
            txtdenda.setText(Integer.toString(50000));
        } else if (lama >= 10 && lama <= 15) {
            txtdenda.setText(Integer.toString(75000));
        } else {
            JOptionPane.showMessageDialog(null, "Maaf sewa anda telah melebihi batas waktu, maka anda kena denda 50% dari harga sewa", "Pesan Error", JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < Tabledata.getRowCount(); i++) {
                int sewa = Integer.parseInt((String) Tabledata.getValueAt(i, 3));
                int totaldenda = (50 * sewa) / 100;

                txtdenda.setText(Integer.toString(totaldenda));
            }
        }
    }

    public void ItemNamaLaptop() {
        String pilihjenis = txtjenis.getSelectedItem().toString();

        switch (pilihjenis) {
            case "Laptop Gaming":
                txtnamalaptop.removeAllItems();
                String[] Laptopgaming = {"Laptop ROG Zephyrus", "Laptop Acer Nitro 5"};
                for (String gaming : Laptopgaming) {
                    txtnamalaptop.addItem(gaming);
                }
                break;
            case "Laptop Notebook":
                txtnamalaptop.removeAllItems();
                String[] Laptopnotebook = {"Laptop ASUS Vivobook 14", "Laptop ASUS Vivobook 14 Oled"};
                for (String notebook : Laptopnotebook) {
                    txtnamalaptop.addItem(notebook);
                }
                break;
            default:
                txtnamalaptop.removeAllItems();
                break;
        }
    }

    public void NilaiDepo() {
        for (int i = 0; i < Tabledata.getRowCount(); i++) {
            int sewa = Integer.parseInt((String) Tabledata.getValueAt(i, 3));
            int totaldepo = (30 * sewa) / 100;

            txtdeposit.setText(Integer.toString(totaldepo));
        }
    }

    public void HargaSewa() {
        String pilihnama = txtnamalaptop.getSelectedItem().toString();

        switch (pilihnama) {
            case "Laptop ROG Zephyrus":
                txtsewa.setText(Integer.toString(450000));
                break;
            case "Laptop Acer Nitro 5":
                txtsewa.setText(Integer.toString(300000));
                break;
            case "Laptop ASUS Vivobook 14":
                txtsewa.setText(Integer.toString(250000));
                break;
            case "Laptop ASUS Vivobook 14 Oled":
                txtsewa.setText(Integer.toString(350000));
                break;
            default:
                txtsewa.setText(Integer.toString(0));
                break;
        }
    }

    public void ProsesHarga() {
        int total = 0;
        int deposit, denda, lama;
        deposit = Integer.parseInt(txtdeposit.getText());
        denda = Integer.parseInt(txtdenda.getText());
        lama = Integer.parseInt(txtlama.getText());
        for (int i = 0; i < Tabledata.getRowCount(); i++) {
            int sewa = Integer.parseInt((String) Tabledata.getValueAt(i, 3));
            total = ((total + (sewa * lama) + denda) - deposit);
            txtharga.setText(String.valueOf(total));
            String AA = Tabledata.getValueAt(i, 0) + "\t" + Tabledata.getValueAt(i, 2) + "\t" + Tabledata.getValueAt(i, 3) + "\n";
            TextArea.append(AA);
        }
    }

    public void tambah() {
        data[0] = txtnomor.getText();
        data[1] = txtjenis.getSelectedItem().toString();
        data[2] = txtnamalaptop.getSelectedItem().toString();
        data[3] = txtsewa.getText();
        tableModel.addRow(data);
        kosong();
    }

    public void kosong() {
        txtjenis.setSelectedItem(false);
        txtnamalaptop.setSelectedItem(false);
        txtsewa.setText("");
        txtnomor.requestFocus();
        NomorSewa();
    }

    public void tampil() {
        String notrans = txttransaksi.getText();
        labelno.setText(notrans);
        String nama = txtnama.getText();
        labelnama.setText(nama);
        String ktp = txtktp.getText();
        labelktp.setText(ktp);
        String kembali = txtkembali.getText();
        labelkembali.setText(kembali);
        String pinjam = txtpinjam.getText();
        labelpinjam.setText(pinjam);
        String lama = txtlama.getText();
        labellama.setText(lama + " Hari");
        String deposit = txtdeposit.getText();
        labeldeposit.setText("Rp. " + deposit);
        String denda = txtdenda.getText();
        labeldenda.setText("Rp. " + denda);
        String harga = txtharga.getText();
        labelharga.setText("Rp. " + harga);

        judul.setText("Data Rental Laptop " + notrans);
    }

    public void Clear() {
        labelno.setText("");
        labelnama.setText("");
        labelktp.setText("");
        labelkembali.setText("");
        labelpinjam.setText("");
        labellama.setText("");
        labeldeposit.setText("");
        labeldenda.setText("");
        labelharga.setText("");
        TextArea.setText("");
        
        txtnama.setText("");
        txtkembali.setText("");
        txtdenda.setText("");
        txtlama.setText("");
        txtdeposit.setText("");
        txtharga.setText("");
        txtktp.setText("");

        judul.setText("Data Rental Laptop");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txttransaksi = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtktp = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtpinjam = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtkembali = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtlama = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtdeposit = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtdenda = new javax.swing.JTextField();
        Denda = new javax.swing.JLabel();
        Bpreview = new javax.swing.JButton();
        Bproses = new javax.swing.JButton();
        txtharga = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtnomor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtjenis = new javax.swing.JComboBox<>();
        txtnamalaptop = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtsewa = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Bsimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabledata = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        judul = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelno = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelnama = new javax.swing.JLabel();
        labelktp = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelkembali = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        labelpinjam = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        labellama = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        labeldeposit = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        labelharga = new javax.swing.JLabel();
        labeldenda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rental Laptop911");
        setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        setResizable(false);

        MainPane.setBackground(new java.awt.Color(5, 8, 13));
        MainPane.setForeground(new java.awt.Color(204, 0, 51));
        MainPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        MainPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        MainPane.setAlignmentX(0.9F);
        MainPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        MainPane.setFont(new java.awt.Font("ROG Fonts", 1, 24)); // NOI18N
        MainPane.setInheritsPopupMenu(true);
        MainPane.setOpaque(true);
        MainPane.setRequestFocusEnabled(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Form Rental", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel14.setText("Nomor Transaksi");

        txttransaksi.setBackground(new java.awt.Color(204, 0, 51));
        txttransaksi.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txttransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txttransaksi.setCaretColor(new java.awt.Color(255, 255, 255));
        txttransaksi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txttransaksi.setEnabled(false);
        txttransaksi.setOpaque(true);
        txttransaksi.setSelectionColor(new java.awt.Color(204, 0, 51));

        txtnama.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtnama.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtnama.setCaretColor(new java.awt.Color(93, 95, 239));
        txtnama.setDisabledTextColor(new java.awt.Color(93, 95, 239));

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel15.setText("Nama Pelanggan");

        txtktp.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtktp.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtktp.setCaretColor(new java.awt.Color(93, 95, 239));
        txtktp.setDisabledTextColor(new java.awt.Color(93, 95, 239));

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel16.setText("No KTP");

        txtpinjam.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtpinjam.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtpinjam.setCaretColor(new java.awt.Color(93, 95, 239));
        txtpinjam.setDisabledTextColor(new java.awt.Color(93, 95, 239));

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel17.setText("Tanggal Pinjam");

        txtkembali.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtkembali.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtkembali.setCaretColor(new java.awt.Color(93, 95, 239));
        txtkembali.setDisabledTextColor(new java.awt.Color(93, 95, 239));
        txtkembali.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtkembaliFocusLost(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel18.setText("Tanggal Kembali");

        txtlama.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtlama.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtlama.setCaretColor(new java.awt.Color(93, 95, 239));
        txtlama.setDisabledTextColor(new java.awt.Color(93, 95, 239));
        txtlama.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel20.setText("Lama Pinjam");

        txtdeposit.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtdeposit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtdeposit.setCaretColor(new java.awt.Color(93, 95, 239));
        txtdeposit.setDisabledTextColor(new java.awt.Color(93, 95, 239));

        jLabel21.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel21.setText("Deposit");

        txtdenda.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtdenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtdenda.setCaretColor(new java.awt.Color(93, 95, 239));
        txtdenda.setDisabledTextColor(new java.awt.Color(93, 95, 239));

        Denda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        Denda.setText("Denda");

        Bpreview.setBackground(new java.awt.Color(0, 80, 197));
        Bpreview.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        Bpreview.setForeground(new java.awt.Color(255, 255, 255));
        Bpreview.setText("Preview");
        Bpreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BpreviewActionPerformed(evt);
            }
        });

        Bproses.setBackground(new java.awt.Color(0, 80, 197));
        Bproses.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        Bproses.setForeground(new java.awt.Color(255, 255, 255));
        Bproses.setText("Proses");
        Bproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BprosesActionPerformed(evt);
            }
        });

        txtharga.setBackground(new java.awt.Color(204, 0, 51));
        txtharga.setFont(new java.awt.Font("ROG Fonts", 0, 18)); // NOI18N
        txtharga.setForeground(new java.awt.Color(204, 0, 0));
        txtharga.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtharga.setCaretColor(new java.awt.Color(93, 95, 239));
        txtharga.setDisabledTextColor(new java.awt.Color(204, 0, 0));
        txtharga.setEnabled(false);
        txtharga.setOpaque(true);

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel19.setText("Total Harga");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnama, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtktp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpinjam, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtkembali, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlama, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdeposit, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdenda, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtharga, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Denda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(Bproses, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Bpreview, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtktp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtlama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtdeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Denda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtdenda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bpreview, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bproses, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Form Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel10.setText("Nomor Laptop");

        txtnomor.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        txtnomor.setForeground(new java.awt.Color(204, 0, 0));
        txtnomor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtnomor.setDisabledTextColor(new java.awt.Color(204, 0, 0));
        txtnomor.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel11.setText("Jenis Laptop");

        txtjenis.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        txtjenis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtjenisItemStateChanged(evt);
            }
        });
        txtjenis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtjenisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtjenisFocusLost(evt);
            }
        });
        txtjenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjenisActionPerformed(evt);
            }
        });

        txtnamalaptop.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        txtnamalaptop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtnamalaptopItemStateChanged(evt);
            }
        });
        txtnamalaptop.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnamalaptopFocusLost(evt);
            }
        });
        txtnamalaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamalaptopActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel12.setText("Nama Laptop");

        txtsewa.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        txtsewa.setForeground(new java.awt.Color(204, 0, 0));
        txtsewa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtsewa.setCaretColor(new java.awt.Color(93, 95, 239));
        txtsewa.setDisabledTextColor(new java.awt.Color(204, 0, 0));
        txtsewa.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel13.setText("Harga Sewa");

        Bsimpan.setBackground(new java.awt.Color(0, 80, 197));
        Bsimpan.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        Bsimpan.setForeground(new java.awt.Color(255, 255, 255));
        Bsimpan.setText("Simpan");
        Bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsimpanActionPerformed(evt);
            }
        });

        Tabledata.setFont(new java.awt.Font("ROG Fonts", 0, 14)); // NOI18N
        Tabledata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor", "Jenis", "Nama", "Harga Sewa"
            }
        ));
        jScrollPane1.setViewportView(Tabledata);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnomor)
                    .addComponent(txtjenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnamalaptop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsewa)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(Bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtnomor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtjenis, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtnamalaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtsewa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(Bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 867, Short.MAX_VALUE)
        );

        MainPane.addTab("FORM RENTAL", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Verdana", 0, 18))); // NOI18N

        judul.setBackground(new java.awt.Color(0, 0, 0));
        judul.setFont(new java.awt.Font("ROG Fonts", 0, 35)); // NOI18N
        judul.setForeground(new java.awt.Color(204, 0, 0));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setLabelFor(judul);
        judul.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        judul.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel2.setText("No Transaksi");

        labelno.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labelno.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel4.setText("Nama Pelanggan");

        labelnama.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labelnama.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelktp.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labelktp.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel7.setText("No. KTP");

        labelkembali.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labelkembali.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel9.setText("Tanggal Kembali");

        jLabel22.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel22.setText("Tanggal Pinjam");

        labelpinjam.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labelpinjam.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TextArea.setColumns(20);
        TextArea.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        TextArea.setForeground(new java.awt.Color(204, 0, 0));
        TextArea.setLineWrap(true);
        TextArea.setRows(5);
        TextArea.setDisabledTextColor(new java.awt.Color(204, 0, 0));
        TextArea.setEnabled(false);
        jScrollPane2.setViewportView(TextArea);

        jLabel26.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel26.setText("Lama");

        labellama.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labellama.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel28.setText("Deposit");

        labeldeposit.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labeldeposit.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel30.setFont(new java.awt.Font("ROG Fonts", 0, 15)); // NOI18N
        jLabel30.setText("Denda");

        labelharga.setBackground(new java.awt.Color(204, 0, 51));
        labelharga.setFont(new java.awt.Font("ROG Fonts", 0, 40)); // NOI18N
        labelharga.setForeground(new java.awt.Color(255, 255, 255));
        labelharga.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelharga.setText("jLabel3");
        labelharga.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        labelharga.setOpaque(true);

        labeldenda.setFont(new java.awt.Font("ROG Fonts", 1, 18)); // NOI18N
        labeldenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labeldenda, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelharga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labeldeposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(1, 1, 1))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelnama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelktp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(labelkembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(labelpinjam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labellama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(30, 30, 30))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelno, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelnama, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelktp, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labellama, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeldeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeldenda, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelharga, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MainPane.addTab("PREVIEW", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MainPane, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtjenisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtjenisFocusLost
        // TODO add your handling code here:
        ItemNamaLaptop();
    }//GEN-LAST:event_txtjenisFocusLost

    private void txtnamalaptopFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnamalaptopFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtnamalaptopFocusLost

    private void txtjenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjenisActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_txtjenisActionPerformed

    private void txtnamalaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamalaptopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamalaptopActionPerformed

    private void txtjenisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtjenisItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjenisItemStateChanged

    private void txtnamalaptopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtnamalaptopItemStateChanged
        // TODO add your handling code here:
        HargaSewa();
    }//GEN-LAST:event_txtnamalaptopItemStateChanged

    private void BsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsimpanActionPerformed
        // TODO add your handling code here:
        if (txtnomor.getText().equals("") || txtnamalaptop.getSelectedIndex() == -1 || txtnamalaptop.getSelectedIndex() == -1 || txtsewa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
            txtnomor.requestFocus();
        } else {
            tambah();
        }
    }//GEN-LAST:event_BsimpanActionPerformed

    private void txtkembaliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtkembaliFocusLost
        // TODO add your handling code here:
        inputtanggal();

        NilaiDepo();
        NilaiDenda();
    }//GEN-LAST:event_txtkembaliFocusLost

    private void BprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BprosesActionPerformed
        // TODO add your handling code here:
        ProsesHarga();
    }//GEN-LAST:event_BprosesActionPerformed

    private void BpreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BpreviewActionPerformed
        // TODO add your handling code here:
        String TextButton = Bpreview.getText();
        if (TextButton.equals("Preview")){
            Bpreview.setText("Clear");
            MainPane.setSelectedIndex(1);
            Bpreview.setBackground(new java.awt.Color(204, 0, 51));
            tampil();
        }else if (TextButton.equals("Clear")){
            Clear();
            Bpreview.setBackground(new java.awt.Color(0,80,197));
            Bpreview.setText("Preview"); 
        }else{
            Bpreview.setBackground(new java.awt.Color(0,80,197));
            Bpreview.setText("Preview"); 
        }
    }//GEN-LAST:event_BpreviewActionPerformed

    private void txtjenisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtjenisFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjenisFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Rental_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rental_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rental_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rental_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Rental_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bpreview;
    private javax.swing.JButton Bproses;
    private javax.swing.JButton Bsimpan;
    private javax.swing.JLabel Denda;
    private javax.swing.JTabbedPane MainPane;
    private javax.swing.JTable Tabledata;
    private javax.swing.JTextArea TextArea;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel labeldenda;
    private javax.swing.JLabel labeldeposit;
    private javax.swing.JLabel labelharga;
    private javax.swing.JLabel labelkembali;
    private javax.swing.JLabel labelktp;
    private javax.swing.JLabel labellama;
    private javax.swing.JLabel labelnama;
    private javax.swing.JLabel labelno;
    private javax.swing.JLabel labelpinjam;
    private javax.swing.JTextField txtdenda;
    private javax.swing.JTextField txtdeposit;
    private javax.swing.JTextField txtharga;
    private javax.swing.JComboBox<String> txtjenis;
    private javax.swing.JTextField txtkembali;
    private javax.swing.JTextField txtktp;
    private javax.swing.JTextField txtlama;
    private javax.swing.JTextField txtnama;
    private javax.swing.JComboBox<String> txtnamalaptop;
    private javax.swing.JTextField txtnomor;
    private javax.swing.JTextField txtpinjam;
    private javax.swing.JTextField txtsewa;
    private javax.swing.JTextField txttransaksi;
    // End of variables declaration//GEN-END:variables
}
