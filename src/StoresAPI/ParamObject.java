package StoresAPI;

import java.util.HashMap;

import org.json.JSONArray;

public class ParamObject {

	String methodType;
	HashMap<String, String> headers = new HashMap<>();
	String payload;
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public void addHeader(String key, String value){
		headers.put(key, value);
	}
}

