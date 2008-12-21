package info.widmogrod.gwt.library.client.ui;

import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

public class ImageXList<T extends JavaScriptObject> extends WidgetListAbstract<Image, T> {

	private FlowPanel panel;
	
	public ImageXList(RenderCallback<Image, T> renderCallback) {
		setRenderCallback(renderCallback);

		panel = new FlowPanel();
		initWidget(panel);
		
		setStyleName("kx-ImageList");
	}
	
	@Override
	public void refresh() {
		panel.clear();
		RenderCallback<Image, T> renderCallback = getRenderCallback();
		for (T row : getModel()) {
			Image widget;
			// obiekcik modelu nie ma wizualizacji, tworzymy!
			if (!list.containsKey(row)) {
				widget = new Image();
				list.put(row, widget);
			} else {
				widget = list.get(row);
			}

			renderCallback.onRender(widget, row);

			list.put(row, widget);
			panel.add(widget);
		}
	}

	@Override
	public void render() {
		list.clear();
		panel.clear();

		RenderCallback<Image, T> renderCallback = getRenderCallback();

		for (T row : getModel()) {
			Image widget = new Image();

			renderCallback.onRender(widget, row);

			list.put(row, widget);
			panel.add(widget);
		}
	}
}
