package info.widmogrod.gwt.kontorx.client.view;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.view.category.CategoryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryFormMediator;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.ui.Label;

public class MainWindowMediator extends Mediator {

	public static final String NAME = "MainWindowMediator";
	
	public MainWindowMediator(MainWindow viewComponent) {
		super(NAME, viewComponent);
	}

	@Override
	public MainWindow getViewComponent() {
		return (MainWindow) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				GalleryProxy.BLOCK_ACTION_LOAD,
				GalleryProxy.BLOCK_ACTION_NEW,
				GalleryProxy.BLOCK_ACTION_CANCEL,
				CategoryProxy.BLOCK_ACTION_LOAD,
				CategoryProxy.BLOCK_ACTION_NEW,
				CategoryProxy.BLOCK_ACTION_CANCEL};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		Facade facade = Facade.getInstance(ApplicationFacade.INIT);
		
		if (name == GalleryProxy.BLOCK_ACTION_LOAD) {
			GalleryFormMediator mediator = (GalleryFormMediator) facade.retrieveMediator(GalleryFormMediator.NAME);
			getViewComponent().setEditPanel(mediator.getViewComponent());
		} else
		if (name == GalleryProxy.BLOCK_ACTION_NEW) {
			GalleryFormMediator mediator = (GalleryFormMediator) facade.retrieveMediator(GalleryFormMediator.NAME);
			getViewComponent().setEditPanel(mediator.getViewComponent());
		} else
		if (name == GalleryProxy.BLOCK_ACTION_CANCEL) {
			getViewComponent().setEditPanel(new Label("Informacje gal"));
		} else
			if (name == CategoryProxy.BLOCK_ACTION_LOAD) {
				CategoryFormMediator mediator = (CategoryFormMediator) facade.retrieveMediator(CategoryFormMediator.NAME);
				getViewComponent().setEditPanel(mediator.getViewComponent());
			} else
			if (name == CategoryProxy.BLOCK_ACTION_NEW) {
				CategoryFormMediator mediator = (CategoryFormMediator) facade.retrieveMediator(CategoryFormMediator.NAME);
				getViewComponent().setEditPanel(mediator.getViewComponent());
			} else
			if (name == CategoryProxy.BLOCK_ACTION_CANCEL) {
				getViewComponent().setEditPanel(new Label("Informacje kat"));
			}
	}
}