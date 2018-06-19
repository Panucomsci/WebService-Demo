package com.tanatat.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("v1")
public class Applications extends ResourceConfig {
	public Applications() {
		packages("com.tanatat.wsv1");
	}
}