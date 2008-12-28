package info.widmogrod.gwt.kontorx.client.controller;

import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.category.CategoryBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.image.ImageBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.image.ImageFormMediator;
import info.widmogrod.gwt.library.client.ui.MessageBox;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class InitDataCommand extends SimpleCommand {
	@Override
	public void execute(INotification notification) {
		Facade facade = getFacade();
		
		// Category
		CategoryProxy categoryProxy = (CategoryProxy) facade.retrieveProxy(CategoryProxy.NAME);
		CategoryBlockMediator categoryBlockMediator = (CategoryBlockMediator) facade.retrieveMediator(CategoryBlockMediator.NAME);
		// Gallery
		GalleryProxy galleryProxy = (GalleryProxy) facade.retrieveProxy(GalleryProxy.NAME);
		GalleryBlockMediator galleryBlockMediator = (GalleryBlockMediator) facade.retrieveMediator(GalleryBlockMediator.NAME);
		GalleryFormMediator galleryFormMediator = (GalleryFormMediator) facade.retrieveMediator(GalleryFormMediator.NAME);
		// Image
		ImageProxy imageProxy = (ImageProxy) facade.retrieveProxy(ImageProxy.NAME);
		ImageBlockMediator imageBlockMediator = (ImageBlockMediator) facade.retrieveMediator(ImageBlockMediator.NAME);
		ImageFormMediator imageFormMediator = (ImageFormMediator) facade.retrieveMediator(ImageFormMediator.NAME);
		

		// .. category
		final CheckBoxListManager<CategoryVO> managerCatalog = categoryBlockMediator.getViewComponent().getCheckBoxListManager();		
		final CheckBoxListManager<CategoryVO> managerFormGallery = galleryFormMediator.getViewComponent().getCategoryCheckBoxListManager();
		
		managerCatalog.setModel(categoryProxy);
		managerFormGallery.setModel(categoryProxy);

		categoryProxy.load(new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				managerCatalog.render();
				managerFormGallery.render();
			}
			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});

		// .. gallery
		final CheckBoxListManager<GalleryVO> managerGallery = galleryBlockMediator.getViewComponent().getCheckBoxListManager();
		final CheckBoxListManager<GalleryVO> managerFormImage = imageFormMediator.getViewComponent().getGalleryCheckBoxListManager();

		managerGallery.setModel(galleryProxy);
		managerFormImage.setModel(galleryProxy);

		galleryProxy.load(new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				managerGallery.render();
				managerFormImage.render();
			}
			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
		
		// .. images
		final ImageListManager<ImageVO> managerImages = imageBlockMediator.getViewComponent().getListManager();
		
		managerImages.setModel(imageProxy);

		imageProxy.load(new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				managerImages.render();
				// Brak zaznaczonych galerii - ten komunikat powoduje wyświetlenie
				// grafik nie przypisanych do żadnej z galerii!
				sendNotification(GalleryProxy.BLOCK_ACTION_SELECT_NONE, null, null);
			}
			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
}
