package presenter;

import model.Model;
import view.View;

/**
 * <h1>GenerateMazeCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 * @param prop - properties
 */
public class GenerateMazeCommand implements Command {
	private Model model;
	private View view;
	private Properties prop;
	
	/**
	 * Constructor of GenerateMazeCommand
	 */
	public GenerateMazeCommand(Model model, View view, Properties prop) {
		this.model = model;
		this.view = view;
		this.prop = prop;

	}	
	
	@Override
	public void doCommand(String[] args) {
		if (args==null||args.length !=4)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String name = args[0];
		int floors = Integer.parseInt(args[1]);
		int rows = Integer.parseInt(args[2]);
		int cols = Integer.parseInt(args[3]);
		
		model.generateMaze(name, floors, rows, cols, prop.getAlgoCreate());
	}

}




//package presenter;
//
//import model.Model;
//import view.View;
//
//public class GenerateMazeCommand implements Command {
//
//	private Model model;
//	private View view;
//	public GenerateMazeCommand(Model model) {
//		this.model = model;
//	}	
//	
//	@Override
//	public void doCommand(String[] args) {		
//		if (args.length != 4 ){
//			view.displayMessage("ERROR - enter 4 arguments\n");
//			return;
//		}
//	
//		try{
//			model.generateMaze(args [0] ,  Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));	
//			}
//			 catch (Exception e) {
//					view.displayMessage("The Generation of maze3d" + args[0] + "failed..." + e.toString());
//					 
//			}
//	}
//}
