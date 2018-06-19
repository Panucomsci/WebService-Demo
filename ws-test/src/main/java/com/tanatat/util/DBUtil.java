package com.tanatat.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {

	private static Logger log = LoggerFactory.getLogger(DBUtil.class);

	public static void init() {

		Connection conn = getJndiDb("jdbc/service_d_db");
		try {
			if (conn != null) {
				log.info("[OK] services_d_db is connected");
			}
		} catch (Exception e) {
			log.error("[ERROR] services_d_db can not connect");
		} finally {
			closeDbConnection(conn);
		}
	}

	public static Connection getJndiDb(String jndiName) {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = null;

			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup(jndiName);

			conn = ds.getConnection();

		} catch (SQLException e) {
			log.error("Error while getting " + jndiName + " connection : " + e.getMessage());
		} catch (Exception e) {
//			log.error("Error while getting " + jndiName + " connection : " + e.getMessage());
		}
		return conn;
	}

	public static void closeDbConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			log.error("Error while closing database connection : " + e.getMessage());
		}
	}

	public static void closeStatement(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			log.error("Error while closing sql statement : " + e.getMessage());
		}
	}

	public static void closeResultSet(ResultSet rst) {
		try {
			if (rst != null) {
				rst.close();
				rst = null;
			}
		} catch (SQLException e) {
			log.error("Error while closing sql result set : " + e.getMessage());
		}
	}

	public static void rollbackDB(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			log.error("Error while rollback database connection : " + e.getMessage());
		}
	}

	public static int runSql(Connection conn, String sql) {
		int res = 0;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			res = stmt.executeUpdate();
		} catch (Exception e) {
			log.error("Error while execute update sql : " + e.getMessage());
		} finally {
			closeStatement(stmt);
		}
		return res;
	}
}