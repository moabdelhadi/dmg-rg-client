package com.dmg.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SHAEncrypt {

	// This is secret for encoding the MD5 hash
	// This secret will vary from merchant to merchant
	// static final String SECURE_SECRET = "your-secure-hash-secret";
	//    static String SECURE_SECRET = "787F5B76DB89CC4D2D20B0516D43431C";

	// This is an array for creating hex chars
	static final char[] HEX_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * This method is for sorting the fields and creating an MD5 secure hash.
	 * 
	 * @param fields
	 *            is a map of all the incoming hey-value pairs from the VPC
	 * @param buf
	 *            is the hash being returned for comparison to the incoming hash
	 */
	public static String hashAllFields(Map<String, String> fields, String SECURE_SECRET) {

		// 	    String hashKeys = "";
		// 	    String hashValues = "";

		// create a list and sort it
		List<String> fieldNames = new ArrayList<String>(fields.keySet());
		Collections.sort(fieldNames);

		// create a buffer for the md5 input and add the secure secret first
		StringBuffer buf = new StringBuffer();
		buf.append(SECURE_SECRET);

		// iterate through the list and add the remaining field values
		Iterator itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = fields.get(fieldName);
			// 	            hashKeys += fieldName + ", ";
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				buf.append(fieldValue);
			}
		}

		return encryptKey(buf.toString());

	} // end hashAllFields()

	public static String encryptKey(String buf) {
		MessageDigest md5 = null;
		byte[] ba = null;

		// create the md5 hash and ISO-8859-1 encode it
		try {
			md5 = MessageDigest.getInstance("MD5");
			ba = md5.digest(buf.getBytes("ISO-8859-1"));
		} catch (Exception e) {

		} // wont happen

		// 	    hashValues = buf.toString();
		return hex(ba);
	}

	/**
	 * Returns Hex output of byte array
	 */
	private static String hex(byte[] input) {
		// create a StringBuffer 2x the size of the hash array
		StringBuffer sb = new StringBuffer(input.length * 2);

		// retrieve the byte array data, convert it to hex
		// and add it to the StringBuffer
		for (int i = 0; i < input.length; i++) {
			sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
			sb.append(HEX_TABLE[input[i] & 0xf]);
		}
		return sb.toString();
	}

	/**
	 * This method takes a data String and returns a predefined value if empty
	 * If data Sting is null, returns string "No Value Returned", else returns
	 * input
	 * 
	 * @param in
	 *            String containing the data String
	 * 
	 * @return String containing the output String
	 */
	private static String null2unknown(String in) {
		if (in == null || in.length() == 0) {
			return "No Value Returned";
		} else {
			return in;
		}
	} // null2unknown()

	/**
	 * This function uses the returned status code retrieved from the Digital
	 * Response and returns an appropriate description for the code
	 * 
	 * @param vResponseCode
	 *            String containing the vpc_TxnResponseCode
	 * @return description String containing the appropriate description
	 */
	String getResponseDescription(String vResponseCode) {

		String result = "";

		// check if a single digit response code
		if (vResponseCode.length() != 1) {

			// Java cannot switch on a string so turn everything to a char
			char input = vResponseCode.charAt(0);

			switch (input) {
			case '0':
				result = "Transaction Successful";
				break;
			case '1':
				result = "Unknown Error";
				break;
			case '2':
				result = "Bank Declined Transaction";
				break;
			case '3':
				result = "No Reply from Bank";
				break;
			case '4':
				result = "Expired Card";
				break;
			case '5':
				result = "Insufficient Funds";
				break;
			case '6':
				result = "Error Communicating with Bank";
				break;
			case '7':
				result = "Payment Server System Error";
				break;
			case '8':
				result = "Transaction Type Not Supported";
				break;
			case '9':
				result = "Bank declined transaction (Do not contact Bank)";
				break;
			case 'A':
				result = "Transaction Aborted";
				break;
			case 'C':
				result = "Transaction Cancelled";
				break;
			case 'D':
				result = "Deferred transaction has been received and is awaiting processing";
				break;
			case 'F':
				result = "3D Secure Authentication failed";
				break;
			case 'I':
				result = "Card Security Code verification failed";
				break;
			case 'L':
				result = "Shopping Transaction Locked (Please try the transaction again later)";
				break;
			case 'N':
				result = "Cardholder is not enrolled in Authentication Scheme";
				break;
			case 'P':
				result = "Transaction has been received by the Payment Adaptor and is being processed";
				break;
			case 'R':
				result = "Transaction was not processed - Reached limit of retry attempts allowed";
				break;
			case 'S':
				result = "Duplicate SessionID (OrderInfo)";
				break;
			case 'T':
				result = "Address Verification Failed";
				break;
			case 'U':
				result = "Card Security Code Failed";
				break;
			case 'V':
				result = "Address Verification and Card Security Code Failed";
				break;
			case '?':
				result = "Transaction status is unknown";
				break;
			default:
				result = "Unable to be determined";
			}

			return result;
		} else {
			return "No Value Returned";
		}
	} // getResponseDescription()

	/**
	 * This function uses the QSI AVS Result Code retrieved from the Digital
	 * Receipt and returns an appropriate description for this code.
	 * 
	 * @param vAVSResultCode
	 *            String containing the vpc_AVSResultCode
	 * @return description String containing the appropriate description
	 */
	private String displayAVSResponse(String vAVSResultCode) {

		String result = "";
		if (vAVSResultCode != null || vAVSResultCode.length() == 0) {

			if (vAVSResultCode.equalsIgnoreCase("Unsupported") || vAVSResultCode.equalsIgnoreCase("No Value Returned")) {
				result = "AVS not supported or there was no AVS data provided";
			} else {
				// Java cannot switch on a string so turn everything to a char
				char input = vAVSResultCode.charAt(0);

				switch (input) {
				case 'X':
					result = "Exact match - address and 9 digit ZIP/postal code";
					break;
				case 'Y':
					result = "Exact match - address and 5 digit ZIP/postal code";
					break;
				case 'S':
					result = "Service not supported or address not verified (international transaction)";
					break;
				case 'G':
					result = "Issuer does not participate in AVS (international transaction)";
					break;
				case 'A':
					result = "Address match only";
					break;
				case 'W':
					result = "9 digit ZIP/postal code matched, Address not Matched";
					break;
				case 'Z':
					result = "5 digit ZIP/postal code matched, Address not Matched";
					break;
				case 'R':
					result = "Issuer system is unavailable";
					break;
				case 'U':
					result = "Address unavailable or not verified";
					break;
				case 'E':
					result = "Address and ZIP/postal code not provided";
					break;
				case 'N':
					result = "Address and ZIP/postal code not matched";
					break;
				case '0':
					result = "AVS not requested";
					break;
				default:
					result = "Unable to be determined";
				}
			}
		} else {
			result = "null response";
		}
		return result;
	}

	/**
	 * This function uses the QSI CSC Result Code retrieved from the Digital
	 * Receipt and returns an appropriate description for this code.
	 * 
	 * @param vCSCResultCode
	 *            String containing the vpc_CSCResultCode
	 * @return description String containing the appropriate description
	 */
	private String displayCSCResponse(String vCSCResultCode) {

		String result = "";
		if (vCSCResultCode != null || vCSCResultCode.length() == 0) {

			if (vCSCResultCode.equalsIgnoreCase("Unsupported") || vCSCResultCode.equalsIgnoreCase("No Value Returned")) {
				result = "CSC not supported or there was no CSC data provided";
			} else {
				// Java cannot switch on a string so turn everything to a char
				char input = vCSCResultCode.charAt(0);

				switch (input) {
				case 'M':
					result = "Exact code match";
					break;
				case 'S':
					result = "Merchant has indicated that CSC is not present on the card (MOTO situation)";
					break;
				case 'P':
					result = "Code not processed";
					break;
				case 'U':
					result = "Card issuer is not registered and/or certified";
					break;
				case 'N':
					result = "Code invalid or not matched";
					break;
				default:
					result = "Unable to be determined";
				}
			}

		} else {
			result = "null response";
		}
		return result;
	}

	/**
	 * This method uses the 3DS verStatus retrieved from the
	 * Response and returns an appropriate description for this code.
	 * 
	 * @param vpc_VerStatus
	 *            String containing the status code
	 * @return description String containing the appropriate description
	 */
	private String getStatusDescription(String vStatus) {

		String result = "";
		if (vStatus != null && !vStatus.equals("")) {

			if (vStatus.equalsIgnoreCase("Unsupported") || vStatus.equals("No Value Returned")) {
				result = "3DS not supported or there was no 3DS data provided";
			} else {

				// Java cannot switch on a string so turn everything to a character
				char input = vStatus.charAt(0);

				switch (input) {
				case 'Y':
					result = "The cardholder was successfully authenticated.";
					break;
				case 'E':
					result = "The cardholder is not enrolled.";
					break;
				case 'N':
					result = "The cardholder was not verified.";
					break;
				case 'U':
					result = "The cardholder's Issuer was unable to authenticate due to some system error at the Issuer.";
					break;
				case 'F':
					result = "There was an error in the format of the request from the merchant.";
					break;
				case 'A':
					result = "Authentication of your Merchant ID and Password to the ACS Directory Failed.";
					break;
				case 'D':
					result = "Error communicating with the Directory Server.";
					break;
				case 'C':
					result = "The card type is not supported for authentication.";
					break;
				case 'S':
					result = "The signature on the response received from the Issuer could not be validated.";
					break;
				case 'P':
					result = "Error parsing input from Issuer.";
					break;
				case 'I':
					result = "Internal Payment Server system error.";
					break;
				default:
					result = "Unable to be determined";
					break;
				}
			}
		} else {
			result = "null response";
		}
		return result;
	}

}
