package info.widmogrod.gwt.kontorx.client.view.category;

import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryBlock;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class CategoryBlockMediator extends Mediator {

	public static final String NAME = "CategoryBlockMediator";

	public CategoryBlockMediator(CategoryBlock view) {
		super(NAME, view);
		
		view.getAddButton().addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				sendNotification(CategoryProxy.BLOCK_ACTION_NEW, null, null);
			}
		});

		final CheckBoxListManager<CategoryVO> manager = view.getCheckBoxListManager();

		// renderowanie odpowiednich nazw
		manager.setRenderCallback(new RenderCallback<CheckBoxList<CategoryVO>, CategoryVO>() {
			public void onRender(CheckBoxList<CategoryVO> component, CategoryVO model) {
				component.setText(model.getName());
			}
		});

		manager.setClickListner(new ClickListener(){
			@SuppressWarnings("unchecked")
			public void onClick(Widget sender) {
				if (manager.getCountChecked() > 1) {
					// powiadamia ze jest zaznaczonych kilka ..
					sendNotification(CategoryProxy.BLOCK_ACTION_SELECT_MULTI, null, null);
				} else {
					CheckBoxList<CategoryVO> ch = (CheckBoxList<CategoryVO>) sender;
					if (ch.isChecked()) {
						// zaznaczona jest jedna galeria
						sendNotification(CategoryProxy.BLOCK_ACTION_SELECT, ch.getModel(), null);
					} else {
						// nie ma zadnych zaznaczonych, czyli usun widok formularza
						sendNotification(CategoryProxy.BLOCK_ACTION_SELECT_NONE, null, null);
					}
				}
			}
		});
		
//		CategoryProxy proxy = getCategoryProxy();
//		manager.setModel(proxy);
//		proxy.load(new AsyncCallback<Boolean>() {
//			public void onSuccess(Boolean result) {
//				manager.render();
//			}
//			public void onFailure(Throwable caught) {
//				String message = caught.getMessage();
//				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
//			}
//		});
	}

//	private CategoryProxy categoryProxy;
//	
//	private CategoryProxy getCategoryProxy() {
//		if (null == categoryProxy) {
//			// TODO bardzo dziwne nie dziala getFacade() ..
//			categoryProxy = (CategoryProxy) Facade.getInstance(ApplicationFacade.INIT).retrieveProxy(CategoryProxy.NAME);
//		}
//		return categoryProxy;
//	}
	
	@Override
	public CategoryBlock getViewComponent() {
		return (CategoryBlock) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				// Category
				CategoryProxy.CATEGORY_ADDED,
				CategoryProxy.CATEGORY_DELETED,
				CategoryProxy.CATEGORY_DELETED_MULTI,
				CategoryProxy.CATEGORY_UPDATED,
				CategoryProxy.CATEGORY_UPDATED_MULTI,
				// Gallery
				GalleryProxy.BLOCK_ACTION_SELECT_MULTI};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();
		
		CheckBoxListManager<CategoryVO> manager = getViewComponent().getCheckBoxListManager();

		if (name == CategoryProxy.CATEGORY_ADDED) {
			manager.refresh();
			// Zaznaczenie świerzo dodanego rekordu
			CategoryVO row = (CategoryVO) notification.getBody();
			manager.setCheckedByModelRow(row);
		}
		if (name == CategoryProxy.CATEGORY_UPDATED
				|| name == CategoryProxy.CATEGORY_UPDATED_MULTI
				|| name == CategoryProxy.CATEGORY_DELETED
				|| name == CategoryProxy.CATEGORY_DELETED_MULTI) {
			manager.refresh();
		} else
		if (name == GalleryProxy.BLOCK_ACTION_SELECT_MULTI) {
			// odznaczamy zaznaczenia
			manager.setChecked(false);
		}
	}
}