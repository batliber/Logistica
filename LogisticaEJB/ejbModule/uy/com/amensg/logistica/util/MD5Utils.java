package uy.com.amensg.logistica.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String stringToMD5(String string) {
		String result = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(string.getBytes());
			byte[] digest = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}