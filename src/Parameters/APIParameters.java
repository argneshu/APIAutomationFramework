package Parameters;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import StoreAPI.ParamObject;

public class APIParameters {
	// static String URL="http://139.162.7.202";
	static String URL = "http://139.162.14.155";
//	static String URL = "http://45.56.112.85";
	static String URL_Retailer = "http://139.162.6.154";

	
	// static String URL = "http://139.162.6.154";

	static String xAPIVersion = "1.1";
	static String accessToken = "ffc08b95d9ea451d308d35c44545100986691911";
	// static String accessToken = "7e4c3d5bb54ff7b97aa9777f47f82a9bd52dc65d";

	// static String authorization = "Token a7b978cdd05b43afa1ba39720f39638f";
	static String authorization = "Token ffc08b95d9ea451d308d35c44545100986691911";
	static String clientKey = "cb5a4b9e5de0ee57647aed56f9295546";
	static String region_id = "2";
	static String client_Info = "";
	static String latitude = "";
	static String longitude = "";

	//Get warrant enabled category
	public static ParamObject getParamsForWarrantyEnabledCategoriesAPI() {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/warranty/categories/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	public static ParamObject getParamsForLoginAPI(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/rms/user/login/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		// obj.addHeader("Device-Id", "34434323323333");
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Order status API
	public static ParamObject getParamsForOrderStatusAPI(String order_id, String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/orders/" + order_id + "/confirm_retailer_status/";
		System.out.println(url);
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Order list API
	public static ParamObject getParamsForOrderListAPI(String pageSize, String pageNo, String store_id, String status) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/orders/?page_size=" + pageSize + "&page=" + pageNo + "&status="
				+ status + "&store_id=" + store_id;
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	//Get order details by order id, store id and order status
	public static ParamObject getParamsForOrderDetailAPI(String order_id, String store_id, String status) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/orders/" + order_id + "/?store_id=" + store_id + "&status=" + status;
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	//Parameters to get info by store id
	public static ParamObject getParamsForStoreAPI(String store_id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/" + store_id + "/";
		obj.setUrl(url);
		// obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	//Parameters for get info by store slug id
	public static ParamObject getParamsForStoreBySlugAPI(String Slug) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/" + Slug + "/";
		obj.setUrl(url);
		// obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	//Get categories parameters
	public static ParamObject getParamsForGetCategoriesAPI() {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/category/brand/map";
		obj.setUrl(url);
		// obj.addHeader("Access-Token", accessToken);
		// obj.setPayload(payLoad);
		obj.setMethodType("GET");
		return obj;
	}

	public static ParamObject getParamsMyStores() {
		String url = URL + "/mystores/";
		System.out.println(url);
		ParamObject obj = new ParamObject();
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Authorization", authorization);
		obj.setMethodType("GET");
		return obj;

	}
	
	
	public static ParamObject getParamsForProductListing(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = "http://45.56.112.85/products/listing/";
		obj.setUrl(url);
		obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
		obj.addHeader("Region-Id", "2");
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}
	

//	public static ParamObject getParamsForProductListing(String payLoad) {
//		ParamObject obj = new ParamObject();
//		try {
//			TimeUnit.MILLISECONDS.sleep(100);
//		} catch (InterruptedException e) {
//
//			e.printStackTrace();
//		}
//		String url = URL + "/products/listing/";
//		obj.setUrl(url);
//		obj.addHeader("Client-Key", clientKey);
//		obj.addHeader("Region-Id", region_id);
//		obj.setPayload(payLoad);
//		obj.setMethodType("POST");
//		return obj;
//	}

	//Get all invoices
	public static ParamObject getParamsAllInvoices() {
		String url = URL_Retailer + "/rms/warranty/invoice/";
		System.out.println(url);
		ParamObject obj = new ParamObject();
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setMethodType("GET");
		System.out.println(obj.toString());
		return obj;

	}

	public static ParamObject getParamsInvoiceDetails(String invoice_id) {
		String url = URL + "/rms/invoice/" + invoice_id + "/";
		System.out.println(url);
		ParamObject obj = new ParamObject();
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setMethodType("GET");
		return obj;

	}
	
	//Parameters for New store creation
	public static ParamObject getParamsForCreateNewStoreAPI(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Parameters to delete store by store id
	public static ParamObject getParamsForDeleteStoreAPI(String slug) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/" + slug + "/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Authorization", authorization);
		// obj.setPayload(payLoad);
		obj.setMethodType("DELETE");
		return obj;
	}

	//Get Warrant amount API
	public static ParamObject getParamsForGetWarrantyAmount(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/warranty/invoice/calculate_warranty_amount/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Generate shipment API
	public static ParamObject getParamsForShipmentHandler(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/shipment/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Deliver order API
	public static ParamObject getParamsForOrderDeliveryStatus(String payLoad, String orderID) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/shipment/" + orderID + "/delivery_status/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Sell query API
	public static ParamObject getParamsForSellQuery(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/retailer/sellquery/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		// obj.addHeader("Accept", "application/x-www-form-urlencoded");
		// obj.addHeader("Content-type", "application/x-www-form-urlencoded");
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForGenerateWarrantyHelpDeskTicket(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/warranty/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForCreateNewInvoice(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/invoice/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForUploadInvoiceImage(String payLoad, String invoice_ID) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL_Retailer + "/rms/warranty/invoice/" + invoice_ID + "/upload/";
		obj.setUrl(url);
		obj.addHeader("X-Api-Version", xAPIVersion);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForAddCart(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/cart/";
		obj.setUrl(url);
		obj.addHeader("Client-Info", client_Info);
		obj.addHeader("Access-Token", accessToken);
		obj.addHeader("Client-Key", clientKey);
		obj.addHeader("Region-Id", region_id);
		obj.addHeader("Latitude", latitude);
		obj.addHeader("Longitude", accessToken);
		obj.addHeader("x-api-version", xAPIVersion);

		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForGetCart() {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		URL = "http://45.56.112.85";
		String url = URL + "/cart/";
		obj.setUrl(url);
		obj.addHeader("Client-Info", "com.zopperapp.dev(10100)");
		obj.addHeader("Access-Token", "qnhkd4s48b1tvyr9kf96tknldozhkkr5");
		obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
		obj.addHeader("Region-Id", "1");
		obj.addHeader("Latitude", "28.626641");
		obj.addHeader("Longitude", "77.384803");
		obj.addHeader("x-api-version", "1.4");

		obj.setMethodType("GET");
		return obj;
	}

	public static ParamObject getParamsForVerifyStore(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/verify/";
		obj.setUrl(url);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Create new user API Parameter
	public static ParamObject getParamsForCreateUser(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/accounts/users/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Delete user API parameter
	public static ParamObject getParamsForDeleteUser(String UserName) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/accounts/users/" + UserName + "/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setMethodType("DELETE");
		return obj;
	}

	//Get services API parameter
	public static ParamObject getParamsForGetServices() {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/services/";
		obj.setUrl(url);
		// obj.addHeader("Authorization", authorization);
		obj.setMethodType("GET");
		return obj;
	}

	//Get store listing by multiple arguments
	public static ParamObject getParamsForStoreListing(String payLoad) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/search/";
		obj.setUrl(url);
		obj.addHeader("Access-Token", accessToken);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForSellQuery_Retailer(String payLoad, String store_Id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/" + store_Id + "/addprice/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Get products price by store id
	public static ParamObject getParamsForGetPricesByStore(String store_Id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/" + store_Id + "/showprices/?type=active";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setMethodType("GET");
		return obj;
	}

	//Purge price by store id and product id
	public static ParamObject getParamsForPurgePriceByStore(String payLoad, String store_Id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/" + store_Id + "/purgeprice/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Purge all prices by store id parameter
	public static ParamObject getParamsForPurgeAllPriceByStore(String payLoad, String store_Id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/" + store_Id + "/purgeallprices/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	//Get Price verification data info API parameter
	public static ParamObject getParamsForGetPriceVerficationData() {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/priceverify/";
		obj.setUrl(url);
		// obj.addHeader("Authorization", authorization);
		obj.setMethodType("GET");
		return obj;
	}

	public static ParamObject getParamsForVerifyPrice(String payload, String instance_id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/priceverify/" + instance_id + "/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payload);
		obj.setMethodType("PUT");
		return obj;
	}

	//Change purge time by category id parameter
	public static ParamObject getParamsForChangePurgeTimeOfLeafCategory(String payload, String cat_id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/category/" + cat_id + "/";
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payload);
		obj.setMethodType("PUT");
		return obj;
	}

	//Get multi store info by store ids parameter
	public static ParamObject getParamsForMultipleStoreInfo(String store_ids) {
		ParamObject obj = new ParamObject();
		String url = URL + "/multistoresinfo/?store_ids=";
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String[] str = store_ids.split(",");
		for (int i = 0; i < str.length; i++) {
			if (i == 0)
				url = url + str[i];
			else
				url = url + "," + str[i];
		}
		obj.setUrl(url);
		obj.addHeader("Authorization", authorization);
		obj.setMethodType("GET");
		return obj;
	}
	
	//Parameters to update store by store id
	public static ParamObject getParamsForUpdateStoreByStore_Id(String payLoad,String store_id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/store/"+store_id+"/";
		obj.setUrl(url);
//		obj.addHeader("Content-Type", "application/json");
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		System.out.println(payLoad);
		obj.setMethodType("PUT");
		return obj;
	}
	
	//Parameters to update store by slug id
	public static ParamObject getParamsForUpdateStoreBySlug(String payLoad,String store_id) {
		ParamObject obj = new ParamObject();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String url = URL + "/stores/"+store_id+"/";
		obj.setUrl(url);
//		obj.addHeader("Content-Type", "application/json");
		obj.addHeader("Authorization", authorization);
		obj.setPayload(payLoad);
		System.out.println(payLoad);
		obj.setMethodType("PUT");
		return obj;
	}

	public static ParamObject getParamsForStoreLogin(String payLoad) {
		ParamObject obj = new ParamObject();
		String url = APIParameters.URL + "/accounts/login/";
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		obj.setUrl(url);
		obj.setPayload(payLoad);
		obj.setMethodType("POST");
		return obj;
	}

	public static ParamObject getParamsForProductId(String id) {
		String url = "http://45.56.112.85/product/" + id + "/";
		// System.out.println(url);
		ParamObject obj = new ParamObject();
		obj.setUrl(url);
		obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
		obj.addHeader("Region-Id", "1");
		obj.setMethodType("GET");
		return obj;

	}

//	 public static ParamObject getParamsForProductId() {
//         String url = "http://45.56.112.85/product/9313735/";
//         // System.out.println(url);
//         ParamObject obj = new ParamObject();
//         obj.setUrl(url);
//         obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
//         obj.addHeader("Region-Id", "1");
//         obj.addHeader("Longitude", "77.3848031");
//         obj.addHeader("Latitude", "28.6266412");
//         
//         obj.setMethodType("GET");
//         return obj;
//
// }
	 
	 /*****************************************/
	 
		//New
		public static ParamObject getParamsForGetListOfStates() {
			String url = APIParameters.URL + "/states/";
			// System.out.println(url);
			ParamObject obj = new ParamObject();
			obj.setUrl(url);
			obj.setMethodType("GET");
			return obj;

		}

		//New
		public static ParamObject getParamsForGetListOfCities(String id) {
			String url = APIParameters.URL + "/states/" + id + "/cities/";
			// System.out.println(url);
			ParamObject obj = new ParamObject();
			obj.setUrl(url);
			obj.setMethodType("GET");
			return obj;

		}

		//New
		public static ParamObject getParamsVerifyStore(String payload) {
			String url = APIParameters.URL + "/stores/verify";
			System.out.println(url);
			// System.out.println(url);
			ParamObject obj = new ParamObject();
			obj.setUrl(url);
			obj.addHeader("Authorization",
					"Token ffc08b95d9ea451d308d35c44545100986691911");
			obj.setPayload(payload);
			obj.setMethodType("POST");
			return obj;

		}

		
		//New
		public static ParamObject getParamsForGetStoreAggreement(String store_id) {
			ParamObject obj = new ParamObject();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			String url = URL + "/store/" + store_id + "/agreement/";
			obj.setUrl(url);

			obj.setMethodType("GET");
			return obj;
		}

		//New
		public static ParamObject getParamsForStores(String store_id) {
			ParamObject obj = new ParamObject();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			String url = URL + "/getstore/?store_id=" + store_id;
			obj.setUrl(url);

			obj.setMethodType("GET");
			return obj;
		}


		//New
		public static ParamObject getParamsStoreByRegion() {
			String url = APIParameters.URL + "/storebyregion/?region_id=3";
			System.out.println(url);
			// System.out.println(url);
			ParamObject obj = new ParamObject();
			obj.setUrl(url);
			obj.setMethodType("GET");
			return obj;

		}

		//New
		public static ArrayList<String> getIDsArrayFromProductsJson(String json)
				throws Exception {
			JSONObject rootobject = new JSONObject(json);
			JSONArray products = rootobject.getJSONArray("products");
			ArrayList<String> ids = new ArrayList<>();
			for (int i = 0; i < products.length(); i++) {
				JSONObject item = products.getJSONObject(i);
				String id = item.getString("id");
				ids.add(id);
			}
			return ids;
		}

		//New
		public static ArrayList<String> getIDsArrayFromStates(String json)
				throws JSONException {
			JSONArray rootarray = new JSONArray(json);
			ArrayList<String> ids = new ArrayList<>();
			for (int i = 0; i < rootarray.length(); i++) {
				JSONObject obj = rootarray.getJSONObject(i);
				String id = obj.getString("id");
				ids.add(id);
			}
			return ids;

		}

		public static ParamObject getParamsForLogin(String payLoad) {
			ParamObject obj = new ParamObject();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			String url = "https://api.zopper.com/login/email/";
			System.out.println(url);
			obj.setUrl(url);
			obj.addHeader("Client-Key", "cb5a4b9e5de0ee57647aed56f9295546");
			obj.addHeader("Device-Id", "C99DE4DC586DE98A");
			obj.addHeader("Client-Info", "com.zopperapp.staging(13301)");
			obj.addHeader("Device-Info", "0123456789ABCDEF");
			obj.addHeader("X-Api-Version", "1.7");
			obj.setPayload(payLoad);
			obj.setMethodType("POST");
			return obj;
		}
	 
	 
	 
}
