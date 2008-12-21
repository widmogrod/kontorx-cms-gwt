package info.widmogrod.gwt.kontorx.client.model.vo;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class CategoryVO extends JavaScriptObject {
	protected CategoryVO() {}
	public final native int getId() /*-{ return this.id; }-*/;
	public final native void setId(int id) /*-{ this.id = id; }-*/;

	public final native String getName() /*-{ return this.name; }-*/;
	public final native void setName(String name) /*-{ this.name = name; }-*/;

	public final native String getUrl() /*-{ return this.url; }-*/;
	public final native void setUrl(String url) /*-{ this.url = url; }-*/;
	
	public final native boolean getPublicated() /*-{ return this.publicated == 1 ? true : false; }-*/;
	public final native void setPublicated(boolean publicated) /*-{ this.publicated = publicated; }-*/;

	public final HashMap<String, Object> getData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", getId());		
		map.put("name", getName());
		map.put("url", getUrl());
		map.put("publicated", getPublicated() ? "1" : "0");
		return map;
	}

	public final static CategoryVO get() {
		JSONObject o = new JSONObject();
		return (CategoryVO) o.getJavaScriptObject();
	}
}