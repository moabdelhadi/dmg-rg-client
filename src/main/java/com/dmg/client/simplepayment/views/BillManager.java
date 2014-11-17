package com.dmg.client.simplepayment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.simplepayment.beans.Bill;
import com.dmg.client.simplepayment.beans.Constants;
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

	public List<Bill> getLatestBills(String contractID) {

		List<Bill> list = new ArrayList<>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Constants.BILL_CONTRACT_NUMBER, contractID);

		try {
			list.addAll(FacadeFactory.getFacade().list(Bill.class, parameters));
		} catch (DataAccessLayerException e) {
			logger.error("Error in get bills for contract number , "
					+ contractID, e);
			e.printStackTrace();
		}

		return list;
	}

	public Bill getBillById(Long id) {

		Bill bill = null;
		try {
			bill = FacadeFactory.getFacade().find(Bill.class, id);
		} catch (DataAccessLayerException e) {
			logger.error("Error in get bill form id =" + id, e);
			e.printStackTrace();
		}
		return bill;

	}

}
