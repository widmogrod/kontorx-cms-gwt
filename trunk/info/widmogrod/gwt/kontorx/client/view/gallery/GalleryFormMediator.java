package info.widmogrod.gwt.kontorx.client.view.gallery;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.Category;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.Gallery;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryForm;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryForm.Mode;
import info.widmogrod.gwt.library.client.ui.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.CheckBoxListManager;
import info.widmogrod.gwt.library.client.ui.CheckBoxListRenderCallback;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import java.util.ArrayList;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class GalleryFormMediator extends Mediator {
	
	public static final String NAME = "GalleryFormMediator";

	public GalleryFormMediator(final GalleryForm view) {
		super(NAME, view);
		
		final GalleryProxy proxy = getGalleryProxy();
		
		view.getActionButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				switch (view.getMode()) {
					default:
					case ADD:
						proxy.add(view.getNewModel());
						break;
					case UPDATE:
						proxy.edit(view.getModel());
						break;
					case UPDATE_MULTI:
						GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
						ArrayList<Gallery> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.edit(models, view.getModel());
						break;
				}
				
			}
		});
		
		view.getDeleteButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				switch (view.getMode()) {
					case UPDATE:
						proxy.delete(view.getModel());
						break;
					case UPDATE_MULTI:
						GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
						ArrayList<Gallery> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.delete(models);
						break;
				}
				
			}
		});
		
		view.getCancelButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				sendNotification(GalleryProxy.BLOCK_ACTION_CANCEL, null, null);
			}
		});

		final CheckBoxListManager<Category> manager = view.getCategoryCheckBoxListManager();

		// renderowanie odpowiednich nazw
		manager.setRenderCallback(new CheckBoxListRenderCallback<Category>() {
			public void onRender(CheckBoxList<Category> component, Category model) {
				component.setText(model.getName());
			}
		});
		
		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				// zaznaczone galerie
				GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
				ArrayList<Gallery> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();

				// przypisz kategorie
				CheckBoxList<Category> ch = (CheckBoxList<Category>) sender;
				proxy.update(models, ch.getModel());

				if (!ch.isChecked()) {
					// nie ma zadnych zaznaczonych, czyli usun widok formularza
					sendNotification(GalleryProxy.BLOCK_ACTION_CANCEL, null, null);
				}
			}
		});
		
		CategoryProxy proxyCategory = getCategoryProxy();
		manager.setModel(proxyCategory);
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

	private CategoryProxy categoryProxy;
	
	private CategoryProxy getCategoryProxy() {
		if (null == categoryProxy) {
			// TODO bardzo dziwne nie dziala getFacade() ..
			categoryProxy = (CategoryProxy) Facade.getInstance(ApplicationFacade.INIT).retrieveProxy(CategoryProxy.NAME);
		}
		return categoryProxy;
	}
	
	@Override
	public GalleryForm getViewComponent() {
		return (GalleryForm) super.getViewComponent();
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				// Gallery
				GalleryProxy.GALLERY_ADDED,
				GalleryProxy.GALLERY_DELETED,
				GalleryProxy.GALLERY_UPDATED,
				GalleryProxy.GALLERY_UPDATED_MULTI,
				GalleryProxy.BLOCK_ACTION_NEW,
				GalleryProxy.BLOCK_ACTION_LOAD,
				GalleryProxy.BLOCK_ACTION_LOAD_MULTI,
				GalleryProxy.GALLERY_UPDATED_CATEGORY,
				// Category
				CategoryProxy.CATEGORY_ADDED,
				CategoryProxy.CATEGORY_UPDATED,
				CategoryProxy.CATEGORY_UPDATED_MULTI,
				CategoryProxy.CATEGORY_DELETED,
				CategoryProxy.CATEGORY_DELETED_MULTI};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		GalleryForm view = getViewComponent();
		
		if (name == GalleryProxy.BLOCK_ACTION_LOAD
				|| name == GalleryProxy.GALLERY_UPDATED
				|| name == GalleryProxy.GALLERY_ADDED) {
			Gallery gallery = (Gallery) notification.getBody();
			view.setMode(Mode.UPDATE);
			view.setModel(gallery);

			CheckBoxListManager<Category> manager = view.getCategoryCheckBoxListManager();

			CategoryProxy proxy = getCategoryProxy();
			Category row = proxy.findBy(gallery);

			if (row != null) {
				manager.setCheckedByModelRow(row);
			} else {
				manager.setChecked(false);
			}
		} else
		if (name == GalleryProxy.GALLERY_UPDATED_MULTI) {
			view.setMode(Mode.UPDATE_MULTI);
			view.setModel((Gallery) notification.getBody());
		} else
		if (name == GalleryProxy.GALLERY_UPDATED_CATEGORY) {
			ArrayList<Gallery> rowset = (ArrayList<Gallery>) notification.getBody();
			Gallery gallery = rowset.get(0);

			CheckBoxListManager<Category> manager = view.getCategoryCheckBoxListManager();

			CategoryProxy proxy = getCategoryProxy();
			Category row = proxy.findBy(gallery);

			if (row != null) {
				manager.setCheckedByModelRow(row);
			} else {
				manager.setChecked(false);
			}
		} else
		if (name == GalleryProxy.GALLERY_DELETED) {
			view.setMode(Mode.ADD);
			view.cleanModel();
		} else
		if (name == GalleryProxy.BLOCK_ACTION_NEW
				|| name == GalleryProxy.GALLERY_DELETED_MULTI) {
			view.setMode(Mode.ADD);
			view.cleanModel();
		} else
		if (name == GalleryProxy.BLOCK_ACTION_LOAD_MULTI) {
			view.setMode(Mode.UPDATE_MULTI);
			view.cleanModel();
		} else
		if (name == CategoryProxy.CATEGORY_ADDED
				|| name == CategoryProxy.CATEGORY_UPDATED
				|| name == CategoryProxy.CATEGORY_UPDATED_MULTI
				|| name == CategoryProxy.CATEGORY_DELETED
				|| name == CategoryProxy.CATEGORY_DELETED_MULTI) {

			CheckBoxListManager<Category> manager = view.getCategoryCheckBoxListManager();
//			manager.setModel(getCategoryProxy().getModel());
			manager.refresh();
		}
	}
}
