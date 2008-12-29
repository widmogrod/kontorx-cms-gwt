package info.widmogrod.gwt.kontorx.client.model;

import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.library.client.db.xmlrpc.JsonDataRowset;
import info.widmogrod.gwt.library.client.db.xmlrpc.XmlRpcDbTableDecorator;
import info.widmogrod.gwt.library.client.puremvc.patterns.ProxyModel;
import info.widmogrod.gwt.library.client.ui.MessageBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;

public class ImageProxy extends ProxyModel<ImageVO> {

	public static final String NAME = "ImageProxy";

	// XmlRpc settings
	public static final String XMLRPC_URL = GWT.getHostPageBaseURL() + "gwt/rpc";
	public static final String XMLRPC_PROXY = "image";
	public static final String JSON_DATA_URL = GWT.getHostPageBaseURL() + "image/list/format/gwtjson";
	
	public static final String FORM_ADD_URL = GWT.getHostPageBaseURL() + "image/upload/format/gwtjson";

	// Notifications
	public static final String BLOCK_ACTION_NEW = "ImageProxy_BLOCK_ACTION_NEW";
	public static final String BLOCK_ACTION_CANCEL = "ImageProxy_BLOCK_ACTION_CANCEL";
	public static final String BLOCK_ACTION_SHOW = "ImageProxy_BLOCK_ACTION_SHOW";
//	public static final String BLOCK_ACTION_SHOW_NONE = "ImageProxy_BLOCK_ACTION_SHOW_NONE";
	public static final String BLOCK_ACTION_SHOW_MULTI = "ImageProxy_BLOCK_ACTION_SHOW_MULTI";
	public static final String BLOCK_ACTION_EDIT = "ImageProxy_BLOCK_ACTION_EDIT";
	public static final String BLOCK_ACTION_EDIT_MULTI = "ImageProxy_BLOCK_ACTION_EDIT_MULTI";

	public static final String IMAGE_ADDED = "ImageProxy_IMAGE_ADDED";
	public static final String IMAGE_DELETED = "ImageProxy_IMAGE_DELETED";
	public static final String IMAGE_DELETED_MULTI = "ImageProxy_IMAGE_DELETED_MULTI";
	public static final String IMAGE_UPDATED = "ImageProxy_IMAGE_UPDATED";
	public static final String IMAGE_UPDATED_MULTI = "ImageProxy_IMAGE_UPDATED_MULTI";
	public static final String IMAGE_UPDATED_GALLERY = "ImageProxy_IMAGE_UPDATED_GALLERY";

	private XmlRpcDbTableDecorator<ImageVO> clientModel;
	private JsonDataRowset<ImageVO> jsonDataRowset;

	public ImageProxy() {
		super(NAME);
		clientModel = new XmlRpcDbTableDecorator<ImageVO>(XMLRPC_URL, XMLRPC_PROXY);
		jsonDataRowset = new JsonDataRowset<ImageVO>(JSON_DATA_URL);
	}

	protected void addModelRow(ImageVO row) {
		getModel().add(row);
	}
	
	protected void updateModelRow(ImageVO row) {
		ArrayList<ImageVO> model = getModel();
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).getId() == row.getId()) {
				model.set(i, row);
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, ImageVO row) {
		ArrayList<ImageVO> model = getModel();
		for (ImageVO r : model) {
			if (idList.contains(r.getId())) {
				r.setPublicated(row.getPublicated());
			}
		}
	}
	
	protected void updateModelRowset(ArrayList<Integer> idList, GalleryVO row) {
		ArrayList<ImageVO> model = getModel();
		for (ImageVO r : model) {
			if (idList.contains(r.getId())) {
				r.setGalleryId(row.getId());
			}
		}
	}

	protected void deleteModelRow(ImageVO row) {
		Iterator<ImageVO> i = getModel().iterator();
		while (i.hasNext()) {
			if (i.next().getId() == row.getId()) {
				i.remove();
			}
		}
	}

	protected void deleteModelRowset(ArrayList<Integer> idList) {
		Iterator<ImageVO> i = getModel().iterator();
		while (i.hasNext()) {
			if (idList.contains(i.next().getId())) {
				i.remove();
			}
		}
	}

	
	public void add(FormSubmitCompleteEvent result) {
		JSONValue v = JSONParser.parse(result.getResults());
		JSONObject o = v.isObject();
		
		ImageVO row = null;
		if (o != null) {
			row = (ImageVO) o.getJavaScriptObject();

			addModelRow(row);
			sendNotification(IMAGE_ADDED, row, null);

			String message = "Plik został dodany";
			sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
		} else {
			String message = "Plik nie został dodany";
			sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
		}

//		clientModel.insert(row.getData(), new AsyncCallback<Integer>() {
//			public void onSuccess(Integer result) {
//				row.setId(result);
//
//				// dzialanie off-line
//				addModelRow(row);
//				sendNotification(IMAGE_ADDED, row, null);
//
//				String message = "Grafika została dodana";
//				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.INFO);
//			}
//
//			public void onFailure(Throwable caught) {
//				String message = caught.getMessage();
//				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, InfoBox.ERROR);
//			}
//		});
	}
	
	public void delete(final ImageVO row) {
		clientModel.delete(row.getId(), new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRow(row);
				sendNotification(IMAGE_DELETED, row, null);

				String message = "Grafika została usunięta";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void delete(final ArrayList<ImageVO> rowset) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (ImageVO model : rowset) {
			idList.add(model.getId());
		}

		clientModel.delete(idList, new AsyncCallback<Object>(){
			public void onSuccess(Object result) {
				// dzialanie off-line
				deleteModelRowset(idList);
				sendNotification(IMAGE_DELETED_MULTI, null, null);

				String message = "Grafiki zostały usunięte";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void edit(final ImageVO row) {
		clientModel.update(row.getId(), row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRow(row);
				sendNotification(IMAGE_UPDATED, row, null);

				String message = "Zmiany w grafikach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}

	public void edit(ArrayList<ImageVO> rowset, final ImageVO row) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (ImageVO model : rowset) {
			idList.add(model.getId());
		}

		clientModel.update(idList, row.getData(), new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, row);
				sendNotification(IMAGE_UPDATED_MULTI, row, null);

				String message = "Zmiany w grafikach zostały zapisane";
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
	}
	
	public void update(final ArrayList<ImageVO> rowset, final GalleryVO gallery) {
		final ArrayList<Integer> idList = new ArrayList<Integer>();
		for (ImageVO model : rowset) {
			idList.add(model.getId());
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put(ImageVO.FIELD_GALLERY_ID, gallery.getId());

		clientModel.update(idList, data, new AsyncCallback<Object>() {
			public void onSuccess(Object result) {
				// dzialanie off-line
				updateModelRowset(idList, gallery);
				sendNotification(IMAGE_UPDATED_GALLERY, rowset, null);
//				sendNotification(GalleryProxy.BLOCK_ACTION_SELECT, gallery, null);

				String message = "Grafiki zostały przypisane do galerii " + gallery.getName();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.INFO);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
			}
		});
		
	}

	public ArrayList<ImageVO> findBy(GalleryVO gallery) {
		ArrayList<ImageVO> result = new ArrayList<ImageVO>();
		for (ImageVO row : getModel()) {
			if (row.getGalleryId() == gallery.getId()) {
				result.add(row);
			}
		}
		return result;
	}
	
	
	public void load(final AsyncCallback<Boolean> asyncCallback) {
		jsonDataRowset.findAll(new AsyncCallback<ArrayList<ImageVO>>() {
			public void onSuccess(ArrayList<ImageVO> result) {
				setModel(result);
				asyncCallback.onSuccess(true);
			}

			public void onFailure(Throwable caught) {
				String message = caught.getMessage();
				sendNotification(InfoBoxMediator.DISPLAY_MESSAGE, message, MessageBox.ERROR);
				
				asyncCallback.onFailure(caught);
			}
		});
	}
}
