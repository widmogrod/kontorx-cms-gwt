package info.widmogrod.gwt.library.client;

import java.util.HashMap;

public class Binding {
	public enum Type {LEFT, RIGHT, BOTH}

	HashMap<Bindable, Bindable> bindings = new HashMap<Bindable, Bindable>();

	public void add(Bindable left, Bindable right) {
		bindings.put(left, right);
	}

	public void noticeOnChange(Bindable bindable) {
		if (bindings.containsKey(bindable)){
			switch (type) {
			case BOTH:
			case LEFT:
				bindings.get(bindable).setValue(bindable.getValue());
				break;
			}
			
		}

		if (bindings.containsValue(bindable)) {
			switch (type) {
			case BOTH:
			case RIGHT:	
				for (Bindable b : bindings.keySet()) {
					if (bindings.get(b) == bindable) {
						b.setValue(bindable.getValue());
					}
				}						
				break;
			}
		}
	}

	public Type type = Type.BOTH;
	
	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void bind() {
		bind(Type.BOTH);
	}
	
	public void bind(Type type) {
		setType(type);
		for (Bindable bindable : bindings.keySet()) {
			bindable.setBinding(this);
			bindings.get(bindable).setBinding(this);
		}
	}
}
