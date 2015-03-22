package com.dmg.client.simplepayment.beans;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.user.UserManager;

public class BeansFactory {
	
	private static final BeansFactory INSTANCE = new BeansFactory();
	private static final Logger log = LoggerFactory.getLogger(BeansFactory.class);

	private BeansFactory() {
		
	}
	
	public static BeansFactory getInstance() {
		return INSTANCE;
	}
	
	
	public UserAccount getUserAccount(String city){
		
		if(city==null){
			log.debug("Error : getUserAccount , City is Null");
			return null;
		}
		
		if(city.equals("DUBAI")){
			return new UserAccountsDU();
		}
		
		if(city.equals("ABUDHABI")){
			return new UserAccountsAUH();
		}
		
		log.error("ERROR in CITY Name, getUserAccount , city=" + city );
		return null;
		
	}
	
	public Bill getBill(String city){
		
		if(city==null){
			log.debug("Error : getBill , City is Null");
			return null;
		}
		
		if(city.equals("DUBAI")){
			return new BillDu();
		}
		
		if(city.equals("ABUDHABI")){
			return new BillAUH();
		}
		
		log.error("ERROR in CITY Name, getBill , city=" + city );
		return null;
		
	}
	
//	public Transaction getTxn(String city){
//		
//		if(city==null){
//			log.debug("Error : getTxn , City is Null");
//			return null;
//		}
//		
//		if(city.equals("DUBAI")){
//			return new TransactionDu();
//		}
//		
//		if(city.equals("ABUDHABI")){
//			return new TransactionAUH();
//		}
//		
//		log.error("ERROR in CITY Name, getTxn , city=" + city );
//		return null;
//		
//	}
	
	
	

}
