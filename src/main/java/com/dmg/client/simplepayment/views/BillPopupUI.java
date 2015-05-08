package com.dmg.client.simplepayment.views;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.auth.SessionHandler;
import com.dmg.core.bean.Bill;
import com.dmg.core.bean.UserAccount;
import com.dmg.util.PropertiesManager;
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
	private static final Logger log = LoggerFactory.getLogger(BillManager.class);
	
	private static final String COMPANY_NAME_EN= "companyNameEn";
	
	private static final String COMPANY_PHONE_EN_DU= "companyPhoneEnDU";
	private static final String COMPANY_FAX_EN_DU= "companyFaxEnDU";
	private static final String COMPANY_EMAIL_EN_DU= "companyEmailEnDU";
	
	private static final String COMPANY_PHONE_EN_AUH= "companyPhoneEnAUH";
	private static final String COMPANY_FAX_EN_AUH= "companyFaxEnAUH";
	private static final String COMPANY_EMAIL_EN_AUH= "companyEmailEnAUH";

	
	
	private static final String COMPANY_NAME_AR= "companyNameAr";
	
	private static final String COMPANY_PHONE_AR_DU= "companyPhoneArDU";
	private static final String COMPANY_FAX_AR_DU= "companyFaxArDU";
	private static final String COMPANY_EMAIL_AR_DU= "companyEmailArDU";
	
	private static final String COMPANY_PHONE_AR_AUH= "companyPhoneArAUH";
	private static final String COMPANY_FAX_AR_AUH= "companyFaxArAUH";
	private static final String COMPANY_EMAIL_AR_AUH= "companyEmailArAUH";
	
	
	private Map<String, String> addressAUH = new HashMap<String, String>();
	private Map<String, String> addressDU = new HashMap<String, String>();
	
	

	@Override
	protected void init(VaadinRequest request) {
		
		populateAddress();

		UserAccount userAccount = SessionHandler.get();
		
		String billId = request.getParameter("billId");
		String city = request.getParameter("billCity");
		

		if (userAccount == null) {
			log.error("User Not Available");
			return;
		}
		
		Map<String,String> addressMap = getAddressMap(userAccount);

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
		//setHeight("1000px");
		CustomLayout customLayout = new CustomLayout("billView");
		
		//customLayout.setHeight("1000px");
		setContent(customLayout);

		String logoPath ="";
		if("DUBAI".equalsIgnoreCase(userAccount.getCity())){
			logoPath ="img/dlogo.png";
		}else{
			logoPath ="img/adlogo.png";
		}
		
		ThemeResource source = new ThemeResource(logoPath);
		Image logo = new Image(null, source);
		customLayout.addComponent(logo, "logo");

		
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_NAME_AR")), "companyNameLblAr");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_NAME_EN")), "companyNameLbl");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_PHONE_AR")), "companyTelLblAr");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_PHONE_EN")), "companyTelLbl");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_FAX_AR")), "companyFaxLblAr");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_FAX_EN")), "companyFaxLbl");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_EMAIL_AR")), "companyEmailLblAr");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_EMAIL_EN")), "companyEmailLbl");
		customLayout.addComponent(getLabel(addressMap.get("COMPANY_EMAIL_EN")), "cmpEmailFotter");
		
		
		
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

	private Map<String,String> getAddressMap(UserAccount userAccount) {
		String city = userAccount.getCity();
		if("DUBAI".equalsIgnoreCase(city)){
			return addressDU;
		}
		
		return addressAUH;
		
	}

	private void populateAddress() {
		
		addressAUH.clear();
		addressAUH.put("COMPANY_NAME_AR", getpropertyValue(COMPANY_NAME_AR));
		addressAUH.put("COMPANY_NAME_EN", getpropertyValue(COMPANY_NAME_EN));
		addressAUH.put("COMPANY_PHONE_AR", getpropertyValue(COMPANY_PHONE_AR_AUH));
		addressAUH.put("COMPANY_PHONE_EN", getpropertyValue(COMPANY_PHONE_EN_AUH));
		addressAUH.put("COMPANY_FAX_AR", getpropertyValue(COMPANY_FAX_AR_AUH));
		addressAUH.put("COMPANY_FAX_EN", getpropertyValue(COMPANY_FAX_EN_AUH));
		addressAUH.put("COMPANY_EMAIL_AR", getpropertyValue(COMPANY_EMAIL_AR_AUH));
		addressAUH.put("COMPANY_EMAIL_EN", getpropertyValue(COMPANY_EMAIL_EN_AUH));

		addressDU.clear();
		addressDU.put("COMPANY_NAME_AR", getpropertyValue(COMPANY_NAME_AR));
		addressDU.put("COMPANY_NAME_EN", getpropertyValue(COMPANY_NAME_EN));
		addressDU.put("COMPANY_PHONE_AR", getpropertyValue(COMPANY_PHONE_AR_DU));
		addressDU.put("COMPANY_PHONE_EN", getpropertyValue(COMPANY_PHONE_EN_DU));
		addressDU.put("COMPANY_FAX_AR", getpropertyValue(COMPANY_FAX_AR_DU));
		addressDU.put("COMPANY_FAX_EN", getpropertyValue(COMPANY_FAX_EN_DU));
		addressDU.put("COMPANY_EMAIL_AR", getpropertyValue(COMPANY_EMAIL_AR_DU));
		addressDU.put("COMPANY_EMAIL_EN", getpropertyValue(COMPANY_EMAIL_EN_DU));
		
	}
	
	private String  getpropertyValue(String name){
		return PropertiesManager.getInstance().getProperty(name);
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
