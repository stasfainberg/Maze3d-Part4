package presenter;


import model.Model;
import view.View;

/**
 * <h1>DirCommand Class</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * show all the files and directory (folders) that are in <Path> -
 * - specific path of the file system Computer
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */


public class DirCommand implements Command {

	private Model model;
	private View view;
	
	/**
	 * Constructor of DirCommand
	 */ 
	public DirCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	} 

	/************************* doCommand() ***********************/
	/**
	 * the function call to dir() from MyModel
	 * the function dir() display all the files and directory in the path
	 */
	@Override
	public void doCommand(String[] args) {
		model.dir();
	}

	
	

}
