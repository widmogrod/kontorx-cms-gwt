package info.widmogrod.gwt.kontorx.client.model;

import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.library.client.db.xmlrpc.XmlRpcDbTableDecorator;
import info.widmogrod.gwt.library.client.puremvc.patterns.ProxyModel;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CategoryProxy extends ProxyModel<Category> {

	public static final String NAME = "CategoryProxy";

	// XmlRpc settings
	public static final String XMLRPC_URL = GWT.getHostPageBaseURL() + "gwt/rpc";
	public static final String XMLRPC_PROXY = "category";

	// Notifications
	public static final String BLOCK_ACTION_NEW = "CategoryProxy_CATEGORY_NEW";
	public static final String BLOCK_ACTION_CANCEL = "CategoryProxy_CATEGORY_CANCEL";
	public static final String BLOCK_ACTION_LOAD = "CategoryProxy_CATEGORY_LOADED";
	public static final String BLOCK_ACTION_LOAD_MULTI = "CategoryProxy_CATEGORY_LOADED_MULTI";
	public static final String CATEGORY_ADDED = "CategoryProxy_CATEGORY_ADDED";
	public static final String CATEGORY_DELETED = "CategoryProxy_CATEGORY_DELETED";
	public static final String CATEGORY_DELETED_MULTI = "CategoryProxy_CATEGORY_DELETED_MULTI";
	public static final String CATEGORY_UPDATED = "CategoryProxy_CATEGORY_UPDATED";
	public static final String CATEGORY_UPDATED_MULTI = "CategoryProxy_CATEGORY_UPDATED_MULTI";

	private XmlRpcDbTableDecorator<Category> clientModel;

	public CategoryProxy() {
		super(NAME);
		clientModel = new XmlRpcDbTableDecorator<Category>(XMLRPC_URL, XMLRPC_PROXY);
	}

	protected void addModelRow(Category row) {
		getModel().add(row);
	}
	
	protected void updateModelRow(Category row) {
		ArrayList<Category> model = getModel();
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).getId() == row.getId()) {
				model.set(i, row);
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, Category row) {
		ArrayList<Category> model = getModel();
		for (Category r : model) {
			if (idList.contains(r.getId())) {
				r.setPublicated(row.getPublicated());
			}
		}
	}

	protected void deleteModelRow(Category row) {
		Iterator<Category> i = getModel().iterator();
		while (i.hasNext()) {
			if (i.next().getId() == row.getId()) {
				i.remove();
			}
		}
	}

	protected void deleteModelRowset(ArrayList<Integer> idList) {
		Iterator<Category> i = getModel().iterator();
		while (i.hasNext()) {
			if (idList.contains(i.next().getId())) {
				i.remove();
			}
		}
	}

	public void add(final Category row) {
		clientModel.insert(row.getData(), new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				row.setId(result);

				// dzialanie off-line
				addModelRow(row);
				sendNotification(CATEGORY_ADDED, row, null);

				String message = "Kategoria została dodana";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void delete(final Category row) {
		clientModel.delete(row.getId(), new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRow(row);
				sendNotification(CATEGORY_DELETED, row, null);

				String message = "Kategoria została usunięta";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void delete(final ArrayList<Category> rowset) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Category model : rowset) {
			idList.add(model.getId());
		}

		clientModel.delete(idList, new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRowset(idList);
				sendNotification(CATEGORY_DELETED_MULTI, null, null);

				String message = "Kategorie zostały usunięte";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void edit(final Category row) {
		clientModel.update(row.getId(), row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRow(row);
				sendNotification(CATEGORY_UPDATED, row, null);

				String message = "Zmiany w kategoriach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}

	public void edit(ArrayList<Category> rowset, final Category row) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Category model : rowset) {
			idList.add(model.getId());
		}

		clientModel.update(idList, row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, row);
				sendNotification(CATEGORY_UPDATED_MULTI, row, null);

				String message = "Zmiany w kategoriach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}

	public Category findBy(Gallery gallery) {
		for (Category row : getModel()) {
			if (row.getId() == gallery.getCategoryId()) {
				return row;
			}
		}
		return null;
	}
	
	public void load(final AsyncCallback<Boolean> asyncCallback) {
		clientModel.findAll(new AsyncCallback<ArrayList<Category>>() {
			public void onSuccess(ArrayList<Category> result) {
				setModel(result);
				asyncCallback.onSuccess(true);
			}

			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);

				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
}