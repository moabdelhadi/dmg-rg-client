package com.dmg.client.simplepayment.views;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.risto.formsender.FormSenderBuilder;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.dmg.client.auth.SessionHandler;
import com.dmg.client.payment.PaymentManager;
import com.dmg.client.user.UserManager;
import com.dmg.core.bean.Bill;
import com.dmg.core.bean.Constants;
import com.dmg.core.bean.Transaction;
import com.dmg.core.bean.UserAccount;
import com.dmg.util.PropertiesManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class AccountOverview extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AccountOverview.class);
	private static final String PAYMENT_URL = "payment.paymentUrl";
	// private PaymentManager manager = PaymentManager.getInstance();
	private Map<String, String> postFields;

	private Navigator navigator;

	/** Login Fileds **/
	private Button payButton;

	private UserAccount user;

	private Label name;
	private List<Label> dates = new ArrayList<Label>();
	private List<Label> amounts = new ArrayList<Label>();
	private List<Button> billViews = new ArrayList<Button>();
	private Label totalAmount;
	private double totalAnoountDouble = 0;
	private double fees = 0;
	private Label feeNote;
	private String lBDocType="" ;
	private String lBDocNo="" ;
	private String lBYearCode="" ;

	public AccountOverview(Navigator navigator) {
		this.navigator = navigator;
		init();
	}

	private void init() {

		fees = PropertiesManager.getInstance().getPropertyInt(Constants.ONLINE_FEES_NAME);
		setSizeFull();

		HorizontalLayout hsplit = new HorizontalLayout();
		CustomLayout optionLayout = new AccountOptions(navigator).createOptionLayout();
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
		
		feeNote = new Label("Please note that "+fees+" AED are added to your amount as an online service fee");
		customLayout.addComponent(feeNote, "feeNote");

		for (int i = 0; i < 3; i++) {

			Label date = new Label("..." + counter);
			date.setStyleName("table-td-font-size");
			customLayout.addComponent(date, "date_" + counter);
			dates.add(date);

			Label amount = new Label("..." + counter);
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

		totalAmount = new Label("Ammount Due date: " + " ... " + " AED");
		customLayout.addComponent(totalAmount, "totalAmount");

		// payButton

		payButton = new Button("Pay now");
		payButton.addStyleName(Runo.BUTTON_BIG);
		customLayout.addComponent(payButton, "payButton");
		payButton.setWidth("140px");
		payButton.setHeight("30px");

		hsplit.addComponent(customLayout);
		addComponent(hsplit);

		payButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					log.info("Pay on process");
					PaymentManager manager = PaymentManager.getInstance();
					int intValue = (int) (totalAnoountDouble * 100);
					
					if(intValue==0){
						Notification notification = new Notification("No Payment Available","Your Balance is 0 you have no payment to do", Notification.Type.HUMANIZED_MESSAGE, true);
						notification.setDelayMsec(-1);
						notification.show(Page.getCurrent());
						return;
					}
					
					Map<String, String> postFields = manager.getPostFields(user, totalAnoountDouble, lBDocNo, lBDocType, lBYearCode);

					Transaction txn = manager.getPaymentByTxnRef(postFields.get("vpc_MerchTxnRef"));

					if (txn == null) {
						log.error("Error the txn Dose Not Saved Well");
						Notification notification = new Notification("Payment process error","Error happen during process the payment", Notification.Type.HUMANIZED_MESSAGE, true);
						notification.setDelayMsec(-1);
						notification.show(Page.getCurrent());
						return;
					}

					FormSenderBuilder formSender = FormSenderBuilder.create().withUI(getUI()).withAction(PropertiesManager.getInstance().getProperty(PAYMENT_URL)).withMethod(Method.POST)
							.withTarget("_parent");

					for (String key : postFields.keySet()) {
						log.debug("map key - value:" + key + " : " + postFields.get(key));
						formSender = formSender.withValue(key, postFields.get(key));
					}

					formSender.submit();

					txn.setStatus("SENT");
					manager.save(txn);

				} catch (Exception e) {
					log.error("Error in sending the Payment", e);
					Notification notification = new Notification("Payment process error","Error happen during process the payment", Notification.Type.HUMANIZED_MESSAGE, true);
					notification.setDelayMsec(-1);
					notification.show(Page.getCurrent());
					return;
				}
			}
		});
	}

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

		List<Bill> list = BillManager.getInstance().getLatestBills(user.getContractNo(), user.getCity());
//		BigDecimal totalAmountvalue = list.get(0).getTotalAmount();
//		BigDecimal receivedAmmountValue = list.get(0).getReceivedAmmount();
//		BigDecimal subtract = totalAmountvalue.subtract(receivedAmmountValue);
		BigDecimal balance = user.getBalance();
		
		totalAnoountDouble = balance.doubleValue() +fees;
		totalAmount.setValue(totalAnoountDouble+ " AED" );

		Bill lasBill= list.get(0);
		lBDocNo = lasBill.getDocNo();
		lBDocType = lasBill.getDocType();
		lBYearCode = lasBill.getYearCode();
		
		int counter = 0;
		for (Bill bill : list) {

			Date currentReadingDate = bill.getBillDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			log.debug(currentReadingDate.toString());
			dates.get(counter).setValue(dateFormat.format(currentReadingDate));
			amounts.get(counter).setValue(bill.getTotalAmount().toString());
			Button button = billViews.get(counter);
			BrowserWindowOpener opener = new BrowserWindowOpener(BillPopupUI.class);
			opener.setFeatures("");
			opener.setParameter("accountId", bill.getContractNo());
			opener.setParameter("billId", bill.getId().toString());
			opener.setParameter("billCity", bill.getCity().toString());
			opener.extend(button);

			counter++;
			if (counter >= 3) {
				break;
			}

		}

	}
}
