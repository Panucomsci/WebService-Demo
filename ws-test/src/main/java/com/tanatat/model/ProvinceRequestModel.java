package com.tanatat.model;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.exceptions.MissingAttributeException;

public class ProvinceRequestModel {

	private String provinceName;
	private int provinceID;
	private static final Logger log = LoggerFactory.getLogger(ProvinceRequestModel.class);

	public ProvinceRequestModel jsonToBean(String jsonString) throws MissingAttributeException {
		ProvinceRequestModel loginBean = new ProvinceRequestModel();
		try {
			JSONObject js = new JSONObject(jsonString);
			log.info("json :" + js.toString());
			log.info(js.getString("dev_imei") + "");
			loginBean.setprovinceName(js.getString("province_name"));
		} catch (Exception e) {
			log.error("Error parse json Activate Request " + e.getMessage());
			throw new MissingAttributeException();
		}
		return loginBean;
	}

	public JSONObject toJSONObject() {
		JSONObject js = new JSONObject();
		js.put("province_name", provinceName);
		js.put("province_id", provinceID);
		return js;
	}

	public ProvinceRequestModel() {

	}

	public String getprovinceName() {
		return provinceName;
	}

	public void setprovinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getprovinceID() {
		return provinceID;
	}

	public void setprovinceID(int provinceID) {
		this.provinceID = provinceID;
	}
}