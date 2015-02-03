package com.dmg.client.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dmg.client.simplepayment.beans.UserAccount;
import com.dmg.util.PropertiesManager;

public class PaymentManager {

	private static String ACCESS_CODE = "payment.vpc_AccessCode";
	private static String VERSION = "payment.vpc_Version";
	private static String SUBMIT = "payment.submit";
	private static String COMMAND = "payment.vpc_Command";
	private static String LOCALE = "payment.vpc_Locale";
	private static String MERCHANT_ID_AD = "payment.vpc_Merchant.AD";
	private static String MERCHANT_ID_DU = "payment.vpc_Merchant.DU";
	private static String SECURE_HASH_CODE = "payment.SecureHashCode";
	private static String RETURN_URL = "payment.vpc_ReturnURL";
	private static String PAYMENT_URL = "payment.paymentUrl";
	private static String PAYMENT_URL_FALLBACK = "";
	private static PaymentManager INSTANCE = new PaymentManager();
	private Map<String, String> merchantMap = new HashMap<String, String>();

	
	private PaymentManager() {
		
		merchantMap.put("DUBAI", );
		merchantMap.put("ABUDHABI", );
		
		
	}

	public static PaymentManager getInstance() {
		return INSTANCE;
	}
	
	
	public Map<String, String> getPostFields(UserAccount user, String ammount){
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("vpc_AccessCode", readProperty(ACCESS_CODE));
		map.put("vpc_Version", readProperty(VERSION));
		map.put("submit", readProperty(SUBMIT));
		map.put("vpc_Command", readProperty(COMMAND));
		map.put("vpc_Locale", readProperty(LOCALE));
		map.put("vpc_ReturnURL", readProperty(RETURN_URL));
		

		String city = user.getCity();
		String merchantID = merchantMap.get(city);
		map.put("vpc_Merchant", readProperty(merchantID));

		map.put("vpc_MerchTxnRef", "RG-AD-20150203-CONTRACTNO-NO");
		
		map.put("vpc_Amount", ammount);
		
		map.put("vpc_OrderInfo", "RG-AD-20150203-CONTRACTNO-NO");
		
		
		
		map.put("vpc_SecureHash", readProperty());
		
	}
	
	
	private String readProperty(String key){
		
		if(StringUtils.isEmpty(key)){
			return null;
		}
		
		String property = PropertiesManager.getInstance().getProperty(key);
		
		return property;
		
	}
	

}
