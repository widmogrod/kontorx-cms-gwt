package info.widmogrod.gwt.kontorx.client.controller;

import info.widmogrod.gwt.kontorx.client.model.CategoryProxy;
import info.widmogrod.gwt.kontorx.client.model.GalleryProxy;
import info.widmogrod.gwt.kontorx.client.model.ImageProxy;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

public class InitModelCommand extends SimpleCommand {

	@Override
	public void execute(INotification notification) {
		Facade facade = getFacade();
		facade.registerProxy(new CategoryProxy());
		facade.registerProxy(new GalleryProxy());
		facade.registerProxy(new ImageProxy());
	}
}
