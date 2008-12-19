package info.widmogrod.gwt.library.client;

public interface Bindable {
	public void setBinding(Binding binding);
	public void setValue(Object value);
	public Object getValue();
	public void setBindableListener(BindableListener listner);
}
