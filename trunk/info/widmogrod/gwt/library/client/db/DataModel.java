package info.widmogrod.gwt.library.client.db;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;

public interface DataModel<E extends JavaScriptObject> {
	public ArrayList<E> getModel();
}
