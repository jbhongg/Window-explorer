////����������Ʈ 6�� Ž����
////
////2013041024 ������
////�ڵ庴�� SeekerŬ���� centerpane ���� ����� �Լ� �α��� ��� ���� Ŭ����&�޼ҵ�
////Ŭ���� : Seeker MyActionListener MyKeyAdapter MyMouseAdapter
////�޼ҵ� : Seeker() makeCenter() m11~33()
////makeroottable() maketable(File) sortfile(File[]) searchfilter(String)  deleteFile(File)
////Ŭ���� : User UserFrame MyUserActionListener
////�޼ҵ� : User() updateData() updateVector()
////UserFrame() makeuserCpane() makeuserSpane() 
////login() logout() userCreate() userUpdate() userDelete() userFrameClose()
////
////2015041077 ȫ����
////westpane menubar ����Ʈ�� ���� �Լ�
////Ŭ���� : MyTreeSelectionListener MyTreeWillExpandListener
////�޼ҵ� : makeWest() makeMenu()
////getPath(TreeExpansionEvent e) getPath(TreeSelectEvent e) treeadd(String) treedel(String)
////
////2017038006 ������
////eastpane northpane ���ã��, �̸����� ���� �Լ�
////Ŭ���� : PreviewImage PreviewText
////�޼ҵ� : makeEast() makeNorth() preView()
////addLike() delLike()  moveLocate() moveUp()
//
//import java.awt.*; 
//import java.awt.event.*;
//import java.io.*;
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.Date;
//import java.util.Properties;
//
//import javax.swing.*;
//import javax.swing.event.*;
//import javax.swing.border.*;
//import javax.swing.tree.*;
//import javax.swing.table.*;
//
//import java.sql.*;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//public class Seeker extends JFrame {
//	boolean sort = true;			//���� Ÿ�� (true�� �̸��� �˻�)
//	String reallocate = "";			//���� �����ִ� ���丮�� �ּҸ� ������ ����
//	String filter = "";				//�˻��� Ű����
//	JTextField locatetext = new JTextField(58);		//�ּ��Է��ʵ�
//	JPanel previewpane = new JPanel();				//�̸����� �г�
//	JTree tree;										//���� ����Ʈ��
//	DefaultMutableTreeNode root = new DefaultMutableTreeNode("����ǻ��");	//Ʈ���� ���� ��Ʈ
//	Vector likevector = new Vector();		//���� ���ã�⿡ ǥ�õǴ� �̸�����
//	Vector realvector = new Vector();		//���� ���ã���� ���� �ּҹ���
//	JList likelist = new JList(likevector);	//���� ���ã���� ����Ʈ
//	String colName[] = { "���ϸ�", "ũ��", "����", "������ ��¥" };			//table���� ��忡 �� ���
//	DefaultTableModel model = new DefaultTableModel(colName, 0) {	//table���� ��
//		public boolean isCellEditable(int row, int column) {		//���� �Ұ����ϰ� ����
//			return false;
//		}
//	};
//	JTable table = new JTable(model);		//�߾ӿ� �� table
//	
//	JPanel northpane = new JPanel();		//���ʿ� �� �г�
//	JScrollPane westpane = null;			//���ʿ� �� �г�
//	JPanel eastpane = new JPanel();			//�����ʿ� �� �г�
//	JPanel centerpane = new JPanel();		//��� �� �г�
//	
//	User NowUser = new User();				//���� �α��� ���� �����
//	
//	static final String id = "root";		//DB ����
//	static final String pass = "asdf";		//DB ��ȣ
//	//DB �ּ�
//	String url = "jdbc:mysql://localhost:3306/6team_Seeker?serverTimezone=UTC";
//	
//	//���Ǹ�����
//	//��ư �޴��������� ���ð� ������ �Լ��� ����
//	class MyActionListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			JButton b = new JButton();
//			JMenuItem m = new JMenuItem();
//			
//			if(e.getSource().getClass() == b.getClass())
//				b = (JButton)e.getSource();
//			else if(e.getSource().getClass() == m.getClass())
//				m = (JMenuItem)e.getSource();
//			
//			if(b.getActionCommand() == "�߰�") addLike();
//			else if(b.getActionCommand() == "����") delLike();
//			else if(b.getActionCommand() == "...") moveUp();
//			else if(b.getActionCommand() == "�̵�") moveLocate();
//			
//			if(m.getActionCommand() == "�� â ����") m11();
//			else if(m.getActionCommand() == "�����") m12();
//			else if(m.getActionCommand() == "�ݱ�") m13();
//			else if(m.getActionCommand() == "������") m21();
//			else if(m.getActionCommand() == "�̸��ٲٱ�") m22();
//			else if(m.getActionCommand() == "�̵�") m23();
//			else if(m.getActionCommand() == "����") m24();
//			else if(m.getActionCommand() == "�̸��� ����") m31();
//			else if(m.getActionCommand() == "�ֽż� ����") m32();
//			else if(m.getActionCommand() == "�˻�") m33();
//			else if(m.getActionCommand() == "�����ּ� ����") {
//				if(!NowUser.ID.equals("")) {
//					m41();
//				} else {
//					JOptionPane.showMessageDialog(null, "�α��� �� �ּ���", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//			else if(m.getActionCommand() == "���� ������") {
//				if(!NowUser.ID.equals("")) {
//					m42();
//				} else {
//					JOptionPane.showMessageDialog(null, "�α��� �� �ּ���", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}
//	}
//	
//	//Ʈ�����ø�����
//	//westpane�� Ʈ����带 �������� �� �ش� ��ġ�� �̵��ϵ��� ����
//	class MyTreeSelectionListener implements TreeSelectionListener {
//		public void valueChanged(TreeSelectionEvent e) {
//			try {
//				//��Ʈ��带 �������� ��
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("����ǻ��")) {
//					reallocate = "";		//������ġ �ʱ�ȭ
//					locatetext.setText("����ǻ��");	//�ּ�â�� "����ǻ��" ���
//					makeroottable();		//��Ʈ���̺�� �ʱ�ȭ
//				}
//				else {	//��Ʈ��尡 �ƴ� �ٸ� ��带 �������� ���
//					File file = new File(getPath(e));	//���õ� ����� �ּҷ� file ����
//					if(file.isDirectory()) { maketable(file); }		//�ش� ������ ������� ���̺� �ʱ�ȭ
//					else {	//�ش������� ������ �ƴ϶��
//						//���̺� ����
//						while(model.getRowCount() != 0) {
//							model.removeRow(0);
//						}
//						table.updateUI(); //����� UI ����
//					}
//					locatetext.setText(getPath(e));		//�ּ�â ����
//					reallocate = getPath(e);			//���� �ּ� ����
//				}
//			} catch(Exception ex) {		//����ó��
//				JOptionPane.showMessageDialog(null, "������ ������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyTreeWillExpandListener implements TreeWillExpandListener {			//Ʈ��Ȯ�帮����
//		public void treeWillCollapse(TreeExpansionEvent e)		//Ʈ���� ���� ��
//				throws ExpandVetoException {
//			try {
//				//��Ʈ ����� ���
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("����ǻ��")){}
//				else {
//					//���õ� ���� parent ��� ����
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
//					while(parent.getChildCount() != 0) {		//�ش� ����� �ڼ� ������ŭ
//						parent.remove(0);						//�ڼ� ��带 ����
//					}
//					parent.add(new DefaultMutableTreeNode("�������"));		//parent��忡 "�������"��� �߰�
//				}
//			} catch(Exception ex) {		//����ó��
//				JOptionPane.showMessageDialog(null, "������ ������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//
//		public void treeWillExpand(TreeExpansionEvent e)		//Ʈ���� Ȯ�� �� ��
//				throws ExpandVetoException {
//			try {
//				//��Ʈ����� ���
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("����ǻ��")){}
//				else {
//					File file = new File(getPath(e));		//���õ� ���� file ����
//					File subfile[] = file.listFiles();		//file�� subfile �迭 ����
//					//���õ� ���� parent ��� ����
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
//					DefaultMutableTreeNode tempn;			//�ӽó�� tempn ����
//					//parent�� subfile��ŭ �ڽĳ�� �߰�
//					for(int i = 0; i < subfile.length; i++) {								//subfile ���̸�ŭ �ݺ�
//						if(!subfile[i].isHidden()) {										//�ش� sub������ ������°� �ƴϸ�
//							tempn = new DefaultMutableTreeNode(subfile[i].getName());		//tempn�� i���� subfile ��� �߰�
//							if(subfile[i].isDirectory())									//�ش� ��尡 ������ ���
//								tempn.add(new DefaultMutableTreeNode("�������"));				//"�������"��� �߰�
//							parent.add(tempn);												//parent�� tempn �߰�
//						}
//					}
//					if(parent.getChildCount() != 1)		//��尡 ������� ������
//						parent.remove(0);				//ù���� �ڽĳ��("�������"���) ����
//				}
//			} catch(Exception ex) {			//����ó��
//				JOptionPane.showMessageDialog(null, "������ ������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyListSelectionListener implements ListSelectionListener {		//����Ʈ ���� ������
//		public void valueChanged(ListSelectionEvent e) {
//			try {
//				if(!e.getValueIsAdjusting()) {
//					//���õ� ����Ʈ�� �ε����� ���� realvector�� �ش� �ּҷ� file����
//					File file = new File(realvector.get(likelist.getSelectedIndex())+"");
//					//���õ� ����Ʈ�� �ε����� ���� realvector�� �ش� �ּҷ� �����ּ�(reallocate) ����
//					reallocate = realvector.get(likelist.getSelectedIndex())+"";
//					locatetext.setText(reallocate);		//reallocate�� �ּ�â ����
//					maketable(file);					//file�� ���̺� ����
//				}
//			} catch(Exception ex) {			//����ó��
//				JOptionPane.showMessageDialog(null, "������ ������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyMouseAdapter extends MouseAdapter {		//���콺 ���
//		public void mouseClicked(MouseEvent e) {		//���콺 Ű�� ������
//			preView();									//�̸����� ����
//			if(e.getClickCount() == 2) {				//����Ŭ���� ���
//				//�ּ�â�� �ּҸ� ���̺��� ���õ� ���Ϸ� �ٲٰ�
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {
//					locatetext.setText(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//				}
//				else {
//					locatetext.setText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				}
//    			moveLocate();			//�̵� ��� ����
//    		}
//		}
//	}
//	
//	class MyKeyAdapter extends KeyAdapter {		//Ű ���
//		public void keyPressed(KeyEvent e) {		//Ű�� ������
//			try {
//				if(e.getKeyCode() == 10) {		//Ű�� "Enter" ���
//					//�ּ�â�� �ּҸ� ���̺��� ���õ� ���Ϸ� �ٲ۴�.
//					if(reallocate.endsWith("\\") || reallocate.equals("")) {
//						locatetext.setText(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						locatetext.setText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					moveLocate();		//�̵� ��� ����
//				}
//				else if(e.getKeyCode() == 8) {		//Ű�� "Backspace" ���
//					moveUp();
//				}
//			} catch(Exception ex) {
//				JOptionPane.showMessageDialog(null, "table���� ���ų� �̵��� ��� ����/������ �����ϼ���.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//		
//		public void keyReleased(KeyEvent e) {		//Ű�� ����
//			preView();								//�̸����� ����
//		}
//	}
//	
//	class PreviewImage extends JPanel {		//�̸������� �̹��� ����� �г�
//		Image image;
//		
//		PreviewImage(String loc) {
//			setPreferredSize(new Dimension(125, 125));		//ũ�⼳��
//			setBackground(Color.WHITE);						//���� ����
//			
//			image = Toolkit.getDefaultToolkit().getImage(loc);	
//			
//		}
//		
//		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);	//�̹��� �׸���
//		}
//	}
//	
//	class PreviewText extends JTextArea {		//�̸������� �ؽ�Ʈ ����г�
//		PreviewText(String loc) {
//			setEditable(false);							//�����Ұ����ϰ� ����
//			setPreferredSize(new Dimension(125, 125));	//ũ�⼳��
//			setBackground(Color.WHITE);					//���� WHITE�� ����
//			try {
//				FileReader fr = new FileReader(loc);	//�Ű�����loc�� �ּҷ� FileReader����
//				String intext = "";						//����� �ؽ�Ʈ
//				int c, count = 0, linecount = 0;		//���� ����, ��, ���� ǥ���� ����
//				
//				//�� ���ڸ� �о�ͼ� c�� �����ϰ� �װ� ���� �ƴϰų� ���� 7������ �̻��� �ƴϸ� �ݺ�
//				while((c = fr.read()) != -1 && linecount < 7) {
//					if(c == 13) {			//���� ���ڰ� �ٹٲ��̸�
//						linecount ++;		//�ຯ�� �ϳ� ����
//						count = -2;			//������ �ʱ�ȭ
//					}
//					else if(count == 17) {	//�� �ٿ��� 17��° �����̸�
//						intext += "\n";		//���� �߰�
//						linecount++;		//�ຯ�� �ϳ� ����
//						count = 0;			//������ �ʱ�ȭ
//					}
//					if(count == -2)			//�������� -2�̸� 
//						intext += "\n";	//����� �ؽ�Ʈ�� '\n'�߰�
//					else if(count != -1)	//�������� -1�� �ƴϸ� 
//						intext += (char)c;	//����� �ؽ�Ʈ�� c�߰�
//					count++;				//������ �ϳ� ����
//				}
//				setText(intext);			//intext�� ���
//				fr.close();					//fr�ݱ�
//			} catch(IOException ex) {		//����ó��
//				//�����޼��� "����� ����" �߻�
//				JOptionPane.showMessageDialog(null, "����� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class User {
//		String ID;				//������� ID
//		String passWord;		//������� ��й�ȣ
//		String name;			//������� �̸�
//		String mailAdr;			//������� �����ּ�
//		String mailPass;		//������� ���Ͼ�ȣ
//		
//		//�⺻ ������
//		User(){
//			ID = "";
//			passWord = "";
//			name = "";
//			mailAdr = "";
//			mailPass = "";
//		}
//		
//		//��������
//		void updateData(String ID, String passWord, String name){
//			this.ID = ID;
//			this.passWord = passWord;
//			this.name = name;
//			setTitle("6�� Ž���� - " + NowUser.ID);
//		}
//		
//		//���ã�����
//		void updateVector(String ulv, String urv) {
//			//���ã�� ���� �ʱ�ȭ
//			for(int j=likevector.size(); j>0; j--) {
//				likevector.remove(0);
//				realvector.remove(0);
//			}
//			//�̸� ���� �籸��
//			if(!ulv.equals("[]")) {
//				String templ1[] = ulv.split("\\[");
//				String templ2[] = templ1[1].split("\\]");
//				String templ[] = templ2[0].split(", ");
//				
//				for(int i=0; i<templ.length; i++) {
//					likevector.add(templ[i]);
//				}
//			}
//			//���� �ּ� ���� �籸��
//			if(!urv.equals("[]")) {
//				String tempr1[] = urv.split("\\[");
//				String tempr2[] = tempr1[1].split("\\]");
//				String tempr[] = tempr2[0].split(", ");
//				
//				for(int i=0; i<tempr.length; i++) {
//					realvector.add(tempr[i]);
//				}
//			}
//			
//			likelist.updateUI();	//����� UI ����
//		}
//		
//		//�������� ����
//		void updateMail(String mailAdr, String mailPass) {
//			this.mailAdr = mailAdr;
//			this.mailPass = mailPass;
//		}
//	}
//	
//	public class UserFrame extends JFrame {
//		JTextField IDText = new JTextField(10);		//ID�Է��ʵ�
//		JTextField passText = new JTextField(10);		//password�Է��ʵ�
//		JTextField nameText = new JTextField(10);		//�̸��Է��ʵ�
//		
//		JPanel userCpane = new JPanel();		//����������� �� �߰� �г�
//		JPanel userSpane = new JPanel();		//����������� �� �Ʒ��� �г�
//		
//		UserFrame(){
//			setTitle("����� ���� - " + NowUser.ID);					//Ÿ��Ʋ ����
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����� ��� �����Ӹ� ����ǵ��� ����
//			Container con = getContentPane();		//����Ʈ �� ��������
//			con.setLayout(new BorderLayout());		//���̾ƿ��� BorderLayout���� ����
//			
//			makeuserCpane();
//			makeuserSpane();
//			
//			userSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane ũ�⼳��
//			
//			con.add(userCpane, "Center");
//			con.add(userSpane, "South");
//			
//			setSize(400, 275);			//ũ�⼳��
//			setVisible(true);			//ȭ�鿡 ���
//		}
//		
//		void makeuserCpane(){
//			JLabel IDLabel = new JLabel("    I      D");		//ID ���̺� ����
//			JLabel passLabel = new JLabel("password");		//password ���̺� ����
//			JLabel nameLabel = new JLabel("    ��    ��");		//name ���̺� ����
//			
//			JLabel userIDLabel = new JLabel(NowUser.ID);			//����� ID ���̺� ����
//			JLabel userPassLabel = new JLabel(NowUser.passWord);		//����� password ���̺� ����
//			JLabel userNameLabel = new JLabel(NowUser.name);		//����� name ���̺� ����
//			
//			//ID, password, �̸� ���̺��� ũ�� ����
//			IDLabel.setPreferredSize(new Dimension(80, 22));
//			passLabel.setPreferredSize(new Dimension(80, 22));
//			nameLabel.setPreferredSize(new Dimension(80, 22));
//			
//			//������� ID, password, �̸� ���̺��� ũ�� ����
//			userIDLabel.setPreferredSize(new Dimension(114, 22));
//			userPassLabel.setPreferredSize(new Dimension(114, 22));
//			userNameLabel.setPreferredSize(new Dimension(114, 22));
//			
//			userCpane.add(IDLabel);				//'ID'���̺� ���
//			userCpane.add(IDText);					//ID�Է��ʵ� ���
//			userCpane.add(userIDLabel);					//ID�Է��ʵ� ���
//			userCpane.add(passLabel);			//'passworld'���̺� ���
//			userCpane.add(passText);					//password�Է��ʵ� ���
//			userCpane.add(userPassLabel);					//ID�Է��ʵ� ���
//			userCpane.add(nameLabel);			//'�̸�'���̺� ���
//			userCpane.add(nameText);					//�̸��Է��ʵ� ���
//			userCpane.add(userNameLabel);					//ID�Է��ʵ� ���
//			
//			if(NowUser.ID.equals("")) {
//				userCpane.add(new JLabel("1. �α��� : ID�� password�� �Է��ϰ� �α��� ��ư�� Ŭ��"));
//				userCpane.add(new JLabel("2. ȸ������ : ��� ������ �Է��ϰ� ȸ������ ��ư�� Ŭ��"));
//				userCpane.add(new JLabel("    �ߺ��� ID�� ������ ������ ���� ����"));
//			}
//			else {
//				userCpane.add(new JLabel("1. �α׾ƿ� : �α׾ƿ� ��ư�� Ŭ��"));
//				userCpane.add(new JLabel("2. ���� : ��� �����͸� �Է��ϰ� ���� ��ư�� Ŭ��"));
//				userCpane.add(new JLabel("    ID�� �������� �� ��� ȸ��Ż�� ����"));
//			}
//			userCpane.add(new JLabel("3. �ݱ� : � �ൿ�� �����ʰ� â�� ����"));
//			
//		}
//		
//		void makeuserSpane(){
//			if(NowUser.ID.equals("")) {
//				JButton login = new JButton("�α���");		//'�α���' ��ư ����
//				JButton userCreate = new JButton("ȸ������");		//'ȸ������' ��ư ����
//				login.addActionListener(new MyUserActionListener());		//�α��� ��ư ���Ǹ����� ���
//				userCreate.addActionListener(new MyUserActionListener());		//ȸ������ ��ư ���Ǹ����� ���
//				
//				userSpane.add(login);			//'�α���'��ư ���
//				userSpane.add(userCreate);			//'ȸ������'��ư ���
//			}
//			else {
//				JButton logout = new JButton("�α׾ƿ�");		//'�α׾ƿ�' ��ư ����
//				JButton userUpdate = new JButton("����");		//'ȸ������' ��ư ����
//				logout.addActionListener(new MyUserActionListener());		//�α׾ƿ� ��ư ���Ǹ����� ���
//				userUpdate.addActionListener(new MyUserActionListener());		//���� ��ư ���Ǹ����� ���
//				
//				userSpane.add(logout);			//'�α׾ƿ�'��ư ���
//				userSpane.add(userUpdate);			//'����'��ư ���
//			}
//			JButton userFrameClose = new JButton("�ݱ�");		//�ݱ� ��ư ����
//			userFrameClose.addActionListener(new MyUserActionListener());		//�ݱ� ��ư ���Ǹ����� ���
//			
//			userSpane.add(userFrameClose);			//'�ݱ�'��ư ���
//		}
//		
//		//������������� ���Ǹ�����
//		//������������� ��ư�� ������ �Լ��� ����
//		class MyUserActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "�α���") login();
//				else if(b.getActionCommand() == "�α׾ƿ�") logout();
//				else if(b.getActionCommand() == "ȸ������") {
//					if(!IDText.getText().equals("")) userCreate();
//					else JOptionPane.showMessageDialog(null, "ID�� �Է��ϼ���.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//				else if(b.getActionCommand() == "����") {
//					if(!IDText.getText().equals("")) userUpdate();
//					else userDelete();
//				}
//				else if(b.getActionCommand() == "�ݱ�") userFrameClose();
//			}
//		}
//		
//		//�α����Ѵ�.
//		//DB���� ����� ������ �ҷ��´�.
//		void login() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB����
//				//DB���� ID �˻�
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				rs = pstmt.executeQuery();
//				
//				if(rs.next()) {			//DB�� ID�� ������ ���
//					if(rs.getString("user_pass").equals(passText.getText())) {		//ID�� password�� ��ġ�� ���
//						NowUser.updateData(IDText.getText(), passText.getText(), rs.getString("user_name"));
//						NowUser.updateVector(rs.getString("user_lv"), rs.getString("user_rv"));
//						NowUser.updateMail(rs.getString("user_mail_adr"), rs.getString("user_mail_pass"));
//						userFrameClose();
//					}
//					else {			//���޼��� ���
//						JOptionPane.showMessageDialog(null, "��ȣ�� ���� �ʽ��ϴ�..", "Message", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "ID�� �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				}						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			} finally {
//				try {
//					if(pstmt != null) pstmt.close();
//					if(conn != null) conn.close();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//�α׾ƿ��Ѵ�.
//		void logout() {
//			NowUser.updateData("", "", "");
//			NowUser.updateVector("[]", "[]");
//			userFrameClose();
//		}
//		
//		//���ο� ����ڸ� ����Ѵ�.
//		//DB���� ����Ѵ�.
//		void userCreate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB����
//				//DB���� ID �˻�
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				
//				if(!pstmt.executeQuery().next()) {		//DB�� ���� ID�� ���� ���
//					query = "insert into userlist(user_id,user_pass,user_name,user_lv, user_rv, user_mail_adr, user_mail_pass) values (?,?,?,?,?,?,?)";
//					pstmt = conn.prepareStatement(query);
//					
//					pstmt.setString(1, IDText.getText());
//					pstmt.setString(2, passText.getText());
//					pstmt.setString(3, nameText.getText());
//					pstmt.setString(4, likevector.toString());
//					pstmt.setString(5, realvector.toString());
//					pstmt.setString(6, "");
//					pstmt.setString(7, "");
//					
//					pstmt.executeUpdate();
//					
//					NowUser.updateData(IDText.getText(), passText.getText(), nameText.getText());
//					userFrameClose();
//				}
//				else {			//���޼��� ���
//					JOptionPane.showMessageDialog(null, "���� ID�� �����մϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			} finally {
//				try {
//					if(pstmt != null) pstmt.close();
//					if(conn != null) conn.close();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//����� ������ �����Ѵ�.
//		//DB������ �����Ѵ�.
//		void userUpdate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB����
//				//DB���� ID �˻�
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				
//						
//				if(!pstmt.executeQuery().next() || NowUser.ID.equals(IDText.getText())) {		//DB�� ���� ID�� ���� ���
//					//DB�� ���� ����
//					query = "update userlist set user_ID=?,user_pass=?,user_name=? where user_ID = ?";
//					pstmt = conn.prepareStatement(query);
//					pstmt.setString(4, NowUser.ID);
//					pstmt.setString(1, IDText.getText());
//					pstmt.setString(2, passText.getText());
//					pstmt.setString(3, nameText.getText());
//					pstmt.executeUpdate();
//					
//					NowUser.updateData(IDText.getText(), passText.getText(), nameText.getText());
//					userFrameClose();
//				}
//				else {			//���޼��� ���
//					JOptionPane.showMessageDialog(null, "���� ID�� �����մϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				}						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			} finally {
//				try {
//					if(pstmt != null) pstmt.close();
//					if(conn != null) conn.close();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//DB���� ����ڵ����͸� �����Ѵ�.
//		//�α׾ƿ��Ѵ�.
//		void userDelete() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB����
//				//DB���� ID �˻�
//				String query = "delete from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, NowUser.ID);
//				pstmt.executeUpdate();
//				
//				logout();						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			} finally {
//				try {
//					if(pstmt != null) pstmt.close();
//					if(conn != null) conn.close();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//����� â�� �ݴ´�.
//		void userFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	public class MailAdrUpdateFrame extends JFrame {
//		JTextField mailAdrText = new JTextField(10);		//�����ּ��Է��ʵ�
//		JTextField mailPassText = new JTextField(10);		//����password�Է��ʵ�
//		
//		JPanel mailCpane = new JPanel();		//���ϼ����� �� �߰� �г�
//		JPanel mailSpane = new JPanel();		//���ϼ����� �� �Ʒ��� �г�
//		
//		MailAdrUpdateFrame() {
//			setTitle("���� ���� - " + NowUser.ID);					//Ÿ��Ʋ ����
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����� ��� �����Ӹ� ����ǵ��� ����
//			Container con = getContentPane();		//����Ʈ �� ��������
//			con.setLayout(new BorderLayout());		//���̾ƿ��� BorderLayout���� ����
//			
//			makemailCpane();
//			makemailSpane();
//			
//			mailSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane ũ�⼳��
//			
//			con.add(mailCpane, "Center");
//			con.add(mailSpane, "South");
//			
//			setSize(400, 150);			//ũ�⼳��
//			setVisible(true);			//ȭ�鿡 ���
//		}
//		
//		void makemailCpane(){
//			JLabel mailAdrLabel = new JLabel("�����ּ�");			//�����ּ� ���̺� ����
//			JLabel mailPassLabel = new JLabel("password");		//���� password ���̺� ����
//			
//			JLabel userMailAdrLabel = new JLabel(NowUser.mailAdr);			//����� �����ּ� ���̺� ����
//			JLabel userMailPassLabel = new JLabel(NowUser.mailPass);		//����� ���� password ���̺� ����
//			
//			//�����ּ�, password, �̸� ���̺��� ũ�� ����
//			mailAdrLabel.setPreferredSize(new Dimension(80, 22));
//			mailPassLabel.setPreferredSize(new Dimension(80, 22));
//			
//			//������� �����ּ�, password, �̸� ���̺��� ũ�� ����
//			userMailAdrLabel.setPreferredSize(new Dimension(114, 22));
//			userMailPassLabel.setPreferredSize(new Dimension(114, 22));
//			
//			mailCpane.add(mailAdrLabel);				//'�����ּ�'���̺� ���
//			mailCpane.add(mailAdrText);					//�����ּ��Է��ʵ� ���
//			mailCpane.add(userMailAdrLabel);					//����ڸ����ּ� ���̺� ���
//			mailCpane.add(mailPassLabel);			//'passworld'���̺� ���
//			mailCpane.add(mailPassText);					//password�Է��ʵ� ���
//			mailCpane.add(userMailPassLabel);					//����� password ���̺� ���
//		}
//		
//		void makemailSpane(){
//			JButton mailUpdate = new JButton("����");		//'����' ��ư ����
//			JButton mailFrameClose = new JButton("�ݱ�");		//�ݱ� ��ư ����
//			mailUpdate.addActionListener(new MyMailActionListener());		//���ϼ��� ��ư ���Ǹ����� ���
//			mailFrameClose.addActionListener(new MyMailActionListener());		//�ݱ� ��ư ���Ǹ����� ���
//
//			mailSpane.add(mailUpdate);			//'���ϼ���'��ư ���
//			mailSpane.add(mailFrameClose);			//'�ݱ�'��ư ���
//		}
//		
//		//���ϼ��� �������� ���Ǹ�����
//		//���ϼ��� �������� ��ư�� ������ �Լ��� ����
//		class MyMailActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "����") mailAdrUpdate();
//				else if(b.getActionCommand() == "�ݱ�") mailFrameClose();
//			}
//		}
//		
//		//�����ּ� ����
//		//DB������ ����
//		void mailAdrUpdate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB����
//				
//				//DB�� ���� ����
//				String query = "update userlist set user_mail_adr=?,user_mail_pass=? where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(3, NowUser.ID);
//				pstmt.setString(1, mailAdrText.getText());
//				pstmt.setString(2, mailPassText.getText());
//				pstmt.executeUpdate();
//				
//				NowUser.updateMail(mailAdrText.getText(), mailPassText.getText());
//				mailFrameClose();				
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			} finally {
//				try {
//					if(pstmt != null) pstmt.close();
//					if(conn != null) conn.close();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		//���ϼ��� â�� �ݴ´�
//		void mailFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	//������ ������ ������
//	public class MailSendFrame extends JFrame {
//		JTextField mailTitleText = new JTextField(15);		//�������� �Է��ʵ�
//		JTextArea mailText = new JTextArea();			//���ϳ��� �Է°���
//		JTextField mailToText = new JTextField(15);		//���Ϲ޴��� �Է��ʵ�
//		JTextField mailFileText = new JTextField(15);		//÷������ �Է��ʵ�
//		
//		JPanel sendCpane = new JPanel();		//���ϼ����� �� �߰� �г�
//		JPanel sendSpane = new JPanel();		//���ϼ����� �� �Ʒ��� �г�
//		
//		MailSendFrame() {
//			setTitle("���� ������ - " + NowUser.ID);					//Ÿ��Ʋ ����
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����� ��� �����Ӹ� ����ǵ��� ����
//			Container con = getContentPane();		//����Ʈ �� ��������
//			con.setLayout(new BorderLayout());		//���̾ƿ��� BorderLayout���� ����
//			
//			makemailText();
//			makesendCpane();
//			makesendSpane();
//			
//			sendSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane ũ�⼳��
//			
//			//con.add(mailText, "North");
//			con.add(sendCpane, "Center");
//			con.add(sendSpane, "South");
//			
//			setSize(400, 450);			//ũ�⼳��
//			setVisible(true);			//ȭ�鿡 ���
//		}
//		
//		void makesendCpane(){
//			JLabel mailTitleLabel = new JLabel("��������");		//�������� ���̺� ����
//			JLabel mailLabel = new JLabel("���ϳ���");				//���ϳ��� ���̺� ����
//			JLabel mailToLabel = new JLabel("�޴��ּ�");				//�޴��ּ� ���̺� ����
//			JLabel mailFileLabel = new JLabel("÷������");			//÷������ ���̺� ����
//			JButton selectFile = new JButton("���ϼ���");			//'���ϼ���' ��ư ����
//			selectFile.addActionListener(new MySendActionListener());		//�ݱ� ��ư ���Ǹ����� ���
//			
//			mailFileText.setEditable(false);				//÷������ �ʵ� �����Ұ����ϰ� ����
//			mailFileText.setBackground(Color.WHITE);		//����� �Ͼ������ ����
//			
//			//���� ���̺��� ũ�� ����
//			mailTitleLabel.setPreferredSize(new Dimension(100, 22));
//			mailLabel.setPreferredSize(new Dimension(100, 22));
//			mailToLabel.setPreferredSize(new Dimension(100, 22));
//			mailFileLabel.setPreferredSize(new Dimension(100, 22));
//			
//			//�гο� ���
//			sendCpane.add(mailTitleLabel);				//'��������'���̺� ���
//			sendCpane.add(mailTitleText);				//'��������'�Է��ʵ� ���
//			sendCpane.add(mailLabel);					//'���ϳ���'���̺� ���
//			sendCpane.add(mailText);					//'���ϳ���'�Է��ʵ� ���
//			sendCpane.add(mailToLabel);					//'�޴��ּ�'���̺� ���
//			sendCpane.add(mailToText);					//'�޴��ּ�'���̺� ���
//			sendCpane.add(mailFileLabel);				//'÷������'���̺� ���
//			sendCpane.add(mailFileText);				//'÷������'���̺� ���
//			sendCpane.add(selectFile);					//'���ϼ���'��ư ���
//		}
//		
//		void makesendSpane(){
//			JButton mailSend = new JButton("������");			//'������' ��ư ����
//			JButton sendFrameClose = new JButton("�ݱ�");		//�ݱ� ��ư ����
//			mailSend.addActionListener(new MySendActionListener());				//���ϼ��� ��ư ���Ǹ����� ���
//			sendFrameClose.addActionListener(new MySendActionListener());		//�ݱ� ��ư ���Ǹ����� ���
//
//			sendSpane.add(mailSend);				//'������'��ư ���
//			sendSpane.add(sendFrameClose);			//'�ݱ�'��ư ���
//		}
//		
//		void makemailText() {
//			mailText.setPreferredSize(new Dimension(400, 200));	//ũ�⼳��
//			mailText.setBackground(Color.WHITE);					//���� WHITE�� ����
//		}
//		
//		//���Ϻ����� �������� ���Ǹ�����
//		//���Ϻ����� �������� ��ư�� ������ �Լ��� ����
//		class MySendActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "���ϼ���") selectSendFile();
//				else if(b.getActionCommand() == "������") mailSend();
//				else if(b.getActionCommand() == "�ݱ�") sendFrameClose();
//			}
//		}
//		
//		//������ ������.
//		void mailSend() {
//			try {
//				String tmp[] = NowUser.mailAdr.split("@");
//				
//				String host     = "smtp." + tmp[1];
//				final String user   = tmp[0];
//				final String password  = NowUser.mailPass;
//				String to = mailToText.getText();
//
//				  
//				// Get the session object
//				Properties props = new Properties();
//				props.put("mail.smtp.host", host);
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.starttls.enable", "true");
//
//				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//							return new PasswordAuthentication(user, password);
//					}
//				});
//
//				// Compose the message
//				MimeMessage message = new MimeMessage(session);
//				message.setFrom(new InternetAddress(user));
//				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//				// Subject
//				message.setSubject(mailTitleText.getText());
//			
//				// Text
//				Multipart mp = new MimeMultipart();
//				MimeBodyPart mbp = new MimeBodyPart();
//				mbp.setText(mailText.getText());
//				mp.addBodyPart(mbp);
//				
//				//file
//				if(!mailFileText.getText().equals("")) {
//					mbp = new MimeBodyPart();
//					File file = new File(mailFileText.getText());
//					FileDataSource fds = new FileDataSource(file);
//					mbp.setDataHandler(new DataHandler(fds));
//					String fileName = fds.getName();
//					fileName = new String(fileName.getBytes("KSC5601"), "8859_1");
//					mbp.setFileName(fileName);
//					mp.addBodyPart(mbp);
//				}
//
//
//				message.setContent(mp);
//				
//				// send the message
//				Transport.send(message);
//				JOptionPane.showMessageDialog(null, "������ ���������� ���������ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "���� �����Ⱑ �����߽��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//		
//		//÷�������� �����Ѵ�.
//		void selectSendFile() {
//			//�� â�� ��� ÷���� ���ϸ� �Է¹���
//			String locate = JOptionPane.showInputDialog("÷���� ������ �ּҸ� �Է��ϼ���.");
//			if(locate.equals("")) {		//�Է¹��� �ּҰ� ������ ��
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "�ּҸ� �Է����� �ʾҽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			} else {
//				File selectfile = new File(locate);		//�Է¹��� �ּҷ� ���� ����
//				if(!selectfile.exists()) {		//�ش� ������ �������� ���� ��
//					//�����޼��� ���
//					JOptionPane.showMessageDialog(null, "������ �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				} else {
//					mailFileText.setText(locate);
//				}
//			}
//		}
//		
//		//���Ϻ����� â�� �ݴ´�
//		void sendFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	Seeker() {
//		//DB����
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		setTitle("6�� Ž���� - " + NowUser.ID);			//Ÿ��Ʋ ����
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����� ��� �����Ӹ� ����ǵ��� ����
//		Container con = getContentPane();		//����Ʈ �� ��������
//		con.setLayout(new BorderLayout());		//���̾ƿ��� BorderLayout���� ����
//		
//		makeMenu();		//�޴� �����
//		makeNorth();		//northpane�����
//		makeWest();		//westpane�����
//		makeEast();		//eastpane�����
//		makeCenter();		//centerpane�����
//		
//		westpane.setPreferredSize(new java.awt.Dimension(150, 0));		//westpane ũ�⼳��
//		eastpane.setPreferredSize(new java.awt.Dimension(150, 0));		//eastpane ũ�⼳��
//		
//		con.add(northpane, "North");			//nortpane�� ���ʿ� �ֱ�
//		con.add(westpane, "West");			//westpane�� ���ʿ� �ֱ�
//		con.add(eastpane, "East");			//eastpane�� �����ʿ� �ֱ�
//		con.add(centerpane, "Center");			//centerpane�� ����� �ֱ�
//		
//		setSize(800, 600);			//ũ�⼳��
//		setVisible(true);			//ȭ�鿡 ���
//	}
//	
//	void makeMenu() {		//�޴������
//		JMenuBar mb = new JMenuBar();		//�޴��� mb ����
//		
//		JMenu m1 = new JMenu("����");			//'����'�޴� m1 ����
//		JMenuItem m11 = new JMenuItem("�� â ����");	//'�� â ����'�޴� ������ m11 ����
//		JMenuItem m12 = new JMenuItem("�����");	//'�����'�޴� ������ m12 ����
//		JMenuItem m13 = new JMenuItem("�ݱ�");		//'�ݱ�' �޴������� m13 ����
//		m11.addActionListener(new MyActionListener());		//m11���Ǹ����� ���
//		m12.addActionListener(new MyActionListener());		//m12���Ǹ����� ���
//		m13.addActionListener(new MyActionListener());		//m13���Ǹ����� ���
//		
//		m1.add(m11);		//m1�� m11�߰�
//		m1.add(m12);		//m1�� m12�߰�
//		m1.addSeparator();	//m1�� ���м� �߰�
//		m1.add(m13);		//m1�� m13�߰�
//		
//		JMenu m2 = new JMenu("����");			//'����' �޴� m2 ����
//		JMenuItem m21 = new JMenuItem("������");		//'������' �޴� ������ m21����
//		JMenuItem m22 = new JMenuItem("�̸��ٲٱ�");	//'�̸� �ٲٱ�' �޴������� m22����
//		JMenuItem m23 = new JMenuItem("�̵�");		//'�̵�' �޴������� m23����
//		JMenuItem m24 = new JMenuItem("����");		//'����' �޴������� m24����
//		m21.addActionListener(new MyActionListener());	//m21���Ǹ����� ���
//		m22.addActionListener(new MyActionListener());	//m22���Ǹ����� ���
//		m23.addActionListener(new MyActionListener());	//m23���Ǹ����� ���
//		m24.addActionListener(new MyActionListener());	//m24���Ǹ����� ���
//		
//		m2.add(m21);		//m2�� m21�߰�
//		m2.add(m22);		//m2�� m22�߰�
//		m2.add(m23);		//m2�� m23�߰�
//		m2.add(m24);		//m2�� m24�߰�
//		
//		JMenu m3 = new JMenu("����");				//'����'�޴� m3����
//		JMenuItem m31 = new JMenuItem("�̸��� ����");	//'�̸��� ����'�޴������� m31����
//		JMenuItem m32 = new JMenuItem("�ֽż� ����");	//'�ֽż� ����'�޴������� m32����
//		JMenuItem m33 = new JMenuItem("�˻�");		//'�˻�'�޴������� m33����
//		m31.addActionListener(new MyActionListener());	//m31���Ǹ����� ���
//		m32.addActionListener(new MyActionListener());	//m32���Ǹ����� ���
//		m33.addActionListener(new MyActionListener());	//m33���Ǹ����� ���
//				
//		m3.add(m31);		//m3�� m31�߰�
//		m3.add(m32);		//m3�� m32�߰�
//		m3.add(m33);		//m3�� m33�߰�
//		
//		JMenu m4 = new JMenu("����");				//'����'�޴� m4����
//		JMenuItem m41 = new JMenuItem("�����ּ� ����");	//'�����ּ� ����'�޴������� m31����
//		JMenuItem m42 = new JMenuItem("���� ������");		//'���Ϻ�����'�޴������� m32����
//		m41.addActionListener(new MyActionListener());	//m31���Ǹ����� ���
//		m42.addActionListener(new MyActionListener());	//m32���Ǹ����� ���
//
//		m4.add(m41);		//m3�� m41�߰�
//		m4.add(m42);		//m3�� m42�߰�
//	
//		mb.add(m1);			//mb�� m1�߰�
//		mb.add(m2);			//mb�� m2�߰�
//		mb.add(m3);			//mb�� m3�߰�
//		mb.add(m4);			//mb�� m4�߰�
//		
//		setJMenuBar(mb);	//�޴��ٷ� mb����
//	}
//	
//	void makeNorth() {			//northpane�����
//		JButton moveup = new JButton("...");		//�������� ��ư ����
//		JLabel locatelabel = new JLabel("�ּ�");		//�ּ� ���̺� ����
//		JButton movelocate = new JButton("�̵�");			//�̵� ��ư ����
//		moveup.addActionListener(new MyActionListener());	//����������ư �׼Ǹ����� �߰�
//		movelocate.addActionListener(new MyActionListener());	//�̵����p �׼Ǹ����� �߰�
//		
//		moveup.setPreferredSize(new Dimension(30, 20));		//����������ư ũ�⼳��
//		movelocate.setPreferredSize(new Dimension(60, 20));	//�̵���ư ũ�⼳��
//		
//		northpane.add(moveup);		//�������� ��ư �߰�
//		northpane.add(locatelabel);	//�ּ� ���̺� �߰�
//		northpane.add(locatetext);	//�ּ�â �߰�
//		northpane.add(movelocate);	//�̵���ư �߰�
//	}
//
//	void makeWest() {			//westpane�����
//		File list[] = File.listRoots();		//��ü ���� ����Ʈ ����
//		DefaultMutableTreeNode temp;		//�ӽ� Ʈ����� temp ����
//		
//		for(int i=0;i<list.length;++i) {	//��ü ���ϸ�ŭ �ݺ�
//			if(list[i].exists()) {			//�ش� ������ �����Ѵٸ�
//				temp = new DefaultMutableTreeNode(list[i].getPath());		//temp�� ��� �߰�
//				temp.add(new DefaultMutableTreeNode("�������"));				//temp������ "�������"��� �߰�
//				root.add(temp);		//root�� tmep �߰�
//			}
//		}
//		
//		tree = new JTree(root);			//root�� ����� JTree�����ϰ� tree�ʱ�ȭ
//		tree.addTreeSelectionListener(new MyTreeSelectionListener());	//tree�� Ʈ������ ������ �߰�
//		tree.addTreeWillExpandListener(new MyTreeWillExpandListener());	//tree�� Ʈ�� Ȯ�� ������ �߰�
//		westpane = new JScrollPane(tree);		//tree�� JScrollPane����, westpane �ʱ�ȭ
//	}
//	
//	void makeEast() {			//eastpane�����
//		JButton addlike = new JButton("�߰�");		//�߰� ��ư ����
//		JButton dellike = new JButton("����");		//���� ��ư ����
//		addlike.addActionListener(new MyActionListener());	//�߰� ��ư ���Ǹ����� �߰�
//		dellike.addActionListener(new MyActionListener());	//���� ��ư ���Ǹ����� �߰�
//		
//		//likelist�� ����Ʈ ���� ������ �߰�
//		likelist.addListSelectionListener(new MyListSelectionListener());
//		
//		JPanel likepane = new JPanel();		//JPanel�� likepane ����
//		
//		likepane.add(likelist);		//likepane�� likelist �߰�
//		likepane.add(addlike);		//likepane�� �߰���ư(addlike) �߰�
//		likepane.add(dellike);		//likepane�� ������ư(dellike) �߰�
//		
//		likelist.setPreferredSize(new Dimension(125, 225));		//likelistũ�� ����
//		
//		likepane.setPreferredSize(new Dimension(150, 300));		//llkepane ũ�⼳��
//		previewpane.setPreferredSize(new Dimension(150, 175));	//previewpane ũ�⼳��
//		
//		likepane.setBorder(new TitledBorder("���ã��")); 		//likepane �׵θ�, Ÿ��Ʋ ����
//		previewpane.setBorder(new TitledBorder("�̸�����")); 	//previewpane �׵θ�, Ÿ��Ʋ ����
//		
//		eastpane.add(likepane);			//eastpane�� likepane �߰�
//		eastpane.add(previewpane);		//eastpane�� likpane �߰�
//	}
//	
//	void makeCenter() {		//centerpane�����
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//���� ���� ���� ����
//		table.addMouseListener(new MyMouseAdapter());			//���콺 ��� �߰�
//		table.addKeyListener(new MyKeyAdapter());				//Ű���� ��� �߰�
//		makeroottable();								//��Ʈ���̺� ����
//		locatetext.setText("����ǻ��");					//�ּ�â"����ǻ��"�� ����
//		centerpane.add(new JScrollPane(table));			//table�� ����� JScrollPane ����, centerpane�� �߰�
//	}
//	
//	void addLike() {		//���ã���� '�߰�'��� ���� �Լ�
//		File tempf = new File(reallocate);		//�����ּ�(reallocate)�� �ӽ� ���� tempf ����
//		if(tempf.exists()){				//tempf�� �����Ѵٸ�
//			realvector.add(reallocate);						//realvector�� reallocate �߰�
//			String tempn[] = reallocate.split("\\\\");		//reallocate�� \\�������� �и��ؼ� tempn�迭 ����
//			if(tempn.length == 1)				//tempn ���̰� 1�̶�� �� ��Ʈ �ٷ� ���� �ּҶ��
//				likevector.add(tempn[0] + "\\");	//likevector�� �ش� ��� �߰�(����̺��� �������� '\' �߰�)
//			else
//				likevector.add(tempn[tempn.length-1]);	//likevector�� ������ ��� �߰�
//			likelist.updateUI();		//UI ������� ����
//			
//			//DB���������� ���
//			if(!NowUser.ID.equals("")) {		//�α��� ���� ���
//				Connection conn = null;
//				PreparedStatement pstmt = null;
//				//DB����
//				try {
//					conn = DriverManager.getConnection(url,id,pass);		//DB����
//					//DB���� ID �˻�
//					String query = "update userlist set user_lv=?,user_rv=? where user_ID = ?";
//					pstmt = conn.prepareStatement(query);
//					pstmt.setString(3, NowUser.ID);
//					pstmt.setString(1, likevector.toString());
//					pstmt.setString(2, realvector.toString());
//					pstmt.executeUpdate();
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//				} finally {
//					try {
//						if(pstmt != null) pstmt.close();
//						if(conn != null) conn.close();
//					} catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		else {		//����ó��
//			JOptionPane.showMessageDialog(null, "�ּҰ� �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	void delLike() {		//���ã���� '����'��� ���� �Լ�
//		try {
//			realvector.remove(likelist.getSelectedIndex());		//���õ� �ε����� realvector ����
//			likevector.remove(likelist.getSelectedIndex());		//���õ� �ε����� likevector ����
//			likelist.updateUI();		//����� UI ����
//			
//			//DB�� �������� ���
//			if(!NowUser.ID.equals("")) {		//�α��� ���� ���
//				Connection conn = null;
//				PreparedStatement pstmt = null;
//				//DB����
//				try {
//					conn = DriverManager.getConnection(url,id,pass);		//DB����
//					//DB���� ID �˻�
//					String query = "update userlist set user_lv=?,user_rv=? where user_ID = ?";
//					pstmt = conn.prepareStatement(query);
//					pstmt.setString(3, NowUser.ID);
//					pstmt.setString(1, likevector.toString());
//					pstmt.setString(2, realvector.toString());
//					pstmt.executeUpdate();
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "DB ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//				} finally {
//					try {
//						if(pstmt != null) pstmt.close();
//						if(conn != null) conn.close();
//					} catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch(Exception ex) {			//����ó��
//			JOptionPane.showMessageDialog(null, "���ã�⿡�� ������ ����� �����ϼ���.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	
//	void moveUp() {		//�ּ��� '...'��� ���� �Լ�
//		if(reallocate.equals("")) {		//��Ʈ��ġ�� ���
//			JOptionPane.showMessageDialog(null, "�� �̻� ���������� �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		if(reallocate.endsWith("\\")) {		//��� �ٷ� ���� ��ġ�� ���
//			reallocate = "";				//reallocate �ʱ�ȭ
//			locatetext.setText("����ǻ��");	//locatetext �ʱ�ȭ
//			makeroottable();				//��Ʈ���̺� ����
//			return;
//		}
//		String token[] = reallocate.split("\\\\");		//�����ּ�(reallocate)�� "\\"�������� �и�
//		String locate = token[0];						//locate ����
//		for(int i = 1; i < token.length-1; i++) {		//���������� �ι�° ����
//			locate += "\\" + token[i];					//�ּ����·� �纴��
//		}
//		if(token.length == 2)				//��Ʈ �ٷ� ���� ��ġ�� ���
//			locate += "\\";					//�ּ� ���� '\'�߰�
//		reallocate = locate;				//���� �ּ� ����
//		locatetext.setText(locate);			//�ּ�â ����
//		File file = new File(locate);		//locate�� file����
//		maketable(file);					//file�� ���̺� �ʱ�ȭ
//	}
//	
//	void moveLocate() {		//�ּ��� '�̵�'��� ���� �Լ�
//		try {
//			File file = new File(locatetext.getText());		//�ּ�â���� ���ڿ��� �о�� file����
//			if(file.exists()){					//������ �����Ѵٸ�
//				if(file.isDirectory()) {		//������ �������
//					maketable(file);			//file�� ���̺� ����
//					reallocate = locatetext.getText();	//���� �ּҸ� �ּ�â�� �ؽ�Ʈ�� ����
//				}
//				else {		//������ �ƴ϶��
//					//����
//					Process process = Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + file.getAbsolutePath());
//				}
//			}
//			else {		//����ó��
//				JOptionPane.showMessageDialog(null, "�ּҰ� �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {		//����ó��
//			JOptionPane.showMessageDialog(null, "Ȯ�ε��� ���� ����", "Message", JOptionPane.ERROR_MESSAGE);
//			maketable(new File(reallocate));		//���̺� �����ּҷ� �ʱ�ȭ
//			locatetext.setText(reallocate);			//�ּ�â �����ּҷ� �ʱ�ȭ
//		}
//	}
//	
//	void m11() {		//�޴��� '�� â ����'��� ���� �ռ�
//		new Seeker();
//	}
//	
//	void m12() {		//�޴��� '�����'��� ���� �ռ�
//		new UserFrame();
//	}
//	
//	void m13() {		//�޴��� '�ݱ�'��� ���� �Լ�
//		this.setVisible(false);
//		this.dispose();
//	}
//	
//	void m21() {		//�޴��� '������'��� ���� �Լ�
//		try {
//			if(reallocate.equals("")) {		//��Ʈ��ġ���
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "�� ������ ���� ��ġ�� ������.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			//�� â�� ��� �� ������ �̸��� �Է¹���
//			String name = JOptionPane.showInputDialog("�� ������ �̸��� �Է��ϼ���.");
//			if(name.equals("")) {		//�̸��� ������ ���
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "�������� �̸��� �Է����� �ʾҽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			File file = new File(reallocate);			//���� ��ġ�� file ����
//			File sonfile = new File(reallocate + "\\" + name);		//reallocate�� name�� ����� sonfile ����
//			if(sonfile.exists()) {		//sonfile �̹� ������ ���
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "���丮���� ���� �̸��� ����/������ �����մϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			sonfile.mkdir();					//�� ���� ����
//			if(reallocate.endsWith("\\")) {		//������ġ�� ��Ʈ �ٷ� ���� ��ġ�� ���
//				treeadd(reallocate + name);		//Ʈ���� �� ���� �߰�
//			}
//			else {
//				treeadd(reallocate + "\\" + name);		//Ʈ���� ������ �߰�
//			}
//			maketable(file);		//file�� ���̺� �ʱ�ȭ
//		} catch(Exception ex) {}		// ����ó��
//	}
//	
//	void m22() {		//�޴��� '�̸� �ٲٱ�'��� ���� �Լ�
//		try {
//			//�� â�� ��� �ٲ� �̸��� �Է¹���
//			String name = JOptionPane.showInputDialog("�� �̸��� �Է��ϼ���.");
//			if(name.equals("")) {			//�̸��� ������ ���
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "�� �̸��� �Է����� �ʾҽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			try {
//				File file = new File(reallocate);		//���� ��ġ�� file����
//				//���õ� ���Ϸ� sefile ����
//				File sefile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				File refile = new File(reallocate + "\\" + name);		//���ο� �̸��� ���� refile����
//				if(refile.exists()) {		//�̹� �����ϴ� �̸��� ���
//					//�����޼��� ���
//					JOptionPane.showMessageDialog(null, "���� �̸��� ����/������ �̹� �ֽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				sefile.renameTo(refile);		//���� �̸� ����
//				if(reallocate.endsWith("\\")) {		//���� ��ġ�� ��Ʈ ���� ��ġ�� ���
//					//Ʈ������ ���� ��� ����
//					treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					treeadd(reallocate + name);		//Ʈ���� ���ο� ��� ����
//				}
//				else {
//					//Ʈ������ ���� ��� ����
//					treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					treeadd(reallocate + "\\" + name);		//Ʈ���� ���ο� ��� ����
//				}
//				maketable(file);		//file�� ���̺� �ʱ�ȭ
//			} catch(Exception ex) {		//����ó��
//				JOptionPane.showMessageDialog(null, "table���� �̸��� ������ ��� ����/������ �����ϼ���", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {}		//����ó��
//	}
//	
//	void m23() {		//�޴��� '�̵�'��� ���� �Լ�
//		try {
//			//�� â�� ��� �̵���ų ��ġ�� �Է¹���
//			String locate = JOptionPane.showInputDialog("�� ��ġ�� �Է��ϼ���.");
//			if(locate.equals("")) {		//�Է¹��� ��ġ�� ������ ��
//				//�����޼��� ���
//				JOptionPane.showMessageDialog(null, "�� ��ġ�� �Է����� �ʾҽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			try {
//				File wanfile = new File(locate);		//�� ��ġ�� wanfile ����
//				if(!wanfile.exists()) {		//wanfile�� �������� ���� ��
//					//�����޼��� ���
//					JOptionPane.showMessageDialog(null, "��ġ�� �������� �ʽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//		
//				}
//				File file = new File(reallocate);		//���� ��ġ�� file ����
//				//�̵���ų ���Ϸ� sefile ����
//				File sefile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				//����ġ�� ���Ϸ� refile ����
//				File refile = new File(locate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				if(refile.exists()) {		//refile�� ������ ���
//					//�����޼��� ���
//					JOptionPane.showMessageDialog(null, "�� ��ġ�� ���� �̸��� ����/������ �̹� �ֽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//				if(sefile.renameTo(refile)) {			//sefile�� refile�� �̸�����(=�ּҺ��� =�̵�)
//					if(reallocate.endsWith("\\")) {		//������ġ�� ��Ʈ ������ġ�� ���
//						//Ʈ������ ���� ������
//						treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						//Ʈ������ ������� ����
//						treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					if(locate.endsWith("\\")) {		//�� ��ġ�� ��Ʈ ���� ��ġ�� ���
//						//Ʈ���� �� ���� ��� ����
//						treeadd(locate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						//Ʈ���� �� ���� ��� ����
//						treeadd(locate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					maketable(file);		//���̺� �ʱ�ȭ
//				}
//				else JOptionPane.showMessageDialog(null, "Ȯ�ε��� ���� �����߻�", "Message", JOptionPane.ERROR_MESSAGE);	
//			} catch(Exception ex) {		//����ó��
//				JOptionPane.showMessageDialog(null, "table���� �̵��� ��� ����/������ �����ϼ���", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {}		//����ó��
//	}
//	
//	void m24() {		//�޴��� '����'��� ���� �ռ�
//		//���� �����Ұ��� �� â���� Ȯ��
//		int result = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
//		if(result != JOptionPane.YES_OPTION)		//���� �����ϰ� ���� �ʴٸ�
//			return;		//���
//		try {
//			File file = new File(reallocate);		//������ġ�� file����
//			//���õ� ���Ϸ� selfile ����
//			File selfile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//			deletefile(selfile);		//���õ� ���� ����
//			if(reallocate.endsWith("\\")) {			//������ġ�� ��Ʈ �ٷ� ���� ��ġ���
//				//Ʈ������ ���� ����
//				treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//			}
//			else {
//				//Ʈ������ ���� ����
//				treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//			}
//			maketable(file);		//file�� ���̺� �ʱ�ȭ
//		} catch(Exception ex) {		//����ó��
//			JOptionPane.showMessageDialog(null, "table���� ������ ��� ����/������ �����ϼ���.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	
//	void m31() {		//�޴��� '�̸��� ����'��� ���� �Լ�
//		if(reallocate.equals("")) {		//��Ʈ��ġ�� ���
//			sort = true;		//sort�� true�� ����
//			return;				//�Լ� ����
//		}
//		File file = new File(reallocate);		//������ġ�� ���� ����
//		sort = true;							//sort�� true�� ����
//		maketable(file);						//file�� ���̺� �ʱ�ȭ
//	}
//	
//	void m32() {		//�޴��� '�ֽż� ����'��� ���� �Լ�
//		if(reallocate.equals("")) {				//������ġ�� ��Ʈ��ġ�� ���
//			sort = false;						//sort�� false�� ����
//			return;								//�Լ� ����
//		}
//		File file = new File(reallocate);		//������ġ�� file����
//		sort = false;							//sort�� false�� ����
//		maketable(file);						//file�� ���̺� ��ȭ
//	}
//	
//	void m33() {		//�޴��� '�˻�'��� ���� �Լ�
//		String name = JOptionPane.showInputDialog("�˻��� �̸��� �Է��ϼ���.");		//��â���� �˻��� �̸� �Է�
//		if(name.equals("")) {													//�Է¹��� �̸��� ������ ���
//			//�����޼��� ���
//			JOptionPane.showMessageDialog(null, "�˻��� �̸��� �Է����� �ʾҽ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		File file = new File(reallocate);		//������ġ�� file ����
//		filter = name;							//filter�� �˻��� �̸����� ����
//		maketable(file);						//file�� ���̺� �����
//		filter = "";							//fileter �ʱ�ȭ
//	}
//	
//	void m41() {		//�޴��� '�����ּ� ����'��� ���� �Լ�
//		new MailAdrUpdateFrame();
//	}
//	
//	void m42() {		//�޴��� '���� ������'��� ���� �Լ�
//		new MailSendFrame();
//	}
//	
//	void preView() {		//�̸����� ����� ������ �Լ�
//		previewpane.removeAll();			//�̸����� �г��� ���� ����
//		try {
//			if(model.getValueAt(table.getSelectedRow(), 2).equals("txt")) {		//���õ� ������ Ȯ���ڰ� txt���
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {		//������ġ�� ��Ʈ ���� ��ġ�ų� ��Ʈ���
//					//���õ� ���Ϸ� PreviewText�� ����� previewpane�� �߰�
//					previewpane.add(new PreviewText(reallocate + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//				else {
//					//���õ� ���Ϸ� PreviewText�� ����� previewpane�� �߰�
//					previewpane.add(new PreviewText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0)));
//				}	
//			}
//			else {
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {		//������ġ�� ��Ʈ ���� ��ġ�ų� ��Ʈ���
//					//���õ� ���Ϸ� PreviewImage�� ����� previewpane�� �߰�
//					previewpane.add(new PreviewImage(reallocate + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//				else {
//					//���õ� ���Ϸ� PreviewImage�� ����� previewpane�� �߰�
//					previewpane.add(new PreviewImage(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//			}
//		} catch(Exception ex) {}
//		previewpane.updateUI();		//�̸����� �г� ����
//	}
//	
//	public static void main(String args[]) {		//�����Լ�
//		new Seeker();							//�ƻ��� ����
//	}
//	
//	void makeroottable() {			//��Ʈ���̺� ����
//		File subfile[] = File.listRoots();			//listRoots()�� subfile����
//		String tempr[] = { "", "", "", "" };		//���ڿ� �迭 tempr ����
//		//��¥ �ڷ����� sd ����
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//		sortfile(subfile);		//subfiel ����
//		while(model.getRowCount() != 0) {		//���̺� ����
//			model.removeRow(0);
//		}
//		for(int i = 0; i < subfile.length; i++) {		//�������� ���̸�ŭ �ݺ�
//			if(subfile[i].exists()) {					//�ش� subfile�� �����Ѵٸ�
//				tempr[0] = subfile[i].getPath();		//�ش� subfile���� �̸� ����
//				//subfile���� ������ ��¥ ����
//				tempr[3] = sdf.format(new Date(subfile[i].lastModified()));
//				model.addRow(tempr);					//���̺� tempr �߰�
//			}
//		}
//		table.updateUI();				//�׾ƺ� ����
//	}
//	
//	void maketable(File file) {			//���̺� ����
//		File subfile[] = file.listFiles();			//listfile()�� subfile����
//		String tempr[] = { "", "", "", "" };		//���̺� �� �ٷ� �� ���ڿ� �迭 tempr ����
//		//��¥�ڷ��� sdf ����
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//		sortfile(subfile);					//subfile ����
//		while(model.getRowCount() != 0) {	//���̺� ���� ����
//			model.removeRow(0);
//		}
//		for(int i = 0; i < subfile.length; i++) {		//�������� ��ŭ �ݺ�
//			if(!subfile[i].isHidden() && searchfilter(subfile[i].getName())) {		//�ش� ���������� �����������ʰų� ���Ϳ� ����Ǿ��ٸ�
//				tempr[0] = subfile[i].getName();		//�ش� ���������� �̸��� ���ؼ� temp[0]�� �Է�
//				if(subfile[i].isFile())					//�ش缷������ �����̶��
//					tempr[1] = subfile[i].length()+"byte";		//ũ�⸦ ���ؼ� tmep[1]�� �Է�
//				else
//					tempr[1] = "";				//temp[1]�� ����
//				if(subfile[i].isDirectory())		//�ش� ���������� �������
//					tempr[2] = "����";				//tmepr[2]�� "����"�� ����
//				else {
//					String temps[] = subfile[i].getName().split("\\.");			//�ش� ������ Ȯ���ڸ� ���ؼ�
//					if(temps.length > 1)
//						tempr[2] = temps[temps.length-1];		//tempr[2]�� �ش� ������ Ȯ���ڷ� ����
//					else
//						tempr[2] = "����";						//Ȯ���ڰ� ������ tempr[2]�� "����"�� ����
//				}
//				tempr[3] = sdf.format(new Date(subfile[i].lastModified()));		//������ ��¥�� ���ؼ� tempr[3]�� ����
//				model.addRow(tempr);		//���̺� �߰�
//			}
//		}
//		table.updateUI();				//���̺� ����
//	}
//	
//	void sortfile(File[] subfile) {		//subfile�� �Է¹޾Ƽ� ���ĵ� ���������� ��ȯ�ϴ� �Լ�
//		File temp;
//		if(sort == false){			//subfile�� �⺻�� �̸��� ���������� �ֽż� ������ ��쿡�� ������ �����Ѵ�
//			for(int i = 0; i < subfile.length; i++) {				//�������� ���̸�ŭ �ݺ�
//				for(int j = i; j < subfile.length; j++) {			//�������� ���̸�ŭ �ݺ�
//					//i��° subfile���� j���� subfile�� ������ ��¥�� �� ũ�ٸ� ���� �ٲ۴�
//					if(subfile[j].lastModified() > subfile[i].lastModified()) {
//						temp = subfile[i];
//						subfile[i] = subfile[j];
//						subfile[j] = temp;
//					}
//				}
//			}
//		}
//	}
//	
//	boolean searchfilter(String fname) {			//fname(�̸�)�� �Է¹޾Ƽ� ����filter�� ��
//		if(fname.startsWith(filter))		//fname�� filter�� �����Ѵٸ�
//			return true;					//true ��ȯ
//		else if(fname.endsWith(filter))		//fname�� filter�� ������
//			return true;					//true ��ȯ
//		
//		if(!filter.equals("")){				//filter�� ""�� �ƴ϶��(�� ���Ͱ��� �����Ǿ� �ִٸ�)
//			String token[] = fname.split(filter);		//fname�� filter�� �������� �и�
//			if(token.length == 1)			//�и��� fname�� 1�����̶��(�� fname�� filter�� ���Ե��� �ʴٸ�)
//				return false;				//false ��ȯ
//			return true;					//true ��ȯ
//		}
//		return true;						//true ��ȯ
//	}
//	
//	//��� �߰� �Լ�
//	//�� ������ �߰��� �ڿ�  Ʈ������ �߰��� �ֱ� ���� ���
//	void treeadd(String locate) {
//		boolean succes = true;						//���� ���θ� ��Ÿ���� succes ���� true�� �ʱ�ȭ
//		String token[] = locate.split("\\\\");		//locate�� "\\"�������� �и�
//		DefaultMutableTreeNode temp = root;			//�ӽ� ��� temp ����, root�� �ʱ�ȭ
//		token[0] += "\\";							//ù��° ��ū�� '\' �߰� (ex: C �� C\�� �ٲ��ֱ� ����)
//		//�ش� ��ġ�� ����� �ٷ� ���� ��带 �˻�
//		for(int i = 0; i < token.length-1; i++) {		//��ū���̺��� �ϳ� �۰� �ݺ�
//			for(int j = 0; j < temp.getChildCount(); j++) {				//temp�� �ڽ� ������ŭ �ݺ�
//				if(temp.getChildAt(j).toString().equals(token[i])) {	//j���� temp�� �ڽ��� i��° ��ū�� ���ٸ�
//					temp = (DefaultMutableTreeNode) temp.getChildAt(j);	//temp�� temp�� j��° �ڽ����� ����
//					break;												//�ڽ��� Ȯ���� �ߴ��ϰ� ���� ��ū Ȯ��
//				}
//				else if(j == temp.getChildCount()-1) {					//
//					succes = false;
//					break;
//				}
//			}
//			if(!succes) break;
//		}
//		if(succes) {		
//			//�ش� ��ġ�� �ٷ� ���� ��尡 ��������� "�������"��� ����
//			if(temp.getChildAt(0).toString().equals("�������")) temp.remove(0);
//			DefaultMutableTreeNode child = new DefaultMutableTreeNode(token[token.length-1]);	//���� �߰��� ��� ����
//			child.add(new DefaultMutableTreeNode("�������"));		//�� ��忡 "�������"��� �߰�
//			temp.add(child);			//������忡 �� ��� �߰�
//			tree.updateUI();			//Ʈ�� ����
//		}
//	}
//	
//	//������ �Լ�
//	//���� ������ ������ �Ŀ� ��������� Ʈ������ �����ϱ� ���� �Լ�
//	//�� �Լ��� ���
//	void treedel(String locate) {
//		boolean succes = true;
//		String token[] = locate.split("\\\\");
//		DefaultMutableTreeNode temp = root;
//		token[0] += "\\";
//		//�ش� ��� �ٷ� ���� ��带 �˻�
//		for(int i = 0; i < token.length-1; i++) {
//			for(int j = 0; j < temp.getChildCount(); j++) {
//				if(temp.getChildAt(j).toString().equals(token[i])) {
//					temp = (DefaultMutableTreeNode) temp.getChildAt(j);
//					break;
//				}
//				else if(j == temp.getChildCount()-1) {
//					succes = false;
//					break;
//				}
//			}
//			if(!succes) break;
//		}
//		//�ش� ��带 ����
//		if(succes) {
//			//�ش� ��ġ�� �ڽĵ� �߿� �ش� ���� ���� �̸��� ���� ����
//			for(int i = 0; i < temp.getChildCount(); i++) {
//				if(temp.getChildAt(i).toString().equals(token[token.length-1])) {
//					temp.remove(i);
//				}
//			}
//			//���� �� �ش� ��ġ�� �ƹ� ���ϵ� ���ٸ� "�������"��� �߰�
//			if(temp.getChildCount() == 0) temp.add(new DefaultMutableTreeNode("�������"));
//			tree.updateUI();		//Ʈ�� ����
//		}
//	}
//	
//	//���� ���� �Լ�
//	//���������� �ִ� ������� ������ ������� �ʱ⶧���� ��� �������Ͽ����� ��������� ����� ����
//	void deletefile(File selfile) {
//		if(selfile.isDirectory()) {	//���õ� ������ �������
//			File subselfile[] = selfile.listFiles();	//���õ� ������ �������Ϸ� �迭 ����
//			for(int i = 0; i < subselfile.length; i++) {		//��� �������Ͽ� ����
//				deletefile(subselfile[i]);				//���� ������ ����
//			}
//		}
//		selfile.delete();	//���õ� ���� ����
//	}
//	
//	//Ʈ��Ȯ�� �̺�Ʈ�� ��忡�� �ּ� ����
//	//getPath().toString()�� Ʈ������ ���õ� ������ ��ġ�� ��� ��带 ,�� ����� ǥ�� ���� �ּ����·� ��ȯ�� �ʿ䰡 �ִ�.
//	//getPath().toString()�� ���ڿ� ���۰� �������� '[', ']'�� ���� ������ ����
//	//���� Ʈ���� ��Ʈ���� "����ǻ��"�� �ּ�ȭ�� �ʿ� ����.
//	String getPath(TreeExpansionEvent e) {
//		String temp = "";		//�ӽ� ���ڿ� temp
//		String tempsplit[] = e.getPath().toString().split(", ");	//���õ� ��带 ���ڿ��� �ٲپ�','�������� �и�
//		//ù����(��Ʈ���)�� ������ ��带 ������ ��� ��忡�� �ݺ� �и��� ���ڿ� ���̿�'\'�� �־ �ּ����·� ��ȯ
//		for(int i = 1; i < tempsplit.length - 1; i++) {
//			if(i == 1) temp += tempsplit[i];
//			else temp += (tempsplit[i]+"\\");
//		}
//		temp += e.getPath().getLastPathComponent();	//������ ��带 �߰�
//		return temp;		//�ּ� ��ȯ
//	}
//	
//	String getPath(TreeSelectionEvent e) {	//Ʈ������ �̺�Ʈ�� ��忡�� �ּ� ���� , ���Լ��� ���� ����
//		String temp = "";
//		String tempsplit[] = e.getPath().toString().split(", ");
//		for(int i = 1; i < tempsplit.length - 1; i++) {
//			if(i == 1) temp += tempsplit[i];
//			else temp += (tempsplit[i]+"\\");
//		}
//		temp += e.getPath().getLastPathComponent();
//		return temp;
//	}
//}