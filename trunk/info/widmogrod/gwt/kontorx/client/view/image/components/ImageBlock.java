package info.widmogrod.gwt.kontorx.client.view.image.components;

import info.widmogrod.gwt.kontorx.client.model.vo.ImageVO;
import info.widmogrod.gwt.library.client.ui.interfaces.RenderCallback;
import info.widmogrod.gwt.library.client.ui.list.ImageList;
import info.widmogrod.gwt.library.client.ui.list.ImageListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageBlock extends Composite {
	public static final String IMAGE_URL_THUMB = "upload/gallery/thumb/";

	private ImageListManager<ImageVO> list;
	private Button addButton;

	public ImageBlock() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		addButton = new Button("Dodaj");
		verticalPanel.add(addButton);
		
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
	}

	public ImageListManager<ImageVO> getListManager() {
		return list;
	}
	public Button getAddButton() {
		return addButton;
	}
}
