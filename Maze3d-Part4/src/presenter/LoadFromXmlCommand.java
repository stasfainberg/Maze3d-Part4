package presenter;

import java.io.FileNotFoundException;

import model.Model;
import view.View;

/**
 * <h1>LoadFromXmlCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class LoadFromXmlCommand implements Command {

	Model model;
	View view;
	
	/**
	 * Constructor of LoadFromXmlCommand
	 */
	public LoadFromXmlCommand(Model model) {
		this.model = model;
	}
	@Override
	public void doCommand(String[] args) {
		try {
			if (args==null||args.length !=1)
			{
				view.displayMessage("Incorrect number of args");
				return;
			}
			model.loadFromXml(args[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
