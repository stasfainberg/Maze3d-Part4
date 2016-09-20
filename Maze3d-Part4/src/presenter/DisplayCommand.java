package presenter;

import model.Model;
import view.View;

/**
 * <h1>DisplayCommand</1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class DisplayCommand implements Command {

	private Model model;
	private View view;
	
	/**
	 * Constructor of DisplayCommand 
	 */
	public DisplayCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	/************************* doCommand() ***********************/
	/**
	 * the function call to method display() from MyModel
	 * the function display() printing the maze
	 * נדפיס למשתמש את המבוך בשם name
	 */
	@Override
	public void doCommand(String[] args) {
		model.display(args[0]);
	}

}
