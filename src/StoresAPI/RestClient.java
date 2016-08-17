package StoresAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;

import org.json.JSONObject;

//import com.android.ddmlib.Log;

//import com.android.vantageLogManager.Logger;

//import android.util.Log;

public class RestClient {
	

	public static final int RETURN_RESPONSE_CODE = 1;
	public static final int RETURN_JSON_STRING = 2;
	static URLConnection conn = null;
    static HttpURLConnection httpConn = null;

	// constructor
	public RestClient() {

	}

	public static Object getJSONFromParamsObject(ParamObject obj, int returnType)
			throws IOException {

		InputStream in = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {

			switch (returnType) {
			case RETURN_RESPONSE_CODE:
				return OpenHttpConnection(obj, returnType);
			}
			in = (InputStream) OpenHttpConnection(obj, returnType);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			json = sb.toString();
			// System.out.println(json);
		} catch (Exception e) {
//			Log.e("Buffer Error", "Error converting result " + e.toString());
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return json;

	}

	private static Object OpenHttpConnection(ParamObject params, int returnType)
			throws IOException {
		InputStream in = null;

//		Log.d("palval", "OpenHttpConnection");

		int response = -1;

		URL url = new URL(params.getUrl());

		 conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {

			httpConn = (HttpURLConnection) conn;
			// httpConn.setAllowUserInteraction(false);
			// httpConn.setInstanceFollowRedirects(true);
			// httpConn.setConnectTimeout(60000);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod(params.getMethodType());

			for (Entry<String, String> entry : params.headers.entrySet()) {
				// System.out.println(entry.getKey() + ":" + entry.getValue());
				httpConn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestProperty("Content-type", "application/json");
			// int i=2;
			// String s=Integer.toString(i);
			// System.out.println(s);
			//

			// int i=1;
			if ("POST".equals(params.methodType)) {

				String payload = params.getPayload();

				System.out.println("Writing Payload :" + payload);

				// String payload = "{\"cat\":[\"230\"],\"page\":\"3\"}";

				OutputStreamWriter writer = new OutputStreamWriter(
						httpConn.getOutputStream(), "UTF-8");
				writer.write(payload.toString());
				writer.close();
				int responseCode = httpConn.getResponseCode();
				System.out.println(responseCode);
			} else {

			}
			httpConn.connect();
			int responseCode = httpConn.getResponseCode();

			switch (returnType) {
			case RETURN_RESPONSE_CODE:
				return responseCode;
			}

			in = httpConn.getInputStream();

		}

		catch (Exception ex) {
			ex.printStackTrace();

		}
		return in;

	}
	
	static int getResponseCode() throws IOException {
        return httpConn.getResponseCode();
}

	// private static InputStream OpenHttpConnection(String urlString)
	// throws IOException {
	// InputStream in = null;
	//
	// Log.d("palval", "OpenHttpConnection");
	//
	// int response = -1;
	//
	// URL url = new URL(urlString);
	//
	// URLConnection conn = url.openConnection();
	//
	// if (!(conn instanceof HttpURLConnection))
	// throw new IOException("Not an HTTP connection");
	//
	// try {
	//
	// HttpURLConnection httpConn = (HttpURLConnection) conn;
	// // httpConn.setAllowUserInteraction(false);
	// // httpConn.setInstanceFollowRedirects(true);
	// // httpConn.setConnectTimeout(60000);
	// for (int i = 2; i < 4; i++) {
	// // for(int y=0;y<i;y++){
	// // RestMain.getproduct("http://45.56.112.85/products/listing/");
	// // }
	//
	// httpConn.setDoOutput(true);
	// httpConn.setDoInput(true);
	// httpConn.setRequestMethod("POST");
	// httpConn.setRequestProperty("Client-Key",
	// "cb5a4b9e5de0ee57647aed56f9295546");
	// httpConn.setRequestProperty("Region-Id", "1");
	// httpConn.setRequestProperty("Accept", "application/json");
	// httpConn.setRequestProperty("Content-type", "application/json");
	// // int i=2;
	// // String s=Integer.toString(i);
	// // System.out.println(s);
	// //
	//
	// // int i=1;
	//
	// String payload = "{\"cat\":[\"230\"],\"page\":\"" + i + "\"}";
	// System.out.println(payload);
	//
	// // String payload = "{\"cat\":[\"230\"],\"page\":\"3\"}";
	//
	// OutputStreamWriter writer = new OutputStreamWriter(
	// httpConn.getOutputStream(), "UTF-8");
	// writer.write(payload);
	// writer.close();
	//
	// // JSONObject jobj = new JSONObject();
	// // jobj.put("page","1");
	// httpConn.connect();
	// // response = httpConn.getResponseCode();
	// // Logger.error("Connected to Stream", "YES");
	// // if (response == HttpURLConnection.HTTP_OK) {
	// in = httpConn.getInputStream();
	//
	// }
	// // System.out.println(payload);
	//
	// // }
	// // System.out.println(in);
	//
	// }
	//
	// catch (Exception ex) {
	// Log.e("Internet Connecting Exception", "Unable To Connect");
	//
	// }
	// return in;
	//
	// }
	//
	// public static int GETOpenHttpConnection(String urlString)
	// throws IOException {
	//
	// Log.d("palval", "OpenHttpConnection");
	// InputStream in = null;
	// int response = -1;
	//
	// URL url = new URL(urlString);
	// URLConnection conn = url.openConnection();
	//
	// if (!(conn instanceof HttpURLConnection))
	// throw new IOException("Not an HTTP connection");
	//
	// try {
	// HttpURLConnection httpConn = (HttpURLConnection) conn;
	// // httpConn.setAllowUserInteraction(false);
	// // httpConn.setInstanceFollowRedirects(true);
	// // httpConn.setConnectTimeout(60000);
	// httpConn.setRequestMethod("GET");
	// httpConn.setRequestProperty("Client-Key",
	// "cb5a4b9e5de0ee57647aed56f9295546");
	// httpConn.setRequestProperty("Region-Id", "1");
	// httpConn.setRequestProperty("Longitude", "77.3848031");
	// httpConn.setRequestProperty("Latitude", "28.6266412");
	// httpConn.connect();
	// response = httpConn.getResponseCode();
	// // Logger.error("Connected to Stream", "YES");
	// // if (response == HttpURLConnection.HTTP_OK) {
	// // in = httpConn.getInputStream();
	// // }
	// //
	// } catch (Exception ex) {
	// Log.e("Internet Connecting Exception", "Unable To Connect");
	// }
	//
	// return response;
	// }
	//
	// public static String getJSONFromUrl(String url) throws IOException {
	//
	// InputStream in = null;
	// JSONObject jObj = null;
	// String json = "";
	// // Making HTTP request
	// try {
	//
	// in = OpenHttpConnection(url);
	//
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// in, "utf-8"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// in.close();
	// json = sb.toString();
	// System.out.println(json);
	// } catch (Exception e) {
	// Log.e("Buffer Error", "Error converting result " + e.toString());
	// } finally {
	// if (in != null) {
	// in.close();
	// }
	// }
	//
	// return json;
	//
	// }

	// public static String GetrequestJSON(String url) {
	//
	// InputStream in = null;
	// JSONObject jObj = null;
	// String json = "";
	// // Making HTTP request
	// try {
	//
	// in = OpenHttpConnection(url);
	//
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// in, "utf-8"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// in.close();
	// json = sb.toString();
	// // System.out.println(json);
	// } catch (Exception e) {
	// Log.e("Buffer Error", "Error converting result " + e.toString());
	// }
	//
	// return json;
	//
	// }

}
