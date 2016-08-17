//package StoreAPI;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import Parameters.APIParameters;
//import Payload.APIPayload;
//import Utility.Getdbdata;
//import VerificationChecks.Verification_Checks;
//import testCaseReporting.SuiteReporting;
//import testCaseReporting.TestCaseReporting;
//
//public class CopyOfRestMain {
//	static SuiteReporting suitreporting = new SuiteReporting("APIAutomationSuite");
//	static TestCaseReporting testCaseReporting = new TestCaseReporting("Test");
//
//	static long starttime;
//	static long endtime;
//
//	// public RestMain() {
//	// // TODO Auto-generated constructor stub
//	// DriverSession.setLastExecutionReportingInstance(testCaseReporting);
//	// }
//
//	private static void getStoresInfo() throws IOException {
//		ParamObject params = APIParameters.getParamsMyStores();
//		String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//		System.out.println(jsonString);
//		testCaseReporting.teststepreporting("Store info fetched successfully", "PASS",
//				"Store info should be Fetched successfully.", "t");
//
//	}
//
//	public static String trimDoubleQuotes(String text) {
//
//		text = text.replace("\"", "");
//		text = text.replace('"', '\"');
//		int textLength = text.length();
//		return text.substring(1, textLength - 1);
//		//
//
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
//	private static void getProducts() throws Exception {
//		String categories[] = { "208" };
//
//		int pageNumbers = 1;
//		for (String cat : categories) {
//
//			String[] cats = { cat };
//
//			for (int page = 1; page <= pageNumbers; page++) {
//
//				String payload = APIPayload.jsonPayLoadForProductListing(cats, "" + page);
//				// System.out.println("" + payload);
//
//				ParamObject params = APIParameters.getParamsForProductListing(payload);
//
//				String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//				System.out.println("json string : " + jsonString);
//				ArrayList<String> ids = getIDsArrayFromProductsJson(jsonString);
//
//				for (String id : ids) {
//					// String fileName ="E:\\sel-online\\data2.txt";
//					ParamObject obj = getParamsForProductId(id);
//					int responseCode = (int) RestClient.getJSONFromParamsObject(params,
//							RestClient.RETURN_RESPONSE_CODE);
//					System.out.println("Response Code for ProductID = " + id + "  ---- is  ---->>> " + responseCode);
//					CopyOfRestMain.printToFile(id, responseCode);
//
//				}
//			}
//		}
//
//	}
//
//	private static void getLoginDetails() throws Exception {
//		starttime = System.currentTimeMillis();
//		String payload = APIPayload.jsonPayLoadForLoginAPI("new-gen-retail", "xxq");
//
//		System.out.println("" + payload);
//
//		ParamObject params = APIParameters.getParamsForLoginAPI(payload);
//		String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//		//
//		System.out.println("Json string : " + jsonString);
//		// ArrayList<String> ids = getIDsArrayFromProductsJson(jsonString);
//		// endtime = System.currentTimeMillis();
//		// testCaseReporting.teststepreporting("Login details fetched
//		// successfully", "PASS",
//		// "Login details should be Fetched successfully.",
//		// getTotalExecutionTime(starttime, endtime));
//		// for (String id : ids) {
//		// // String fileName ="E:\\sel-online\\data2.txt";
//		// ParamObject obj = getParamsForProductId(id);
//		// int responseCode = (int) RestClient.getJSONFromParamsObject(params,
//		// RestClient.RETURN_RESPONSE_CODE);
//		// System.out.println("Response Code for ProductID = " + id + " ---- is
//		// ---->>> " + responseCode);
//		// RestMain.printToFile(id, responseCode);
//		//
//		// }
//
//	}
//
//	//Order status API
//	public static void getOrderStatus(String order_id, String store_Id, String status) throws Exception {
//
//		try {
//			testCaseReporting.header("Order Status");
//			RestClient.restClient_Flag = false;
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForOrderStatusAPI(store_Id, status);
//
//			ParamObject params = APIParameters.getParamsForOrderStatusAPI(order_id, payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("Json string : " + jsonString);
//
//			System.out.println(getValueFromParser(jsonString, "message"));
//			String order_Status = getValueFromParser(jsonString, "message");
//			endtime = System.currentTimeMillis();
//			if (order_Status.trim().equalsIgnoreCase(Verification_Checks.ORDER_STATUS)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + order_Status + " </i></b>Order Status verified with <b><i>"
//								+ Verification_Checks.ORDER_STATUS + "</i></b>",
//						"PASS", "Order status should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + order_Status + " </i></b>Order Status not verified with <b><i>"
//								+ Verification_Checks.ORDER_STATUS + "</i></b>",
//						"FAIL", "Order status should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Order status should be verified", getTotalExecutionTime(starttime, endtime));
//			throw new Exception(e.toString());
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get store info by store slug id
//	public static String getStoreBySlugAPI(String store_id) throws Exception {
//		String jsonString = null;
//		try {
//			testCaseReporting.header("Get Store By Slug");
//			starttime = System.currentTimeMillis();
//
//			String[] str = store_id.split("##");
//			ParamObject params = APIParameters.getParamsForStoreBySlugAPI(str[1].trim());
//
//			jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("getStoreBySlugAPI" + jsonString);
//
//			String slug = getValueFromParser(jsonString, "slug");
//			System.out.println(slug);
//			System.out.println(str[1]);
//			endtime = System.currentTimeMillis();
//			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug + " </i></b>Store verified by slug Details<b><i>" + str[1].trim() + "</i></b>",
//						"PASS", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug + " </i></b>Store slug Details not verified with <b><i>" + str[1].trim()
//								+ "</i></b>",
//						"FAIL", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store details should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//		return jsonString;
//
//	}
//
//	//Get categories API
//	public static void getCategoriesAPI() throws Exception {
//		String parentCat = "", parentName = "", childCat = "", childname = "", child = "";
//		try {
//			testCaseReporting.header("Get Categories");
//			starttime = System.currentTimeMillis();
//			
//			Getdbdata.connectDB();
//			ParamObject params = APIParameters.getParamsForGetCategoriesAPI();
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			String data = getValueFromParser_WithBrace(jsonString, "data");
//			JSONObject serv = new JSONObject(data);
//
//			JSONArray st = serv.getJSONArray("categories");
//			for (int i = 0; i < st.length() - 1; i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				parentCat = getValueFromParser(json_obj.toString(), "cat_id");
//				parentName = getValueFromParser(json_obj.toString(), "name");
//				String count=Getdbdata.executeQuery("SELECT count(*) FROM merchant_test.store_category where name='"+parentName+"' and cat_id='"+parentCat+"'");
//				endtime = System.currentTimeMillis();
//				if (Integer.parseInt(count.trim())>0) {
//					testCaseReporting.teststepreporting(
//							"Category name <b><i>" + parentName + " </i></b> and category id <b><i>" + parentCat + " </i></b> are verified from DB", "PASS",
//							"Product name and category id should be verified", getTotalExecutionTime(starttime, endtime));
//				} else {
//					testCaseReporting.teststepreporting(
//							"Category name <b><i>" + parentName + " </i></b> and category id <b><i>" + parentCat + " </i></b> are not verified from DB", "PASS",
//							"Product name and category id should be verified", getTotalExecutionTime(starttime, endtime));
//				}
//				
//				JSONArray ch = json_obj.getJSONArray("children");
//				for (int j = 0; j < ch.length(); j++) {
//					JSONObject json_ChildObj = ch.getJSONObject(i);
//					childCat = getValueFromParser(json_ChildObj.toString(), "cat_id");
//					childname = getValueFromParser(json_ChildObj.toString(), "name");
//					child = child + " " + childname + "(" + childCat + ")";
//					String cCount=Getdbdata.executeQuery("SELECT count(*) FROM merchant_test.store_category where name='"+childname+"' and cat_id='"+childCat+"'");
//					if (Integer.parseInt(cCount.trim())>0) {
//						testCaseReporting.teststepreporting(
//								"Category name <b><i>" + childname + " </i></b> and category id <b><i>" + childCat + " </i></b> are verified from DB", "PASS",
//								"Product name and category id should be verified", getTotalExecutionTime(starttime, endtime));
//					} else {
//						testCaseReporting.teststepreporting(
//								"Category name <b><i>" + childname + " </i></b> and category id <b><i>" + childCat + " </i></b> are not verified from DB", "PASS",
//								"Product name and category id should be verified", getTotalExecutionTime(starttime, endtime));
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Product categories should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			Getdbdata.closeDBConnection();
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get store info by store id
//	public static void getStoreByStoreIdAPI(String store_id) throws Exception {
//
//		try {
//			testCaseReporting.header("Get Store By Store Id");
//			starttime = System.currentTimeMillis();
//			String[] str = store_id.split("##");
//			ParamObject params = APIParameters.getParamsForStoreAPI(str[0].trim());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("getStoreByStoreIdAPI" + jsonString);
//
//			String slug = getValueFromParser(jsonString, "slug");
//			endtime = System.currentTimeMillis();
//			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug + " </i></b>Store verified by store id <b><i>" + str[1].trim() + "</i></b>",
//						"PASS", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug + " </i></b>Store slug Details not verified with <b><i>" + str[1].trim()
//								+ "</i></b>",
//						"FAIL", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store details should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get order details by order id, store id and order status
//	public static String getOrderDetailsAPI(String order_id, String store_id, String status) throws Exception {
//		String order_items, order_List = "";
//		try {
//			testCaseReporting.header("Order Details");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForOrderDetailAPI(order_id, store_id, status);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray ch = serv.getJSONArray("items");
//			for (int j = 0; j < ch.length(); j++) {
//				JSONObject json_ChildObj = ch.getJSONObject(j);
//				order_items = getValueFromParser(json_ChildObj.toString(), "id").trim();
//				order_List = order_List + " " + order_items;
//			}
//
//			endtime = System.currentTimeMillis();
//			if (!order_List.equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting("<b><i>" + order_List + " </i></b> Order details verified", "PASS",
//						"Order details should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>" + order_List + "</i></b> Order details not verified",
//						"FAIL", "Order details should be Fetched successfully.",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
//					"Order details should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//		return order_List;
//
//	}
//
//	//Get warrant enabled category
//	public static void getWarrantyEnabledCategoriesAPI(String price, String duration) throws Exception {
//
//		boolean flag = true;
//		try {
//			testCaseReporting.header("Warranty Enabled Categories");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForWarrantyEnabledCategoriesAPI();
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			String categories = getValueFromParser(jsonString, "categories");
//			System.out.println(jsonString);
//			JSONArray st = new JSONArray(categories);
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				String cat = getValueFromParser(json_obj.toString(), "cat").trim();
//				String cat_id = getValueFromParser(json_obj.toString(), "cat_id").trim().replace("[", "");
//				cat_id = cat_id.replace("]", "");
//
//				getWarrentyAmount(cat_id, price, duration);
//
//				System.out.println(cat_id);
//				cat = cat_id + ":" + cat;
//
//				if (Verification_Checks.CATEGORIES_CODE.contains(cat)) {
//
//				} else {
//					flag = false;
//					break;
//				}
//
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + categories + " </i></b>Warranty enabled categories verified with <b><i>"
//								+ Verification_Checks.CATEGORIES_CODE + "</i></b>",
//						"PASS", "Warranty enabled categories should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + categories + "</i></b> Warranty enabled categories not verified with <b><i>"
//								+ Verification_Checks.CATEGORIES_CODE + "</i></b>",
//						"FAIL", "Warranty enabled categories should be Fetched successfully",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
//					"Warranty enabled categories should be Fetched successfully.",
//					getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Create a new store
//	public static String createNewStore() throws Exception {
//		String store_id = "", slug = null;
//		try {
//			testCaseReporting.header("Create Store");
//			starttime = System.currentTimeMillis();
//			String[] phn_nos = { "1124566776" };
//			String[] contPer = { "Ram", "Shyam", "Laxman" };
//			String[] wrkdays = { "mo", "tu" };
//			String payload = APIPayload.jsonPayLoadForStoreCreation(phn_nos, contPer, wrkdays);
//			ParamObject params = APIParameters.getParamsForCreateNewStoreAPI(payload);
//			System.out.println(params.toString());
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("createNewStore" + jsonString);
//
//			endtime = System.currentTimeMillis();
//			store_id = getValueFromParser(jsonString, "store_id");
//			slug = getValueFromParser(jsonString, "slug");
//			if (!store_id.trim().equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"New Store with store id <b><i>" + store_id.trim() + "</i></b> created successfully", "PASS",
//						"New store should be created", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("New Store creation failed", "FAIL", "New store should be created",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting("Exception occured in new store creation", "FAIL",
//					"New store should be created", getTotalExecutionTime(starttime, endtime));
//
//		} finally {
//			testCaseReporting.footer();
//		}
//		return store_id + "##" + slug;
//
//	}
//
//	//Delete store by store id
//	public static void deleteStore(String slug) throws Exception {
//		try {
//			testCaseReporting.header("Delete Store");
//			starttime = System.currentTimeMillis();
//			String[] str = slug.split("##");
//			ParamObject params = APIParameters.getParamsForDeleteStoreAPI(str[1].trim());
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("deleteStore" + jsonString);
//
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//			if (responseCode == 204) {
//				testCaseReporting.teststepreporting("Store deleted successfully", "PASS", "Store should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Store deletion failed", "FAIL", "Store should be deleted",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting("Exception occured in store deletion", "FAIL",
//					"Store should be deleted", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Order list API
//	public static void getOrderListAPI(String pageSize, String pageNo, String store_id, String status)
//			throws Exception {
//		String order_id, order_Item, total_Amount, order_List = "";
//		try {
//			testCaseReporting.header("Order Listing");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForOrderListAPI(pageSize, pageNo, store_id, status);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("results");
//			System.out.println("array length: " + st.length());
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				order_id = getValueFromParser(json_obj.toString(), "order_id");
//				order_Item = getValueFromParser(json_obj.toString(), "id");
//				total_Amount = getValueFromParser(json_obj.toString(), "total_amount");
//				order_List = order_List + " " + order_id + "##" + order_Item + "##" + total_Amount;
//			}
//			System.out.println("order_List " + order_List);
//			endtime = System.currentTimeMillis();
//			if (!order_List.equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting("<b><i>" + order_List + " </i></b> Order list verified", "PASS",
//						"Order List should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>" + order_List + "</i></b> Order List not verified", "FAIL",
//						"Order List should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
//					"Order List should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//	}
//
//	private static void printToFile(String id, int responseCode) throws IOException {
//		String fileName = "E:\\sel-online\\ResponseCode.txt";
//
//		FileWriter fr = new FileWriter(fileName, true);
//		BufferedWriter br = new BufferedWriter(fr);
//		if (responseCode == 200)
//			br.write("Response Code for ProductID = " + id + "  ---- is  ---->>> " + responseCode + "\r\n");
//		else if (responseCode == 502)
//			br.write("Response Code for ProductID = " + id + "  ---- is  ---->>> " + responseCode + "\r\n");
//		else if (responseCode == 501)
//			br.write("Response Code for ProductID = " + id + "  ---- is  ---->>> " + responseCode + "\r\n");
//		br.close();
//
//	}
//
//	private static String getValueFromParser(String str, String keyValue) throws Exception {
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
//	private static String getValueFromParser_WithBrace(String str, String keyValue) throws Exception {
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
//	private static ArrayList<String> getIDsArrayFromProductsJson(String json) throws Exception {
//
//		JSONObject rootobject = new JSONObject(json);
//		JSONArray products = rootobject.getJSONArray("products");
//		ArrayList<String> ids = new ArrayList<>();
//		for (int i = 0; i < products.length(); i++) {
//			JSONObject item = products.getJSONObject(i);
//			String id = item.getString("id");
//			ids.add(id);
//		}
//		return ids;
//	}
//
//	private static ArrayList<String> getIDsArrayFromProductsJsonNEW(String json) throws Exception {
//
//		JSONObject rootobject = new JSONObject(json);
//		JSONArray products = rootobject.getJSONArray("products");
//		ArrayList<String> ids = new ArrayList<>();
//		for (int i = 0; i < products.length(); i++) {
//			JSONObject item = products.getJSONObject(i);
//			String id = item.getString("id");
//			ids.add(id);
//		}
//		return ids;
//	}
//
//	private static ParamObject getParamsForProductId(String id) {
//		String url = "http://45.56.112.85/product/" + id + "/";
//		ParamObject obj = new ParamObject();
//		obj.setUrl(url);
//		obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
//		obj.addHeader("Region-Id", "1");
//		obj.setMethodType("GET");
//		return obj;
//
//	}
//
//	//Get all invoices
//	public static void getAllInvoices() throws Exception {
//
//		boolean flag = true;
//		try {
//			testCaseReporting.header("Get All Invoice");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsAllInvoices();
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("jsonString   "+jsonString);
//			JSONArray st = new JSONArray(getValueFromParser(jsonString, "results"));
//			System.out.println(st.length());
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//
//				getInvoiceDetails(getValueFromParser(json_obj.toString(), "invoice_id").trim(),
//						getValueFromParser(json_obj.toString(), "user_name"));
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting.teststepreporting("All invoices verified successfully", "PASS",
//						"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("All invoices not verified", "FAIL",
//						"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	private static void getInvoiceDetails(String invoice_ID, String user_Name) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsInvoiceDetails(invoice_ID);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			System.out.println(getValueFromParser(jsonString, "full_name"));
//			String userName = getValueFromParser(jsonString, "full_name");
//			endtime = System.currentTimeMillis();
//
//			if (userName.trim().equalsIgnoreCase(user_Name.trim())) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + userName + " </i></b>Invoice details verified with <b><i>" + user_Name
//								+ "</i></b> for invoice id" + invoice_ID,
//						"PASS", "Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + userName + " and " + user_Name + " </i></b>Invoice details not verified with <b><i>"
//								+ user_Name + "</i></b> for invoice id " + invoice_ID,
//						"FAIL", "Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	//Get Warrant amount API
//	public static void getWarrentyAmount(String cat, String price, String duration) throws Exception {
//
//		boolean flag = true;
//		String amount = "", durn = "";
//		try {
//			RestClient.restClient_Flag = false;
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForCalculateWarrantyAmount(cat, price, duration);
//			ParamObject params = APIParameters.getParamsForGetWarrantyAmount(payload);
//			System.out.println(params.getPayload());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//
//			JSONArray st = new JSONArray(jsonString);
//			System.out.println("array length : " + st.length());
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				amount = amount + " " + getValueFromParser(json_obj.toString(), "amount");
//
//				durn = durn + " " + getValueFromParser(json_obj.toString(), "duration");
//			}
//			endtime = System.currentTimeMillis();
//			if (!amount.trim().equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"Warranty amount generated with duration i.e., Amount <b><i>" + amount
//								+ " </i></b>with year duration <b><i>" + durn + " </i></b>respectedly",
//						"PASS", "Warranty amount should be generated with duration",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Unable to generate waranty amount", "FAIL",
//						"Warranty amount should be generated with duration", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Warranty amount should be generated with duration", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	//Generate shipment API
//	public static void getShipmentHandler(String type, String orderItems, String storeId) throws Exception {
//
//		String shipmentId = "";
//		try {
//			testCaseReporting.header("Generate Shipment");
//			RestClient.restClient_Flag = false;
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForShipmentHandler(type, orderItems, storeId);
//			ParamObject params = APIParameters.getParamsForShipmentHandler(payload);
//			System.out.println(params.getPayload());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//
//			shipmentId = getValueFromParser(jsonString, "shipment_id");
//			endtime = System.currentTimeMillis();
//			if (!shipmentId.trim().equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting("<b><i>" + shipmentId + " Shipment id fetched successfully", "PASS",
//						"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Unable to fetch shipment id", "FAIL",
//						"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Deliver order API
//	public static void getOrderDeliveryStatus(String shipmentId, String shipmentStatus, String storeId, String orderId)
//			throws Exception {
//
//		String summary = "";
//		try {
//			testCaseReporting.header("Order Delivery status");
//			RestClient.restClient_Flag = false;
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForOrderDeliveryStatus(shipmentId, shipmentStatus, storeId);
//			ParamObject params = APIParameters.getParamsForOrderDeliveryStatus(payload, orderId);
//			System.out.println(params.getPayload());
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//
//			summary = getValueFromParser(jsonString, "message");
//			endtime = System.currentTimeMillis();
//			if (summary.trim().equalsIgnoreCase(Verification_Checks.SUMMERY)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + summary.trim() + "</i></b> Order delivery status verified with <b><i>"
//								+ Verification_Checks.SUMMERY + "</i></b>",
//						"PASS", "Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Order delivery status not verified", "FAIL",
//						"Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Sell query API
//	public static void getSellQuery(String productId, String storeId, String sPrice) throws Exception {
//
//		String summary = "";
//		try {
//			testCaseReporting.header("Retailer App Sell Query");
//			RestClient.restClient_Flag = false;
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForSellQuery(productId, storeId, sPrice);
//			ParamObject params = APIParameters.getParamsForSellQuery(payload);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//
//			summary = getValueFromParser(jsonString, "message");
//			endtime = System.currentTimeMillis();
//			if (summary.trim().equalsIgnoreCase(Verification_Checks.SELL_QUERY_MESSAGE)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + summary.trim() + "</i></b> Sell query verified with <b><i>"
//								+ Verification_Checks.SELL_QUERY_MESSAGE + "</i></b>",
//						"PASS", "Sell Query should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Sell Query not verified", "FAIL", "Sell Query should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Sell Query should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	public static void generateWarrantyHelpDeskTicket() throws Exception {
//
//		try {
//			testCaseReporting.header("Generate Warranty Helpdesk Ticket");
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForGenerateWarrantyHelpDeskTicket();
//			ParamObject params = APIParameters.getParamsForGenerateWarrantyHelpDeskTicket(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//
//			if (responseCode == 201) {
//				testCaseReporting.teststepreporting("Warranty help desk ticket generated successfuly", "PASS",
//						"Warranty help desk ticket should be generated", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Warranty help desk ticket not generated", "FAIL",
//						"Warranty help desk ticket should be generated", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Warranty help desk ticket should be generated", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	private static void createNewInvoice(String prod_Name, String serial_No, String name, String email, String cat_id,
//			String cost, String purchase_date, String contact_no, String has_warrant, String duration,
//			String warranty_serial, String amount, String warranty_purchase_date) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForCreateNewInvoice(prod_Name, serial_No, name, email, cat_id, cost,
//					purchase_date, contact_no, has_warrant, duration, warranty_serial, amount, warranty_purchase_date);
//			ParamObject params = APIParameters.getParamsForCreateNewInvoice(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			String invoice_ID = getValueFromParser(jsonString, "invoice_id");
//			System.out.println("invoice ID : " + invoice_ID);
//			endtime = System.currentTimeMillis();
//
//			if (invoice_ID.trim().equalsIgnoreCase(Verification_Checks.INVOICE_ID)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + invoice_ID.trim()
//								+ " </i></b>New invoice id generated successfuly and verified with <b><i>"
//								+ Verification_Checks.INVOICE_ID,
//						"PASS", "New Invoice ID should be generated", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + invoice_ID.trim()
//								+ " </i></b>New invoice id either not generated successfuly or not verified with <b><i>"
//								+ Verification_Checks.INVOICE_ID,
//						"FAIL", "New Invoice ID should be generated", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"New Invoice ID should be generated", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	public static void verifyStore(String slug) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String[] str = slug.split("##");
//			String payload = APIPayload.jsonPayLoadForVerifyStore(str[1]);
//
//			ParamObject params = APIParameters.getParamsForVerifyStore(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			System.out.println("Json string : " + jsonString);
//
//			System.out.println(getValueFromParser(jsonString, "changed"));
//			String slug_Id = getValueFromParser(jsonString, "changed");
//			endtime = System.currentTimeMillis();
//			if (slug_Id.trim().contains(slug)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug_Id + " </i></b>Slug verified with <b><i>" + slug + "</i></b>", "PASS",
//						"Slug should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + slug_Id + " </i></b>Slug not verified with <b><i>" + slug + "</i></b>", "FAIL",
//						"Order status should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Slug should be verified", getTotalExecutionTime(starttime, endtime));
//			throw new Exception(e.toString());
//		}
//
//	}
//
//	//Create user API
//	public static String createUser(String usrName, String password, String store_id) throws Exception {
//		String usrID = "";
//		try {
//			testCaseReporting.header("Create User");
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForCreateUser(usrName, password, store_id);
//
//			ParamObject params = APIParameters.getParamsForCreateUser(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			usrID = getValueFromParser(jsonString, "user_id").trim();
//			endtime = System.currentTimeMillis();
//			if (getValueFromParser(jsonString, "status").trim().equalsIgnoreCase("true")
//					&& usrID.equalsIgnoreCase(usrName)) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + getValueFromParser(jsonString, "user_id")
//								+ " </i></b>User created and verified with <b><i>" + usrName + "</i></b>",
//						"PASS", "User Should be created", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + getValueFromParser(jsonString, "user_id") + " </i></b>User not verified with <b><i>"
//								+ usrName + "</i></b>",
//						"FAIL", "User Should be created", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"User Should be created", getTotalExecutionTime(starttime, endtime));
//			throw new Exception(e.toString());
//		} finally {
//			testCaseReporting.footer();
//		}
//		return usrID;
//
//	}
//
//	//Delete user API
//	public static void deleteUser(String userName) throws Exception {
//		try {
//			testCaseReporting.header("Delete User");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForDeleteUser(userName);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//			if (responseCode == 204) {
//				testCaseReporting.teststepreporting("<b><i>" + userName + " </i></b>User deleted successfully", "PASS",
//						"User should be deleted", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("<b><i>" + userName + " </i></b>User not deleted", "FAIL",
//						"User should be deleted", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			testCaseReporting.teststepreporting("Exception occured in store deletion", "FAIL", "User should be deleted",
//					getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	private static void uploadInvoiceImage(String imageURL, String invoice_ID) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForUploadInvoiceImage(imageURL);
//			ParamObject params = APIParameters.getParamsForUploadInvoiceImage(payload, invoice_ID);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//
//			if (responseCode == 204) {
//				testCaseReporting.teststepreporting(
//						"Invoice image uploaded successfuly for invoice id <b><i>" + invoice_ID + "</i></b>", "PASS",
//						"Invoice image should be uploaded", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"Invoice image not uploaded for invoice id <b><i>" + invoice_ID + "</i></b>", "FAIL",
//						"Invoice image should be uploaded", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Invoice image should be uploaded", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	private static void addCart(String prod_Id, String store_Id) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForAddCart(prod_Id, store_Id);
//			ParamObject params = APIParameters.getParamsForAddCart(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			int responseCode = RestClient.getResponseCode();
//			endtime = System.currentTimeMillis();
//
//			if (responseCode == 201) {
//				testCaseReporting.teststepreporting(
//						"Product added to cart successfully with product id <b><i>" + prod_Id
//								+ " </i></b> and store id <b><i>" + store_Id + "</i></b>",
//						"PASS", "Product should be added to cart", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Product not added to cart", "FAIL",
//						"Product should be added to cart", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Product should be added to cart", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	private static void getCart() throws Exception {
//		String prod_List = "";
//		try {
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForGetCart();
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONArray st = new JSONArray(getValueFromParser(jsonString, "items"));
//			System.out.println("array length: " + st.length());
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				System.out.println(json_obj.toString());
//				System.out.println(getValueFromParser(json_obj.toString(), "product_id"));
//				prod_List = prod_List + " " + getValueFromParser(json_obj.toString(), "product_id");
//
//			}
//
//			endtime = System.currentTimeMillis();
//
//			if (!prod_List.trim().equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"Get product id from cart successfully i.e., <b><i>" + prod_List + " </i></b>", "PASS",
//						"Product id should be fetch from cart", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("No product found", "FAIL", "Product id should be fetch from cart",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Product id should be fetch from cart", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	//Get services API
//	public static void getServicesAPI() throws Exception {
//		String name = "", serviceType = "", out = "";
//		boolean flag = false;
//		try {
//			testCaseReporting.header("Get Services");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForGetServices();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("services");
//
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				name = getValueFromParser(json_obj.toString(), "name");
//				serviceType = getValueFromParser(json_obj.toString(), "serviceType");
//				out = out + name + "_" + serviceType + "##";
//				if (Verification_Checks.SERVICETYPE.contains(name + "_" + serviceType)) {
//					flag = true;
//
//				} else {
//					flag = false;
//					break;
//				}
//			}
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + Verification_Checks.SERVICETYPE
//								+ " </i></b>Get services API verified successfully with <b><i>" + out + "</i></b>",
//						"PASS", "Get services API should be verified successfully",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Get services API noy verified", "FAIL",
//						"Get services API should be verified successfully", getTotalExecutionTime(starttime, endtime));
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Get services API should be verified successfully", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	private static void sellQuery_Retail(String prod_Id, String prod_Title, String store_id, String cat_Id,
//			int base_Price, int msp_price, int sp_price, int price_exp_time) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForSellQuery_Retail(prod_Id, prod_Title, store_id, cat_Id,
//					base_Price, msp_price, sp_price, price_exp_time);
//			ParamObject params = APIParameters.getParamsForSellQuery_Retailer(payload, store_id);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("sellQuery_Retail : "+jsonString);
//
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Updated")) {
//				testCaseReporting.teststepreporting(
//						"Product price updated for product id and store id respectively <b><i>" + prod_Id + ","
//								+ store_id + "</i></b>",
//						"PASS", "Product price should be updated", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Product price not updated", "FAIL",
//						"Product price should be updated", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Product price should be updated", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	//Get product prices by store id
//	public static void getPricesByStore(String store_Id) throws Exception {
//		String product_ids = "";
//		boolean flag = true;
//		try {
//			testCaseReporting.header("Get Prices");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForGetPricesByStore(store_Id);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("results");
//			Utility.Getdbdata.connectDB();
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				String prod_Id = getValueFromParser(json_obj.toString(), "product_id");
//
//				String cat_Id = getValueFromParser(json_obj.toString(), "category");
//				String prod_Title = getValueFromParser(json_obj.toString(), "product_title");
//				String sp_price = getValueFromParser(json_obj.toString(), "sp_price");
//				sp_price = sp_price.replace(".00", "");
//				int sp = Integer.parseInt(sp_price) + 100;
//				sp_price = String.valueOf(sp);
//				product_ids = product_ids + " " + prod_Id;
//
//				String Category_id = Utility.Getdbdata.executeQuery("SELECT cat_id FROM merchant_test.store_category where id="+cat_Id);
//
//				sellQuery_Retail(prod_Id, prod_Title, store_Id, Category_id, 0, 0, sp, 720);
//				String sp_Price = Utility.Getdbdata
//						.executeQuery("SELECT sp_price FROM merchant_test.store_product_price where store_id="
//								+ store_Id + " and product_id=" + prod_Id + " and category_id=" + cat_Id);
//				if (sp_Price.trim().contains(sp_price.trim())) {
//
//				} else {
//					flag = false;
//					break;
//				}
//			}
//			
//			endtime = System.currentTimeMillis();
//			if (flag) {
//				testCaseReporting.teststepreporting("Prices verified", "PASS", "Prices should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Prices are not verified", "FAIL", "Prices should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Prices should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			Utility.Getdbdata.closeDBConnection();
//			testCaseReporting.footer();
//		}
//
//	}
//
//	public static String getPricesByStore_ReturnProductIds(String store_Id) throws Exception {
//		String product_ids = "";
//		try {
//			ParamObject params = APIParameters.getParamsForGetPricesByStore(store_Id);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("results");
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				String prod_Id = getValueFromParser(json_obj.toString(), "product_id");
//				product_ids = product_ids + " " + prod_Id;
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return product_ids;
//
//	}
//
//	//Purge price API by product id and store id
//	public static void purgePriceByStore(String prod_Id, String store_id) throws Exception {
//		boolean flag = true;
//		try {
//			testCaseReporting.header("Purge Price By Store");
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForPurgePriceByStore(prod_Id);
//			ParamObject params = APIParameters.getParamsForPurgePriceByStore(payload, store_id);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//
//			String product_ids = getPricesByStore_ReturnProductIds(store_id);
//			String[] prodList = product_ids.split(" ");
//			for (int i = 0; i < prodList.length; i++) {
//				if (prodList[i].equalsIgnoreCase(prod_Id)) {
//					flag = false;
//					break;
//				} else {
//					flag = true;
//
//				}
//			}
//
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Purged") && flag == true) {
//				testCaseReporting.teststepreporting(
//						"Price purged for product id and store id respectively <b><i>" + prod_Id + "," + store_id
//								+ "</i></b>",
//						"PASS", "Price should be purged", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Price not purged", "FAIL", "Price should be purged",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Price should be purged", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Purge all prices by store id
//	public static void purgeAllPriceByStore(String store_id) throws Exception {
//
//		try {
//			testCaseReporting.header("Purge All Price By Store");
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForPurgeAllPriceByStore();
//			ParamObject params = APIParameters.getParamsForPurgeAllPriceByStore(payload, store_id);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			String product_ids = getPricesByStore_ReturnProductIds(store_id);
//			System.out.println(product_ids);
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("All price purged")
//					&& product_ids.equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting("All Prices are purged for store id <b><i>" + store_id + "</i></b>",
//						"PASS", "All Prices should be purged", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("All Prices are not purged", "FAIL", "All Prices should be purged",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"All Prices should be purged", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get Price verification data info API
//	public static void getPriceVerficationData() throws Exception {
//		String priceDetails = "";
//		try {
//			testCaseReporting.header("Get Price Verification");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForGetPriceVerficationData();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println(jsonString);
//			JSONObject serv = new JSONObject(jsonString);
//			JSONArray st = serv.getJSONArray("results");
//			System.out.println("Array count " + st.length());
//			for (int i = 0; i < st.length(); i++) {
//				JSONObject json_obj = st.getJSONObject(i);
//				String instance_Id = getValueFromParser(json_obj.toString(), "id");
//				String store = getValueFromParser(json_obj.toString(), "store");
//				String product_Id = getValueFromParser(json_obj.toString(), "product_id");
//				priceDetails = priceDetails + " " + instance_Id + "##" + store + "##" + product_Id;
//				verifyPrice(store, instance_Id, true);
//			}
//			endtime = System.currentTimeMillis();
//			if (!priceDetails.equalsIgnoreCase("")) {
//				testCaseReporting.teststepreporting(
//						"<b><i>" + priceDetails + "</i></b> Price verification data fetched successfully", "PASS",
//						"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Price verification data not found", "FAIL",
//						"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	private static void verifyPrice(String store_id, String instance_id, Boolean sts) throws Exception {
//
//		try {
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForVerifyPrice(sts);
//			ParamObject params = APIParameters.getParamsForVerifyPrice(payload, instance_id);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("verify price " + jsonString);
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Verified")) {
//				testCaseReporting.teststepreporting("Price verified for store id <b><i>" + store_id + "</i></b>",
//						"PASS", "Price should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Price is not verified", "FAIL", "Price should be verified",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Price should be verified", getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//	//Change purge time by category id
//	public static void changePurgeTimeForLeafCategory(String cat_id, int purgeTime) throws Exception {
//
//		try {
//			testCaseReporting.header("Change Purge Time");
//			starttime = System.currentTimeMillis();
//			String payload = APIPayload.jsonPayLoadForChangePurgeTimeOfLeafCategory(purgeTime);
//			ParamObject params = APIParameters.getParamsForChangePurgeTimeOfLeafCategory(payload, cat_id);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("changePurgeTimeForLeafCategory " + jsonString);
//			Utility.Getdbdata.connectDB();
//			String prgTime = Utility.Getdbdata
//					.executeQuery(" SELECT purge_time FROM merchant_test.store_category where cat_id=" + cat_id);
//			Utility.Getdbdata.closeDBConnection();
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Done")
//					&& purgeTime == Integer.parseInt(prgTime.trim())) {
//				testCaseReporting.teststepreporting(
//						"Purge time<b><i> " + purgeTime + " </i></b>updated for leaf category <b><i>" + cat_id
//								+ "</i></b>",
//						"PASS", "Purge time should be updated for leaf category",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Purge time not updated for leaf category", "FAIL",
//						"Purge time should be updated for leaf category", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Purge time should be updated for leaf category", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get multi store info by store ids
//	public static void multipleStoreInfo(String store_ids) throws Exception {
//		boolean flag = true;
//		try {
//			testCaseReporting.header("Multi Store Info");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForMultipleStoreInfo(store_ids);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("changePurgeTimeForLeafCategory " + jsonString);
//			String[] stores = store_ids.split(",");
//			for (int i = 0; i < stores.length; i++) {
//				String data = getValueFromParser_WithBrace(jsonString, "data");
//				String store = getValueFromParser_WithBrace(data, stores[i]);
//				String owner = getValueFromParser(store, "owner");
//				Utility.Getdbdata.connectDB();
//				String ownName = Utility.Getdbdata
//						.executeQuery("SELECT owner FROM merchant_test.store_store where store_id=" + stores[i]);
//				Utility.Getdbdata.closeDBConnection();
//				if (owner.trim().equalsIgnoreCase(ownName.trim())) {
//				} else {
//					flag = false;
//					break;
//				}
//			}
//
//			endtime = System.currentTimeMillis();
//
//			if (flag) {
//				testCaseReporting.teststepreporting("Multi stores info verified successfully", "PASS",
//						"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Multi stores info are not verified", "FAIL",
//						"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Get store listing by multiple arguments
//	public static void storeListing(String store_Name, String store_id, String phoneNo, String city, String is_verified,
//			String date_range, String lat, String lng) throws Exception {
//		String dbCount = null;
//		try {
//			testCaseReporting.header("Store Listing");
//			starttime = System.currentTimeMillis();
//			Utility.Getdbdata.connectDB();
//			String payload = APIPayload.jsonPayLoadForStoreListing(store_Name, store_id, phoneNo, city, is_verified,
//					date_range, lat, lng);
//			ParamObject params = APIParameters.getParamsForStoreListing(payload);
//
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			System.out.println("changePurgeTimeForLeafCategory " + jsonString);
//
//			String count = getValueFromParser(jsonString, "count");
//			if (!store_Name.trim().equalsIgnoreCase(""))
//				dbCount = Utility.Getdbdata
//						.executeQuery("SELECT count(*) FROM merchant_test.store_store where name like '" + store_Name
//								+ "%' and is_deleted=0");
//
//			if (!store_id.trim().equalsIgnoreCase(""))
//				dbCount = Utility.Getdbdata
//						.executeQuery("SELECT count(*) FROM merchant_test.store_store where store_id=" + store_id
//								+ " and is_deleted=0");
//
//			if (!phoneNo.trim().equalsIgnoreCase(""))
//				dbCount = Utility.Getdbdata.executeQuery(
//						"SELECT count(*) FROM merchant_test.store_store st,merchant_test.store_storephoneno phn where phn.phone_number="
//								+ phoneNo + " and phn.store_id=st.id and is_deleted=0");
//
//			if (!city.trim().equalsIgnoreCase(""))
//				dbCount = Utility.Getdbdata.executeQuery(
//						"SELECT count(*) FROM merchant_test.store_store where city='" + city + "' and is_deleted=0");
//
//			if (!is_verified.trim().equalsIgnoreCase("")) {
//				int k;
//				if (is_verified.trim().equalsIgnoreCase("true"))
//					k = 1;
//				else
//					k = 0;
//
//				dbCount = Utility.Getdbdata.executeQuery(
//						"SELECT count(*) FROM merchant_test.store_store where is_verified=" + k + " and is_deleted=0");
//			}
//
//			Utility.Getdbdata.closeDBConnection();
//			endtime = System.currentTimeMillis();
//
//			if (Integer.parseInt(count.trim()) == Integer.parseInt(dbCount.trim())) {
//				testCaseReporting.teststepreporting(
//						"DB store count <b><i> " + dbCount + " </i></b> is verified with API store count <b><i>" + count
//								+ "</i></b>",
//						"PASS", "Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting(
//						"DB store count <b><i> " + dbCount + " </i></b> is not verified with API store count <b><i>"
//								+ count + "</i></b>",
//						"FAIL", "Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Update store by store id
//	public static void updateStoreByStore_Id(String payload, String store_id, String keyMain, String value,
//			String DbCol) throws Exception {
//		try {
//			testCaseReporting.header("Upload Store By Store_Id");
//			starttime = System.currentTimeMillis();
//			String[] str = store_id.split("##");
//			JSONObject obj = new JSONObject(payload);
//
//			Iterator<?> iterator = obj.keys();
//			String key = null;
//			while (iterator.hasNext()) {
//				key = (String) iterator.next();
//				// if object is just string we change value in key
//				if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
//					if ((key.equals(keyMain))) {
//						// put new value
//						obj.put(key, value);
//					}
//				}
//			}
//			ParamObject params = APIParameters.getParamsForUpdateStoreByStore_Id(obj.toString(), str[0]);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			Utility.Getdbdata.connectDB();
//			String keyValue = Utility.Getdbdata
//					.executeQuery(" SELECT " + DbCol + " FROM merchant_test.store_store where store_id=" + str[0]);
//			Utility.Getdbdata.closeDBConnection();
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "action").equalsIgnoreCase("updated")
//					&& keyValue.trim().equalsIgnoreCase(value.trim())) {
//				testCaseReporting.teststepreporting("Store updated successfully", "PASS", "Store should be updated",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Store is not updated", "FAIL", "Store should be updated",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store should be updated", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	//Update store by slug id
//	public static void updateStoreBySlug(String payload, String store_id, String keyMain, String value, String DbCol)
//			throws Exception {
//		try {
//			testCaseReporting.header("Update Store By Slug");
//			starttime = System.currentTimeMillis();
//			String[] str = store_id.split("##");
//			JSONObject obj = new JSONObject(payload);
//
//			Iterator<?> iterator = obj.keys();
//			String key = null;
//			while (iterator.hasNext()) {
//				key = (String) iterator.next();
//				// if object is just string we change value in key
//				if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
//					if ((key.equals(keyMain))) {
//						// put new value
//						obj.put(key, value);
//					}
//				}
//			}
//			ParamObject params = APIParameters.getParamsForUpdateStoreBySlug(obj.toString(), str[1]);
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			Utility.Getdbdata.connectDB();
//			String keyValue = Utility.Getdbdata
//					.executeQuery("SELECT " + DbCol + " FROM merchant_test.store_store where slug=\"" + str[1] + "\"");
//			Utility.Getdbdata.closeDBConnection();
//			endtime = System.currentTimeMillis();
//
//			if (getValueFromParser(jsonString, "action").equalsIgnoreCase("updated")
//					&& keyValue.trim().equalsIgnoreCase(value.trim())) {
//				testCaseReporting.teststepreporting("Store updated successfully", "PASS", "Store should be updated",
//						getTotalExecutionTime(starttime, endtime));
//			} else {
//				testCaseReporting.teststepreporting("Store is not updated", "FAIL", "Store should be updated",
//						getTotalExecutionTime(starttime, endtime));
//			}
//		} catch (Exception e) {
//			endtime = System.currentTimeMillis();
//			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
//					"Store should be updated", getTotalExecutionTime(starttime, endtime));
//		} finally {
//			testCaseReporting.footer();
//		}
//
//	}
//
//	public static void test() throws Exception {
//		try {
//			// testCaseReporting.header("Get Services");
//			starttime = System.currentTimeMillis();
//			ParamObject params = APIParameters.getParamsForProductId();
//			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
//			// System.out.println(jsonString);
//			String valu = getValueFromParser_WithBrace(jsonString, "info");
//			System.out.println(getValueFromParser(valu, "title"));
//			if (getValueFromParser(valu, "title").equalsIgnoreCase("Samsung Galaxy J7 Black"))
//				System.out.println("PASS");
//			else
//				System.out.println("FAIL");
//			endtime = System.currentTimeMillis();
//			// if (flag) {
//			// testCaseReporting.teststepreporting(
//			// "<b><i>" + Verification_Checks.SERVICETYPE
//			// + " </i></b>Get services API verified successfully with <b><i>" +
//			// out + "</i></b>",
//			// "PASS", "Get services API should be verified successfully",
//			// getTotalExecutionTime(starttime, endtime));
//			// } else {
//			// testCaseReporting.teststepreporting("Get services API noy
//			// verified", "FAIL",
//			// "Get services API should be verified successfully",
//			// getTotalExecutionTime(starttime, endtime));
//			// }
//
//		} catch (Exception e) {
//			// System.out.println(e.getMessage());
//			// endtime = System.currentTimeMillis();
//			// testCaseReporting.teststepreporting("Exception occured : " +
//			// "<b><i>" + e.toString() + "</i></b>", "FAIL",
//			// "Get services API should be verified successfully",
//			// getTotalExecutionTime(starttime, endtime));
//		}
//
//	}
//
//}
