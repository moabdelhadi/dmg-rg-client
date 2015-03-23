package com.dmg.client.simplepayment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.core.bean.BeansFactory;
import com.dmg.core.bean.Bill;
import com.dmg.core.bean.Constants;
import com.dmg.core.exception.DataAccessLayerException;
import com.dmg.core.persistence.FacadeFactory;

public class BillManager {

	private static final Logger logger = LoggerFactory
			.getLogger(BillManager.class);

	private static final BillManager INSTANCE = new BillManager();

	private BillManager() {

	}

	public static BillManager getInstance() {
		return INSTANCE;
	}

	public List<Bill> getLatestBills(String contractID , String city) {

		List<Bill> list = new ArrayList<>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.BILL_CONTRACT_NUMBER, contractID);
		
		Bill bill = BeansFactory.getInstance().getBill(city);
		

		try {
			list.addAll(FacadeFactory.getFacade().list(bill.getClass(), parameters, Constants.BILL_INV_DATE, false));
		} catch (DataAccessLayerException e) {
			logger.error("Error in get bills for contract number , "
					+ contractID, e);
			e.printStackTrace();
		}

		return list;
	}

	public Bill getBillById(Long id, String city) {

		Bill bill = null;
		try {
			Bill billBean = BeansFactory.getInstance().getBill(city);
			if(billBean==null){
				logger.error("Error in get bill form id and City =" + id + " , "+city);
				return bill;
			}
			bill = FacadeFactory.getFacade().find(billBean.getClass(), id);
		} catch (DataAccessLayerException e) {
			logger.error("Error in get bill form id =" + id, e);
		}
		return bill;

	}

}
