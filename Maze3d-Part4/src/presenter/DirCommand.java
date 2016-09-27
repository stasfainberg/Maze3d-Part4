package presenter;


import model.Model;
import view.View;

/**
 * <h1>DirCommand Class</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * Shows all files and directories in a given path.
 * 
 * @param Model model - holds the Model class in this project.
 * @param View view - holds the View class in this project.
 */


public class DirCommand implements Command {

	private Model model;
	private View view;
	
	/****************************** Constructor **************************/ 
	public DirCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	} 

	
	/****************************** Methods **************************/ 
	/**
	 * This doCommand() method calls the dir() method from the model data member.
	 * dir() method displays all files and directories in a given path.
	 */
	@Override
	public void doCommand(String[] args) {
		model.dir();
	}

	
	

}
