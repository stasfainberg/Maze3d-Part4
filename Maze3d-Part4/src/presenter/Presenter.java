package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * <h1>Presenter</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public class Presenter implements Observer {

	/******************************** Data Members ***********************************/
	private Model model;
	private View view;
	private Properties prop;

	
	private HashMap<String, Command> viewCommands;
	private HashMap<String, Command> modelCommands;
	
	
	/************************** Constructor of Presenter ******************************/
	public Presenter(Model model, View view, Properties prop) {
		this.model = model;
		this.view = view;
		this.prop = prop;
		buildCommands();
	}
	
	
	/********************************** Setters & Getters *************************/
	public HashMap<String, Command> getViewCommands() {
		return viewCommands;
	}

	public void setViewCommands(HashMap<String, Command> viewCommands) {
		this.viewCommands = viewCommands;
	}
	
	
	
	/***************************************** Methods *****************************/
	private void buildCommands() {
		viewCommands = new HashMap<String, Command>();
		viewCommands.put("generate_maze", new GenerateMazeCommand(model, view, prop));
		viewCommands.put("help", new HelpCommand(model,viewCommands ));
		viewCommands.put("dir", new DirCommand(model, view));
		viewCommands.put("display", new DisplayCommand(model, view));
		viewCommands.put("display_section", new DisplayCrossSectionCommand(model, view));
		viewCommands.put("save_maze", new SaveMazeCommand(model, view));
		viewCommands.put("load_maze", new LoadMazeCommand(model, view));
		viewCommands.put("solve", new SolveCommand(model, view, prop));
		viewCommands.put("display_solution", new DisplaySolutionCommand(model, view));
		viewCommands.put("exit", new ExitCommand(model, view));
		viewCommands.put("load_zip", new LoadFromZipCommand(model, view));
		viewCommands.put("save_zip", new SaveToZipCommand(model, view));
		viewCommands.put("loadFromXml", new LoadFromXmlCommand(model));
		
		
		
		modelCommands = new HashMap<String, Command>();
		modelCommands.put("display_maze_solution", new DisplayMazeSolutionCommand(model, view));
		modelCommands.put("display_message", new DisplayMessageCommand(model, view));
		modelCommands.put("display", new DisplayMaze3DCommand(model, view));


		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == model) 
		{
			
			String commandName = (String)arg;
			Command command = modelCommands.get(commandName);
			command.doCommand(null);
		}
		else if (o == view) 
		{
			String commandLine = (String)arg;
			String[] arr = commandLine.split(" ");
			String commandName = arr[0];	
			
			String[] args = null;
			if (arr.length > 1) {
				args = new String[arr.length - 1];			
				System.arraycopy(arr, 1, args, 0, arr.length - 1);
			}
			Command command = viewCommands.get(commandName);
			if(command == null)
			{
				view.displayMessage("Bad Command");
				return;
			}
			command.doCommand(args);
			
		}
	}

}
