package info.widmogrod.gwt.kontorx.client.model;

import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.library.client.db.xmlrpc.XmlRpcDbTableDecorator;
import info.widmogrod.gwt.library.client.puremvc.patterns.ProxyModel;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GalleryProxy extends ProxyModel<Gallery> {

	public static String NAME = "GalleryProxy";
	
	// XmlRpc settings
	public static String XMLRPC_URL = GWT.getHostPageBaseURL() + "gwt/rpc";
	public static String XMLRPC_PROXY = "gallery";

	// Notifications
	public static String BLOCK_ACTION_NEW = "GalleryProxy_GALLERY_NEW";
	public static String BLOCK_ACTION_CANCEL = "GalleryProxy_GALLERY_CANCEL";
	public static String GALLERY_ADDED = "GalleryProxy_GALLERY_ADDED";
	public static String BLOCK_ACTION_LOAD = "GalleryProxy_GALLERY_LOADED";
	public static String BLOCK_ACTION_LOAD_MULTI = "GalleryProxy_GALLERY_LOADED_MULTI";
	public static String GALLERY_DELETED = "GalleryProxy_GALLERY_DELETED";
	public static String GALLERY_DELETED_MULTI = "GalleryProxy_GALLERY_DELETED_MULTI";
	public static String GALLERY_UPDATED = "GalleryProxy_GALLERY_UPDATED";
	public static String GALLERY_UPDATED_MULTI = "GalleryProxy_GALLERY_UPDATED_MULTI";
	public static String GALLERY_UPDATED_CATEGORY = "GalleryProxy_GALLERY_UPDATE_CATEGORY_MULTI";
	
	public static String GALLERY_LIST_REFRESH = "GalleryProxy_GALLERY_LIST_REFRESH";
	
	private XmlRpcDbTableDecorator<Gallery> clientModel;

	public GalleryProxy() {
		super(NAME);
		clientModel = new XmlRpcDbTableDecorator<Gallery>(XMLRPC_URL, XMLRPC_PROXY);
	}
	
	protected void addModelRow(Gallery row) {
		getModel().add(row);
	}
	
	protected void updateModelRow(Gallery row) {
		ArrayList<Gallery> model = getModel();
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).getId() == row.getId()) {
				model.set(i, row);
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, Gallery row) {
		ArrayList<Gallery> model = getModel();
		for (Gallery r : model) {
			if (idList.contains(r.getId())) {
				r.setPublicated(row.getPublicated());
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, Category category) {
		ArrayList<Gallery> model = getModel();
		for (Gallery r : model) {
			if (idList.contains(r.getId())) {
				r.setCategoryId(category.getId());
			}
		}
		
	}

	protected void deleteModelRow(Gallery row) {
		Iterator<Gallery> i = getModel().iterator();
		while (i.hasNext()) {
			if (i.next().getId() == row.getId()) {
				i.remove();
			}
		}
	}

	protected void deleteModelRowset(ArrayList<Integer> idList) {
		Iterator<Gallery> i = getModel().iterator();
		while (i.hasNext()) {
			if (idList.contains(i.next().getId())) {
				i.remove();
			}
		}
	}
	
	public void add(final Gallery row) {
		clientModel.insert(row.getData(), new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				row.setId(result);

				// dzialanie off-line
				addModelRow(row);
				sendNotification(GALLERY_ADDED, row, null);
				
				String message = "Galeria została dodana";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}

	public void delete(final Gallery row) {
		clientModel.delete(row.getId(), new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRow(row);
				sendNotification(GALLERY_DELETED, row, null);

				String message = "Galeria została usunięta";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void delete(final ArrayList<Gallery> rowset) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Gallery model : rowset) {
			idList.add(model.getId());
		}

		clientModel.delete(idList, new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRowset(idList);
				sendNotification(GALLERY_DELETED_MULTI, null, null);
				
				String message = "Galerie zostały usunięte";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void edit(final Gallery row) {
		clientModel.update(row.getId(), row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRow(row);
				sendNotification(GALLERY_UPDATED, row, null);

				String message = "Zmiany w galerii zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}

	public void edit(ArrayList<Gallery> rowset, final Gallery row) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Gallery model : rowset) {
			idList.add(model.getId());
		}

		clientModel.update(idList, row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, row);
				sendNotification(GALLERY_UPDATED_MULTI, row, null);

				String message = "Zmiany w galeriach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
	}
	
	public void update(final ArrayList<Gallery> rowset, final Category category) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Gallery model : rowset) {
			idList.add(model.getId());
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put(Gallery.FIELD_CATEGORY_ID, category.getId());

		clientModel.update(idList, data, new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, category);
				sendNotification(GALLERY_UPDATED_CATEGORY, rowset, null);

				String message = "Galerie zostały przypisane do kategorii " + category.getName();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
			}
		});
		
	}

	public void load(final AsyncCallback<Boolean> asyncCallback) {
		clientModel.findAll(new AsyncCallback<ArrayList<Gallery>>() {
			public void onSuccess(ArrayList<Gallery> result) {
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
