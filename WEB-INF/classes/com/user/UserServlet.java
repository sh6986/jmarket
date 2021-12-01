package com.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MainServlet;

@WebServlet("/user/*")
public class UserServlet extends MainServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("newuser.do")!=-1) {
			insertUser(req, resp);
		} else if(uri.indexOf("newuser_ok.do")!=-1) {
			submitUser(req, resp);
		} else if(uri.indexOf("updateuser.do")!=-1) {
			updateuser(req, resp);
		} else if(uri.indexOf("updateuser_ok.do")!=-1) {
			updateubmitUser(req, resp);
		}
	}
	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/page/user/login.jsp";
		forward(req, resp, path);
	}
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		UserDAO dao = new UserDAO();
		
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		UserDTO dto = dao.readUser(id);
		
		System.out.println(id+"aaa");
		System.out.println(pwd+"aaaabb");
		
		if(dto == null || ! dto.getPwd().equals(pwd) || dto.getEnabled() !=1 ) {
			resp.sendRedirect(cp+"/home/home.do");
			return;
		}
		
		HttpSession session=req.getSession();
		session.setMaxInactiveInterval(30*60);
		
		SessionInfo info = new SessionInfo();
		info.setId(dto.getId());
		info.setName(dto.getName());

		session.setAttribute("member", info);
		resp.sendRedirect(cp+"/home/home.do");
	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		session.invalidate();
		resp.sendRedirect(cp);
	}
	
	protected void insertUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/page/user/newuser.jsp");
	}
	
	protected void submitUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDAO dao=new UserDAO();
		UserDTO dto = new UserDTO();
		dto.setId(req.getParameter("userId"));
		dto.setPwd(req.getParameter("userPwd"));
		dto.setName(req.getParameter("userName"));
		dto.setBirth(req.getParameter("birth"));
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		if (email1.length() != 0 && email2.length() != 0) {
			dto.setEmail(email1 + "@" + email2);
		}
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1.length() != 0 && tel2.length() != 0 && tel3.length() != 0) {
			dto.setTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		
		dto.setHome(req.getParameter("zip")); 
		dto.setAddr1(req.getParameter("addr2"));
		dto.setAddr2(req.getParameter("addr1"));

		try {
			dao.insertUser(dto);
		} catch (Exception e) {
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/page/user/newuser.jsp");
			return;
		}

		String cp=req.getContextPath();
		resp.sendRedirect(cp+"/user/login.do");
	}
	
	protected void updateuser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{	
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String id = info.getId();
		
		UserDTO dto = new UserDTO();
		UserDAO dao = new UserDAO();
		
		try {
			dto = dao.readUser(id);
		} catch (Exception e) {
		}
		if(dto.getTel().length()!=0) {
			String[] tel = dto.getTel().split("-");
			dto.setTel1(tel[0]);
			dto.setTel1(tel[1]);
			dto.setTel1(tel[2]);
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("title", "회원 정보 수정");
		req.setAttribute("mode", "update");
		forward(req, resp, "/WEB-INF/page/user/newuser.jsp");
	}
	
	protected void updateubmitUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		UserDTO dto = new UserDTO();
		UserDAO dao = new UserDAO();
		dto.setId(req.getParameter("userId"));
		dto.setPwd(req.getParameter("userPwd"));
		dto.setName(req.getParameter("userName"));
		dto.setBirth(req.getParameter("birth"));
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		if (email1 != null && email1.length() != 0 && email2 != null
				&& email2.length() != 0) {
			dto.setEmail(email1 + "@" + email2);
		}
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1 != null && tel1.length() != 0 && tel2 != null
				&& tel2.length() != 0 && tel3 != null && tel3.length() != 0) {
			dto.setTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		dto.setHome(req.getParameter("zip"));
		dto.setAddr1(req.getParameter("addr1"));
		dto.setAddr2(req.getParameter("addr2"));
		
		try {
			dao.updateUser(dto);
		} catch (Exception e) {
		}
		
		String cp = req.getContextPath();
		resp.sendRedirect(cp+"/home/home.do");
	}
}
