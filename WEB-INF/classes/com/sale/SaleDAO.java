package com.sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SaleDAO {
	private Connection conn = DBConn.getConnection();
	
	//글 등록 
	
	public int insertSale(SaleDTO dto) {
		int result=0;
		String sql; 
		PreparedStatement pstmt = null;
		
		try {
			sql = "INSERT INTO sale(num, id, subject,pname, sprice, content, fileName1, fileName2,fileName3, name) VALUES(sale_seq.NEXTVAL,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getPname());
			pstmt.setInt(4, dto.getSprice());
			pstmt.setString(5, dto.getContent());
			pstmt.setString(6, dto.getFileName1());
			pstmt.setString(7, dto.getFileName2());
			pstmt.setString(8, dto.getFileName3());
			pstmt.setString(9, dto.getName());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				} 
			}
		}
		
		return result;
		
	}
	
	
	
	//전체 데이터 개수
	public int dataCount() {
		int result = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql;
		
		
		try {
			sql = "SELECT NVL(COUNT(num),0) cnt FROM sale";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
		
	}
	
	
	//검색모드에서 전체 개수 구하기 
	public int dataCount(String condition,String keyword) {
		int result = 0; 
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM sale s JOIN member1 m ON s.id = m.id";
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("-", "");
				sql+="WHERE TO_CHAR(s.created, 'YYYYMMDD')=?";
			}else if(condition.equalsIgnoreCase("name")) {
				sql="WHERE INSTR(name,?)=1";
			}else {
				sql+="WHERE INSTR("+condition+",?)>=1";
			}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result =rs.getInt(1);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		return result;
	}
	
	
	
	public List<SaleDTO> listSale(int offset, int rows, int div){
		List<SaleDTO> list = new ArrayList<SaleDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SaleDTO dto = null;
		String sql;
		
		try {
			sql = " SELECT num, m.name, subject, hitCount, fileName1,TO_CHAR(s.created,'YYYY-MM-DD') created "
				+ " FROM sale s "
				+ " JOIN member1 m ON s.id = m.id "
				+ " WHERE sold = "+div
				+ " ORDER BY num DESC "
				+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			rs=pstmt.executeQuery();

			while(rs.next()) {
				dto = new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setFileName1(rs.getString("fileName1"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	
	
	
	//검색에서 리스트 
	public List<SaleDTO> listSale(int offset, int rows, String condition, String keyword, int div) {
		List<SaleDTO> list = new ArrayList<SaleDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT num,name,subject,hitCount,fileName1, ");
			sb.append(" s.created ");
			sb.append(" FROM sale s ");
			sb.append(" JOIN member1 m ON s.id = m.id ");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("-", "");
				sb.append(" WHERE TO_CHAR(s.created, 'YYYY-MM-DD') =? ");
			}else if(condition.equalsIgnoreCase("name")) {
				sb.append(" WHERE INSTR(s.id,?)=1 AND sold = 0");
			}else {
				sb.append(" WHERE INSTR("+condition+", ?)>=1 AND sold ="+div);
			}
			sb.append(" ORDER BY num DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				SaleDTO dto = new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				dto.setFileName1(rs.getString("fileName1"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
		
	}
	
	
	
	
	public List<SaleDTO> listSale(){
		List<SaleDTO> list = new ArrayList<SaleDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT num,name,subject,hitCount,fileName1");
			sb.append(" TO_CHAR(s.created, 'YYYY-MM-DD') created");
			sb.append(" FROM sale s ");
			sb.append(" JOIN member1 m ON s.id = m.id");
			sb.append(" WHERE sale =1 ");
			sb.append(" ORDER BY num DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SaleDTO dto = new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setFileName1(rs.getString("fileName1"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	
	//글보기 
	public SaleDTO readSale(int num) {
		SaleDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ;
		
		sql = "SELECT num, s.id, m.name, subject, pname, sprice, content, fileName1,fileName2,fileName3, hitCount, s.created ";
		sql += "FROM sale s JOIN member1 m ON s.id = m.id WHERE num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			System.out.println(num);
			if(rs.next()) {
				dto=new SaleDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setPname(rs.getString("pname"));
				dto.setSprice(rs.getInt("sprice"));
				dto.setContent(rs.getString("content"));
				dto.setFileName1(rs.getString("fileName1"));
				dto.setFileName2(rs.getString("fileName2"));
				dto.setFileName3(rs.getString("fileName3"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}

	
	//이전글 
	
	public SaleDTO preReadSale(int num, String condition, String keyword, int div) {
		SaleDTO dto = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		
		try {
			if(keyword.length()!=0) {
				sb.append("SELECT num,subject FROM sale s JOIN member1 m ON s.id = m.id ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
                	sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
				}else {
                	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1) AND sold = "+div);
                }
	                sb.append("         AND (num > ? )  ");
	                sb.append(" ORDER BY num ASC  ");
	                sb.append(" FETCH  FIRST  1  ROWS  ONLY");
	
	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setString(1, keyword);
	                pstmt.setInt(2, num);
			}else {
				sb.append("SELECT num, subject FROM sale s JOIN member1 m ON s.id=m.id ");
				   sb.append(" WHERE num > ?  AND sold ="+div );
	                sb.append(" ORDER BY num ASC  ");
	                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto= new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
    
        return dto;		
	}
	
	
	//다음글 
	
	public SaleDTO nextReadSale(int num, String condition, String keyword, int div) {
		SaleDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if(keyword.length()!=0) {
				sb.append("SELECT num,subject FROM sale s JOIN member1 m ON s.id = m.id ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
					sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ");
				}else {
					sb.append(" WHERE(INSTR("+condition+",?)>=1) AND sold = "+div );
				}
				sb.append(" AND (num < ? ) ");
				sb.append(" ORDER BY num DESC  ");
	            sb.append(" FETCH  FIRST  1  ROWS  ONLY ");
	            
	            pstmt=conn.prepareStatement(sb.toString());
	            pstmt.setString(1, keyword);
	            pstmt.setInt(2, num);
				}else {
					sb.append(" SELECT num, subject FROM sale s JOIN member1 m ON s.id=m.id ");
					sb.append(" WHERE num < ? AND sold =" +div );
					sb.append(" ORDER BY num DESC ");
					sb.append(" FETCH FIRST 1 ROWS ONLY ");
					
	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
				}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SaleDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
    
        return dto;
		}
	

	
	public int updateHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " UPDATE sale SET hitCount= hitCount+1 WHERE num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
		}

	
	//글 수정

	public int updateSale(SaleDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE sale SET subject=?, pname=?,content=?,fileName1=?, fileName2=?, fileName3=? ";
			sql +=" WHERE num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getPname());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getFileName1());
			pstmt.setString(5, dto.getFileName2());
			pstmt.setString(6, dto.getFileName3());
			pstmt.setInt(7, dto.getNum());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	
	
	
	public int deleteSale(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM sale WHERE num=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	
	
	public int updateSold(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " UPDATE sale SET sold= 1 WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
		}


}
	
	
	
	
	
	
	
	
	
	
