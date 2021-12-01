package com.report;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.SessionInfo;
import com.util.MyUtil;

@WebServlet("/report/*")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cp = req.getContextPath();
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("user");
		
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReportDAO dao = new ReportDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page =1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition == null) {
			condition = "title";
			keyword = "";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword =  URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount=dao.dataCount();
		} else {
			dataCount=dao.dataCount(condition, keyword);
		}
		
		int rows = 5;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page -1) * rows;
		
		List<ReportDTO> list = null;
		if(keyword.length() == 0) {
			list = dao.listReport(offset, rows);
		} else {
			list = dao.listReport(offset, rows, condition, keyword);
		}
		
		int listNum, n = 0;
		for(ReportDTO dto:list) {
			listNum = dataCount-(offset + n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query="";
		if(keyword.length() != 0) {
			query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl = cp+"/report/list.do";
		String articleUrl = cp+"/report/article.do?page="+current_page;
		if(query.length() != 0) {
			listUrl += "?"+query;
			articleUrl +="&"+query;
		}
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/page/report/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/page/report/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		ReportDAO dao = new ReportDAO();
		ReportDTO dto = new ReportDTO();
		
		dto.setId(info.getId());
		dto.setCategory(req.getParameter("category"));
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		
		dao.insertReport(dto);
		
		resp.sendRedirect(cp+"/report/list.do");
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		ReportDAO dao = new ReportDAO();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition == null) {
			condition = "title";
			keyword="";
		}
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length() != 0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		dao.updateViews(num);
		
		ReportDTO dto = dao.readReport(num);
		if(dto == null) {
			resp.sendRedirect(cp+"/report/list.do"+query);
			return;
		}
		dto.setContent(dto.getContent().replaceAll("\n", ""));
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("query", query);
		
		forward(req, resp, "/WEB-INF/page/report/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		ReportDAO dao = new ReportDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));
		ReportDTO dto = dao.readReport(num);
		
		if(dto == null) {
			resp.sendRedirect(cp+"/report/list.do?page="+page);
			return;
		}
		
		if(! dto.getId().equals(info.getId())) {
			resp.sendRedirect(cp+"/report/list.do?page="+page);
			return;
		}
		
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		
		forward(req, resp, "/WEB-INF/page/report/write.jsp");
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		ReportDAO dao = new ReportDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String page = req.getParameter("page");
		
		if(req.getMethod().equalsIgnoreCase("get")) {
			resp.sendRedirect(cp+"/report/list.do?page="+page);
			return;
		}
		
		ReportDTO dto = new ReportDTO();
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setCategory(req.getParameter("category"));
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setId(info.getId());
		
		dao.updateReport(dto);
		
		resp.sendRedirect(cp+"/report/list.do?page="+page);
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		ReportDAO dao = new ReportDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String page=req.getParameter("page");
		int num=Integer.parseInt(req.getParameter("num"));
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "title";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query = "page="+page;
		if(keyword.length() != 0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		dao.deleteReport(num, info.getId());
		
		resp.sendRedirect(cp+"/report/list.do?"+query);
	}
}
