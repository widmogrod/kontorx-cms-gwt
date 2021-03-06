package info.widmogrod.gwt.kontorx.client.view.image;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.image.components.ImageForm;
import info.widmogrod.gwt.kontorx.client.view.image.components.ImageForm.Mode;
import info.widmogrod.gwt.library.client.ui.MessageBox;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import java.util.ArrayList;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Widget;

public class ImageFormMediator extends Mediator {
	
	public static final String NAME = "ImageFormMediator";

	public ImageFormMediator(final ImageForm view) {
		super(NAME, view);
		
		final ImageProxy proxy = getImageProxy();
		
		view.getFormPanel().addFormHandler(new FormHandler() {
			public void onSubmit(FormSubmitEvent event) {
				String message = "Wysyłanie pliku";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}
			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				proxy.add(event);
			}
		});

		view.getActionButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				switch (view.getMode()) {
					default:
					case SHOW:
						sendNotification(ImageProxy.BLOCK_ACTION_EDIT, view.getModel(), null);
						break;
					case SHOW_MULTI:
						sendNotification(ImageProxy.BLOCK_ACTION_EDIT_MULTI, null, null);
						break;
					case NEW:
						view.getFormPanel().submit();
						break;
					case EDIT:
						proxy.edit(view.getModel());
						break;
					case EDIT_MULTI:
						ImageBlockMediator mediator = (ImageBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(ImageBlockMediator.NAME);
						ArrayList<ImageVO> models = mediator.getViewComponent().getListManager().getCheckedModels();
						proxy.edit(models, view.getModel());
						break;
				}
				
			}
		});

		view.getDeleteButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (Window.confirm("Czy chczesz usunąć grafikę?")) {
					switch (view.getMode()) {
					case SHOW:
						proxy.delete(view.getModel());
						break;
					case SHOW_MULTI:
						ImageBlockMediator mediator = (ImageBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(ImageBlockMediator.NAME);
						ArrayList<ImageVO> models = mediator.getViewComponent().getListManager().getCheckedModels();
						proxy.delete(models);
						break;
					
					}
				}
			}
		});
		
		view.getCancelButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
			}
		});

		final CheckBoxListManager<GalleryVO> manager = view.getGalleryCheckBoxListManager();

		// renderowanie odpowiednich nazw
		manager.setRenderCallback(new RenderCallback<CheckBoxList<GalleryVO>, GalleryVO>() {
			public void onRender(CheckBoxList<GalleryVO> component, GalleryVO model) {
				component.setText(model.getName());
			}
		});
		
		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				// zaznaczone grafiki
				ImageBlockMediator mediator = (ImageBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(ImageBlockMediator.NAME);
				ArrayList<ImageVO> models = mediator.getViewComponent().getListManager().getCheckedModels();

				// przypisz grafike
				CheckBoxList<GalleryVO> ch = (CheckBoxList<GalleryVO>) sender;
				proxy.update(models, ch.getModel());

				if (!ch.isChecked()) {
					// nie ma zadnych zaznaczonych, czyli usun widok formularza
					sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
				}
			}
		});
	}

	private ImageProxy imageProxy;
	
	private ImageProxy getImageProxy() {
		if (null == imageProxy) {
			// TODO bardzo dziwne nie dziala getFacade() ..
			imageProxy = (ImageProxy) Facade.getInstance(ApplicationFacade.INIT).retrieveProxy(ImageProxy.NAME);
		}
		return imageProxy;
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
	public ImageForm getViewComponent() {
		return (ImageForm) super.getViewComponent();
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				// Image
				ImageProxy.IMAGE_ADDED,
//				ImageProxy.IMAGE_DELETED,
				ImageProxy.IMAGE_UPDATED,
				ImageProxy.IMAGE_UPDATED_MULTI,
//				ImageProxy.IMAGE_UPDATED_GALLERY,
				ImageProxy.BLOCK_ACTION_NEW,
				ImageProxy.BLOCK_ACTION_SHOW,
				ImageProxy.BLOCK_ACTION_SHOW_MULTI,
				ImageProxy.BLOCK_ACTION_EDIT,
				ImageProxy.BLOCK_ACTION_EDIT_MULTI,
				// Category
				GalleryProxy.GALLERY_ADDED,
				GalleryProxy.GALLERY_UPDATED,
				GalleryProxy.GALLERY_UPDATED_MULTI,
				GalleryProxy.GALLERY_DELETED,
				GalleryProxy.GALLERY_DELETED_MULTI};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		ImageForm view = getViewComponent();
		
		if (name == ImageProxy.BLOCK_ACTION_NEW) {
			view.setMode(Mode.NEW);
			view.cleanModel();
		} else
		if (name == ImageProxy.BLOCK_ACTION_SHOW
				|| name == ImageProxy.IMAGE_UPDATED
				|| name == ImageProxy.IMAGE_ADDED) {
			ImageVO image = (ImageVO) notification.getBody();
			view.setMode(Mode.SHOW);
			view.setModel(image);

			CheckBoxListManager<GalleryVO> manager = view.getGalleryCheckBoxListManager();

			GalleryProxy proxy = getGalleryProxy();
			GalleryVO row = proxy.findBy(image);

			if (row != null) {
				manager.setCheckedByModelRow(row);
			} else {
				manager.setChecked(false);
			}
		} else
		if (name == ImageProxy.BLOCK_ACTION_SHOW_MULTI
				|| name == ImageProxy.IMAGE_UPDATED_MULTI) {
			view.setMode(Mode.SHOW_MULTI);
			view.cleanModel();
		} else
//		if (name == ImageProxy.IMAGE_UPDATED_GALLERY) {
//			ArrayList<ImageVO> rowset = (ArrayList<ImageVO>) notification.getBody();
//			ImageVO image = rowset.get(0);
//
//			CheckBoxListManager<GalleryVO> manager = view.getGalleryCheckBoxListManager();
//
//			GalleryProxy proxy = getGalleryProxy();
//			GalleryVO row = proxy.findBy(image);
//
//			if (row != null) {
//				manager.setCheckedByModelRow(row);
//			} else {
//				manager.setChecked(false);
//			}
//		} else
		if (name == ImageProxy.BLOCK_ACTION_EDIT) {
			view.setMode(Mode.EDIT);
			view.setModel((ImageVO) notification.getBody());
		} else
		if (name == ImageProxy.BLOCK_ACTION_EDIT_MULTI) {
			view.setMode(Mode.EDIT_MULTI);
			view.cleanModel();
		} else
		if (name == GalleryProxy.GALLERY_ADDED
				|| name == GalleryProxy.GALLERY_UPDATED
				|| name == GalleryProxy.GALLERY_UPDATED_MULTI
				|| name == GalleryProxy.GALLERY_DELETED
				|| name == GalleryProxy.GALLERY_DELETED_MULTI) {

			CheckBoxListManager<GalleryVO> manager = view.getGalleryCheckBoxListManager();
			manager.refresh();
		}
	}
}
