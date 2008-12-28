package info.widmogrod.gwt.library.client.ui.list;

import info.widmogrod.gwt.library.client.db.DataModel;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheckBoxListManager<T extends JavaScriptObject> extends Composite {
	private VerticalPanel verticalPanel;
	
	protected HashMap<T, CheckBoxList<T>> list = new HashMap<T, CheckBoxList<T>>();

	public CheckBoxListManager() {
		verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("100%");
		initWidget(verticalPanel);
		
		setStyleName("kx-CheckBoxListManager");
	}

	public void render() {
		list.clear();
		verticalPanel.clear();

		for (T row : getModel()) {
			CheckBoxList<T>	ch = new CheckBoxList<T>(this);
			ch.setModel(row);
			list.put(row, ch);

			// przekazywanie ustawień
			if (renderCallback != null) {
				ch.setRenderCallback(renderCallback);
			}
			if (clickListener != null) {
				ch.setClickListner(clickListener);
			}

			verticalPanel.add(ch);
			ch.render();
		}
	}
	
	public void refresh() {
		// TODO Może dobrze by bylo napidac this.list
		// nowymi wartosciami
		verticalPanel.clear();

		for (T row : getModel()) {
			CheckBoxList<T> ch;
			// obiekcik modelu nie ma wizualizacji, tworzymy!
			if (!list.containsKey(row)) {
				ch = new CheckBoxList<T>(this);
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

			verticalPanel.add(ch);
			ch.render();
		}
	}

	private RenderCallback<CheckBoxList<T>, T> renderCallback;
	
	public void setRenderCallback(RenderCallback<CheckBoxList<T>, T> callback) {
		renderCallback = callback;
		for (CheckBoxList<T> ch : list.values()) {
			ch.setRenderCallback(callback);
		}
	}
	
	private ClickListener clickListener;

	public void setClickListner(ClickListener listener) {
		clickListener = listener;
		for (CheckBoxList<T> ch : list.values()) {
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
	
	public HashMap<T, CheckBoxList<T>> getList() {
		return list;
	}

	public int getCountChecked() {
		int count = 0;
		for (CheckBoxList<T> ch : list.values()) {
			if (ch.isChecked()) {
				count++;
			}
		}
		return count;
	}

	public ArrayList<T> getCheckedModels() {
		ArrayList<T> r = new ArrayList<T>();
		for (CheckBoxList<T> ch : list.values()) {
			if (ch.isChecked()) {
				r.add(ch.getModel());
			}
		}
		return r;
	}

	public ArrayList<CheckBoxList<T>> getCheckedCheckBox() {
		ArrayList<CheckBoxList<T>> r = new ArrayList<CheckBoxList<T>>();
		for (CheckBoxList<T> ch : list.values()) {
			if (ch.isChecked()) {
				r.add(ch);
			}
		}
		return r;
	}

	public void setChecked(boolean checked) {
		for (CheckBoxList<T> ch : list.values()) {
			ch.setChecked(checked);
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
