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

public class Supplier extends JFrame implements ActionListener {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	int queryNums = -1;

	Connection myConnection = null; // ����
	String querySqlAll = "SELECT * FROM Supplier";

	JLabel jlSupplierInfo = new JLabel("��������Ϣ");
	JLabel jlNoResult = new JLabel("��ѯ�޹� !");

	JTable jtSupplier = new JTable();
	JTable jtQueryResult = new JTable();
	JTable jtAddSupplier = new JTable();

	DefaultTableModel supplierModel;
	DefaultTableModel resultModel;
	DefaultTableModel insertModel;

	JButton jbAdd = new JButton("���");
	JButton jbQuery = new JButton("��ѯ");
	JButton jbDelete = new JButton("xɾ��");
	JButton jbModify = new JButton("*�޸�");
	JComboBox<Object> comboBox;
	JTextField jtfQueryCond = new JTextField();
	JPanel jpMain = new JPanel();
	JScrollPane jspTable;
	JScrollPane jspTable2;
	JScrollPane jspTable3;

	Vector<Vector<Object>> vtSupplierData = new Vector<>();
	Vector<Vector<Object>> vtQueryResult = new Vector<>();
	Vector<Vector<Object>> vtInsertColumn = new Vector<>();
	Vector<Object> tableHeader = new Vector<>();

	public Supplier(Connection conn) {
		this.myConnection = conn; // ���ݿ�����
		this.setBounds(300, 200, 700, 400);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("��������Ϣ");
		jpMain.setLayout(null);

		jlSupplierInfo.setFont(new Font("����", Font.BOLD, 20));
		jlSupplierInfo.setBounds(290, 0, 150, 50);

		tableHeader.add("������id");
		tableHeader.add("ҩƷid");
		tableHeader.add("��������");
		tableHeader.add("��ϵ�绰");
		this.readEmployeeData(); // ��ȡ���ݿ⵽���
		jtSupplier.setModel(supplierModel);

		jspTable = new JScrollPane(jtSupplier);
		jspTable.setBounds(15, 40, 660, 200);

		jbAdd.setBounds(15, 250, 80, 40);
		jbAdd.setFont(new Font("����", Font.BOLD, 20));
		jbAdd.addActionListener(this);

		jbQuery.setBounds(15, 300, 150, 40);
		jbQuery.setFont(new Font("����", Font.BOLD, 20));
		jbQuery.addActionListener(this);

		DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<Object>();
		comboBoxModel.addElement("������id");
		comboBoxModel.addElement("ҩƷid");
		comboBoxModel.addElement("��������");
		comboBox = new JComboBox<Object>(comboBoxModel);
		comboBox.setBounds(170, 300, 100, 40);
		comboBox.setFont(new Font("����", Font.BOLD, 15));

		jtfQueryCond.setFont(new Font("����", Font.BOLD, 18));
		jtfQueryCond.setBounds(280, 300, 100, 40);
		
		jbModify.setBounds(450, 300, 100, 40);
		jbModify.setFont(new Font("����", Font.BOLD, 20));
		jbModify.addActionListener(this);
		jbDelete.setBounds(560, 300, 100, 40);
		jbDelete.setFont(new Font("����", Font.BOLD, 20));
		jbDelete.addActionListener(this);
		
		jlNoResult.setBounds(370, 300, 200, 50);
		jlNoResult.setFont(new Font("����", Font.BOLD, 20));
		jlNoResult.setVisible(false);

		jspTable2 = new JScrollPane(jtQueryResult);
		jspTable2.setBounds(15, 350, 660, 100);
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
		vtInsertColumn.add(rowData);
		insertModel = new DefaultTableModel(vtInsertColumn, tableHeader);
		jtAddSupplier = new JTable(insertModel);

		jspTable3 = new JScrollPane(jtAddSupplier);
		jspTable3.setBounds(100, 250, 580, 40);

		jpMain.add(jbAdd);
		jpMain.add(jbQuery);
		jpMain.add(jbModify);
		jpMain.add(jbDelete);
		jpMain.add(jlSupplierInfo);
		jpMain.add(jlNoResult);
		jpMain.add(jspTable);
		jpMain.add(jspTable2);
		jpMain.add(jspTable3);
		jpMain.add(comboBox);
		jpMain.add(jtfQueryCond);

		this.add(jpMain);
	}

	public void readEmployeeData() { // ��ȡ���ݵ����
		DefaultTableModel model = (DefaultTableModel) jtSupplier.getModel();
		int row_count = model.getRowCount();
		for (int i = row_count - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		try {
			Statement stmt = this.myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(this.querySqlAll);
			while (rs.next()) {
				Vector<Object> rowData = new Vector<>();
				rowData.add(rs.getString("s_id"));
				rowData.add(rs.getString("m_id"));
				rowData.add(rs.getString("s_name"));
				rowData.add(rs.getString("tel"));
				vtSupplierData.add(rowData); // ����һ����¼
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.supplierModel = new DefaultTableModel(this.vtSupplierData, this.tableHeader) {
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
		if (e.getSource() == jbAdd) {
			String queryString = "INSERT INTO supplier " + "VALUES ('%s', '%s', '%s', '%s')";
			String[] insertStrings = { "9999", "1001", "���ܹ�˾", "99999999999" };
			for (int i = 0; i < 4; i++) {
				insertStrings[i] = this.jtAddSupplier.getValueAt(0, i).toString();
			}
			if (this.jtAddSupplier.getValueAt(0, 0) == "") {
				queryString = String.format(queryString, "9999", "1001", "���ܹ�˾", "99999999999");
			} else {
				queryString = String.format(queryString, insertStrings[0], insertStrings[1], insertStrings[2],
						insertStrings[3]);
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
			String queryString = "SELECT * FROM supplier WHERE %s='%s'";
			String queryCondString = comboBox.getSelectedItem().toString();
			// ��ձ��
			DefaultTableModel model = (DefaultTableModel) jtQueryResult.getModel();
			int row_count = model.getRowCount();
			for (int i = row_count - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			if (queryCondString == "������id") {
				queryString = String.format(queryString, "s_id", jtfQueryCond.getText());
				queryNums = executeSQL(queryString);
			} else if (queryCondString == "ҩƷid") {
				queryString = String.format(queryString, "m_id", jtfQueryCond.getText());
				queryNums = executeSQL(queryString);
			} else if (queryCondString == "��������") {
				queryString = String.format(queryString, "s_name", jtfQueryCond.getText());
				queryNums = executeSQL(queryString);
			}
		}else if (e.getSource()==jbDelete) {  // ɾ��
			if (queryNums == 1) {
				int selectRowId = jtQueryResult.getSelectedRow();
				if (selectRowId != -1) {  // ѡ����
					String deleteString = "DELETE FROM supplier WHERE s_id = '%s'";
					deleteString = String.format(deleteString, jtQueryResult.getValueAt(selectRowId, 0));
					try {
						Statement stmt = this.myConnection.createStatement();
						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
						stmt.executeUpdate(deleteString);
						stmt.execute("SET FOREIGN_KEY_CHECKS =1");
						stmt.close();
						JOptionPane.showMessageDialog(null, "ɾ���ɹ� ^_^  �����������ݿ⣡");
						this.readEmployeeData();
//						this.dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��! x_x\nInfo: " + e1.getMessage(), "��������",
								JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "û��ѡ�е��� -_-��", null, JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "û�в�ѯ��� -_-��", null, JOptionPane.WARNING_MESSAGE);
			}
		}else if (e.getSource()==jbModify) {  // �޸�
			if (queryNums == 1) {
				int selectRowId = jtQueryResult.getSelectedRow();
				if (selectRowId != -1) {  // ѡ������
					String modifyString = "UPDATE supplier SET %s='%s'\r\n" + 
							"WHERE s_id='%s' AND m_id = '%s'";
					int selectColId = jtQueryResult.getSelectedColumn();
					String[] headerNameStrings = {"s_id","m_id","s_name", "tel"};
					if (selectColId >= 2) {
						String inputValue = JOptionPane.showInputDialog("���������µ�����");
						modifyString = String.format(modifyString, headerNameStrings[selectColId], inputValue,
								jtQueryResult.getValueAt(selectRowId, 0), jtQueryResult.getValueAt(selectRowId, 1));
						try {
							Statement stmt = this.myConnection.createStatement();
							stmt.executeUpdate(modifyString);
							stmt.close();
							JOptionPane.showMessageDialog(null, "�޸ĳɹ� ^_^  �����������ݿ⣡");
							this.readEmployeeData();
//							this.dispose();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "�޸�ʧ��! x_x\nInfo: " + e2.getMessage(), "��������",
									JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "��ֹ�޸Ĵ��У����Լ����������ϵ����Ա��", null, JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "û��ѡ�е��� -_-��", null, JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "û�в�ѯ��� -_-��", null, JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public int executeSQL(String sqlString) {
		int c = 0;
		try {
			Statement stmt = this.myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
				c++;
				Vector<Object> rowData = new Vector<>();
				rowData.add(rs.getString("s_id"));
				rowData.add(rs.getString("m_id"));
				rowData.add(rs.getString("s_name"));
				rowData.add(rs.getString("tel"));
				this.vtQueryResult.add(rowData);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (c == 0) { // ��ѯ�޹�
			this.jspTable2.setVisible(false);
			this.setBounds(300, 200, 700, 400);
			JOptionPane.showMessageDialog(null, "oh no!   ��ѯ�޹� ...");
			return -1;
		} else {
			this.jspTable2.setVisible(true);

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
			this.jtQueryResult.updateUI();
			this.setBounds(300, 200, 700, 500);
			return 1;
		}
	}
}
