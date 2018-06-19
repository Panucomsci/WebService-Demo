package com.tanatat.wsv1;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanatat.authentication.EncryptData;
import com.tanatat.authentication.Keys;
import com.tanatat.authentication.ValidateKey;
import com.tanatat.dao.ServicesDDb;
import com.tanatat.exceptions.DatabaseException;
import com.tanatat.exceptions.DbConnException;
import com.tanatat.exceptions.MissingAttributeException;
import com.tanatat.model.MemberRequestModel;
import com.tanatat.model.RegisterRequestModel;
import com.tanatat.model.RequestBean;
import com.tanatat.stderr.StatusCode;

@Path("service")
public class Service {
	private static final Logger log = LoggerFactory.getLogger(Register.class);

	@POST
	@Path("type")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void activate(@Suspended final AsyncResponse aSyncres, @HeaderParam("Authorization") String authen,
			RequestBean data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONObject js = new JSONObject();
				LocalTime timeStartProcess = LocalTime.now();
				MemberRequestModel member = null;
				String dataClient = null;
				ServicesDDb servicesDDb = new ServicesDDb();
				RegisterRequestModel request = new RegisterRequestModel();
				int res;

				if (!ValidateKey.validateKeyHeader(authen)) {
					setResultCode(js, StatusCode.Status.AUTHENTICATION_FAIL);
				} else {
					try {
						dataClient = EncryptData.Decrypt(data.getDt(), false, Keys.DEFAULT.k1);
						request = request.jsonToBean(dataClient);
						res = servicesDDb.registerMember(request);
						setResultCode(js, StatusCode.Status.OK);
					} catch (DatabaseException e) {
						setResultCode(js, e.getResponse());
					} catch (DbConnException e) {
						setResultCode(js, e.getResponse());
					} catch (MissingAttributeException e) {
						setResultCode(js, e.getResponse());
					}
				}
				String result = js.toString();
				long successTime = timeStartProcess.until(LocalTime.now(), ChronoUnit.MILLIS);
				log.info("Request data : " + dataClient + " time:" + successTime + " ms. result:" + result);

				result = EncryptData.Encrypt(result, true, Keys.DEFAULT.k1);
				aSyncres.resume(Response.status(Status.OK).entity(result).build());
			}
		}).start();
	}

	public JSONObject setResultCode(JSONObject jObject, StatusCode.Status status) {
		jObject.put("result_code", status.getCode());
		jObject.put("result_text", status.getText());
		return jObject;
	}
}