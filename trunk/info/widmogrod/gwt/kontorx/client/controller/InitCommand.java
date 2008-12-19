package info.widmogrod.gwt.kontorx.client.controller;

import org.puremvc.java.multicore.patterns.command.MacroCommand;

public class InitCommand extends MacroCommand {
	public InitCommand() {
		addSubCommand(new InitModelCommand());
		addSubCommand(new InitViewCommand());
	}
}
