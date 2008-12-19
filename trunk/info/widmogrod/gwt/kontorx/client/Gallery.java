package info.widmogrod.gwt.kontorx.client;

import org.puremvc.java.multicore.patterns.facade.Facade;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Gallery implements EntryPoint {

	public void onModuleLoad() {
		// inicjujemy
		Facade application = new ApplicationFacade();
		application.sendNotification(ApplicationFacade.INIT, RootPanel.get("main"), null);
	}
	
	public static native void log(String l) /*-{ console.log(l)}-*/;
}
