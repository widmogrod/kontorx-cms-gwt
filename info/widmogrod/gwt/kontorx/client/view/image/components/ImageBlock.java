package info.widmogrod.gwt.kontorx.client.view.image.components;

import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.ImageList;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
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
		grafikiLabel.setWidth("100px");
		horizontalPanel.setCellHorizontalAlignment(grafikiLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellVerticalAlignment(grafikiLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		grafikiLabel.setStyleName("kx-header-2");

		actionsBox = new ListBox();
		horizontalPanel.add(actionsBox);
		horizontalPanel.setCellVerticalAlignment(actionsBox, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(actionsBox, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellWidth(actionsBox, "100%");

		actionsBox.addItem(Actions.NONE.getName(), Actions.NONE.getValue());
		actionsBox.addItem(Actions.SELECT_ALL.getName(), Actions.SELECT_ALL.getValue());
		actionsBox.addItem(Actions.SELECT_NONE.getName(), Actions.SELECT_NONE.getValue());
		actionsBox.addItem(Actions.SELECT_FLIP.getName(), Actions.SELECT_FLIP.getValue());
		
		addButton = new Button("Dodaj");
		horizontalPanel.add(addButton);
		horizontalPanel.setCellWidth(addButton, "60px");
		horizontalPanel.setCellHorizontalAlignment(addButton, HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setCellVerticalAlignment(addButton, HasVerticalAlignment.ALIGN_TOP);

		final ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setHeight("390px");
		verticalPanel.setCellWidth(scrollPanel, "100%");

		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");

		final HTML html = new HTML("New <i>HTML</i> panel");
		verticalPanel_1.add(html);
		verticalPanel_1.setCellVerticalAlignment(html, HasVerticalAlignment.ALIGN_TOP);
		html.setStyleName("kx-HTML-info");
		html.setText("Poniżej znajdują się grafiki nieprzypisane do żadnej galerii lub grafiki należące do jednej z wybranych galerii");
		
		list = new ImageListManager<ImageVO>() {
			@Override
			public boolean compareObject(ImageVO o1, ImageVO o2) {
				return o1.getId() == o2.getId();
			}
		};
		verticalPanel_1.add(list);
		verticalPanel_1.setCellVerticalAlignment(list, HasVerticalAlignment.ALIGN_TOP);
		list.setSize("100%", "100%");
		
		list.setRenderCallback(new RenderCallback<ImageList<ImageVO>, ImageVO>() {
			public void onRender(ImageList<ImageVO> widget, ImageVO model) {
				widget.setUrl(IMAGE_URL_THUMB + model.getImage());
			};
		});

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
}
