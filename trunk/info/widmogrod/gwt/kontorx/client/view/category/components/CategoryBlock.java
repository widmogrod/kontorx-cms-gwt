package info.widmogrod.gwt.kontorx.client.view.category.components;

import info.widmogrod.gwt.kontorx.client.model.vo.CategoryVO;
import info.widmogrod.gwt.library.client.ui.list.CheckBoxListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CategoryBlock extends Composite {

	private Button addButton;
	CheckBoxListManager<CategoryVO> checkBoxListManager;

	public CategoryBlock() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setStyleName("kx-NavigationBar");
		horizontalPanel.setWidth("100%");
		verticalPanel.setCellWidth(horizontalPanel, "100%");
		
		Label name = new Label("Kategorie");
		horizontalPanel.add(name);
		horizontalPanel.setCellHorizontalAlignment(name, HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setCellWidth(name, "100%");
		horizontalPanel.setCellVerticalAlignment(name, HasVerticalAlignment.ALIGN_MIDDLE);
		name.setStyleName("kx-header-2");

		addButton = new Button();
		horizontalPanel.add(addButton);
		horizontalPanel.setCellWidth(addButton, "100%");
		horizontalPanel.setCellVerticalAlignment(addButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(addButton, HasHorizontalAlignment.ALIGN_RIGHT);
		addButton.setText("Dodaj");

		final ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		
		checkBoxListManager = new CheckBoxListManager<CategoryVO>() {
			@Override
			public boolean compareObject(CategoryVO o1, CategoryVO o2) {
				return o1.getId() == o2.getId();
			}
		};
		scrollPanel.setWidget(checkBoxListManager);
		checkBoxListManager.setSize("100%", "150px");
//				for (CheckBoxList<CategoryVO> ch : list.values()) {
//					if (ch.getModel().getId() == model.getId()) {
//						ch.setChecked(true);
//					} else {
//						ch.setChecked(false);
//					}
//				}
//			}
//		};
		setWidth("100%");
	}

	public CheckBoxListManager<CategoryVO> getCheckBoxListManager() {
		return checkBoxListManager;
	}
	public Button getAddButton() {
		return addButton;
	}
}
