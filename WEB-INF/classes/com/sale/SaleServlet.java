package com.sale;

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

@MultipartConfig
@WebServlet("/sale/*")
public class SaleServlet extends FileServlet{

	private static final long serialVersionUID = 1L;
	private String pathname;
	private SaleDAO dao = new SaleDAO(); 
	
	

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cp = req.getContextPath();
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root+"photo"+File.separator+"sale";
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		}else if(uri.indexOf("list2.do")!=-1) {
			list2(req, resp);	
		}else if(uri.indexOf("write.do")!=-1) {
			writeForm(req, resp);
		}else if(uri.indexOf("write_ok.do")!=-1) {
			writeSubmit(req, resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		}else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		}else if(uri.indexOf("read.do")!=-1) {
			read(req, resp);
		}else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		}else if (uri.indexOf("pay.do")!=-1) {
			pay(req,resp);
		}else if (uri.indexOf("sold.do")!=-1) {
			sold(req,resp);
		}
		
		
		
	}


	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		int div = 0;
		String page = req.getParameter("page");
		int current_page = 1; 
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		String condition =req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "subject";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword,"utf-8");
		}
		
		int rows = 10; 
		int dataCount;
		if(keyword.length()!=0) {
			dataCount=dao.dataCount(condition,keyword);
		}else {
			dataCount=dao.dataCount();
		}
		
		int total_page = util.pageCount(rows, dataCount);
		
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset = (current_page-1)*rows;
		
		List<SaleDTO> list;
		if(keyword.length()==0) {
			list=dao.listSale(offset, rows,div);
		}else {
			list=dao.listSale(offset, rows, condition, keyword, div);
		}

		//페이징 처리 
		
		String listUrl = cp+"/sale/list.do";
		String articleUrl = cp+"/sale/read.do?page="+current_page;
        String query = "";
        if(keyword.length()!=0) {
           query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
        }
        
		if(query.length()!=0) {
	            listUrl+="?"+query;
	            articleUrl = articleUrl+"&"+query;
	         }
	         
	    String paging = util.paging(current_page, total_page,listUrl);
	    
	    req.setAttribute("now", "list");
        req.setAttribute("list", list); 
        req.setAttribute("paging", paging);
        req.setAttribute("total_page", total_page); 
        req.setAttribute("page", current_page); 
        req.setAttribute("dataCount", dataCount); 
        req.setAttribute("articleUrl", articleUrl); 
        req.setAttribute("condition", condition); 
        req.setAttribute("keyword", keyword);
        
        forward(req, resp, "/WEB-INF/page/sale/list.jsp");
	
	
	}
	
	
	
	protected void list2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		int div =1;
		
		String page = req.getParameter("page");
		int current_page = 1; 
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		String condition =req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "subject";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword,"utf-8");
		}
		
		int rows = 10; 
		
		
		int dataCount;
		if(keyword.length()!=0) {
			dataCount=dao.dataCount(condition,keyword);
		}else {
			dataCount=dao.dataCount();
		}
		
		int total_page = util.pageCount(rows, dataCount);
		
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset = (current_page-1)*rows;
		
		List<SaleDTO> list;
		if(keyword.length()!=0) {
			list=dao.listSale(offset, rows, condition, keyword, div);
		}else {
			list=dao.listSale(offset, rows, div);
		}

		//페이징 처리 
		
		String listUrl = cp+"/sale/list2.do";
		String articleUrl = cp+"/sale/read.do?page="+current_page;
        String query = "";
        if(keyword.length()!=0) {
           query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
        }
        
		if(query.length()!=0) {
	            listUrl+="?"+query;
	            articleUrl = articleUrl+"&"+query;
	         }
	         
	    String paging = util.paging(current_page, total_page,listUrl);
	    
	    req.setAttribute("now", "list2");
        req.setAttribute("list", list); 
        req.setAttribute("paging", paging);
        req.setAttribute("total_page", total_page); 
        req.setAttribute("page", current_page); 
        req.setAttribute("dataCount", dataCount); 
        req.setAttribute("articleUrl", articleUrl); 
        req.setAttribute("condition", condition); 
        req.setAttribute("keyword", keyword);
        
        forward(req, resp, "/WEB-INF/page/sale/list2.jsp");
	
	
	}
	
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/page/sale/write.jsp");
	}
	
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		SaleDTO dto = new SaleDTO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		System.out.println(info.getName());
		dto.setId(info.getId());
		dto.setName(info.getName());
		dto.setSubject(req.getParameter("subject"));
		dto.setPname(req.getParameter("pname"));
		dto.setSprice(Integer.parseInt(req.getParameter("sprice")));
		dto.setContent(req.getParameter("content"));
		
		
		//FILE 1
		String fileName = null;
		Part p = req.getPart("upload1");
		Map<String, String> map1 = fileUpload(p, pathname);
		if(map1 !=null) {
			fileName = map1.get("fileName");
			if(fileName!=null) {
				dto.setFileName1(fileName);
			}
		}
		
		
		
		//FILE 2
		Part pp = req.getPart("upload2");
		if(pp!=null) {
			Map<String, String> map2 = fileUpload(pp, pathname);
			if(map2 !=null) {
				fileName = map2.get("fileName");
				if(fileName!=null) {
					dto.setFileName2(fileName);
				}
			}
		}
		
		//FILE3
			Part ppp = req.getPart("upload3");
			if(ppp!=null) {
			Map<String, String> map3 = fileUpload(ppp, pathname);
			if(map3 !=null) {
				fileName = map3.get("fileName");
				if(fileName!=null) {
					dto.setFileName3(fileName);
				}
			}
		}	
		dao.insertSale(dto);
		resp.sendRedirect(cp+"/sale/list.do");
		}
		
	protected void read(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String now = req.getParameter("now");
		
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
		
		// 조회수
		dao.updateHitCount(num);
		
		int div = 0;
		if(req.getParameter("now").equals("list")) {
			div=0;
		}else {
			div = 1;
		}
		
		
		// 게시물 가져오기
		SaleDTO dto=dao.readSale(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/sale/list.do?"+query);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		// 이전글/다음글
		SaleDTO preReadDto = dao.preReadSale(dto.getNum(), condition, keyword, div);
		SaleDTO nextReadDto = dao.nextReadSale(dto.getNum(), condition, keyword, div);
		
		req.setAttribute("listdiv", div);
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/page/sale/read.jsp");
	}
		
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String cp = req.getContextPath();
		String page= req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));
		
		SaleDTO dto = dao.readSale(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/sale/list.do?page="+page);
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/page/sale/write.jsp");
	}
	
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		SaleDTO dto = new SaleDTO();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/sale/list.do?page="+page);
			return;
		}
		
		dto.setNum(num);
		dto.setPname(req.getParameter("pname"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setFileName1(req.getParameter("fileName1"));
		dto.setFileName2(req.getParameter("fileName2"));
		dto.setFileName3(req.getParameter("fileName3"));
		
		System.out.println(dto);
	//파일 처리 
		
		//file1
		Part p = req.getPart("upload1");
		Map<String, String> map1 = fileUpload(p, pathname);

		if(map1 != null) {
			// 기존파일 삭제
			if(req.getParameter("fileName1").length()!=0) {
				FileManager.doFiledelete(pathname, req.getParameter("fileName1"));
			}
		// 새로운 파일	
		String fileName = map1.get("fileName");
		dto.setFileName1(fileName);
		}

			
		//file2
		Part pp = req.getPart("upload2");
		Map<String, String> map2 = fileUpload(pp, pathname);
		if(map2 != null) {
			// 기존파일 삭제
			if(req.getParameter("fileName2").length()!=0) {
				FileManager.doFiledelete(pathname, req.getParameter("fileName2"));
				}	
		// 새로운 파일
		String fileName = map2.get("fileName");
		dto.setFileName2(fileName);
		}
			
		
		//file3
		Part ppp = req.getPart("upload3");
		Map<String, String> map3 = fileUpload(ppp, pathname);
		if(map3 != null) {
			// 기존파일 삭제
			if(req.getParameter("fileName3").length()!=0) {
				FileManager.doFiledelete(pathname, req.getParameter("fileName3"));
				}	
		// 새로운 파일
		String fileName = map3.get("fileName");
		dto.setFileName3(fileName);
		}

		dao.updateSale(dto);
		resp.sendRedirect(cp+"/sale/list.do?page="+page);
	
	}
	
	
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//수정에서 파일만 삭제 
		
		String cp=req.getContextPath();
	
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		SaleDTO dto=dao.readSale(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/sale/list.do?page="+page);
			return;
		}
	
		
		// 파일삭제
		FileManager.doFiledelete(pathname, dto.getFileName1());
		FileManager.doFiledelete(pathname, dto.getFileName2());
		FileManager.doFiledelete(pathname, dto.getFileName3());
		
		
		// 파일명과 파일크기 변경
		dto.setFileName1("");
		dto.setFileName2("");
		dto.setFileName3("");
		dao.updateSale(dto);
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/page/sale/write.jsp");			
	}
		

	
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword = URLDecoder.decode(keyword,"utf-8");
		String query = "page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		SaleDTO dto = dao.readSale(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/sale/list.do?"+query);
			return;
		}
		
		
		//파일삭제
		if(dto.getFileName1()!=null && dto.getFileName1().length()!=0) {
			FileManager.doFiledelete(pathname, dto.getFileName1());}
		
		if(dto.getFileName2()!=null && dto.getFileName2().length()!=0) {
			FileManager.doFiledelete(pathname, dto.getFileName2());}
		
		if(dto.getFileName3()!=null && dto.getFileName3().length()!=0) {
			FileManager.doFiledelete(pathname, dto.getFileName3());}
		
		dao.deleteSale(num);
		
		resp.sendRedirect(cp+"/sale/list.do?page="+page);
		
	}
	
	protected void pay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/page/sale/jmpay.jsp";
		forward(req, resp, path);
	}
	
	
	protected void sold(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		dao.updateSold(num);
		resp.sendRedirect(cp+"/sale/list.do?"+query);
	
	}
	
	

	
	
	
	
}



