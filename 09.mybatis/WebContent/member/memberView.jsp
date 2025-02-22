﻿<%@page import="site.itwill.dao.MyMemberXMLDAO"%>
<%@page import="site.itwill.dto.MyMemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id=request.getParameter("id");
	MyMemberDTO member=MyMemberXMLDAO.getDAO().selectMember(id);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mybatis</title>
</head>
<body>
	<h1 align="center">회원상세정보</h1>
	<hr>
	<table align="center" border="1" cellspacing="0" width="300">
		<tr>
			<th width="100">아이디</th>
			<td align="center" width="200"><%=member.getId() %></td>
		</tr>
		<tr>
			<th width="100">이름</th>
			<td align="center" width="200"><%=member.getName()%></td>
		</tr>
		<tr>
			<th width="100">전화번호</th>
			<td align="center" width="200"><%=member.getPhone() %></td>
		</tr>
		<tr>
			<th width="100">이메일</th>
			<td align="center" width="200"><%=member.getEmail() %></td>
		</tr>
		<tr align="right">
			<td colspan="2">
				<button type="button" onclick="location.href='memberUpdateForm.jsp?id=<%=member.getId()%>';">회원변경</button>
				<button type="button" onclick="location.href='memberDeleteAction.jsp?id=<%=member.getId()%>';">회원삭제</button>
				<button type="button" onclick="location.href='memberDisplay.jsp';">회원목록</button>
			</td>
		</tr>
	</table>
</body>
</html>