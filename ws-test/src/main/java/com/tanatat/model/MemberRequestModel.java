package com.tanatat.model;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.exceptions.MissingAttributeException;

public class MemberRequestModel {
	private String userName;
	private String password;
	private int memberId;
	private static final Logger log = LoggerFactory.getLogger(MemberRequestModel.class);

	public MemberRequestModel jsonToBean(String jsonString) throws MissingAttributeException {
		MemberRequestModel loginBean = new MemberRequestModel();
		try {
			JSONObject js = new JSONObject(jsonString);
			log.info("json :" + js.toString());
			log.info(js.getString("dev_imei") + "");
			loginBean.setUserName(js.getString("user_name"));
			loginBean.setPassword(js.getString("password"));
		} catch (Exception e) {
			log.error("Error parse json Activate Request " + e.getMessage());
			throw new MissingAttributeException();
		}
		return loginBean;
	}

	public JSONObject toJSONObject() {
		JSONObject js = new JSONObject();
		js.put("user_name", userName);
		js.put("password", password);
		js.put("member_id", memberId);
		return js;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
}