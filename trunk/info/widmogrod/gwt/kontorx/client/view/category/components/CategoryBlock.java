package info.widmogrod.gwt.kontorx.client.view.category.components;

import info.widmogrod.gwt.kontorx.client.model.Category;
import info.widmogrod.gwt.library.client.ui.CheckBoxList;
import info.widmogrod.gwt.library.client.ui.CheckBoxListManager;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CategoryBlock extends Composite {

	private Button addButton;
	CheckBoxListManager<Category> checkBoxListManager;

	public CategoryBlock() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		verticalPanel.setCellWidth(horizontalPanel, "100%");
		
		Label name = new Label("Kategorie");
		horizontalPanel.add(name);
		horizontalPanel.setCellVerticalAlignment(name, HasVerticalAlignment.ALIGN_MIDDLE);
		name.setStyleName("kx-header-2");

		addButton = new Button();
		horizontalPanel.add(addButton);
		horizontalPanel.setCellVerticalAlignment(addButton, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(addButton, HasHorizontalAlignment.ALIGN_RIGHT);
		addButton.setText("Dodaj");
		
		checkBoxListManager = new CheckBoxListManager<Category>() {
			@Override
			public void setCheckedByModelRow(Category model) {
				for (CheckBoxList<Category> ch : list.values()) {
					if (ch.getModel().getId() == model.getId()) {
						ch.setChecked(true);
					} else {
						ch.setChecked(false);
					}
				}
			}
		};
		verticalPanel.add(checkBoxListManager);
		verticalPanel.setCellWidth(checkBoxListManager, "100%");
	}

	public CheckBoxListManager<Category> getCheckBoxListManager() {
		return checkBoxListManager;
	}
	public Button getAddButton() {
		return addButton;
	}
}
