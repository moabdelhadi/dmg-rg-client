package com.dmg.simplepayment.views;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

@Theme("dmg-theme")
public class BillPopupUI extends UI{

	@Override
	protected void init(VaadinRequest request) {
		
		setSizeFull();
		CustomLayout customLayout = new CustomLayout("billView");

		setContent(customLayout);
	}

}
