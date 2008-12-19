package info.widmogrod.gwt.kontorx.client.view.gallery.components;

import info.widmogrod.gwt.kontorx.client.model.Category;
import info.widmogrod.gwt.kontorx.client.model.Gallery;
import info.widmogrod.gwt.library.client.BindTextBox;
import info.widmogrod.gwt.library.client.Bindable;
import info.widmogrod.gwt.library.client.BindableListener;
import info.widmogrod.gwt.library.client.Binding;
import info.widmogrod.gwt.library.client.Binding.Type;
import info.widmogrod.gwt.library.client.ui.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.CheckBoxListManager;
import info.widmogrod.gwt.library.client.ui.DropDownList;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class GalleryForm extends Composite {
	public enum Mode {
		SHOW("Edytuj",null),
		ADD("Dodaj","Usuń"),
		UPDATE("Zapisz","Usuń"),
		UPDATE_MULTI("Aktualizuj","Usuń zaznaczone");

		private String name;
		private String delete;

		private Mode(String name, String delete) {
			this.name = name;
			this.delete = delete;
		}

		public String getName() {
			return name;
		}
		
		public String getDeleteName() {
			return delete;
		}
	};

	private TextBox nameTextBox;
	private TextBox urlTextBox;
	private CheckBox publicatedCheckBox;

	private Button cancelButton;
	private Button deleteButton;
	private Button actionButton;

	private DropDownList categoryDropDownList;
	private CheckBoxListManager<Category> categoryBoxListManager;

	public GalleryForm() {
		final FlexTable flexTable = new FlexTable();
		initWidget(flexTable);

		final Label nazwaLabel = new Label("Nazwa");
		flexTable.setWidget(1, 0, nazwaLabel);

		nameTextBox = new TextBox();
		flexTable.setWidget(1, 1, nameTextBox);
		nameTextBox.setText("name");
		nameTextBox.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("100%");
		flexTable.setWidget(0, 1, horizontalPanel);
		flexTable.getCellFormatter().setWidth(0, 1, "100%");
		flexTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

		actionButton = new Button();
		horizontalPanel.add(actionButton);
		actionButton.setText(Mode.ADD.getName());

		cancelButton = new Button();
		horizontalPanel.add(cancelButton);
		cancelButton.setText("Anuluj");
		
		categoryDropDownList = new DropDownList("Kategoria");
		horizontalPanel.add(categoryDropDownList);

		categoryBoxListManager = new CheckBoxListManager<Category>() {
			@Override
			public void setCheckedByModelRow(Category model) {
				// porownywanie obiektow musi byc po ID by wszystko dzialalo!
				for (CheckBoxList<Category> ch : list.values()) {
					if (ch.getModel().getId() == model.getId()) {
						ch.setChecked(true);
					} else {
						ch.setChecked(false);
					}
				}
			}
		};
		categoryDropDownList.add(categoryBoxListManager);

//		DropDownList categoryDropDownList2 = new DropDownList("Kategoria 222");
//		horizontalPanel.add(categoryDropDownList2);
//		categoryDropDownList2.add(new CheckBoxListManager<Category>());
		
		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel.setCellHorizontalAlignment(horizontalPanel_1, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellWidth(horizontalPanel_1, "100%");

		deleteButton = new Button();
		horizontalPanel_1.add(deleteButton);
		deleteButton.setEnabled(false);
		deleteButton.setText(Mode.ADD.getDeleteName());

		final Label label = new Label("Opublikować");
		flexTable.setWidget(3, 0, label);

		publicatedCheckBox = new CheckBox();
		flexTable.setWidget(3, 1, publicatedCheckBox);
		publicatedCheckBox.setText("Tak");

		final Label urlLabel = new Label("Url");
		flexTable.setWidget(2, 0, urlLabel);

		urlTextBox = new TextBox();
		flexTable.setWidget(2, 1, urlTextBox);
		urlTextBox.setText("url");
		urlTextBox.setWidth("100%");
		
		Binding binding = new Binding();
		binding.add(new BindTextBox(nameTextBox), new BindTextBox(urlTextBox, new BindableListener() {
			public Object onSet(Object value, Bindable sender) {
				String text = (String) value;
				text = text.toLowerCase();
				text = text.replaceAll(" ", "-");
				return text;
			}
		}));
		binding.bind(Type.LEFT);
	}

	public TextBox getNameTextBox() {
		return nameTextBox;
	}

	private Gallery model;

	public void cleanModel() {
		this.model = null;
		setModel(Gallery.get());
	}
	
	public void setModel(Gallery model) {
		this.model = model;
		nameTextBox.setText(model.getName());
		urlTextBox.setText(model.getUrl());
		getPublicatedCheckBox().setChecked(model.getPublicated());
	}

	public Gallery getModel() {
		model.setName(nameTextBox.getText());
		model.setUrl(urlTextBox.getText());
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	public Gallery getNewModel() {
		Gallery model = Gallery.get();
		model.setName(nameTextBox.getText());
		model.setUrl(urlTextBox.getText());
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	private Mode mode = Mode.ADD;
	
	public void setMode(Mode mode) {
		switch (mode) {
			default:
			case ADD:
				getCategoryDropDownList().setVisible(false);
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.ADD.getName());
				getDeleteButton().setText(Mode.ADD.getDeleteName());
				getDeleteButton().setEnabled(false);
				break;
			case UPDATE:
				getCategoryDropDownList().setVisible(true);
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.UPDATE.getName());
				getDeleteButton().setText(Mode.UPDATE.getDeleteName());
				getDeleteButton().setEnabled(true);
				break;
			case UPDATE_MULTI:
				getCategoryDropDownList().setVisible(true);
				getNameTextBox().setEnabled(false);
				getUrlTextBox().setEnabled(false);
				getActionButton().setHTML(Mode.UPDATE_MULTI.getName());
				getDeleteButton().setText(Mode.UPDATE_MULTI.getDeleteName());
				getDeleteButton().setEnabled(true);
				break;
		}
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}

	public Button getActionButton() {
		return actionButton;
	}
	public Button getDeleteButton() {
		return deleteButton;
	}
	public Button getCancelButton() {
		return cancelButton;
	}
	public DropDownList getCategoryDropDownList() {
		return categoryDropDownList;
	}
	public CheckBoxListManager<Category> getCategoryCheckBoxListManager() {
		return categoryBoxListManager;
	}
	protected CheckBox getPublicatedCheckBox() {
		return publicatedCheckBox;
	}
	public TextBox getUrlTextBox() {
		return urlTextBox;
	}
}
