package com.tanatat.authentication;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateKey {
	private static final Logger log = LoggerFactory.getLogger(ValidateKey.class);
	private static final ResourceBundle bundleKeys = ResourceBundle.getBundle("key");
	public static boolean validateKeyHeader(String key) {
		boolean isCorrect = false;
		try {
			if (key != null && key.length() > 0) {
				Enumeration<String> enm = bundleKeys.getKeys();
				while (enm.hasMoreElements()) {
					String keyName = (String) enm.nextElement();
					String value = bundleKeys.getString(keyName);
					if (key.equals(value)) {
						isCorrect = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error Validate Key : " + e.getMessage());
		}
		return isCorrect;
	}
}
