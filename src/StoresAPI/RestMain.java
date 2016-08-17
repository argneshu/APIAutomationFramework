//package StoresAPI;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import Parameters.APIParameters;
//import Payload.APIPayload;
//import TestReporting.SuiteReporting;
//import TestReporting.TestCaseReporting;
//import VerificationChecks.Verification_Checks;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class RestMain {
//
//	static SuiteReporting suitreporting = new SuiteReporting("Suite");;
//	static TestCaseReporting testCaseReporting = new TestCaseReporting("test");
//	static Long starttime;
//	static Long endtime;
//
//	@SuppressWarnings("static-access")
//	private static void getStoresInfo1() throws IOException, JSONException {
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsMyStores();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			// System.out.println(jsonString);
//			JSONArray arr = new JSONArray(jsonString);
//			for (int i = 0; i < arr.length(); i++) {
//				JSONObject jso = arr.getJSONObject(i);
//				String name = jso.getString("name");
//				String store_id = jso.getString("store_id");
//				endtime = System.currentTimeMillis();
//				if (name.equalsIgnoreCase(Verification_Checks.name2)
//						&& store_id
//								.equalsIgnoreCase(Verification_Checks.StoreID1)) {
//					testCaseReporting.teststepreporting("Store name is: "
//							+ name + " AND Store id is :" + store_id, "PASS",
//							"New-Gen-retail with store id 81307 ",
//							getTotalExecutionTime(starttime, endtime));
//
//				} else {
//					testCaseReporting.teststepreporting("Store name is: "
//							+ name + " AND Store id is :" + store_id, "Fail",
//							"Sanchar World with store id 83523 ",
//							getTotalExecutionTime(starttime, endtime));
//
//				}
//
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"get stores info 1 should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//
//	}
//
//	@SuppressWarnings({ "static-access", "unused" })
//	private static void storeLoginAPI() throws JSONException, IOException {
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForLoginAPI();
//			System.out.println(payload);
//			ParamObject params = APIParameters.getParamsForStoreLogin(payload);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			// System.out.println(jsonString);
//			JSONObject root = new JSONObject(jsonString);
//			String name = root.getString("username");
//			String token = root.getString("token");
//			endtime = System.currentTimeMillis();
//			if (name.equalsIgnoreCase(Verification_Checks.name1)) {
//				testCaseReporting.teststepreporting("Store name is: " + name
//						+ " and token is :" + token, "PASS",
//						"New-Gen-Retail with token id " + token,
//						getTotalExecutionTime(starttime, endtime));
//				getStoresInfo1();
//			} else {
//				testCaseReporting.teststepreporting("Store name is: " + name
//						+ " and token is :" + token, "FAIL",
//						"New-Gen-Retail with token id " + token,
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"store login name should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//
//	}
//
//	@SuppressWarnings({ "unused", "static-access" })
//	private static void verifyStoreAPI() throws JSONException, IOException {
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForVerifyStoresAPI();
//			System.out.println(payload);
//			ParamObject params = APIParameters.getParamsVerifyStore(payload);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			// System.out.println(jsonString);
//			JSONObject root = new JSONObject(jsonString);
//			JSONArray slugname = root.getJSONArray("changed");
//			String username = (String) slugname.get(0);
//			endtime = System.currentTimeMillis();
//			if (username.equalsIgnoreCase(Verification_Checks.username)) {
//				TestCaseReporting.teststepreporting(username, "PASS",
//						"Username should be " + username,
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				TestCaseReporting.teststepreporting(username, "FAIL",
//						"Username should be" + username,
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"verify stores info should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//
//	}
//
//	@SuppressWarnings("static-access")
//	private static void getListOfStates() throws IOException, JSONException {
//		try {
//			starttime = System.currentTimeMillis();
//			boolean flag = true;
//			String nameValue = "";
//			ParamObject params = APIParameters.getParamsForGetListOfStates();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			// System.out.println(jsonString);
//			JSONArray rootarray = new JSONArray(jsonString);
//			for (int i = 0; i < rootarray.length(); i++) {
//				JSONObject obj = rootarray.getJSONObject(i);
//				String name = obj.getString("name");
//				name = name.replace(" ", "");
//				// System.out.print(name);
//				nameValue = nameValue + " " + name;
//
//				// System.out.println(nameValue);
//				if (Verification_Checks.state.contains(name)) {
//
//				} else {
//					flag = false;
//					break;
//				}
//
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				TestCaseReporting.teststepreporting("States names are "
//						+ nameValue, "PASS", "Statename should contain "
//						+ nameValue, getTotalExecutionTime(starttime, endtime));
//			} else {
//				TestCaseReporting.teststepreporting("States names are "
//						+ nameValue, "FAIL", "Statename should contain "
//						+ nameValue, getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"get states info should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//	}
//
//	@SuppressWarnings("static-access")
//	private static void getListOfCities() throws IOException, JSONException {
//		try {
//			starttime = System.currentTimeMillis();
//			boolean flag = true;
//			String nameValue = "";
//			ParamObject params = APIParameters.getParamsForGetListOfStates();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			ArrayList<String> ids = APIParameters
//					.getIDsArrayFromStates(jsonString);
//			for (String id : ids) {
//				ParamObject citiesparams = APIParameters
//						.getParamsForGetListOfCities(id);
//				String jsonStringForCities = (String) RestClient
//						.getJSONFromParamsObject(citiesparams,
//								RestClient.RETURN_JSON_STRING);
//				JSONArray rootarray = new JSONArray(jsonStringForCities);
//				for (int i = 0; i < rootarray.length(); i++) {
//					JSONObject obj = rootarray.getJSONObject(i);
//					String name = obj.getString("name");
//					name = name.replace(" ", "");
//					// System.out.print(name);
//					nameValue = nameValue + " " + name;
//					if (Verification_Checks.cities.contains(name)) {
//
//					} else {
//						flag = false;
//					}
//
//				}
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				TestCaseReporting.teststepreporting("Cities names are "
//						+ nameValue, "PASS", "Citiesname should contain "
//						+ nameValue, getTotalExecutionTime(starttime, endtime));
//			} else {
//				TestCaseReporting.teststepreporting("Cities names are "
//						+ nameValue, "FAIL", "Citiesname should contain "
//						+ nameValue, getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"get cities info should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//
//	}
//
//	@SuppressWarnings({ "static-access", "unused" })
//	private static void getStoresByRegion() throws Exception {
//		String query = "select count(*) from store_store where city='Noida' OR city='Ghaziabad'";
//		boolean flag = true;
//		String nameValue = "";
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsStoreByRegion();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			int dbcount = Utility.Getdbdata.dbGetData(query);
//			JSONArray rootArray = new JSONArray(jsonString);
//			int size = rootArray.length();
//			endtime = System.currentTimeMillis();
//			if (dbcount == size) {
//				TestCaseReporting.teststepreporting("Store count  are " + size,
//						"PASS", "Store count should be  " + dbcount,
//						getTotalExecutionTime(starttime, endtime));
//
//			} else {
//				TestCaseReporting.teststepreporting("Store count  are " + size,
//						"PASS", "Store count should be  " + dbcount,
//						getTotalExecutionTime(starttime, endtime));
//			}
//
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"get stores info should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//	}
//
//	@SuppressWarnings({ "unused", "static-access" })
//	private static void getProducts() throws Exception {
//		try {
//			starttime = System.currentTimeMillis();
//			String categories[] = { "208" };
//
//			int pageNumbers = 1;
//			for (String cat : categories) {
//
//				String[] cats = { cat };
//
//				for (int page = 1; page <= pageNumbers; page++) {
//
//					String payload = APIPayload.jsonPayLoadForProductListing(
//							cats, "" + page);
//					// System.out.println("" + payload);
//
//					ParamObject params = APIParameters
//							.getParamsForProductListing(payload);
//
//					String jsonString = (String) RestClient
//							.getJSONFromParamsObject(params,
//									RestClient.RETURN_JSON_STRING);
//
//					ArrayList<String> ids = APIParameters
//							.getIDsArrayFromProductsJson(jsonString);
//
//					for (String id : ids) {
//						// String fileName ="E:\\sel-online\\data2.txt";
//						ParamObject obj = APIParameters
//								.getParamsForProductId(id);
//						int responseCode = (int) RestClient
//								.getJSONFromParamsObject(params,
//										RestClient.RETURN_RESPONSE_CODE);
//						System.out.println("Response Code for ProductID = "
//								+ id + "  ---- is  ---->>> " + responseCode);
//						RestMain.printToFile(id, responseCode);
//
//					}
//				}
//			}
//			endtime = System.currentTimeMillis();
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"get products info should be verified",
//					getTotalExecutionTime(starttime, endtime));
//			throw (e);
//		}
//
//	}
//
//	@SuppressWarnings("static-access")
//	private static String createNewStore() throws Exception {
//		String store_id = "", slug = null;
//		try {
//			starttime = System.currentTimeMillis();
//			String[] phn_nos = { "1124566776" };
//			String[] contPer = { "Ram", "Shyam", "Laxman" };
//			String[] wrkdays = { "mo", "tu" };
//			String payload = APIPayload.jsonPayLoadForStoreCreation(phn_nos,
//					contPer, wrkdays);
//			ParamObject params = APIParameters
//					.getParamsForCreateNewStoreAPI(payload);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("createNewStore" + jsonString);
//
//			endtime = System.currentTimeMillis();
//			store_id = getValueFromParser(jsonString, "store_id");
//			slug = getValueFromParser(jsonString, "slug");
//			if (!store_id.trim().equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"New Store with store id <b><i>" + store_id.trim()
//								+ "</i></b> created successfully", "PASS",
//						"New store should be created",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"New Store creation failed", "FAIL",
//						"New store should be created",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting(
//					"Exception occured in new store creation", "FAIL",
//					"New store should be created",
//					getTotalExecutionTime(starttime, endtime));
//
//		}
//		return store_id + "##" + slug;
//
//	}
//
//	@SuppressWarnings("static-access")
//	public static void getStoreBySlugAPI(String store_id) throws Exception {
//
//		try {
//			// testCaseReporting.header();
//			starttime = System.currentTimeMillis();
//
//			String[] str = store_id.split("##");
//			ParamObject params = APIParameters
//					.getParamsForStoreBySlugAPI(str[1].trim());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("getStoreBySlugAPI" + jsonString);
//
//			String slug = getValueFromParser(jsonString, "slug");
//			System.out.println(slug);
//			System.out.println(str[1]);
//			endtime = System.currentTimeMillis();
//			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
//				testCaseReporting.teststepreporting("<b><i>" + slug
//						+ " </i></b>Store verified by slug Details<b><i>"
//						+ str[1].trim() + "</i></b>", "PASS",
//						"Store Details should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting
//						.teststepreporting(
//								"<b><i>"
//										+ slug
//										+ " </i></b>Store slug Details not verified with <b><i>"
//										+ str[1].trim() + "</i></b>", "FAIL",
//								"Store Details should be verified",
//								getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store details should be verified",
//					getTotalExecutionTime(starttime, endtime));
//		}
//		// finally {
//		// testCaseReporting.footer();
//		// }
//
//	}
//
//	@SuppressWarnings("static-access")
//	public static void getStoreByStoreIdAPI(String store_id) throws Exception {
//
//		try {
//			// testCaseReporting.header();
//			starttime = System.currentTimeMillis();
//			String[] str = store_id.split("##");
//			ParamObject params = APIParameters.getParamsForStoreAPI(str[0]
//					.trim());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("getStoreByStoreIdAPI" + jsonString);
//
//			String slug = getValueFromParser(jsonString, "slug");
//			endtime = System.currentTimeMillis();
//			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug
//								+ " </i></b>Store verified by store id<b><i>"
//								+ str[1].trim() + "</i></b>", "PASS",
//						"Store Details should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting
//						.teststepreporting(
//								"<b><i>"
//										+ slug
//										+ " </i></b>Store slug Details not verified with <b><i>"
//										+ str[1].trim() + "</i></b>", "FAIL",
//								"Store Details should be verified",
//								getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store details should be verified",
//					getTotalExecutionTime(starttime, endtime));
//		}
//		// finally {
//		// testCaseReporting.footer();
//		// }
//
//	}
//
//	@SuppressWarnings("static-access")
//	private static void deleteStore(String slug) throws Exception {
//		try {
//			// testCaseReporting.header();
//			starttime = System.currentTimeMillis();
//			String[] str = slug.split("##");
//			ParamObject params = APIParameters
//					.getParamsForDeleteStoreAPI(str[1].trim());
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("deleteStore" + jsonString);
//
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//			if (responseCode == 204) {
//				testCaseReporting.teststepreporting(
//						"Store deleted successfully", "PASS",
//						"Store should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Store deletion failed",
//						"FAIL", "Store should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting(
//					"Exception occured in store deletion", "FAIL",
//					"Store should be deleted",
//					getTotalExecutionTime(starttime, endtime));
//		}
//		// }finally {
//		// testCaseReporting.footer();
//		// }
//
//	}
//
//	@SuppressWarnings("static-access")
//	public static void getServicesAPI() throws Exception {
//		String name = "", serviceType = "", out = "";
//		boolean flag = false;
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForGetServices();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("services");
//
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				name = getValueFromParser(json_obj.toString(), "name");
//				serviceType = getValueFromParser(json_obj.toString(),
//						"serviceType");
//				out = out + name + "_" + serviceType + "##";
//				if (Verification_Checks.SERVICETYPE.contains(name + "_"
//						+ serviceType)) {
//					flag = true;
//
//				} else {
//					flag = false;
//					break;
//				}
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting
//						.teststepreporting(
//								"<b><i>"
//										+ Verification_Checks.SERVICETYPE
//										+ " </i></b>Get services API verified successfully with <b><i>"
//										+ out + "</i></b>",
//								"PASS",
//								"Get services API should be verified successfully",
//								getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"Get services API noy verified", "FAIL",
//						"Get services API should be verified successfully",
//						getTotalExecutionTime(starttime, endtime));
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Get services API should be verified successfully",
//					getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	private static void printToFile(String id, int responseCode)
//			throws IOException {
//		String fileName = "E:\\sel-online\\ResponseCode.txt";
//
//		FileWriter fr = new FileWriter(fileName, true);
//		BufferedWriter br = new BufferedWriter(fr);
//		if (responseCode == 200)
//			br.write("Response Code for ProductID = " + id
//					+ "  ---- is  ---->>> " + responseCode + "\r\n");
//		else if (responseCode == 502)
//			br.write("Response Code for ProductID = " + id
//					+ "  ---- is  ---->>> " + responseCode + "\r\n");
//		else if (responseCode == 501)
//			br.write("Response Code for ProductID = " + id
//					+ "  ---- is  ---->>> " + responseCode + "\r\n");
//		br.close();
//
//	}
//
//	@SuppressWarnings("static-access")
//	public static void getCategoriesAPI() throws Exception {
//		String parentCat = "", parentName = "", childCat = "", childname = "", parent = "", child = "", parentCategories = "";
//		boolean flag = false;
//		try {
//			// testCaseReporting.header();
//			starttime = System.currentTimeMillis();
//
//			ParamObject params = APIParameters.getParamsForGetCategoriesAPI();
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			String data = getValueFromParser_WithBrace(jsonString, "data");
//			JSONObject serv = new JSONObject(data);
//
//			JSONArray st = serv.getJSONArray("categories");
//			for (int i = 0; i < st.length() - 1; i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				parentCat = getValueFromParser(json_obj.toString(), "cat_id");
//				parentName = getValueFromParser(json_obj.toString(), "name");
//
//				JSONArray ch = json_obj.getJSONArray("children");
//				for (int j = 0; j < ch.length(); j++) {
//					JSONObject json_ChildObj = ch.getJSONObject(i);
//					childCat = getValueFromParser(json_ChildObj.toString(),
//							"cat_id");
//					childname = getValueFromParser(json_ChildObj.toString(),
//							"name");
//					child = child + " " + childname + "(" + childCat + ")";
//					break;
//				}
//				parent = parentName + "(" + parentCat + ")";
//				if (Verification_Checks.CATEGORIES.contains(child.trim())
//						&& Verification_Checks.CATEGORIES.contains(parent
//								.trim())) {
//					flag = true;
//				} else {
//					flag = false;
//					break;
//				}
//				parentCategories = parentCategories + " " + parent + " "
//						+ child;
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting
//						.teststepreporting(
//								"<b><i>"
//										+ parentCategories
//										+ " </i></b> Following product categories are verified",
//								"PASS",
//								"Product categories should be verified",
//								getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>" + parentCategories
//						+ " </i></b> Product categories not  verified", "FAIL",
//						"Product categories should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Product categories should be verified",
//					getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	@SuppressWarnings("static-access")
//	public static String createUser(String usrName, String password,
//			String store_id) throws Exception {
//		String usrID = "";
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForCreateUser(usrName,
//					password, store_id);
//
//			ParamObject params = APIParameters.getParamsForCreateUser(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			usrID = getValueFromParser(jsonString, "user_id").trim();
//			endtime = System.currentTimeMillis();
//			if (getValueFromParser(jsonString, "status").trim()
//					.equalsIgnoreCase("true")
//					&& usrID.equalsIgnoreCase(usrName)) {
//				testCaseReporting.teststepreporting("<b><i>"
//						+ getValueFromParser(jsonString, "user_id")
//						+ " </i></b>User created and verified with <b><i>"
//						+ usrName + "</i></b>", "PASS",
//						"User Should be created",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>"
//						+ getValueFromParser(jsonString, "user_id")
//						+ " </i></b>User not verified with <b><i>" + usrName
//						+ "</i></b>", "FAIL", "User Should be created",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"User Should be created",
//					getTotalExecutionTime(starttime, endtime));
//			throw new Exception(e.toString());
//		}
//		return usrID;
//
//	}
//
//	@SuppressWarnings({ "static-access", "static-access" })
//	private static void deleteUser(String userName) throws Exception {
//		try {
//			// testCaseReporting.header();
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForDeleteUser(userName);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//			if (responseCode == 204) {
//				testCaseReporting.teststepreporting("<b><i>" + userName
//						+ " </i></b>User deleted successfully", "PASS",
//						"User should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>" + userName
//						+ " </i></b>User not deleted", "FAIL",
//						"User should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting(
//					"Exception occured in store deletion", "FAIL",
//					"User should be deleted",
//					getTotalExecutionTime(starttime, endtime));
//		}
//		// }finally {
//		// testCaseReporting.footer();
//		// }
//
//	}
//
//	@SuppressWarnings("static-access")
//	private static void getStoreAgreement() {
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters
//					.getParamsForGetStoreAggreement("202929");
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(
//					params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			endtime = System.currentTimeMillis();
//
//			if (!jsonString.equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"Store aggreement Info recieved", "PASS",
//						"Store aggreement Info should be recieved",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"Store aggreement Info not recieved", "FAIL",
//						"Store aggreement Info should be recieved",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : "
//					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store aggreement Info should be recieved",
//					getTotalExecutionTime(starttime, endtime));
//		}
//	}
//	
//	@SuppressWarnings("static-access")
//	private static void getStore(String store_id) {
//        try {
//                starttime = System.currentTimeMillis();
//                ParamObject params = APIParameters.getParamsForStores(store_id);
//
//                String jsonString = (String) RestClient.getJSONFromParamsObject(
//                                params, RestClient.RETURN_JSON_STRING);
//                System.out.println(jsonString);
//                String storid = getValueFromParser(jsonString, "store_id");
//                endtime = System.currentTimeMillis();
//
//                if (storid.equals(store_id)) {
//                        testCaseReporting.teststepreporting("Store Info recieved",
//                                        "PASS", "Store Info should be recieved",
//                                        getTotalExecutionTime(starttime, endtime));
//                } else {
//                        testCaseReporting.teststepreporting("Store Info not recieved",
//                                        "FAIL", "Store Info should be recieved",
//                                        getTotalExecutionTime(starttime, endtime));
//                }
//        } catch (Exception e) {
//                endtime = System.currentTimeMillis();
//                testCaseReporting.teststepreporting("Exception occured : "
//                                + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//                                "Store aggreement Info should be recieved",
//                                getTotalExecutionTime(starttime, endtime));
//        }
//}
//
//	// private static String jsonPayLoadForProductListing(String[] cat,
//	// String pageNumber) throws JSONException {
//	// JSONObject root = new JSONObject();// {}
//	// JSONArray catArray = new JSONArray();// []
//	//
//	// for (String s : cat) {
//	// catArray.put(s);// ["230"] // ["230","231"]
//	// }
//	// root.put("cat", catArray);// {"cat":["230"]}
//	// root.put("page", pageNumber); // {"cat":["230"],"page":"2"}
//	// System.out.println("Payload String is :" + root);
//	// return root.toString();
//	// }
//	//
//	// private static ParamObject getParamsForProductListing(String payLoad) {
//	// ParamObject obj = new ParamObject();
//	// try {
//	// TimeUnit.MILLISECONDS.sleep(100);
//	// } catch (InterruptedException e) {
//	//
//	// e.printStackTrace();
//	// }
//	// String url = "http://45.56.112.85/products/listing/";
//	// obj.setUrl(url);
//	// obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
//	// obj.addHeader("Region-Id", "2");
//	// obj.setPayload(payLoad);
//	// obj.setMethodType("POST");
//	// return obj;
//	// }
//	//
//	// private static ArrayList<String> getIDsArrayFromProductsJson(String json)
//	// throws Exception {
//	// JSONObject rootobject = new JSONObject(json);
//	// JSONArray products = rootobject.getJSONArray("products");
//	// ArrayList<String> ids = new ArrayList<>();
//	// for (int i = 0; i < products.length(); i++) {
//	// JSONObject item = products.getJSONObject(i);
//	// String id = item.getString("id");
//	// ids.add(id);
//	// }
//	// return ids;
//	// }
//	//
//	// private static ParamObject getParamsForProductId(String id) {
//	// String url = "http://45.56.112.85/product/" + id + "/";
//	// //System.out.println(url);
//	// ParamObject obj = new ParamObject();
//	// obj.setUrl(url);
//	// obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
//	// obj.addHeader("Region-Id", "1");
//	// obj.setMethodType("GET");
//	// return obj;
//	//
//	// }
//	// @SuppressWarnings({ "static-access", "unused" })
//	// private static void getStoresInfo() throws IOException, JSONException {
//	// try {
//	// starttime = System.currentTimeMillis();
//	// boolean flag = true;
//	// ParamObject params = getParamsMyStores();
//	// String jsonString = (String) RestClient.getJSONFromParamsObject(
//	// params, RestClient.RETURN_JSON_STRING);
//	// JSONArray arr = new JSONArray(jsonString);
//	// for (int i = 0; i < arr.length(); i++) {
//	// JSONObject jso = arr.getJSONObject(i);
//	// String name = jso.getString("name");
//	// String store_id = jso.getString("store_id");
//	// endtime = System.currentTimeMillis();
//	// if (name.equalsIgnoreCase(Verification_Checks.name)
//	// && store_id
//	// .equalsIgnoreCase(Verification_Checks.StoreID)) {
//	// testCaseReporting.teststepreporting("Store name is:" + name
//	// + "Store id is :" + store_id, "PASS",
//	// "Sanchar World with store id 83523",
//	// getTotalExecutionTime(starttime, endtime));
//	//
//	// } else {
//	// testCaseReporting.teststepreporting("Store name is:" + name
//	// + "Store id is :" + store_id, "Fail",
//	// "Sanchar World with store id 83523",
//	// getTotalExecutionTime(starttime, endtime));
//	//
//	// }
//	//
//	// }
//	//
//	// } catch (Exception e) {
//	// endtime = System.currentTimeMillis();
//	// testCaseReporting.teststepreporting("Exception occured : "
//	// + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//	// "get stores info should be verified",
//	// getTotalExecutionTime(starttime, endtime));
//	// throw (e);
//	//
//	// }
//	// }
//
//	public static ParamObject getParamsMyStores() {
//		String url = "http://139.162.14.155/mystores/";
//		// System.out.println(url);
//		ParamObject obj = new ParamObject();
//		obj.setUrl(url);
//		obj.addHeader("Authorization", "Token a7b978cdd05b43afa1ba39720f39638f");
//		obj.setMethodType("GET");
//		return obj;
//
//	}
//
//	public static void main(String[] args) throws IOException {
//		String Store_id = "203313";
//
//		try {
//
//			// suitreporting = new SuiteReporting("Suite");
//			testCaseReporting.header();
//		//	getStoresInfo();
//			//getProducts();
//
//			storeLoginAPI();
//			getListOfStates();
//			getListOfCities();
//			//verifyStoreAPI();
//			getStoresByRegion();
////slug is concatenation of Store id + ## + slug
//		   String slug = createNewStore();
////Above concatenated store id and slug is splited internally in below method to get slug
//			getStoreBySlugAPI(slug);
////Above concatenated store id and slug is splited internally in below method to get store id
//			getStoreByStoreIdAPI(slug);
////Above concatenated store id and slug is splited internally in below method to get slug
//			deleteStore(slug);
//			getServicesAPI();
//			getCategoriesAPI();
//			String userName = createUser("User_" + Long.toHexString(System.currentTimeMillis()),"123", "");
//			deleteUser(userName);
//			getStoreAgreement();
//			getStore(Store_id);
//
//			// suitreporting.consolidateResultFooter();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			testCaseReporting.footer();
//		}
//	}
//
//	private static String getTotalExecutionTime(long starttime, long endtime) {
//
//		long diff = endtime - starttime;
//		long diffmilliSeconds = diff % 1000;
//		long diffSeconds = diff / 1000 % 60;
//		// long diffMinutes = diff / (60 * 1000) % 60;
//		// long diffHours = diff / (60 * 60 * 1000) % 24;
//		// long diffDays = diff / (24 * 60 * 60 * 1000);
//
//		return (diffSeconds + ":" + diffmilliSeconds);
//
//	}
//
//	private static String getValueFromParser(String str, String keyValue)
//			throws Exception {
//		String value = "";
//		Object temp;
//		try {
//			JsonParser jsonParser = new JsonParser();
//			JsonElement element = jsonParser.parse(str.toString());
//			JsonObject jsonObject = element.getAsJsonObject();
//			try {
//				temp = jsonObject.get("status");
//				value = temp.toString();
//				if (value.contains("Failed")) {
//					temp = jsonObject.get("msg");
//					value = temp.toString();
//					throw new Exception(value);
//				}
//			} catch (Exception e) {
//
//			}
//
//			temp = jsonObject.get(keyValue);
//			value = temp.toString().replaceAll("\"", "");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new Exception(value);
//		}
//		return value;
//
//	}
//
//	private static String getValueFromParser_WithBrace(String str,
//			String keyValue) throws Exception {
//		String value = "";
//		Object temp;
//		try {
//			JsonParser jsonParser = new JsonParser();
//			JsonElement element = jsonParser.parse(str.toString());
//			JsonObject jsonObject = element.getAsJsonObject();
//			try {
//				temp = jsonObject.get("status");
//				value = temp.toString();
//				if (value.contains("Failed")) {
//					temp = jsonObject.get("msg");
//					value = temp.toString();
//					throw new Exception(value);
//				}
//			} catch (Exception e) {
//
//			}
//
//			temp = jsonObject.get(keyValue);
//			// value = temp.toString().replaceAll("\"", "");
//			value = temp.toString();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new Exception(value);
//		}
//		return value;
//
//	}
//
//	//
//	// // String url = "http://45.56.112.85/products/listing/";
//	// // String json = RestClient.getJSONFromUrl(url);
//	// // // System.out.println(json);
//	// // try {
//	// // JSONObject rootobject = new JSONObject(json);
//	// // JSONArray products = rootobject.getJSONArray("products");
//	// // for (int i = 0; i < products.length(); i++) {
//	// // JSONObject item = products.getJSONObject(i);
//	// // String name = item.getString("id");
//	// // System.out.println(name);
//	// // int response = RestClient
//	// // .GETOpenHttpConnection("http://45.56.112.85/product/"
//	// // + name + "/");
//	// // System.out.println("response code of product id " + name + "\t"
//	// // + "is" + "\t" + response);
//	// // }
//	// //
//	// // // Integer status = rootObject.getInt("status");
//	// // } catch (JSONException e) {
//	// // // TODO Auto-generated catch block
//	// // e.printStackTrace();
//	// //
//	// // }
//	// }
//
//	// public static void getproduct(String url) throws IOException {
//	// String json = RestClient.getJSONFromUrl(url);
//	// // System.out.println(json);
//	// try {
//	// JSONObject rootobject = new JSONObject(json);
//	// JSONArray products = rootobject.getJSONArray("products");
//	// for (int i = 0; i < products.length(); i++) {
//	// JSONObject item = products.getJSONObject(i);
//	// String name = item.getString("id");
//	// System.out.println(name);
//	// int response = RestClient
//	// .GETOpenHttpConnection("http://45.56.112.85/product/"
//	// + name + "/");
//	// System.out.println("response code of product id " + name + "\t"
//	// + "is" + "\t" + response);
//	// }
//	//
//	// // Integer status = rootObject.getInt("status");
//	// } catch (JSONException e) {
//	// // TODO Auto-generated catch block
//	// e.printStackTrace();
//	//
//	// }
//
//	// }
//
//	// int response = RestClient
//	// .GETOpenHttpConnection("http://45.56.112.85/product/9301966/");
//	// System.out.println(response);
//
//}
