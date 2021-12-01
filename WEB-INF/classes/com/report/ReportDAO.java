package com.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReportDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertReport(ReportDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO report(num, id, category, title, content) VALUES(REPORT_SEQ.NEXTVAL, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getCategory());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM report";
			pstmt=conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM report r JOIN member1 m ON r.id = m.id";
			
			if(condition.equalsIgnoreCase("name")) {
				sql  += " WHERE INSTR(name, ?) = 1 ";
			} else if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("-", "");
				sql += " WHERE TO_CHAR(created, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" +condition+ ", ?) >= 1";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public List<ReportDTO> listReport(int offset, int rows){
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT num, name, category, title, views, TO_CHAR(r.created, 'YYYY-MM-DD') created FROM report r JOIN member1 m ON r.id = m.id ORDER BY num DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportDTO dto = new ReportDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setCategory(rs.getString("category"));
				dto.setTitle(rs.getString("title"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
			try {
				rs.close();
			} catch (Exception e2) {
			}
		}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public List<ReportDTO> listReport(int offset, int rows, String condition, String keyword) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT num, name, category, title, views, TO_CHAR(r.created, 'YYYY-MM-DD') created FROM report r JOIN member1 m ON r.id = m.id";
			
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("-", "");
				sql +=" WHERE TO_CHAR(created, 'YYYYMMDD') = ?";
			} else if(condition.equalsIgnoreCase("name")) {
				sql +=" WHERE INSTR(name, ?) = 1";
			} else {
				sql +=" WHERE INSTR("+condition+", ?) >= 1";
			}
			sql += " ORDER BY num DESC";
			sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportDTO dto = new ReportDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setCategory(rs.getString("category"));
				dto.setTitle(rs.getString("title"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	public int updateViews(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE report SET views = views+1 WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public ReportDTO readReport(int num) {
		ReportDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT num, r.id, name, category, title, content, r.created, views From report r JOIN member1 m ON r.id = m.id WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ReportDTO();
				dto.setNum(rs.getInt("num"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setCategory(rs.getString("category"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated(rs.getString("created"));
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
				
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			} 
		
		return dto;
	}
	
	public int updateReport(ReportDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql ="UPDATE report SET category=?, title=?, content=? WHERE num=? AND id=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getNum());
			pstmt.setString(5, dto.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public int deleteReport(int num, String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(id.equals("admin")) {
				sql = "DELETE FROM report WHERE num=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM report WHERE num=? and id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, id);
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	
	
	
	
}
