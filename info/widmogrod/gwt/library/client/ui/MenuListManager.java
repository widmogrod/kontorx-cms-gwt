package info.widmogrod.gwt.library.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class MenuListManager<T extends JavaScriptObject> extends Composite {

	MenuBar itemList;
	MenuItem mainMenuItem;

	public MenuListManager() {
		MenuBar menuBar = new MenuBar();
		initWidget(menuBar);

		// przechowuje liste elementow
		itemList = new MenuBar(true);
		// etykiete
		mainMenuItem = new MenuItem(null, itemList);
		menuBar.addItem(mainMenuItem);
		
		setStyleName("kx-MenuListManager");
	}

	public void setText(String name) {
		mainMenuItem.setText(name);
	}

	private JsArray<T> model;
	
	public void setModel(JsArray<T> model) {
		this.model = model;
	}

	public JsArray<T> getModel() {
		return model;
	}

	private Command cmd;

	public void setCommand(Command cmd) {
		this.cmd = cmd;
	}

	private MenuListRenderCallback<T> renderCallback;
	
	public void setRenderCallback(MenuListRenderCallback<T> callback) {
		this.renderCallback = callback;
	}

	public void render() throws NullPointerException {
		if (renderCallback == null) {
			throw new NullPointerException("RenderCallback is not set");
		}

		itemList.clearItems();

		JsArray<T> model = getModel();
		for (int i = 0; i < model.length(); i++) {
			MenuItem item = renderCallback.onRender(model.get(i));
			item.setCommand(cmd);
			itemList.addItem(item);
		}
	}
}
