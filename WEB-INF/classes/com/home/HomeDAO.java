package com.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.buy.BuyDTO;
import com.sale.SaleDTO;
import com.util.DBConn;

public class HomeDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<SaleDTO> bestSalelist(){
		List<SaleDTO> list=new ArrayList<SaleDTO>();
		PreparedStatement pstmt=null;
		String sql;
		SaleDTO dto=null;
		ResultSet rs=null;
		try {
			sql="SELECT num, sprice, subject, hitCount, fileName1, fileName2, TO_CHAR(created,'YYYY-MM-DD') created FROM sale ORDER BY hitCount DESC";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int n=0;
			while(rs.next()) {
				n++;
				dto = new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSprice(rs.getInt("sprice"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setFileName1(rs.getString("fileName1"));
				dto.setFileName1(rs.getString("fileName2"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
				if(n==3) {
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	public List<BuyDTO> bestBuylist(){
		List<BuyDTO> list=new ArrayList<BuyDTO>();
		PreparedStatement pstmt=null;
		String sql;
		BuyDTO dto=null;
		ResultSet rs=null;
		try {
			sql="SELECT num, price, subject, views, imagename, TO_CHAR(created,'YYYY-MM-DD') created FROM buy ORDER BY views DESC";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int n=0;
			while(rs.next()) {
				n++;
				dto = new BuyDTO();
				dto.setNum(rs.getInt("num"));
				dto.setPrice(Integer.toString(rs.getInt("price")));
				dto.setSubject(rs.getString("subject"));
				dto.setViews(rs.getInt("views"));
				dto.setImageName(rs.getString("imagename"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
				if(n==3) {
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
}
