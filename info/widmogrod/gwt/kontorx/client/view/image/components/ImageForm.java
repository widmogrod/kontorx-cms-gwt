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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ImageForm extends Composite {
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

		final FlexTable flexTable = new FlexTable();
		formPanel.add(flexTable);
		

		final Label imageLabel = new Label("Grafika");
		flexTable.setWidget(1, 0, imageLabel);

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
		
		galleryDropDownList = new DropDownMenu("Galeria");
		horizontalPanel.add(galleryDropDownList);

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
		flexTable.setWidget(2, 0, label);

		publicatedCheckBox = new CheckBox();
		publicatedCheckBox.setName("publicated");
		publicatedCheckBox.setText("Tak");
		flexTable.setWidget(2, 1, publicatedCheckBox);

		imageUpload = new FileUpload();
		imageUpload.setName("photoupload");
		flexTable.setWidget(1, 1, imageUpload);
	}

	private ImageVO model;

	public void cleanModel() {
		this.model = null;
		setModel(ImageVO.get());
	}
	
	public void setModel(ImageVO model) {
		this.model = model;
		getPublicatedCheckBox().setChecked(model.getPublicated());
	}

	public ImageVO getModel() {
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	public ImageVO getNewModel() {
		ImageVO model = ImageVO.get();
		model.setPublicated(getPublicatedCheckBox().isChecked());
		return model;
	}
	
	private Mode mode = Mode.ADD;
	
	public void setMode(Mode mode) {
		switch (mode) {
			default:
			case ADD:
				getGalleryDropDownList().setVisible(false);
				getActionButton().setText(Mode.ADD.getName());
				getDeleteButton().setText(Mode.ADD.getDeleteName());
				getDeleteButton().setEnabled(false);
				getImageUpload().setVisible(true);
				break;
			case UPDATE:
				getGalleryDropDownList().setVisible(true);
				getActionButton().setText(Mode.UPDATE.getName());
				getDeleteButton().setText(Mode.UPDATE.getDeleteName());
				getDeleteButton().setEnabled(true);
				getImageUpload().setVisible(true);
				break;
			case UPDATE_MULTI:
				getGalleryDropDownList().setVisible(true);
				getActionButton().setHTML(Mode.UPDATE_MULTI.getName());
				getDeleteButton().setText(Mode.UPDATE_MULTI.getDeleteName());
				getDeleteButton().setEnabled(true);
				getImageUpload().setVisible(false);
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
}
