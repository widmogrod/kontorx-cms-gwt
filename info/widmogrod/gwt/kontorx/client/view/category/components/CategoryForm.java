package info.widmogrod.gwt.kontorx.client.view.category.components;

import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.library.client.BindTextBox;
import info.widmogrod.gwt.library.client.Bindable;
import info.widmogrod.gwt.library.client.BindableListener;
import info.widmogrod.gwt.library.client.Binding;
import info.widmogrod.gwt.library.client.Binding.Type;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CategoryForm extends Composite {
	public enum Mode {
		SHOW("Edytuj","Usuń"),
		SHOW_MULTI("Edytuj","Usuń kategorie"),
		NEW("Dodaj","Usuń"),
		EDIT("Zapisz","Usuń"),
		EDIT_MULTI("Aktualizuj","Usuń kategorie");

		private String name;
		private String delete;

		private Mode(String name) {
			this.name = name;
		}
		
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

	private FlexTable editContentTable;
	private TextBox urlTextBox;
	private CheckBox publicatedCheckBox;
	private Button cancelButton;
	private Button deleteButton;
	private Button actionButton;
	private TextBox nameTextBox;
	
	public CategoryForm() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		horizontalPanel.setStyleName("kx-NavigationBar");

		actionButton = new Button();
		horizontalPanel.add(actionButton);
		actionButton.setText(Mode.NEW.getName());

		cancelButton = new Button();
		horizontalPanel.add(cancelButton);
		cancelButton.setText("Anuluj");

		deleteButton = new Button();
		horizontalPanel.add(deleteButton);
		horizontalPanel.setCellVerticalAlignment(deleteButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(deleteButton, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellWidth(deleteButton, "100%");
		deleteButton.setEnabled(false);
		deleteButton.setText(Mode.NEW.getDeleteName());
		
		editContentTable = new FlexTable();
		verticalPanel.add(editContentTable);
		editContentTable.setWidth("100%");
		verticalPanel.setCellVerticalAlignment(editContentTable, HasVerticalAlignment.ALIGN_TOP);
		verticalPanel.setCellHorizontalAlignment(editContentTable, HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel.setCellWidth(editContentTable, "100%");

		final Label label = new Label("Opublikować");
		editContentTable.setWidget(2, 0, label);

		publicatedCheckBox = new CheckBox();
		editContentTable.setWidget(2, 1, publicatedCheckBox);
		publicatedCheckBox.setText("Tak");

		final Label nazwaLabel = new Label("Nazwa");
		editContentTable.setWidget(0, 0, nazwaLabel);

		nameTextBox = new TextBox();
		editContentTable.setWidget(0, 1, nameTextBox);
		nameTextBox.setText("name");
		nameTextBox.setWidth("100%");
		
		final Label urlLabel = new Label("Url");
		editContentTable.setWidget(1, 0, urlLabel);

		urlTextBox = new TextBox();
		editContentTable.setWidget(1, 1, urlTextBox);
		editContentTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
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

	private CategoryVO model;

	public void cleanModel() {
		this.model = null;
		setModel(CategoryVO.get());
	}
	
	public void setModel(CategoryVO model) {
		this.model = model;
		nameTextBox.setText(model.getName());
		urlTextBox.setText(model.getUrl());
		getPublicatedCheckBox().setChecked(model.getPublicated());
	}

	public CategoryVO getModel() {
		model.setName(nameTextBox.getText());
		model.setUrl(urlTextBox.getText());
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}

	public CategoryVO getNewModel() {
		CategoryVO model = CategoryVO.get();
		model.setName(nameTextBox.getText());
		model.setUrl(urlTextBox.getText());
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	private Mode mode = Mode.NEW;
	
	public void setMode(Mode mode) {
		switch (mode) {
			default:
			case SHOW:
				getActionButton().setText(Mode.SHOW.getName());
				getDeleteButton().setText(Mode.SHOW.getDeleteName());
				getDeleteButton().setEnabled(true);
				getDeleteButton().setVisible(true);
				getEditContentTable().setVisible(false);
				break;
			case SHOW_MULTI:
				getActionButton().setText(Mode.SHOW_MULTI.getName());
				getDeleteButton().setText(Mode.SHOW_MULTI.getDeleteName());
				getDeleteButton().setEnabled(true);
				getDeleteButton().setVisible(true);
				getEditContentTable().setVisible(false);
				break;
			case NEW:
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.NEW.getName());
				getDeleteButton().setText(Mode.NEW.getDeleteName());
				getDeleteButton().setVisible(false);
				getEditContentTable().setVisible(true);
				break;
			case EDIT:
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.EDIT.getName());
				getDeleteButton().setVisible(false);
				getEditContentTable().setVisible(true);
				break;
			case EDIT_MULTI:
				getNameTextBox().setEnabled(false);
				getUrlTextBox().setEnabled(false);
				getActionButton().setHTML(Mode.EDIT_MULTI.getName());
				getDeleteButton().setVisible(false);
				getEditContentTable().setVisible(true);
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
	protected CheckBox getPublicatedCheckBox() {
		return publicatedCheckBox;
	}
	public TextBox getUrlTextBox() {
		return urlTextBox;
	}
	protected FlexTable getEditContentTable() {
		return editContentTable;
	}
}
