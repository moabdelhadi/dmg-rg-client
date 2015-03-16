package com.dmg.client.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmg.client.payment.PaymentManager;

@WebServlet("/payResponse")
public class PayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(PaymentManager.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("get");
	try{
			
			Map fields = new HashMap<String, String>();
			int count = 0;
			Enumeration<String> parameterNames = request.getParameterNames();
			for (Enumeration e = parameterNames; e.hasMoreElements();) {
				String fieldName = (String) e.nextElement();
				String fieldValue = request.getParameter(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					fields.put(fieldName, fieldValue);
					log.debug("fiels" + count++ + ": " + fieldName + " = "
							+ fieldValue);
				}
			}
			
			String processSerponse = PaymentManager.getInstance().processResponse(fields);
			String responseDescription = getResponseDescription(processSerponse);
			
			request.setAttribute("resultMessage", responseDescription);
			request.setAttribute("resultVal", processSerponse);
		    RequestDispatcher view = request.getRequestDispatcher("/views/responseMessage.jsp");

		    response.setHeader("Content-Type","text/html");
			response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
//			response.setDateHeader("Last-Modified", Calendar.getInstance().getTimeInMillis());
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.setHeader("Pragma","no-cache");

		    
		    view.forward(request, response);

			
		}catch(Exception e){
			
			log.error("error in handling response",e);
//			response.setHeader("Content-Type","text/html, charset=ISO-8859-1");
			response.setHeader("Content-Type","text/html");

			response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
//			response.setDateHeader("Last-Modified", Calendar.getInstance().getTimeInMillis());
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.setHeader("Pragma","no-cache");
		    
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			
			log.debug("post");
			Map fields = new HashMap<String, String>();
			int count = 0;
			Enumeration<String> parameterNames = request.getParameterNames();
			for (Enumeration e = parameterNames; e.hasMoreElements();) {
				String fieldName = (String) e.nextElement();
				String fieldValue = request.getParameter(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					fields.put(fieldName, fieldValue);
					log.debug("fiels" + count++ + ": " + fieldName + " = "
							+ fieldValue);
				}
			}
			
			String processSerponse = PaymentManager.getInstance().processResponse(fields);
			String responseDescription = getResponseDescription(processSerponse);
			
//			request.setAttribute("resultMessage", responseDescription);
//			request.setAttribute("resultVal", processSerponse);
//		    RequestDispatcher view = request.getRequestDispatcher("/views/responseMessage.jsp");
//		    view.forward(request, resp);
//		    log.debug("forwarding to jsp Page");
			response.setHeader("Content-Type","text/html, charset=ISO-8859-1");
			response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
			response.setDateHeader("Last-Modified", Calendar.getInstance().getTimeInMillis());
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.setHeader("Pragma","no-cache");

//		    view.forward(request, resp);
		    //resp.sendRedirect("/views/responseMessage.jsp");
//			PrintWriter out  = resp.getWriter();
//			out.println("<h1>" + responseDescription + "</h1>");
		    
//		    resp.setHeader("Content-Type","text/html, charset=ISO-8859-1");
//		    resp.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
//		    resp.setDateHeader("Last-Modified", Calendar.getInstance().getTimeInMillis());
//		    resp.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
//		    resp.setHeader("Pragma","no-cache");

			
		}catch(Exception e){
			
			log.error("error in handling response",e);
			response.setHeader("Content-Type","text/html, charset=ISO-8859-1");
			response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
			response.setDateHeader("Last-Modified", Calendar.getInstance().getTimeInMillis());
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.setHeader("Pragma","no-cache");
		    
		}
		
	}
	
	
	/*
	 * This function uses the returned status code retrieved from the Digital
	 * Response and returns an appropriate description for the code
	 * 
	 * @param vResponseCode String containing the vpc_TxnResponseCode
	 * 
	 * @return description String containing the appropriate description
	 */
	String getResponseDescription(String vResponseCode) {

		String result = "";
		log.debug("vResponseCode"+ vResponseCode);
		// check if a single digit response code
		if (vResponseCode.length() == 1) {

			// Java cannot switch on a string so turn everything to a char
			char input = vResponseCode.charAt(0);

			switch (input) {
			case '0':
				result = "Transaction Successful";
				break;
			case '1':
				result = "Unknown Error";
				break;
			case '2':
				result = "Bank Declined Transaction";
				break;
			case '3':
				result = "No Reply from Bank";
				break;
			case '4':
				result = "Expired Card";
				break;
			case '5':
				result = "Insufficient Funds";
				break;
			case '6':
				result = "Error Communicating with Bank";
				break;
			case '7':
				result = "Payment Server System Error";
				break;
			case '8':
				result = "Transaction Type Not Supported";
				break;
			case '9':
				result = "Bank declined transaction (Do not contact Bank)";
				break;
			case 'A':
				result = "Transaction Aborted";
				break;
			case 'C':
				result = "Transaction Cancelled";
				break;
			case 'D':
				result = "Deferred transaction has been received and is awaiting processing";
				break;
			case 'F':
				result = "3D Secure Authentication failed";
				break;
			case 'I':
				result = "Card Security Code verification failed";
				break;
			case 'L':
				result = "Shopping Transaction Locked (Please try the transaction again later)";
				break;
			case 'N':
				result = "Cardholder is not enrolled in Authentication Scheme";
				break;
			case 'P':
				result = "Transaction has been received by the Payment Adaptor and is being processed";
				break;
			case 'R':
				result = "Transaction was not processed - Reached limit of retry attempts allowed";
				break;
			case 'S':
				result = "Duplicate SessionID (OrderInfo)";
				break;
			case 'T':
				result = "Address Verification Failed";
				break;
			case 'U':
				result = "Card Security Code Failed";
				break;
			case 'V':
				result = "Address Verification and Card Security Code Failed";
				break;
			case '?':
				result = "Transaction status is unknown";
				break;
			default:
				result = "Unable to be determined";
			}

			return result;
		} else {
			return "No Value Returned";
		}
	} // getResponseDescription()
}
