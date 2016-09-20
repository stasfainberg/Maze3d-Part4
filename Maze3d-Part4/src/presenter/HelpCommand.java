package presenter;

import java.util.HashMap;

import model.Model;


/**
 * <h1>HelpCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 */
public class HelpCommand implements Command {

	
	private Model model;
	private HashMap<String, Command> commands;
	
	/**
	 * Constructor of HelpCommand
	 */
	public HelpCommand(Model model, HashMap<String, Command> commands) {
		this.model = model;
		this.commands = commands;
	}
	
	/************************* doCommand() ***********************/
	/**
	 * the function call to method help() from MyModel
     * the function print all the commands that the user can enter
	 */	
	@Override
	public void doCommand(String[] args) {
		model.help(commands);
	}
	
	/************  דוגמת הרצה *********/
	/** Such as executables (for example):
	 > Please enter command or 'help': help
	 
		> Please type in command according to the list below: 
			> generate maze
			> load Maze
			> exit
			> help
			> display by section
			> display
			> file size
			> solve maze
			> dir
			> save maze
			> maze size
			> display solution
	 */

}
