package com.dmg.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.util.PropertiesManager;

public class MailManager {
	
	private static final Logger log = LoggerFactory.getLogger(MailManager.class);

//	private static final String SENDER_EMAIL = "dontreply@royalgas.com";
//	private static final String SENDER_USER_NAME = "dontreply@royalgas.com";
//	private static final String SENDER_PASSWORD = "X1Nmdd2T4CbJ";
//	private final String MAIL_SERVER_NAME = "mail.royalgas.com";

	private static final String SENDER_EMAIL = PropertiesManager.getInstance().getProperty("email.sender.email");
	private static final String SENDER_USER_NAME = PropertiesManager.getInstance().getProperty("email.semder.username");
	private static final String SENDER_PASSWORD = PropertiesManager.getInstance().getProperty("email.senderPassword");
	private static final String MAIL_SERVER_NAME = PropertiesManager.getInstance().getProperty("email.serverName");
	private static final String MAIL_DEUG = PropertiesManager.getInstance().getProperty("email.debug");
	private static final String MAIL_SERVER_PORT = PropertiesManager.getInstance().getProperty("email.server.port");
	
	
//	dontreply@royalgas.com
//	X1Nmdd2T4CbJ
//	mail.royalgas.com

	private static final MailManager INSTANCE = new MailManager();

	private MailManager() {
	}

	public static MailManager getInstance() {
		return INSTANCE;
	}

	public void sendMail(String to, String subject, String body) {

		final String username = SENDER_EMAIL;
		final String password = SENDER_PASSWORD;
		Properties props = new Properties();
		
		props.put("mail.smtp.host", MAIL_SERVER_NAME);
		props.put("mail.debug", MAIL_DEUG);
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		props.put("mail.smtp.port", MAIL_SERVER_PORT);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_USER_NAME));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(SENDER_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
//			message.setText(body);
			message.setSentDate(new Date());
			message.setContent(body, "text/html");
			log.info("sent Html Body Message");

			Transport.send(message);

			log.info("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		MailManager instance2 = getInstance();
		instance2.sendMail("mome9@hotmail.com", "subject text", "text body text body text body text body text body text body text  ");
	}
}