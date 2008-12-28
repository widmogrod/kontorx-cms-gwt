package info.widmogrod.gwt.library.client.db.xmlrpc;

import java.util.ArrayList;
import java.util.HashMap;

import com.fredhat.gwt.xmlrpc.client.XmlRpcClient;
import com.fredhat.gwt.xmlrpc.client.XmlRpcRequest;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class XmlRpcDbTableDecorator<T extends JavaScriptObject> {

	private XmlRpcClient client;
	private String proxy = null;

	public XmlRpcDbTableDecorator(String xmlRpcClientUrl, String proxy) {
		client = new XmlRpcClient(xmlRpcClientUrl);
		this.proxy = proxy;
	}

	public void findAll(final AsyncCallback<ArrayList<T>> callback) {
		String methodName = proxy + ".findAll";
		Object[] params = new Object[] {};

		XmlRpcRequest<String> request = new XmlRpcRequest<String>(client, methodName, params, new AsyncCallback<String>() {

			@SuppressWarnings("unchecked")
			public void onSuccess(String response) {
				JSONValue v = JSONParser.parse(response);
				JSONArray a = v.isArray();
				
				ArrayList<T> rowset = new ArrayList<T>();

				if (a != null) {
					JsArray<T> jsArray = (JsArray<T>) a.getJavaScriptObject();
					for (int i = 0; i < jsArray.length(); i++) {
						rowset.add(jsArray.get(i));
					}
				}

				callback.onSuccess(rowset);
			}

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});

		request.execute();
	}

	public void findById(int id, final AsyncCallback<T> callback) {
		String methodName = proxy + ".findById";
		Integer[] params = new Integer[] {id};

		XmlRpcRequest<String> request = new XmlRpcRequest<String>(client, methodName, params, new AsyncCallback<String>() {

			@SuppressWarnings("unchecked")
			public void onSuccess(String response) {
				JSONValue v = JSONParser.parse(response);
				JSONObject o = v.isObject();
				
				T row = null;
				if (o != null) {
					row = (T) o.getJavaScriptObject();
				}
				callback.onSuccess(row);
			}

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});

		request.execute();
	}

	public void insert(HashMap<String, Object> data, final AsyncCallback<Integer> callback) {
		String methodName = proxy + ".insert";
		Object[] params = new Object[] {data};

		XmlRpcRequest<Integer> request = new XmlRpcRequest<Integer>(client, methodName, params, callback);

		request.execute();
	}

	public void update(int id, HashMap<String, Object> data, final AsyncCallback<Object> callback) {
		String methodName = proxy + ".update";
		Object[] params = new Object[] {id, data};

		XmlRpcRequest<Object> request = new XmlRpcRequest<Object>(client, methodName, params, callback);

		request.execute();
	}
	
	public void update(ArrayList<Integer>id, HashMap<String, Object> data, final AsyncCallback<Object> callback) {
		String methodName = proxy + ".update";
		Object[] params = new Object[] {id, data};

		XmlRpcRequest<Object> request = new XmlRpcRequest<Object>(client, methodName, params, callback);

		request.execute();
	}

	public void delete(int id, final AsyncCallback<Object> callback) {
		String methodName = proxy + ".delete";
		Object[] params = new Object[] {id};

		XmlRpcRequest<Object> request = new XmlRpcRequest<Object>(client, methodName, params, callback);

		request.execute();
	}
	
	public void delete(ArrayList<Integer>id, final AsyncCallback<Object> callback) {
		String methodName = proxy + ".delete";
		Object[] params = new Object[] {id};

		XmlRpcRequest<Object> request = new XmlRpcRequest<Object>(client, methodName, params, callback);

		request.execute();
	}
}
