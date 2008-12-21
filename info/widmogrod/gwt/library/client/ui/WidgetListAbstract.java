package info.widmogrod.gwt.library.client.ui;

import info.widmogrod.gwt.library.client.db.DataModel;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

abstract public class WidgetListAbstract<W extends Widget, T extends JavaScriptObject> extends Composite {
	protected HashMap<T, W> list = new HashMap<T, W>();

	abstract public  void render();

	abstract public void refresh();

	private RenderCallback<W, T> renderCallback;
	
	public void setRenderCallback(RenderCallback<W, T> callback) {
		renderCallback = callback;
	}
	
	public RenderCallback<W, T> getRenderCallback() {
		return renderCallback;
	}
	
	private DataModel<T> model = null;

	public void setModel(DataModel<T> model) {
		this.model = model;
	}

	public ArrayList<T> getModel() {
		return model.getModel();
	}
}
