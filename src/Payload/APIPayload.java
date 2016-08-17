package Payload;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIPayload {

	//Order status API
	public static String jsonPayLoadForOrderStatusAPI(String store_Id, String status) throws JSONException {
//		 JSONObject root = new JSONObject();
//		 root.put("store_id", store_Id);
//		 root.put("retailer_status", status);
//		 System.out.println("Payload String is :" + root);
//		 return root.toString();
		return "store_id=" + store_Id + "&retailer_status=" + status;
	}

	public static String jsonPayLoadForProductListing(String[] cat, String pageNumber) throws JSONException {
		JSONObject root = new JSONObject();// {}
		JSONArray catArray = new JSONArray();// []

		for (String s : cat) {
			catArray.put(s);// ["230"] // ["230","231"]
		}
		root.put("cat", catArray);// {"cat":["230"]}
		root.put("page", pageNumber); // {"cat":["230"],"page":"2"}
		System.out.println("Payload String is :" + root);
		return root.toString();
	}

	public static String jsonPayLoadForLoginAPI() throws JSONException {
		JSONObject root = new JSONObject();
		root.put("username", "new-gen-retail");
		root.put("password", "xxq"); 
		return root.toString();
	}
	
	//New
	public static String jsonPayLoadForVerifyStoresAPI() throws JSONException {
		JSONArray payloadArray = new JSONArray();
		JSONObject root = new JSONObject();
		root.put("is_verified", "true");
		root.put("slug", "ramdev-home-appliances"); 
		payloadArray.put(root);
		//System.out.println("Payload String is :" + payloadArray);
		//return root.toString();
		return payloadArray.toString();
	}

	//Payload for new store creation
	public static String jsonPayLoadForStoreCreation(String[] phn_nos, String[] contactPerson, String[] wrkngDy)
			throws JSONException {
		Random rnd = new Random();
		String randStr = Long.toHexString(System.currentTimeMillis());
		JSONObject root = new JSONObject();// {}
		JSONObject child = new JSONObject();
		JSONObject startTime = new JSONObject();
		JSONObject endTime = new JSONObject();
		JSONObject timeSlot = new JSONObject();
		JSONObject children = new JSONObject();
		JSONObject location = new JSONObject();
		JSONObject brand = new JSONObject();
		JSONObject categories = new JSONObject();
		JSONObject storeTiming = new JSONObject();
		JSONArray catArray = new JSONArray();// []
		JSONArray contPersonArray = new JSONArray();
		JSONArray timeSlotArray = new JSONArray();
		JSONArray services = new JSONArray();
		JSONArray brandArray = new JSONArray();
		JSONArray childrenArray = new JSONArray();
		JSONArray categoriesArray = new JSONArray();
		JSONArray workingDays = new JSONArray();

		root.put("name", "Test Store " + randStr);
		root.put("address", "Test Address " + randStr);
		root.put("locality", "Test Locality " + randStr);
		root.put("landmark", "Test LandMark " + randStr);
		root.put("pincode", 100000 + rnd.nextInt(900000));
		root.put("state", "Uttar Pradesh");
		root.put("city", "Lucknow");

		// phone_nos Array string
		for (int i = 0; i < phn_nos.length; i++) {
			catArray.put(child.put("phone_number", phn_nos[i]));
		}
		root.put("phone_nos", catArray);

		root.put("email", "TestStore@" + randStr + ".com");
		root.put("owner", "Test Owner " + randStr);

		// ContactPerson Array string
		for (String s : contactPerson) {
			contPersonArray.put(s);// ["230"] // ["230","231"]
		}
		root.put("contactPerson", contPersonArray);

		// working days
		for (String s : wrkngDy) {
			workingDays.put(s);// ["230"] // ["230","231"]
		}

		// Start time
		startTime.put("time", "10:30");
		startTime.put("meridian", "AM");
		timeSlot.put("start", startTime);

		// End time
		endTime.put("time", "8:00");
		endTime.put("meridian", "PM");
		timeSlot.put("end", endTime);

		// Time slot array
		timeSlotArray.put(timeSlot);

		// Store timing array
		storeTiming.put("workingDays", workingDays);
		storeTiming.put("timeSlot", timeSlotArray);
		root.put("storeTiming", storeTiming);

		// Location array
		location.put("lat", "14.12");
		location.put("lng", "12.34");
		root.put("location", location);

		// service array
		services.put("Self");
		services.put("warranty");
		root.put("services", services);

		root.put("agreement", true);

		// brand array
		brand.put("name", "TestCategory" + randStr);
		brandArray.put(brand);

		// children array
		children.put("name", "TestCategory" + randStr);
		children.put("cat_id", 2);
		children.put("brand", brandArray);
		childrenArray.put(children);

		// Categories array
		categories.put("name", "TestCategory" + randStr);
		categories.put("cat_id", 1);
		categories.put("children", childrenArray);
		categoriesArray.put(categories);
		root.put("categories", categoriesArray);
		System.out.println(root.toString());
		return root.toString();
	}

	//Get Warrant amount API
	public static String jsonPayLoadForCalculateWarrantyAmount(String cat, String price, String duration)
			throws JSONException {
//		 JSONObject root = new JSONObject();// {}
//		
//		 root.put("product_category_id", cat);
//		 root.put("product_price", price);
//		 root.put("duration", duration);
//		 System.out.println("Payload String is :" + root);
		return "product_category_id=" + cat + "&product_price=" + price + "&duration=" + duration;

//		 return root.toString();
	}

	//Generate shipment API
	public static String jsonPayLoadForShipmentHandler(String type, String orderItems, String storeId)
			throws JSONException {
//		JSONObject root = new JSONObject();// {}
//		root.put("type", type);
//		root.put("order_items", orderItems);
//		root.put("store_id", storeId);
//		System.out.println("Payload String is :" + root);
//
//		return root.toString();
		return "type=" + type + "&order_items=" + orderItems + "&store_id=" + storeId;
	}

	//Deliver order API
	public static String jsonPayLoadForOrderDeliveryStatus(String shipmentId, String shipmentStatus, String storeId)
			throws JSONException {
//		JSONObject root = new JSONObject();// {}
//		root.put("shipment_id", shipmentId);
//		root.put("shipment_status", shipmentStatus);
//		root.put("store_id", storeId);
//		System.out.println("Payload String is :" + root);

//		return root.toString();
		
		return "shipment_id=" + shipmentId + "&shipment_status=" + shipmentStatus + "&store_id=" + storeId;
	}

	//Sell query API
	public static String jsonPayLoadForSellQuery(String productId,String storeId,String sPrice) throws JSONException {
//		 JSONObject root = new JSONObject();// {}
//		 root.put("product_id", productId);
//		 root.put("store_id", storeId);
//		 root.put("price", sPrice);
//		
//		 return root.toString();
		return "store_id=" + storeId + "&price=" +sPrice + "&product_id=" + productId;
	}

	public static String jsonPayLoadForGenerateWarrantyHelpDeskTicket() throws JSONException {
		JSONObject root = new JSONObject();// {}
		System.out.println("Payload String is :" + root);

		return root.toString();
	}

	public static String jsonPayLoadForCreateNewInvoice(String prod_Name, String serial_No, String name, String email,
			String cat_id, String cost, String purchase_date, String contact_no, String has_warrant, String duration,
			String warranty_serial, String amount, String warranty_purchase_date) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("product_name", prod_Name);
		root.put("serial_no", serial_No);
		root.put("name", name);
		root.put("email", email);
		root.put("cat_id", cat_id);
		root.put("cost", cost);
		root.put("purchase_date", purchase_date);
		root.put("contact_no", contact_no);
		root.put("has_warrant", has_warrant);
		if (has_warrant.equalsIgnoreCase("true")) {
			root.put("duration", duration);
			root.put("warranty_serial", warranty_serial);
			root.put("amount", amount);
			root.put("warranty_purchase_date", warranty_purchase_date);
		}
		System.out.println("Payload String is :" + root);

		return root.toString();
	}
	
	public static String jsonPayLoadForUploadInvoiceImage(String imageURL) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("invoice_image", imageURL);
		System.out.println("Payload String is :" + root);

		return root.toString();
	}
	
	public static String jsonPayLoadForAddCart(String prod_Id,String store_Id) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("product_id", prod_Id);
		root.put("store_id", store_Id);

		return root.toString();
	}
	
	public static String jsonPayLoadForVerifyStore(String slug) throws JSONException {
		JSONObject root = new JSONObject();// {}
		JSONArray catArray = new JSONArray();
		root.put("slug", slug);
		root.put("is_verified", "true");
		catArray.put(root);

		return catArray.toString();
	}
	
	//Create new user Payload
	public static String jsonPayLoadForCreateUser(String usrName,String password,String store_id) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("username", usrName);
		root.put("password", password);
		if(!store_id.equals(""))
		root.put("store_id", store_id);

		return root.toString();
	}

	//Get store listing by multiple arguments
	public static String jsonPayLoadForStoreListing(String store_Name,String store_id,String phoneNo,String city,String is_verified,String date_range,String lat,String lng) throws JSONException {
		JSONObject root = new JSONObject();// {}
		if(!store_Name.equalsIgnoreCase(""))
		root.put("name", store_Name);
		if(!store_id.equalsIgnoreCase(""))
		root.put("store_id", store_id);
		if(!phoneNo.equalsIgnoreCase(""))
		root.put("phoneNos", phoneNo);
		if(!city.equalsIgnoreCase(""))
		root.put("city", city);
		if(!is_verified.equalsIgnoreCase(""))
		root.put("is_verified", is_verified);
		if(!date_range.equalsIgnoreCase(""))
		root.put("date_range", date_range);
		if(!lat.equalsIgnoreCase(""))
		root.put("lat", lat);
		if(!lng.equalsIgnoreCase(""))
		root.put("lng", lng);
		
		return root.toString();
	}
	
	public static String jsonPayLoadForSellQuery_Retail(String prod_Id,String prod_Title,String store_id,String cat_Id,int base_Price,int msp_price,int sp_price,int price_exp_time) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("product_id", prod_Id);
		root.put("product_title", prod_Title);
		root.put("store", store_id);
		root.put("category", cat_Id);
		root.put("base_price", base_Price);
		root.put("msp_price", msp_price);
		root.put("sp_price", sp_price);
		root.put("price_exp_time", price_exp_time);
		System.out.println(root.toString());
		return root.toString();
	}
	
	//Purge price by product id and store id
	public static String jsonPayLoadForPurgePriceByStore(String prod_id) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("product_id", prod_id);
		
		return root.toString();
	}
	
	//Purge all prices by store id payload
	public static String jsonPayLoadForPurgeAllPriceByStore() throws JSONException {
		JSONObject root = new JSONObject();// {}
		
		return root.toString();
	}
	
	public static String jsonPayLoadForVerifyPrice(Boolean sts) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("is_verified", sts);
		System.out.println(root.toString());
		return root.toString();
	}
	
	//Change purge time by category id payload
	public static String jsonPayLoadForChangePurgeTimeOfLeafCategory(int purgeTime) throws JSONException {
		JSONObject root = new JSONObject();// {}
		root.put("purge_time", purgeTime);
		return root.toString();
	}
	
	public static String jsonPayLoadForLogin(String User_Id, String Password) throws JSONException {
//		JSONObject root = new JSONObject();
//		root.put("email", User_Id);
//		root.put("password", Password);
//		return root.toString();
		return "email=" + User_Id + "&password=" + Password;
	}
}
