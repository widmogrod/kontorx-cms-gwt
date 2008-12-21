package info.widmogrod.gwt.library.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DropDownMenu extends Composite implements ClickListener, PopupListener {
	private ToggleButton button;
	private PopupPanel panel;
	private VerticalPanel verticalPanel;

	public DropDownMenu(String name) {
		VerticalPanel layout = new VerticalPanel();
		initWidget(layout);

		button = new ToggleButton(name, this);
		layout.add(button);

		panel = new PopupPanel(true);
		panel.addPopupListener(this);
		
		verticalPanel = new VerticalPanel();
		panel.add(verticalPanel);
		
		setStyleName("kx-DropDownList");
	}

	public void add(Widget widget) {
		verticalPanel.add(widget);
	}

	public void addClickListner(ClickListener listner) {
		button.addClickListener(listner);
	}

	private boolean open = false;

	public void setOpen(boolean open) {
		this.open = open;

		if (open) {
			button.setDown(true);
			panel.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + 15);
			panel.show();
		} else {
			button.setDown(false);
			panel.hide();
		}
	}

	public boolean isOpen() {
		return open;
	}

	public void onClick(Widget sender) {
		if (button.isDown()) {
			open = true;
			panel.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + 15);
			panel.show();
		} else {
			open = false;
			panel.hide();
		}
	}

	public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
		// nie moze byc wywolana metoda setOpen
		// dlatego ze popup moze byc zamkniety poprzez
		// klikniecie na obszar obok, zatem ..
		// ToggleButton pozostanie wcisniety .. 
		this.open = false;
		button.setDown(false);
	}

	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}
}
