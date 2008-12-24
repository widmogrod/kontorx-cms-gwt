package info.widmogrod.gwt.kontorx.client.model;

import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.library.client.db.xmlrpc.XmlRpcDbTableDecorator;
import info.widmogrod.gwt.library.client.puremvc.patterns.ProxyModel;
import info.widmogrod.gwt.library.client.ui.MessageBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GalleryProxy extends ProxyModel<GalleryVO> {

	public static String NAME = "GalleryProxy";
	
	// XmlRpc settings
	public static String XMLRPC_URL = GWT.getHostPageBaseURL() + "gwt/rpc";
	public static String XMLRPC_PROXY = "gallery";

	// Notifications
	public static final String BLOCK_ACTION_NEW = "GalleryProxy_BLOCK_ACTION_NEW";
	public static final String BLOCK_ACTION_CANCEL = "GalleryProxy_BLOCK_ACTION_CANCEL";
	public static final String BLOCK_ACTION_SELECT = "GalleryProxy_BLOCK_ACTION_SELECT";
	public static final String BLOCK_ACTION_SELECT_MULTI = "GalleryProxy_BLOCK_ACTION_SELECT_MULTI";
	public static final String BLOCK_ACTION_EDIT = "GalleryProxy_BLOCK_ACTION_EDIT";
	public static final String BLOCK_ACTION_EDIT_MULTI = "GalleryProxy_BLOCK_ACTION_EDIT_MULTI";
	public static final String GALLERY_ADDED = "GalleryProxy_GALLERY_ADDED";
	public static final String GALLERY_DELETED = "GalleryProxy_GALLERY_DELETED";
	public static final String GALLERY_DELETED_MULTI = "GalleryProxy_GALLERY_DELETED_MULTI";
	public static final String GALLERY_UPDATED = "GalleryProxy_GALLERY_UPDATED";
	public static final String GALLERY_UPDATED_MULTI = "GalleryProxy_GALLERY_UPDATED_MULTI";
	public static final String GALLERY_UPDATED_CATEGORY = "GalleryProxy_GALLERY_UPDATED_CATEGORY";
	
	private XmlRpcDbTableDecorator<GalleryVO> clientModel;

	public GalleryProxy() {
		super(NAME);
		clientModel = new XmlRpcDbTableDecorator<GalleryVO>(XMLRPC_URL, XMLRPC_PROXY);
	}
	
	protected void addModelRow(GalleryVO row) {
		getModel().add(row);
	}
	
	protected void updateModelRow(GalleryVO row) {
		ArrayList<GalleryVO> model = getModel();
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).getId() == row.getId()) {
				model.set(i, row);
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, GalleryVO row) {
		ArrayList<GalleryVO> model = getModel();
		for (GalleryVO r : model) {
			if (idList.contains(r.getId())) {
				r.setPublicated(row.getPublicated());
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, CategoryVO category) {
		ArrayList<GalleryVO> model = getModel();
		for (GalleryVO r : model) {
			if (idList.contains(r.getId())) {
				r.setCategoryId(category.getId());
			}
		}
		
	}

	protected void deleteModelRow(GalleryVO row) {
		Iterator<GalleryVO> i = getModel().iterator();
		while (i.hasNext()) {
			if (i.next().getId() == row.getId()) {
				i.remove();
			}
		}
	}

	protected void deleteModelRowset(ArrayList<Integer> idList) {
		Iterator<GalleryVO> i = getModel().iterator();
		while (i.hasNext()) {
			if (idList.contains(i.next().getId())) {
				i.remove();
			}
		}
	}
	
	public void add(final GalleryVO row) {
		clientModel.insert(row.getData(), new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				row.setId(result);

				// dzialanie off-line
				addModelRow(row);
				sendNotification(GALLERY_ADDED, row, null);
				
				String message = "Galeria została dodana";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}

	public void delete(final GalleryVO row) {
		clientModel.delete(row.getId(), new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRow(row);
				sendNotification(GALLERY_DELETED, row, null);

				String message = "Galeria została usunięta";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void delete(final ArrayList<GalleryVO> rowset) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (GalleryVO model : rowset) {
			idList.add(model.getId());
		}

		clientModel.delete(idList, new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRowset(idList);
				sendNotification(GALLERY_DELETED_MULTI, null, null);
				
				String message = "Galerie zostały usunięte";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void edit(final GalleryVO row) {
		clientModel.update(row.getId(), row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRow(row);
				sendNotification(GALLERY_UPDATED, row, null);

				String message = "Zmiany w galerii zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}

	public void edit(ArrayList<GalleryVO> rowset, final GalleryVO row) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (GalleryVO model : rowset) {
			idList.add(model.getId());
		}

		clientModel.update(idList, row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, row);
				sendNotification(GALLERY_UPDATED_MULTI, row, null);

				String message = "Zmiany w galeriach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void update(final ArrayList<GalleryVO> rowset, final CategoryVO category) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (GalleryVO model : rowset) {
			idList.add(model.getId());
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put(GalleryVO.FIELD_CATEGORY_ID, category.getId());

		clientModel.update(idList, data, new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, category);
				sendNotification(GALLERY_UPDATED_CATEGORY, rowset, null);

				String message = "Galerie zostały przypisane do kategorii " + category.getName();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
		
	}
	
	public GalleryVO findBy(ImageVO image) {
		for (GalleryVO row : getModel()) {
			if (row.getId() == image.getGalleryId()) {
				return row;
			}
		}
		return null;
	}

	public void load(final AsyncCallback<Boolean> asyncCallback) {
		clientModel.findAll(new AsyncCallback<ArrayList<GalleryVO>>() {
			public void onSuccess(ArrayList<GalleryVO> result) {
				setModel(result);
				asyncCallback.onSuccess(true);
			}

			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);

				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
}
