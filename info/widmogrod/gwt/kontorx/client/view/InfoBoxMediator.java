package info.widmogrod.gwt.kontorx.client.view;

import info.widmogrod.gwt.library.client.ui.MessageBox;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class InfoBoxMediator extends Mediator {

	public static final String NAME = "InfoBoxMediator";	
	public static final String DISPLAY_MESSAGE = "InfoBoxMediator_DISPLAY_MESSAGE";

	public InfoBoxMediator(MessageBox view) {
		super(NAME, view);
	}

	@Override
	public MessageBox getViewComponent() {
		return (MessageBox) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		// TODO Auto-generated method stub
		return new String[] {DISPLAY_MESSAGE};
	}

	@Override
	public void handleNotification(INotification notification) {
		getViewComponent().addMessage((String) notification.getBody(), notification.getType());
	}
}
