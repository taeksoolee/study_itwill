package site.itwill.student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

//학생 관리 프로그램 - 메뉴 선택에 따른 학생정보 저장,변경,삭제,검색 기능 구현 
public class StudentCUIApp {
	private BufferedReader in;
	
	public StudentCUIApp() {
		in=new BufferedReader(new InputStreamReader(System.in));
		
		String[] menu={"1.학생정보 추가","2.학생정보 변경","3.학생정보 삭제"
				,"4.학생이름 검색","5.전체학생 검색","6.프로그램 종료"};
		
		System.out.println("<<학생 관리 프로그램>>");
		
		while(true) {
			for(String item:menu) {
				System.out.println(item);
			}
			
			int choice;
			try {
				System.out.print("선택[1~6] >> ");
				choice=Integer.parseInt(in.readLine());
				if(choice<1 || choice>6) throw new RuntimeException();
			} catch (Exception e) {
				System.out.println("[에러]메뉴를 잘못 선택 하였습니다. 다시 선택해 주세요.");
				System.out.println();
				continue;
			}
			
			if(choice==6) break;
			System.out.println();
			
			//메뉴 선택에 따른 기능 구현 메소드 호출
			switch (choice) {
			case 1:	addStduent(); break;
			case 2:	modifyStudent(); break;
			case 3:	removeStudent(); break;
			case 4:	searchNameStudent(); break;
			case 5:	searchAllStudent(); break;
			}
			System.out.println();
		}
		System.out.println("[메세지]학생 관리 프로그램을 종료합니다.");
	}
	
	public static void main(String[] args) {
		new StudentCUIApp();
	}
	
	//[학생정보 추가] 메뉴를 선택한 경우 호출되는 메소드
	// => 키보드로 학생정보를 입력받아 STUDENT 테이블에 저장 후 결과 출력
	private void addStduent() {
		System.out.println("### 학생정보 추가 ###");
		try {
			//키보드로 학생정보를 입력받아 저장
			// => 입력값에 대한 유효성 검사 >> 검증 실패시 메세지 출력 후 재입력
			int no;
			while(true) {
				System.out.print("학번 입력 >> ");
				String noTemp=in.readLine();
				
				if(noTemp==null || noTemp.equals("")) {
					System.out.println("[입력오류]학번을 반드시 입력해 주세요.");
					continue;
				}
				
				//String noReg="^[0-9]{4}$";
				String noReg="\\d{4}";
				if(!Pattern.matches(noReg, noTemp)) {
					System.out.println("[입력오류]학번은 4자리 숫자로 입력해 주세요.");
					continue;
				}
				
				no=Integer.parseInt(noTemp);
				
				//입력된 학번이 STUDENT 테이블에 저장된 경우 재입력
				//학번을 전달하여 STUDENT 테이블의 저장행을 검색하여 반환받아 저장
				// => StudentDAO 클래스의 selectNoStudent() 메소드 호출
				StudentDTO student=StudentDAO.getStudentDAO().selectNoStudent(no);
				if(student!=null) {
					System.out.println("[입력오류]이미 사용중인 학번을 입력 하였습니다.");
					continue;
				}
				
				break;
			}
			
			String name;
			while(true) {
				System.out.print("이름 입력 >> ");
				name=in.readLine();
				
				if(name==null || name.equals("")) {
					System.out.println("[입력오류]이름을 반드시 입력해 주세요.");
					continue;
				}
				
				String nameReg="^[가-힣]{2,5}$";
				if(!Pattern.matches(nameReg, name)) {
					System.out.println("[입력오류]이름은 2~5 범위의 한글만 입력 가능합니다.");
					continue;
				}
				
				break;
			}
			
			String phone;
			while(true) {
				System.out.print("전화번호 입력 >> ");
				phone=in.readLine();
				
				if(phone==null || phone.equals("")) {
					System.out.println("[입력오류]전화번호를 반드시 입력해 주세요.");
					continue;
				}
				
				String phoneReg="(01[016789])-\\d{3,4}-\\d{4}";
				if(!Pattern.matches(phoneReg, phone)) {
					System.out.println("[입력오류]전화번호를 형식에 맞게 입력해 주세요.");
					continue;
				}
				
				break;
			}
			
			String address;
			while(true) {
				System.out.print("주소 입력 >> ");
				address=in.readLine();
				
				if(address==null || address.equals("")) {
					System.out.println("[입력오류]주소를 반드시 입력해 주세요.");
					continue;
				}
				
				break;
			}
			
			String birthday;
			while(true) {
				System.out.print("생년월일 입력 >> ");
				birthday=in.readLine();
				
				if(birthday==null || birthday.equals("")) {
					System.out.println("[입력오류]생년월일을 반드시 입력해 주세요.");
					continue;
				}
				
				String birthdayReg="(18|19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
				if(!Pattern.matches(birthdayReg, birthday)) {
					System.out.println("[입력오류]생년월일을 형식에 맞게 입력해 주세요.");
					continue;
				}
				
				break;
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
			
			System.out.println("[처리결과]"+rows+"명의 학생정보를 저장 하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//[학생정보 변경] 메뉴를 선택한 경우 호출되는 메소드
	// => 키보드로 학번을 입력받아 STUDENT 테이블의 저장행를 검색하여 출력하고 
	//    변경하고자 하는 학생정보를 입력받아 STUDENT 테이블의 저장행 변경 후 결과 출력
	// => 학번의 학생정보가 존재하지 않는 경우 메세지 출력 후 메소드 종료
	private void modifyStudent() {
		System.out.println("### 학생정보 변경 ###");
		try {
			int no;
			while(true) {
				System.out.print("학번 입력 >> ");
				String noTemp=in.readLine();
				
				if(noTemp==null || noTemp.equals("")) {
					System.out.println("[입력오류]학번을 반드시 입력해 주세요.");
					continue;
				}
				
				String noReg="\\d{4}";
				if(!Pattern.matches(noReg, noTemp)) {
					System.out.println("[입력오류]학번은 4자리 숫자로 입력해 주세요.");
					continue;
				}
				
				no=Integer.parseInt(noTemp);
				break;
			}

			//학번을 전달하여 STUDENT 테이블의 저장행을 검색하여 반환받아 저장
			// => StudentDAO 클래스의 selectNoStudent() 메소드 호출
			StudentDTO student=StudentDAO.getStudentDAO().selectNoStudent(no);
			if(student==null) {
				System.out.println("[처리결과]변경하고자 하는 학생정보가 존재하지 않습니다.");
				return;
			}
			
			//검색된 학생정보 출력
			System.out.println("==========================================================");
			System.out.println("학번\t이름\t전화번호\t주소\t\t생년월일");
			System.out.println("==========================================================");
			System.out.println(student.getNo()+"\t"+student.getName()
				+"\t"+student.getPhone()+"\t"+student.getAddress()
				+"\t"+student.getBirthday());
			System.out.println("==========================================================");
			
			System.out.println("[메세지]변경값 입력 >> 변경하지 않을 경우 엔터만 입력");
			
			String name;
			while(true) {
				System.out.print("이름 입력 >> ");
				name=in.readLine();
			
				String nameReg="^[가-힣]{2,5}$";
				if(name!=null && !name.equals("") && !Pattern.matches(nameReg, name)) {
					System.out.println("[입력오류]이름은 2~5 범위의 한글만 입력 가능합니다.");
					continue;
				}
				
				break;
			}
			
			String phone;
			while(true) {
				System.out.print("전화번호 입력 >> ");
				phone=in.readLine();
				
				String phoneReg="(01[016789])-\\d{3,4}-\\d{4}";
				if(phone!=null && !phone.equals("") && !Pattern.matches(phoneReg, phone)) {
					System.out.println("[입력오류]전화번호를 형식에 맞게 입력해 주세요.");
					continue;
				}
				
				break;
			}
			
			System.out.print("주소 입력 >> ");
			String address=in.readLine();
			
			String birthday;
			while(true) {
				System.out.print("생년월일 입력 >> ");
				birthday=in.readLine();
				
				String birthdayReg="(18|19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
				if(birthday!=null && !birthday.equals("") && !Pattern.matches(birthdayReg, birthday)) {
					System.out.println("[입력오류]생년월일을 형식에 맞게 입력해 주세요.");
					continue;
				}
				
				break;
			}
			
			//반환받은 StudentDTO 인스턴스를 변경값으로 필드값 변경
			// => 변경값을 입력하지 않은 경우 필드값 미변경
			if(name!=null && !name.equals("")) student.setName(name);
			if(phone!=null && !phone.equals("")) student.setPhone(phone);
			if(address!=null && !address.equals("")) student.setAddress(address);
			if(birthday!=null && !birthday.equals("")) student.setBirthday(birthday);
			
			//STUDENT 테이블의 저장행을 변경하고 결과를 반환받아 저장
			// => StudnetDAO 클래스의 updateStudent() 메소드 호출
			int rows=StudentDAO.getStudentDAO().updateStudent(student);
			
			System.out.println("[처리결과]"+rows+"명의 학생정보를 변경 하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//[학생정보 삭제] 메뉴를 선택한 경우 호출되는 메소드
	// => 키보드로 학번을 입력받아 STUDENT 테이블의 저장행을 삭제 후 결과 출력
	// => 학번의 학생정보가 존재하지 않는 경우 메세지 출력 후 메소드 종료
	private void removeStudent() {
		System.out.println("### 학생정보 삭제 ###");
		try {
			int no;
			while(true) {
				System.out.print("학번 입력 >> ");
				String noTemp=in.readLine();
				
				if(noTemp==null || noTemp.equals("")) {
					System.out.println("[입력오류]학번을 반드시 입력해 주세요.");
					continue;
				}
				
				String noReg="\\d{4}";
				if(!Pattern.matches(noReg, noTemp)) {
					System.out.println("[입력오류]학번은 4자리 숫자로 입력해 주세요.");
					continue;
				}
				
				no=Integer.parseInt(noTemp);
				break;
			}

			//STUDENT 테이블의 저장행을 삭제하고 결과를 반환받아 저장
			// => StudnetDAO 클래스의 deleteStudent() 메소드 호출
			int rows=StudentDAO.getStudentDAO().deleteStudent(no);
			
			if(rows>0) {
				System.out.println("[처리결과]"+rows+"명의 학생정보를 삭제 하였습니다.");
			} else {
				System.out.println("[처리결과]삭제하고자 하는 학생정보가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//[학생이름 검색] 메뉴를 선택한 경우 호출되는 메소드
	// => 검색된 학생정보가 존재하지 않는 경우 메세지 출력 후 메소드 종료
	private void searchNameStudent() {
		System.out.println("### 학생정보 목록 ###");
		
		try {
			String name;
			while(true) {
				System.out.print("이름 입력 >> ");
				name=in.readLine();
				
				if(name==null || name.equals("")) {
					System.out.println("[입력오류]이름을 반드시 입력해 주세요.");
					continue;
				}
				
				String nameReg="^[가-힣]{2,5}$";
				if(!Pattern.matches(nameReg, name)) {
					System.out.println("[입력오류]이름은 2~5 범위의 한글만 입력 가능합니다.");
					continue;
				}
				
				break;
			}
			
			//이름을 전달하여 STUDENT 테이블의 저장행을 검색하여 결과를 반환받아 저장
			// => StudnetDAO 클래스의 selectNameStudent() 메소드 호출
			List<StudentDTO> studentList=StudentDAO.getStudentDAO().selectNameStudent(name);
			
			if(studentList.isEmpty()) {
				System.out.println("[처리결과]검색된 학생정보가 존재하지 않습니다.");
				return;
			}
			
			System.out.println("==========================================================");
			System.out.println("학번\t이름\t전화번호\t주소\t\t생년월일");
			System.out.println("==========================================================");
			for(StudentDTO student:studentList) {
				System.out.println(student.getNo()+"\t"+student.getName()
					+"\t"+student.getPhone()+"\t"+student.getAddress()
					+"\t"+student.getBirthday());
			}
			System.out.println("==========================================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//[전체학생 검색] 메뉴를 선택한 경우 호출되는 메소드
	// => STUDENT 테이블의 저장행을 모두 검색 후 결과 출력
	// => 검색된 학생정보가 존재하지 않는 경우 메세지 출력 후 메소드 종료
	private void searchAllStudent() {
		System.out.println("### 학생정보 목록 ###");
		
		//STUDENT 테이블의 저장행을 모두 검색하여 결과를 반환받아 저장
		// => StudnetDAO 클래스의 selectAllStudent() 메소드 호출
		List<StudentDTO> studentList=StudentDAO.getStudentDAO().selectAllStudent();
		
		if(studentList.isEmpty()) {
			System.out.println("[처리결과]저장된 학생정보가 존재하지 않습니다.");
			return;
		}
		
		System.out.println("==========================================================");
		System.out.println("학번\t이름\t전화번호\t주소\t\t생년월일");
		System.out.println("==========================================================");
		for(StudentDTO student:studentList) {
			System.out.println(student.getNo()+"\t"+student.getName()
				+"\t"+student.getPhone()+"\t"+student.getAddress()
				+"\t"+student.getBirthday());
		}
		System.out.println("==========================================================");
	}
}






