package info.widmogrod.gwt.kontorx.client.view;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.view.category.CategoryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.image.ImageFormMediator;

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
				// Gallery
				GalleryProxy.BLOCK_ACTION_SELECT,
				GalleryProxy.BLOCK_ACTION_SELECT_NONE,
				GalleryProxy.BLOCK_ACTION_NEW,
				GalleryProxy.BLOCK_ACTION_CANCEL,
				// Category
				CategoryProxy.BLOCK_ACTION_EDIT,
				CategoryProxy.BLOCK_ACTION_NEW,
				CategoryProxy.BLOCK_ACTION_SELECT,
				CategoryProxy.BLOCK_ACTION_SELECT_NONE,
				CategoryProxy.BLOCK_ACTION_CANCEL,
				// Image
				ImageProxy.BLOCK_ACTION_SHOW,
				ImageProxy.BLOCK_ACTION_NEW,
				ImageProxy.BLOCK_ACTION_CANCEL};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		Facade facade = Facade.getInstance(ApplicationFacade.INIT);
		
		if (name == GalleryProxy.BLOCK_ACTION_SELECT) {
			GalleryFormMediator mediator = (GalleryFormMediator) facade.retrieveMediator(GalleryFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == GalleryProxy.BLOCK_ACTION_NEW) {
			GalleryFormMediator mediator = (GalleryFormMediator) facade.retrieveMediator(GalleryFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == GalleryProxy.BLOCK_ACTION_CANCEL
				|| name == GalleryProxy.BLOCK_ACTION_SELECT_NONE) {
			getViewComponent().setInfoPanel(new Label("Informacje gal"));
		} else
		if (name == CategoryProxy.BLOCK_ACTION_EDIT) {
			CategoryFormMediator mediator = (CategoryFormMediator) facade.retrieveMediator(CategoryFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == CategoryProxy.BLOCK_ACTION_NEW
				|| name == CategoryProxy.BLOCK_ACTION_SELECT) {
			CategoryFormMediator mediator = (CategoryFormMediator) facade.retrieveMediator(CategoryFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == CategoryProxy.BLOCK_ACTION_CANCEL
				|| name == CategoryProxy.BLOCK_ACTION_SELECT_NONE) {
			getViewComponent().setInfoPanel(new Label("Informacje kat"));
		} else
		if (name == ImageProxy.BLOCK_ACTION_SHOW) {
			ImageFormMediator mediator = (ImageFormMediator) facade.retrieveMediator(ImageFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == ImageProxy.BLOCK_ACTION_NEW) {
			ImageFormMediator mediator = (ImageFormMediator) facade.retrieveMediator(ImageFormMediator.NAME);
			getViewComponent().setInfoPanel(mediator.getViewComponent());
		} else
		if (name == ImageProxy.BLOCK_ACTION_CANCEL) {
			getViewComponent().setInfoPanel(new Label("Informacje kat"));
		}
	}
}