package info.widmogrod.gwt.kontorx.client.view.gallery.components;

import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.library.client.BindTextBox;
import info.widmogrod.gwt.library.client.Bindable;
import info.widmogrod.gwt.library.client.BindableListener;
import info.widmogrod.gwt.library.client.Binding;
import info.widmogrod.gwt.library.client.Binding.Type;
import info.widmogrod.gwt.library.client.ui.DropDownMenu;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GalleryForm extends Composite {
	public enum Mode {
		SHOW("Edytuj","Usuń"),
		SHOW_MULTI("Edytuj","Usuń kategorie"),
		NEW("Dodaj","Usuń"),
		EDIT("Zapisz","Usuń"),
		EDIT_MULTI("Aktualizuj","Usuń zaznaczone");

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

	private FlexTable editContentTable;
	private TextBox nameTextBox;
	private TextBox urlTextBox;
	private CheckBox publicatedCheckBox;

	private Button cancelButton;
	private Button deleteButton;
	private Button actionButton;

	private DropDownMenu categoryDropDownList;
	private CheckBoxListManager<CategoryVO> categoryBoxListManager;

	public GalleryForm() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellVerticalAlignment(horizontalPanel, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel.setWidth("100%");
		horizontalPanel.setStyleName("kx-NavigationBar");

		actionButton = new Button();
		horizontalPanel.add(actionButton);
		actionButton.setText(Mode.NEW.getName());

		cancelButton = new Button();
		horizontalPanel.add(cancelButton);
		cancelButton.setText("Anuluj");
		
		categoryDropDownList = new DropDownMenu("Kategoria");
		horizontalPanel.add(categoryDropDownList);
		horizontalPanel.setCellVerticalAlignment(categoryDropDownList, HasVerticalAlignment.ALIGN_MIDDLE);

		categoryBoxListManager = new CheckBoxListManager<CategoryVO>() {
			@Override
			public boolean compareObject(CategoryVO o1, CategoryVO o2) {
				return o1.getId() == o2.getId();
			}
		};
//		{
//			@Override
//			public void setCheckedByModelRow(CategoryVO model) {
//				// porownywanie obiektow musi byc po ID by wszystko dzialalo!
//				for (CheckBoxList<CategoryVO> ch : list.values()) {
//					if (ch.getModel().getId() == model.getId()) {
//						ch.setChecked(true);
//					} else {
//						ch.setChecked(false);
//					}
//				}
//			}
//		};
		categoryDropDownList.add(categoryBoxListManager);

		final SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);

		deleteButton = new Button();
		horizontalPanel.add(deleteButton);
		horizontalPanel.setCellHorizontalAlignment(deleteButton, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellVerticalAlignment(deleteButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellWidth(deleteButton, "100%");
		deleteButton.setEnabled(false);
		deleteButton.setText(Mode.NEW.getDeleteName());

//		DropDownList categoryDropDownList2 = new DropDownList("Kategoria 222");
//		horizontalPanel.add(categoryDropDownList2);
//		categoryDropDownList2.add(new CheckBoxListManager<Category>());
		
		editContentTable = new FlexTable();
		verticalPanel.add(editContentTable);
		verticalPanel.setCellVerticalAlignment(editContentTable, HasVerticalAlignment.ALIGN_TOP);
		editContentTable.setWidth("100%");

		final Label nazwaLabel = new Label("Nazwa");
		editContentTable.setWidget(0, 0, nazwaLabel);

		nameTextBox = new TextBox();
		editContentTable.setWidget(0, 1, nameTextBox);
		nameTextBox.setText("name");
		nameTextBox.setWidth("100%");

		final Label label = new Label("Opublikować");
		editContentTable.setWidget(2, 0, label);

		publicatedCheckBox = new CheckBox();
		editContentTable.setWidget(2, 1, publicatedCheckBox);
		publicatedCheckBox.setText("Tak");

		final Label urlLabel = new Label("Url");
		editContentTable.setWidget(1, 0, urlLabel);

		urlTextBox = new TextBox();
		editContentTable.setWidget(1, 1, urlTextBox);
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

	private GalleryVO model;

	public void cleanModel() {
		this.model = null;
		setModel(GalleryVO.get());
	}
	
	public void setModel(GalleryVO model) {
		this.model = model;
		nameTextBox.setText(model.getName());
		urlTextBox.setText(model.getUrl());
		getPublicatedCheckBox().setChecked(model.getPublicated());
	}

	public GalleryVO getModel() {
		model.setName(nameTextBox.getText());
		model.setUrl(urlTextBox.getText());
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	public GalleryVO getNewModel() {
		GalleryVO model = GalleryVO.get();
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
				getCategoryDropDownList().setVisible(true);
				getActionButton().setText(Mode.SHOW.getName());
				getDeleteButton().setText(Mode.SHOW.getDeleteName());
				getDeleteButton().setEnabled(true);
				getDeleteButton().setVisible(true);
				getEditContentTable().setVisible(false);
				break;
			case SHOW_MULTI:
				getCategoryDropDownList().setVisible(true);
				getActionButton().setText(Mode.SHOW_MULTI.getName());
				getDeleteButton().setText(Mode.SHOW_MULTI.getDeleteName());
				getDeleteButton().setEnabled(true);
				getDeleteButton().setVisible(true);
				getEditContentTable().setVisible(false);
				break;
			case NEW:
				getCategoryDropDownList().setVisible(false);
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.NEW.getName());
				getDeleteButton().setText(Mode.NEW.getDeleteName());
				getDeleteButton().setVisible(false);
				getEditContentTable().setVisible(true);
				break;
			case EDIT:
				getCategoryDropDownList().setVisible(false);
				getNameTextBox().setEnabled(true);
				getUrlTextBox().setEnabled(true);
				getActionButton().setText(Mode.EDIT.getName());
				getDeleteButton().setText(Mode.EDIT.getDeleteName());
				getDeleteButton().setVisible(false);
				getEditContentTable().setVisible(true);
				break;
			case EDIT_MULTI:
				getCategoryDropDownList().setVisible(false);
				getNameTextBox().setEnabled(false);
				getUrlTextBox().setEnabled(false);
				getActionButton().setHTML(Mode.EDIT_MULTI.getName());
				getDeleteButton().setText(Mode.EDIT_MULTI.getDeleteName());
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
	public DropDownMenu getCategoryDropDownList() {
		return categoryDropDownList;
	}
	public CheckBoxListManager<CategoryVO> getCategoryCheckBoxListManager() {
		return categoryBoxListManager;
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
