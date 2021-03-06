package info.widmogrod.gwt.kontorx.client.view.image;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.image.components.ImageBlock;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;
import info.widmogrod.gwt.library.client.ui.list.ImageList;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import java.util.ArrayList;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ImageBlockMediator extends Mediator {
	public static final String NAME = "ImageBlockMediator";

	public ImageBlockMediator(ImageBlock view) {
		super(NAME, view);
	
		final ImageListManager<ImageVO> manager = view.getListManager();

		view.getAddButton().addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				sendNotification(ImageProxy.BLOCK_ACTION_NEW, null, null);
			}
		});
		
		view.getActionsBox().addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				ListBox list = (ListBox) sender;
				String value = list.getValue(list.getSelectedIndex());
				// resetuję do wyboru wartości
				list.setSelectedIndex(0);

				if (ImageBlock.Actions.SELECT_ALL.getValue() == value) {
					manager.setCheckedVisible(true);
					sendNotification(ImageProxy.BLOCK_ACTION_SHOW_MULTI, null, null);
				} else
				if (ImageBlock.Actions.SELECT_NONE.getValue() == value) {
					manager.setCheckedVisible(false);
					sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
				} else
				if (ImageBlock.Actions.SELECT_FLIP.getValue() == value) {
					manager.setCheckedFlipVisible();
					if (manager.getCountChecked() > 0) {
						sendNotification(ImageProxy.BLOCK_ACTION_SHOW_MULTI, null, null);
					} else {
						sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
					}
				}				
			}
		});
		
		manager.setClickListner(new ClickListener(){
			public void onClick(Widget sender) {
				int count = manager.getCountChecked();

				if (count > 1) {
					// powiadamia ze jest zaznaczonych kilka ..
					sendNotification(ImageProxy.BLOCK_ACTION_SHOW_MULTI, null, null);
				} else
				if (count == 1) {
					ImageList<ImageVO> ich = manager.getCheckedCheckBox().get(0);
					// zaznaczona jest jedna galeria
					sendNotification(ImageProxy.BLOCK_ACTION_SHOW, ich.getModel(), null);
				} else {
					// nie ma zadnych zaznaczonych, czyli usun widok formularza
					sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
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
	
	@Override
	public ImageBlock getViewComponent() {
		return (ImageBlock) super.getViewComponent();
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				// ImageProxy
				ImageProxy.IMAGE_ADDED,
				ImageProxy.IMAGE_DELETED,
				ImageProxy.IMAGE_DELETED_MULTI,
				ImageProxy.IMAGE_UPDATED,
				ImageProxy.IMAGE_UPDATED_MULTI,
				ImageProxy.IMAGE_UPDATED_GALLERY,
				// GalleryProxy
				GalleryProxy.BLOCK_ACTION_SELECT,
				GalleryProxy.BLOCK_ACTION_SELECT_NONE,
				GalleryProxy.BLOCK_ACTION_SELECT_MULTI
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		ImageListManager<ImageVO> manager = getViewComponent().getListManager();
		
		if (name == ImageProxy.IMAGE_ADDED) {
			manager.refresh();
			// zaznaczenie dodanego rekordu
			ImageVO row = (ImageVO) notification.getBody();
			manager.setCheckedByModelRow(row);

			// pokaż wszystkie grafiki, które nie są przypisane do żadnej galerii
			for (ImageList<ImageVO> ich : manager.getList().values()) {
				// TODO To występuje niebespieczeństwo pominiecia galerii o ID=0!
				if (ich.getModel().getGalleryId() > 0) {
					ich.setVisible(false);
				} else {
					ich.setVisible(true);
				}
			}
		} else
		if (name == ImageProxy.IMAGE_UPDATED_GALLERY) {
			// Prypisanie grafiki do galerii - powoduje zaznaczenie aktualnej galerii
			ArrayList<ImageVO> rowset = (ArrayList<ImageVO>) notification.getBody();
			ImageVO image = rowset.get(0);

			GalleryProxy proxy = getGalleryProxy();
			GalleryVO gallery = proxy.findBy(image);

			for (ImageList<ImageVO> ich : manager.getList().values()) {
				if (ich.getModel().getGalleryId() == gallery.getId()) {
					ich.setVisible(true);
				} else {
					ich.setVisible(false);
				}
			}
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT) {
			// odznaczam zaznaczone grafiki
			manager.setChecked(false);
			// pokaz tylko grafiki, ktore nalerza do zaznaczonej galerii
			GalleryVO gallery = (GalleryVO) notification.getBody();
			for (ImageList<ImageVO> ich : manager.getList().values()) {
				if (ich.getModel().getGalleryId() == gallery.getId()) {
					ich.setVisible(true);
				} else {
					ich.setVisible(false);
				}
			}
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT_NONE) {
			// odznaczam zaznaczone grafiki
			manager.setChecked(false);
			// gdy nie ma zaznaczonej żadnej galerii, pokaż wszystkie grafiki
			// które nie są przypisane do żadnej galerii
			for (ImageList<ImageVO> ich : manager.getList().values()) {
				// TODO To występuje niebespieczeństwo pominiecia galerii o ID=0!
				if (ich.getModel().getGalleryId() > 0) {
					ich.setVisible(false);
				} else {
					ich.setVisible(true);
				}
			}
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT_MULTI) {
			// odznaczam zaznaczone grafiki
			manager.setChecked(false);
			// pobieram zaznaczone galerie
			GalleryBlockMediator galleryBlock = (GalleryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(GalleryBlockMediator.NAME);
			CheckBoxListManager<GalleryVO> managerGallery = galleryBlock.getViewComponent().getCheckBoxListManager();
			// tworze liste id zaznaczonych galerii
			ArrayList<Integer> idList = new ArrayList<Integer>();
			for (GalleryVO gallery : managerGallery.getCheckedModels()) {
				idList.add(gallery.getId());
			}
			// pokaż grafiki nalerzace do zaznaczonych galerii
			for (ImageList<ImageVO> ich : manager.getList().values()) {
				if (idList.contains(ich.getModel().getGalleryId())) {
					ich.setVisible(true);
				} else {
					ich.setVisible(false);
				}
			}
		} else
		if (name == ImageProxy.IMAGE_DELETED
				|| name == ImageProxy.IMAGE_DELETED_MULTI
				|| name == ImageProxy.IMAGE_UPDATED
				|| name == ImageProxy.IMAGE_UPDATED_MULTI) {
			manager.refresh();
		}
	}
}
