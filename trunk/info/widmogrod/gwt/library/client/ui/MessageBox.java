package info.widmogrod.gwt.library.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessageBox extends Composite {
	public enum Style {
		INFO, ERROR
	};
	
	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";

	private VerticalPanel panel;

	public MessageBox() {
		panel = new VerticalPanel();
		panel.setStyleName("kx-MessageBox");

		initWidget(panel);
	}

	public void clearMessages() {
		panel.clear();
	}
	
	public void setMessage(String message, Style style) {
		clearMessages();
		addMessage(message, style);
	}

	public void addMessage(String message, Style style) {
		final Label msg = new Label(message);
		
		panel.add(msg);
		
		switch (style) {
			default:
			case INFO:
				msg.setStyleName("kx-MessageBox-info");
				break;
			case ERROR:
				msg.setStyleName("kx-MessageBox-error");
				break;
		}

		// ustawienie że po 5 sek. znika wiadomosc
		Timer t = new Timer() {
			@Override
			public void run() {
				msg.removeFromParent();
			}
		};
		t.schedule(3000);
	}

	public void addMessage(String message, String style) {
		Style s = Style.INFO;
		if (style.equals(INFO)) {
			s = Style.INFO;
		} else
		if (style.equals(ERROR)) {
			s = Style.ERROR;
		}
		addMessage(message, s);
	}
}
