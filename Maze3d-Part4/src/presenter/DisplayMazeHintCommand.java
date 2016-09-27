package presenter;

import model.Model;
import view.View;



/**
 * <h1>DisplayMazeHintCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class DisplayMazeHintCommand implements Command {

	private Model model;
	private View view;

	/**
	 * Constructor of DisplaySolutionCommand
	 */
	public DisplayMazeHintCommand(Model model, View view) 
	{
		this.model = model;
		this.view = view;
	}

	
	
	@Override
	public void doCommand(String[] args) 
	{
		
		view.displayMazeHint(model.getHint());

	}

}
