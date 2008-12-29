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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
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
	
	public static final String IMAGE_URL_THUMB_100x100 = "upload/gallery/thumb1/";

	private Image imageShow;
	private HTML descriptionShowHTML;
	private Label nameShowLabel;
	private VerticalPanel showPanel;
	private Label nazwaLabel;
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
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

		publicatedCheckBox = new CheckBox();
		contextPanel.add(publicatedCheckBox);
		publicatedCheckBox.setName("publicated");
		publicatedCheckBox.setText("Opublikuj grafikę");

		nazwaLabel = new Label("Nazwa");
		contextPanel.add(nazwaLabel);

		nameTextBox = new TextBox();
		contextPanel.add(nameTextBox);
		nameTextBox.setText("");

		descriptionLabel = new Label("Opis grafiki");
		contextPanel.add(descriptionLabel);

		descriptionTextArea = new TextArea();
		contextPanel.add(descriptionTextArea);
		descriptionTextArea.setSize("100%", "150px");
		descriptionTextArea.setText("");

		showPanel = new VerticalPanel();
		verticalPanel.add(showPanel);
		showPanel.setWidth("100%");
		verticalPanel.setCellWidth(showPanel, "100%");

		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		showPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("100%");
		showPanel.setCellWidth(horizontalPanel_1, "100%");

		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_1);
		horizontalPanel_1.setCellHorizontalAlignment(verticalPanel_1, HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel_1.setCellVerticalAlignment(verticalPanel_1, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel_1.setCellWidth(verticalPanel_1, "100%");

		nameShowLabel = new Label("nameShowLabel");
		verticalPanel_1.add(nameShowLabel);

		descriptionShowHTML = new HTML("New <i>HTML</i> panel");
		verticalPanel_1.add(descriptionShowHTML);
		descriptionShowHTML.setWidth("100%");
		descriptionShowHTML.setText("descriptionShowHTML");

		imageShow = new Image();
		horizontalPanel_1.add(imageShow);
		imageShow.setStyleName("gwt-Image.right");
		horizontalPanel_1.setCellVerticalAlignment(imageShow, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel_1.setCellHorizontalAlignment(imageShow, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel_1.setCellHeight(imageShow, "100%");
		horizontalPanel_1.setCellWidth(imageShow, "100px");
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
		getDescriptionTextArea().setText(model.getDescription());
		
		getNameShowLabel().setText(model.getName());
		getDescriptionShowHTML().setHTML(model.getDescription());
		getImageShow().setUrl(IMAGE_URL_THUMB_100x100 + model.getImage());
	}

	public ImageVO getModel() {
		model.setPublicated(getPublicatedCheckBox().isChecked());
		model.setName(getNameTextBox().getText());
		model.setDescription(getDescriptionTextArea().getText());
		return model;
	}
	
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
				getNazwaLabel().setVisible(false);
				getShowPanel().setVisible(true);
				break;
			case SHOW_MULTI:
				getGalleryDropDownList().setVisible(true);
				getActionButton().setText(Mode.SHOW_MULTI.getName());
				getDeleteButton().setText(Mode.SHOW_MULTI.getDeleteName());
				getDeleteButton().setVisible(true);
				getContextPanel().setVisible(false);
				getShowPanel().setVisible(false);
				break;
			case NEW:
				getGalleryDropDownList().setVisible(false);
				getActionButton().setText(Mode.NEW.getName());
				getDeleteButton().setText(Mode.NEW.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(true);
				getPublicatedCheckBox().setVisible(false);
				
				getDescriptionLabel().setVisible(false);
				getDescriptionTextArea().setVisible(false);
				getNazwaLabel().setVisible(false);
				getNameTextBox().setVisible(false);
				
				getShowPanel().setVisible(false);
				break;
			case EDIT:
				getGalleryDropDownList().setVisible(false);
				getActionButton().setText(Mode.EDIT.getName());
				getDeleteButton().setText(Mode.EDIT.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(true);
				getPublicatedCheckBox().setVisible(true);

				getNazwaLabel().setVisible(true);
				getNameTextBox().setVisible(true);
				getDescriptionLabel().setVisible(true);
				getDescriptionTextArea().setVisible(true);
				
				getShowPanel().setVisible(false);
				break;
			case EDIT_MULTI:
				getGalleryDropDownList().setVisible(false);;
				getActionButton().setHTML(Mode.EDIT_MULTI.getName());
				getDeleteButton().setText(Mode.EDIT_MULTI.getDeleteName());
				getDeleteButton().setVisible(false);
				getContextPanel().setVisible(true);
				getImageUpload().setVisible(false);
				getPublicatedCheckBox().setVisible(true);

				getDescriptionLabel().setVisible(false);
				getDescriptionTextArea().setVisible(false);
				getNazwaLabel().setVisible(false);
				getNameTextBox().setVisible(false);
				
				getShowPanel().setVisible(false);
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
	protected TextArea getDescriptionTextArea() {
		return descriptionTextArea;
	}
	protected Label getDescriptionLabel() {
		return descriptionLabel;
	}
	protected Label getNazwaLabel() {
		return nazwaLabel;
	}
	protected VerticalPanel getShowPanel() {
		return showPanel;
	}
	protected Label getNameShowLabel() {
		return nameShowLabel;
	}
	protected HTML getDescriptionShowHTML() {
		return descriptionShowHTML;
	}
	protected Image getImageShow() {
		return imageShow;
	}
}
