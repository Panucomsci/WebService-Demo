package com.tanatat.wsv1;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.dao.ServicesDDb;
import com.tanatat.model.ProvinceRequestModel;

public class Province {
	private static final Logger log = LoggerFactory.getLogger(Province.class);
	private ArrayList<ProvinceRequestModel> memList = new ArrayList<ProvinceRequestModel>();

	@GET
	@Path("select")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getMember(@Suspended AsyncResponse aSysn) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				ServicesDDb db = new ServicesDDb();
				memList = db.getProvinceList();
				JSONArray jsArray = new JSONArray(memList);
				JSONObject jOb = new JSONObject();
				jOb.put("member2", jsArray);

				String result = jOb.toString();
				aSysn.resume(Response.status(Status.OK).entity(result).build());
			}
		}).start();
	}

}
