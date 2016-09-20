package presenter;

import model.Model;
import view.View;


/**
 * <h1>LoadMazeCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class LoadMazeCommand implements Command {

	private Model model;
	private View view;
	
	/**
	 * Constructor of LoadMazeCommand
	 */
	public LoadMazeCommand(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}
    
	/************************* doCommand() ***********************/
	/**
	 * the function load maze from the file 
	 * @param args - Arguments:
	 * args[0] - Name of the maze , args[1]- name of the file
	 */
	@Override
	public void doCommand(String[] args){
		if (args==null||args.length !=2)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String name = args[0];
		String fileName = args[1];
		
		model.loadMaze(fileName,name);	}

	
	
}
