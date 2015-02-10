//package com.dmg.client.servlet;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//
//import org.apache.commons.lang.StringUtils;
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.dmg.admin.bean.Bill;
//import com.dmg.admin.bean.UserAccount;
//import com.dmg.admin.rest.bean.BillJson;
//import com.dmg.admin.rest.bean.BillsJson;
//import com.dmg.admin.rest.bean.ResultJson;
//import com.dmg.admin.rest.bean.UserAccountJson;
//import com.dmg.admin.rest.bean.UserAccountsJson;
//import com.dmg.admin.service.BillService;
//import com.dmg.admin.service.UserAccountService;
//import com.dmg.core.exception.DataAccessLayerException;
//import com.sun.jersey.api.core.PackagesResourceConfig;
//import com.sun.jersey.api.json.JSONConfiguration;
//
//@Path("/")
//public class RestServiceController extends PackagesResourceConfig {
//
//	private static final Logger logger = LoggerFactory.getLogger(RestServiceController.class);
////	BillService billService;
////	UserAccountService userAccountService;
//	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//	private final static String WARNING = "WARNING";
//	private final static String SUCCESS = "SUCCESS";
//	private final static String FAILED = "FAILED";
//
//	public RestServiceController() {
//		super("com.dmg.admin.rest.bean");
//		HashMap<String, Object> settings = new HashMap<String, Object>(1);
//		settings.put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
//		setPropertiesAndFeatures(settings);
////		billService = new BillService();
////		userAccountService = new UserAccountService();
//	}
//
//	@POST
//	@Path("/bills/test")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response testPost(@FormParam("test") String test) {
//
//		return Response.status(Status.OK).entity(test).build();
//	}
//
//	@POST
//	@Path("/bills/store")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response storeBills(String jsonString) {
//		ObjectMapper mapper = new ObjectMapper();
//		BillsJson billsJson;
//		ResultJson resultJson = new ResultJson();
//
//		try {
//			billsJson = mapper.readValue(jsonString, BillsJson.class);
//			if (billsJson.getBillJsonList().isEmpty()) {
//				logger.warn("List of bills is empty!!!");
//			} else {
//				for (BillJson billJson : billsJson.getBillJsonList()) {
//					try {
//						Bill bill = populateBill(billJson);
//						billService.storeBill(bill);
//					} catch (ParseException e) {
//						logger.error("ParseException Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo() + " DOC_TYPE: "
//								+ billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						logger.error("ParseException ==>", e.getMessage());
//
//						resultJson.setStatus(WARNING);
//						billJson.setErrorMsg(e.getMessage() + ": " + "Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo()
//								+ " DOC_TYPE: " + billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						resultJson.addBill(billJson);
//
//					} catch (DataAccessLayerException e) {
//						logger.error("DataAccessLayerException Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo() + " DOC_TYPE: "
//								+ billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						logger.error("DataAccessLayerException ==>", e.getMessage());
//
//						resultJson.setStatus(WARNING);
//						billJson.setErrorMsg(e.getCause().getMessage() + ": " + "Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo()
//								+ " DOC_TYPE: " + billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						resultJson.addBill(billJson);
//					}
//				}
//			}
//			if (resultJson.getStatus() == null || !resultJson.getStatus().equals(WARNING)) {
//				resultJson.setStatus(SUCCESS);
//			}
//			if (billsJson.getBillJsonList().size() != resultJson.getBills().size()) {
//				resultJson.setMessages("Records have been successfully saved");
//			}
//
//			return Response.status(Status.OK).entity(resultJson).build();
//		} catch (JsonParseException e) {
//			logger.error("JsonParseException json value is: " + jsonString);
//			logger.error("JsonParseException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonParseException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (JsonMappingException e) {
//			logger.error("JsonMappingException json value is: " + jsonString);
//			logger.error("JsonMappingException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonMappingException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (IOException e) {
//			logger.error("IOException json value is: " + jsonString);
//			logger.error("IOException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("IOException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		}
//
//	}
//
//	@POST
//	@Path("/bills/update")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateBills(String jsonString) {
//		ObjectMapper mapper = new ObjectMapper();
//		BillsJson billsJson;
//		ResultJson resultJson = new ResultJson();
//
//		try {
//			billsJson = mapper.readValue(jsonString, BillsJson.class);
//			if (billsJson.getBillJsonList().isEmpty()) {
//				logger.warn("List of bills is empty!!!");
//			} else {
//				for (BillJson billJson : billsJson.getBillJsonList()) {
//					try {
//						Bill bill = populateBill(billJson);
//						Bill billDB = billService.findBill(bill);
//						if (billDB == null) {
//							logger.error("Bill Not Found To Update==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo() + " DOC_TYPE: "
//									+ billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//
//							resultJson.setStatus(WARNING);
//							billJson.setErrorMsg("Bill not found: " + "Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo()
//									+ " DOC_TYPE: " + billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//							resultJson.addBill(billJson);
//						} else {
//							bill.setId(billDB.getId());
//							bill.setCreationDate(billDB.getCreationDate());
//							billService.storeBill(bill);
//						}
//
//					} catch (ParseException e) {
//						logger.error("ParseException Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo() + " DOC_TYPE: "
//								+ billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						logger.error("ParseException ==>", e.getMessage());
//
//						resultJson.setStatus(WARNING);
//						billJson.setErrorMsg(e.getMessage() + ": " + "Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo()
//								+ " DOC_TYPE: " + billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						resultJson.addBill(billJson);
//						// resultJson.setMessage(e.getMessage());
//					} catch (DataAccessLayerException e) {
//						logger.error("DataAccessLayerException Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo() + " DOC_TYPE: "
//								+ billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						logger.error("DataAccessLayerException ==>", e.getMessage());
//
//						resultJson.setStatus(WARNING);
//						billJson.setErrorMsg(e.getMessage() + ": " + "Bill is==> CITY: " + billJson.getCity() + " YEAR_CODE: " + billJson.getYearCode() + " DOC_NO: " + billJson.getDocNo()
//								+ " DOC_TYPE: " + billJson.getDocType() + " SRNO: " + billJson.getSerialNo());
//						resultJson.addBill(billJson);
//					}
//				}
//
//			}
//			if (resultJson.getStatus() == null || !resultJson.getStatus().equals(WARNING)) {
//				resultJson.setStatus(SUCCESS);
//			}
//			if (billsJson.getBillJsonList().size() != resultJson.getBills().size()) {
//				resultJson.setMessages("Records have been successfully saved");
//			}
//			return Response.status(Status.OK).entity(resultJson).build();
//		} catch (JsonParseException e) {
//			logger.error("JsonParseException json value is: " + jsonString);
//			logger.error("JsonParseException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonParseException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (JsonMappingException e) {
//			logger.error("JsonMappingException json value is: " + jsonString);
//			logger.error("JsonMappingException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonMappingException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (IOException e) {
//			logger.error("IOException json value is: " + jsonString);
//			logger.error("IOException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("IOException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		}
//
//	}
//
//	@GET
//	@Path("/users/get")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getUserAccounts() {
//		UserAccountsJson userAccountsJson = new UserAccountsJson();
//		List<UserAccount> modifiedUsers;
//
//		try {
//			modifiedUsers = userAccountService.getModifiedUsers();
//			for (UserAccount userAccount : modifiedUsers) {
//				UserAccountJson userAccountJson = populateUserAccountJson(userAccount);
//				userAccountsJson.addUserAccountJson(userAccountJson);
//				userAccount.setStatus(2);// setting status to 2 means sent to
//											// dll but waiting for update reqest
//											// to reset to 0
//				userAccountService.update(userAccount);
//			}
//		} catch (DataAccessLayerException e) {
//			logger.error("DataAccessLayerException useraccounts ==>", e.getMessage());
//			return Response.serverError().entity(userAccountsJson).build();
//		}
//		return Response.ok().entity(userAccountsJson).build();
//	}
//
//	@POST
//	@Path("/users/store")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response storeUsers(String jsonString) {
//		ObjectMapper mapper = new ObjectMapper();
//		UserAccountsJson userAccountsJson;
//		ResultJson resultJson = new ResultJson();
//
//		try {
//			userAccountsJson = mapper.readValue(jsonString, UserAccountsJson.class);
//			for (UserAccountJson userAccountJson : userAccountsJson.getUserAccountJsonList()) {
//				try {
//
//					UserAccount userAccount = populateUserAccount(userAccountJson);
//					userAccount.setStatus(0);// reset the status to zero means
//												// coming from dll
//					userAccountService.update(userAccount);
//
//				} catch (DataAccessLayerException e) {
//					logger.error("DataAccessLayerException saving useraccount ==>", e.getMessage());
//					resultJson.setStatus(WARNING);
//					userAccountJson.setErrorMsg(e.getMessage() + ": " + "UserAccount is==> NAME: " + userAccountJson.getName());
//					resultJson.addUser(userAccountJson);
//				}
//			}
//			if (resultJson.getStatus() == null || !resultJson.getStatus().equals(WARNING)) {
//				resultJson.setStatus(SUCCESS);
//			}
//			if (userAccountsJson.getUserAccountJsonList().size() != resultJson.getBills().size()) {
//				resultJson.setMessages("Records have been successfully saved");
//			}
//			return Response.status(Status.OK).entity(resultJson).build();
//		} catch (JsonParseException e) {
//			logger.error("JsonParseException json value is: " + jsonString);
//			logger.error("JsonParseException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonParseException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (JsonMappingException e) {
//			logger.error("JsonMappingException json value is: " + jsonString);
//			logger.error("JsonMappingException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonMappingException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (IOException e) {
//			logger.error("IOException json value is: " + jsonString);
//			logger.error("IOException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("IOException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		}
//
//	}
//
//	@POST
//	@Path("/users/update")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateUsers(String jsonString) {
//		ObjectMapper mapper = new ObjectMapper();
//		UserAccountsJson userAccountsJson;
//		ResultJson resultJson = new ResultJson();
//
//		try {
//			userAccountsJson = mapper.readValue(jsonString, UserAccountsJson.class);
//			for (UserAccountJson userAccountJson : userAccountsJson.getUserAccountJsonList()) {
//				try {
//
//					UserAccount userAccount = populateUserAccount(userAccountJson);
//					if (StringUtils.isBlank(userAccount.getContractNo()) || StringUtils.isBlank(userAccount.getCity())) {
//						logger.error("contractNo OR city not found:  " + "UserAccount is==> NAME: " + userAccountJson.getName());
//						resultJson.setStatus(WARNING);
//						userAccountJson.setErrorMsg("contractNo OR city not found:  " + "UserAccount is==> NAME: " + userAccountJson.getName());
//						resultJson.addUser(userAccountJson);
//					} else {
//						UserAccount dbUser = userAccountService.findUsersToUpdate(userAccount.getContractNo(), userAccount.getCity());
//						if (dbUser == null) {
//							logger.error("User account not found in DB:  " + "UserAccount is==> NAME: " + userAccountJson.getName());
//							resultJson.setStatus(WARNING);
//							userAccountJson.setErrorMsg("User account not found in DB:  " + "UserAccount is==> NAME: " + userAccountJson.getName());
//							resultJson.addUser(userAccountJson);
//						}
//						userAccount.setId(dbUser.getId());
//						userAccount.setStatus(0);// reset the status to zero
//													// means coming from dll
//						userAccountService.update(userAccount);
//					}
//
//				} catch (DataAccessLayerException e) {
//					logger.error("DataAccessLayerException saving useraccount ==>", e.getMessage());
//					resultJson.setStatus(WARNING);
//					userAccountJson.setErrorMsg(e.getMessage() + ": " + "UserAccount is==> NAME: " + userAccountJson.getName());
//					resultJson.addUser(userAccountJson);
//				}
//			}
//			if (resultJson.getStatus() == null || !resultJson.getStatus().equals(WARNING)) {
//				resultJson.setStatus(SUCCESS);
//			}
//			if (userAccountsJson.getUserAccountJsonList().size() != resultJson.getBills().size()) {
//				resultJson.setMessages("Records have been successfully saved");
//			}
//			return Response.status(Status.OK).entity(resultJson).build();
//		} catch (JsonParseException e) {
//			logger.error("JsonParseException json value is: " + jsonString);
//			logger.error("JsonParseException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonParseException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (JsonMappingException e) {
//			logger.error("JsonMappingException json value is: " + jsonString);
//			logger.error("JsonMappingException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("JsonMappingException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		} catch (IOException e) {
//			logger.error("IOException json value is: " + jsonString);
//			logger.error("IOException ==>", e.getMessage());
//			resultJson.setStatus(FAILED);
//			resultJson.setMessages("IOException json value is: " + jsonString);
//			return Response.serverError().entity(resultJson).build();
//		}
//
//	}
//
//	private Bill populateBill(BillJson billJson) throws ParseException {
//		Bill bill = new Bill();
//		bill.setDocNo(billJson.getDocNo());
//		bill.setDocType(billJson.getDocType());
//		bill.setYearCode(billJson.getYearCode());
//		bill.setSerialNo(billJson.getSerialNo());
//		bill.setPartyName(billJson.getPartyName());
//		bill.setPrevBalance(StringUtils.isNotBlank(billJson.getPrevBalance()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getPrevBalance())) : null);
//		bill.setLastReceivingDate(StringUtils.isNotBlank(billJson.getLastReceivingDate()) ? sdf.parse(billJson.getLastReceivingDate()) : null);
//		bill.setLastReceivingAmount(StringUtils.isNotBlank(billJson.getLastReceivingAmount()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getLastReceivingAmount())) : null);
//		bill.setCity(billJson.getCity());
//		bill.setBillDate(StringUtils.isNotBlank(billJson.getBillDate()) ? sdf.parse(billJson.getBillDate()) : null);
//		bill.setService(StringUtils.isNotBlank(billJson.getService()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getService())) : null);
//		bill.setGasDifference(StringUtils.isNotBlank(billJson.getGasDifference()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getGasDifference())) : null);
//		bill.setLastReceivedPayReference(billJson.getLastReceivedPayReference());
//		bill.setCollectorName(billJson.getCollectorName());
//		bill.setLastReading(billJson.getLastReading());
//		bill.setLastReadingDate(StringUtils.isNotBlank(billJson.getLastReadingDate()) ? sdf.parse(billJson.getLastReadingDate()) : null);
//		bill.setCurrentReading(billJson.getCurrentReading());
//		bill.setCurrentReadingDate(StringUtils.isNotBlank(billJson.getCurrentReadingDate()) ? sdf.parse(billJson.getCurrentReadingDate()) : null);
//		bill.setBuildingCode(billJson.getBuildingCode());
//		bill.setBuildingName(billJson.getBuildingName());
//		bill.setApartmentCode(billJson.getApartmentCode());
//		bill.setTotalUnit(billJson.getTotalUnit());
//		bill.setUnitPrice(billJson.getUnitPrice());
//		bill.setAmount(StringUtils.isNotBlank(billJson.getAmount()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getAmount())) : null);
//		bill.setTotalAmount(StringUtils.isNotBlank(billJson.getTotalAmount()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getTotalAmount())) : null);
//		bill.setContractNo(billJson.getContractNo());
//		bill.setReceivedAmmount(StringUtils.isNotBlank(billJson.getReceivedAmmount()) ? BigDecimal.valueOf(Double.parseDouble(billJson.getReceivedAmmount())) : null);
//
//		return bill;
//	}
//
//	private UserAccount populateUserAccount(UserAccountJson userAccountJson) {
//		UserAccount userAccount = new UserAccount();
//		userAccount.setAppartmentNumber(userAccountJson.getAppartmentNumber());
//		userAccount.setBuildingNumber(userAccountJson.getBuildingNumber());
//		userAccount.setCity(userAccountJson.getContractNo());
//		userAccount.setContractNo(userAccountJson.getAppartmentNumber());
//		userAccount.setEmail(userAccountJson.getEmail());
//		userAccount.setMobile(userAccountJson.getMobile());
//		userAccount.setPhone(userAccountJson.getPhone());
//		userAccount.setName(userAccountJson.getName());
//		userAccount.setPobox(userAccountJson.getPobox());
//		userAccount.setPoboxCity(userAccountJson.getPoboxCity());
//
//		return userAccount;
//	}
//
//	private UserAccountJson populateUserAccountJson(UserAccount userAccount) {
//		UserAccountJson userAccountJson = new UserAccountJson();
//		userAccountJson.setAppartmentNumber(userAccount.getAppartmentNumber());
//		userAccountJson.setBuildingNumber(userAccount.getBuildingNumber());
//		userAccountJson.setCity(userAccount.getContractNo());
//		userAccountJson.setContractNo(userAccount.getAppartmentNumber());
//		userAccountJson.setEmail(userAccount.getEmail());
//		userAccountJson.setMobile(userAccount.getMobile());
//		userAccountJson.setPhone(userAccount.getPhone());
//		userAccountJson.setName(userAccount.getName());
//		userAccountJson.setPobox(userAccount.getPobox());
//		userAccountJson.setPoboxCity(userAccount.getPoboxCity());
//
//		return userAccountJson;
//	}
//
//}
