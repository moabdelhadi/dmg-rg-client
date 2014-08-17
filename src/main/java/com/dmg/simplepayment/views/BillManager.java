package com.dmg.simplepayment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;
import com.dmg.simplepayment.beans.Bill;
import com.dmg.simplepayment.beans.Constants;
import com.dmg.simplepayment.beans.UserAccount;
import com.dmg.util.Logger;

public class BillManager {
	
	private static final BillManager INSTANCE= new BillManager();
	
	private BillManager() {

	}
	
	public static BillManager getInstance() {
		return INSTANCE;
	}
	
	public List<Bill> getLatestBills(String contractID){
		
		List<Bill> list = new ArrayList<>();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.BILL_CONTRACT_NUMBER, contractID);

		
		try {
			list.addAll(FacadeFactory.getFacade().list(Bill.class, parameters));
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Error in get bills for contract number , "+contractID, e);
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	public Bill getBillById(Long id){
		
		Bill bill=null;
		try {
			bill = FacadeFactory.getFacade().find(Bill.class, id);
		} catch (DataAccessLayerException e) {
			Logger.error(this, "Error in get bill form id ="+id, e);
			e.printStackTrace();
		}
		return bill;
		
	}

}
