package site.itwill.student; 

/********************************************************
파    일   명 : StudentGUIApp.java
기         능 : 학생 관리 프로그램
*********************************************************/
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StudentGUIApp extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final int NONE = 0;
	public static final int ADD = 1;
	public static final int DELETE = 2;
	public static final int UPDATE = 3;
	public static final int UPDATE_CHANGE = 4;
	public static final int SEARCH = 5;

	JTextField noTF, nameTF, phoneTF, addressTF, birthdayTF;
	JButton addB, deleteB, updateB, searchB, cancelB;
	
	JTable table;
	
	int cmd;
	/********************************************
	 * 생성자 정의
	 *********************************************/
	public StudentGUIApp() throws Exception {
		setTitle("◆◆◆ 학생 관리 프로그램 ◆◆◆");
		setSize(800, 400);

		Dimension dim = getToolkit().getScreenSize();
		setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2
				- getHeight() / 2);

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(5, 1));

		JPanel pno = new JPanel();
		pno.add(new JLabel("번  호"));
		pno.add(noTF = new JTextField(10));

		JPanel pname = new JPanel();
		pname.add(new JLabel("이  름"));
		pname.add(nameTF = new JTextField(10));
		
		JPanel pbirthday = new JPanel();
		pbirthday.add(new JLabel("생  일"));
		pbirthday.add(birthdayTF = new JTextField(10));
		
		JPanel pphone = new JPanel();
		pphone.add(new JLabel("전  화"));
		pphone.add(phoneTF = new JTextField(10));

		JPanel paddress = new JPanel();
		paddress.add(new JLabel("주  소"));
		paddress.add(addressTF = new JTextField(10));

		left.add(pno);
		left.add(pname);
		left.add(pphone);
		left.add(paddress);
		left.add(pbirthday);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 5));

		bottom.add(searchB = new JButton("검  색"));
		searchB.addActionListener(this);

		bottom.add(addB = new JButton("추  가"));
		addB.addActionListener(this);

		bottom.add(deleteB = new JButton("삭  제"));
		deleteB.addActionListener(this);
		
		bottom.add(updateB = new JButton("변  경"));
		updateB.addActionListener(this);

		bottom.add(cancelB = new JButton("취  소"));
		cancelB.addActionListener(this);

		Object[] title={"학번","이름","전화번호","주소","생년월일"};
		table=new JTable(new DefaultTableModel(title, 0));
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane sp=new JScrollPane(table);
		
		add(sp, "Center");
		add(left, "West");
		add(bottom, "South");
		cmd = NONE;
		initialize();

		displayAllStudent();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void initialize() {
		noTF.setEditable(false);
		nameTF.setEditable(false);
		phoneTF.setEditable(false);
		addressTF.setEditable(false);
		birthdayTF.setEditable(false);
	}

	public void setEditable(int n) {
		switch (n) {
		case ADD:
			noTF.setEditable(true);
			nameTF.setEditable(true);
			phoneTF.setEditable(true);
			addressTF.setEditable(true);
			birthdayTF.setEditable(true);
			break;
		case DELETE:
			noTF.setEditable(true);
			break;
		case UPDATE:
			noTF.setEditable(true);
			break;
		case UPDATE_CHANGE:
			noTF.setEditable(false);
			nameTF.setEditable(true);
			phoneTF.setEditable(true);
			addressTF.setEditable(true);
			birthdayTF.setEditable(true);
			break;
		case SEARCH:
			nameTF.setEditable(true);
			break;
		case NONE:
			noTF.setEditable(false);
			nameTF.setEditable(false);
			phoneTF.setEditable(false);
			addressTF.setEditable(false);
			birthdayTF.setEditable(false);
		}
	}

	public void setEnable(int n) {
		addB.setEnabled(false);
		deleteB.setEnabled(false);
		updateB.setEnabled(false);
		searchB.setEnabled(false);

		switch (n) {
		case ADD:
			addB.setEnabled(true);
			setEditable(ADD);
			cmd = ADD;
			break;
		case DELETE:
			deleteB.setEnabled(true);
			setEditable(DELETE);
			cmd = DELETE;
			break;
		case UPDATE:
			updateB.setEnabled(true);
			setEditable(UPDATE);
			cmd = UPDATE;
			break;			
		case UPDATE_CHANGE:
			updateB.setEnabled(true);
			setEditable(UPDATE_CHANGE);
			cmd = UPDATE_CHANGE;
			break;			
		case SEARCH:
			searchB.setEnabled(true);
			setEditable(SEARCH);
			cmd = SEARCH;
			break;
		case NONE:
			addB.setEnabled(true);
			deleteB.setEnabled(true);
			updateB.setEnabled(true);
			searchB.setEnabled(true);
		}
	}

	public void clear() {
		noTF.setText("");
		nameTF.setText("");
		phoneTF.setText("");
		addressTF.setText("");
		birthdayTF.setText("");
	}

	public void initDisplay() {
		setEnable(NONE);
		clear();
		cmd = NONE;
		initialize();		
	}

	public static void main(String args[]) throws Exception {
		new StudentGUIApp();
	}
	
	public void actionPerformed(ActionEvent ev) {
		Component c = (Component) ev.getSource();
		try {
			if (c == addB) {//추가 버튼을 누른 경우
				if (cmd != ADD) {//ADD 상태가 아닌 경우
					setEnable(ADD);//ADD 상태로 활성화					
				} else {//ADD 상태인 경우
					addStudent();
				}
			} else if (c == deleteB) {
				if (cmd != DELETE) {
					setEnable(DELETE);
				} else {
					removeStudent();
				}
			} else if (c == updateB) {
				if (cmd != UPDATE && cmd != UPDATE_CHANGE) {
					setEnable(UPDATE);
				} else if (cmd != UPDATE_CHANGE) {
					searchNoStudent();
				} else  {
					modifyStudent();
				}
			} else if (c == searchB) {
				if (cmd != SEARCH) {
					setEnable(SEARCH);
				} else {
					searchNameStudent();
				}
			} else if (c == cancelB) {
				displayAllStudent();
				initDisplay();
			}
		} catch (Exception e) {
			System.out.println("예외 발생 : " + e);
		}		
	}
		
	//JTextField에서 입력된 학생정보를 반환받아 STUDENT 테이블에 저장하는 메소드
	public void addStudent() {
		String noTemp=noTF.getText();
		if(noTemp.equals("")) {
			JOptionPane.showMessageDialog(this, "학번을 반드시 입력해 주세요.");
			noTF.requestFocus();//입력촛점 이동하는 메소드
			return;
		}
		
		String noReg="\\d{4}";
		if(!Pattern.matches(noReg, noTemp)) {
			JOptionPane.showMessageDialog(this, "학번은 4자리 숫자로 입력해 주세요.");
			noTF.requestFocus();
			return;
		}
		
		int no=Integer.parseInt(noTemp);
		
		if(StudentDAO.getStudentDAO().selectNoStudent(no)!=null) {
			JOptionPane.showMessageDialog(this, "이미 사용중인 학번을 입력 하였습니다.");
			noTF.requestFocus();
			return;
		}
		
		String name=nameTF.getText();
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 반드시 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="^[가-힣]{2,5}$";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 2~5 범위의 한글만 입력 가능합니다.");
			nameTF.requestFocus();
			return;
		}
		
		String phone=phoneTF.getText();
		if(phone.equals("")) {
			JOptionPane.showMessageDialog(this, "전화번호를 반드시 입력해 주세요.");
			phoneTF.requestFocus();
			return;
		}
		
		String phoneReg="(01[016789])-\\d{3,4}-\\d{4}";
		if(!Pattern.matches(phoneReg, phone)) {
			JOptionPane.showMessageDialog(this, "전화번호를 형식에 맞게 입력해 주세요.");
			phoneTF.requestFocus();
			return;
		}
		
		String address=addressTF.getText();
		if(address.equals("")) {
			JOptionPane.showMessageDialog(this, "주소를 반드시 입력해 주세요.");
			addressTF.requestFocus();
			return;
		}
		
		String birthday=birthdayTF.getText();
		if(birthday.equals("")) {
			JOptionPane.showMessageDialog(this, "생년월일을 반드시 입력해 주세요.");
			birthdayTF.requestFocus();
			return;
		}
		
		String birthdayReg="(18|19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(birthdayReg, birthday)) {
			JOptionPane.showMessageDialog(this, "생년월일을 형식에 맞게 입력해 주세요.");
			birthdayTF.requestFocus();
			return;
		}
		
		//StudentDTO 인스턴스를 생성하여 입력값으로 필드값 변경
		StudentDTO student=new StudentDTO();
		student.setNo(no);
		student.setName(name);
		student.setPhone(phone);
		student.setAddress(address);
		student.setBirthday(birthday);
		
		//STUDENT 테이블에 입력값으로 행을 저장하고 결과를 반환받아 저장
		// => StudnetDAO 클래스의 insertStudent() 메소드 호출
		int rows=StudentDAO.getStudentDAO().insertStudent(student);
		
		JOptionPane.showMessageDialog(this, rows+"명의 학생정보를 저장 하였습니다.");

		displayAllStudent();
		initDisplay();
	}
	
	//JTextField에서 입력된 학생정보를 반환받아 STUDENT 테이블에 저장된 학생정보를 변경하는 메소드
	public void modifyStudent() {
		int no=Integer.parseInt(noTF.getText());
		
		String name=nameTF.getText();
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 반드시 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="^[가-힣]{2,5}$";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 2~5 범위의 한글만 입력 가능합니다.");
			nameTF.requestFocus();
			return;
		}
		
		String phone=phoneTF.getText();
		if(phone.equals("")) {
			JOptionPane.showMessageDialog(this, "전화번호를 반드시 입력해 주세요.");
			phoneTF.requestFocus();
			return;
		}
		
		String phoneReg="(01[016789])-\\d{3,4}-\\d{4}";
		if(!Pattern.matches(phoneReg, phone)) {
			JOptionPane.showMessageDialog(this, "전화번호를 형식에 맞게 입력해 주세요.");
			phoneTF.requestFocus();
			return;
		}
		
		String address=addressTF.getText();
		if(address.equals("")) {
			JOptionPane.showMessageDialog(this, "주소를 반드시 입력해 주세요.");
			addressTF.requestFocus();
			return;
		}
		
		String birthday=birthdayTF.getText();
		if(birthday.equals("")) {
			JOptionPane.showMessageDialog(this, "생년월일을 반드시 입력해 주세요.");
			birthdayTF.requestFocus();
			return;
		}
		
		String birthdayReg="(18|19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(birthdayReg, birthday)) {
			JOptionPane.showMessageDialog(this, "생년월일을 형식에 맞게 입력해 주세요.");
			birthdayTF.requestFocus();
			return;
		}
		
		//StudentDTO 인스턴스를 생성하여 입력값으로 필드값 변경
		StudentDTO student=new StudentDTO();
		student.setNo(no);
		student.setName(name);
		student.setPhone(phone);
		student.setAddress(address);
		student.setBirthday(birthday);
		
		//STUDENT 테이블의 저장행을 변경하고 결과를 반환받아 저장
		// => StudnetDAO 클래스의 updateStudent() 메소드 호출
		int rows=StudentDAO.getStudentDAO().updateStudent(student);
		
		JOptionPane.showMessageDialog(this, rows+"명의 학생정보를 변경 하였습니다.");

		displayAllStudent();
		initDisplay();
	}
	
	//JTextField에서 입력된 학번을 반환 받아 해당 학번의 학생정보를 STUDENT 테이블에서 삭제하는 메소드
	public void removeStudent() {
		String noTemp=noTF.getText();
		if(noTemp.equals("")) {
			JOptionPane.showMessageDialog(this, "학번을 반드시 입력해 주세요.");
			noTF.requestFocus();
			return;
		}
		
		String noReg="\\d{4}";
		if(!Pattern.matches(noReg, noTemp)) {
			JOptionPane.showMessageDialog(this, "학번은 4자리 숫자로 입력해 주세요.");
			noTF.requestFocus();
			return;
		}

		int no=Integer.parseInt(noTemp);
		
		//STUDENT 테이블의 저장행을 삭제하고 결과를 반환받아 저장
		// => StudnetDAO 클래스의 deleteStudent() 메소드 호출
		int rows=StudentDAO.getStudentDAO().deleteStudent(no);
		
		if(rows>0) {
			JOptionPane.showMessageDialog(this, rows+"명의 학생정보를 삭제 하였습니다.");
		} else {
			JOptionPane.showMessageDialog(this, "삭제하고자 하는 학생정보가 존재하지 않습니다.");
		}

		displayAllStudent();
		initDisplay();
	}
	
	//JTextField에서 입력된 학번을 반환 받아 해당 학번의 학생정보를 STUDENT 테이블에서
	//검색하여 JTextField에 출력하는 메소드
	public void searchNoStudent() {
		String noTemp=noTF.getText();
		if(noTemp.equals("")) {
			JOptionPane.showMessageDialog(this, "학번을 반드시 입력해 주세요.");
			noTF.requestFocus();
			return;
		}
		
		String noReg="\\d{4}";
		if(!Pattern.matches(noReg, noTemp)) {
			JOptionPane.showMessageDialog(this, "학번은 4자리 숫자로 입력해 주세요.");
			noTF.requestFocus();
			return;
		}
		
		int no=Integer.parseInt(noTemp);
		
		StudentDTO student=StudentDAO.getStudentDAO().selectNoStudent(no);
				
		if(student==null) {
			JOptionPane.showMessageDialog(this, "변경하고자 하는 학생정보가 존재하지 않습니다.");
			noTF.requestFocus();
			noTF.setText("");
			return;
		}
		
		noTF.setText(student.getNo()+"");
		nameTF.setText(student.getName());
		phoneTF.setText(student.getPhone());
		addressTF.setText(student.getAddress());
		birthdayTF.setText(student.getBirthday());
		
		setEnable(UPDATE_CHANGE);
	}
	
	//JTextField에서 입력된 학생이름을 반환받아 STUDENT 테이블에  
	//저장된 해당 학생의 학생정보를 검색하여 출력하는 메소드
	public void searchNameStudent() {
		String name=nameTF.getText();
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 반드시 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="^[가-힣]{2,5}$";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 2~5 범위의 한글만 입력 가능합니다.");
			nameTF.requestFocus();
			return;
		}
		
		//이름을 전달하여 STUDENT 테이블의 저장행을 검색하여 결과를 반환받아 저장
		// => StudnetDAO 클래스의 selectNameStudent() 메소드 호출
		List<StudentDTO> studentList=StudentDAO.getStudentDAO().selectNameStudent(name);
		
		if(studentList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "검색된 학생정보가 존재하지 않습니다.");
			return;
		}

		DefaultTableModel model=(DefaultTableModel)table.getModel();
		
		//JTable에 출력된 기존의 학생정보를 한 행씩 제거하는 반복문
		for(int i=model.getRowCount();i>0;i--) {
			model.removeRow(0);
		}
		
		//반환받은 학생정보들을 한 행씩 출력하는 반복문
		for(StudentDTO student:studentList) {
			Vector<Object> rowData=new Vector<Object>();
			rowData.add(student.getNo());
			rowData.add(student.getName());
			rowData.add(student.getPhone());
			rowData.add(student.getAddress());
			rowData.add(student.getBirthday());
			model.addRow(rowData);
		}
		
		initDisplay();
	}
	
	//STUDENT 테이블에 저장된 모든 학생정보를 검색하여 출력하는 메소드
	public void displayAllStudent() {
		List<StudentDTO> studentList=StudentDAO.getStudentDAO().selectAllStudent();
		
		if(studentList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "저장된 학생정보가 존재하지 않습니다.");
			return;
		}
		
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		
		//JTable에 출력된 기존의 학생정보를 한 행씩 제거하는 반복문
		for(int i=model.getRowCount();i>0;i--) {
			model.removeRow(0);
		}
		
		//반환받은 학생정보들을 한 행씩 출력하는 반복문
		for(StudentDTO student:studentList) {
			Vector<Object> rowData=new Vector<Object>();
			rowData.add(student.getNo());
			rowData.add(student.getName());
			rowData.add(student.getPhone());
			rowData.add(student.getAddress());
			rowData.add(student.getBirthday());
			model.addRow(rowData);
		}
	}
}






