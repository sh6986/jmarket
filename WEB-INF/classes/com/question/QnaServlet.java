package com.question;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.SessionInfo;
import com.util.MainServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MainServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		if(uri.indexOf("qna_list.do")!=-1) {
			qna_list(req, resp);
		}else if(uri.indexOf("qna_created.do")!=-1) {
			qna_createdForm(req, resp);
		}else if(uri.indexOf("qna_created_ok.do")!=-1){
			qna_createdSubmit(req, resp);
		}else if(uri.indexOf("qna_update.do")!=-1) {
			qna_updateForm(req, resp);
		}else if(uri.indexOf("qna_update_ok.do")!=-1) {
			qna_updateSubmit(req, resp);
		}else if(uri.indexOf("answer_list.do")!=-1){
			answer_list(req, resp);
		}else if(uri.indexOf("answer_created.do")!=-1) {
			answer_createdForm(req, resp);
		}else if(uri.indexOf("answer_created_ok.do")!=-1) {
			answer_createdSubmit(req, resp);
		}else if(uri.indexOf("answer_delete_ok.do")!=-1) {
			answer_deleteSubmit(req, resp);
		}
	}
	protected void qna_list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		MyUtil util=new MyUtil();
		
		//로그인하지 않았으면 로그인창으로 돌아간다.
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
//		if(info==null) {
//			forward(req, resp, "/WEB-INF/page/user/login.jsp");
//			return;
//		}
		
		//날짜계산
		Calendar cal=Calendar.getInstance();  //현재날짜, 시간
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
		Calendar min=(Calendar)cal.clone();
		min.set(Calendar.MONTH,cal.get(Calendar.MONTH)-6);
		Calendar month1=(Calendar)cal.clone();
		month1.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		
		req.setAttribute("mode", "qna");
		req.setAttribute("minyear", min.get(Calendar.YEAR));
		req.setAttribute("calyear", cal.get(Calendar.YEAR));
		req.setAttribute("minmonth", min.get(Calendar.MONTH));
		req.setAttribute("calmonth", cal.get(Calendar.MONTH));
		req.setAttribute("minday", min.get(Calendar.DATE));
		req.setAttribute("calday", cal.get(Calendar.DATE));
		req.setAttribute("month1year", month1.get(Calendar.YEAR));
		req.setAttribute("month1month", month1.get(Calendar.MONTH));
		req.setAttribute("month1day", month1.get(Calendar.DATE));
		
		//페이징
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		
		String status=req.getParameter("status");
		if(status==null) {
			status="-1";
		}
		
		//매개변수로 넘겨줄 값이므로 앞에 0이 붙으면 안된다.
		String yyy=""+min.get(Calendar.YEAR);
		String yyy2=""+cal.get(Calendar.YEAR);
		String mmm=""+min.get(Calendar.MONTH);
		String mmm2=""+cal.get(Calendar.MONTH);
		String ddd=""+min.get(Calendar.DATE);
		String ddd2=""+cal.get(Calendar.DATE);
		
		//계산할 값이므로 10보다 작으면 0을 붙여줘야한
		
		String day1=""+min.get(Calendar.YEAR)+numch(mmm)+numch(ddd);
		String day2=""+cal.get(Calendar.YEAR)+numch(mmm2)+numch(ddd2);
		
		if(req.getParameter("year")!=null) {
			day1=req.getParameter("year")+numch(req.getParameter("month"))+numch(req.getParameter("day"));
			day2=req.getParameter("year2")+numch(req.getParameter("month2"))+numch(req.getParameter("day2"));
			
			yyy=req.getParameter("year");
			yyy2=req.getParameter("year2");
			mmm=req.getParameter("month");
			mmm2=req.getParameter("month2");
			ddd=req.getParameter("day");
			ddd2=req.getParameter("day2");
		}
		
		// 전체 데이터 개수
		int dataCount;
		if((status.equals("-1")||status.equals("2"))) {  //처리상태 선택x이거나 전체선택일때
			dataCount=dao.dataCountId(info.getId(), day1, day2);
		}else {  
			dataCount=dao.dataCountId(info.getId(), Integer.parseInt(status), day1, day2);
		}
		
		// 전체 페이지 수
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
		int offset=(current_page-1)*rows;
		
		// 게시물 가져오기
		List<QnaDTO> list=dao.listQnaId(offset, rows, info.getId());
		
		if((status.equals("-1")||status.equals("2"))) { 
			list=dao.listQnaId(offset, rows, info.getId(), day1, day2);
		}else { 
			list=dao.listQnaId(offset, rows, info.getId(), Integer.parseInt(status), day1, day2);
		}

		// 리스트 글번호 만들기 
		int listNum, n=0;
		for(QnaDTO dto:list) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}

		String query="";
		String aa="year="+yyy+"&month="+mmm+"&day="+ddd;
		aa+="&year2="+yyy2+"&month2="+mmm2+"&day2="+ddd2;
		if(!status.equals("-1")) {  //상태조건 있을때
			query="status="+status;
		}
		
		query+="&"+aa;
		
		// 페이징 처리
		String listUrl=cp+"/qna/qna_list.do";
		if(query.length()!=0) {
			listUrl+="?"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		// 포워딩할 JSP로 넘길 속성
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("paging", paging);
		req.setAttribute("status", status);
		req.setAttribute("list", list);
		
		req.setAttribute("totalData", dao.dataCountId(info.getId()));  //총문의개수
		req.setAttribute("answerData", dao.dataCountId(info.getId(), 1));  //답변완료개수
		req.setAttribute("questionData", dao.dataCountId(info.getId(), 0));  //접수완료개수
		
		req.setAttribute("year", yyy);
		req.setAttribute("year2", yyy2);
		req.setAttribute("month", mmm);
		req.setAttribute("month2", mmm2);
		req.setAttribute("day", ddd);
		req.setAttribute("day2", ddd2);
		
		forward(req, resp, "/WEB-INF/page/question/qna_list.jsp");
	}
	
	protected void qna_createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
//		if(info==null) {
//			forward(req, resp, "/WEB-INF/page/user/login.jsp");
//			return;
//		}
		
		FaqDAO dao=new FaqDAO();
		String category=req.getParameter("category");
		
		if(category==null) {
			category="goods";
		}
		
		List<FaqDTO> list=dao.listFaq();
		
		req.setAttribute("mode", "created");
		req.setAttribute("list", list);
		req.setAttribute("listsize", list.size());
		
		forward(req, resp, "/WEB-INF/page/question/qna_created.jsp");
	}
	
	protected void qna_createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDTO dto=new QnaDTO();
		QnaDAO dao=new QnaDAO();
		dto.setCategory(req.getParameter("category"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		dto.setId(info.getId());
		
		dao.insertQna(dto);
		
		resp.sendRedirect(cp+"/qna/qna_list.do");
		
	}
	
	protected void qna_updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
//		if(info==null) {
//			forward(req, resp, "/WEB-INF/page/user/login.jsp");
//			return;
//		}
		
		FaqDAO dao=new FaqDAO();
		String category=req.getParameter("category");
		
		if(category==null) {
			category="goods";
		}
		
		QnaDAO qnadao=new QnaDAO();
		QnaDTO qnadto=qnadao.readQna(Integer.parseInt(req.getParameter("num")));
		req.setAttribute("dto", qnadto);
		
		List<FaqDTO> list=dao.listFaq();
		
		req.setAttribute("mode", "update");
		req.setAttribute("list", list);
		req.setAttribute("listsize", list.size());
		forward(req, resp, "/WEB-INF/page/question/qna_created.jsp");
	}
	
	protected void qna_updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDTO dto=new QnaDTO();
		QnaDAO dao=new QnaDAO();
		dto.setCategory(req.getParameter("category"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		
		dao.updateQna(dto);
		resp.sendRedirect(cp+"/qna/qna_list.do");
	}

	protected void answer_list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		MyUtil util=new MyUtil();
		
		//날짜계산
		Calendar cal=Calendar.getInstance();  //현재날짜, 시간
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
		Calendar min=(Calendar)cal.clone();
		min.set(Calendar.MONTH,cal.get(Calendar.MONTH)-6);
		Calendar month1=(Calendar)cal.clone();
		month1.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		
		req.setAttribute("mode", "answer");
		req.setAttribute("minyear", min.get(Calendar.YEAR));
		req.setAttribute("calyear", cal.get(Calendar.YEAR));
		req.setAttribute("minmonth", min.get(Calendar.MONTH));
		req.setAttribute("calmonth", cal.get(Calendar.MONTH));
		req.setAttribute("minday", min.get(Calendar.DATE));
		req.setAttribute("calday", cal.get(Calendar.DATE));
		req.setAttribute("month1year", month1.get(Calendar.YEAR));
		req.setAttribute("month1month", month1.get(Calendar.MONTH));
		req.setAttribute("month1day", month1.get(Calendar.DATE));
		
		//페이징
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		
		String status=req.getParameter("status");
		if(status==null) {
			status="-1";
		}
		//매개변수로 넘겨줄 값이므로 앞에 0이 붙으면 안된다.
		String yyy=""+min.get(Calendar.YEAR);
		String yyy2=""+cal.get(Calendar.YEAR);
		String mmm=""+min.get(Calendar.MONTH);
		String mmm2=""+cal.get(Calendar.MONTH);
		String ddd=""+min.get(Calendar.DATE);
		String ddd2=""+cal.get(Calendar.DATE);
		
		//계산할 값이므로 10보다 작으면 0을 붙여줘야한
		
		String day1=""+min.get(Calendar.YEAR)+numch(mmm)+numch(ddd);
		String day2=""+cal.get(Calendar.YEAR)+numch(mmm2)+numch(ddd2);
		
		if(req.getParameter("year")!=null) {
			day1=req.getParameter("year")+numch(req.getParameter("month"))+numch(req.getParameter("day"));
			day2=req.getParameter("year2")+numch(req.getParameter("month2"))+numch(req.getParameter("day2"));
			
			yyy=req.getParameter("year");
			yyy2=req.getParameter("year2");
			mmm=req.getParameter("month");
			mmm2=req.getParameter("month2");
			ddd=req.getParameter("day");
			ddd2=req.getParameter("day2");
		}

		// 전체 데이터 개수
		int dataCount;
		if(status.equals("-1")||status.equals("2")) {
			dataCount=dao.dataCountId("", day1, day2);
		}else { 
			dataCount=dao.dataCountId("", Integer.parseInt(status), day1, day2);
		}
		
		// 전체 페이지 수
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
		int offset=(current_page-1)*rows;

		
		// 게시물 가져오기
		List<QnaDTO> list=dao.listQnaId(offset, rows, "");
		if(status.equals("-1")||status.equals("2")) {
			list=dao.listQnaId(offset, rows, "", day1, day2);
		}else {  
			list=dao.listQnaId(offset, rows, "", Integer.parseInt(status), day1, day2);
		}
		
		// 리스트 글번호 만들기 
		int listNum, n=0;
		for(QnaDTO dto:list) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query="";
		if(!status.equals("-1")) {  //상태조건 있을때
			query="status="+status;
		}
		query+="&year="+yyy+"&month="+mmm+"&day="+ddd+"&year2="+yyy2+"&month2="+mmm2+"&day2="+ddd2;

		// 페이징 처리
		String listUrl=cp+"/qna/answer_list.do";
		if(query.length()!=0) {
			listUrl+="?"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		// 포워딩할 JSP로 넘길 속성
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("paging", paging);
		req.setAttribute("status", status);
		req.setAttribute("list", list);
		
		req.setAttribute("totalData", dao.dataCountId(""));  //총문의개수
		req.setAttribute("answerData", dao.dataCountId("",1));  //답변완료개수
		req.setAttribute("questionData", dao.dataCountId("",0));  //접수완료개수
		
		req.setAttribute("year", yyy);
		req.setAttribute("year2", yyy2);
		req.setAttribute("month", mmm);
		req.setAttribute("month2", mmm2);
		req.setAttribute("day", ddd);
		req.setAttribute("day2", ddd2);
		
		forward(req, resp, "/WEB-INF/page/question/qna_list.jsp");
		
	}
	
	protected void answer_createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
//		if(info==null) {
//			forward(req, resp, "/WEB-INF/page/user/login.jsp");
//			return;
//		}
		
		//선택한 고객문의 정보 가져오기
		int num=Integer.parseInt(req.getParameter("num"));
		QnaDAO dao=new QnaDAO();
		QnaDTO dto=dao.readQna(num);
		req.setAttribute("dto", dto);
		forward(req, resp, "/WEB-INF/page/question/answer_created.jsp");
	}
	
	protected void answer_createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		QnaDTO dto=new QnaDTO();
		dto.setAn_content(req.getParameter("an_content"));
		dto.setNum(Integer.parseInt(req.getParameter("dtonum")));
		
		System.out.println();
		dao.answerOk(dto);
		resp.sendRedirect(cp+"/qna/answer_list.do");
	}
	
	protected void answer_deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		dao.deleteQna(Integer.parseInt(req.getParameter("num")));
		
		resp.sendRedirect(cp+"/qna/answer_list.do");
	}
	
	protected String numch(String num) {
		if(Integer.parseInt(num)<10) {
			return "0"+num;
		}
		return ""+num;
	}
}
