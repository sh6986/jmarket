package com.question;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MainServlet;

@WebServlet("/faq/*")
public class FaqServlet extends MainServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		if(uri.indexOf("faq_list.do")!=-1) {
			faq_list(req, resp);
		}else if(uri.indexOf("faq_created.do")!=-1) {
			faq_createdForm(req, resp);
		}else if(uri.indexOf("faq_created_ok.do")!=-1) {
			faq_createdSubmit(req, resp);
		}else if(uri.indexOf("faq_update.do")!=-1) {
			faq_updateForm(req, resp);
		}else if(uri.indexOf("faq_update_ok.do")!=-1) {
			faq_updateSubmit(req, resp);
		}else if(uri.indexOf("faq_delete_ok.do")!=-1) {
			faq_deleteSubmit(req, resp);
		}
		
	}
	
	protected void faq_list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAO();
		String category=req.getParameter("category");
		
		if(category==null) {
			category="goods";
		}
		
		List<FaqDTO> list=dao.listFaq(category);
		req.setAttribute("list",list);
		
		forward(req, resp, "/WEB-INF/page/question/faq_list.jsp");
		
	}
	
	protected void faq_createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/page/question/faq_created.jsp");
		
	}
	
	protected void faq_createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		FaqDTO dto=new FaqDTO();
		dto.setCategory(req.getParameter("category"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		
		FaqDAO dao=new FaqDAO();
		dao.insertFaq(dto);
		
		
		resp.sendRedirect(cp+"/faq/faq_list.do");
		
	}
	
	protected void faq_updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAO();
		FaqDTO dto=dao.readFaq(Integer.parseInt(req.getParameter("num")));
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		
		forward(req, resp, "/WEB-INF/page/question/faq_created.jsp");
	}
	
	protected void faq_updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		FaqDAO dao=new FaqDAO();
		FaqDTO dto=new FaqDTO();
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setCategory(req.getParameter("category"));
		dto.setContent(req.getParameter("content"));
		dto.setSubject(req.getParameter("subject"));
		dao.updateFaq(dto);
		
		resp.sendRedirect(cp+"/faq/faq_list.do");
	}
	
	protected void faq_deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		FaqDAO dao=new FaqDAO();
		dao.deleteFaq(Integer.parseInt(req.getParameter("num")));
		
		resp.sendRedirect(cp+"/faq/faq_list.do");
	}

	
}
