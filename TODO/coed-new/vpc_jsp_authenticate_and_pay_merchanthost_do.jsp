<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%--

/* -----------------------------------------------------------------------------

 Version 1.0

------------------ Disclaimer --------------------------------------------------

Copyright 2003 Dialect Holdings.  All rights reserved.

This document is provided by Dialect Holdings on the basis that you will treat
it as confidential.

No part of this document may be reproduced or copied in any form by any means
without the written permission of Dialect Holdings.  Unless otherwise expressly
agreed in writing, the information contained in this document is subject to
change without notice and Dialect Holdings assumes no responsibility for any
alteration to, or any error or other deficiency, in this document.

All intellectual property rights in the Document and in all extracts and things
derived from any part of the Document are owned by Dialect and will be assigned
to Dialect on their creation. You will protect all the intellectual property
rights relating to the Document in a manner that is equal to the protection
you provide your own intellectual property.  You will notify Dialect
immediately, and in writing where you become aware of a breach of Dialect's
intellectual property rights in relation to the Document.

The names "Dialect", "QSI Payments" and all similar words are trademarks of
Dialect Holdings and you must not use that name or any similar name.

Dialect may at its sole discretion terminate the rights granted in this
document with immediate effect by notifying you in writing and you will
thereupon return (or destroy and certify that destruction to Dialect) all
copies and extracts of the Document in its possession or control.

Dialect does not warrant the accuracy or completeness of the Document or its
content or its usefulness to you or your merchant customers.   To the extent
permitted by law, all conditions and warranties implied by law (whether as to
fitness for any particular purpose or otherwise) are excluded.  Where the
exclusion is not effective, Dialect limits its liability to $100 or the
resupply of the Document (at Dialect's option).

Data used in examples and sample data files are intended to be fictional and
any resemblance to real persons or companies is entirely coincidental.

Dialect does not indemnify you or any third party in relation to the content or
any use of the content as contemplated in these terms and conditions.

Mention of any product not owned by Dialect does not constitute an endorsement
of that product.

This document is governed by the laws of New South Wales, Australia and is
intended to be legally binding.

-------------------------------------------------------------------------------

Following is a copy of the disclaimer / license agreement provided by RSA:

Copyright (C) 1991-2, RSA Data Security, Inc. Created 1991. All rights reserved.

License to copy and use this software is granted provided that it is identified
as the "RSA Data Security, Inc. MD5 Message-Digest Algorithm" in all material
mentioning or referencing this software or this function.

License is also granted to make and use derivative works provided that such
works are identified as "derived from the RSA Data Security, Inc. MD5
Message-Digest Algorithm" in all material mentioning or referencing the
derived work.

RSA Data Security, Inc. makes no representations concerning either the
merchantability of this software or the suitability of this software for any
particular purpose. It is provided "as is" without express or implied warranty
of any kind.

These notices must be retained in any copies of any part of this documentation
and/or software.

--------------------------------------------------------------------------------

This example assumes that a form has been sent to this example with the
required fields. The example then processes the command and displays the
receipt or error to a HTML page in the users web browser.

*****
NOTE:
*****

  For jdk1.2, 1.3
  * Must have jsse.jar, jcert.jar and jnet.jar in your classpath
  * Best approach is to make them installed extensions -
    i.e. put them in the jre/lib/ext directory.

  For jdk1.4 (jsse is already part of default installation - should run fine)

--------------------------------------------------------------------------------

 @author Dialect Payment Solutions Pty Ltd Group

------------------------------------------------------------------------------*/

--%>
<%@ page import="java.util.List,
                 java.util.ArrayList,
                 java.util.Collections,
                 java.util.Comparator,
                 java.util.Iterator,
                 java.util.Enumeration,
                 java.util.Date,
                 java.security.MessageDigest,
                 java.util.Map,
                 java.net.URLEncoder,
                 java.util.HashMap"%>

<%! // Define Constants
    // ****************
    // This is secret for encoding the MD5 hash
    // This secret will vary from merchant to merchant
    // static final String SECURE_SECRET = "your-secure-hash-secret";
    static String SECURE_SECRET = "787F5B76DB89CC4D2D20B0516D43431C";

    // This is an array for creating hex chars
    static final char[] HEX_TABLE = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

//  ----------------------------------------------------------------------------

    String hashKeys = new String();
    String hashValues = new String();

   /**
    * This method is for sorting the fields and creating an MD5 secure hash.
    *
    * @param fields is a map of all the incoming hey-value pairs from the VPC
    * @param buf is the hash being returned for comparison to the incoming hash
    */
    String hashAllFields(Map fields) {

	    hashKeys = "";
	    hashValues = "";
	
	    // create a list and sort it
	    List fieldNames = new ArrayList(fields.keySet());
	    Collections.sort(fieldNames);
	
	    // create a buffer for the md5 input and add the secure secret first
	    StringBuffer buf = new StringBuffer();
	    buf.append(SECURE_SECRET);
	
	    // iterate through the list and add the remaining field values
	    Iterator itr = fieldNames.iterator();
	
	    while (itr.hasNext()) {
	        String fieldName = (String) itr.next();
	        String fieldValue = (String) fields.get(fieldName);
	            hashKeys += fieldName + ", ";
	        if ((fieldValue != null) && (fieldValue.length() > 0)) {
	            buf.append(fieldValue);
	        }
	    }
	
	    MessageDigest md5 = null;
	    byte[] ba = null;
	
	    // create the md5 hash and ISO-8859-1 encode it
	    try {
	        md5 = MessageDigest.getInstance("MD5");
	        ba = md5.digest(buf.toString().getBytes("ISO-8859-1"));
	    } catch (Exception e) {} // wont happen
	
	    hashValues = buf.toString();
	    return hex(ba);
	
	} // end hashAllFields()

//  ----------------------------------------------------------------------------

    /**
     * Returns Hex output of byte array
     */
    static String hex(byte[] input) {
        // create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }

//  ----------------------------------------------------------------------------
%><%
    // *******************************************
    // START OF MAIN PROGRAM
    // *******************************************

	// The Page does a transaction using the Virtual Payment Client

    // retrieve all the parameters into a hash map
    Map fields = new HashMap();
    Enumeration e = request.getParameterNames();

    while (e.hasMoreElements()) {
        String fieldName = (String) e.nextElement();
        String fieldValue = request.getParameter(fieldName);
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }

    // no need to send the vpc url and submit button to the vpc
    String vpcURL = (String) fields.get("virtualPaymentClientURL");
    fields.remove("virtualPaymentClientURL");
    fields.remove("SubButL");


    // Add the submit button that will be used for the HTTPS POST on this page as
    // it will also get added to the POST data when the user submits the form and
    // will therefore need to be incorporated into the secure hash.
    fields.put("submit", "Continue");

    // Create MD5 secure hash and insert it into the hash map if it was created
    // created. Remember if SECURE_SECRET = "" it will not be created
    if (SECURE_SECRET != null && SECURE_SECRET.length() > 0) {
        String secureHash = hashAllFields(fields);
        fields.put("vpc_SecureHash", secureHash);
    }

    response.setHeader("Content-Type","text/html, charset=ISO-8859-1");
    response.setHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
    response.setDateHeader("Last-Modified", new Date().getTime());
    response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
    response.setHeader("Pragma","no-cache");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

    <!-- Version 3.4
    Copyright 2005 Dialect Solutions Holdings.  All rights reserved. -->

    <!-- ------------ Disclaimer ---------------------------------------------------

    This document is provided by Dialect Solutions Holdings on the basis that you
    will treat it as confidential.

    No part of this document may be reproduced or copied in any form by any means
    without the written permission of Dialect Solutions Holdings.  Unless otherwise
    expressly agreed in writing, the information contained in this document is
    subject to change without notice and Dialect Solutions Holdings assumes no
    responsibility for any alteration to, or any error or other deficiency, in this
    document.

    All intellectual property rights in the Document and in all extracts and things
    derived from any part of the Document are owned by Dialect and will be assigned
    to Dialect on their creation. You will protect all the intellectual property
    rights relating to the Document in a manner that is equal to the protection you
    provide your own intellectual property.  You will notify Dialect immediately,
    and in writing where you become aware of a breach of Dialect's intellectual
    property rights in relation to the Document.

    The names "Dialect", "QSI Payments" and all similar words are trademarks of
    Dialect Solutions Holdings and you must not use that name or any similar name.

    Dialect may at its sole discretion terminate the rights granted in this document
    with immediate effect by notifying you in writing and you will thereupon return
    (or destroy and certify that destruction to Dialect) all copies and extracts of
    the Document in its possession or control.

    Dialect does not warrant the accuracy or completeness of the Document or its
    content or its usefulness to you or your merchant customers. To the extent
    permitted by law, all conditions and warranties implied by law (whether as to
    fitness for any particular purpose or otherwise) are excluded.  Where the
    exclusion is not effective, Dialect limits its liability to AU$100 or the
    resupply of the Document (at Dialect's option).

    Data used in examples and sample data files are intended to be fictional and any
    resemblance to real persons or companies is entirely coincidental.

    Dialect does not indemnify you or any third party in relation to the content or
    any use of the content as contemplated in these terms and conditions.

    Mention of any product not owned by Dialect does not constitute an endorsement
    of that product.

    This document is governed by the laws of New South Wales, Australia and is
    intended to be legally binding.
    ---------------------------------------------------------------------------- -->

    <head><title>Virtual Payment Client Example</title>
        <meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <style type='text/css'>
            <!--
            H1       { font-family:Arial,sans-serif; font-size:20pt; color:#08185A; font-weight:600; margin-bottom:0.1em}
            H2       { font-family:Arial,sans-serif; font-size:14pt; color:#08185A; font-weight:100; margin-top:0.1em}
            H2.co    { font-family:Arial,sans-serif; font-size:24pt; color:#08185A; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}
            H3.co    { font-family:Arial,sans-serif; font-size:16pt; color:#FFFFFF; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}
            BODY     { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#08185A background-color:#FFFFFF }
            TR       { height:25px; }
            TR.shade { height:25px; background-color:#E1E1E1 }
            TR.title { height:25px; background-color:#C1C1C1 }
            TD       { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A }
            P        { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#FFFFFF }
            P.blue   { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#08185A }
            P.red    { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0066 }
            P.green  { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#00AA00 }
            DIV.bl   { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#C1C1C1 }
            LI       { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0066 }
            INPUT    { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:bold }
            SELECT   { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:bold; width:300 }
            TEXTAREA { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:normal; scrollbar-arrow-color:#08185A; scrollbar-base-color:#E1E1E1 }
            A:link   { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A }
            A:visited{ font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A }
            A:hover  { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0000 }
            A:active { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0000 }
            -->
        </style>
    </head>
    <body>
        <!-- start branding table -->
        <table width='100%' border='2' cellpadding='2' bgcolor='#C1C1C1'>
            <tr>
                <td bgcolor='#E1E1E1' width='90%'><h2 class='co'>&nbsp;Virtual Payment Client Example</h2></td>
                <td bgcolor='#C1C1C1' align='center'><h3 class='co'>MiGS</h3></td>
            </tr>
        </table>
        <!-- end branding table -->

        <center><h1>JSP 3-Party Super Transaction</h1></center>

        <form name="RedirectForm" action="<%=vpcURL%>" method="post">
            <table width="80%" align="center" border="0" cellpadding='0' cellspacing='0'>
<%
    for (Iterator itr = fields.keySet().iterator(); itr.hasNext();) {
        String fieldName = (String) itr.next();
%>
                <tr>
                    <td><input type="hidden" name="<%=fieldName%>" value="<%=fields.get(fieldName)%>"><%=fieldName%>:&nbsp;</td>
                    <td><%=fields.get(fieldName)%></td>
                </tr>
<%
    }
%>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr><td colspan="2" align="center"><input type="submit" name="submit" value="Continue" /></td></tr>
            </table>
        </form>
    </body>
    <head>
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="expires" content="0" />
    </head>
</html>