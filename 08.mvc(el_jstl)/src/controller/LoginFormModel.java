package site.itwill.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//클라이언트가 "/loginForm.do"로 요청한 경우 컨트롤러에 의해 인스턴스로 생성되는 클래스
// => "user_login.jsp"로 포워드 이동되도록 인스턴스 반환
public class LoginFormModel implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward actionForward=new ActionForward();
		actionForward.setForward(true);
		actionForward.setPath("model_two/user_login.jsp");
		return actionForward;
	}
}







