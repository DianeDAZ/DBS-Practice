package database_homework;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Medicine extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Connection myConnection = null; // ����
	String querySqlAll = "SELECT * FROM medicine";

	JLabel jlMedicineInfo = new JLabel("ҩƷ�����Ϣ");
	JLabel jlNoResult = new JLabel("��ѯ�޹� !");
	JLabel jlDay = new JLabel("��*");
	JLabel jlMonth = new JLabel("��*");
	JLabel jlTo = new JLabel("��");

	JTable jtMedicine = new JTable();
	JTable jtQueryResult = new JTable();
	JTable jtAddMedicine = new JTable();
	JTable jtQueryPrice = new JTable();
	JTable jtDelivery = new JTable();

	DefaultTableModel medicineModel;
	DefaultTableModel resultModel;
	DefaultTableModel insertModel;
	DefaultTableModel priceModel;
	DefaultTableModel deliveryModel;

	JButton jbAdd = new JButton("���");
	JButton jbQuery = new JButton("ҩƷ��ѯ");
	JButton jbDelivery = new JButton("<- �����¼��ѯ*");
	
	JComboBox<Object> comboBox;
	JComboBox<Object> comboBoxYear1;
	JComboBox<Object> comboBoxDay1;
	JComboBox<Object> comboBoxYear2;
	JComboBox<Object> comboBoxDay2;
	
	JTextField jtfQueryCond = new JTextField();
	JPanel jpMain = new JPanel();
	
	JScrollPane jspTable;
	JScrollPane jspTable2;
	JScrollPane jspTable3;
	JScrollPane jspQueryPrice;
	JScrollPane jspDelivery;

	Vector<Vector<Object>> vtMedicineData = new Vector<>();
	Vector<Vector<Object>> vtQueryResult = new Vector<>();
	Vector<Vector<Object>> vtInsertColumn = new Vector<>();
	Vector<Vector<Object>> vtPrice = new Vector<>();
	Vector<Vector<Object>> vtDelivery = new Vector<>();	
	Vector<Object> tableHeader = new Vector<>();
	Vector<Object> priceHeader = new Vector<>();
	Vector<Object> deliveryHeader = new Vector<>();

	public Medicine(Connection conn) {
		this.myConnection = conn; // ���ݿ�����
		this.setBounds(300, 200, 1100, 400);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("ҩƷ�����Ϣ");
		jpMain.setLayout(null);

		jlMedicineInfo.setFont(new Font("����", Font.BOLD, 20));
		jlMedicineInfo.setBounds(500, 0, 150, 50);

		tableHeader.add("ҩƷid");
		tableHeader.add("Ʒ��");
		tableHeader.add("����");
		tableHeader.add("���");
		tableHeader.add("��λ");
		tableHeader.add("��������");
		tableHeader.add("��׼�ĺ�");
		tableHeader.add("��������");
		tableHeader.add("��Ч��");
		tableHeader.add("������id");
		tableHeader.add("��������");
		tableHeader.add("��¼��id");
		tableHeader.add("����");
		tableHeader.add("������");
		tableHeader.add("����");
		tableHeader.add("���ۼ�");
		
		priceHeader.add("��������");
		priceHeader.add("ҩƷid");
		priceHeader.add("Ʒ��");
		priceHeader.add("����");
		priceHeader.add("��������");
		priceHeader.add("��ϵ�绰");
		
		deliveryHeader.add("���ⵥ��ˮ��");
		deliveryHeader.add("��¼��id");
		deliveryHeader.add("�����id");
		deliveryHeader.add("��������");
		deliveryHeader.add("ҩƷid");
		deliveryHeader.add("��������");
		
		this.readEmployeeData(); // ��ȡ���ݿ⵽���
		jtMedicine.setModel(medicineModel);
		jtMedicine.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(3).setPreferredWidth(50);
		jtMedicine.getColumnModel().getColumn(4).setPreferredWidth(30);
		jtMedicine.getColumnModel().getColumn(5).setPreferredWidth(80);
		jtMedicine.getColumnModel().getColumn(9).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(11).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(12).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(13).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(14).setPreferredWidth(40);
		jtMedicine.getColumnModel().getColumn(15).setPreferredWidth(40);
		jspTable = new JScrollPane(jtMedicine);
		jspTable.setBounds(15, 40, 1050, 200);

		jbAdd.setBounds(15, 250, 80, 40);
		jbAdd.setFont(new Font("����", Font.BOLD, 20));
		jbAdd.addActionListener(this);

		jbQuery.setBounds(15, 300, 150, 40);
		jbQuery.setFont(new Font("����", Font.BOLD, 20));
		jbQuery.addActionListener(this);
		
		jbDelivery.setBounds(610, 300, 200, 40);
		jbDelivery.setFont(new Font("����", Font.BOLD, 15));
		jbDelivery.addActionListener(this);

		DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<Object>();
		comboBoxModel.addElement("ҩƷid");
		comboBoxModel.addElement("Ʒ��");
		comboBoxModel.addElement("����*");
		comboBox = new JComboBox<Object>(comboBoxModel);
		comboBox.setBounds(170, 300, 80, 40);
		comboBox.setFont(new Font("����", Font.BOLD, 15));

		jtfQueryCond.setFont(new Font("����", Font.BOLD, 18));
		jtfQueryCond.setBounds(260, 300, 100, 40);
		// ��һ����
		DefaultComboBoxModel<Object> comboBoxYearModel1 = new DefaultComboBoxModel<Object>();
		for (int i = 1; i <= 12; i++) {
			comboBoxYearModel1.addElement(String.valueOf(i));
		}
		comboBoxYear1 = new JComboBox<Object>(comboBoxYearModel1);
		comboBoxYear1.setBounds(370, 300, 80, 20);
		comboBoxYear1.setFont(new Font("����", Font.BOLD, 15));

		DefaultComboBoxModel<Object> comboBoxYearModel2 = new DefaultComboBoxModel<Object>();
		for (int i = 1; i <= 12; i++) {
			comboBoxYearModel2.addElement(String.valueOf(i));
		}
		comboBoxYear2 = new JComboBox<Object>(comboBoxYearModel2);
		comboBoxYear2.setBounds(490, 300, 80, 20);
		comboBoxYear2.setFont(new Font("����", Font.BOLD, 15));
		jlMonth.setBounds(580, 285, 50, 50);
		jlTo.setBounds(460, 300, 50, 50);
		jlTo.setFont(new Font("����", Font.BOLD, 20));
		// �ڶ�����
		DefaultComboBoxModel<Object> comboBoxDayModel1 = new DefaultComboBoxModel<Object>();
		for (int i = 1; i <= 31; i++) {
			comboBoxDayModel1.addElement(String.valueOf(i));
		}
		comboBoxDay1 = new JComboBox<Object>(comboBoxDayModel1);
		comboBoxDay1.setBounds(370, 330, 80, 20);
		comboBoxDay1.setFont(new Font("����", Font.BOLD, 15));
		
		DefaultComboBoxModel<Object> comboBoxDayModel2 = new DefaultComboBoxModel<Object>();
		for (int i = 1; i <= 31; i++) {
			comboBoxDayModel2.addElement(String.valueOf(i));
		}
		comboBoxDay2 = new JComboBox<Object>(comboBoxDayModel2);
		comboBoxDay2.setBounds(490, 330, 80, 20);
		comboBoxDay2.setFont(new Font("����", Font.BOLD, 15));
		jlDay.setBounds(580, 315, 50, 50);

		jlNoResult.setBounds(900, 300, 200, 50);
		jlNoResult.setFont(new Font("����", Font.BOLD, 20));
		jlNoResult.setVisible(false);

		jspTable2 = new JScrollPane(jtQueryResult);
		jspTable2.setBounds(15, 350, 1050, 100);
		jspTable2.setVisible(false);

		Vector<Object> rowData = new Vector<>();
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		vtInsertColumn.add(rowData);
		insertModel = new DefaultTableModel(vtInsertColumn, tableHeader);
		jtAddMedicine = new JTable(insertModel);
		jtAddMedicine.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtAddMedicine.getColumnModel().getColumn(3).setPreferredWidth(50);
		jtAddMedicine.getColumnModel().getColumn(4).setPreferredWidth(30);
		jtAddMedicine.getColumnModel().getColumn(5).setPreferredWidth(80);
		jtAddMedicine.getColumnModel().getColumn(9).setPreferredWidth(50);
		jtAddMedicine.getColumnModel().getColumn(11).setPreferredWidth(50);
		jtAddMedicine.getColumnModel().getColumn(12).setPreferredWidth(40);
		jtAddMedicine.getColumnModel().getColumn(13).setPreferredWidth(40);
		jtAddMedicine.getColumnModel().getColumn(14).setPreferredWidth(40);
		jtAddMedicine.getColumnModel().getColumn(15).setPreferredWidth(40);

		jspTable3 = new JScrollPane(jtAddMedicine);
		jspTable3.setBounds(100, 250, 965, 40);
		
		jspQueryPrice = new JScrollPane(jtQueryPrice);
		jspQueryPrice.setBounds(15, 350, 1050, 100);
		jspQueryPrice.setVisible(false);
		
		jspDelivery = new JScrollPane(jtDelivery);
		jspDelivery.setBounds(15, 350, 1050, 100);
		jspDelivery.setVisible(false);

		jpMain.add(jbAdd);
		jpMain.add(jbQuery);
		jpMain.add(jbDelivery);
		jpMain.add(jlMedicineInfo);
		jpMain.add(jlNoResult);
		jpMain.add(jlDay);
		jpMain.add(jlMonth);
		jpMain.add(jlTo);
		jpMain.add(jspTable);
		jpMain.add(jspTable2);
		jpMain.add(jspTable3);
		jpMain.add(jspQueryPrice);
		jpMain.add(jspDelivery);
		jpMain.add(comboBox);
		jpMain.add(comboBoxDay1);
		jpMain.add(comboBoxYear1);
		jpMain.add(comboBoxDay2);
		jpMain.add(comboBoxYear2);
		jpMain.add(jtfQueryCond);

		this.add(jpMain);
	}

	public void readEmployeeData() { // ��ȡ���ݵ����
		DefaultTableModel model = (DefaultTableModel) jtMedicine.getModel();
		int row_count = model.getRowCount();
		for (int i = row_count - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		try {
			Statement stmt = this.myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(this.querySqlAll);
			while (rs.next()) {
				Vector<Object> rowData = new Vector<>();
				rowData.add(rs.getString("m_id"));
				rowData.add(rs.getString("m_name"));
				rowData.add(rs.getString("type"));
				rowData.add(rs.getString("specification"));
				rowData.add(rs.getString("unit"));
				rowData.add(rs.getString("manufacturer"));
				rowData.add(rs.getString("approval_number"));
				rowData.add(rs.getString("stock_date"));
				rowData.add(rs.getString("expiry_date"));
				rowData.add(rs.getString("s_id"));
				rowData.add(rs.getString("batch_number"));
				rowData.add(rs.getString("recorder_id"));
				rowData.add(rs.getString("purchase_amount"));
				rowData.add(rs.getString("trade_price"));
				rowData.add(rs.getString("purchase_price"));
				rowData.add(rs.getString("retail_price"));
				vtMedicineData.add(rowData); // ����һ����¼
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.medicineModel = new DefaultTableModel(this.vtMedicineData, this.tableHeader) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false; // ���ò��ɱ༭
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbAdd) {  // ���
			String queryString = "INSERT INTO medicine " + 
		                         "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, %s, %s, %s)";
			String[] insertStrings = { "9999", "������ҩ", "����", "1g/Ƭ", "g", "������ҩ��˾", "Z99999999", "2099-01-01", "2099-01-01", "9999", "999999-99", "9999", "9999", "999.99", "999.99", "999.99"};
			for (int i = 0; i < 16; i++) {
				insertStrings[i] = this.jtAddMedicine.getValueAt(0, i).toString();
			}
			if (this.jtAddMedicine.getValueAt(0, 0) == "") {
				queryString = String.format(queryString, "9999", "������ҩ", "����", "1g/Ƭ", "g", "������ҩ��˾", "Z99999999", "2099-01-01", "2099-01-01", "9999", "999999-99", "9999", "9999", "999.99", "999.99", "999.99");
			} else {
				queryString = String.format(queryString, insertStrings[0], insertStrings[1], insertStrings[2],
						insertStrings[3], insertStrings[4], insertStrings[5], insertStrings[6], insertStrings[7]);
			}
			try {
				Statement stmt = this.myConnection.createStatement();
				stmt.executeUpdate(queryString);
				stmt.close();
				JOptionPane.showMessageDialog(null, "����ɹ� ^_^  �����������ݿ⣡");
				this.readEmployeeData();
//				this.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "�����¼ʧ��! x_x\nInfo: " + e1.getMessage(), "��������",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == jbQuery) { // ��ѯ
			String queryString = "SELECT * FROM medicine WHERE %s='%s'";
			String queryString2 = "SELECT DISTINCT medicine.stock_date, medicine.m_id, medicine.m_name, medicine.purchase_price, supplier.s_name, supplier.tel\r\n" + 
					              "FROM medicine, supplier\r\n" + 
					              "WHERE supplier.s_id = medicine.s_id AND medicine.stock_date >= '2018-%s-%s' AND medicine.stock_date <= '2018-%s-%s'";
			String queryCondString = comboBox.getSelectedItem().toString();
			int c = 0;
			String signString = "";
			
			// ��ձ��
			DefaultTableModel model = (DefaultTableModel) jtQueryResult.getModel();
			int row_count = model.getRowCount();
			for (int i = row_count - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			DefaultTableModel model1 = (DefaultTableModel) jtQueryPrice.getModel();
			int row_count1 = model1.getRowCount();
			for (int i = row_count1 - 1; i >= 0; i--) {
				model1.removeRow(i);
			}

			if (queryCondString == "ҩƷid") {
				queryString = String.format(queryString, "m_id", jtfQueryCond.getText());
				signString = "1";
			} else if (queryCondString == "Ʒ��") {
				queryString = String.format(queryString, "m_name", jtfQueryCond.getText());
				signString = "1";
			} else if (queryCondString == "����*") {
				queryString = String.format(queryString2, comboBoxYear1.getSelectedItem().toString(), comboBoxDay1.getSelectedItem().toString(),
						comboBoxYear2.getSelectedItem().toString(), comboBoxDay2.getSelectedItem().toString());
				signString = "2";
			}
			
			
			if (signString == "1") {// ����ѯ����
				try {
					Statement stmt = this.myConnection.createStatement();
					ResultSet rs = stmt.executeQuery(queryString);
					while (rs.next()) {
						c++;
						Vector<Object> rowData = new Vector<>();
						rowData.add(rs.getString("m_id"));
						rowData.add(rs.getString("m_name"));
						rowData.add(rs.getString("type"));
						rowData.add(rs.getString("specification"));
						rowData.add(rs.getString("unit"));
						rowData.add(rs.getString("manufacturer"));
						rowData.add(rs.getString("approval_number"));
						rowData.add(rs.getString("stock_date"));
						rowData.add(rs.getString("expiry_date"));
						rowData.add(rs.getString("s_id"));
						rowData.add(rs.getString("batch_number"));
						rowData.add(rs.getString("recorder_id"));
						rowData.add(rs.getString("purchase_amount"));
						rowData.add(rs.getString("trade_price"));
						rowData.add(rs.getObject("purchase_price"));
						rowData.add(rs.getDouble("retail_price"));
						vtQueryResult.add(rowData);
					}
					rs.close();
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else {  // ��ѯ����
				try {
					Statement stmt = this.myConnection.createStatement();
					ResultSet rs = stmt.executeQuery(queryString);
					while (rs.next()) {
						c++;
						Vector<Object> rowData = new Vector<>();
						rowData.add(rs.getString("stock_date"));
						rowData.add(rs.getString("m_id"));
						rowData.add(rs.getString("m_name"));
						rowData.add(rs.getString("purchase_price"));
						rowData.add(rs.getString("s_name"));
						rowData.add(rs.getString("tel"));
						vtPrice.add(rowData);
					}
					rs.close();
					stmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			
			if (c == 0) { // ��ѯ�޹�
				this.jlNoResult.setVisible(true);
				this.jspTable2.setVisible(false);  // ��ͨ��ѯ��
				this.jspQueryPrice.setVisible(false);  // ���۲�ѯ��
				this.jspDelivery.setVisible(false); // ����
				this.setBounds(300, 200, 1100, 400);
			} else {
				this.jlNoResult.setVisible(false);
				if (signString == "1") {  // ����ѯ����
					this.jspTable2.setVisible(true);
					this.jspQueryPrice.setVisible(false);  // ���۲�ѯ��
					this.jspDelivery.setVisible(false); // ����
					this.resultModel = new DefaultTableModel(this.vtQueryResult, this.tableHeader) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column) {
							return false; // ���ò��ɱ༭
						}
					};
					this.jtQueryResult.setModel(this.resultModel);
					jtQueryResult.getColumnModel().getColumn(0).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(3).setPreferredWidth(50);
					jtQueryResult.getColumnModel().getColumn(4).setPreferredWidth(30);
					jtQueryResult.getColumnModel().getColumn(5).setPreferredWidth(80);
					jtQueryResult.getColumnModel().getColumn(9).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(11).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(12).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(13).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(14).setPreferredWidth(40);
					jtQueryResult.getColumnModel().getColumn(15).setPreferredWidth(40);
					this.jtQueryResult.updateUI();
				}else {  // ��ѯ����
					this.jspQueryPrice.setVisible(true);
					this.jspTable2.setVisible(false);  // ��ͨ��ѯ��
					this.jspDelivery.setVisible(false); // ����
					this.priceModel = new DefaultTableModel(this.vtPrice, this.priceHeader) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column) {
							return false; // ���ò��ɱ༭
						}
					};
					this.jtQueryPrice.setModel(this.priceModel);
					this.jtQueryPrice.updateUI();
				}
				this.setBounds(300, 200, 1100, 500);
			}
		}else if (e.getSource()==jbDelivery) {  // ��ѯ�����¼
			String queryString = "SELECT *\r\n" + 
		              "FROM delivery_log\r\n" + 
		              "WHERE delivery_date >= '2019-%s-%s' AND delivery_date <= '2019-%s-%s'";
			queryString = String.format(queryString, comboBoxYear1.getSelectedItem().toString(), comboBoxDay1.getSelectedItem().toString(),
					comboBoxYear2.getSelectedItem().toString(), comboBoxDay2.getSelectedItem().toString());
			int c = 0;
			
			// ��ձ��
			DefaultTableModel model = (DefaultTableModel) jtDelivery.getModel();
			int row_count = model.getRowCount();
			for (int i = row_count - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			
			try {
				Statement stmt = this.myConnection.createStatement();
				ResultSet rs = stmt.executeQuery(queryString);
				while (rs.next()) {
					c++;
					Vector<Object> rowData = new Vector<>();
					rowData.add(rs.getString("delivery_id"));
					rowData.add(rs.getString("recorder_id"));
					rowData.add(rs.getString("collector_id"));
					rowData.add(rs.getString("delivery_date"));
					rowData.add(rs.getString("m_id"));
					rowData.add(rs.getString("delivery_amount"));
					vtDelivery.add(rowData);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			if (c == 0) {
				this.jlNoResult.setVisible(true);
				this.jspTable2.setVisible(false);  // ��ͨ��ѯ��
				this.jspQueryPrice.setVisible(false);  // ���۲�ѯ��
				this.jspDelivery.setVisible(false); // 
				this.setBounds(300, 200, 1100, 400);
			}else {
				this.jspDelivery.setVisible(true);
				this.jspTable2.setVisible(false);  // ��ͨ��
				this.jspQueryPrice.setVisible(false);  // ���۲�ѯ��
				this.deliveryModel = new DefaultTableModel(this.vtDelivery, this.deliveryHeader) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
						return false; // ���ò��ɱ༭
					}
				};
				this.jtDelivery.setModel(this.deliveryModel);
				this.jtDelivery.updateUI();
				this.setBounds(300, 200, 1100, 500);
			}
		}
	}
}
