package com.dmg.client.simplepayment.views.response;

import java.util.Map;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dmg-theme")
public class PaymentResponse extends UI {

	@Override
	protected void init(VaadinRequest request) {

		//String printParams = printParams(request);
		
		
		VerticalLayout layout = new VerticalLayout();
		
		Label label = new Label("hello: printParams="+"");

		layout.addComponent(label);
		
		setContent(layout);
		
		
		
		
		
		
	}

	private String printParams(VaadinRequest request) {
		
		
		StringBuilder result= new StringBuilder();
		Map<String, String[]> parameterMap = request.getParameterMap();
		boolean first=true;
		for (String key : parameterMap.keySet()) {
			
			System.out.println(key);
			
			if(key.startsWith("v-") || key.equals("theme")){
				continue;
			}
			
			if(!first){
				result.append(",");
			}
			

			
			String[] strings = parameterMap.get(key);
			result.append(key);
			result.append("=");
			
			if(strings!=null){
				String value=strings[0];
				result.append(value);
			}
			first=false;
		}
		
		return result.toString();
		
	}
}
