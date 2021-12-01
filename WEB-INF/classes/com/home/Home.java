package com.home;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buy.BuyDTO;
import com.sale.SaleDTO;
import com.util.MainServlet;

@WebServlet("/home/*")
public class Home extends MainServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		HomeDAO dao=new HomeDAO();
		List<SaleDTO> list=dao.bestSalelist();
		
		List<BuyDTO> list2=dao.bestBuylist();
		
		req.setAttribute("list", list);
		req.setAttribute("list2", list2);
		
		if(uri.indexOf("home.do")!=-1) {
			forward(req, resp, "/WEB-INF/page/home/home.jsp");
		}
	}
}
