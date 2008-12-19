package info.widmogrod.gwt.library.client.puremvc.patterns;

import info.widmogrod.gwt.library.client.db.DataModel;

import java.util.ArrayList;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.google.gwt.core.client.JavaScriptObject;

abstract public class ProxyModel<T extends JavaScriptObject> extends Proxy implements DataModel<T>{

	public ProxyModel(String proxyName) {
		super(proxyName);
	}

	private ArrayList<T> model = new ArrayList<T>();

	public ArrayList<T> getModel() {
		return model;
	}
	
	public void setModel(ArrayList<T> model) {
		this.model = model;
	}	
}
