package info.widmogrod.gwt.library.client.ui.list;

import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderInterface;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CheckBoxList <T extends JavaScriptObject> extends Composite implements RenderInterface, ClickListener {

	private HorizontalPanel horizontalPanel;

	private CheckBox checkBox;

	private Label label;

	private boolean checked = false;

	public CheckBoxList(final CheckBoxListManager<T> manager) {
		horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);

		setStyleName("kx-CheckBoxList");
		
		checkBox = new CheckBox();
		checkBox.addClickListener(this);
		horizontalPanel.add(checkBox);
		
		label = new Label();
		label.setStyleName("kx-CheckBoxList-Label");
		label.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// klikniecie na label, odznacza inne zaznaczenia
				manager.setChecked(false);
			}
		});
		label.addClickListener(this);
		horizontalPanel.add(label);
		horizontalPanel.setCellWidth(label, "100%");
	}
	
	public void render() {
		if (renderCallback != null) {
			renderCallback.onRender(this, model);
		}
	}
	
	public void refresh() {
		render();
	}

	public void setText(String name) {
		label.setText(name);
	}

	private T model = null;
	
	public void setModel(T model) {
		this.model = model;
	}

	public T getModel() {
		return model;
	}

	ClickListener clickListener;
	
	public void setClickListner(ClickListener listener) {
		clickListener = listener;
	}

	RenderCallback<CheckBoxList<T>, T> renderCallback;

	public void setRenderCallback(RenderCallback<CheckBoxList<T>, T> callback) {
		renderCallback = callback;
	}

	public void onClick(Widget sender) {
		// zmiana statusu kliknięty
		setChecked(!isChecked());

		if (clickListener != null) {
			clickListener.onClick(this);
		}
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		checkBox.setChecked(checked);

		if (checked) {
			addStyleName("checked");
		} else {
			removeStyleName("checked");
		}
	}

	public boolean isChecked() {
		return checked;
	}

	public void remove() {
		removeFromParent();
	}
}