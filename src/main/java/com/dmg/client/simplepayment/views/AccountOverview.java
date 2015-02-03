package com.dmg.client.simplepayment.views;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.risto.formsender.FormSender;
import org.vaadin.risto.formsender.FormSenderBuilder;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.dmg.client.auth.SessionHandler;
import com.dmg.client.payment.PaymentManager;
import com.dmg.client.simplepayment.beans.Bill;
import com.dmg.client.simplepayment.beans.UserAccount;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class AccountOverview extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AccountOverview.class);

	private Navigator navigator;

	/** Login Fileds **/
	private Button payButton;

	private UserAccount user;

	private Label name;
	private List<Label> dates = new ArrayList<Label>();
	private List<Label> amounts = new ArrayList<Label>();
	private List<Button> billViews = new ArrayList<Button>();
	private Label totalAmount;

	public AccountOverview(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		setSizeFull();

		HorizontalLayout hsplit = new HorizontalLayout();
		CustomLayout optionLayout = AccountOptions.getInstance(navigator).createOptionLayout();
		hsplit.addComponent(optionLayout);

		CustomLayout customLayout = new CustomLayout("AccountOverview");
		// customLayout.setWidth("20%");

		name = new Label("");
		name.setStyleName("h1");
		customLayout.addComponent(name, "welcomeName");
		customLayout.setWidth("750px");

		int counter = 0;

		dates.clear();
		amounts.clear();
		billViews.clear();

		for (int i = 0; i < 3; i++) {

			Label date = new Label("..." + counter);
			date.setStyleName("table-td-font-size");
			customLayout.addComponent(date, "date_" + counter);
			dates.add(date);

			Label amount = new Label("....." + counter);
			amount.setStyleName("table-td-font-size");
			customLayout.addComponent(amount, "amount_" + counter);
			amounts.add(amount);

			Button link = new Button();
			link.setIcon(new ThemeResource("img/BillIcon.png"));
			link.addStyleName("viewButton");
			customLayout.addComponent(link, "view_" + counter);
			billViews.add(link);

			counter++;

		}

		totalAmount = new Label("Ammount Due date: " + " ??? " + " AED");
		customLayout.addComponent(totalAmount, "totalAmount");

		// payButton

		payButton = new Button("Pay through NBAD");
		payButton.addStyleName(Runo.BUTTON_BIG);
		customLayout.addComponent(payButton, "payButton");
		payButton.setWidth("140px");
		payButton.setHeight("30px");

		hsplit.addComponent(customLayout);
		addComponent(hsplit);

		payButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				log.info( "Pay on process");
				
				PaymentManager manager = PaymentManager.getInstance();
				
				FormSender sender = new FormSender();
				sender.setFormMethod(Method.POST);
				sender.setFormAction("https://migs.mastercard.com.au/vpcpay");
				sender.setFormTarget("_self");
				sender.addValue("vpc_AccessCode", "");
				sender.addValue("vpc_Version", "1");
				sender.addValue("vpc_Command", "pay");
				sender.addValue("vpc_OrderInfo", "");
				sender.addValue("vpc_Locale", "en");
				sender.addValue("vpc_Merchant", "");
				sender.addValue("vpc_Amount", "");
				sender.addValue("vpc_ReturnURL", "");
				sender.addValue("vpc_MerchTxnRef", "");
				sender.addValue("vpc_SecureHash", "");
				sender.addValue("vpc_SecureHashType", "SHA256");
				sender.submit();
				
			
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				String text = "This is some text";
				md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
				byte[] digest = md.digest();
				System.out.println(digest.toString());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
				
				
				
//				FormSenderBuilder formSender = FormSenderBuilder.create().withUI(getUI());
//				.withAction("sadasdas")
//                .withMethod(Method.POST)
//                .withValue("name", "asdasdasd")
//                .withValue("password", "asdasdasd")
//                .submit();

			}
		});

	}
	
	
	
	/*
	 * 
	 * 
vpc_AccessCode: 	sdsds
vpc_Version: 	1
submit: 	Continue
vpc_Command: 	pay
vpc_OrderInfo: 	sdsdsd
vpc_Locale: 	en
vpc_Merchant: 	sdsd
vpc_Amount: 	100
vpc_SecureHash: 	FFC6B9FFFD5EEB7BBA51B5C58CD739C8
vpc_ReturnURL: 	http://162.243.46.82:8080/code/vpc_jsp_authenticate_and_pay_merchanthost_dr.jsp
vpc_MerchTxnRef: 	sdsdsdsd

	 * 
	 * 
	 * 
	 * */

	@Override
	public void enter(ViewChangeEvent event) {

		UserAccount userAccount = SessionHandler.get();

		if (userAccount == null) {
			navigator.navigateTo(Views.LOGIN);
			return;
		}

		UserAccount accountFromAccountID = UserManager.getInstance().getAccountFromAccountID(userAccount);

		if (accountFromAccountID == null) {
			log.error("No Valid user with this parameter");
			return;
		}
		user = accountFromAccountID;

		name.setValue(user.getName());

		List<Bill> list = BillManager.getInstance().getLatestBills(user.getContractNo());
		BigDecimal totalAmountvalue = list.get(0).getTotalAmount();
		BigDecimal receivedAmmountValue = list.get(0).getReceivedAmmount();
		BigDecimal subtract = totalAmountvalue.subtract(receivedAmmountValue);

		totalAmount.setValue(subtract.toString());

		int counter = 0;
		for (Bill bill : list) {

			Date currentReadingDate = bill.getCurrentReadingDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dates.get(counter).setValue(dateFormat.format(currentReadingDate));
			amounts.get(counter).setValue(bill.getTotalAmount().toString());
			Button button = billViews.get(counter);
			BrowserWindowOpener opener = new BrowserWindowOpener(BillPopupUI.class);
			opener.setFeatures("");
			opener.setParameter("accountId", bill.getContractNo());
			opener.setParameter("billId", bill.getId().toString());
			opener.extend(button);

			counter++;
			if (counter >= 3) {
				break;
			}

		}
	}
}
