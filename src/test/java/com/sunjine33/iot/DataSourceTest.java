package com.sunjine33.iot;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class DataSourceTest {

	@Inject
	private DataSource ds;//Connection을 전부 담당한다

	@Test
	public void test() {
		try (Connection con = ds.getConnection()) {
			System.out.println(con);
			assertNotEquals(0, getAss(con));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String SelectSQL = "select count(*) from Test";

	private int getAss(Connection con) throws Exception {
		int result = 0;
		try (Statement pstmt = con.createStatement()) {
			ResultSet rs = pstmt.executeQuery(SelectSQL);
			if (rs.next())
				result = rs.getInt(1);
			
			System.out.println(result);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
