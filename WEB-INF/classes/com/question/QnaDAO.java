package com.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.MyUtil;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	public int insertQna(QnaDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO qna(num,category,subject,content,an_created,an_content,id) "
					+ "VALUES(qna_seq.NEXTVAL,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getAn_created());
			pstmt.setString(5, dto.getAn_content());
			pstmt.setString(6, dto.getId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public int updateQna(QnaDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qna SET category=?,subject=?,content=? WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getNum());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}


	public int answerOk(QnaDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qna SET an_created=SYSDATE, status=1, an_content=? WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getAn_content());
			pstmt.setInt(2, dto.getNum());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public QnaDTO readQna(int num) {
		QnaDTO dto = new QnaDTO();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		MyUtil util=new MyUtil();
		try {
			sql = "SELECT num,category,subject,content,TO_CHAR(created,'YYYY-MM-DD') created,"
					+ "TO_CHAR(an_created,'YYYY-MM-DD') an_created,status,an_content,id FROM qna WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setCategory(rs.getString("category"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content")); //////////////////////
				dto.setCreated(rs.getString("created"));
				dto.setAn_created(rs.getString("an_created"));
				dto.setStatus(rs.getInt("status"));
				dto.setAn_content(util.htmlSymbols(rs.getString("an_content")));
				dto.setId(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;

	}

	public int deleteQna(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM qna WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	public List<QnaDTO> listQnaId(int offset, int rows, String id) {
		QnaDTO dto = null;
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		MyUtil util=new MyUtil();
		try {
			sql = "SELECT num,category,subject,content,TO_CHAR(created,'YYYY-MM-DD') created,"
					+ "TO_CHAR(an_created,'YYYY-MM-DD') an_created,status,an_content,id FROM qna ";
			if (id.length() != 0) {
				sql += "WHERE id=?";
			}
			sql += " ORDER BY num DESC " + "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);

			} else {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, rows);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("num"));
				dto.setCategory(rs.getString("category"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCreated(rs.getString("created"));
				dto.setAn_created(rs.getString("an_created"));
				dto.setStatus(rs.getInt("status"));
				dto.setAn_content(util.htmlSymbols(rs.getString("an_content")));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

		}

		return list;
	}
	
	public List<QnaDTO> listQnaId(int offset, int rows, String id, String day1, String day2) {
		QnaDTO dto = null;
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		MyUtil util=new MyUtil();
		try {
			sql = "SELECT num,category,subject,content,TO_CHAR(created,'YYYY-MM-DD') created,"
					+ "TO_CHAR(an_created,'YYYY-MM-DD') an_created,status,an_content,id FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND ";
			}
			sql += "TO_CHAR(created,'YYYYMMDD') BETWEEN ? AND ? ORDER BY num DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setString(2, day1);
				pstmt.setString(3, day2);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, rows);
			} else {
				pstmt.setString(1, day1);
				pstmt.setString(2, day2);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("num"));
				dto.setCategory(rs.getString("category"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCreated(rs.getString("created"));
				dto.setAn_created(rs.getString("an_created"));
				dto.setStatus(rs.getInt("status"));
				dto.setAn_content(util.htmlSymbols(rs.getString("an_content")));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

		}

		return list;
	}


	// 데이터 개수
	public int dataCountId(String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna ";
			if (id.length() != 0) {
				sql += "WHERE id=?";
			}

			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
			}
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	public int dataCountId(String id, String day1, String day2) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND ";
			}
			sql+="TO_CHAR(created,'YYYYMMDD') BETWEEN ? AND ?";

			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setString(2, day1);
				pstmt.setString(3, day2);
			}else {
				pstmt.setString(1, day1);
				pstmt.setString(2, day2);
			}
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	public int dataCountId(String id, int status) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND ";
			}
			sql += "status=?";
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setInt(2, status);
			} else {
				pstmt.setInt(1, status);
			}
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	public int dataCountId(String id, int status, String day1, String day2) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND ";
			}
			sql += "status=? AND TO_CHAR(created,'YYYYMMDD') BETWEEN ? AND ?"; 
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setInt(2, status);
				pstmt.setString(3, day1);
				pstmt.setString(4, day2);
			} else {
				pstmt.setInt(1, status);
				pstmt.setString(2, day1);
				pstmt.setString(3, day2);
			}
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	public List<QnaDTO> listQnaId(int offset, int rows, String id, int status) {
		QnaDTO dto = null;
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		MyUtil util=new MyUtil();
		try {
			sql = "SELECT num,category,subject,content,TO_CHAR(created,'YYYY-MM-DD') created,"
					+ "TO_CHAR(an_created,'YYYY-MM-DD') an_created,status,an_content,id FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND";
			}
			sql += " status=? ORDER BY num DESC ";
			sql += "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setInt(2, status);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			} else {
				pstmt.setInt(1, status);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);

			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("num"));
				dto.setCategory(rs.getString("category"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCreated(rs.getString("created"));
				dto.setAn_created(rs.getString("an_created"));
				dto.setStatus(rs.getInt("status"));
				dto.setAn_content(util.htmlSymbols(rs.getString("an_content")));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

		}

		return list;
	}
	
	public List<QnaDTO> listQnaId(int offset, int rows, String id, int status, String day1, String day2) {
		QnaDTO dto = null;
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		MyUtil util=new MyUtil();
		try {
			sql = "SELECT num,category,subject,content,TO_CHAR(created,'YYYY-MM-DD') created,"
					+ "TO_CHAR(an_created,'YYYY-MM-DD') an_created,status,an_content,id FROM qna WHERE ";
			if (id.length() != 0) {
				sql += "id=? AND";
			}
			sql += " status=? AND TO_CHAR(created,'YYYYMMDD') BETWEEN ? AND ? ORDER BY num DESC ";
			sql += "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			pstmt = conn.prepareStatement(sql);
			if (id.length() != 0) {
				pstmt.setString(1, id);
				pstmt.setInt(2, status);
				pstmt.setString(3, day1);
				pstmt.setString(4, day2);
				pstmt.setInt(5, offset);
				pstmt.setInt(6, rows);
			} else {
				pstmt.setInt(1, status);
				pstmt.setString(2, day1);
				pstmt.setString(3, day2);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, rows);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("num"));
				dto.setCategory(rs.getString("category"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(util.htmlSymbols(rs.getString("content")));
				dto.setCreated(rs.getString("created"));
				dto.setAn_created(rs.getString("an_created"));
				dto.setStatus(rs.getInt("status"));
				dto.setAn_content(util.htmlSymbols(rs.getString("an_content")));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

		}

		return list;
	}

}
