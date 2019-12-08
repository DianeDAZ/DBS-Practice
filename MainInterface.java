package database_homework;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainInterface extends JFrame implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Connection myConnection;  // ����
	final String MY_DB_URL = "jdbc:mysql://localhost:3306/pharmacy_ms?useSSL=false";  // ҩ�����ݿ��ַ
	final ImageIcon arrowIcon = new ImageIcon(MainInterface.class.getResource("arrow1.png"));
	final ImageIcon arrowIcon2 = new ImageIcon(MainInterface.class.getResource("arrow2.png"));
	final ImageIcon loadingIcon = new ImageIcon(MainInterface.class.getResource("loading.gif"));

	JLabel jlSystemName = new JLabel("��ҩ�����ϵͳ��");
	JLabel[] jlImg = new JLabel[5];
	JLabel[] jlImgRight = new JLabel[5];
//	JLabel jlRight = new JLabel();
	JLabel jlLoading = new JLabel();
	
	JButton jbEmployeeInfo = new JButton("1.Ա����Ϣ");
	JButton jbMedicineInfo = new JButton("2.���ҩƷ��Ϣ");
	JButton jbCounterInfo = new JButton("3.��̨ҩƷ��Ϣ");
	JButton jbSaleInfo = new JButton("4.������Ϣ");
	JButton jbSupplierInfo = new JButton("5.��������Ϣ");
	JButton jbInitDatabase = new JButton("> Start <");
	
	JPanel jpMainPanel = new JPanel();
	
	Employee myEmployee;  // Ա����Ϣ����
	Medicine myMedicine;  // ҩƷ�����Ϣ
	Counter myCounter;    // ��̨ҩƷ��Ϣ
	Sale mySale;          // ���ۼ�¼��Ϣ
	Supplier mySupplier;  // ��Ӧ����Ϣ
	
	public MainInterface() {
		this.setVisible(true);
		this.setBounds(600, 200, 600, 700);
		this.setResizable(false);
		this.setTitle("ҩ�����ϵͳ");
		jpMainPanel.setLayout(null);
		jpMainPanel.setBackground(new Color(255, 255, 255));
		
		for (int i = 0; i < jlImg.length; i++) {
			jlImg[i] = new JLabel();
			jlImg[i].setIcon(arrowIcon);
			jlImg[i].setFont(new Font("����", Font.BOLD, 20));
			jlImg[i].setBounds(50, 90+i*100, 208, 103);
			jlImg[i].setVisible(false);
			
			jlImgRight[i] = new JLabel();
			jlImgRight[i].setIcon(arrowIcon2);
			jlImgRight[i].setFont(new Font("����", Font.BOLD, 20));
			jlImgRight[i].setBounds(455, 90+i*100, 208, 103);
			jlImgRight[i].setVisible(false);
		}
//		jlRight.setIcon(arrowIcon2);
//		jlRight.setBounds(170, 575, 208, 103);
//		jlRight.setVisible(false);
		jlLoading.setIcon(loadingIcon);
		jlLoading.setBounds(195, 575, 228, 103);
		jlLoading.setVisible(false);
		
		jlSystemName.setFont(new Font("����", Font.BOLD, 35));
		jlSystemName.setBounds(155, 10, 340, 50);
		jpMainPanel.add(jlSystemName);
		
		jbEmployeeInfo.setFont(new Font("����", Font.PLAIN, 20));
		jbEmployeeInfo.setBounds(150, 100, 300, 75);
		jbEmployeeInfo.setEnabled(false);
//		jbEmployeeInfo.setHorizontalAlignment(SwingConstants.LEFT);
		jbEmployeeInfo.addActionListener(this);
		jbEmployeeInfo.addMouseListener(this);
		
		jbMedicineInfo.setFont(new Font("����", Font.PLAIN, 20));
		jbMedicineInfo.setBounds(150, 200, 300, 75);
		jbMedicineInfo.setEnabled(false);
		jbMedicineInfo.addActionListener(this);
		jbMedicineInfo.addMouseListener(this);
		
		jbCounterInfo.setFont(new Font("����", Font.PLAIN, 20));
		jbCounterInfo.setBounds(150, 300, 300, 75);
		jbCounterInfo.setEnabled(false);
		jbCounterInfo.addActionListener(this);
		jbCounterInfo.addMouseListener(this);
		
		jbSaleInfo.setFont(new Font("����", Font.PLAIN, 20));
		jbSaleInfo.setBounds(150, 400, 300, 75);
		jbSaleInfo.setEnabled(false);
		jbSaleInfo.addActionListener(this);
		jbSaleInfo.addMouseListener(this);
		
		jbSupplierInfo.setFont(new Font("����", Font.PLAIN, 20));
		jbSupplierInfo.setBounds(150, 500, 300, 75);
		jbSupplierInfo.setEnabled(false);
		jbSupplierInfo.addActionListener(this);
		jbSupplierInfo.addMouseListener(this);
		
		jbInitDatabase.setFont(new Font("����", Font.PLAIN, 20));
		jbInitDatabase.setBounds(40, 600, 130, 50);
//		jbInitDatabase.setBackground(new Color(220,20,60)); // ��ɫ
		jbInitDatabase.setBackground(new Color(127,255,170));
		jbInitDatabase.addActionListener(this);
		jbInitDatabase.addMouseListener(this);
		
		for (int i = 0; i < jlImg.length; i++) {
			jpMainPanel.add(jlImg[i]);
			jpMainPanel.add(jlImgRight[i]);
		}
//		jpMainPanel.add(jlRight);
		jpMainPanel.add(jlLoading);
		jpMainPanel.add(jbInitDatabase);
		jpMainPanel.add(jbEmployeeInfo);
		jpMainPanel.add(jbMedicineInfo);
		jpMainPanel.add(jbCounterInfo);
		jpMainPanel.add(jbSaleInfo);
		jpMainPanel.add(jbSupplierInfo);
		this.add(jpMainPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==jbInitDatabase) {
			this.jbEmployeeInfo.setEnabled(true);
			this.jbMedicineInfo.setEnabled(true);
			this.jbCounterInfo.setEnabled(true);
			this.jbSaleInfo.setEnabled(true);
			this.jbSupplierInfo.setEnabled(true);
//			this.jbInitDatabase.setBackground(new Color(127,255,170)); ��ɫ
			this.jbInitDatabase.setBackground(new Color(255,255,255)); // ��ɫ
			this.jbInitDatabase.setText("Running");
			this.jbInitDatabase.setFont(new Font("����", Font.ITALIC, 20));
			this.jbInitDatabase.setBorderPainted(false);
			this.connectDatabase();  // �������ݿ�
			this.initDatabase();  // ��ʼ�����ݿ�
			jbInitDatabase.setEnabled(false);
			jlLoading.setVisible(true);
		}else if (e.getSource()==jbEmployeeInfo) {  // Ա����Ϣ���ݿ�
			this.myEmployee = new Employee(this.myConnection);
		}else if (e.getSource()==jbMedicineInfo) {  // ҩƷ������ݿ� 
			this.myMedicine = new Medicine(this.myConnection);
		}else if (e.getSource()==jbCounterInfo) {  // ��̨ҩƷ���ݿ�
			this.myCounter = new Counter(this.myConnection);
		}else if (e.getSource()==jbSaleInfo) {  // ���ۼ�¼���ݿ�
			this.mySale = new Sale(this.myConnection);
		}else if (e.getSource()==jbSupplierInfo) {  // ���������ݿ�
			this.mySupplier = new Supplier(this.myConnection);
		}
	}
	
	public void initDatabase() {		
			String deleteString = "DELETE FROM %s WHERE %s = '%s'";
			deleteMyRow(deleteString, "employee", "e_id", "9999");
			deleteMyRow(deleteString, "medicine", "m_id", "9999");
			deleteMyRow(deleteString, "counter_log", "counter_log_id", "99999999");
			deleteMyRow(deleteString, "sale_log", "sale_id", "99999999");	
			deleteMyRow(deleteString, "supplier", "s_id", "9999");
	}
	
	public void deleteMyRow(String deleteString, String name, String key, String value) {
		try {
			deleteString = String.format(deleteString, name, key, value);
			Statement stmt = this.myConnection.createStatement();
			stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
			stmt.executeUpdate(deleteString);
			stmt.execute("SET FOREIGN_KEY_CHECKS =1");
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connectDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.myConnection = DriverManager.getConnection(this.MY_DB_URL, "root", "root");
		} catch (Exception e) {
			System.out.println("�������ݿ�ʱ��������");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainInterface mainInterface = new MainInterface();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==jbEmployeeInfo) {
			showArrowLable(0);
		}else if (e.getSource()==jbMedicineInfo) {
			showArrowLable(1);
		}else if (e.getSource()==jbCounterInfo) {
			showArrowLable(2);
		}else if (e.getSource()==jbSaleInfo) {
			showArrowLable(3);
		}else if (e.getSource()==jbSupplierInfo) {
			showArrowLable(4);
		}else if (e.getSource()==jbInitDatabase) {
//			this.jlRight.setVisible(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==jbEmployeeInfo) {
			hideArrowLable(0);
		}else if (e.getSource()==jbMedicineInfo) {
			hideArrowLable(1);
		}else if (e.getSource()==jbCounterInfo) {
			hideArrowLable(2);
		}else if (e.getSource()==jbSaleInfo) {
			hideArrowLable(3);
		}else if (e.getSource()==jbSupplierInfo) {
			hideArrowLable(4);
		}else if (e.getSource()==jbInitDatabase) {
//			this.jlRight.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void showArrowLable(int seq) {
		this.jlImg[seq].setVisible(true);
		this.jlImgRight[seq].setVisible(true);
	}
	
	public void hideArrowLable(int seq) {
		this.jlImg[seq].setVisible(false);
		this.jlImgRight[seq].setVisible(false);
	}
}
