package com.dmg.util;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptionUtil {

	private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
	private static final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, };
	private static final String ALGORITHEM = "PBEWithMD5AndDES";
	
	public static void main(String[] args) throws Exception {
		String originalPassword = "testasaskdhaksdahdjkhasdjkhajkdhasjd asdhka sdkjas d";
		System.out.println("Original password: " + originalPassword);
		String encryptedPassword = encrypt(originalPassword);
		System.out.println("Encrypted password: " + encryptedPassword);
		String decryptedPassword = decrypt(encryptedPassword);
		System.out.println("Decrypted password: " + decryptedPassword);
	}

	public static String encrypt(String property) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHEM);
			SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
			Cipher pbeCipher = Cipher.getInstance(ALGORITHEM);
			pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
			return base64Encode(pbeCipher.doFinal(property.getBytes()));
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private static String base64Encode(byte[] bytes) {
		// NB: This class is internal, and you probably should use another impl
		return DatatypeConverter.printBase64Binary(bytes);
	}

	public static String decrypt(String property) throws Exception {

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHEM);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		Cipher pbeCipher = Cipher.getInstance(ALGORITHEM);
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return new String(pbeCipher.doFinal(base64Decode(property)));

	}

	private static byte[] base64Decode(String property) throws IOException {
		// NB: This class is internal, and you probably should use another impl
//		DatatypeConverter.
		return DatatypeConverter.parseBase64Binary(property);
	}

}