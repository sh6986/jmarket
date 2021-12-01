package com.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class UserDAO {
	private Connection conn=DBConn.getConnection();
	
	public UserDTO readUser(String id) {
		UserDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT m1.Id, Name, pwd,");
			sb.append("      enabled, created, ");
			sb.append("      TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("      email, tel,");
			sb.append("      home, addr1, addr2");
			sb.append("      FROM member1 m1");
			sb.append("      LEFT OUTER JOIN member2 m2 ");
			sb.append("      ON m1.id=m2.id");
			sb.append("      WHERE m1.id=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto=new UserDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setEnabled(rs.getInt("enabled"));
				dto.setCreated(rs.getString("created"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				if(dto.getTel()!=null) {
					String[] ss=dto.getTel().split("-");
					if(ss.length==3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				dto.setEmail(rs.getString("email"));
				if(dto.getEmail()!=null) {
					String[] ss=dto.getEmail().split("@");
					if(ss.length==2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setHome(rs.getString("home"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	public void insertUser(UserDTO dto) throws Exception {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "INSERT INTO member1(id, pwd, name) VALUES (?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getName());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql = "INSERT INTO member2(id, birth, email, tel, home, addr1, addr2) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getHome());
			pstmt.setString(6, dto.getAddr1());
			pstmt.setString(7, dto.getAddr2());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void updateUser(UserDTO dto) throws Exception {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE member1 SET pwd=? WHERE id=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getId());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql = "UPDATE member2 SET birth=?, email=?, tel=?, home=?, addr1=?, addr2=? WHERE id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getHome());
			pstmt.setString(5, dto.getAddr1());
			pstmt.setString(6, dto.getAddr2());
			pstmt.setString(7, dto.getId());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void deleteUser(String id) throws Exception {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE member1 SET enabled=0 WHERE id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql="DELETE FROM  member2 WHERE id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
