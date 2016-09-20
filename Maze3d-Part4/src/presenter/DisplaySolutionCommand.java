package presenter;

import model.Model;
import view.View;

/**<h1>DisplaySolutionCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 */
public class DisplaySolutionCommand implements Command {

	private Model model;
	private View view;
	
	/**
	 * Constructor of DisplaySolutionCommand
	 */
	public DisplaySolutionCommand(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}

	/************************* doCommand() ***********************/
	/**
	 * the function display maze solution
	 * @param args - Arguments -> args[0] - Name of the maze.
	 */
	@Override
	public void doCommand(String[] args) {
		if (args==null||args.length !=1)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String name = args[0];
		model.displaySolution(name);	}

}
