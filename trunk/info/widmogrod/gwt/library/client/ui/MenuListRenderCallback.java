package info.widmogrod.gwt.library.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.MenuItem;

public interface MenuListRenderCallback<T extends JavaScriptObject> {
	public MenuItem onRender(T model);
}
