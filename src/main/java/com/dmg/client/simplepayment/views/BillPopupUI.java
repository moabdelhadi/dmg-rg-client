package com.dmg.client.simplepayment.views;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.dmg.core.bean.Bill;
import com.dmg.core.bean.UserAccount;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Theme("dmg-theme")
public class BillPopupUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(BillManager.class);


	@Override
	protected void init(VaadinRequest request) {

		UserAccount userAccount = SessionHandler.get();
		
		String billId = request.getParameter("billId");
		String city = request.getParameter("billCity");
		

		if (userAccount == null) {
			log.error("User Not Available");
			return;
		}

		Long billIdValue = null;
		try {
			billIdValue = Long.parseLong(billId);
		} catch (Exception e) {
			log.error("Invalis billId " + billId);
			return;
		}

		Bill bill = BillManager.getInstance().getBillById(billIdValue, city);
		if(bill==null){
			log.error( "Invalis bill ID" + billId);
			return;
		}
		

//		setSizeFull();
		setHeight("1000px");
		CustomLayout customLayout = new CustomLayout("billView");
		//customLayout.setHeight("1000px");
		setContent(customLayout);

		ThemeResource source = new ThemeResource("img/adlogo.jpg");
		Image logo = new Image(null, source);
		customLayout.addComponent(logo, "logo");

		
		customLayout.addComponent(getLabel("Royal Development for Gas Works"), "companyNameLbl");
		
		customLayout.addComponent(getLabel(userAccount.getName()), "userName");
		customLayout.addComponent(getLabel(bill.getBuildingName()), "BuildingOwner");
		customLayout.addComponent(getLabel(bill.getBuildingCode()), "BuildingCode");
		customLayout.addComponent(getLabel(bill.getApartmentCode()), "apartmentCode");
		customLayout.addComponent(getLabel(bill.getDocNo()), "billCode");
		customLayout.addComponent(getLabel(bill.getBillDate()), "billDate");
		customLayout.addComponent(getLabel(bill.getContractNo()), "accountId");
		customLayout.addComponent(getLabel(bill.getLastReceivingAmount()), "lastRecivedAmmount");
		customLayout.addComponent(getLabel(bill.getLastReceivingDate()), "lastRecivedDate");
		customLayout.addComponent(getLabel(bill.getCurrentReading()), "currentReading");
		customLayout.addComponent(getLabel(bill.getLastReading()), "previousReading");
		customLayout.addComponent(getLabel(bill.getTotalUnit()), "consumptionAmt");
		customLayout.addComponent(getLabel(bill.getUnitPrice()), "unitPrice");
		log.debug("consumptionValue = "+ bill.getAmount());
		customLayout.addComponent(getLabel(bill.getAmount()), "consumptionValue");
		customLayout.addComponent(getLabel(bill.getAmount()), "consumptionValue2");
		customLayout.addComponent(getLabel(bill.getCurrentReadingDate()), "currentReadingDate");
		customLayout.addComponent(getLabel(bill.getLastReadingDate()), "previousReadingDate");
		customLayout.addComponent(getLabel(bill.getPrevBalance()), "previousValue");
		customLayout.addComponent(getLabel(bill.getService()), "service");
		customLayout.addComponent(getLabel(bill.getGasDifference()), "gasdifference");
		customLayout.addComponent(getLabel(bill.getTotalAmount()), "total");
		customLayout.addComponent(getLabel(bill.getCollectorName()), "collectorName");

	}

	private Label getLabel(String label) {

		if(label==null){
			return new Label("");
		}
		Label lbl = new Label(label);
		return lbl;

	}

	private Label getLabel(BigDecimal label) {

		String value = "0.0";
		if(label ==null){
			return new Label(value);
		}
		try {
			value = label.toString();
		} catch (Exception e) {
			log.error("error in convert decimal value",e);
		}

		Label lbl = new Label(value);
		return lbl;

	}
	
	private Label getLabel(Date label) {

		String value = "";
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			value= dateFormat.format(label);
		} catch (Exception e) {
			log.error("error in convert decimal value");
		}

		Label lbl = new Label(value);
		return lbl;

	}

}
