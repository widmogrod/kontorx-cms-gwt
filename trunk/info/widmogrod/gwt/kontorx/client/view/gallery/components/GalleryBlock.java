package info.widmogrod.gwt.kontorx.client.view.gallery.components;

import info.widmogrod.gwt.kontorx.client.model.vo.GalleryVO;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GalleryBlock extends Composite {

	private Button addButton;
	CheckBoxListManager<GalleryVO> checkBoxListManager;

	public GalleryBlock() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		verticalPanel.setCellWidth(horizontalPanel, "100%");
		
		Label name = new Label("Galerie");
		horizontalPanel.add(name);
		horizontalPanel.setCellVerticalAlignment(name, HasVerticalAlignment.ALIGN_MIDDLE);
		name.setStyleName("kx-header-2");

		addButton = new Button();
		horizontalPanel.add(addButton);
		horizontalPanel.setCellVerticalAlignment(addButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(addButton, HasHorizontalAlignment.ALIGN_RIGHT);
		addButton.setText("Dodaj");
		
		checkBoxListManager = new CheckBoxListManager<GalleryVO>();
		// TODO Może to przypadek ale gdy dodam zawartosc ponizej
		// nie pokauje sie w form - kategorie
//		{
//			@Override
//			public boolean compareObject(GalleryVO o1, GalleryVO o2) {
//				return o1.getId() == o2.getId();
//			}
//		};
		verticalPanel.add(checkBoxListManager);
		verticalPanel.setCellWidth(checkBoxListManager, "100%");
	}

	public CheckBoxListManager<GalleryVO> getCheckBoxListManager() {
		return checkBoxListManager;
	}
	public Button getAddButton() {
		return addButton;
	}
}
