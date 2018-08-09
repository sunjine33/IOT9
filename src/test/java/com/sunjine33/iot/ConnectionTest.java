package com.sunjine33.iot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;


public class ConnectionTest {
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://104.198.89.83:3306/testdb?useSSL=false";
	private static final String USER = "root";
	private static final String PW = "1234";

	private static final Connection Con = null;

	@Test
	public void testConnection() throws Exception {
		Class.forName(DRIVER);// DRIVER에 담겨있는 이름으로 클래스 찾아서 메모리에 올린다.

		try (Connection con = DriverManager.getConnection(URL, USER, PW)) {// Connection interface 타입의 con에
																			// 드라이버매니저.get함수에 URL,USER,PW를 넣고 반환값을
			// Connection 인터페이스 con에 입력
			System.out.println(con);
			String name = "홍" + System.currentTimeMillis();
			connInsert(con, name);
			String name2 = getLast(con);
			if (!name.equals(name2)) {
				throw new Exception("Not equals name!!");
			}else {
				System.out.println(name2);
			}
			int delc = delLast(con);
			if(delc != 1) {
				throw new Exception("Not valId delete!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	private static final String DelSQL = "delete from Test where id = last_insert_id();";
	private int delLast(Connection con) throws Exception {
		
			return writeDB(con,DelSQL);
		
	}
	
	
	private int writeDB(Connection con, String sql) throws Exception {
		return writeDB(con,sql,null);
	}
	
	
	private int writeDB(Connection con, String sql, String name) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			if(name != null)
				pstmt.setString(1, name);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		}
	}
	
	private static final String sql = "insert into Test(name) values(?)";
	private int connInsert(Connection con, String name) throws Exception {
		
			return writeDB(con,sql,name);
	}


	private static final String SelectSQL = "select name from Test where id = last_insert_id();";
	private String getLast(Connection con) throws Exception {
		// TODO Auto-generated method stub
		String result = null;
		try (Statement pstmt = con.createStatement()) {
			ResultSet rs = pstmt.executeQuery(SelectSQL);
			if(rs.next()) 
			result = rs.getString("name");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}


}
