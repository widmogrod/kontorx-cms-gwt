package info.widmogrod.gwt.kontorx.client.view.image;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.image.components.ImageBlock;
import info.widmogrod.gwt.library.client.ui.MessageBox;
import info.widmogrod.gwt.library.client.ui.list.ImageList;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

				if (ImageBlock.Actions.SELECT_ALL.getValue() == value) {
					manager.setChecked(true);
				} else
				if (ImageBlock.Actions.SELECT_NONE.getValue() == value) {
					manager.setChecked(false);
				} else
				if (ImageBlock.Actions.SELECT_FLIP.getValue() == value) {
					manager.setCheckedFlip();
				}
			}
		});
		
		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				if (manager.getCountChecked() > 1) {
					// powiadamia ze jest zaznaczonych kilka ..
					sendNotification(ImageProxy.BLOCK_ACTION_LOAD_MULTI, null, null);
				} else {
					ImageList<ImageVO> ich = (ImageList<ImageVO>) sender;
					if (ich.isChecked()) {
						// zaznaczona jest jedna galeria
						sendNotification(ImageProxy.BLOCK_ACTION_LOAD, ich.getModel(), null);
					} else {
						// nie ma zadnych zaznaczonych, czyli usun widok formularza
						sendNotification(ImageProxy.BLOCK_ACTION_CANCEL, null, null);
					}
				}
			}
		});
		
		ImageProxy proxy = getImageProxy();
		manager.setModel(proxy);
		proxy.load(new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				manager.render();
			}
			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
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
	
	@Override
	public ImageBlock getViewComponent() {
		return (ImageBlock) super.getViewComponent();
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				ImageProxy.IMAGE_ADDED,
				ImageProxy.IMAGE_DELETED,
				ImageProxy.IMAGE_DELETED_MULTI,
				ImageProxy.IMAGE_UPDATED,
				ImageProxy.IMAGE_UPDATED_MULTI,
				ImageProxy.IMAGE_UPDATED_GALLERY
		};
	}

	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		ImageListManager<ImageVO> manager = getViewComponent().getListManager();
		
		if (name == ImageProxy.IMAGE_ADDED) {
			manager.refresh();
			// zaznaczenie dodanego rekordu
			ImageVO row = (ImageVO) notification.getBody();
			manager.setCheckedByModelRow(row);
		} else
		if (name == ImageProxy.IMAGE_DELETED
				|| name == ImageProxy.IMAGE_DELETED_MULTI
				|| name == ImageProxy.IMAGE_UPDATED
				|| name == ImageProxy.IMAGE_UPDATED_MULTI
				|| name == ImageProxy.IMAGE_UPDATED_GALLERY) {
			manager.refresh();
		}
	}
}
