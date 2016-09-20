package presenter;

import model.Model;
import view.View;

/**
 * <h1>ExitCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class ExitCommand implements Command {

	private Model model;
	private View view;

	/**
	 * Constructor of ExitCommand
	 */
	public ExitCommand(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}



	/************************* doCommand() ***********************/
	/**
	 * the function call to method Exit from MyModel
	 * in method Exit we closed all the threads are working and close the Program
	 */
	@Override
	public void doCommand(String[] args) {
		// TODO Auto-generated method stub
		model.Exit();
	}

}



