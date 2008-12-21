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
					case ADD:
						proxy.add(view.getNewModel());
						break;
					case UPDATE:
						proxy.edit(view.getModel());
						break;
					case UPDATE_MULTI:
						CategoryBlockMediator mediator = (CategoryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(CategoryBlockMediator.NAME);
						ArrayList<CategoryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
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
						CategoryBlockMediator mediator = (CategoryBlockMediator) Facade.getInstance(ApplicationFacade.INIT).retrieveMediator(CategoryBlockMediator.NAME);
						ArrayList<CategoryVO> models = mediator.getViewComponent().getCheckBoxListManager().getCheckedModels();
						proxy.delete(models);
						break;
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
				CategoryProxy.CATEGORY_ADDED,
				CategoryProxy.CATEGORY_DELETED,
				CategoryProxy.CATEGORY_UPDATED,
				CategoryProxy.CATEGORY_UPDATED_MULTI,
				CategoryProxy.BLOCK_ACTION_NEW,
				CategoryProxy.BLOCK_ACTION_LOAD,
				CategoryProxy.BLOCK_ACTION_LOAD_MULTI};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		CategoryForm view = getViewComponent();
		
		String name = notification.getName();
		if (name == CategoryProxy.BLOCK_ACTION_LOAD
				|| name == CategoryProxy.CATEGORY_UPDATED
				|| name == CategoryProxy.CATEGORY_ADDED) {
			view.setMode(Mode.UPDATE);
			view.setModel((CategoryVO) notification.getBody());
		} else
		if (name == CategoryProxy.CATEGORY_DELETED_MULTI) {
			view.setMode(Mode.UPDATE_MULTI);
			view.setModel((CategoryVO) notification.getBody());
		} else
		if (name == CategoryProxy.CATEGORY_DELETED) {
			view.setMode(Mode.ADD);
			view.cleanModel();
		} else
		if (name == CategoryProxy.BLOCK_ACTION_NEW
				|| name == CategoryProxy.CATEGORY_DELETED_MULTI) {
			view.setMode(Mode.ADD);
			view.cleanModel();
		} else
		if (name == CategoryProxy.BLOCK_ACTION_LOAD_MULTI) {
			view.setMode(Mode.UPDATE_MULTI);
			view.cleanModel();
		}
	}
}
