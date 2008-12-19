package info.widmogrod.gwt.kontorx.client;

import info.widmogrod.gwt.kontorx.client.controller.InitCommand;

import org.puremvc.java.multicore.patterns.facade.Facade;

public class ApplicationFacade extends Facade {

	public static String INIT = "INIT";
	
	protected ApplicationFacade() {
		super(INIT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initializeFacade() {
		// TODO Auto-generated method stub
		super.initializeFacade();

		registerCommand(INIT, new InitCommand());
	}
}
