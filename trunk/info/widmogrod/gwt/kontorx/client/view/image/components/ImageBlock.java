package info.widmogrod.gwt.kontorx.client.view.image.components;

import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.ImageList;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageBlock extends Composite {
	
	public enum Actions {
		NONE("Wybierz operację", "0"),
		SELECT_ALL("Zaznacz wszystkie", "1"),
		SELECT_NONE("Odznacz wszystkie", "2"),
		SELECT_FLIP("Odwróć zaznaczenie", "3");

		private String name;
		private String idx;

		private Actions(String name, String idx) {
			this.name = name;
			this.idx = idx;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return idx;
		}
	};
	
	private HTML actionLinkShowFreeImage;
	private ListBox actionsBox;
	public static final String IMAGE_URL_THUMB = "upload/gallery/thumb/";

	private ImageListManager<ImageVO> list;
	private Button addButton;

	public ImageBlock() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellWidth(horizontalPanel, "100%");
		horizontalPanel.setStyleName("kx-NavigationBar");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setWidth("100%");

		final Label grafikiLabel = new Label("Grafiki");
		horizontalPanel.add(grafikiLabel);
		horizontalPanel.setCellWidth(grafikiLabel, "100px");
		horizontalPanel.setCellVerticalAlignment(grafikiLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		grafikiLabel.setStyleName("kx-header-2");

		final FlowPanel flowPanel = new FlowPanel();
		horizontalPanel.add(flowPanel);
		horizontalPanel.setCellHorizontalAlignment(flowPanel, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(flowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellWidth(flowPanel, "100%");

		actionsBox = new ListBox();
		flowPanel.add(actionsBox);

		actionsBox.addItem(Actions.NONE.getName(), Actions.NONE.getValue());
		actionsBox.addItem(Actions.SELECT_ALL.getName(), Actions.SELECT_ALL.getValue());
		actionsBox.addItem(Actions.SELECT_NONE.getName(), Actions.SELECT_NONE.getValue());
		actionsBox.addItem(Actions.SELECT_FLIP.getName(), Actions.SELECT_FLIP.getValue());

		actionLinkShowFreeImage = new HTML("New <i>HTML</i> panel");
		flowPanel.add(actionLinkShowFreeImage);
		actionLinkShowFreeImage.setStyleName("kx-Hyperlink-option");
		actionLinkShowFreeImage.setText("Pokaż nieprzypisane");
		
		addButton = new Button("Dodaj");
		horizontalPanel.add(addButton);
		horizontalPanel.setCellWidth(addButton, "60px");
		horizontalPanel.setCellHorizontalAlignment(addButton, HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setCellVerticalAlignment(addButton, HasVerticalAlignment.ALIGN_TOP);
		
		list = new ImageListManager<ImageVO>() {
			@Override
			public boolean compareObject(ImageVO o1, ImageVO o2) {
				return o1.getId() == o2.getId();
			}
		};
		
		list.setRenderCallback(new RenderCallback<ImageList<ImageVO>, ImageVO>() {
			public void onRender(ImageList<ImageVO> widget, ImageVO model) {
				widget.setUrl(IMAGE_URL_THUMB + model.getImage());
			};
		});

		verticalPanel.add(list);
		list.setStyleName("kx-MenuListManager");
	}

	public ImageListManager<ImageVO> getListManager() {
		return list;
	}
	public Button getAddButton() {
		return addButton;
	}
	public ListBox getActionsBox() {
		return actionsBox;
	}
	public HTML getActionLinkShowFreeImage() {
		return actionLinkShowFreeImage;
	}
}
