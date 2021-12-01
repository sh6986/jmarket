package com.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.MyUtil;

public class FaqDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertFaq(FaqDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO faq(num,subject,content,category) VALUES(faq_seq.NEXTVAL,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getCategory());
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public List<FaqDTO> listFaq(String category){
		List<FaqDTO> list=new ArrayList<FaqDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		FaqDTO dto;
		MyUtil util=new MyUtil();
		try {
			sql="SELECT num, subject, content, category FROM faq WHERE category=? ORDER BY num DESC";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto=new FaqDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCategory(rs.getString("category"));
				list.add(dto);
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
		}
		return list;
	}
	
	public List<FaqDTO> listFaq(){
		List<FaqDTO> list=new ArrayList<FaqDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		FaqDTO dto;
		MyUtil util=new MyUtil();
		try {
			sql="SELECT num, subject, content, category FROM faq ORDER BY num DESC";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto=new FaqDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCategory(rs.getString("category"));
				list.add(dto);
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
		}
		return list;
	}
	
	public int updateFaq(FaqDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE faq SET subject=?, content=?, category=? WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getCategory());
			pstmt.setInt(4, dto.getNum());
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public FaqDTO readFaq(int num){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		FaqDTO dto=new FaqDTO();
		MyUtil util=new MyUtil();
		try {
			sql="SELECT num, subject, content, category FROM faq WHERE num=? ORDER BY num DESC";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCategory(rs.getString("category"));
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
		}
		return dto;
	}
	
	public int deleteFaq(int num){
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		try {
			sql="DELETE FROM faq WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
}
