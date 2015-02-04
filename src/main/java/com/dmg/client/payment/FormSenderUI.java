package com.dmg.client.payment;

import org.vaadin.risto.formsender.FormSenderBuilder;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FormSenderUI extends UI {

    private static final long serialVersionUID = 8564331787044638071L;

    @SuppressWarnings("unchecked")
    @Override
    public void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Formsender Demo");

        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);

        Panel panel = new Panel();
        panel.setWidth("400px");
        VerticalLayout panelContent = new VerticalLayout();
        panel.setContent(panelContent);

        Panel login = new Panel("Login");
        VerticalLayout loginLayout = new VerticalLayout();
        login.setContent(loginLayout);
        final TextField usernameField = new TextField("Username");
        final TextField passwordField = new TextField("Password");
        Button submit = new Button("Submit");

        loginLayout.addComponent(usernameField);
        loginLayout.addComponent(passwordField);
        loginLayout.addComponent(submit);

        final NativeSelect select = new NativeSelect("Choose submit action");
        select.setNullSelectionAllowed(false);
        select.addContainerProperty("caption", String.class, "");
        // TODO fix for V7
        // select.addItem("app").getItemProperty("caption")
        // .setValue("This application");
        select.addItem("printparameters.jsp").getItemProperty("caption")
                .setValue("JSP page");
        select.setItemCaptionPropertyId("caption");
        select.select("printparameters.jsp");

        panelContent.addComponent(login);
        panelContent.addComponent(select);
        mainLayout.addComponent(panel);

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(password)) {
            Label label = new Label("Name: " + name + " Password: " + password);
            mainLayout.addComponent(label);
        }

        submit.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 3248886901323015804L;

            @Override
            public void buttonClick(ClickEvent event) {
                FormSenderBuilder.create().withAction("http://www.google.com")
                        .withMethod(Method.POST)
                        .withValue("name", usernameField.getValue())
                        .withValue("password", passwordField.getValue())
                        .submit();
            }
        });
    }
}