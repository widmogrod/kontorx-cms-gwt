package info.widmogrod.gwt.kontorx.client.view.gallery;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.Gallery;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryBlock;
import info.widmogrod.gwt.library.client.ui.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.CheckBoxListManager;
import info.widmogrod.gwt.library.client.ui.CheckBoxListRenderCallback;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class GalleryBlockMediator extends Mediator {
	public static final String NAME = "GalleryBlockMediator";

	public GalleryBlockMediator(GalleryBlock view) {
		super(NAME, view);
		
		view.getAddButton().addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				sendNotification(GalleryProxy.BLOCK_ACTION_NEW, null, null);
			}
		});

		final CheckBoxListManager<Gallery> manager = view.getCheckBoxListManager();

		// renderowanie odpowiednich nazw
		manager.setRenderCallback(new CheckBoxListRenderCallback<Gallery>() {
			public void onRender(CheckBoxList<Gallery> component, Gallery model) {
				component.setText(model.getName());
			}
		});

		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				if (manager.getCountChecked() > 1) {
					// powiadamia ze jest zaznaczonych kilka ..
					sendNotification(GalleryProxy.BLOCK_ACTION_LOAD_MULTI, null, null);
				} else {
					CheckBoxList<Gallery> ch = (CheckBoxList<Gallery>) sender;
					if (ch.isChecked()) {
						// zaznaczona jest jedna galeria
						sendNotification(GalleryProxy.BLOCK_ACTION_LOAD, ch.getModel(), null);
					} else {
						// nie ma zadnych zaznaczonych, czyli usun widok formularza
						sendNotification(GalleryProxy.BLOCK_ACTION_CANCEL, null, null);
					}
				}
			}
		});
		
		GalleryProxy proxy = getGalleryProxy();
		manager.setModel(proxy);
		proxy.load(new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				manager.render();
			}
			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}

	private GalleryProxy galleryProxy;
	
	private GalleryProxy getGalleryProxy() {
		if (null == galleryProxy) {
			// TODO bardzo dziwne nie dziala getFacade() ..
			galleryProxy = (GalleryProxy) Facade.getInstance(ApplicationFacade.INIT).retrieveProxy(GalleryProxy.NAME);
		}
		return galleryProxy;
	}
	
	@Override
	public GalleryBlock getViewComponent() {
		return (GalleryBlock) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				// Gallery
				GalleryProxy.GALLERY_DELETED,
				GalleryProxy.GALLERY_DELETED_MULTI,
				GalleryProxy.GALLERY_ADDED,
				GalleryProxy.GALLERY_UPDATED,
				GalleryProxy.GALLERY_UPDATED_MULTI,
				GalleryProxy.GALLERY_UPDATED_CATEGORY,
				// Category
				CategoryProxy.BLOCK_ACTION_LOAD_MULTI,
				CategoryProxy.BLOCK_ACTION_LOAD};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		CheckBoxListManager<Gallery> manager = getViewComponent().getCheckBoxListManager();
		
		String name = notification.getName();
		if (name == GalleryProxy.GALLERY_UPDATED) {
			manager.refresh();
		} else
		if (name == GalleryProxy.GALLERY_UPDATED_MULTI) {
			manager.refresh();
		} else
		if (name == GalleryProxy.GALLERY_UPDATED_CATEGORY) {
			manager.refresh();
		} else
		if (name == GalleryProxy.GALLERY_DELETED
				|| name == GalleryProxy.GALLERY_DELETED_MULTI) {
			manager.refresh();
		} else
		if (name == GalleryProxy.GALLERY_ADDED) {
			manager.refresh();
		} else
		if (name == CategoryProxy.BLOCK_ACTION_LOAD_MULTI) {
			// odznaczamy zaznaczenia
			getViewComponent().getCheckBoxListManager().setChecked(false);
		} else
		if (name == CategoryProxy.BLOCK_ACTION_LOAD) {
			// odznaczamy zaznaczenia
			getViewComponent().getCheckBoxListManager().setChecked(false);
		}
	}
}