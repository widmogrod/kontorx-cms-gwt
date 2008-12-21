package info.widmogrod.gwt.library.client.ui.interfaces;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;

public interface RenderCallback <W extends Widget,T extends JavaScriptObject> {
	public void onRender(W widget, T model);
}
