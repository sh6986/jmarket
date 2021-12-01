package com.buy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BuyDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertBuy(BuyDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO buy(num,id,subject,content,imagename,buying,productname,price,how) VALUES(buy_seq.NEXTVAL,?,?,?,?,0,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImageName());
			pstmt.setString(5, dto.getProductName());
			pstmt.setString(6, dto.getPrice());
			pstmt.setString(7, dto.getHow());
			result = pstmt.executeUpdate();
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
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM buy";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);

		} catch (Exception e) {
			System.out.println(e.toString());
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
		
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
        int result=0;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
        	if(condition.equals("created")) {
        		keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM buy b JOIN member1 m ON b.Id=m.Id WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
        	} else if(condition.equals("userName")) {
        		sql="SELECT NVL(COUNT(*), 0) FROM buy b JOIN member1 m ON b.Id=m.Id WHERE INSTR(Name, ?) = 1 ";
        	} else {
        		sql="SELECT NVL(COUNT(*), 0) FROM buy b JOIN member1 m ON b.Id=m.Id WHERE INSTR("+(condition.equals("id")?"b.":"");
        		sql+=condition + ", ?) >= 1 ";
        	}
        	
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, keyword);

            rs=pstmt.executeQuery();

            if(rs.next())
                result=rs.getInt(1);
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
        return result;
    }
	
	public List<BuyDTO> listBuy(int offset, int rows, int div) {
		List<BuyDTO> list=new ArrayList<BuyDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql =" SELECT name, num, b.id, subject, imagename, TO_CHAR(b.created,'YYYY-MM-DD') created, buying, views"
			    +" FROM buy b "
			    +" JOIN member1 m ON b.Id=m.Id "
			    +" WHERE buying = "+div
			    +" ORDER BY num DESC "
			    +" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);

			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				BuyDTO dto=new BuyDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageName(rs.getString("imagename"));
				dto.setCreated(rs.getString("created"));
				dto.setBuying(rs.getInt("buying"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setViews(rs.getInt("views"));
				list.add(dto);
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
		return list;
	}
	
	public List<BuyDTO> listBuy(int offset, int rows, String condition, String keyword, int div) {
		List<BuyDTO> list=new ArrayList<BuyDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql =" SELECT name, num, b.id, subject, imagename, TO_CHAR(b.created,'YYYY-MM-DD') created, buying, views"
				+" FROM buy b "
				+" JOIN member1 m ON b.Id=m.Id ";
			
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("-", "");
				sql += " WHERE TO_CHAR(created, 'YYYYMMDD') = ?AND buying = 0 ";
			} else if(condition.equalsIgnoreCase("userName")) {
				sql +=" WHERE INSTR(b.id, ?) = 1 AND buying = 0 ";
			} else {
				sql +=" WHERE INSTR("+(condition.equals("id")?"b.":"");
				sql +=condition+", ?) >= 1 AND buying = "+div;
			}
			
			sql+=" ORDER BY num DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BuyDTO dto=new BuyDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageName(rs.getString("imagename"));
				dto.setCreated(rs.getString("created"));
				dto.setBuying(rs.getInt("buying"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setViews(rs.getInt("views"));
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
	
	public int updateviews(int num)  {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE buy SET views=views+1 WHERE num=?";
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
	
	public BuyDTO readBuy(int num) {
		BuyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT num, name, b.Id, subject, content, price, productname, TO_CHAR(b.created,'YYYY-MM-DD') created, buying, views, imagename, how "
				+ " FROM buy b "
				+ " JOIN member1 m ON b.Id=m.Id "
				+ " WHERE num = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new BuyDTO();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageName(rs.getString("imagename"));
				dto.setCreated(rs.getString("created"));
				dto.setBuying(rs.getInt("buying"));
				dto.setId(rs.getString("id"));
				dto.setViews(rs.getInt("views"));
				dto.setContent(rs.getString("content"));
				dto.setPrice(rs.getString("price"));
				dto.setProductName(rs.getString("productname"));
				dto.setHow(rs.getString("how"));
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
	
	public int updateBuy(BuyDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE buy SET subject=?, content=?, imagename=?, price=?, productName=?, how=? WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageName());
			pstmt.setString(4, dto.getPrice());
			pstmt.setString(5, dto.getProductName());
			pstmt.setString(6, dto.getHow());
			pstmt.setInt(7, dto.getNum());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public int deleteBuy(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM buy WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
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
	
	public BuyDTO frontBuy(int num, String condition, String keyword, int div) {
		BuyDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
            if(keyword.length() != 0) {
                sql = "SELECT num, subject FROM buy b JOIN member1 m ON b.Id = m.Id ";
                if(condition.equals("id")) {
                    sql += " WHERE ( INSTR(b.id, ?) = 1) ";
                } else if(condition.equals("created")) {
                	keyword=keyword.replaceAll("-", "");
                    sql += " WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ";
                } else {
                	sql += " WHERE ( INSTR("+condition+", ?) > 0) ";
                }
                sql += " AND buying = " +div
                	+ "AND (num > ? ) ORDER BY num ASC FETCH  FIRST  1  ROWS  ONLY ";

                pstmt=conn.prepareStatement(sql);
                pstmt.setString(1, keyword);
               	pstmt.setInt(2, num);
            } else {
                sql = "SELECT num, subject FROM buy WHERE num > ? AND buying = "+ div +"  ORDER BY num ASC "
                    + " FETCH  FIRST  1  ROWS  ONLY ";

                pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1, num);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BuyDTO();
                dto.setNum(rs.getInt("num"));
                dto.setSubject(rs.getString("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
                
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return dto;
	}	
	
	public BuyDTO BackBuy(int num, String condition, String keyword, int div) {
		BuyDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
            if(keyword.length() != 0) {
                sql = "SELECT num, subject FROM buy b JOIN member1 m ON b.Id = m.Id ";
                if(condition.equals("id")) {
                    sql += " WHERE ( INSTR(b.id, ?) = 1) ";
                } else if(condition.equals("created")) {
                	keyword=keyword.replaceAll("-", "");
                    sql += " WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ";
                } else {
                	sql += " WHERE ( INSTR("+condition+", ?) > 0) ";
                }
                sql +=" AND buying = " + div
                	+ " AND (num < ? ) ORDER BY num ASC FETCH  FIRST  1  ROWS  ONLY ";

                pstmt=conn.prepareStatement(sql);
                pstmt.setString(1, keyword);
               	pstmt.setInt(2, num);
            } else {
                sql = "SELECT num, subject FROM buy WHERE num < ? AND buying = "+ div+ " ORDER BY num ASC "
                    + " FETCH  FIRST  1  ROWS  ONLY ";

                pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1, num);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BuyDTO();
                dto.setNum(rs.getInt("num"));
                dto.setSubject(rs.getString("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
                
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return dto;
	}
	
	public int updateBuying(int num) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql;
		
		try {
			sql = "UPDATE buy SET buying=1 WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
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
