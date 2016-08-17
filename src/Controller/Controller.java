package Controller;

import StoreAPI.RestMain;
import Utility.SendAPIExecutionReport;
import testCaseReporting.SuiteReporting;
import testCaseReporting.TestCaseReporting;

public class Controller {
	static SuiteReporting suitreporting = new SuiteReporting("APIAutomationSuite");
	static TestCaseReporting testCaseReporting = new TestCaseReporting("Test");

	static long starttime;
	static long endtime;

	public static void main(String[] args) throws Exception {
		try {
			// Change text file name
			// ConsolePrinter.testConsole();
			String slug = RestMain.createNewStore();

			// Above concatenated store id and slug is splited internally in
			// below method to get slug
			String payload = RestMain.getStoreBySlugAPI(slug);
			RestMain.updateStoreByStore_Id(payload, slug, "owner", "Arvind Katheriya", "owner");
			RestMain.updateStoreBySlug(payload, slug, "name", "Arvind", "name");

			// Above concatenated store id and slug is splited internally
			// below method to get store id
			RestMain.getStoreByStoreIdAPI(slug);

			// Above concatenated store id and slug is splited internally
			// below method to get slug
			RestMain.deleteStore(slug);
			RestMain.getCategoriesAPI();
			String userName = RestMain.createUser("User_" + Long.toHexString(System.currentTimeMillis()), "123", "");
			RestMain.deleteUser(userName);
			RestMain.getServicesAPI();

			RestMain.getPricesByStore("81307");
			RestMain.purgePriceByStore("9313323", "81307");
			RestMain.purgeAllPriceByStore("203313");
			RestMain.getPriceVerficationData();
			RestMain.changePurgeTimeForLeafCategory("230", 400);
			RestMain.multipleStoreInfo("203528");

			/*
			 * Arg[1]=store_Name, Arg[2]=store_id, Arg[3]=phoneNo, Arg[4]=city,
			 * Arg[5]=is_verified, Arg[6]=date_range, Arg[7]=lat, Arg[8]=lng
			 * Below API works only for 1 value at a time
			 */
			RestMain.storeListing("", "81307", "", "", "", "", "", "");

			// =====================================Retailer App
			// API======================================================

			/*
			 * Arg[1]=order_id, Arg[2]=store_id, Arg[3]=status Returns order
			 * item
			 */
//			String order_Item = RestMain.getOrderDetailsAPI("52080074", "203313", "DELIVERED"); // DONE1

			String order_Item = RestMain.getOrderDetailsAPI("41357688", "84706", "DELIVERED"); // DONE1

			/*
			 * Arg[1]=order_id, Arg[2]=store_id, Arg[3]=status Form Data Payload
			 */

			RestMain.getOrderStatus("41357688", "84706", "ACCEPTED"); // Done 2

//			RestMain.getOrderStatus("52080074", "203313", "ACCEPTED"); // Done 2

			/*
			 * Arg[1]=type, Arg[2]=order_item, Arg[3]=store_id Form Data Payload
			 */
//			RestMain.getShipmentHandler("3pl", "11771", "203313"); // Done 3
			RestMain.getShipmentHandler("self", "173663", "203956"); // Done 3

			/*
			 * Arg[1]=Shipment_id, Arg[2]=status, Arg[3]=store_id,
			 * Arg[4]=order_id Form Data Payload
			 */

//			RestMain.getOrderDeliveryStatus("52080074_1", "DELIVERED", "203313", "52080074");// Done4
			RestMain.getOrderDeliveryStatus("18784980_1  ", "DELIVERED", "73118", "18784980");// Done4

			/*
			 * Arg[1]=pageSize, Arg[2]=pageNo, Arg[3]=store_id, Arg[4]=status
			 */
			RestMain.getOrderListAPI("10", "1", "83523", "DELIVERED"); // DONE

			/* Arg[1]=price, Arg[2]=duration */
			RestMain.getWarrantyEnabledCategoriesAPI("20000", "1"); // DONE

			/*
			 * Arg[1]=cat_id, Arg[2]=price, Arg[3]=duration Form Data Payload
			 */
			RestMain.getWarrentyAmount("207", "20000", "1"); // Done
			RestMain.getAllInvoices(); // Done-------No invoice found
			/*
			 * Arg[1]=productId, Arg[2]=storeId, Arg[3]=sPrice Form Data Payload
			 */
			RestMain.getSellQuery("9316162", "81307", "100000"); // Done
			// RestMain.generateWarrantyHelpDeskTicket();

			// createNewInvoice(prod_Name, serial_No, name, email, cat_id, cost,
			// purchase_date, contact_no, has_warrant, duration,
			// warranty_serial, amount, warranty_purchase_date);
			// uploadInvoiceImage(imageURL, invoice_ID);
			// getCart();
			
			/**************************/
			
			String Store_id = "203313";


				// suitreporting = new SuiteReporting("Suite");
//				testCaseReporting.header();
			//	getStoresInfo();
				RestMain.getProducts();

				RestMain.storeLoginAPI();
				RestMain.getListOfStates();
				RestMain.getListOfCities();
				
				//verifyStoreAPI();
				RestMain.getStoresByRegion();
	//slug is concatenation of Store id + ## + slug
				slug = RestMain.createNewStore();
	//Above concatenated store id and slug is splited internally in below method to get slug
				RestMain.getStoreBySlugAPI(slug);
	//Above concatenated store id and slug is splited internally in below method to get store id
				RestMain.getStoreByStoreIdAPI(slug);
	//Above concatenated store id and slug is splited internally in below method to get slug
				RestMain.deleteStore(slug);
				RestMain.getServicesAPI();
				RestMain.getCategoriesAPI();
				
				userName = RestMain.createUser("User_" + Long.toHexString(System.currentTimeMillis()),"123", "");
				RestMain.deleteUser(userName);
				RestMain.getStoreAgreement();
				RestMain.getStore(Store_id);
				RestMain.login();
			
			
		} catch (Exception e) {
			e.getMessage();
		} finally {
			suitreporting.consolidateResultFooter();
			SendAPIExecutionReport sReport=new SendAPIExecutionReport();
			sReport.sendMail();
			testCaseReporting.openReport();
		}
	}
}
