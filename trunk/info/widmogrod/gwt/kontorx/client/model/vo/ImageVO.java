package info.widmogrod.gwt.kontorx.client.model.vo;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class ImageVO extends JavaScriptObject {
	public static final String FIELD_GALLERY_ID = "gallery_id";

	protected ImageVO() {}
	public final native int getId() /*-{ return this.id; }-*/;
	public final native void setId(int id) /*-{ this.id = id; }-*/;

	public final native String getImage() /*-{ return this.image; }-*/;
	public final native void setImage(String image) /*-{ this.image = image; }-*/;
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native void setName(String name) /*-{ this.name = name; }-*/;
	
	public final native String getDescription() /*-{ return this.description; }-*/;
	public final native void setDescription(String description) /*-{ this.description = description; }-*/;

	public final native int getUserId() /*-{ return this.user_id; }-*/;
	
	public final native int getGalleryId() /*-{ return this.gallery_id; }-*/;
	public final native void setGalleryId(int id) /*-{ this.gallery_id = id; }-*/;

	public final native boolean getPublicated() /*-{ return this.publicated == 1 ? true : false; }-*/;
	public final native void setPublicated(boolean publicated) /*-{ this.publicated = publicated; }-*/;
	
	public final native boolean getVisible() /*-{ return this.visible; }-*/;
	public final native boolean getStyle() /*-{ return this.style; }-*/;
	
	public final HashMap<String, Object> getData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", getId());		
		map.put("image", getImage());
		map.put("name", getName());
		map.put("description", getDescription());
		map.put("user_id", getUserId());
		map.put("gallery_id", getGalleryId());
		map.put("publicated", getPublicated() ? "1" : "0");
		map.put("visible", getVisible());
		return map;
	}

	public final static ImageVO get() {
		JSONObject o = new JSONObject();
		return (ImageVO) o.getJavaScriptObject();
	}
}