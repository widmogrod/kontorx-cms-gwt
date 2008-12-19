package info.widmogrod.gwt.library.client.ui;

import com.google.gwt.core.client.JavaScriptObject;

public interface CheckBoxListRenderCallback <T extends JavaScriptObject> {
	public void onRender(CheckBoxList<T> component, T model);
}
