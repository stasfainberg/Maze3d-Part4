package presenter;

import model.Model;
import view.View;

/**
 * <h1>DisplayMaze3DCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class DisplayMaze3DCommand implements Command {

	private Model model;
	private View view;

	   /**
	     * Constructor of DisplayMaze3DCommand
	   */
		public DisplayMaze3DCommand(Model model, View view) 
		{
			this.model = model;
			this.view = view;
		}

		@Override
		public void doCommand(String[] args) 
		{
			view.Display(model.getMaze3d());

		}

}
