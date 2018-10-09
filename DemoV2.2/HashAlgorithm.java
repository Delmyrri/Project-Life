package demo2.V2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.IllegalArgumentException;


public class HashAlgorithm {

	public StringBuffer Hash(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());

		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb;
	}
	
	public StringBuffer Hash(StringBuffer sourceSB) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(String.valueOf(sourceSB).getBytes());

		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb;
	}

	public StringBuffer Hash(StringBuffer[] SBArray) throws NoSuchAlgorithmException {
		if (SBArray.length ==0) throw new IllegalArgumentException("Array size cannot be zero.");
		if (SBArray.length == 1)
			return SBArray[0];
		else {
			StringBuffer result = SBArray[0];
			for (int i = 0; i<SBArray.length; i++) {
				result.append(SBArray[i]);
			}
			return Hash(result);
		}
	}
}