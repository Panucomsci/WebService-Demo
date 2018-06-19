package com.tanatat.model;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.exceptions.MissingAttributeException;

public class RegisterRequestModel {
	private String registerUserName;
	private String registerPassword;
	private String registerFirstName;
	private String registerLastName;
	private String registerMobileNo;
	private String registerAddress;
	private String registerCity;
	private String registerProvince;
	private String registerZipcode;
	private String registerEmail;
	private static final Logger log = LoggerFactory.getLogger(RegisterRequestModel.class);

	public RegisterRequestModel jsonToBean(String jsonString) throws MissingAttributeException {
		RegisterRequestModel register = new RegisterRequestModel();
		try {
			JSONObject js = new JSONObject(jsonString);
			log.info("json :" + js.toString());
			register.setRegisterUserName(contains(js, "user_name") ? js.getString("user_name") : null);
			register.setRegisterPassword(contains(js, "password") ? js.getString("password") : null);
			register.setRegisterFirstName(contains(js, "first_name") ? js.getString("first_name") : null);
			register.setRegisterLastName(contains(js, "last_name") ? js.getString("last_name") : null);
			register.setRegisterMobileNo(contains(js, "mobile_no") ? js.getString("mobile_no") : null);
			register.setRegisterAddress(contains(js, "address") ? js.getString("address") : null);
			register.setRegisterEmail(contains(js, "email") ? js.getString("email") : null);
			register.setRegisterCity(contains(js, "city") ? js.getString("city") : null);
			register.setRegisterProvince(contains(js, "province") ? js.getString("province") : null);
			register.setRegisterZipcode(contains(js, "zip_code") ? js.getString("zip_code") : null);
			// register.setRegisterZipcode(contains(js, "gps_long") ?
			// js.getString("gps_long") : null);
		} catch (Exception e) {
			log.error("Error parse json Activate Request " + e.getMessage());
			throw new MissingAttributeException();

		}
		return register;
	}

	public static boolean contains(JSONObject jsonObject, String key) {
		return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
	}

	public String getRegisterFirstName() {
		return registerFirstName;
	}

	public void setRegisterFirstName(String registerFirstName) {
		this.registerFirstName = registerFirstName;
	}

	public String getRegisterLastName() {
		return registerLastName;
	}

	public void setRegisterLastName(String registerLastName) {
		this.registerLastName = registerLastName;
	}

	public String getRegisterMobileNo() {
		return registerMobileNo;
	}

	public void setRegisterMobileNo(String registerMobileNo) {
		this.registerMobileNo = registerMobileNo;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getRegisterCity() {
		return registerCity;
	}

	public void setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
	}

	public String getRegisterProvince() {
		return registerProvince;
	}

	public void setRegisterProvince(String registerProvince) {
		this.registerProvince = registerProvince;
	}

	public String getRegisterZipcode() {
		return registerZipcode;
	}

	public void setRegisterZipcode(String registerZipcode) {
		this.registerZipcode = registerZipcode;
	}

	public String getRegisterEmail() {
		return registerEmail;
	}

	public void setRegisterEmail(String registerEmail) {
		this.registerEmail = registerEmail;
	}

	public String getRegisterUserName() {
		return registerUserName;
	}

	public void setRegisterUserName(String registerUserName) {
		this.registerUserName = registerUserName;
	}

	public String getRegisterPassword() {
		return registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}
}
