package info.widmogrod.gwt.kontorx.client.controller;

import info.widmogrod.gwt.kontorx.client.view.InfoBoxMediator;
import info.widmogrod.gwt.kontorx.client.view.MainWindow;
import info.widmogrod.gwt.kontorx.client.view.MainWindowMediator;
import info.widmogrod.gwt.kontorx.client.view.category.CategoryBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.category.CategoryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryBlock;
import info.widmogrod.gwt.kontorx.client.view.category.components.CategoryForm;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryBlockMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.GalleryFormMediator;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryBlock;
import info.widmogrod.gwt.kontorx.client.view.gallery.components.GalleryForm;
import info.widmogrod.gwt.library.client.ui.InfoBox;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.google.gwt.user.client.ui.RootPanel;

public class InitViewCommand extends SimpleCommand {
	@Override
	public void execute(INotification notification) {
		RootPanel layout = (RootPanel) notification.getBody();
		Facade facade = getFacade();

		// MainWindow
		MainWindow mainWindow = new MainWindow();
		layout.add(mainWindow);
		facade.registerMediator(new MainWindowMediator(mainWindow));

		// InfoBox
		InfoBox infoBox = new InfoBox();
		mainWindow.setInfoBox(infoBox);
		facade.registerMediator(new InfoBoxMediator(infoBox));

		// CategoryBlock
		CategoryBlock categoryBlock = new CategoryBlock();
		mainWindow.setCategoryBlock(categoryBlock);
		facade.registerMediator(new CategoryBlockMediator(categoryBlock));
		
		// CategoryForm
		facade.registerMediator(new CategoryFormMediator(new CategoryForm()));
		
		// GalleryBlock
		GalleryBlock galleryBlock = new GalleryBlock();
		mainWindow.setGalleryBlock(galleryBlock);
		facade.registerMediator(new GalleryBlockMediator(galleryBlock));
		
		// GalleryForm
		facade.registerMediator(new GalleryFormMediator(new GalleryForm()));
	}
}
