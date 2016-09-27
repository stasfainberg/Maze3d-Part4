package presenter;

import model.Model;
import view.View;

/**
 * <h1>HintCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 * @param prop - properties
 */
public class HintCommand implements Command {

	private Model model;
	private View view;

	
	/**
	 * Constructor of SolveCommand
	 */ 
	public HintCommand(Model model, View view) {
		super();
		this.model = model;
		this.view = view;

	}
	
	/************************* doCommand() ***********************/
	/**
	 * This method shows the user what is the next position he should take in order to solve the maze. 
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

		model.hintMaze(name, algo, view.getplayerPos());
	}

}
