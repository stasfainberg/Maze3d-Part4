package presenter;

import model.Model;
import view.View;


/**
 * <h1>SaveMazeCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 */
public class SaveMazeCommand implements Command {

	private Model model;
	private View view;
	
	
	/**
	 * Constructor of SaveMazeCommand
	 */ 
	public SaveMazeCommand(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}
	
	
	/************************* doCommand() ***********************/
	/**
	 * the function save the maze in a file 
	 * @param args - Arguments:
	 * args[0] - Name of the maze , args[1]- name of the file
	 */
	@Override
	public void doCommand(String[] args) {
		if (args==null||args.length!=2)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String name = args[0];
		String filename = args[1];
		
		model.saveMaze(name, filename);	} 
	
	
	
	
    /*
	@Override
	public void doCommand(String[] args) {
		System.out.println(args.length);
		//System.out.println(args[1]);
		//String name = args[0];
		//String fileName = args[1];
		
		//model.saveMaze(name, fileName);
		model.saveMaze(args[0], args[1]);
		view.displayMessage("The maze:" + args[0] + "succesfuly saved in the file:" + args[1]);
		//view.displayMessage("The maze:" + name + "succesfuly saved in the file:" + fileName);
	}*/
	


}

