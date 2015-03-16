package com.dmg.client.payment;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.simplepayment.beans.Constants;
import com.dmg.client.simplepayment.beans.PaymentResponse;
import com.dmg.client.simplepayment.beans.Transaction;
import com.dmg.client.simplepayment.beans.UserAccount;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.util.PropertiesManager;
import com.dmg.util.SHAEncrypt;

public class PaymentManager {

	private static String ACCESS_CODE_AD = "payment.vpc_AccessCode.AD";
	private static String ACCESS_CODE_DU = "payment.vpc_AccessCode.DU";
	private static String VERSION = "payment.vpc_Version";
	// private static String SUBMIT = "payment.submit";
	private static String COMMAND = "payment.vpc_Command";
	private static String LOCALE = "payment.vpc_Locale";
	private static String MERCHANT_ID_AD = "payment.vpc_Merchant.AD";
	private static String MERCHANT_ID_DU = "payment.vpc_Merchant.DU";
	private static String SECURE_HASH_CODE_AD = "payment.SecureHashCode.AD";
	private static String SECURE_HASH_CODE_DU = "payment.SecureHashCode.DU";
	private static String RETURN_URL = "payment.vpc_ReturnURL";
	// private static String PAYMENT_URL = "payment.paymentUrl";
	// private static String PAYMENT_URL_FALLBACK = "";
	private static PaymentManager INSTANCE = new PaymentManager();
	private Map<String, String> merchantMap = new HashMap<String, String>();
	private Map<String, String> accessCodeMap = new HashMap<String, String>();
	private Map<String, String> secureHashMap = new HashMap<String, String>();
	private static final Logger log = LoggerFactory.getLogger(PaymentManager.class);

	private PaymentManager() {

		merchantMap.put("DUBAI", readProperty(MERCHANT_ID_DU));
		merchantMap.put("ABUDHABI", readProperty(MERCHANT_ID_AD));

		accessCodeMap.put("DUBAI", readProperty(ACCESS_CODE_DU));
		accessCodeMap.put("ABUDHABI", readProperty(ACCESS_CODE_AD));

		secureHashMap.put("DUBAI", readProperty(SECURE_HASH_CODE_DU));
		secureHashMap.put("ABUDHABI", readProperty(SECURE_HASH_CODE_AD));

	}

	public static PaymentManager getInstance() {
		return INSTANCE;
	}

	public Map<String, String> getPostFields(UserAccount user, String ammount) {

		Map<String, String> map = new HashMap<String, String>();

		if (user == null) {
			log.error("Error user is null");
			return map;
		}

		map.put("vpc_Version", readProperty(VERSION));
		// map.put("submit", readProperty(SUBMIT));
		map.put("vpc_Command", readProperty(COMMAND));
		map.put("vpc_Locale", readProperty(LOCALE));
		map.put("vpc_ReturnURL", readProperty(RETURN_URL));

		String city = user.getCity();
		String merchantID = merchantMap.get(city);
		String accessCode = accessCodeMap.get(city);
		String secureHashKey = secureHashMap.get(city);

		map.put("vpc_AccessCode", accessCode);
		map.put("vpc_Merchant", merchantID);

		Calendar calendar = Calendar.getInstance();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		// String date = dateFormat.format(calendar.getTime());

		String MerchTxnRef = "RG-" + user.getCity() + "-" + user.getContractNo() + "-" + calendar.getTimeInMillis();

		map.put("vpc_MerchTxnRef", MerchTxnRef);

		map.put("vpc_Amount", ammount);

		map.put("vpc_OrderInfo", MerchTxnRef);
		String hashAllFields = SHAEncrypt.hashAllFields(map, secureHashKey);

		// map.remove("submit");
		// map.put("vpc_SecureHashType", "MD5");

		map.put("vpc_SecureHash", hashAllFields);

		Transaction transaction = saveNewPayment(map, user);
		try {
			FacadeFactory.getFacade().store(transaction);
		} catch (Exception e) {

			log.error("error in save new payment: transaction=" + transaction, e);
			return null;
		}

		return map;

	}

	private Transaction saveNewPayment(Map<String, String> map, UserAccount user) {

		Transaction transaction = new Transaction();
		Date date = Calendar.getInstance().getTime();
		transaction.setCreationDate(date);
		transaction.setUpdateDate(date);
		transaction.setContractNo(user.getContractNo());
		transaction.setStatus("NEW");
		transaction.setCity(user.getCity());
		transaction.setSyncStatus(0);
		transaction.setAmount(map.get("vpc_Amount"));
		transaction.setCommand(map.get("vpc_Command"));
		transaction.setOrderInfo(map.get("vpc_OrderInfo"));
		transaction.setMerchTxnRef(map.get("vpc_MerchTxnRef"));
		transaction.setSecureHash(map.get("vpc_SecureHash"));
		transaction.setSecureHashType(map.get("vpc_SecureHashType"));
		transaction.setVersion(map.get("vpc_Version"));
		transaction.setLocale(map.get("vpc_Locale"));
		transaction.setReturnURL(map.get("vpc_ReturnURL"));
		transaction.setAccessCode(map.get("vpc_AccessCode"));
		transaction.setMerchant(map.get("vpc_Merchant"));
		transaction.setSecureHashType(map.get("vpc_SecureHashType"));
		return transaction;

	}

	private String readProperty(String key) {

		if (StringUtils.isEmpty(key)) {
			return null;
		}

		String property = PropertiesManager.getInstance().getProperty(key);

		return property;

	}

	/*
	 * This method takes a data String and returns a predefined value if empty
	 * If data Sting is null, returns string "No Value Returned", else returns
	 * input
	 * 
	 * @param in String containing the data String
	 * 
	 * @return String containing the output String
	 */
	private static String null2unknown(String in) {
		if (in == null || in.length() == 0) {
			return null;
		} else {
			return in;
		}
	} // null2unknown()

	/*
	 * This function uses the returned status code retrieved from the Digital
	 * Response and returns an appropriate description for the code
	 * 
	 * @param vResponseCode String containing the vpc_TxnResponseCode
	 * 
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
	 * This method uses the 3DS verStatus retrieved from the Response and
	 * returns an appropriate description for this code.
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

				// Java cannot switch on a string so turn everything to a
				// character
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

	/*
	 * If there has been a merchant secret set then sort and loop through all
	 * the data in the Virtual Payment Client response. while we have the data,
	 * we can append all the fields that contain values (except the secure hash)
	 * so that we can create a hash and validate it against the secure hash in
	 * the Virtual Payment Client response.
	 * 
	 * NOTE: If the vpc_TxnResponseCode in not a single character then there was
	 * a Virtual Payment Client error and we cannot accurately validate the
	 * incoming data from the secure hash.
	 */
	public String processResponse(Map<String, String> fields) {

		// remove the vpc_TxnResponseCode code from the response fields as we do
		// not
		// want to include this field in the hash calculation
		String vpc_Txn_Secure_Hash = null2unknown((String) fields.remove("vpc_SecureHash"));
		String hashValidated = null;

		// defines if error message should be output
		boolean errorExists = false;

		// create secure hash and append it to the hash map if it was
		// created
		// remember if SECURE_SECRET = "" it wil not be created
		String secureHash = SHAEncrypt.hashAllFields(fields, secureHashMap.get("DUBAI"));

		// Validate the Secure Hash (remember MD5 hashes are not case
		// sensitive)
		if (vpc_Txn_Secure_Hash != null && vpc_Txn_Secure_Hash.equalsIgnoreCase(secureHash)) {
			// Secure Hash validation succeeded, add a data field to be
			// displayed later.
			hashValidated = "CORRECT";
		} else {
			// Secure Hash validation failed, add a data field to be
			// displayed later.
			errorExists = true;
			hashValidated = "INVALID HASH";
		}

		// Extract the available receipt fields from the VPC Response
		// If not present then let the value be equal to 'Unknown'
		// Standard Receipt Data
		String amount = null2unknown((String) fields.get("vpc_Amount"));
		String locale = null2unknown((String) fields.get("vpc_Locale"));
		String batchNo = null2unknown((String) fields.get("vpc_BatchNo"));
		String command = null2unknown((String) fields.get("vpc_Command"));
		String message = null2unknown((String) fields.get("vpc_Message"));
		String version = null2unknown((String) fields.get("vpc_Version"));
		String cardType = null2unknown((String) fields.get("vpc_Card"));
		String orderInfo = null2unknown((String) fields.get("vpc_OrderInfo"));
		String receiptNo = null2unknown((String) fields.get("vpc_ReceiptNo"));
		String merchantID = null2unknown((String) fields.get("vpc_Merchant"));
		String merchTxnRef = null2unknown((String) fields.get("vpc_MerchTxnRef"));
		String authorizeID = null2unknown((String) fields.get("vpc_AuthorizeId"));
		String transactionNo = null2unknown((String) fields.get("vpc_TransactionNo"));
		String acqResponseCode = null2unknown((String) fields.get("vpc_AcqResponseCode"));
		String txnResponseCode = null2unknown((String) fields.get("vpc_TxnResponseCode"));

		// CSC Receipt Data
		String vCSCResultCode = null2unknown((String) fields.get("vpc_CSCResultCode"));
		String vCSCRequestCode = null2unknown((String) fields.get("vpc_CSCRequestCode"));
		String vACQCSCRespCode = null2unknown((String) fields.get("vpc_AcqCSCRespCode"));

		// 3-D Secure Data
		String transType3DS = null2unknown((String) fields.get("vpc_VerType"));
		String verStatus3DS = null2unknown((String) fields.get("vpc_VerStatus"));
		String token3DS = null2unknown((String) fields.get("vpc_VerToken"));
		String secureLevel3DS = null2unknown((String) fields.get("vpc_VerSecurityLevel"));
		String enrolled3DS = null2unknown((String) fields.get("vpc_3DSenrolled"));
		String xid3DS = null2unknown((String) fields.get("vpc_3DSXID"));
		String eci3DS = null2unknown((String) fields.get("vpc_3DSECI"));
		String status3DS = null2unknown((String) fields.get("vpc_3DSstatus"));
		String acqAVSRespCode = null2unknown((String) fields.get("vpc_AcqAVSRespCode"));
		String riskOverallResult = null2unknown((String) fields.get("vpc_RiskOverallResult"));

		String error = "";
		// Show this page as an error page if error condition
		if (txnResponseCode == null || txnResponseCode.equals("7") || txnResponseCode.equals("No Value Returned") || errorExists) {
			error = "Error ";
		}
		PaymentResponse response = new PaymentResponse();
		response.setAmount(amount);
		response.setLocale(locale);
		response.setBatchNo(batchNo);
		response.setCommand(command);
		response.setMessage(message);
		response.setVersion(version);
		response.setCard(cardType);
		response.setOrderInfo(orderInfo);
		response.setReceiptNo(receiptNo);
		response.setMerchant(merchantID);
		response.setMerchTxnRef(merchTxnRef);
		response.setAuthorizeId(authorizeID);
		response.setTransactionNo(transactionNo);
		response.setAcqResponseCode(acqResponseCode);
		response.setTxnResponseCode(txnResponseCode);
		response.setcSCResultCode(vCSCResultCode);
		response.setcSCRequestCode(vCSCRequestCode);
		response.setAcqCSCRespCode(vACQCSCRespCode);
		response.setVerType(transType3DS);
		response.setVerStatus(verStatus3DS);
		response.setVerToken(token3DS);
		response.setVerSecurityLevel(secureLevel3DS);
		response.setD3Senrolled(enrolled3DS);
		response.setD3sxid(xid3DS);
		response.setD3SECI(eci3DS);
		response.setD3Sstatus(status3DS);
		response.setAcqAVSRespCode(acqAVSRespCode);
		response.setSecureHash(vpc_Txn_Secure_Hash);
		response.setRiskOverallResult(riskOverallResult);
		Date date = Calendar.getInstance().getTime();
		response.setUpdateDate(date);
		response.setCreationDate(date);

		try {
			FacadeFactory.getFacade().store(response);
			Transaction txn = getPaymentByTxnRef(merchTxnRef);
			if (txnResponseCode.equals("0")) {
				txn.setStatus("SUCCESS");
			} else {
				txn.setStatus("FAIL");
			}
			FacadeFactory.getFacade().store(txn);

		} catch (Exception e) {
			log.error("Error n saving response" + response.toString(), e);
		}

		return txnResponseCode;

	}

	public Transaction getPaymentByTxnRef(String txnRef) throws Exception {

		try {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("merchTxnRef", txnRef);
			List<Transaction> list = FacadeFactory.getFacade().list(Transaction.class, parameters);

			if (list == null || list.isEmpty()) {
				log.error("cannot find txn for txnRef=" + txnRef);
				throw new PaymentException("Connot get txn from database for txnref=" + txnRef);
			}

			if (list.size() > 1) {

				log.error("more than two txn available for the same txnRef=" + txnRef);
				for (Transaction transaction : list) {
					log.warn("txn=" + transaction.toString());
				}
				throw new PaymentException("more than two txn available for the same txnRef=" + txnRef);
			}

			Transaction transaction = list.get(0);

			return transaction;

		} catch (Exception e) {
			log.error("error in getting the txn over All txnRef=" + txnRef, e);
			throw e;
		}
	}

	public void save(Transaction txn) throws DataAccessLayerException {

		FacadeFactory.getFacade().store(txn);

	}
}
