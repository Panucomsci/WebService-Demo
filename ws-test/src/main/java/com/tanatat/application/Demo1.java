package com.tanatat.application;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("test")
public class Demo1 {

	@GET
	@Path("gettext")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateAuthenChattest(@Suspended final AsyncResponse aSysn) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				aSysn.resume(Response.status(Status.OK).entity("{\"res\":\"ok\",\"text\":\"hello world\"}").build());
			}
		}).start();
	} 
}