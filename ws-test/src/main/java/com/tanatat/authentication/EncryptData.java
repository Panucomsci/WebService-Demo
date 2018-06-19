package com.tanatat.authentication;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONObject;

import com.tanatat.crypto.EncryptGCM;
import com.tanatat.exception.DecryptErrorException;
import com.tanatat.stderr.StatusCode;

public class EncryptData {
	
	public static String Encrypt(String text, boolean isFormatJSON, String secretKey) {
		String result = null;
		try {
			result = EncryptGCM.encrypt(text, isFormatJSON, secretKey);

		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			JSONObject js = new JSONObject();
			result = setResultCode(js, StatusCode.Status.CRYPTO_FAILUER).toString();
		}
		return result;
	}

	public static String Decrypt(String text, boolean isFormatJSON, String secretKey) {
		String result = null;
		try {
			result = EncryptGCM.decrypt(text, isFormatJSON, secretKey);
		} catch (DecryptErrorException e) {
			JSONObject js = new JSONObject();
			result = setResultCode(js, StatusCode.Status.CRYPTO_FAILUER).toString();
		}
		return result;
	}
	
	private static JSONObject setResultCode(JSONObject jObject, StatusCode.Status status) {
		jObject.put("result_code", status.getCode());
		jObject.put("result_text", status.getText());
		return jObject;
	} 
}