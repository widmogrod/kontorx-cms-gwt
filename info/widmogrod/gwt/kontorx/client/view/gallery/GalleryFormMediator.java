package info.widmogrod.gwt.kontorx.client.view.gallery;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryForm;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryForm.Mode;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import java.util.ArrayList;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.Window;
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
					case SHOW:
						sendNotification(GalleryProxy.BLOCK_ACTION_EDIT, view.getModel(), null);
						break;
					case SHOW_MULTI:
						sendNotification(GalleryProxy.BLOCK_ACTION_EDIT_MULTI, null, null);
						break;
					case NEW:
						proxy.add(view.getNewModel());
						break;
					case EDIT:
						proxy.edit(view.getModel());
						break;
					case EDIT_MULTI:
						GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
						ArrayList<GalleryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.edit(models, view.getModel());
						break;
				}
				
			}
		});
		
		view.getDeleteButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (Window.confirm("Czy chczesz usunąć galerię?")) {
					switch (view.getMode()) {
					case SHOW:
						proxy.delete(view.getModel());
						break;
					case SHOW_MULTI:
						GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
						ArrayList<GalleryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.delete(models);
						break;
					}
				}
			}
		});
		
		view.getCancelButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				sendNotification(GalleryProxy.BLOCK_ACTION_CANCEL, null, null);
			}
		});

		final CheckBoxListManager<CategoryVO> manager = view.getCategoryCheckBoxListManager();

		// renderowanie odpowiednich nazw
		manager.setRenderCallback(new RenderCallback<CheckBoxList<CategoryVO>, CategoryVO>() {
			public void onRender(CheckBoxList<CategoryVO> component, CategoryVO model) {
				component.setText(model.getName());
			}
		});
		
		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				// zaznaczone galerie
				GalleryBlockMediator mediator = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
				ArrayList<GalleryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();

				// przypisz kategorie
				CheckBoxList<CategoryVO> ch = (CheckBoxList<CategoryVO>) sender;
				proxy.update(models, ch.getModel());

				if (!ch.isChecked()) {
					// nie ma zadnych zaznaczonych, czyli usun widok formularza
					sendNotification(GalleryProxy.BLOCK_ACTION_CANCEL, null, null);
				}
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
				GalleryProxy.BLOCK_ACTION_NEW,
				GalleryProxy.BLOCK_ACTION_SELECT,
				GalleryProxy.BLOCK_ACTION_SELECT_MULTI,
				GalleryProxy.BLOCK_ACTION_EDIT,
				GalleryProxy.BLOCK_ACTION_EDIT_MULTI,
				GalleryProxy.GALLERY_ADDED,
//				GalleryProxy.GALLERY_DELETED,
				GalleryProxy.GALLERY_UPDATED,
				GalleryProxy.GALLERY_UPDATED_MULTI,
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
		
		if (name == GalleryProxy.BLOCK_ACTION_NEW) {
			view.setMode(Mode.NEW);
			view.cleanModel();
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT
				|| name == GalleryProxy.GALLERY_ADDED
				|| name == GalleryProxy.GALLERY_UPDATED) {
			GalleryVO gallery = (GalleryVO) notification.getBody();
			view.setMode(Mode.SHOW);
			view.setModel(gallery);

			// zaznaczenie przypisanej galerii
			CheckBoxListManager<CategoryVO> manager = view.getCategoryCheckBoxListManager();
			CategoryProxy proxy = getCategoryProxy();
			CategoryVO row = proxy.findBy(gallery);

			if (row != null) {
				manager.setCheckedByModelRow(row);
			} else {
				manager.setChecked(false);
			}
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT_MULTI
				|| name == CategoryProxy.CATEGORY_UPDATED_MULTI) {
			view.setMode(Mode.SHOW_MULTI);
			// TODO czy czyszczenie modelu?
			view.cleanModel();
		} else
		if (name == GalleryProxy.BLOCK_ACTION_EDIT) {
			view.setMode(Mode.EDIT);
			view.setModel((GalleryVO) notification.getBody());
		} else
		if (name == GalleryProxy.BLOCK_ACTION_EDIT_MULTI) {
			view.setMode(Mode.EDIT_MULTI);
			view.cleanModel();
		} else
		if (name == GalleryProxy.GALLERY_UPDATED_CATEGORY) {
			ArrayList<GalleryVO> rowset = (ArrayList<GalleryVO>) notification.getBody();
			GalleryVO gallery = rowset.get(0);

			CheckBoxListManager<CategoryVO> manager = view.getCategoryCheckBoxListManager();

			CategoryProxy proxy = getCategoryProxy();
			CategoryVO row = proxy.findBy(gallery);

			if (row != null) {
				manager.setCheckedByModelRow(row);
			} else {
				manager.setChecked(false);
			}
		} else
		if (name == CategoryProxy.CATEGORY_ADDED
				|| name == CategoryProxy.CATEGORY_UPDATED
				|| name == CategoryProxy.CATEGORY_UPDATED_MULTI
				|| name == CategoryProxy.CATEGORY_DELETED
				|| name == CategoryProxy.CATEGORY_DELETED_MULTI) {

			CheckBoxListManager<CategoryVO> manager = view.getCategoryCheckBoxListManager();
			manager.refresh();
		}
	}
}
