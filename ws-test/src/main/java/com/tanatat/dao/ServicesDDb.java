package com.tanatat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.exceptions.DatabaseException;
import com.tanatat.exceptions.DbConnException;
import com.tanatat.exceptions.LoginFailException;
import com.tanatat.model.MemberRequestModel;
import com.tanatat.model.ProvinceRequestModel;
import com.tanatat.model.RegisterRequestModel;
import com.tanatat.util.DBUtil;

public class ServicesDDb {

	private static final Logger log = LoggerFactory.getLogger(ServicesDDb.class);
	private static final String SERVICE_D_DB = "jdbc/service_d_db"; // Member Database
	private static final String FEELTOP2013_REPORT_DB = "jdbc/feeltop2013_report_db"; // Province Database
	private static final SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

	// Get Member
	public MemberRequestModel checkLogin(String username, String password)
			throws LoginFailException, DatabaseException {
		MemberRequestModel memberModel = new MemberRequestModel();
		Connection conn = DBUtil.getJndiDb(SERVICE_D_DB);
		if (conn != null) {
			PreparedStatement stmt = null;
			ResultSet rst = null;
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("SELECT * FROM  MEMBER_PROFILE ");
			sbSql.append("WHERE member_username = ? and member_password = md5(?) ");
			try {
				stmt = conn.prepareStatement(sbSql.toString());
				stmt.setString(1, username);
				stmt.setString(2, password);
				rst = stmt.executeQuery();
				if (rst.next()) {
					memberModel.setMemberId(rst.getInt("member_id"));
					memberModel.setUserName(rst.getString("member_username"));
				} else {
					throw new LoginFailException();
				}
			} catch (SQLException e) {
				log.error("Error while getting checkMemberByUsername : " + e.getMessage());
				throw new DatabaseException();
			} finally {
				DBUtil.closeResultSet(rst);
				DBUtil.closeStatement(stmt);
				DBUtil.closeDbConnection(conn);
			}
		}
		return memberModel;
	}

	// Insert Member
	public int registerMember(RegisterRequestModel request) throws DbConnException, DatabaseException {
		Connection conn = DBUtil.getJndiDb(SERVICE_D_DB);
		int res = -1;
		if (conn != null) {
			PreparedStatement stmtInsert = null;
			try {
				log.debug("insert Register");
				StringBuilder sbIn = new StringBuilder();
				sbIn.append(
						"insert into MEMBER_PROFILE (member_username,member_password,member_mobileno,member_email,member_fname,");
				sbIn.append("member_lname,member_address,member_city,member_province,member_zipcode,member_create) ");
				sbIn.append("values (?,md5(?),?,?,?,?,?,?,?,?,now()) ");
				stmtInsert = conn.prepareStatement(sbIn.toString());
				stmtInsert.setString(1, request.getRegisterUserName());
				stmtInsert.setString(2, request.getRegisterPassword());
				stmtInsert.setString(3, request.getRegisterMobileNo());
				stmtInsert.setString(4, request.getRegisterEmail());
				stmtInsert.setString(5, request.getRegisterFirstName());
				stmtInsert.setString(6, request.getRegisterLastName());
				stmtInsert.setString(7, request.getRegisterAddress());
				stmtInsert.setString(8, request.getRegisterCity());
				stmtInsert.setString(9, request.getRegisterProvince());
				stmtInsert.setString(10, request.getRegisterZipcode());
				stmtInsert.execute();
				res = 0;
			} catch (SQLException e) {
				DBUtil.rollbackDB(conn);
				log.error("Error while Register : " + e.getMessage());
				throw new DatabaseException();
			} finally {
				DBUtil.closeStatement(stmtInsert);
				DBUtil.closeDbConnection(conn);
			}
		} else {
			throw new DbConnException();
		}

		return res;
	}

	// Get Province
	public ProvinceRequestModel checkprovince(String provinceName) {
		ProvinceRequestModel provinceModel = new ProvinceRequestModel();
		Connection conn = DBUtil.getJndiDb(FEELTOP2013_REPORT_DB);
		if (conn != null) {
			PreparedStatement stmt = null;
			ResultSet rst = null;
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("SELECT * FROM RP_ADD_PROVINCE");
			sbSql.append("WHERE prv_name = ?");
			try {
				stmt = conn.prepareStatement(sbSql.toString());
				stmt.setString(1, provinceName);
				rst = stmt.executeQuery();
				if (rst.next()) {
					provinceModel.setprovinceID(rst.getInt("province_id"));
					provinceModel.setprovinceName(rst.getString("province_name"));
				}
			} catch (SQLException e) {
				log.error("Error while getting ProvinceName : " + e.getMessage());
			} finally {
				DBUtil.closeResultSet(rst);
				DBUtil.closeStatement(stmt);
				DBUtil.closeDbConnection(conn);
			}
		}
		return provinceModel;
	}

	public ArrayList<ProvinceRequestModel> getProvinceList() {
		Connection conn = DBUtil.getJndiDb(FEELTOP2013_REPORT_DB);
		ArrayList<ProvinceRequestModel> prvList = new ArrayList<ProvinceRequestModel>();
		if (conn != null) {
			PreparedStatement stmt = null;
			ResultSet rst = null;
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("SELECT * FROM member_profile pro  ");
			sbSql.append("INNER JOIN member_token token ON pro.member_id = token.member_id ");
			try {
				stmt = conn.prepareStatement(sbSql.toString());
				rst = stmt.executeQuery();
				while (rst.next()) {
					ProvinceRequestModel prv = new ProvinceRequestModel();
					prv.setprovinceName(rst.getString("member_username"));
					prvList.add(prv);
				}
			} catch (Exception e) {
				log.error("Error while getting getProvinceName : " + e.getMessage());
			} finally {
				DBUtil.closeResultSet(rst);
				DBUtil.closeStatement(stmt);
				DBUtil.closeDbConnection(conn);
			}
		}
		return prvList;
	}
}