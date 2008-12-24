package info.widmogrod.gwt.library.client.ui.list;

import info.widmogrod.gwt.library.client.db.DataModel;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class ImageListManager<T extends JavaScriptObject> extends Composite {
	private FlowPanel flowPanel;
	
	protected HashMap<T, ImageList<T>> list = new HashMap<T, ImageList<T>>();

	public ImageListManager() {
		flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		setStyleName("kx-ImageListManager");
	}

	public void render() {
		list.clear();
		flowPanel.clear();

		for (T row : getModel()) {
			ImageList<T> ich = new ImageList<T>(this);
			ich.setModel(row);
			list.put(row, ich);

			// przekazywanie ustawień
			if (renderCallback != null) {
				ich.setRenderCallback(renderCallback);
			}
			if (clickListener != null) {
				ich.setClickListner(clickListener);
			}

			flowPanel.add(ich);
			ich.render();
		}
	}
	
	public void refresh() {
		// TODO Może dobrze by bylo napidac this.list
		// nowymi wartosciami
		flowPanel.clear();

		for (T row : getModel()) {
			ImageList<T> ch;
			// obiekcik modelu nie ma wizualizacji, tworzymy!
			if (!list.containsKey(row)) {
				ch = new ImageList<T>(this);
				ch.setModel(row);
				list.put(row, ch);
			} else {
				ch = list.get(row);
			}

			// przekazywanie ustawień
			if (renderCallback != null) {
				ch.setRenderCallback(renderCallback);
			}
			if (clickListener != null) {
				ch.setClickListner(clickListener);
			}

			flowPanel.add(ch);
			ch.render();
		}
	}

	private RenderCallback<ImageList<T>, T> renderCallback;
	
	public void setRenderCallback(RenderCallback<ImageList<T>, T> callback) {
		renderCallback = callback;
		for (ImageList<T> ch : list.values()) {
			ch.setRenderCallback(callback);
		}
	}
	
	private ClickListener clickListener;

	public void setClickListner(ClickListener listener) {
		clickListener = listener;
		for (ImageList<T> ch : list.values()) {
			ch.setClickListner(listener);
		}
	}

	private DataModel<T> model = null;

	public void setModel(DataModel<T> model) {
		this.model = model;
	}

	public ArrayList<T> getModel() {
		return model.getModel();
	}

	public int getCountChecked() {
		int count = 0;
		for (ImageList<T> ch : list.values()) {
			if (ch.isChecked()) {
				count++;
			}
		}
		return count;
	}

	public ArrayList<T> getCheckedModels() {
		ArrayList<T> r = new ArrayList<T>();
		for (ImageList<T> ch : list.values()) {
			if (ch.isChecked()) {
				r.add(ch.getModel());
			}
		}
		return r;
	}

	public ArrayList<ImageList<T>> getCheckedCheckBox() {
		ArrayList<ImageList<T>> r = new ArrayList<ImageList<T>>();
		for (ImageList<T> ch : list.values()) {
			if (ch.isChecked()) {
				r.add(ch);
			}
		}
		return r;
	}

	public void setChecked(boolean checked) {
		for (ImageList<T> ch : list.values()) {
			ch.setChecked(checked);
		}
	}
	
	public void setCheckedFlip() {
		for (ImageList<T> ch : list.values()) {
			ch.setChecked(!ch.isChecked());
		}
	}
	
	public void setCheckedByModelRow(T row) {
		for (T m : list.keySet()) {
			if (compareObject(m, row)) {
				list.get(m).setChecked(true);
			} else {
				list.get(m).setChecked(false);
			}
		}
	}

	public void setCheckedByModelRowset(ArrayList<T> rowset) {
		for (T t : rowset) {
			if (list.containsKey(t)) {
				list.get(t).setChecked(true);
			}
		}
	}

	public boolean compareObject(T o1, T o2) {
		return o1 == o2;
	}
}
