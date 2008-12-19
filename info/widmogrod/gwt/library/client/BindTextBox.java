package info.widmogrod.gwt.library.client;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BindTextBox implements Bindable, ChangeListener {
	TextBox widget;

	public BindTextBox(TextBox widget) {
		this.widget = widget;
		this.widget.addChangeListener(this);
	}
	
	public BindTextBox(TextBox widget, BindableListener listner) {
		this.widget = widget;
		this.widget.addChangeListener(this);
		setBindableListener(listner);
	}

	private Binding binding;

	public void setBinding(Binding binding) {
		this.binding = binding;
	}

	public void setValue(Object value) {
		if (listner != null) {
			value = listner.onSet(value, this);
		}
		this.widget.setText((String) value);
	}

	public Object getValue() {
		return this.widget.getText();
	}

	BindableListener listner;

	public void setBindableListener(BindableListener listner) {
		this.listner = listner;
	}
	
	public void onChange(Widget sender) {
		binding.noticeOnChange(this);
	}
}
