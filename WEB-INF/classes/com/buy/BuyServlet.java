package com.buy;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.user.SessionInfo;
import com.util.FileManager;
import com.util.FileServlet;
import com.util.MyUtil;

@WebServlet("/buy/*")
@MultipartConfig
public class BuyServlet extends FileServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;
	private BuyDAO dao = new BuyDAO();

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		HttpSession session=req.getSession();
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root+"photo"+File.separator+"buy";
		
		
		if(uri.indexOf("list1.do")!=-1) {
			list1(req, resp);
		} else if(uri.indexOf("list2.do")!=-1) {
			list2(req, resp);
		} else if(uri.indexOf("write.do")!=-1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do")!=-1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("updateBuying.do")!=-1) {
			updateBuying(req, resp);
		}
		
	}
	
	protected void list1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		MyUtil util = new MyUtil();
		int div = 0;
		String page = req.getParameter("page");
		int current_page = 1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "subject";
			keyword="";
		}
		int dataCount;
		if(keyword.length()==0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		int offset = (current_page-1)*rows;
		List<BuyDTO> list = null;
		if(keyword.length()==0) {	
			list = dao.listBuy(offset, rows, div);
		} else {
			list = dao.listBuy(offset, rows, condition, keyword,div);
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl=cp+"/buy/list1.do";
		String articleUrl=cp+"/buy/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl+="&"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("now", "list1");
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		forward(req, resp, "/WEB-INF/page/buy/list1.jsp");
	}
	
	protected void list2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		MyUtil util = new MyUtil();
		int div = 1;
		String page = req.getParameter("page");
		int current_page = 1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "subject";
			keyword="";
		}
		int dataCount;
		if(keyword.length()==0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		int offset = (current_page-1)*rows;
		List<BuyDTO> list = null;
		if(keyword.length()==0) {	
			list = dao.listBuy(offset, rows,div);
		} else {
			list = dao.listBuy(offset, rows, condition, keyword,div);
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl=cp+"/buy/list2.do";
		String articleUrl=cp+"/buy/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl+="&"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("now", "list2");
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		forward(req, resp, "/WEB-INF/page/buy/list2.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("state", "write");
		forward(req, resp, "/WEB-INF/page/buy/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		BuyDTO dto = new BuyDTO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		dto.setId(info.getId());
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setPrice(req.getParameter("price"));
		dto.setProductName(req.getParameter("productname"));
		dto.setHow(req.getParameter("how"));
		
		String filename = null;
		Part p = req.getPart("upload");
		Map<String, String> map = fileUpload(p, pathname);
		if(map != null) {
			filename = map.get("fileName");
		}
		
		if(filename!=null) {
			dto.setImageName(filename);
		}
		System.out.println(filename);
		dao.insertBuy(dto);
		resp.sendRedirect(cp+"/buy/list1.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		MyUtil util  = new MyUtil();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		dao.updateviews(num);
		
		int div = 0;
		if(req.getParameter("now").equals("list1")) {
			div = 0;
		} else {
			div = 1;
		}
		
		BuyDTO dto = dao.readBuy(num);
		
		if(dto==null) { // 게시물이 없으면 다시 리스트로
			resp.sendRedirect(cp+"/buy/list1.do?"+query);
			return;
		}
		
		String trade ="";
		if(dto.getHow().equals("post")) {
			trade = "택배거래";
		} else if(dto.getHow().equals("direct")) {
			trade = "직접거래";
		} else if(dto.getHow().equals("safety")) {
			trade = "안전거래";
		}
		
		req.setAttribute("listdiv", div);
		req.setAttribute("front", dao.frontBuy(num, condition, keyword, div));
		req.setAttribute("back", dao.BackBuy(num, condition, keyword,div));
		req.setAttribute("dto", dto);
		req.setAttribute("trade", trade);
		req.setAttribute("page", page);
		req.setAttribute("query", query);
		forward(req, resp, "/WEB-INF/page/buy/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String page=req.getParameter("page");
		
		int num=Integer.parseInt(req.getParameter("num"));
		BuyDTO dto=dao.readBuy(num);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/buy/list1.do?page="+page);
			return;
		}
		
		if(! dto.getId().equals(info.getId())) {
			resp.sendRedirect(cp+"/buy/list1.do?page="+page);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("state", "update");
		forward(req, resp, "/WEB-INF/page/buy/write.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		BuyDTO dto = new BuyDTO();
		
		String page = req.getParameter("page");
		if(req.getMethod().equalsIgnoreCase("")) {
			resp.sendRedirect(cp+"/photo/list1.do?page="+page);
			return;
		}
		
		String uploadFileName=req.getParameter("upload");
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setPrice(req.getParameter("price"));
		dto.setProductName(req.getParameter("productname"));
		dto.setHow(req.getParameter("how"));
		
		Part p = req.getPart("upload");
		Map<String, String> map = fileUpload(p, pathname);
		if(map != null) {
			String fileName = map.get("fileName");
			FileManager.doFiledelete(pathname, uploadFileName);
			dto.setImageName(fileName);
		} else {
			dto.setImageName(uploadFileName);
		}
		try {
			dao.updateBuy(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/buy/list1.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword = "";
		}
		keyword=URLDecoder.decode(keyword,"utf-8");
		String query = "page="+page;
		if(keyword.length()!=0) {
			query="&condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
		}
		BuyDTO dto = dao.readBuy(num);
		FileManager.doFiledelete(pathname, dto.getImageName());
		
		dao.deleteBuy(num);
		resp.sendRedirect(cp+"/buy/list1.do?"+query);
	}
	
	protected void updateBuying(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword = "";
		}
		keyword=URLDecoder.decode(keyword,"utf-8");
		String query = "page="+page;
		if(keyword.length()!=0) {
			query="&condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
		}
		dao.updateBuying(num);
		resp.sendRedirect(cp+"/buy/list1.do?"+query);
	}
}
