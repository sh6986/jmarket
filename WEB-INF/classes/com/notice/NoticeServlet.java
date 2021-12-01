package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/notice/*")
@MultipartConfig
public class NoticeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cp = req.getContextPath();
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (uri.indexOf("list.do") == -1 && info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "notice";
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null)
			current_page = Integer.parseInt(page);

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "title";
			keyword = "";
		}
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		int rows = 10; // 한페이지 표시할 데이터 개수
		String numPerPage = req.getParameter("rows");
		if (numPerPage != null)
			rows = Integer.parseInt(numPerPage);
		int dataCount, total_page;

		if (keyword.length() != 0)
			dataCount = dao.dataCount(condition, keyword);
		else
			dataCount = dao.dataCount();
		
		total_page = util.pageCount(rows, dataCount);

		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;

		List<NoticeDTO> list;
		if (keyword.length() != 0)
			list = dao.listNotice(offset, rows, condition, keyword);
		else
			list = dao.listNotice(offset, rows);

		// 공지글
		List<NoticeDTO> listNotice = null;
		listNotice = dao.listNotice();
		for (NoticeDTO dto : listNotice) {
			dto.setCreated(dto.getCreated().substring(0, 10));
		}

		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 리스트 글번호 만들기
		int listNum, n = 0;
		for (NoticeDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListnum(listNum);

			try {
				Date date = sdf.parse(dto.getCreated());

				// gap = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // 일자
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60); // 시간
				dto.setGap(gap);
			} catch (Exception e) {
			}

			dto.setCreated(dto.getCreated().substring(0, 10));
			n++;
		}

		String query = "";
		String listUrl;
		String articleUrl;

		listUrl = cp + "/notice/list.do?rows=" + rows;
		articleUrl = cp + "/notice/article.do?page=" + current_page + "&rows=" + rows;
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");

			listUrl += "&" + query;
			articleUrl += "&" + query;
		}

		String paging = util.paging(current_page, total_page, listUrl);

		// 포워딩 jsp에 넘길데이터~~
		req.setAttribute("list", list);
		req.setAttribute("listNotice", listNotice);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("rows", rows);

		forward(req, resp, "/WEB-INF/page/notice/list.jsp");

	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (!info.getId().equals("admin")) {
			resp.sendRedirect(req.getContextPath() + "/notice/list.do");
			return;
		}

		String rows = req.getParameter("rows");

		req.setAttribute("mode", "created");
		req.setAttribute("rows", rows);
		forward(req, resp, "/WEB-INF/page/notice/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String rows = req.getParameter("rows");

		if (!info.getId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}

		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = new NoticeDTO();

		dto.setId(info.getId());

		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		if (req.getParameter("notice") != null) {
			dto.setNotice(Integer.parseInt(req.getParameter("notice")));
		}

		Part p = req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if (map != null) {
			String afilename = map.get("afilename");
			String bfilename = map.get("bfilename");
			long filesize = p.getSize();

			dto.setAfilename(afilename);
			dto.setBfilename(bfilename);
			dto.setFilesize(filesize);
		}

		dao.insertNotice(dto);

		resp.sendRedirect(cp + "/notice/list.do?rows=" + rows);
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "title";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page + "&rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		// 조회수
		dao.updateHitCount(num);

		// 게시물 가져오기
		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?" + query);
			return;
		}

		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

		// 이전글, 다음글
		NoticeDTO preReadDto = dao.preReadNotice(dto.getNum(), condition, keyword);
		NoticeDTO nextReadDto = dao.nextReadNotice(dto.getNum(), condition, keyword);

		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);

		forward(req, resp, "/WEB-INF/page/notice/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		int num = Integer.parseInt(req.getParameter("num"));

		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		// 글을 등록한 사람만 수정 가능
		if (!info.getId().equals(dto.getId())) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/page/notice/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		NoticeDTO dto = new NoticeDTO();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		dto.setNum(num);
		if (req.getParameter("notice") != null) {
			dto.setNotice(Integer.parseInt(req.getParameter("notice")));
		}
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setAfilename(req.getParameter("afilename"));
		dto.setBfilename(req.getParameter("bfilename"));
		dto.setFilesize(Long.parseLong(req.getParameter("filesize")));

		Part p = req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if (map != null) {
			// 기존파일 삭제
			if (req.getParameter("afilename").length() != 0) {
				FileManager.doFiledelete(pathname, req.getParameter("afilename"));
			}

			// 새로운 파일
			String afilename = map.get("afilename");
			String bfilename = map.get("bfilename");
			long size = p.getSize();
			dto.setAfilename(afilename);
			dto.setBfilename(bfilename);
			dto.setFilesize(size);
		}
		dao.updateNotice(dto);

		resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page + "&rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?" + query);
			return;
		}

		if (!info.getId().equals(dto.getId()) && !info.getId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?" + query);
			return;
		}

		if (dto.getAfilename() != null && dto.getAfilename().length() != 0)
			FileManager.doFiledelete(pathname, dto.getAfilename());

		dao.deleteNotice(num);

		resp.sendRedirect(cp + "/notice/list.do?" + query);
	}

	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		int num = Integer.parseInt(req.getParameter("num"));

		NoticeDTO dto = dao.readNotice(num);
		boolean b = false;

		if (dto != null) {
			b = FileManager.doFiledownload(dto.getAfilename(), dto.getAfilename(), pathname, resp);
		}

		if (!b) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일 다운로드가 불가능합니다.'); history.back();</script>");
		}

	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		if (!info.getId().equals(dto.getId())) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&rows=" + rows);
			return;
		}
		FileManager.doFiledelete(pathname, dto.getAfilename());

		dto.setBfilename("");
		dto.setAfilename("");
		dto.setFilesize(0);
		dao.updateNotice(dto);

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);

		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/page/notice/created.jsp");
	}
}
