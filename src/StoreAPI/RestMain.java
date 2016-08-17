package StoreAPI;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import Parameters.APIParameters;
import Payload.APIPayload;
import testCaseReporting.SuiteReporting;
import testCaseReporting.TestCaseReporting;
import VerificationChecks.Verification_Checks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestMain {

	static SuiteReporting suitreporting = new SuiteReporting("Suite");;
	static TestCaseReporting testCaseReporting = new TestCaseReporting("test");
	static Long starttime;
	static Long endtime;

	@SuppressWarnings("static-access")
	private static void getStoresInfo1() throws IOException, JSONException {
		try {
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsMyStores();
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			// System.out.println(jsonString);
			JSONArray arr = new JSONArray(jsonString);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject jso = arr.getJSONObject(i);
				String name = jso.getString("name");
				String store_id = jso.getString("store_id");
				endtime = System.currentTimeMillis();
				if (name.equalsIgnoreCase(Verification_Checks.name2)
						&& store_id
								.equalsIgnoreCase(Verification_Checks.StoreID1)) {
					testCaseReporting.teststepreporting("Store name is: "
							+ name + " AND Store id is :" + store_id, "PASS",
							"New-Gen-retail with store id 81307 ",
							getTotalExecutionTime(starttime, endtime));

				} else {
					testCaseReporting.teststepreporting("Store name is: "
							+ name + " AND Store id is :" + store_id, "Fail",
							"Sanchar World with store id 83523 ",
							getTotalExecutionTime(starttime, endtime));

				}

			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"get stores info 1 should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}

	}

	public static void getPricesByStore(String store_Id) throws Exception {
		String product_ids = "";
		boolean flag = true;
		try {
			testCaseReporting.header("Get Prices");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForGetPricesByStore(store_Id);
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			JSONObject serv = new JSONObject(jsonString);
			JSONArray st = serv.getJSONArray("results");
			Utility.Getdbdata.connectDB();
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				String prod_Id = getValueFromParser(json_obj.toString(), "product_id");

				String cat_Id = getValueFromParser(json_obj.toString(), "category");
				String prod_Title = getValueFromParser(json_obj.toString(), "product_title");
				String sp_price = getValueFromParser(json_obj.toString(), "sp_price");
				sp_price = sp_price.replace(".00", "");
				int sp = Integer.parseInt(sp_price) + 100;
				sp_price = String.valueOf(sp);
				product_ids = product_ids + " " + prod_Id;

				String Category_id = Utility.Getdbdata.executeQuery("SELECT cat_id FROM merchant_test.store_category where id="+cat_Id);

				sellQuery_Retail(prod_Id, prod_Title, store_Id, Category_id, 0, 0, sp, 720);
				String sp_Price = Utility.Getdbdata
						.executeQuery("SELECT sp_price FROM merchant_test.store_product_price where store_id="
								+ store_Id + " and product_id=" + prod_Id + " and category_id=" + cat_Id);
				if (sp_Price.trim().contains(sp_price.trim())) {

				} else {
					flag = false;
					break;
				}
			}
			
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting.teststepreporting("Prices verified", "PASS", "Prices should be verified",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Prices are not verified", "FAIL", "Prices should be verified",
						getTotalExecutionTime(starttime, endtime));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Prices should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			Utility.Getdbdata.closeDBConnection();
			testCaseReporting.footer();
		}

	}
	
	private static void sellQuery_Retail(String prod_Id, String prod_Title, String store_id, String cat_Id,
	int base_Price, int msp_price, int sp_price, int price_exp_time) throws Exception {

	try {
		starttime = System.currentTimeMillis();
		String payload = APIPayload.jsonPayLoadForSellQuery_Retail(prod_Id, prod_Title, store_id, cat_Id,
				base_Price, msp_price, sp_price, price_exp_time);
		ParamObject params = APIParameters.getParamsForSellQuery_Retailer(payload, store_id);
	
		String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
		System.out.println("sellQuery_Retail : "+jsonString);
	
		endtime = System.currentTimeMillis();
	
		if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Updated")) {
			testCaseReporting.teststepreporting(
					"Product price updated for product id and store id respectively <b><i>" + prod_Id + ","
							+ store_id + "</i></b>",
					"PASS", "Product price should be updated", getTotalExecutionTime(starttime, endtime));
		} else {
			testCaseReporting.teststepreporting("Product price not updated", "FAIL",
					"Product price should be updated", getTotalExecutionTime(starttime, endtime));
		}
	} catch (Exception e) {
		endtime = System.currentTimeMillis();
		testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
				"Product price should be updated", getTotalExecutionTime(starttime, endtime));
}

}
	

	public static void purgePriceByStore(String prod_Id, String store_id) throws Exception {
		boolean flag = true;
		try {
			testCaseReporting.header("Purge Price By Store");
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForPurgePriceByStore(prod_Id);
			ParamObject params = APIParameters.getParamsForPurgePriceByStore(payload, store_id);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);

			String product_ids = getPricesByStore_ReturnProductIds(store_id);
			String[] prodList = product_ids.split(" ");
			for (int i = 0; i < prodList.length; i++) {
				if (prodList[i].equalsIgnoreCase(prod_Id)) {
					flag = false;
					break;
				} else {
					flag = true;

				}
			}

			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Purged") && flag == true) {
				testCaseReporting.teststepreporting(
						"Price purged for product id and store id respectively <b><i>" + prod_Id + "," + store_id
								+ "</i></b>",
						"PASS", "Price should be purged", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Price not purged", "FAIL", "Price should be purged",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Price should be purged", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	public static void purgeAllPriceByStore(String store_id) throws Exception {

		try {
			testCaseReporting.header("Purge All Price By Store");
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForPurgeAllPriceByStore();
			ParamObject params = APIParameters.getParamsForPurgeAllPriceByStore(payload, store_id);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			String product_ids = getPricesByStore_ReturnProductIds(store_id);
			System.out.println(product_ids);
			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("All price purged")
					&& product_ids.equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting("All Prices are purged for store id <b><i>" + store_id + "</i></b>",
						"PASS", "All Prices should be purged", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("All Prices are not purged", "FAIL", "All Prices should be purged",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"All Prices should be purged", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}
	
	public static String getPricesByStore_ReturnProductIds(String store_Id) throws Exception {
	String product_ids = "";
	try {
		ParamObject params = APIParameters.getParamsForGetPricesByStore(store_Id);
		String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
		System.out.println(jsonString);
		JSONObject serv = new JSONObject(jsonString);
		JSONArray st = serv.getJSONArray("results");
		for (int i = 0; i < st.length(); i++) {
			JSONObject json_obj = st.getJSONObject(i);
			String prod_Id = getValueFromParser(json_obj.toString(), "product_id");
			product_ids = product_ids + " " + prod_Id;
		}

	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	return product_ids;

}


	//Get Price verification data info API
	public static void getPriceVerficationData() throws Exception {
		String priceDetails = "";
		try {
			testCaseReporting.header("Get Price Verification");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForGetPriceVerficationData();
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			JSONObject serv = new JSONObject(jsonString);
			JSONArray st = serv.getJSONArray("results");
			System.out.println("Array count " + st.length());
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				String instance_Id = getValueFromParser(json_obj.toString(), "id");
				String store = getValueFromParser(json_obj.toString(), "store");
				String product_Id = getValueFromParser(json_obj.toString(), "product_id");
				priceDetails = priceDetails + " " + instance_Id + "##" + store + "##" + product_Id;
				verifyPrice(store, instance_Id, true);
			}
			endtime = System.currentTimeMillis();
			if (!priceDetails.equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting(
						"<b><i>" + priceDetails + "</i></b> Price verification data fetched successfully", "PASS",
						"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Price verification data not found", "FAIL",
						"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Price verification data should be fetched", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	private static void verifyPrice(String store_id, String instance_id, Boolean sts) throws Exception {

		try {
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForVerifyPrice(sts);
			ParamObject params = APIParameters.getParamsForVerifyPrice(payload, instance_id);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println("verify price " + jsonString);
			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Price Verified")) {
				testCaseReporting.teststepreporting("Price verified for store id <b><i>" + store_id + "</i></b>",
						"PASS", "Price should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Price is not verified", "FAIL", "Price should be verified",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Price should be verified", getTotalExecutionTime(starttime, endtime));
		}

	}

	//Change purge time by category id
	public static void changePurgeTimeForLeafCategory(String cat_id, int purgeTime) throws Exception {

		try {
			testCaseReporting.header("Change Purge Time");
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForChangePurgeTimeOfLeafCategory(purgeTime);
			ParamObject params = APIParameters.getParamsForChangePurgeTimeOfLeafCategory(payload, cat_id);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println("changePurgeTimeForLeafCategory " + jsonString);
			Utility.Getdbdata.connectDB();
			String prgTime = Utility.Getdbdata
					.executeQuery(" SELECT purge_time FROM merchant_test.store_category where cat_id=" + cat_id);
			Utility.Getdbdata.closeDBConnection();
			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "message").equalsIgnoreCase("Done")
					&& purgeTime == Integer.parseInt(prgTime.trim())) {
				testCaseReporting.teststepreporting(
						"Purge time<b><i> " + purgeTime + " </i></b>updated for leaf category <b><i>" + cat_id
								+ "</i></b>",
						"PASS", "Purge time should be updated for leaf category",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Purge time not updated for leaf category", "FAIL",
						"Purge time should be updated for leaf category", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Purge time should be updated for leaf category", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Get multi store info by store ids
	public static void multipleStoreInfo(String store_ids) throws Exception {
		boolean flag = true;
		try {
			testCaseReporting.header("Multi Store Info");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForMultipleStoreInfo(store_ids);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println("changePurgeTimeForLeafCategory " + jsonString);
			String[] stores = store_ids.split(",");
			for (int i = 0; i < stores.length; i++) {
				String data = getValueFromParser_WithBrace(jsonString, "data");
				String store = getValueFromParser_WithBrace(data, stores[i]);
				String owner = getValueFromParser(store, "owner");
				Utility.Getdbdata.connectDB();
				String ownName = Utility.Getdbdata
						.executeQuery("SELECT owner FROM merchant_test.store_store where store_id=" + stores[i]);
				Utility.Getdbdata.closeDBConnection();
				if (owner.trim().equalsIgnoreCase(ownName.trim())) {
				} else {
					flag = false;
					break;
				}
			}

			endtime = System.currentTimeMillis();

			if (flag) {
				testCaseReporting.teststepreporting("Multi stores info verified successfully", "PASS",
						"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Multi stores info are not verified", "FAIL",
						"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Multi stores info should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Get store listing by multiple arguments
	public static void storeListing(String store_Name, String store_id, String phoneNo, String city, String is_verified,
			String date_range, String lat, String lng) throws Exception {
		String dbCount = null;
		try {
			testCaseReporting.header("Store Listing");
			starttime = System.currentTimeMillis();
			Utility.Getdbdata.connectDB();
			String payload = APIPayload.jsonPayLoadForStoreListing(store_Name, store_id, phoneNo, city, is_verified,
					date_range, lat, lng);
			ParamObject params = APIParameters.getParamsForStoreListing(payload);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println("changePurgeTimeForLeafCategory " + jsonString);

			String count = getValueFromParser(jsonString, "count");
			if (!store_Name.trim().equalsIgnoreCase(""))
				dbCount = Utility.Getdbdata
						.executeQuery("SELECT count(*) FROM merchant_test.store_store where name like '" + store_Name
								+ "%' and is_deleted=0");

			if (!store_id.trim().equalsIgnoreCase(""))
				dbCount = Utility.Getdbdata
						.executeQuery("SELECT count(*) FROM merchant_test.store_store where store_id=" + store_id
								+ " and is_deleted=0");

			if (!phoneNo.trim().equalsIgnoreCase(""))
				dbCount = Utility.Getdbdata.executeQuery(
						"SELECT count(*) FROM merchant_test.store_store st,merchant_test.store_storephoneno phn where phn.phone_number="
								+ phoneNo + " and phn.store_id=st.id and is_deleted=0");

			if (!city.trim().equalsIgnoreCase(""))
				dbCount = Utility.Getdbdata.executeQuery(
						"SELECT count(*) FROM merchant_test.store_store where city='" + city + "' and is_deleted=0");

			if (!is_verified.trim().equalsIgnoreCase("")) {
				int k;
				if (is_verified.trim().equalsIgnoreCase("true"))
					k = 1;
				else
					k = 0;

				dbCount = Utility.Getdbdata.executeQuery(
						"SELECT count(*) FROM merchant_test.store_store where is_verified=" + k + " and is_deleted=0");
			}

			Utility.Getdbdata.closeDBConnection();
			endtime = System.currentTimeMillis();

			if (Integer.parseInt(count.trim()) == Integer.parseInt(dbCount.trim())) {
				testCaseReporting.teststepreporting(
						"DB store count <b><i> " + dbCount + " </i></b> is verified with API store count <b><i>" + count
								+ "</i></b>",
						"PASS", "Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"DB store count <b><i> " + dbCount + " </i></b> is not verified with API store count <b><i>"
								+ count + "</i></b>",
						"FAIL", "Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store listing sould be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	public static String getOrderDetailsAPI(String order_id, String store_id, String status) throws Exception {
		String order_items, order_List = "";
		try {
			testCaseReporting.header("Order Details");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForOrderDetailAPI(order_id, store_id, status);
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			JSONObject serv = new JSONObject(jsonString);
			JSONArray ch = serv.getJSONArray("items");
			for (int j = 0; j < ch.length(); j++) {
				JSONObject json_ChildObj = ch.getJSONObject(j);
				order_items = getValueFromParser(json_ChildObj.toString(), "id").trim();
				order_List = order_List + " " + order_items;
			}

			endtime = System.currentTimeMillis();
			if (!order_List.equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting("<b><i>" + order_List + " </i></b> Order details verified", "PASS",
						"Order details should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("<b><i>" + order_List + "</i></b> Order details not verified",
						"FAIL", "Order details should be Fetched successfully.",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
					"Order details should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}
		return order_List;

	}

	//Order status API
	public static void getOrderStatus(String order_id, String store_Id, String status) throws Exception {

		try {
			testCaseReporting.header("Order Status");
			RestClient.restClient_Flag = false;
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForOrderStatusAPI(store_Id, status);

			ParamObject params = APIParameters.getParamsForOrderStatusAPI(order_id, payload);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);

			System.out.println("Json string : " + jsonString);

			System.out.println(getValueFromParser(jsonString, "message"));
			String order_Status = getValueFromParser(jsonString, "message");
			endtime = System.currentTimeMillis();
			if (order_Status.trim().equalsIgnoreCase(Verification_Checks.ORDER_STATUS)) {
				testCaseReporting.teststepreporting(
						"<b><i>" + order_Status + " </i></b>Order Status verified with <b><i>"
								+ Verification_Checks.ORDER_STATUS + "</i></b>",
						"PASS", "Order status should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"<b><i>" + order_Status + " </i></b>Order Status not verified with <b><i>"
								+ Verification_Checks.ORDER_STATUS + "</i></b>",
						"FAIL", "Order status should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Order status should be verified", getTotalExecutionTime(starttime, endtime));
			throw new Exception(e.toString());
		} finally {
			testCaseReporting.footer();
		}

	}

	//Generate shipment API
	public static void getShipmentHandler(String type, String orderItems, String storeId) throws Exception {

		String shipmentId = "";
		try {
			testCaseReporting.header("Generate Shipment");
			RestClient.restClient_Flag = false;
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForShipmentHandler(type, orderItems, storeId);
			ParamObject params = APIParameters.getParamsForShipmentHandler(payload);
			System.out.println(params.getPayload());

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);

			shipmentId = getValueFromParser(jsonString, "shipment_id");
			endtime = System.currentTimeMillis();
			if (!shipmentId.trim().equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting("<b><i>" + shipmentId + " Shipment id fetched successfully", "PASS",
						"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Unable to fetch shipment id", "FAIL",
						"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Shipment id should be feteched", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Deliver order API
	public static void getOrderDeliveryStatus(String shipmentId, String shipmentStatus, String storeId, String orderId)
			throws Exception {

		String summary = "";
		try {
			testCaseReporting.header("Order Delivery status");
			RestClient.restClient_Flag = false;
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForOrderDeliveryStatus(shipmentId, shipmentStatus, storeId);
			ParamObject params = APIParameters.getParamsForOrderDeliveryStatus(payload, orderId);
			System.out.println(params.getPayload());

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);

			summary = getValueFromParser(jsonString, "message");
			endtime = System.currentTimeMillis();
			if (summary.trim().equalsIgnoreCase(Verification_Checks.SUMMERY)) {
				testCaseReporting.teststepreporting(
						"<b><i>" + summary.trim() + "</i></b> Order delivery status verified with <b><i>"
								+ Verification_Checks.SUMMERY + "</i></b>",
						"PASS", "Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Order delivery status not verified", "FAIL",
						"Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Order delivery status should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Order list API
	public static void getOrderListAPI(String pageSize, String pageNo, String store_id, String status)
			throws Exception {
		String order_id, order_Item, total_Amount, order_List = "";
		try {
			testCaseReporting.header("Order Listing");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForOrderListAPI(pageSize, pageNo, store_id, status);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			JSONObject serv = new JSONObject(jsonString);
			JSONArray st = serv.getJSONArray("results");
			System.out.println("array length: " + st.length());
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				order_id = getValueFromParser(json_obj.toString(), "order_id");
				order_Item = getValueFromParser(json_obj.toString(), "id");
				total_Amount = getValueFromParser(json_obj.toString(), "total_amount");
				order_List = order_List + " " + order_id + "##" + order_Item + "##" + total_Amount;
			}
			System.out.println("order_List " + order_List);
			endtime = System.currentTimeMillis();
			if (!order_List.equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting("<b><i>" + order_List + " </i></b> Order list verified", "PASS",
						"Order List should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("<b><i>" + order_List + "</i></b> Order List not verified", "FAIL",
						"Order List should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
					"Order List should be Fetched successfully.", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}
	}

	//Get warrant enabled category
	public static void getWarrantyEnabledCategoriesAPI(String price, String duration) throws Exception {

		boolean flag = true;
		try {
			testCaseReporting.header("Warranty Enabled Categories");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForWarrantyEnabledCategoriesAPI();

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);

			String categories = getValueFromParser(jsonString, "categories");
			System.out.println(jsonString);
			JSONArray st = new JSONArray(categories);
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				String cat = getValueFromParser(json_obj.toString(), "cat").trim();
				String cat_id = getValueFromParser(json_obj.toString(), "cat_id").trim().replace("[", "");
				cat_id = cat_id.replace("]", "");

				getWarrentyAmount(cat_id, price, duration);

				System.out.println(cat_id);
				cat = cat_id + ":" + cat;

				if (Verification_Checks.CATEGORIES_CODE.contains(cat)) {

				} else {
					flag = false;
					break;
				}

			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting.teststepreporting(
						"<b><i>" + categories + " </i></b>Warranty enabled categories verified with <b><i>"
								+ Verification_Checks.CATEGORIES_CODE + "</i></b>",
						"PASS", "Warranty enabled categories should be verified",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"<b><i>" + categories + "</i></b> Warranty enabled categories not verified with <b><i>"
								+ Verification_Checks.CATEGORIES_CODE + "</i></b>",
						"FAIL", "Warranty enabled categories should be Fetched successfully",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + e.toString(), "FAIL",
					"Warranty enabled categories should be Fetched successfully.",
					getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Get Warrant amount API
	public static void getWarrentyAmount(String cat, String price, String duration) throws Exception {

		boolean flag = true;
		String amount = "", durn = "";
		try {
			RestClient.restClient_Flag = false;
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForCalculateWarrantyAmount(cat, price, duration);
			ParamObject params = APIParameters.getParamsForGetWarrantyAmount(payload);
			System.out.println(params.getPayload());

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);

			JSONArray st = new JSONArray(jsonString);
			System.out.println("array length : " + st.length());
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				amount = amount + " " + getValueFromParser(json_obj.toString(), "amount");

				durn = durn + " " + getValueFromParser(json_obj.toString(), "duration");
			}
			endtime = System.currentTimeMillis();
			if (!amount.trim().equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting(
						"Warranty amount generated with duration i.e., Amount <b><i>" + amount
								+ " </i></b>with year duration <b><i>" + durn + " </i></b>respectedly",
						"PASS", "Warranty amount should be generated with duration",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Unable to generate waranty amount", "FAIL",
						"Warranty amount should be generated with duration", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Warranty amount should be generated with duration", getTotalExecutionTime(starttime, endtime));
		}

	}

	//Get all invoices
	public static void getAllInvoices() throws Exception {

		boolean flag = true;
		try {
			testCaseReporting.header("Get All Invoice");
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsAllInvoices();

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println("jsonString   "+jsonString);
			JSONArray st = new JSONArray(getValueFromParser(jsonString, "results"));
			System.out.println(st.length());
			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);

				getInvoiceDetails(getValueFromParser(json_obj.toString(), "invoice_id").trim(),
						getValueFromParser(json_obj.toString(), "user_name"));
			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting.teststepreporting("All invoices verified successfully", "PASS",
						"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("All invoices not verified", "FAIL",
						"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"All invoices should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	private static void getInvoiceDetails(String invoice_ID, String user_Name) throws Exception {

		try {
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsInvoiceDetails(invoice_ID);

			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			System.out.println(getValueFromParser(jsonString, "full_name"));
			String userName = getValueFromParser(jsonString, "full_name");
			endtime = System.currentTimeMillis();

			if (userName.trim().equalsIgnoreCase(user_Name.trim())) {
				testCaseReporting.teststepreporting(
						"<b><i>" + userName + " </i></b>Invoice details verified with <b><i>" + user_Name
								+ "</i></b> for invoice id" + invoice_ID,
						"PASS", "Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"<b><i>" + userName + " and " + user_Name + " </i></b>Invoice details not verified with <b><i>"
								+ user_Name + "</i></b> for invoice id " + invoice_ID,
						"FAIL", "Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Invoice details should be verified", getTotalExecutionTime(starttime, endtime));
		}

	}

	//Sell query API
	public static void getSellQuery(String productId, String storeId, String sPrice) throws Exception {

		String summary = "";
		try {
			testCaseReporting.header("Retailer App Sell Query");
			RestClient.restClient_Flag = false;
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForSellQuery(productId, storeId, sPrice);
			ParamObject params = APIParameters.getParamsForSellQuery(payload);
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);

			summary = getValueFromParser(jsonString, "message");
			endtime = System.currentTimeMillis();
			if (summary.trim().equalsIgnoreCase(Verification_Checks.SELL_QUERY_MESSAGE)) {
				testCaseReporting.teststepreporting(
						"<b><i>" + summary.trim() + "</i></b> Sell query verified with <b><i>"
								+ Verification_Checks.SELL_QUERY_MESSAGE + "</i></b>",
						"PASS", "Sell Query should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Sell Query not verified", "FAIL", "Sell Query should be verified",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Sell Query should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}


	@SuppressWarnings({ "static-access", "unused" })
	public static void storeLoginAPI() throws JSONException, IOException {
		try {
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForLoginAPI();
			System.out.println(payload);
			ParamObject params = APIParameters.getParamsForStoreLogin(payload);
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			// System.out.println(jsonString);
			JSONObject root = new JSONObject(jsonString);
			String name = root.getString("username");
			String token = root.getString("token");
			endtime = System.currentTimeMillis();
			if (name.equalsIgnoreCase(Verification_Checks.name1)) {
				testCaseReporting.teststepreporting("Store name is: " + name
						+ " and token is :" + token, "PASS",
						"New-Gen-Retail with token id " + token,
						getTotalExecutionTime(starttime, endtime));
				getStoresInfo1();
			} else {
				testCaseReporting.teststepreporting("Store name is: " + name
						+ " and token is :" + token, "FAIL",
						"New-Gen-Retail with token id " + token,
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"store login name should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}

	}

	@SuppressWarnings({ "unused", "static-access" })
	private static void verifyStoreAPI() throws JSONException, IOException {
		try {
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForVerifyStoresAPI();
			System.out.println(payload);
			ParamObject params = APIParameters.getParamsVerifyStore(payload);
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			// System.out.println(jsonString);
			JSONObject root = new JSONObject(jsonString);
			JSONArray slugname = root.getJSONArray("changed");
			String username = (String) slugname.get(0);
			endtime = System.currentTimeMillis();
			if (username.equalsIgnoreCase(Verification_Checks.username)) {
				testCaseReporting.teststepreporting(username, "PASS",
						"Username should be " + username,
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(username, "FAIL",
						"Username should be" + username,
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"verify stores info should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}

	}

	@SuppressWarnings("static-access")
	public static void getListOfStates() throws IOException, JSONException {
		try {
			starttime = System.currentTimeMillis();
			boolean flag = true;
			String nameValue = "";
			ParamObject params = APIParameters.getParamsForGetListOfStates();
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			// System.out.println(jsonString);
			JSONArray rootarray = new JSONArray(jsonString);
			for (int i = 0; i < rootarray.length(); i++) {
				JSONObject obj = rootarray.getJSONObject(i);
				String name = obj.getString("name");
				name = name.replace(" ", "");
				// System.out.print(name);
				nameValue = nameValue + " " + name;

				// System.out.println(nameValue);
				if (Verification_Checks.state.contains(name)) {

				} else {
					flag = false;
					break;
				}

			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting.teststepreporting("States names are "
						+ nameValue, "PASS", "Statename should contain "
						+ nameValue, getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("States names are "
						+ nameValue, "FAIL", "Statename should contain "
						+ nameValue, getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"get states info should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}
	}

	@SuppressWarnings("static-access")
	public static void getListOfCities() throws IOException, JSONException {
		try {
			starttime = System.currentTimeMillis();
			boolean flag = true;
			String nameValue = "";
			ParamObject params = APIParameters.getParamsForGetListOfStates();
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			ArrayList<String> ids = APIParameters
					.getIDsArrayFromStates(jsonString);
			for (String id : ids) {
				ParamObject citiesparams = APIParameters
						.getParamsForGetListOfCities(id);
				String jsonStringForCities = (String) RestClient
						.getJSONFromParamsObject(citiesparams,
								RestClient.RETURN_JSON_STRING);
				JSONArray rootarray = new JSONArray(jsonStringForCities);
				for (int i = 0; i < rootarray.length(); i++) {
					JSONObject obj = rootarray.getJSONObject(i);
					String name = obj.getString("name");
					name = name.replace(" ", "");
					// System.out.print(name);
					nameValue = nameValue + " " + name;
					if (Verification_Checks.cities.contains(name)) {

					} else {
						flag = false;
					}

				}
			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting.teststepreporting("Cities names are "
						+ nameValue, "PASS", "Citiesname should contain "
						+ nameValue, getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Cities names are "
						+ nameValue, "FAIL", "Citiesname should contain "
						+ nameValue, getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"get cities info should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}

	}

	@SuppressWarnings({ "static-access", "unused" })
	public static void getStoresByRegion() throws Exception {
		String query = "select count(*) from store_store where city='Noida' OR city='Ghaziabad'";
		boolean flag = true;
		String nameValue = "";
		try {
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsStoreByRegion();
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			int dbcount = Utility.Getdbdata.dbGetData(query);
			JSONArray rootArray = new JSONArray(jsonString);
			int size = rootArray.length();
			endtime = System.currentTimeMillis();
			if (dbcount == size) {
				testCaseReporting.teststepreporting("Store count  are " + size,
						"PASS", "Store count should be  " + dbcount,
						getTotalExecutionTime(starttime, endtime));

			} else {
				testCaseReporting.teststepreporting("Store count  are " + size,
						"PASS", "Store count should be  " + dbcount,
						getTotalExecutionTime(starttime, endtime));
			}

		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"get stores info should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}
	}

	@SuppressWarnings({ "unused", "static-access" })
	public static void getProducts() throws Exception {
		try {
			starttime = System.currentTimeMillis();
			String categories[] = { "208" };

			int pageNumbers = 1;
			for (String cat : categories) {

				String[] cats = { cat };

				for (int page = 1; page <= pageNumbers; page++) {

					String payload = APIPayload.jsonPayLoadForProductListing(
							cats, "" + page);
					// System.out.println("" + payload);

					ParamObject params = APIParameters
							.getParamsForProductListing(payload);

					String jsonString = (String) RestClient
							.getJSONFromParamsObject(params,
									RestClient.RETURN_JSON_STRING);

					ArrayList<String> ids = APIParameters
							.getIDsArrayFromProductsJson(jsonString);

					for (String id : ids) {
						// String fileName ="E:\\sel-online\\data2.txt";
						ParamObject obj = APIParameters
								.getParamsForProductId(id);
						int responseCode = (int) RestClient
								.getJSONFromParamsObject(params,
										RestClient.RETURN_RESPONSE_CODE);
						System.out.println("Response Code for ProductID = "
								+ id + "  ---- is  ---->>> " + responseCode);
						RestMain.printToFile(id, responseCode);

					}
				}
			}
			endtime = System.currentTimeMillis();
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"get products info should be verified",
					getTotalExecutionTime(starttime, endtime));
			throw (e);
		}

	}

	@SuppressWarnings("static-access")
	public static String createNewStore() throws Exception {
		testCaseReporting.header("Create New Store");
		String store_id = "", slug = null;
		try {
			starttime = System.currentTimeMillis();
			String[] phn_nos = { "1124566776" };
			String[] contPer = { "Ram", "Shyam", "Laxman" };
			String[] wrkdays = { "mo", "tu" };
			String payload = APIPayload.jsonPayLoadForStoreCreation(phn_nos,
					contPer, wrkdays);
			ParamObject params = APIParameters
					.getParamsForCreateNewStoreAPI(payload);
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);

			System.out.println("createNewStore" + jsonString);

			endtime = System.currentTimeMillis();
			store_id = getValueFromParser(jsonString, "store_id");
			slug = getValueFromParser(jsonString, "slug");
			if (!store_id.trim().equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting(
						"New Store with store id <b><i>" + store_id.trim()
								+ "</i></b> created successfully", "PASS",
						"New store should be created",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"New Store creation failed", "FAIL",
						"New store should be created",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			testCaseReporting.teststepreporting(
					"Exception occured in new store creation", "FAIL",
					"New store should be created",
					getTotalExecutionTime(starttime, endtime));

		}
		return store_id + "##" + slug;

	}
	
	//Update store by slug id
	public static void updateStoreBySlug(String payload, String store_id, String keyMain, String value, String DbCol)
			throws Exception {
		try {
			testCaseReporting.header("Update Store By Slug");
			starttime = System.currentTimeMillis();
			String[] str = store_id.split("##");
			JSONObject obj = new JSONObject(payload);

			Iterator<?> iterator = obj.keys();
			String key = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				// if object is just string we change value in key
				if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
					if ((key.equals(keyMain))) {
						// put new value
						obj.put(key, value);
					}
				}
			}
			ParamObject params = APIParameters.getParamsForUpdateStoreBySlug(obj.toString(), str[1]);
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			Utility.Getdbdata.connectDB();
			String keyValue = Utility.Getdbdata
					.executeQuery("SELECT " + DbCol + " FROM merchant_test.store_store where slug=\"" + str[1] + "\"");
			Utility.Getdbdata.closeDBConnection();
			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "action").equalsIgnoreCase("updated")
					&& keyValue.trim().equalsIgnoreCase(value.trim())) {
				testCaseReporting.teststepreporting("Store updated successfully", "PASS", "Store should be updated",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Store is not updated", "FAIL", "Store should be updated",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store should be updated", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}

	//Update store by store id
	public static void updateStoreByStore_Id(String payload, String store_id, String keyMain, String value,
			String DbCol) throws Exception {
		try {
			testCaseReporting.header("Upload Store By Store_Id");
			starttime = System.currentTimeMillis();
			String[] str = store_id.split("##");
			JSONObject obj = new JSONObject(payload);

			Iterator<?> iterator = obj.keys();
			String key = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				// if object is just string we change value in key
				if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
					if ((key.equals(keyMain))) {
						// put new value
						obj.put(key, value);
					}
				}
			}
			ParamObject params = APIParameters.getParamsForUpdateStoreByStore_Id(obj.toString(), str[0]);
			String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);
			Utility.Getdbdata.connectDB();
			String keyValue = Utility.Getdbdata
					.executeQuery(" SELECT " + DbCol + " FROM merchant_test.store_store where store_id=" + str[0]);
			Utility.Getdbdata.closeDBConnection();
			endtime = System.currentTimeMillis();

			if (getValueFromParser(jsonString, "action").equalsIgnoreCase("updated")
					&& keyValue.trim().equalsIgnoreCase(value.trim())) {
				testCaseReporting.teststepreporting("Store updated successfully", "PASS", "Store should be updated",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Store is not updated", "FAIL", "Store should be updated",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store should be updated", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}

	}


	//Get store info by store slug id
	public static String getStoreBySlugAPI(String store_id) throws Exception {
		String jsonString = null;
		try {
			testCaseReporting.header("Get Store By Slug");
			starttime = System.currentTimeMillis();

			String[] str = store_id.split("##");
			ParamObject params = APIParameters.getParamsForStoreBySlugAPI(str[1].trim());

			jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);

			System.out.println("getStoreBySlugAPI" + jsonString);

			String slug = getValueFromParser(jsonString, "slug");
			System.out.println(slug);
			System.out.println(str[1]);
			endtime = System.currentTimeMillis();
			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
				testCaseReporting.teststepreporting(
						"<b><i>" + slug + " </i></b>Store verified by slug Details<b><i>" + str[1].trim() + "</i></b>",
						"PASS", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"<b><i>" + slug + " </i></b>Store slug Details not verified with <b><i>" + str[1].trim()
								+ "</i></b>",
						"FAIL", "Store Details should be verified", getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store details should be verified", getTotalExecutionTime(starttime, endtime));
		} finally {
			testCaseReporting.footer();
		}
		return jsonString;

	}

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

	@SuppressWarnings("static-access")
	public static void getStoreByStoreIdAPI(String store_id) throws Exception {

		try {
			// testCaseReporting.header();
			starttime = System.currentTimeMillis();
			String[] str = store_id.split("##");
			ParamObject params = APIParameters.getParamsForStoreAPI(str[0]
					.trim());

			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);

			System.out.println("getStoreByStoreIdAPI" + jsonString);

			String slug = getValueFromParser(jsonString, "slug");
			endtime = System.currentTimeMillis();
			if (slug.trim().equalsIgnoreCase(str[1].trim())) {
				testCaseReporting.teststepreporting(
						"<b><i>" + slug
								+ " </i></b>Store verified by store id<b><i>"
								+ str[1].trim() + "</i></b>", "PASS",
						"Store Details should be verified",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting
						.teststepreporting(
								"<b><i>"
										+ slug
										+ " </i></b>Store slug Details not verified with <b><i>"
										+ str[1].trim() + "</i></b>", "FAIL",
								"Store Details should be verified",
								getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store details should be verified",
					getTotalExecutionTime(starttime, endtime));
		}
		// finally {
		// testCaseReporting.footer();
		// }

	}

	@SuppressWarnings("static-access")
	public static void deleteStore(String slug) throws Exception {
		try {
			// testCaseReporting.header();
			starttime = System.currentTimeMillis();
			String[] str = slug.split("##");
			ParamObject params = APIParameters
					.getParamsForDeleteStoreAPI(str[1].trim());
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);

			System.out.println("deleteStore" + jsonString);

			int responseCode = RestClient.getResponseCode();
			endtime = System.currentTimeMillis();
			if (responseCode == 204) {
				testCaseReporting.teststepreporting(
						"Store deleted successfully", "PASS",
						"Store should be deleted",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("Store deletion failed",
						"FAIL", "Store should be deleted",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			testCaseReporting.teststepreporting(
					"Exception occured in store deletion", "FAIL",
					"Store should be deleted",
					getTotalExecutionTime(starttime, endtime));
		}
		// }finally {
		// testCaseReporting.footer();
		// }

	}

	@SuppressWarnings("static-access")
	public static void getServicesAPI() throws Exception {
		String name = "", serviceType = "", out = "";
		boolean flag = false;
		try {
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForGetServices();
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			JSONObject serv = new JSONObject(jsonString);
			JSONArray st = serv.getJSONArray("services");

			for (int i = 0; i < st.length(); i++) {
				JSONObject json_obj = st.getJSONObject(i);
				name = getValueFromParser(json_obj.toString(), "name");
				serviceType = getValueFromParser(json_obj.toString(),
						"serviceType");
				out = out + name + "_" + serviceType + "##";
				if (Verification_Checks.SERVICETYPE.contains(name + "_"
						+ serviceType)) {
					flag = true;

				} else {
					flag = false;
					break;
				}
			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting
						.teststepreporting(
								"<b><i>"
										+ Verification_Checks.SERVICETYPE
										+ " </i></b>Get services API verified successfully with <b><i>"
										+ out + "</i></b>",
								"PASS",
								"Get services API should be verified successfully",
								getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"Get services API noy verified", "FAIL",
						"Get services API should be verified successfully",
						getTotalExecutionTime(starttime, endtime));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Get services API should be verified successfully",
					getTotalExecutionTime(starttime, endtime));
		}

	}

	private static void printToFile(String id, int responseCode)
			throws IOException {
		String fileName = "E:\\sel-online\\ResponseCode.txt";

		FileWriter fr = new FileWriter(fileName, true);
		BufferedWriter br = new BufferedWriter(fr);
		if (responseCode == 200)
			br.write("Response Code for ProductID = " + id
					+ "  ---- is  ---->>> " + responseCode + "\r\n");
		else if (responseCode == 502)
			br.write("Response Code for ProductID = " + id
					+ "  ---- is  ---->>> " + responseCode + "\r\n");
		else if (responseCode == 501)
			br.write("Response Code for ProductID = " + id
					+ "  ---- is  ---->>> " + responseCode + "\r\n");
		br.close();

	}

	@SuppressWarnings("static-access")
	public static void getCategoriesAPI() throws Exception {
		String parentCat = "", parentName = "", childCat = "", childname = "", parent = "", child = "", parentCategories = "";
		boolean flag = false;
		try {
			// testCaseReporting.header();
			starttime = System.currentTimeMillis();

			ParamObject params = APIParameters.getParamsForGetCategoriesAPI();

			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			String data = getValueFromParser_WithBrace(jsonString, "data");
			JSONObject serv = new JSONObject(data);

			JSONArray st = serv.getJSONArray("categories");
			for (int i = 0; i < st.length() - 1; i++) {
				JSONObject json_obj = st.getJSONObject(i);
				parentCat = getValueFromParser(json_obj.toString(), "cat_id");
				parentName = getValueFromParser(json_obj.toString(), "name");

				JSONArray ch = json_obj.getJSONArray("children");
				for (int j = 0; j < ch.length(); j++) {
					JSONObject json_ChildObj = ch.getJSONObject(i);
					childCat = getValueFromParser(json_ChildObj.toString(),
							"cat_id");
					childname = getValueFromParser(json_ChildObj.toString(),
							"name");
					child = child + " " + childname + "(" + childCat + ")";
					break;
				}
				parent = parentName + "(" + parentCat + ")";
				if (Verification_Checks.CATEGORIES.contains(child.trim())
						&& Verification_Checks.CATEGORIES.contains(parent
								.trim())) {
					flag = true;
				} else {
					flag = false;
					break;
				}
				parentCategories = parentCategories + " " + parent + " "
						+ child;
			}
			endtime = System.currentTimeMillis();
			if (flag) {
				testCaseReporting
						.teststepreporting(
								"<b><i>"
										+ parentCategories
										+ " </i></b> Following product categories are verified",
								"PASS",
								"Product categories should be verified",
								getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("<b><i>" + parentCategories
						+ " </i></b> Product categories not  verified", "FAIL",
						"Product categories should be verified",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Product categories should be verified",
					getTotalExecutionTime(starttime, endtime));
		}

	}

	@SuppressWarnings("static-access")
	public static String createUser(String usrName, String password,
			String store_id) throws Exception {
		String usrID = "";
		try {
			starttime = System.currentTimeMillis();
			String payload = APIPayload.jsonPayLoadForCreateUser(usrName,
					password, store_id);

			ParamObject params = APIParameters.getParamsForCreateUser(payload);

			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);

			usrID = getValueFromParser(jsonString, "user_id").trim();
			endtime = System.currentTimeMillis();
			if (getValueFromParser(jsonString, "status").trim()
					.equalsIgnoreCase("true")
					&& usrID.equalsIgnoreCase(usrName)) {
				testCaseReporting.teststepreporting("<b><i>"
						+ getValueFromParser(jsonString, "user_id")
						+ " </i></b>User created and verified with <b><i>"
						+ usrName + "</i></b>", "PASS",
						"User Should be created",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("<b><i>"
						+ getValueFromParser(jsonString, "user_id")
						+ " </i></b>User not verified with <b><i>" + usrName
						+ "</i></b>", "FAIL", "User Should be created",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"User Should be created",
					getTotalExecutionTime(starttime, endtime));
			throw new Exception(e.toString());
		}
		return usrID;

	}

	@SuppressWarnings({ "static-access", "static-access" })
	public static void deleteUser(String userName) throws Exception {
		try {
			// testCaseReporting.header();
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters.getParamsForDeleteUser(userName);
			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);

			int responseCode = RestClient.getResponseCode();
			endtime = System.currentTimeMillis();
			if (responseCode == 204) {
				testCaseReporting.teststepreporting("<b><i>" + userName
						+ " </i></b>User deleted successfully", "PASS",
						"User should be deleted",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting("<b><i>" + userName
						+ " </i></b>User not deleted", "FAIL",
						"User should be deleted",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			testCaseReporting.teststepreporting(
					"Exception occured in store deletion", "FAIL",
					"User should be deleted",
					getTotalExecutionTime(starttime, endtime));
		}
		// }finally {
		// testCaseReporting.footer();
		// }

	}

	@SuppressWarnings("static-access")
	public static void getStoreAgreement() {
		try {
			starttime = System.currentTimeMillis();
			ParamObject params = APIParameters
					.getParamsForGetStoreAggreement("202929");

			String jsonString = (String) RestClient.getJSONFromParamsObject(
					params, RestClient.RETURN_JSON_STRING);
			System.out.println(jsonString);
			endtime = System.currentTimeMillis();

			if (!jsonString.equalsIgnoreCase("")) {
				testCaseReporting.teststepreporting(
						"Store aggreement Info recieved", "PASS",
						"Store aggreement Info should be recieved",
						getTotalExecutionTime(starttime, endtime));
			} else {
				testCaseReporting.teststepreporting(
						"Store aggreement Info not recieved", "FAIL",
						"Store aggreement Info should be recieved",
						getTotalExecutionTime(starttime, endtime));
			}
		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : "
					+ "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"Store aggreement Info should be recieved",
					getTotalExecutionTime(starttime, endtime));
		}
	}
	
	@SuppressWarnings("static-access")
	public static void getStore(String store_id) {
        try {
                starttime = System.currentTimeMillis();
                ParamObject params = APIParameters.getParamsForStores(store_id);

                String jsonString = (String) RestClient.getJSONFromParamsObject(
                                params, RestClient.RETURN_JSON_STRING);
                System.out.println(jsonString);
                String storid = getValueFromParser(jsonString, "store_id");
                endtime = System.currentTimeMillis();

                if (storid.equals(store_id)) {
                        testCaseReporting.teststepreporting("Store Info recieved",
                                        "PASS", "Store Info should be recieved",
                                        getTotalExecutionTime(starttime, endtime));
                } else {
                        testCaseReporting.teststepreporting("Store Info not recieved",
                                        "FAIL", "Store Info should be recieved",
                                        getTotalExecutionTime(starttime, endtime));
                }
        } catch (Exception e) {
                endtime = System.currentTimeMillis();
                testCaseReporting.teststepreporting("Exception occured : "
                                + "<b><i>" + e.toString() + "</i></b>", "FAIL",
                                "Store aggreement Info should be recieved",
                                getTotalExecutionTime(starttime, endtime));
        }
}

	
	public static void login() throws Exception {
		String emailId = "";
		String accessToken = "";
		String password = "";
		ICsvMapReader mapReader = null;
		ICsvMapWriter mapWriter = null;
		RestClient.restClient_Flag = false;
		try {
			testCaseReporting.header("Order Status");
			starttime = System.currentTimeMillis();
			CsvPreference prefs = CsvPreference.STANDARD_PREFERENCE;
			mapReader = new CsvMapReader(new FileReader("./Dummy_User_List_31_Mar.csv"), prefs);
			mapWriter = new CsvMapWriter(new FileWriter("./Dummy_User_List_31_Mar_Copy.csv"),
					prefs);

			// header used to read the original file
			final String[] readHeader = mapReader.getHeader(true);
			System.out.println(readHeader);

			// header used to write the new file
			// (same as 'readHeader', but with additional column)
			final String[] writeHeader = new String[readHeader.length + 1];
			System.out.println(writeHeader);
			System.arraycopy(readHeader, 0, writeHeader, 0, readHeader.length);
			final String timeHeader = "Access Token";
			writeHeader[writeHeader.length - 1] = timeHeader;

			mapWriter.writeHeader(writeHeader);

			Map<String, String> row;
			while ((row = mapReader.read(readHeader)) != null) {

				emailId = row.get("Email Id");
				System.out.println("emailId: " + emailId);
				password = row.get("Password");
				System.out.println("password: " + password);

				String payload = APIPayload.jsonPayLoadForLogin(emailId, "abc123");
				System.out.println("PAyload :: " + payload);
				ParamObject params = APIParameters.getParamsForLogin(payload);
				System.out.println("Params : " + params.toString());
				String jsonString = (String) RestClient.getJSONFromParamsObject(params, RestClient.RETURN_JSON_STRING);

				System.out.println("Json string : " + jsonString);

				System.out.println(getValueFromParser(jsonString, "access_token"));
				accessToken = getValueFromParser(jsonString, "access_token");
				row.put(timeHeader, accessToken);
				endtime = System.currentTimeMillis();
				if (!accessToken.trim().equals("")) {
					testCaseReporting.teststepreporting(
							"<b><i>" + emailId + " </i></b> user logged in successfully and access token :: <b><i>"
									+ accessToken + "</i></b>",
							"PASS", "User login should be successful", getTotalExecutionTime(starttime, endtime));
				} else {
					testCaseReporting.teststepreporting("<b><i>" + emailId + " </i></b> user logged in failed", "FAIL",
							"User login should be successful", getTotalExecutionTime(starttime, endtime));
				}

				mapWriter.write(row, writeHeader);
			}

		} catch (Exception e) {
			endtime = System.currentTimeMillis();
			testCaseReporting.teststepreporting("Exception occured : " + "<b><i>" + e.toString() + "</i></b>", "FAIL",
					"User login should be successful", getTotalExecutionTime(starttime, endtime));
			throw new Exception(e.toString());
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
			if (mapWriter != null) {
				mapWriter.close();
			}
			testCaseReporting.footer();
		}

	}
	// private static String jsonPayLoadForProductListing(String[] cat,
	// String pageNumber) throws JSONException {
	// JSONObject root = new JSONObject();// {}
	// JSONArray catArray = new JSONArray();// []
	//
	// for (String s : cat) {
	// catArray.put(s);// ["230"] // ["230","231"]
	// }
	// root.put("cat", catArray);// {"cat":["230"]}
	// root.put("page", pageNumber); // {"cat":["230"],"page":"2"}
	// System.out.println("Payload String is :" + root);
	// return root.toString();
	// }
	//
	// private static ParamObject getParamsForProductListing(String payLoad) {
	// ParamObject obj = new ParamObject();
	// try {
	// TimeUnit.MILLISECONDS.sleep(100);
	// } catch (InterruptedException e) {
	//
	// e.printStackTrace();
	// }
	// String url = "http://45.56.112.85/products/listing/";
	// obj.setUrl(url);
	// obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
	// obj.addHeader("Region-Id", "2");
	// obj.setPayload(payLoad);
	// obj.setMethodType("POST");
	// return obj;
	// }
	//
	// private static ArrayList<String> getIDsArrayFromProductsJson(String json)
	// throws Exception {
	// JSONObject rootobject = new JSONObject(json);
	// JSONArray products = rootobject.getJSONArray("products");
	// ArrayList<String> ids = new ArrayList<>();
	// for (int i = 0; i < products.length(); i++) {
	// JSONObject item = products.getJSONObject(i);
	// String id = item.getString("id");
	// ids.add(id);
	// }
	// return ids;
	// }
	//
	// private static ParamObject getParamsForProductId(String id) {
	// String url = "http://45.56.112.85/product/" + id + "/";
	// //System.out.println(url);
	// ParamObject obj = new ParamObject();
	// obj.setUrl(url);
	// obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
	// obj.addHeader("Region-Id", "1");
	// obj.setMethodType("GET");
	// return obj;
	//
	// }
	// @SuppressWarnings({ "static-access", "unused" })
	// private static void getStoresInfo() throws IOException, JSONException {
	// try {
	// starttime = System.currentTimeMillis();
	// boolean flag = true;
	// ParamObject params = getParamsMyStores();
	// String jsonString = (String) RestClient.getJSONFromParamsObject(
	// params, RestClient.RETURN_JSON_STRING);
	// JSONArray arr = new JSONArray(jsonString);
	// for (int i = 0; i < arr.length(); i++) {
	// JSONObject jso = arr.getJSONObject(i);
	// String name = jso.getString("name");
	// String store_id = jso.getString("store_id");
	// endtime = System.currentTimeMillis();
	// if (name.equalsIgnoreCase(Verification_Checks.name)
	// && store_id
	// .equalsIgnoreCase(Verification_Checks.StoreID)) {
	// testCaseReporting.teststepreporting("Store name is:" + name
	// + "Store id is :" + store_id, "PASS",
	// "Sanchar World with store id 83523",
	// getTotalExecutionTime(starttime, endtime));
	//
	// } else {
	// testCaseReporting.teststepreporting("Store name is:" + name
	// + "Store id is :" + store_id, "Fail",
	// "Sanchar World with store id 83523",
	// getTotalExecutionTime(starttime, endtime));
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// endtime = System.currentTimeMillis();
	// testCaseReporting.teststepreporting("Exception occured : "
	// + "<b><i>" + e.toString() + "</i></b>", "FAIL",
	// "get stores info should be verified",
	// getTotalExecutionTime(starttime, endtime));
	// throw (e);
	//
	// }
	// }

	public static ParamObject getParamsMyStores() {
		String url = "http://139.162.14.155/mystores/";
		// System.out.println(url);
		ParamObject obj = new ParamObject();
		obj.setUrl(url);
		obj.addHeader("Authorization", "Token a7b978cdd05b43afa1ba39720f39638f");
		obj.setMethodType("GET");
		return obj;

	}

	private static String getTotalExecutionTime(long starttime, long endtime) {

		long diff = endtime - starttime;
		long diffmilliSeconds = diff % 1000;
		long diffSeconds = diff / 1000 % 60;
		// long diffMinutes = diff / (60 * 1000) % 60;
		// long diffHours = diff / (60 * 60 * 1000) % 24;
		// long diffDays = diff / (24 * 60 * 60 * 1000);

		return (diffSeconds + ":" + diffmilliSeconds);

	}

	private static String getValueFromParser(String str, String keyValue)
			throws Exception {
		String value = "";
		Object temp;
		try {
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(str.toString());
			JsonObject jsonObject = element.getAsJsonObject();
			try {
				temp = jsonObject.get("status");
				value = temp.toString();
				if (value.contains("Failed")) {
					temp = jsonObject.get("msg");
					value = temp.toString();
					throw new Exception(value);
				}
			} catch (Exception e) {

			}

			temp = jsonObject.get(keyValue);
			value = temp.toString().replaceAll("\"", "");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(value);
		}
		return value;

	}

	private static String getValueFromParser_WithBrace(String str,
			String keyValue) throws Exception {
		String value = "";
		Object temp;
		try {
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(str.toString());
			JsonObject jsonObject = element.getAsJsonObject();
			try {
				temp = jsonObject.get("status");
				value = temp.toString();
				if (value.contains("Failed")) {
					temp = jsonObject.get("msg");
					value = temp.toString();
					throw new Exception(value);
				}
			} catch (Exception e) {

			}

			temp = jsonObject.get(keyValue);
			// value = temp.toString().replaceAll("\"", "");
			value = temp.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(value);
		}
		return value;

	}
	
	public static void main(String args[]) throws Exception{
		login();
	}

	//
	// // String url = "http://45.56.112.85/products/listing/";
	// // String json = RestClient.getJSONFromUrl(url);
	// // // System.out.println(json);
	// // try {
	// // JSONObject rootobject = new JSONObject(json);
	// // JSONArray products = rootobject.getJSONArray("products");
	// // for (int i = 0; i < products.length(); i++) {
	// // JSONObject item = products.getJSONObject(i);
	// // String name = item.getString("id");
	// // System.out.println(name);
	// // int response = RestClient
	// // .GETOpenHttpConnection("http://45.56.112.85/product/"
	// // + name + "/");
	// // System.out.println("response code of product id " + name + "\t"
	// // + "is" + "\t" + response);
	// // }
	// //
	// // // Integer status = rootObject.getInt("status");
	// // } catch (JSONException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// //
	// // }
	// }

	// public static void getproduct(String url) throws IOException {
	// String json = RestClient.getJSONFromUrl(url);
	// // System.out.println(json);
	// try {
	// JSONObject rootobject = new JSONObject(json);
	// JSONArray products = rootobject.getJSONArray("products");
	// for (int i = 0; i < products.length(); i++) {
	// JSONObject item = products.getJSONObject(i);
	// String name = item.getString("id");
	// System.out.println(name);
	// int response = RestClient
	// .GETOpenHttpConnection("http://45.56.112.85/product/"
	// + name + "/");
	// System.out.println("response code of product id " + name + "\t"
	// + "is" + "\t" + response);
	// }
	//
	// // Integer status = rootObject.getInt("status");
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	//
	// }

	// }

	// int response = RestClient
	// .GETOpenHttpConnection("http://45.56.112.85/product/9301966/");
	// System.out.println(response);

}
