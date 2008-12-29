package info.widmogrod.gwt.library.client.db.xmlrpc;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class JsonDataRowset<T extends JavaScriptObject> {
	private String url;

	public JsonDataRowset(String url) {
		this.url = url;
	}

	public void findAll(final AsyncCallback<ArrayList<T>> callback) {
		RequestBuilder r = new RequestBuilder(RequestBuilder.POST, url);
		r.setCallback(new RequestCallback() {
			@SuppressWarnings("unchecked")
			public void onResponseReceived(Request request, Response response) {
				JSONValue v = JSONParser.parse(response.getText());
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
			public void onError(Request request, Throwable exception) {
				callback.onFailure(exception);
			}
		});
		
		try {
			r.send();
		} catch (RequestException e) {
			callback.onFailure(e);
		}
	}
}
