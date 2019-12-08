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


public class Employee extends JFrame implements ActionListener
{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	Connection myConnection = null;  // ����
	String querySqlAll = "SELECT * FROM employee";
	
	JLabel jlEmployeeInfo = new JLabel("Ա����Ϣ");
	JLabel jlNoResult = new JLabel("��ѯ�޹� !");
	
    JTable jtEmployee = new JTable();
    JTable jtQueryResult = new JTable();
    JTable jtAddEmployee = new JTable();
    
    DefaultTableModel employeeModel;
    DefaultTableModel resultModel;
    DefaultTableModel insertModel;
    
    JButton jbAdd = new JButton("���");
    JButton jbQuery = new JButton("��ѯ");
    JComboBox<Object> comboBox;
    JTextField jtfQueryCond = new JTextField();
    JPanel jpMain = new JPanel();
    JScrollPane jspTable;
    JScrollPane jspTable2;
    JScrollPane jspTable3;
    
    Vector<Vector<Object>> vtEmployeeData = new Vector<>();
    Vector<Vector<Object>> vtQueryResult = new Vector<>();
    Vector<Vector<Object>> vtInsertColumn = new Vector<>();
    Vector<Object> tableHeader = new Vector<>();

    
    public Employee(Connection conn)
    {
    	this.myConnection = conn;  // ���ݿ�����
        this.setBounds(300, 200, 700, 400);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Ա����Ϣ");
        jpMain.setLayout(null);
        
        jlEmployeeInfo.setFont(new Font("����", Font.BOLD, 20));
        jlEmployeeInfo.setBounds(290, 0, 150, 50);

        tableHeader.add("Ա��id");
        tableHeader.add("����");
        tableHeader.add("����");
        tableHeader.add("�Ա�");
        tableHeader.add("ѧ��");
        tableHeader.add("ְλ");
        tableHeader.add("���֤��");
        tableHeader.add("��ϵ�绰");
        this.readEmployeeData();  // ��ȡ���ݿ⵽���
        jtEmployee.setModel(employeeModel);
        jtEmployee.getColumnModel().getColumn(0).setPreferredWidth(40);
        jtEmployee.getColumnModel().getColumn(1).setPreferredWidth(30);
        jtEmployee.getColumnModel().getColumn(2).setPreferredWidth(15);
        jtEmployee.getColumnModel().getColumn(3).setPreferredWidth(15);
        jtEmployee.getColumnModel().getColumn(4).setPreferredWidth(15);
        jtEmployee.getColumnModel().getColumn(6).setPreferredWidth(100);
        jspTable = new JScrollPane(jtEmployee);
        jspTable.setBounds(15, 40, 660, 200);
        
        jbAdd.setBounds(15, 250, 80, 40);
        jbAdd.setFont(new Font("����", Font.BOLD, 20));
        jbAdd.addActionListener(this);
        
        jbQuery.setBounds(15, 300, 150, 40);
        jbQuery.setFont(new Font("����", Font.BOLD, 20));
        jbQuery.addActionListener(this);
        
        DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<Object>();
        comboBoxModel.addElement("Ա��id");
        comboBoxModel.addElement("����");
        comboBox = new JComboBox<Object>(comboBoxModel);
        comboBox.setBounds(170, 300, 80, 40);
        comboBox.setFont(new Font("����", Font.BOLD, 15));
        
        jtfQueryCond.setFont(new Font("����", Font.BOLD, 18));
        jtfQueryCond.setBounds(260, 300, 100, 40);
        
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
	    jtAddEmployee = new JTable(insertModel);
	    jtAddEmployee.getColumnModel().getColumn(0).setPreferredWidth(30);
	    jtAddEmployee.getColumnModel().getColumn(1).setPreferredWidth(30);
	    jtAddEmployee.getColumnModel().getColumn(2).setPreferredWidth(10);
	    jtAddEmployee.getColumnModel().getColumn(3).setPreferredWidth(10);
	    jtAddEmployee.getColumnModel().getColumn(4).setPreferredWidth(15);
	    jtAddEmployee.getColumnModel().getColumn(6).setPreferredWidth(115);
	    
	    jspTable3 = new JScrollPane(jtAddEmployee);
	    jspTable3.setBounds(100, 250, 580, 40);
        
        jpMain.add(jbAdd);
        jpMain.add(jbQuery);
        jpMain.add(jlEmployeeInfo);
        jpMain.add(jlNoResult);
        jpMain.add(jspTable);
        jpMain.add(jspTable2);
        jpMain.add(jspTable3);
        jpMain.add(comboBox);
        jpMain.add(jtfQueryCond);
        
        this.add(jpMain);
    }
    
    public void readEmployeeData() {  // ��ȡ���ݵ����
		DefaultTableModel model = (DefaultTableModel) jtEmployee.getModel();
		int row_count = model.getRowCount();
		for (int i = row_count - 1; i >= 0; i--) {
			model.removeRow(i);
		}
    	try {
    		Statement stmt = this.myConnection.createStatement();
    		ResultSet rs = stmt.executeQuery(this.querySqlAll);
    		while (rs.next()) {
    		    Vector<Object> rowData = new Vector<>();
				rowData.add(rs.getString("e_id"));
				rowData.add(rs.getString("e_name"));
				rowData.add(rs.getInt("age"));
				rowData.add(rs.getString("sex"));
				rowData.add(rs.getString("education"));
				rowData.add(rs.getString("position"));
				rowData.add(rs.getString("id_number"));
				rowData.add(rs.getString("tel"));
				vtEmployeeData.add(rowData);  // ����һ����¼
			}
    		rs.close();
    		stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.employeeModel = new DefaultTableModel(this.vtEmployeeData, this.tableHeader) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column)
            {
                return false;  // ���ò��ɱ༭
            }
    	};
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource()==jbAdd) {
			String queryString = "INSERT INTO employee " + 
					"VALUES ('%s', '%s', %s, '%s', '%s', '%s', '%s', '%s')";
		    String[] insertStrings = {"9999", "�����ϰ�", "99", "��", "����", "����Ա", "974918473628759403","13785987466"};
    		for (int i = 0; i < 8; i++) {
    			insertStrings[i] = this.jtAddEmployee.getValueAt(0, i).toString();
			}
    		if (this.jtAddEmployee.getValueAt(0, 0)=="") {
				queryString = String.format(queryString, "9999", "�����ϰ�", "99", "��", "����", "����Ա", "974918473628759403","13785987466");
			}else {
	    		queryString = String.format(queryString,
	    				insertStrings[0],
	    				insertStrings[1],
	    				insertStrings[2],
	    				insertStrings[3],
	    				insertStrings[4],
	    				insertStrings[5],
	    				insertStrings[6],
	    				insertStrings[7]);
			}
    		try {
				Statement stmt = this.myConnection.createStatement();
	    		stmt.executeUpdate(queryString);
	    		stmt.close();
	    		JOptionPane.showMessageDialog(null, "����ɹ� ^_^  �����������ݿ⣡");  
				this.readEmployeeData();
//	    		this.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, 
						"�����¼ʧ��! x_x\nInfo: "+	e1.getMessage(), 
						"����",JOptionPane.ERROR_MESSAGE);
			}
		}else if (e.getSource()==jbQuery) {   // ��ѯ
			String queryString = "SELECT * FROM employee WHERE %s='%s'";
			String queryCondString = comboBox.getSelectedItem().toString();
    		int c = 0;
    		
			DefaultTableModel model = (DefaultTableModel) jtQueryResult.getModel();
			int row_count = model.getRowCount();
			for (int i = row_count - 1; i >= 0; i--) {
				model.removeRow(i);
			}
    		
			if (queryCondString == "Ա��id") {
				queryString = String.format(queryString, "e_id", jtfQueryCond.getText());
			}else {
				queryString = String.format(queryString, "e_name", jtfQueryCond.getText());
			}
			try {
				Statement stmt = this.myConnection.createStatement();
	    		ResultSet rs = stmt.executeQuery(queryString);
	    		while (rs.next()) {
	    			c++;
	    		    Vector<Object> rowData = new Vector<>();
					rowData.add(rs.getString("e_id"));
					rowData.add(rs.getString("e_name"));
					rowData.add(rs.getInt("age"));
					rowData.add(rs.getString("sex"));
					rowData.add(rs.getString("education"));
					rowData.add(rs.getString("position"));
					rowData.add(rs.getString("id_number"));
					rowData.add(rs.getString("tel"));
					vtQueryResult.add(rowData);
				}
	    		rs.close();
	    		stmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		if (c == 0) {  // ��ѯ�޹�
				this.jlNoResult.setVisible(true);
				this.jspTable2.setVisible(false);
		        this.setBounds(300, 200, 700, 400);
			}else {
				this.jlNoResult.setVisible(false);
				this.jspTable2.setVisible(true);
		    	this.resultModel = new DefaultTableModel(this.vtQueryResult, this.tableHeader) {
		            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					public boolean isCellEditable(int row, int column)
		            {
		                return false;  // ���ò��ɱ༭
		            }
		    	};
		        this.jtQueryResult.setModel(this.resultModel);
		        this.jtQueryResult.getColumnModel().getColumn(0).setPreferredWidth(40);
		        this.jtQueryResult.getColumnModel().getColumn(1).setPreferredWidth(30);
		        this.jtQueryResult.getColumnModel().getColumn(2).setPreferredWidth(15);
		        this.jtQueryResult.getColumnModel().getColumn(3).setPreferredWidth(15);
		        this.jtQueryResult.getColumnModel().getColumn(4).setPreferredWidth(15);
		        this.jtQueryResult.getColumnModel().getColumn(6).setPreferredWidth(100);
		        this.jtQueryResult.updateUI();
		        this.setBounds(300, 200, 700, 500);
			}
		}
    }
}