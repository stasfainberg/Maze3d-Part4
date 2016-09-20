package presenter;

import model.Model;
import view.View;

/**
 * <h1>SolveCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 * @param prop - properties
 */

public class SolveCommand implements Command {

	private Model model;
	private View view;
	private Properties prop;

	
	/**
	 * Constructor of SolveCommand
	 */ 
	public SolveCommand(Model model, View view, Properties prop) {
		super();
		this.model = model;
		this.view = view;
		this.prop = prop;

	}
	
	/************************* doCommand() ***********************/
	/**
	 * the function solve the maze and running in a difference Thread 
	 * @param args - Arguments:
	 *  args[0] - Name of the Maze , args[1]- the Searcher Algorithm (DFS,BFS or Best first serach)
	 */
	@Override
	public void doCommand(String[] args) {

		if (args==null || args.length != 1)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String name = "";

		String algo = args[0];

		model.solveMaze(name, algo, view.getplayerPos());
	}

}