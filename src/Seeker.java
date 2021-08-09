////개발프로젝트 6팀 탐색기
////
////2013041024 김찬수
////코드병합 Seeker클래스 centerpane 파일 입출력 함수 로그인 기능 관련 클래스&메소드
////클래스 : Seeker MyActionListener MyKeyAdapter MyMouseAdapter
////메소드 : Seeker() makeCenter() m11~33()
////makeroottable() maketable(File) sortfile(File[]) searchfilter(String)  deleteFile(File)
////클래스 : User UserFrame MyUserActionListener
////메소드 : User() updateData() updateVector()
////UserFrame() makeuserCpane() makeuserSpane() 
////login() logout() userCreate() userUpdate() userDelete() userFrameClose()
////
////2015041077 홍진빈
////westpane menubar 파일트리 관련 함수
////클래스 : MyTreeSelectionListener MyTreeWillExpandListener
////메소드 : makeWest() makeMenu()
////getPath(TreeExpansionEvent e) getPath(TreeSelectEvent e) treeadd(String) treedel(String)
////
////2017038006 이정수
////eastpane northpane 즐겨찾기, 미리보기 관련 함수
////클래스 : PreviewImage PreviewText
////메소드 : makeEast() makeNorth() preView()
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
//	boolean sort = true;			//정렬 타입 (true면 이름순 검색)
//	String reallocate = "";			//현재 열려있는 디렉토리의 주소를 저장할 변수
//	String filter = "";				//검색할 키워드
//	JTextField locatetext = new JTextField(58);		//주소입력필드
//	JPanel previewpane = new JPanel();				//미리보기 패널
//	JTree tree;										//좌측 파일트리
//	DefaultMutableTreeNode root = new DefaultMutableTreeNode("내컴퓨터");	//트리에 쓰일 루트
//	Vector likevector = new Vector();		//우측 즐겨찾기에 표시되는 이름백터
//	Vector realvector = new Vector();		//우측 즐겨찾기의 실제 주소백터
//	JList likelist = new JList(likevector);	//우측 즐겨찾기의 리스트
//	String colName[] = { "파일명", "크기", "종류", "수정한 날짜" };			//table모델의 헤드에 들어갈 목록
//	DefaultTableModel model = new DefaultTableModel(colName, 0) {	//table에들어갈 모델
//		public boolean isCellEditable(int row, int column) {		//수정 불가능하게 변경
//			return false;
//		}
//	};
//	JTable table = new JTable(model);		//중앙에 들어갈 table
//	
//	JPanel northpane = new JPanel();		//위쪽에 들어갈 패널
//	JScrollPane westpane = null;			//왼쪽에 들어갈 패널
//	JPanel eastpane = new JPanel();			//오른쪽에 들어갈 패널
//	JPanel centerpane = new JPanel();		//가운데 들어갈 패널
//	
//	User NowUser = new User();				//현재 로그인 중인 사용자
//	
//	static final String id = "root";		//DB 계정
//	static final String pass = "asdf";		//DB 암호
//	//DB 주소
//	String url = "jdbc:mysql://localhost:3306/6team_Seeker?serverTimezone=UTC";
//	
//	//엑션리스너
//	//버튼 메뉴아이템의 선택과 각각의 함수를 연동
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
//			if(b.getActionCommand() == "추가") addLike();
//			else if(b.getActionCommand() == "삭제") delLike();
//			else if(b.getActionCommand() == "...") moveUp();
//			else if(b.getActionCommand() == "이동") moveLocate();
//			
//			if(m.getActionCommand() == "새 창 열기") m11();
//			else if(m.getActionCommand() == "사용자") m12();
//			else if(m.getActionCommand() == "닫기") m13();
//			else if(m.getActionCommand() == "새폴더") m21();
//			else if(m.getActionCommand() == "이름바꾸기") m22();
//			else if(m.getActionCommand() == "이동") m23();
//			else if(m.getActionCommand() == "삭제") m24();
//			else if(m.getActionCommand() == "이름순 정렬") m31();
//			else if(m.getActionCommand() == "최신순 정렬") m32();
//			else if(m.getActionCommand() == "검색") m33();
//			else if(m.getActionCommand() == "메일주소 수정") {
//				if(!NowUser.ID.equals("")) {
//					m41();
//				} else {
//					JOptionPane.showMessageDialog(null, "로그인 해 주세요", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//			else if(m.getActionCommand() == "메일 보내기") {
//				if(!NowUser.ID.equals("")) {
//					m42();
//				} else {
//					JOptionPane.showMessageDialog(null, "로그인 해 주세요", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}
//	}
//	
//	//트리선택리스너
//	//westpane의 트리노드를 선택했을 때 해당 위치로 이동하도록 연동
//	class MyTreeSelectionListener implements TreeSelectionListener {
//		public void valueChanged(TreeSelectionEvent e) {
//			try {
//				//루트노드를 선택했을 때
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")) {
//					reallocate = "";		//현재위치 초기화
//					locatetext.setText("내컴퓨터");	//주소창에 "내컴퓨터" 출력
//					makeroottable();		//루트테이블로 초기화
//				}
//				else {	//루트노드가 아닌 다른 노드를 선택했을 경우
//					File file = new File(getPath(e));	//선택된 노드의 주소로 file 생성
//					if(file.isDirectory()) { maketable(file); }		//해당 파일이 폴더라면 테이블 초기화
//					else {	//해당파일이 폴더가 아니라면
//						//테이블 삭제
//						while(model.getRowCount() != 0) {
//							model.removeRow(0);
//						}
//						table.updateUI(); //변경된 UI 적용
//					}
//					locatetext.setText(getPath(e));		//주소창 변경
//					reallocate = getPath(e);			//현재 주소 변경
//				}
//			} catch(Exception ex) {		//예외처리
//				JOptionPane.showMessageDialog(null, "접근을 허용하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyTreeWillExpandListener implements TreeWillExpandListener {			//트리확장리스너
//		public void treeWillCollapse(TreeExpansionEvent e)		//트리가 닫힐 때
//				throws ExpandVetoException {
//			try {
//				//루트 노드일 경우
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")){}
//				else {
//					//선택된 노드로 parent 노드 생성
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
//					while(parent.getChildCount() != 0) {		//해당 노드의 자손 개수만큼
//						parent.remove(0);						//자손 노드를 삭제
//					}
//					parent.add(new DefaultMutableTreeNode("비어있음"));		//parent노드에 "비어있음"노드 추가
//				}
//			} catch(Exception ex) {		//예외처리
//				JOptionPane.showMessageDialog(null, "접근을 허용하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//
//		public void treeWillExpand(TreeExpansionEvent e)		//트리가 확장 될 때
//				throws ExpandVetoException {
//			try {
//				//루트노드일 경우
//				if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")){}
//				else {
//					File file = new File(getPath(e));		//선택된 노드로 file 생성
//					File subfile[] = file.listFiles();		//file로 subfile 배열 생성
//					//선택된 노드로 parent 노드 생성
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
//					DefaultMutableTreeNode tempn;			//임시노드 tempn 생성
//					//parent에 subfile만큼 자식노드 추가
//					for(int i = 0; i < subfile.length; i++) {								//subfile 길이만큼 반복
//						if(!subfile[i].isHidden()) {										//해당 sub파일이 숨김상태가 아니면
//							tempn = new DefaultMutableTreeNode(subfile[i].getName());		//tempn에 i번쨰 subfile 노드 추가
//							if(subfile[i].isDirectory())									//해당 노드가 폴더일 경우
//								tempn.add(new DefaultMutableTreeNode("비어있음"));				//"비어있음"노드 추가
//							parent.add(tempn);												//parent에 tempn 추가
//						}
//					}
//					if(parent.getChildCount() != 1)		//노드가 비어있지 않으면
//						parent.remove(0);				//첫번쨰 자식노드("비어있음"노드) 삭제
//				}
//			} catch(Exception ex) {			//예외처리
//				JOptionPane.showMessageDialog(null, "접근을 허용하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyListSelectionListener implements ListSelectionListener {		//리스트 선택 리스너
//		public void valueChanged(ListSelectionEvent e) {
//			try {
//				if(!e.getValueIsAdjusting()) {
//					//선택된 리스트의 인덱스를 구해 realvector의 해당 주소로 file생성
//					File file = new File(realvector.get(likelist.getSelectedIndex())+"");
//					//선택된 리스트의 인덱스를 구해 realvector의 해당 주소로 현재주소(reallocate) 변경
//					reallocate = realvector.get(likelist.getSelectedIndex())+"";
//					locatetext.setText(reallocate);		//reallocate로 주소창 변경
//					maketable(file);					//file로 테이블 생성
//				}
//			} catch(Exception ex) {			//예외처리
//				JOptionPane.showMessageDialog(null, "접근을 허용하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class MyMouseAdapter extends MouseAdapter {		//마우스 어뎁터
//		public void mouseClicked(MouseEvent e) {		//마우스 키를 누를때
//			preView();									//미리보기 실행
//			if(e.getClickCount() == 2) {				//더블클릭일 경우
//				//주소창의 주소를 테이블의 선택된 파일로 바꾸고
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {
//					locatetext.setText(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//				}
//				else {
//					locatetext.setText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				}
//    			moveLocate();			//이동 기능 실행
//    		}
//		}
//	}
//	
//	class MyKeyAdapter extends KeyAdapter {		//키 어뎁터
//		public void keyPressed(KeyEvent e) {		//키를 누를때
//			try {
//				if(e.getKeyCode() == 10) {		//키가 "Enter" 라면
//					//주소창의 주소를 테이블의 선택된 파일로 바꾼다.
//					if(reallocate.endsWith("\\") || reallocate.equals("")) {
//						locatetext.setText(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						locatetext.setText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					moveLocate();		//이동 기능 실행
//				}
//				else if(e.getKeyCode() == 8) {		//키가 "Backspace" 라면
//					moveUp();
//				}
//			} catch(Exception ex) {
//				JOptionPane.showMessageDialog(null, "table에서 열거나 이동할 대상 파일/폴더를 선택하세요.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//		
//		public void keyReleased(KeyEvent e) {		//키를 땔때
//			preView();								//미리보기 실행
//		}
//	}
//	
//	class PreviewImage extends JPanel {		//미리보기할 이미지 출력할 패널
//		Image image;
//		
//		PreviewImage(String loc) {
//			setPreferredSize(new Dimension(125, 125));		//크기설정
//			setBackground(Color.WHITE);						//배경색 설정
//			
//			image = Toolkit.getDefaultToolkit().getImage(loc);	
//			
//		}
//		
//		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);	//이미지 그리기
//		}
//	}
//	
//	class PreviewText extends JTextArea {		//미리보기할 텍스트 출력패널
//		PreviewText(String loc) {
//			setEditable(false);							//수정불가능하게 설정
//			setPreferredSize(new Dimension(125, 125));	//크기설정
//			setBackground(Color.WHITE);					//배경색 WHITE로 설정
//			try {
//				FileReader fr = new FileReader(loc);	//매개변수loc의 주소로 FileReader생성
//				String intext = "";						//출력할 텍스트
//				int c, count = 0, linecount = 0;		//현재 문자, 열, 행을 표현할 변수
//				
//				//한 문자를 읽어와서 c에 저장하고 그게 끝이 아니거나 줄이 7번쨰줄 이상이 아니면 반복
//				while((c = fr.read()) != -1 && linecount < 7) {
//					if(c == 13) {			//현재 문자가 줄바꿈이면
//						linecount ++;		//행변수 하나 증가
//						count = -2;			//열변수 초기화
//					}
//					else if(count == 17) {	//그 줄에서 17번째 문자이면
//						intext += "\n";		//띄어쓰기 추가
//						linecount++;		//행변수 하나 증가
//						count = 0;			//열변수 초기화
//					}
//					if(count == -2)			//열변수가 -2이면 
//						intext += "\n";	//출력할 텍스트에 '\n'추가
//					else if(count != -1)	//열변수가 -1이 아니면 
//						intext += (char)c;	//출력할 텍스트에 c추가
//					count++;				//열변수 하나 증가
//				}
//				setText(intext);			//intext를 출력
//				fr.close();					//fr닫기
//			} catch(IOException ex) {		//예외처리
//				//오류메세지 "입출력 오류" 발생
//				JOptionPane.showMessageDialog(null, "입출력 오류", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//	
//	class User {
//		String ID;				//사용자의 ID
//		String passWord;		//사용자의 비밀번호
//		String name;			//사용자의 이름
//		String mailAdr;			//사용자의 메일주소
//		String mailPass;		//사용자의 메일암호
//		
//		//기본 생성자
//		User(){
//			ID = "";
//			passWord = "";
//			name = "";
//			mailAdr = "";
//			mailPass = "";
//		}
//		
//		//정보수정
//		void updateData(String ID, String passWord, String name){
//			this.ID = ID;
//			this.passWord = passWord;
//			this.name = name;
//			setTitle("6팀 탐색기 - " + NowUser.ID);
//		}
//		
//		//즐겨찾기수정
//		void updateVector(String ulv, String urv) {
//			//즐겨찾기 백터 초기화
//			for(int j=likevector.size(); j>0; j--) {
//				likevector.remove(0);
//				realvector.remove(0);
//			}
//			//이름 백터 재구성
//			if(!ulv.equals("[]")) {
//				String templ1[] = ulv.split("\\[");
//				String templ2[] = templ1[1].split("\\]");
//				String templ[] = templ2[0].split(", ");
//				
//				for(int i=0; i<templ.length; i++) {
//					likevector.add(templ[i]);
//				}
//			}
//			//실제 주소 백터 재구성
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
//			likelist.updateUI();	//변경된 UI 적용
//		}
//		
//		//메일정보 수정
//		void updateMail(String mailAdr, String mailPass) {
//			this.mailAdr = mailAdr;
//			this.mailPass = mailPass;
//		}
//	}
//	
//	public class UserFrame extends JFrame {
//		JTextField IDText = new JTextField(10);		//ID입력필드
//		JTextField passText = new JTextField(10);		//password입력필드
//		JTextField nameText = new JTextField(10);		//이름입력필드
//		
//		JPanel userCpane = new JPanel();		//사용자정보에 들어갈 중간 패널
//		JPanel userSpane = new JPanel();		//사용자정보에 들어갈 아래쪽 패널
//		
//		UserFrame(){
//			setTitle("사용자 정보 - " + NowUser.ID);					//타이틀 설정
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//종료시 모든 프레임만 졸료되도록 설정
//			Container con = getContentPane();		//컨텐트 팬 가져오기
//			con.setLayout(new BorderLayout());		//레이아웃을 BorderLayout으로 설정
//			
//			makeuserCpane();
//			makeuserSpane();
//			
//			userSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane 크기설정
//			
//			con.add(userCpane, "Center");
//			con.add(userSpane, "South");
//			
//			setSize(400, 275);			//크기설정
//			setVisible(true);			//화면에 출력
//		}
//		
//		void makeuserCpane(){
//			JLabel IDLabel = new JLabel("    I      D");		//ID 레이블 생성
//			JLabel passLabel = new JLabel("password");		//password 레이블 생성
//			JLabel nameLabel = new JLabel("    이    름");		//name 레이블 생성
//			
//			JLabel userIDLabel = new JLabel(NowUser.ID);			//사용자 ID 레이블 생성
//			JLabel userPassLabel = new JLabel(NowUser.passWord);		//사용자 password 레이블 생성
//			JLabel userNameLabel = new JLabel(NowUser.name);		//사용자 name 레이블 생성
//			
//			//ID, password, 이름 레이블의 크기 설정
//			IDLabel.setPreferredSize(new Dimension(80, 22));
//			passLabel.setPreferredSize(new Dimension(80, 22));
//			nameLabel.setPreferredSize(new Dimension(80, 22));
//			
//			//사용자의 ID, password, 이름 레이블의 크기 설정
//			userIDLabel.setPreferredSize(new Dimension(114, 22));
//			userPassLabel.setPreferredSize(new Dimension(114, 22));
//			userNameLabel.setPreferredSize(new Dimension(114, 22));
//			
//			userCpane.add(IDLabel);				//'ID'레이블 등록
//			userCpane.add(IDText);					//ID입력필드 등록
//			userCpane.add(userIDLabel);					//ID입력필드 등록
//			userCpane.add(passLabel);			//'passworld'레이블 등록
//			userCpane.add(passText);					//password입력필드 등록
//			userCpane.add(userPassLabel);					//ID입력필드 등록
//			userCpane.add(nameLabel);			//'이름'레이블 등록
//			userCpane.add(nameText);					//이름입력필드 등록
//			userCpane.add(userNameLabel);					//ID입력필드 등록
//			
//			if(NowUser.ID.equals("")) {
//				userCpane.add(new JLabel("1. 로그인 : ID와 password를 입력하고 로그인 버튼을 클릭"));
//				userCpane.add(new JLabel("2. 회원가입 : 모든 정보를 입력하고 회원가입 버튼을 클릭"));
//				userCpane.add(new JLabel("    중복된 ID의 계정을 생성할 수는 없음"));
//			}
//			else {
//				userCpane.add(new JLabel("1. 로그아웃 : 로그아웃 버튼을 클릭"));
//				userCpane.add(new JLabel("2. 수정 : 모든 데이터를 입력하고 수정 버튼을 클릭"));
//				userCpane.add(new JLabel("    ID를 공란으로 둘 경우 회원탈퇴 수행"));
//			}
//			userCpane.add(new JLabel("3. 닫기 : 어떤 행동도 하지않고 창을 닫음"));
//			
//		}
//		
//		void makeuserSpane(){
//			if(NowUser.ID.equals("")) {
//				JButton login = new JButton("로그인");		//'로그인' 버튼 생성
//				JButton userCreate = new JButton("회원가입");		//'회원가입' 버튼 생성
//				login.addActionListener(new MyUserActionListener());		//로그인 버튼 엑션리스너 등록
//				userCreate.addActionListener(new MyUserActionListener());		//회원가입 버튼 엑션리스너 등록
//				
//				userSpane.add(login);			//'로그인'버튼 등록
//				userSpane.add(userCreate);			//'회원가입'버튼 등록
//			}
//			else {
//				JButton logout = new JButton("로그아웃");		//'로그아웃' 버튼 생성
//				JButton userUpdate = new JButton("수정");		//'회원가입' 버튼 생성
//				logout.addActionListener(new MyUserActionListener());		//로그아웃 버튼 엑션리스너 등록
//				userUpdate.addActionListener(new MyUserActionListener());		//수정 버튼 엑션리스너 등록
//				
//				userSpane.add(logout);			//'로그아웃'버튼 등록
//				userSpane.add(userUpdate);			//'수정'버튼 등록
//			}
//			JButton userFrameClose = new JButton("닫기");		//닫기 버튼 생성
//			userFrameClose.addActionListener(new MyUserActionListener());		//닫기 버튼 엑션리스너 등록
//			
//			userSpane.add(userFrameClose);			//'닫기'버튼 등록
//		}
//		
//		//사용자프레임의 엑션리스너
//		//사용자프레임의 버튼과 각각의 함수를 연동
//		class MyUserActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "로그인") login();
//				else if(b.getActionCommand() == "로그아웃") logout();
//				else if(b.getActionCommand() == "회원가입") {
//					if(!IDText.getText().equals("")) userCreate();
//					else JOptionPane.showMessageDialog(null, "ID를 입력하세요.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//				else if(b.getActionCommand() == "수정") {
//					if(!IDText.getText().equals("")) userUpdate();
//					else userDelete();
//				}
//				else if(b.getActionCommand() == "닫기") userFrameClose();
//			}
//		}
//		
//		//로그인한다.
//		//DB에서 사용자 정보를 불러온다.
//		void login() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB연동
//				//DB에서 ID 검색
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				rs = pstmt.executeQuery();
//				
//				if(rs.next()) {			//DB에 ID가 존재할 경우
//					if(rs.getString("user_pass").equals(passText.getText())) {		//ID와 password가 일치할 경우
//						NowUser.updateData(IDText.getText(), passText.getText(), rs.getString("user_name"));
//						NowUser.updateVector(rs.getString("user_lv"), rs.getString("user_rv"));
//						NowUser.updateMail(rs.getString("user_mail_adr"), rs.getString("user_mail_pass"));
//						userFrameClose();
//					}
//					else {			//경고메세지 출력
//						JOptionPane.showMessageDialog(null, "암호가 맞지 않습니다..", "Message", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "ID가 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				}						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		//로그아웃한다.
//		void logout() {
//			NowUser.updateData("", "", "");
//			NowUser.updateVector("[]", "[]");
//			userFrameClose();
//		}
//		
//		//새로운 사용자를 등록한다.
//		//DB에도 등록한다.
//		void userCreate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB연동
//				//DB에서 ID 검색
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				
//				if(!pstmt.executeQuery().next()) {		//DB에 같은 ID가 없을 경우
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
//				else {			//경고메세지 출력
//					JOptionPane.showMessageDialog(null, "같은 ID가 존재합니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		//사용자 정보를 수정한다.
//		//DB에서도 수정한다.
//		void userUpdate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB연동
//				//DB에서 ID 검색
//				String query = "select * from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, IDText.getText());
//				
//						
//				if(!pstmt.executeQuery().next() || NowUser.ID.equals(IDText.getText())) {		//DB에 같은 ID가 없을 경우
//					//DB에 정보 수정
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
//				else {			//경고메세지 출력
//					JOptionPane.showMessageDialog(null, "같은 ID가 존재합니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				}						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		//DB에서 사용자데이터를 삭제한다.
//		//로그아웃한다.
//		void userDelete() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB연동
//				//DB에서 ID 검색
//				String query = "delete from userlist where user_ID = ?";
//				pstmt = conn.prepareStatement(query);
//				pstmt.setString(1, NowUser.ID);
//				pstmt.executeUpdate();
//				
//				logout();						
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		//사용자 창을 닫는다.
//		void userFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	public class MailAdrUpdateFrame extends JFrame {
//		JTextField mailAdrText = new JTextField(10);		//메일주소입력필드
//		JTextField mailPassText = new JTextField(10);		//메일password입력필드
//		
//		JPanel mailCpane = new JPanel();		//메일수정에 들어갈 중간 패널
//		JPanel mailSpane = new JPanel();		//메일수정에 들어갈 아래쪽 패널
//		
//		MailAdrUpdateFrame() {
//			setTitle("메일 수정 - " + NowUser.ID);					//타이틀 설정
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//종료시 모든 프레임만 졸료되도록 설정
//			Container con = getContentPane();		//컨텐트 팬 가져오기
//			con.setLayout(new BorderLayout());		//레이아웃을 BorderLayout으로 설정
//			
//			makemailCpane();
//			makemailSpane();
//			
//			mailSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane 크기설정
//			
//			con.add(mailCpane, "Center");
//			con.add(mailSpane, "South");
//			
//			setSize(400, 150);			//크기설정
//			setVisible(true);			//화면에 출력
//		}
//		
//		void makemailCpane(){
//			JLabel mailAdrLabel = new JLabel("메일주소");			//메일주소 레이블 생성
//			JLabel mailPassLabel = new JLabel("password");		//메일 password 레이블 생성
//			
//			JLabel userMailAdrLabel = new JLabel(NowUser.mailAdr);			//사용자 메일주소 레이블 생성
//			JLabel userMailPassLabel = new JLabel(NowUser.mailPass);		//사용자 메일 password 레이블 생성
//			
//			//메일주소, password, 이름 레이블의 크기 설정
//			mailAdrLabel.setPreferredSize(new Dimension(80, 22));
//			mailPassLabel.setPreferredSize(new Dimension(80, 22));
//			
//			//사용자의 메일주소, password, 이름 레이블의 크기 설정
//			userMailAdrLabel.setPreferredSize(new Dimension(114, 22));
//			userMailPassLabel.setPreferredSize(new Dimension(114, 22));
//			
//			mailCpane.add(mailAdrLabel);				//'메일주소'레이블 등록
//			mailCpane.add(mailAdrText);					//메일주소입력필드 등록
//			mailCpane.add(userMailAdrLabel);					//사용자메일주소 레이블 등록
//			mailCpane.add(mailPassLabel);			//'passworld'레이블 등록
//			mailCpane.add(mailPassText);					//password입력필드 등록
//			mailCpane.add(userMailPassLabel);					//사용자 password 레이블 등록
//		}
//		
//		void makemailSpane(){
//			JButton mailUpdate = new JButton("수정");		//'수정' 버튼 생성
//			JButton mailFrameClose = new JButton("닫기");		//닫기 버튼 생성
//			mailUpdate.addActionListener(new MyMailActionListener());		//메일수정 버튼 엑션리스너 등록
//			mailFrameClose.addActionListener(new MyMailActionListener());		//닫기 버튼 엑션리스너 등록
//
//			mailSpane.add(mailUpdate);			//'메일수정'버튼 등록
//			mailSpane.add(mailFrameClose);			//'닫기'버튼 등록
//		}
//		
//		//메일수정 프레임의 엑션리스너
//		//메일수정 프레임의 버튼과 각각의 함수를 연동
//		class MyMailActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "수정") mailAdrUpdate();
//				else if(b.getActionCommand() == "닫기") mailFrameClose();
//			}
//		}
//		
//		//메일주소 수정
//		//DB에서도 수정
//		void mailAdrUpdate() {
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try {
//				conn = DriverManager.getConnection(url,id,pass);		//DB연동
//				
//				//DB에 정보 수정
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
//				JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		//메일수정 창을 닫는다
//		void mailFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	//메일을 보내는 프레임
//	public class MailSendFrame extends JFrame {
//		JTextField mailTitleText = new JTextField(15);		//메일제목 입력필드
//		JTextArea mailText = new JTextArea();			//메일내용 입력공간
//		JTextField mailToText = new JTextField(15);		//메일받는이 입력필드
//		JTextField mailFileText = new JTextField(15);		//첨부파일 입력필드
//		
//		JPanel sendCpane = new JPanel();		//메일수정에 들어갈 중간 패널
//		JPanel sendSpane = new JPanel();		//메일수정에 들어갈 아래쪽 패널
//		
//		MailSendFrame() {
//			setTitle("메일 보내기 - " + NowUser.ID);					//타이틀 설정
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//종료시 모든 프레임만 졸료되도록 설정
//			Container con = getContentPane();		//컨텐트 팬 가져오기
//			con.setLayout(new BorderLayout());		//레이아웃을 BorderLayout으로 설정
//			
//			makemailText();
//			makesendCpane();
//			makesendSpane();
//			
//			sendSpane.setPreferredSize(new java.awt.Dimension(0, 50));		//southpane 크기설정
//			
//			//con.add(mailText, "North");
//			con.add(sendCpane, "Center");
//			con.add(sendSpane, "South");
//			
//			setSize(400, 450);			//크기설정
//			setVisible(true);			//화면에 출력
//		}
//		
//		void makesendCpane(){
//			JLabel mailTitleLabel = new JLabel("메일제목");		//메일제목 레이블 생성
//			JLabel mailLabel = new JLabel("메일내용");				//메일내용 레이블 생성
//			JLabel mailToLabel = new JLabel("받는주소");				//받는주소 레이블 생성
//			JLabel mailFileLabel = new JLabel("첨부파일");			//첨부파일 레이블 생성
//			JButton selectFile = new JButton("파일선택");			//'파일선택' 버튼 생성
//			selectFile.addActionListener(new MySendActionListener());		//닫기 버튼 엑션리스너 등록
//			
//			mailFileText.setEditable(false);				//첨부파일 필드 수정불가능하게 설정
//			mailFileText.setBackground(Color.WHITE);		//ㅂ경색 하얀색으로 설정
//			
//			//각각 레이블의 크기 설정
//			mailTitleLabel.setPreferredSize(new Dimension(100, 22));
//			mailLabel.setPreferredSize(new Dimension(100, 22));
//			mailToLabel.setPreferredSize(new Dimension(100, 22));
//			mailFileLabel.setPreferredSize(new Dimension(100, 22));
//			
//			//패널에 등록
//			sendCpane.add(mailTitleLabel);				//'메일제목'레이블 등록
//			sendCpane.add(mailTitleText);				//'메일제목'입력필드 등록
//			sendCpane.add(mailLabel);					//'메일내용'레이블 등록
//			sendCpane.add(mailText);					//'메일내용'입력필드 등록
//			sendCpane.add(mailToLabel);					//'받는주소'레이블 등록
//			sendCpane.add(mailToText);					//'받는주소'레이블 등록
//			sendCpane.add(mailFileLabel);				//'첨부파일'레이블 등록
//			sendCpane.add(mailFileText);				//'첨부파일'레이블 등록
//			sendCpane.add(selectFile);					//'파일선택'버튼 등록
//		}
//		
//		void makesendSpane(){
//			JButton mailSend = new JButton("보내기");			//'보내기' 버튼 생성
//			JButton sendFrameClose = new JButton("닫기");		//닫기 버튼 생성
//			mailSend.addActionListener(new MySendActionListener());				//메일수정 버튼 엑션리스너 등록
//			sendFrameClose.addActionListener(new MySendActionListener());		//닫기 버튼 엑션리스너 등록
//
//			sendSpane.add(mailSend);				//'보내기'버튼 등록
//			sendSpane.add(sendFrameClose);			//'닫기'버튼 등록
//		}
//		
//		void makemailText() {
//			mailText.setPreferredSize(new Dimension(400, 200));	//크기설정
//			mailText.setBackground(Color.WHITE);					//배경색 WHITE로 설정
//		}
//		
//		//메일보내기 프레임의 엑션리스너
//		//메일보내기 프레임의 버튼과 각각의 함수를 연동
//		class MySendActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				JButton b = new JButton();
//				
//				if(e.getSource().getClass() == b.getClass())
//					b = (JButton)e.getSource();
//				
//				if(b.getActionCommand() == "파일선택") selectSendFile();
//				else if(b.getActionCommand() == "보내기") mailSend();
//				else if(b.getActionCommand() == "닫기") sendFrameClose();
//			}
//		}
//		
//		//메일을 보낸다.
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
//				JOptionPane.showMessageDialog(null, "메일이 성공적으로 보내졌습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "메일 보내기가 실패했습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//		
//		//첨부파일을 선택한다.
//		void selectSendFile() {
//			//새 창을 띄워 첨부할 파일를 입력받음
//			String locate = JOptionPane.showInputDialog("첨부할 파일의 주소를 입력하세요.");
//			if(locate.equals("")) {		//입력받은 주소가 공란일 때
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "주소를 입력하지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			} else {
//				File selectfile = new File(locate);		//입력받은 주소로 파일 생성
//				if(!selectfile.exists()) {		//해당 파일이 존재하지 않을 때
//					//오류메세지 출력
//					JOptionPane.showMessageDialog(null, "파일이 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				} else {
//					mailFileText.setText(locate);
//				}
//			}
//		}
//		
//		//메일보내기 창을 닫는다
//		void sendFrameClose() {
//			this.setVisible(false);
//			this.dispose();
//		}
//	}
//	
//	Seeker() {
//		//DB연동
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		setTitle("6팀 탐색기 - " + NowUser.ID);			//타이틀 설정
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//종료시 모든 프레임만 졸료되도록 설정
//		Container con = getContentPane();		//컨텐트 팬 가져오기
//		con.setLayout(new BorderLayout());		//레이아웃을 BorderLayout으로 설정
//		
//		makeMenu();		//메뉴 만들기
//		makeNorth();		//northpane만들기
//		makeWest();		//westpane만들기
//		makeEast();		//eastpane만들기
//		makeCenter();		//centerpane만들기
//		
//		westpane.setPreferredSize(new java.awt.Dimension(150, 0));		//westpane 크기설정
//		eastpane.setPreferredSize(new java.awt.Dimension(150, 0));		//eastpane 크기설정
//		
//		con.add(northpane, "North");			//nortpane를 위쪽에 넣기
//		con.add(westpane, "West");			//westpane를 왼쪽에 넣기
//		con.add(eastpane, "East");			//eastpane를 오른쪽에 넣기
//		con.add(centerpane, "Center");			//centerpane를 가운데에 넣기
//		
//		setSize(800, 600);			//크기설정
//		setVisible(true);			//화면에 출력
//	}
//	
//	void makeMenu() {		//메뉴만들기
//		JMenuBar mb = new JMenuBar();		//메뉴바 mb 생성
//		
//		JMenu m1 = new JMenu("파일");			//'파일'메뉴 m1 생성
//		JMenuItem m11 = new JMenuItem("새 창 열기");	//'새 창 열기'메뉴 아이템 m11 생성
//		JMenuItem m12 = new JMenuItem("사용자");	//'사용자'메뉴 아이템 m12 생성
//		JMenuItem m13 = new JMenuItem("닫기");		//'닫기' 메뉴아이템 m13 생성
//		m11.addActionListener(new MyActionListener());		//m11엑션리스너 등록
//		m12.addActionListener(new MyActionListener());		//m12엑션리스너 등록
//		m13.addActionListener(new MyActionListener());		//m13엑션리스너 등록
//		
//		m1.add(m11);		//m1에 m11추가
//		m1.add(m12);		//m1에 m12추가
//		m1.addSeparator();	//m1에 구분선 추가
//		m1.add(m13);		//m1에 m13추가
//		
//		JMenu m2 = new JMenu("편집");			//'편집' 메뉴 m2 생성
//		JMenuItem m21 = new JMenuItem("새폴더");		//'새폴더' 메뉴 아이템 m21생성
//		JMenuItem m22 = new JMenuItem("이름바꾸기");	//'이름 바꾸기' 메뉴아이템 m22생성
//		JMenuItem m23 = new JMenuItem("이동");		//'이동' 메뉴아이템 m23생성
//		JMenuItem m24 = new JMenuItem("삭제");		//'삭제' 메뉴아이템 m24생성
//		m21.addActionListener(new MyActionListener());	//m21엑션리스너 등록
//		m22.addActionListener(new MyActionListener());	//m22엑션리스너 등록
//		m23.addActionListener(new MyActionListener());	//m23엑션리스너 등록
//		m24.addActionListener(new MyActionListener());	//m24엑션리스너 등록
//		
//		m2.add(m21);		//m2에 m21추가
//		m2.add(m22);		//m2에 m22추가
//		m2.add(m23);		//m2에 m23추가
//		m2.add(m24);		//m2에 m24추가
//		
//		JMenu m3 = new JMenu("보기");				//'보기'메뉴 m3생성
//		JMenuItem m31 = new JMenuItem("이름순 정렬");	//'이름순 정렬'메뉴아이템 m31생성
//		JMenuItem m32 = new JMenuItem("최신순 정렬");	//'최신순 정렬'메뉴아이템 m32생성
//		JMenuItem m33 = new JMenuItem("검색");		//'검색'메뉴아이템 m33생성
//		m31.addActionListener(new MyActionListener());	//m31엑션리스너 등록
//		m32.addActionListener(new MyActionListener());	//m32엑션리스너 등록
//		m33.addActionListener(new MyActionListener());	//m33엑션리스너 등록
//				
//		m3.add(m31);		//m3에 m31추가
//		m3.add(m32);		//m3에 m32추가
//		m3.add(m33);		//m3에 m33추가
//		
//		JMenu m4 = new JMenu("메일");				//'메일'메뉴 m4생성
//		JMenuItem m41 = new JMenuItem("메일주소 수정");	//'메일주소 수정'메뉴아이템 m31생성
//		JMenuItem m42 = new JMenuItem("메일 보내기");		//'메일보내기'메뉴아이템 m32생성
//		m41.addActionListener(new MyActionListener());	//m31엑션리스너 등록
//		m42.addActionListener(new MyActionListener());	//m32엑션리스너 등록
//
//		m4.add(m41);		//m3에 m41추가
//		m4.add(m42);		//m3에 m42추가
//	
//		mb.add(m1);			//mb에 m1추가
//		mb.add(m2);			//mb에 m2추가
//		mb.add(m3);			//mb에 m3추가
//		mb.add(m4);			//mb에 m4추가
//		
//		setJMenuBar(mb);	//메뉴바로 mb설정
//	}
//	
//	void makeNorth() {			//northpane만들기
//		JButton moveup = new JButton("...");		//상위폴더 버튼 생성
//		JLabel locatelabel = new JLabel("주소");		//주소 레이블 생성
//		JButton movelocate = new JButton("이동");			//이동 버튼 생성
//		moveup.addActionListener(new MyActionListener());	//상위폴더버튼 액션리스너 추가
//		movelocate.addActionListener(new MyActionListener());	//이동버늩 액션리스너 추가
//		
//		moveup.setPreferredSize(new Dimension(30, 20));		//상위폴더버튼 크기설정
//		movelocate.setPreferredSize(new Dimension(60, 20));	//이동버튼 크기설정
//		
//		northpane.add(moveup);		//상위폴더 버튼 추가
//		northpane.add(locatelabel);	//주소 레이블 추가
//		northpane.add(locatetext);	//주소창 추가
//		northpane.add(movelocate);	//이동버튼 추가
//	}
//
//	void makeWest() {			//westpane만들기
//		File list[] = File.listRoots();		//전체 파일 리스트 생성
//		DefaultMutableTreeNode temp;		//임시 트리노드 temp 생성
//		
//		for(int i=0;i<list.length;++i) {	//전체 파일만큼 반복
//			if(list[i].exists()) {			//해당 파일이 존재한다면
//				temp = new DefaultMutableTreeNode(list[i].getPath());		//temp에 노드 추가
//				temp.add(new DefaultMutableTreeNode("비어있음"));				//temp하위에 "비어있음"노드 추가
//				root.add(temp);		//root에 tmep 추가
//			}
//		}
//		
//		tree = new JTree(root);			//root를 사용해 JTree생성하고 tree초기화
//		tree.addTreeSelectionListener(new MyTreeSelectionListener());	//tree에 트리선택 리스너 추가
//		tree.addTreeWillExpandListener(new MyTreeWillExpandListener());	//tree에 트리 확장 리스너 추가
//		westpane = new JScrollPane(tree);		//tree로 JScrollPane생성, westpane 초기화
//	}
//	
//	void makeEast() {			//eastpane만들기
//		JButton addlike = new JButton("추가");		//추가 버튼 생성
//		JButton dellike = new JButton("삭제");		//삭제 버튼 생성
//		addlike.addActionListener(new MyActionListener());	//추가 버튼 엑션리스너 추가
//		dellike.addActionListener(new MyActionListener());	//삭제 버튼 엑션리스너 추가
//		
//		//likelist에 리스트 선택 리스너 추가
//		likelist.addListSelectionListener(new MyListSelectionListener());
//		
//		JPanel likepane = new JPanel();		//JPanel로 likepane 생성
//		
//		likepane.add(likelist);		//likepane에 likelist 추가
//		likepane.add(addlike);		//likepane에 추가버튼(addlike) 추가
//		likepane.add(dellike);		//likepane에 사제버튼(dellike) 추가
//		
//		likelist.setPreferredSize(new Dimension(125, 225));		//likelist크기 설정
//		
//		likepane.setPreferredSize(new Dimension(150, 300));		//llkepane 크기설정
//		previewpane.setPreferredSize(new Dimension(150, 175));	//previewpane 크기설정
//		
//		likepane.setBorder(new TitledBorder("즐겨찾기")); 		//likepane 테두리, 타이틀 설정
//		previewpane.setBorder(new TitledBorder("미리보기")); 	//previewpane 테두리, 타이틀 설정
//		
//		eastpane.add(likepane);			//eastpane에 likepane 추가
//		eastpane.add(previewpane);		//eastpane에 likpane 추가
//	}
//	
//	void makeCenter() {		//centerpane만들기
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//단일 선택 모드로 설정
//		table.addMouseListener(new MyMouseAdapter());			//마우스 어뎁터 추가
//		table.addKeyListener(new MyKeyAdapter());				//키보드 어뎁터 추가
//		makeroottable();								//루트테이블 생성
//		locatetext.setText("내컴퓨터");					//주소창"내컴퓨터"로 설정
//		centerpane.add(new JScrollPane(table));			//table을 사용해 JScrollPane 생성, centerpane에 추가
//	}
//	
//	void addLike() {		//즐겨찾기의 '추가'기능 구현 함수
//		File tempf = new File(reallocate);		//현재주소(reallocate)로 임시 파일 tempf 생성
//		if(tempf.exists()){				//tempf가 존재한다면
//			realvector.add(reallocate);						//realvector에 reallocate 추가
//			String tempn[] = reallocate.split("\\\\");		//reallocate를 \\기준으로 분리해서 tempn배열 생성
//			if(tempn.length == 1)				//tempn 길이가 1이라면 즉 루트 바로 다음 주소라면
//				likevector.add(tempn[0] + "\\");	//likevector에 해당 노드 추가(드라이브일 것임으로 '\' 추가)
//			else
//				likevector.add(tempn[tempn.length-1]);	//likevector에 마지막 노드 추가
//			likelist.updateUI();		//UI 변경사항 적용
//			
//			//DB에수정사항 등록
//			if(!NowUser.ID.equals("")) {		//로그인 중일 경우
//				Connection conn = null;
//				PreparedStatement pstmt = null;
//				//DB수정
//				try {
//					conn = DriverManager.getConnection(url,id,pass);		//DB연동
//					//DB에서 ID 검색
//					String query = "update userlist set user_lv=?,user_rv=? where user_ID = ?";
//					pstmt = conn.prepareStatement(query);
//					pstmt.setString(3, NowUser.ID);
//					pstmt.setString(1, likevector.toString());
//					pstmt.setString(2, realvector.toString());
//					pstmt.executeUpdate();
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
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
//		else {		//예외처리
//			JOptionPane.showMessageDialog(null, "주소가 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	void delLike() {		//즐겨찾기의 '삭제'기능 구현 함수
//		try {
//			realvector.remove(likelist.getSelectedIndex());		//선택된 인덱스의 realvector 제거
//			likevector.remove(likelist.getSelectedIndex());		//선택된 인덱스의 likevector 제거
//			likelist.updateUI();		//변경된 UI 적용
//			
//			//DB에 수정사항 등록
//			if(!NowUser.ID.equals("")) {		//로그인 중일 경우
//				Connection conn = null;
//				PreparedStatement pstmt = null;
//				//DB수정
//				try {
//					conn = DriverManager.getConnection(url,id,pass);		//DB연동
//					//DB에서 ID 검색
//					String query = "update userlist set user_lv=?,user_rv=? where user_ID = ?";
//					pstmt = conn.prepareStatement(query);
//					pstmt.setString(3, NowUser.ID);
//					pstmt.setString(1, likevector.toString());
//					pstmt.setString(2, realvector.toString());
//					pstmt.executeUpdate();
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "DB 연결 실패", "Message", JOptionPane.ERROR_MESSAGE);
//				} finally {
//					try {
//						if(pstmt != null) pstmt.close();
//						if(conn != null) conn.close();
//					} catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch(Exception ex) {			//예외처리
//			JOptionPane.showMessageDialog(null, "즐겨찾기에서 삭제할 대상을 선택하세요.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	
//	void moveUp() {		//주소의 '...'기능 구현 함수
//		if(reallocate.equals("")) {		//루트위치일 경우
//			JOptionPane.showMessageDialog(null, "더 이상 상위폴더가 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		if(reallocate.endsWith("\\")) {		//루드 바로 다음 위치일 경우
//			reallocate = "";				//reallocate 초기화
//			locatetext.setText("내컴퓨터");	//locatetext 초기화
//			makeroottable();				//루트테이블 생성
//			return;
//		}
//		String token[] = reallocate.split("\\\\");		//현재주소(reallocate)를 "\\"기준으로 분리
//		String locate = token[0];						//locate 생성
//		for(int i = 1; i < token.length-1; i++) {		//마지막에서 두번째 까지
//			locate += "\\" + token[i];					//주소형태로 재병합
//		}
//		if(token.length == 2)				//루트 바로 다음 위치인 경우
//			locate += "\\";					//주소 끝에 '\'추가
//		reallocate = locate;				//실제 주소 변경
//		locatetext.setText(locate);			//주소창 변경
//		File file = new File(locate);		//locate로 file생성
//		maketable(file);					//file로 테이블 초기화
//	}
//	
//	void moveLocate() {		//주소의 '이동'기능 구현 함수
//		try {
//			File file = new File(locatetext.getText());		//주소창에서 문자열을 읽어와 file생성
//			if(file.exists()){					//파일이 존재한다면
//				if(file.isDirectory()) {		//파일이 폴더라면
//					maketable(file);			//file로 테이블 생성
//					reallocate = locatetext.getText();	//현재 주소를 주소창의 텍스트로 변경
//				}
//				else {		//폴더가 아니라면
//					//실행
//					Process process = Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + file.getAbsolutePath());
//				}
//			}
//			else {		//예외처리
//				JOptionPane.showMessageDialog(null, "주소가 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {		//예외처리
//			JOptionPane.showMessageDialog(null, "확인되지 않은 오류", "Message", JOptionPane.ERROR_MESSAGE);
//			maketable(new File(reallocate));		//테이블 현재주소로 초기화
//			locatetext.setText(reallocate);			//주소창 현재주소로 초기화
//		}
//	}
//	
//	void m11() {		//메뉴의 '새 창 열기'기능 구현 합수
//		new Seeker();
//	}
//	
//	void m12() {		//메뉴의 '사용자'기능 구현 합수
//		new UserFrame();
//	}
//	
//	void m13() {		//메뉴의 '닫기'기능 구현 함수
//		this.setVisible(false);
//		this.dispose();
//	}
//	
//	void m21() {		//메뉴의 '새폴더'기능 구현 함수
//		try {
//			if(reallocate.equals("")) {		//루트위치라면
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "새 폴더를 만들 위치로 가세요.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			//새 창을 열어서 새 폴더의 이름을 입력받음
//			String name = JOptionPane.showInputDialog("새 폴더의 이름을 입력하세요.");
//			if(name.equals("")) {		//이름이 공란일 경우
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "새폴더의 이름을 입력하지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			File file = new File(reallocate);			//현재 위치로 file 생성
//			File sonfile = new File(reallocate + "\\" + name);		//reallocate와 name을 사용해 sonfile 생성
//			if(sonfile.exists()) {		//sonfile 이미 존재할 경우
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "디렉토리내에 같은 이름의 폴더/파일이 존재합니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			sonfile.mkdir();					//새 폴더 생성
//			if(reallocate.endsWith("\\")) {		//현재위치가 루트 바로 다음 위치일 경우
//				treeadd(reallocate + name);		//트리에 새 폴더 추가
//			}
//			else {
//				treeadd(reallocate + "\\" + name);		//트리에 새폴더 추가
//			}
//			maketable(file);		//file로 테이블 초기화
//		} catch(Exception ex) {}		// 예외처리
//	}
//	
//	void m22() {		//메뉴의 '이름 바꾸기'기능 구현 함수
//		try {
//			//새 창을 띄워 바꿀 이름을 입력받음
//			String name = JOptionPane.showInputDialog("새 이름을 입력하세요.");
//			if(name.equals("")) {			//이름이 공란일 경우
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "새 이름을 입력하지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			try {
//				File file = new File(reallocate);		//현재 위치로 file생성
//				//선택된 파일로 sefile 생성
//				File sefile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				File refile = new File(reallocate + "\\" + name);		//새로운 이름의 파일 refile생성
//				if(refile.exists()) {		//이미 존재하는 이름일 경우
//					//오류메세지 출력
//					JOptionPane.showMessageDialog(null, "같은 이름의 파일/폴더가 이미 있습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				sefile.renameTo(refile);		//파일 이름 변경
//				if(reallocate.endsWith("\\")) {		//현재 위치가 루트 다음 위치일 경우
//					//트리에서 이전 노드 삭제
//					treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					treeadd(reallocate + name);		//트리에 새로운 노드 생성
//				}
//				else {
//					//트리에서 이전 노드 삭제
//					treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					treeadd(reallocate + "\\" + name);		//트리에 새로운 노드 생성
//				}
//				maketable(file);		//file로 테이블 초기화
//			} catch(Exception ex) {		//예외처리
//				JOptionPane.showMessageDialog(null, "table에서 이름을 변경할 대상 파일/폴더를 선택하세요", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {}		//예외처리
//	}
//	
//	void m23() {		//메뉴의 '이동'기능 구현 함수
//		try {
//			//새 창을 띄워 이동시킬 위치를 입력받음
//			String locate = JOptionPane.showInputDialog("새 위치를 입력하세요.");
//			if(locate.equals("")) {		//입력받은 위치가 공란일 때
//				//오류메세지 출력
//				JOptionPane.showMessageDialog(null, "새 위치를 입력하지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			try {
//				File wanfile = new File(locate);		//새 위치로 wanfile 생성
//				if(!wanfile.exists()) {		//wanfile이 존재하지 않을 때
//					//오류메세지 출력
//					JOptionPane.showMessageDialog(null, "위치가 존재하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//		
//				}
//				File file = new File(reallocate);		//현재 위치로 file 생성
//				//이동시킬 파일로 sefile 생성
//				File sefile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				//새위치의 파일로 refile 생성
//				File refile = new File(locate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//				if(refile.exists()) {		//refile이 존재할 경우
//					//오류메세지 출력
//					JOptionPane.showMessageDialog(null, "새 위치에 같은 이름의 파일/폴더가 이미 있습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//				}
//				if(sefile.renameTo(refile)) {			//sefile을 refile로 이름변경(=주소변경 =이동)
//					if(reallocate.endsWith("\\")) {		//현재위치가 루트 다음위치일 경우
//						//트리에서 이전 노드삭제
//						treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						//트리에서 이전노드 삭제
//						treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					if(locate.endsWith("\\")) {		//새 위치가 루트 다음 위치일 경우
//						//트리에 새 파일 노드 생성
//						treeadd(locate + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					else {
//						//트리에 새 파일 노드 생성
//						treeadd(locate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//					}
//					maketable(file);		//테이블 초기화
//				}
//				else JOptionPane.showMessageDialog(null, "확인되지 않은 오류발생", "Message", JOptionPane.ERROR_MESSAGE);	
//			} catch(Exception ex) {		//예외처리
//				JOptionPane.showMessageDialog(null, "table에서 이동할 대상 파일/폴더를 선택하세요", "Message", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch(Exception ex) {}		//예외처리
//	}
//	
//	void m24() {		//메뉴의 '삭제'기능 구현 합수
//		//정말 삭제할건지 새 창에서 확인
//		int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
//		if(result != JOptionPane.YES_OPTION)		//정말 삭제하고 싶지 않다면
//			return;		//취소
//		try {
//			File file = new File(reallocate);		//현재위치로 file생성
//			//선택된 파일로 selfile 생성
//			File selfile = new File(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//			deletefile(selfile);		//선택된 파일 삭제
//			if(reallocate.endsWith("\\")) {			//현재위치가 루트 바로 다음 위치라면
//				//트리에서 파일 삭제
//				treedel(reallocate + model.getValueAt(table.getSelectedRow(), 0));
//			}
//			else {
//				//트리에서 파일 삭제
//				treedel(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0));
//			}
//			maketable(file);		//file로 테이블 초기화
//		} catch(Exception ex) {		//예외처리
//			JOptionPane.showMessageDialog(null, "table에서 삭제할 대상 파일/폴더를 선택하세요.", "Message", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	
//	void m31() {		//메뉴의 '이름순 정렬'기능 구현 함수
//		if(reallocate.equals("")) {		//루트위치일 경우
//			sort = true;		//sort를 true로 설정
//			return;				//함수 종료
//		}
//		File file = new File(reallocate);		//현재위치로 파일 생성
//		sort = true;							//sort를 true로 설정
//		maketable(file);						//file로 테이블 초기화
//	}
//	
//	void m32() {		//메뉴의 '최신순 정렬'기능 구현 함수
//		if(reallocate.equals("")) {				//현재위치가 루트위치일 경우
//			sort = false;						//sort를 false로 설정
//			return;								//함수 종료
//		}
//		File file = new File(reallocate);		//현재위치로 file생성
//		sort = false;							//sort를 false로 설정
//		maketable(file);						//file로 테이블 최화
//	}
//	
//	void m33() {		//메뉴의 '검색'기능 구현 함수
//		String name = JOptionPane.showInputDialog("검색할 이름을 입력하세요.");		//새창에서 검색할 이름 입력
//		if(name.equals("")) {													//입력받은 이름이 공란일 경우
//			//오류메세지 출력
//			JOptionPane.showMessageDialog(null, "검색할 이름을 입력하지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		File file = new File(reallocate);		//현재위치로 file 생성
//		filter = name;							//filter를 검색할 이름으로 변경
//		maketable(file);						//file로 테이블 재생성
//		filter = "";							//fileter 초기화
//	}
//	
//	void m41() {		//메뉴의 '메일주소 수정'기능 구현 함수
//		new MailAdrUpdateFrame();
//	}
//	
//	void m42() {		//메뉴의 '메일 보내기'기능 구현 함수
//		new MailSendFrame();
//	}
//	
//	void preView() {		//미리보기 기능을 구현한 함수
//		previewpane.removeAll();			//미리보기 패널을 전부 지움
//		try {
//			if(model.getValueAt(table.getSelectedRow(), 2).equals("txt")) {		//선택된 파일의 확장자가 txt라면
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {		//현재위치가 루트 다음 위치거나 루트라면
//					//선택된 파일로 PreviewText를 만들어 previewpane에 추가
//					previewpane.add(new PreviewText(reallocate + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//				else {
//					//선택된 파일로 PreviewText를 만들어 previewpane에 추가
//					previewpane.add(new PreviewText(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0)));
//				}	
//			}
//			else {
//				if(reallocate.endsWith("\\") || reallocate.equals("")) {		//현재위치가 루트 다음 위치거나 루트라면
//					//선택된 파일로 PreviewImage를 만들어 previewpane에 추가
//					previewpane.add(new PreviewImage(reallocate + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//				else {
//					//선택된 파일로 PreviewImage를 만들어 previewpane에 추가
//					previewpane.add(new PreviewImage(reallocate + "\\" + model.getValueAt(table.getSelectedRow(), 0)));
//				}
//			}
//		} catch(Exception ex) {}
//		previewpane.updateUI();		//미리보기 패널 갱신
//	}
//	
//	public static void main(String args[]) {		//메인함수
//		new Seeker();							//탬색기 실행
//	}
//	
//	void makeroottable() {			//루트테이블 생성
//		File subfile[] = File.listRoots();			//listRoots()로 subfile생성
//		String tempr[] = { "", "", "", "" };		//문자열 배열 tempr 생성
//		//날짜 자료형형 sd 생성
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//		sortfile(subfile);		//subfiel 정렬
//		while(model.getRowCount() != 0) {		//테이블 삭제
//			model.removeRow(0);
//		}
//		for(int i = 0; i < subfile.length; i++) {		//서브파일 길이만큼 반복
//			if(subfile[i].exists()) {					//해당 subfile이 존재한다면
//				tempr[0] = subfile[i].getPath();		//해당 subfile에서 이름 추출
//				//subfile에서 수정한 날짜 추출
//				tempr[3] = sdf.format(new Date(subfile[i].lastModified()));
//				model.addRow(tempr);					//테이블에 tempr 추가
//			}
//		}
//		table.updateUI();				//테아블 갱신
//	}
//	
//	void maketable(File file) {			//테이블 생성
//		File subfile[] = file.listFiles();			//listfile()로 subfile생성
//		String tempr[] = { "", "", "", "" };		//테이블에 한 줄로 들어갈 문자열 배열 tempr 생성
//		//날짜자료형 sdf 생성
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//		sortfile(subfile);					//subfile 정렬
//		while(model.getRowCount() != 0) {	//테이블 전부 삭제
//			model.removeRow(0);
//		}
//		for(int i = 0; i < subfile.length; i++) {		//서브파일 만큼 반복
//			if(!subfile[i].isHidden() && searchfilter(subfile[i].getName())) {		//해당 서브파일이 숨겨져있지않거나 필터에 통과되었다면
//				tempr[0] = subfile[i].getName();		//해당 서브파일의 이름을 구해서 temp[0]에 입력
//				if(subfile[i].isFile())					//해당섭파일이 파일이라면
//					tempr[1] = subfile[i].length()+"byte";		//크기를 구해서 tmep[1]에 입력
//				else
//					tempr[1] = "";				//temp[1]을 지움
//				if(subfile[i].isDirectory())		//해당 서브파일이 폴더라면
//					tempr[2] = "폴더";				//tmepr[2]을 "폴더"로 설정
//				else {
//					String temps[] = subfile[i].getName().split("\\.");			//해당 파일의 확장자를 구해서
//					if(temps.length > 1)
//						tempr[2] = temps[temps.length-1];		//tempr[2]를 해당 파일의 확장자로 변경
//					else
//						tempr[2] = "파일";						//확장자가 없으면 tempr[2]를 "파일"로 설정
//				}
//				tempr[3] = sdf.format(new Date(subfile[i].lastModified()));		//수정한 날짜를 구해서 tempr[3]을 설정
//				model.addRow(tempr);		//테이블에 추가
//			}
//		}
//		table.updateUI();				//테이블 갱신
//	}
//	
//	void sortfile(File[] subfile) {		//subfile을 입력받아서 정렬된 서브파일을 반환하는 함수
//		File temp;
//		if(sort == false){			//subfile은 기본이 이름순 정렬임으로 최신순 정렬일 경우에만 정렬을 실행한다
//			for(int i = 0; i < subfile.length; i++) {				//서브파일 길이만큼 반복
//				for(int j = i; j < subfile.length; j++) {			//서브파일 길이만큼 반복
//					//i번째 subfile보다 j번쨰 subfile의 수정한 날짜가 더 크다면 둘을 바꾼다
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
//	boolean searchfilter(String fname) {			//fname(이름)을 입력받아서 현재filter와 비교
//		if(fname.startsWith(filter))		//fname이 filter로 시작한다면
//			return true;					//true 반환
//		else if(fname.endsWith(filter))		//fname이 filter로 끝나면
//			return true;					//true 반환
//		
//		if(!filter.equals("")){				//filter이 ""가 아니라면(즉 필터값이 설정되어 있다면)
//			String token[] = fname.split(filter);		//fname을 filter을 기준으로 분리
//			if(token.length == 1)			//분리된 fname이 1조각이라면(즉 fname에 filter이 포함되지 않다면)
//				return false;				//false 반환
//			return true;					//true 반환
//		}
//		return true;						//true 반환
//	}
//	
//	//노드 추가 함수
//	//새 폴더를 추가한 뒤에  트리에도 추가해 주기 위한 언어
//	void treeadd(String locate) {
//		boolean succes = true;						//성공 여부를 나타내는 succes 생성 true로 초기화
//		String token[] = locate.split("\\\\");		//locate를 "\\"기준으로 분리
//		DefaultMutableTreeNode temp = root;			//임시 노드 temp 생성, root로 초기화
//		token[0] += "\\";							//첫번째 토큰에 '\' 추가 (ex: C 를 C\로 바꿔주기 위해)
//		//해당 위치의 노드의 바로 상위 노드를 검색
//		for(int i = 0; i < token.length-1; i++) {		//토큰길이보다 하나 작게 반복
//			for(int j = 0; j < temp.getChildCount(); j++) {				//temp의 자식 개수만큼 반복
//				if(temp.getChildAt(j).toString().equals(token[i])) {	//j번쨰 temp의 자식이 i번째 토큰과 같다면
//					temp = (DefaultMutableTreeNode) temp.getChildAt(j);	//temp를 temp의 j번째 자식으로 변경
//					break;												//자식의 확인을 중단하고 다음 토큰 확인
//				}
//				else if(j == temp.getChildCount()-1) {					//
//					succes = false;
//					break;
//				}
//			}
//			if(!succes) break;
//		}
//		if(succes) {		
//			//해당 위치의 바로 상위 노드가 빈폴더라면 "비어있음"노드 삭제
//			if(temp.getChildAt(0).toString().equals("비어있음")) temp.remove(0);
//			DefaultMutableTreeNode child = new DefaultMutableTreeNode(token[token.length-1]);	//새로 추가할 노드 생성
//			child.add(new DefaultMutableTreeNode("비어있음"));		//새 노드에 "비어있음"노드 추가
//			temp.add(child);			//상위노드에 새 노드 추가
//			tree.updateUI();			//트리 갱신
//		}
//	}
//	
//	//노드삭제 함수
//	//파일 삭제를 수행한 후에 변경사항을 트리에도 적용하기 위한 함수
//	//위 함수와 비슷
//	void treedel(String locate) {
//		boolean succes = true;
//		String token[] = locate.split("\\\\");
//		DefaultMutableTreeNode temp = root;
//		token[0] += "\\";
//		//해당 노드 바로 이전 노드를 검색
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
//		//해당 노드를 삭제
//		if(succes) {
//			//해당 위치에 자식들 중에 해당 노드와 같은 이름의 파일 삭제
//			for(int i = 0; i < temp.getChildCount(); i++) {
//				if(temp.getChildAt(i).toString().equals(token[token.length-1])) {
//					temp.remove(i);
//				}
//			}
//			//삭제 후 해당 위치에 아무 파일도 없다면 "비어있음"노드 추가
//			if(temp.getChildCount() == 0) temp.add(new DefaultMutableTreeNode("비어있음"));
//			tree.updateUI();		//트리 갱신
//		}
//	}
//	
//	//파일 삭제 함수
//	//하위파일이 있는 폴더라면 삭제가 진행되지 않기때문에 모든 하위파일에대해 재귀함으로 기능을 수행
//	void deletefile(File selfile) {
//		if(selfile.isDirectory()) {	//선택된 파일이 폴더라면
//			File subselfile[] = selfile.listFiles();	//선택된 파일의 하위파일로 배열 생성
//			for(int i = 0; i < subselfile.length; i++) {		//모든 하위파일에 대해
//				deletefile(subselfile[i]);				//파일 삭제를 실행
//			}
//		}
//		selfile.delete();	//선택된 파일 삭제
//	}
//	
//	//트리확장 이벤트의 노드에서 주소 생성
//	//getPath().toString()는 트리에서 선택된 노드까지 거치는 모든 노드를 ,를 사용해 표현 따라서 주소형태로 변환할 필요가 있다.
//	//getPath().toString()는 문자열 시작과 마지막에 '[', ']'가 들어가기 때문에 주의
//	//현재 트리의 루트노드는 "내컴퓨터"로 주소화에 필요 없다.
//	String getPath(TreeExpansionEvent e) {
//		String temp = "";		//임시 문자열 temp
//		String tempsplit[] = e.getPath().toString().split(", ");	//선택된 노드를 문자열로 바꾸어','기준으로 분리
//		//첫번쨰(루트노드)와 마지막 노드를 제외한 모든 노드에서 반복 분리된 문자열 사이에'\'를 넣어서 주소형태로 변환
//		for(int i = 1; i < tempsplit.length - 1; i++) {
//			if(i == 1) temp += tempsplit[i];
//			else temp += (tempsplit[i]+"\\");
//		}
//		temp += e.getPath().getLastPathComponent();	//마지막 노드를 추가
//		return temp;		//주소 반환
//	}
//	
//	String getPath(TreeSelectionEvent e) {	//트리선택 이벤트의 노드에서 주소 생성 , 위함수와 거의 같음
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