package info.widmogrod.gwt.kontorx.client.view;

import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryBlock;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryBlock;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

public class MainWindow extends Composite {
	DockPanel dockPanel;
	private FlexTable eastFlexTable;
	private FlexTable westFlexTable;
	private FlexTable northFlexTable;
	
	public MainWindow() {
		dockPanel = new DockPanel();
		initWidget(dockPanel);

		northFlexTable = new FlexTable();
		dockPanel.add(northFlexTable, DockPanel.NORTH);

		//
		DecoratorPanel decoratorPanel1 = new DecoratorPanel();
		eastFlexTable = new FlexTable();
		decoratorPanel1.setWidget(eastFlexTable);
		
		dockPanel.add(decoratorPanel1, DockPanel.WEST);
		dockPanel.setCellWidth(decoratorPanel1, "215px");
		dockPanel.setCellHorizontalAlignment(decoratorPanel1, DockPanel.ALIGN_LEFT);
		dockPanel.setCellVerticalAlignment(decoratorPanel1, DockPanel.ALIGN_TOP);

		// 
		DecoratorPanel decoratorPanel2 = new DecoratorPanel();
		westFlexTable = new FlexTable();
		decoratorPanel2.setWidget(westFlexTable);
		
		dockPanel.add(decoratorPanel2, DockPanel.EAST);
		dockPanel.setCellWidth(decoratorPanel2, "500px");
		dockPanel.setCellHorizontalAlignment(decoratorPanel2, DockPanel.ALIGN_RIGHT);
		dockPanel.setCellVerticalAlignment(decoratorPanel2, DockPanel.ALIGN_TOP);
	}

	public void setInfoBox(InfoBox widget) {
		northFlexTable.setWidget(0, 0, widget);
	}

	public void setEditPanel(Widget widget) {
		westFlexTable.setWidget(0, 0, widget);
		CellFormatter cf = eastFlexTable.getCellFormatter();
		cf.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		cf.setStyleName(0, 0, "kx-EditPanel");
	}
	
	public void setCategoryBlock(CategoryBlock widget) {
		eastFlexTable.setWidget(1, 0, widget);
		widget.setWidth("200px");
		CellFormatter cf = eastFlexTable.getCellFormatter();
		cf.setAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		cf.setStyleName(1, 0, "kx-GalleryBlock");
	}

	public void setGalleryBlock(GalleryBlock widget) {
		eastFlexTable.setWidget(2, 0, widget);
		widget.setWidth("200px");
		CellFormatter cf = eastFlexTable.getCellFormatter();
		cf.setAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
		cf.setStyleName(2, 0, "kx-GalleryBlock");
	}
}
