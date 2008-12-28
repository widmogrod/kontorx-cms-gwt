package info.widmogrod.gwt.kontorx.client.view.image.components;

import info.widmogrod.gwt.kontorx.client.model.ImageProxy;
import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.library.client.ui.DropDownMenu;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageForm extends Composite {
	public enum Mode {
		SHOW("Edytuj","Usuń"),
		SHOW_MULTI("Edytuj","Usuń grafiki"),
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

	private RichTextArea descriptionRichTextArea;
	private TextBox nameTextBox;
	private VerticalPanel contextPanel;
	private FormPanel formPanel;
	
	private FileUpload imageUpload;
	private CheckBox publicatedCheckBox;

	private Button cancelButton;
	private Button deleteButton;
	private Button actionButton;

	private DropDownMenu galleryDropDownList;
	private CheckBoxListManager<GalleryVO> categoryBoxListManager;

	public ImageForm() {
		formPanel = new FormPanel();
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setAction(ImageProxy.FORM_ADD_URL);

		initWidget(formPanel);
		VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.add(verticalPanel);
		verticalPanel.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellWidth(horizontalPanel, "100%");
		horizontalPanel.setWidth("100%");
		horizontalPanel.setStyleName("kx-NavigationBar");

		actionButton = new Button();
		horizontalPanel.add(actionButton);
		horizontalPanel.setCellHorizontalAlignment(actionButton, HasHorizontalAlignment.ALIGN_LEFT);
		actionButton.setText(Mode.NEW.getName());

		cancelButton = new Button();
		horizontalPanel.add(cancelButton);
		horizontalPanel.setCellHorizontalAlignment(cancelButton, HasHorizontalAlignment.ALIGN_LEFT);
		cancelButton.setText("Anuluj");
		
		galleryDropDownList = new DropDownMenu("Galeria");
		horizontalPanel.add(galleryDropDownList);
		horizontalPanel.setCellVerticalAlignment(galleryDropDownList, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel.setCellHorizontalAlignment(galleryDropDownList, HasHorizontalAlignment.ALIGN_CENTER);

		categoryBoxListManager = new CheckBoxListManager<GalleryVO>() {
			@Override
			public boolean compareObject(GalleryVO o1, GalleryVO o2) {
				return o1.getId() == o2.getId();
			}
		};
//		{
//			@Override
//			public void setCheckedByModelRow(ImageVO model) {
//				// porownywanie obiektow musi byc po ID by wszystko dzialalo!
//				for (CheckBoxList<ImageVO> ch : list.values()) {
//					if (ch.getModel().getId() == model.getId()) {
//						ch.setChecked(true);
//					} else {
//						ch.setChecked(false);
//					}
//				}
//			}
//		};
		galleryDropDownList.add(categoryBoxListManager);

		deleteButton = new Button();
		horizontalPanel.add(deleteButton);
		horizontalPanel.setCellHorizontalAlignment(deleteButton, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellWidth(deleteButton, "100%");
		deleteButton.setText(Mode.NEW.getDeleteName());

		contextPanel = new VerticalPanel();
		verticalPanel.add(contextPanel);
		verticalPanel.setCellVerticalAlignment(contextPanel, HasVerticalAlignment.ALIGN_TOP);

		imageUpload = new FileUpload();
		contextPanel.add(imageUpload);
		imageUpload.setName("photoupload");

		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		contextPanel.add(horizontalPanel_1);

//		DropDownList categoryDropDownList2 = new DropDownList("Kategoria 222");
//		horizontalPanel.add(categoryDropDownList2);
//		categoryDropDownList2.add(new CheckBoxListManager<Category>());

		publicatedCheckBox = new CheckBox();
		horizontalPanel_1.add(publicatedCheckBox);
		publicatedCheckBox.setName("publicated");
		publicatedCheckBox.setText("Opublikuj grafikę");

		final Label nazwaLabel = new Label("Nazwa");
		verticalPanel.add(nazwaLabel);

		nameTextBox = new TextBox();
		verticalPanel.add(nameTextBox);
		nameTextBox.setText("");

		final Label opisGrafikiLabel = new Label("Opis grafiki");
		verticalPanel.add(opisGrafikiLabel);

		descriptionRichTextArea = new RichTextArea();
		verticalPanel.add(descriptionRichTextArea);
		descriptionRichTextArea.setWidth("100%");
		verticalPanel.setCellWidth(descriptionRichTextArea, "100%");
		descriptionRichTextArea.setText("");
	}

	private ImageVO model;

	public void cleanModel() {
		this.model = null;
		setModel(ImageVO.get());
	}
	
	public void setModel(ImageVO model) {
		this.model = model;
		getPublicatedCheckBox().setChecked(model.getPublicated());
		getNameTextBox().setText(model.getName());
		getDescriptionRichTextArea().setHTML(model.getDescription());
	}

	public ImageVO getModel() {
		model.setPublicated(getPublicatedCheckBox().isChecked());
		model.setName(getNameTextBox().getName());
		model.setDescription(getDescriptionRichTextArea().getHTML());
		return model;
	}
	
//	public ImageVO getNewModel() {
//		// TODO Czy jest brany ??
//		ImageVO model = ImageVO.get();
//		model.setPublicated(getPublicatedCheckBox().isChecked());
//		model.setName(getNameTextBox().getName());
//		model.setDescription(getDescriptionRichTextArea().getHTML());
//		return model;
//	}
	
	private Mode mode = Mode.NEW;
	
	public void setMode(Mode mode) {
		switch (mode) {
			default:
			case SHOW:
				getGalleryDropDownList().setVisible(true);
				getActionButton().setText(Mode.SHOW.getName());
				getDeleteButton().setText(Mode.SHOW.getDeleteName());
				getDeleteButton().setVisible(true);
				getContextPanel().setVisible(false);
				break;
			case SHOW_MULTI:
				getGalleryDropDownList().setVisible(true);
				getActionButton().setText(Mode.SHOW_MULTI.getName());
				getDeleteButton().setText(Mode.SHOW_MULTI.getDeleteName());
				getDeleteButton().setVisible(true);
				getContextPanel().setVisible(false);
				break;
			case NEW:
				getGalleryDropDownList().setVisible(false);
				getActionButton().setText(Mode.NEW.getName());
				getDeleteButton().setText(Mode.NEW.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(true);
				getPublicatedCheckBox().setVisible(false);
				getNameTextBox().setVisible(false);
				getDescriptionRichTextArea().setVisible(false);
				break;
			case EDIT:
				getGalleryDropDownList().setVisible(false);
				getActionButton().setText(Mode.EDIT.getName());
				getDeleteButton().setText(Mode.EDIT.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(true);
				getPublicatedCheckBox().setVisible(true);
				getNameTextBox().setVisible(true);
				getDescriptionRichTextArea().setVisible(true);
				break;
			case EDIT_MULTI:
				getGalleryDropDownList().setVisible(false);;
				getActionButton().setHTML(Mode.EDIT_MULTI.getName());
				getDeleteButton().setText(Mode.EDIT_MULTI.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(false);
				getPublicatedCheckBox().setVisible(true);
				getNameTextBox().setVisible(false);
				getDescriptionRichTextArea().setVisible(false);
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
	public DropDownMenu getGalleryDropDownList() {
		return galleryDropDownList;
	}
	public CheckBoxListManager<GalleryVO> getGalleryCheckBoxListManager() {
		return categoryBoxListManager;
	}
	protected CheckBox getPublicatedCheckBox() {
		return publicatedCheckBox;
	}
	public FileUpload getImageUpload() {
		return imageUpload;
	}
	public FormPanel getFormPanel() {
		return formPanel;
	}
	protected VerticalPanel getContextPanel() {
		return contextPanel;
	}
	protected TextBox getNameTextBox() {
		return nameTextBox;
	}
	protected RichTextArea getDescriptionRichTextArea() {
		return descriptionRichTextArea;
	}
}
