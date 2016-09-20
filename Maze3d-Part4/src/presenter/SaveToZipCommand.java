package presenter;

import model.Model;
import view.View;

/**
 * <h1>SaveToZipCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class SaveToZipCommand implements Command {

	private Model model;
	private View view;
	
	/**
	 * Constructor of SaveToZipCommand
	 */
	public SaveToZipCommand(Model model, View view)
	{
		this.model = model;
		this.view = view;
	}
	@Override
	public void doCommand(String[] args) 
	{
	
		if (args==null||args.length!=1)
		{
			view.displayMessage("Incorrect number of args");
			return;
		}
		String filename = args[0];
		
		model.SaveToZip(filename);
	}

}
