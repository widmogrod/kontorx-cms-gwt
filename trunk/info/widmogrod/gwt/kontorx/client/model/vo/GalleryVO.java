package info.widmogrod.gwt.kontorx.client.model.vo;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class GalleryVO extends JavaScriptObject {
	public static final String FIELD_CATEGORY_ID = "gallery_category_id";

	protected GalleryVO() {}
	public final native int getId() /*-{ return this.id; }-*/;
	public final native void setId(int id) /*-{ this.id = id; }-*/;

	public final native String getName() /*-{ return this.name; }-*/;
	public final native void setName(String name) /*-{ this.name = name; }-*/;

	public final native String getTimeCreate() /*-{ return this.t_create; }-*/;
	public final native int getUserId() /*-{ return this.user_id; }-*/;
	
	public final native String getUrl() /*-{ return this.url; }-*/;
	public final native void setUrl(String url) /*-{ this.url = url; }-*/;
	
	public final native boolean getPublicated() /*-{ return this.publicated == 1 ? true : false; }-*/;
	public final native void setPublicated(boolean publicated) /*-{ this.publicated = publicated; }-*/;
	
	public final native int getCategoryId() /*-{ return this.gallery_category_id; }-*/;
	public final native void setCategoryId(int id) /*-{ this.gallery_category_id = id; }-*/;

	public final native boolean getVisible() /*-{ return this.visible; }-*/;
	public final native boolean getStyle() /*-{ return this.style; }-*/;
	
	public final HashMap<String, Object> getData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", getId());		
		map.put("name", getName());
		map.put("t_create", getTimeCreate());
		map.put("user_id", getUserId());
		map.put("publicated", getPublicated() ? "1" : "0");
		map.put("visible", getVisible());
		map.put("url", getUrl());
		map.put("style", getStyle());
		return map;
	}

	public final static GalleryVO get() {
		JSONObject o = new JSONObject();
		return (GalleryVO) o.getJavaScriptObject();
	}
}