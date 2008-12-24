package info.widmogrod.gwt.kontorx.client.view.category;

import info.widmogrod.gwt.kontorx.client.ApplicationFacade;
import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryForm;
import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryForm.Mode;

import java.util.ArrayList;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.facade.Facade;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class CategoryFormMediator extends Mediator {
	
	public static final String NAME = "CategoryFormMediator";

	public CategoryFormMediator(final CategoryForm view) {
		super(NAME, view);
		
		final CategoryProxy proxy = getCategoryProxy();
		
		view.getActionButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				switch (view.getMode()) {
					default:
					case SHOW:
						sendNotification(CategoryProxy.BLOCK_ACTION_EDIT, view.getModel(), null);
						break;
					case SHOW_MULTI:
						sendNotification(CategoryProxy.BLOCK_ACTION_EDIT_MULTI, null, null);
						break;
					case NEW:
						proxy.add(view.getNewModel());
						break;
					case EDIT:
						proxy.edit(view.getModel());
						break;
					case EDIT_MULTI:
						CategoryBlockMediator mediator = (CategoryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(CategoryBlockMediator.NAME);
						ArrayList<CategoryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.edit(models, view.getModel());
						break;
				}
				
			}
		});
		
		view.getDeleteButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (Window.confirm("Czy chczesz usunąć kategorię?")) {
					switch (view.getMode()) {
					case SHOW:
						proxy.delete(view.getModel());
						break;
					case SHOW_MULTI:
						CategoryBlockMediator mediator = (CategoryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(CategoryBlockMediator.NAME);
						ArrayList<CategoryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.delete(models);
						break;
					}
				}
			}
		});
		
		view.getCancelButton().addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				sendNotification(CategoryProxy.BLOCK_ACTION_CANCEL, null, null);
			}
		});
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
	public CategoryForm getViewComponent() {
		return (CategoryForm) super.getViewComponent();
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
//				CategoryProxy.CATEGORY_DELETED,
//				CategoryProxy.CATEGORY_DELETED_MULTI,
				CategoryProxy.CATEGORY_ADDED,
				CategoryProxy.CATEGORY_UPDATED,
				// TODO Może dodać wczytywanie kategorii
//				CategoryProxy.CATEGORY_UPDATED_MULTI,
				CategoryProxy.BLOCK_ACTION_NEW,
				CategoryProxy.BLOCK_ACTION_SELECT,
				CategoryProxy.BLOCK_ACTION_SELECT_MULTI,
				CategoryProxy.BLOCK_ACTION_EDIT,
				CategoryProxy.BLOCK_ACTION_EDIT_MULTI};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String name = notification.getName();

		CategoryForm view = getViewComponent();
		
		if (name == CategoryProxy.BLOCK_ACTION_NEW) {
			view.setMode(Mode.NEW);
			view.cleanModel();
		}
		if (name == CategoryProxy.BLOCK_ACTION_SELECT
				|| name == CategoryProxy.CATEGORY_ADDED
				|| name == CategoryProxy.CATEGORY_UPDATED) {
			view.setMode(Mode.SHOW);
			view.setModel((CategoryVO) notification.getBody());
		} else
		if (name == CategoryProxy.BLOCK_ACTION_SELECT_MULTI
				|| name == CategoryProxy.CATEGORY_UPDATED) {
			view.setMode(Mode.SHOW_MULTI);
			view.cleanModel();
		} else
		if (name == CategoryProxy.BLOCK_ACTION_EDIT) {
			view.setMode(Mode.EDIT);
			view.setModel((CategoryVO) notification.getBody());
		} else
		if (name == CategoryProxy.BLOCK_ACTION_EDIT_MULTI) {
			view.setMode(Mode.EDIT_MULTI);
			view.cleanModel();
		}
//		if (name == CategoryProxy.CATEGORY_DELETED_MULTI) {
//			view.setMode(Mode.UPDATE_MULTI);
//			view.setModel((CategoryVO) notification.getBody());
//		} else
//		if (name == CategoryProxy.CATEGORY_DELETED) {
//			view.setMode(Mode.ADD);
//			view.cleanModel();
//		} else
	}
}
