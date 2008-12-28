package info.widmogrod.gwt.kontorx.client.view;

import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryBlock;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryBlock;
import info.widmogrod.gwt.kontorx.client.view.image.components.ImageBlock;
import info.widmogrod.gwt.library.client.ui.MessageBox;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {
	private SimplePanel messageSimplePanel;
	private SimplePanel imageSimplePanel;
	private SimplePanel infoSimplePanel;
	private SimplePanel gallerySimplePanel;
	private SimplePanel categorySimplePanel;
	public MainWindow() {
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setHeight("100%");
		
		messageSimplePanel = new SimplePanel();
		absolutePanel.add(messageSimplePanel);
		
		VerticalPanel leftVerticalPanel = new VerticalPanel();

		categorySimplePanel = new SimplePanel();
		leftVerticalPanel.add(categorySimplePanel);

		gallerySimplePanel = new SimplePanel();
		leftVerticalPanel.add(gallerySimplePanel);
		
		DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
		decoratorPanel_1.setWidget(leftVerticalPanel);
		leftVerticalPanel.setWidth("150px");
		absolutePanel.add(decoratorPanel_1);
		
		DecoratorPanel decoratorPanel_2 = new DecoratorPanel();
		absolutePanel.add(decoratorPanel_2, 170, 0);

		imageSimplePanel = new SimplePanel();
		decoratorPanel_2.setWidget(imageSimplePanel);
		imageSimplePanel.setWidth("360px");

		DecoratorPanel decoratorPanel_3 = new DecoratorPanel();
		absolutePanel.add(decoratorPanel_3, 550, 0);

		infoSimplePanel = new SimplePanel();
		decoratorPanel_3.setWidget(infoSimplePanel);
		infoSimplePanel.setWidth("250px");
	}

	public void setMessageBox(MessageBox widget) {
		getMessageSimplePanel().setWidget(widget);
	}

	public void setInfoPanel(Widget widget) {
		getInfoSimplePanel().setWidget(widget);
	}
	
	public void setCategoryBlock(CategoryBlock widget) {
		getCategorySimplePanel().setWidget(widget);
	}

	public void setGalleryBlock(GalleryBlock widget) {
		getGallerySimplePanel().setWidget(widget);
	}
	
	public void setImageBlock(ImageBlock widget) {
		getImageSimplePanel().setWidget(widget);
	}

	protected SimplePanel getCategorySimplePanel() {
		return categorySimplePanel;
	}
	protected SimplePanel getGallerySimplePanel() {
		return gallerySimplePanel;
	}
	protected SimplePanel getInfoSimplePanel() {
		return infoSimplePanel;
	}
	protected SimplePanel getImageSimplePanel() {
		return imageSimplePanel;
	}
	protected SimplePanel getMessageSimplePanel() {
		return messageSimplePanel;
	}
}
