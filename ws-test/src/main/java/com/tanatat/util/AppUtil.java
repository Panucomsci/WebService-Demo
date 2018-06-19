package com.tanatat.util;

import java.io.IOException;
import java.util.Base64;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.bouncycastle.util.encoders.Base64Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.util.Base64Decoder;

public class AppUtil {

	private static final Logger log = LoggerFactory.getLogger(AppUtil.class);

	public static String getAppConfig(String name) {
		String value = "";

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:global/env/");
			value = (String) envContext.lookup(name);
		} catch (Exception e) {
			log.error("Error getting value server config : " + e.getMessage());
		}
		return value;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(Base64.getEncoder().encode("user:password".getBytes()));
	}
}