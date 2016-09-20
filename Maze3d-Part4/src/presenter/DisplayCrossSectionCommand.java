package presenter;

import model.Model;
import view.View;

/**
 * <h1>DisplayCrossSectionCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class DisplayCrossSectionCommand implements Command{

	private Model model;
	private View view;
	
	/**
	 * Constructor of DisplayCrossSectionCommand
	 */
	public DisplayCrossSectionCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	/************************* doCommand() ***********************/
	/**
	 * the function display cross section of the 3Dmaze as a 2Dmaze 
	 * by calling to method displayCroosSection() from MyModel
	 * the function displayCroosSection() display the cross section (x/y/z) by index
	 */
	@Override
	public void doCommand(String[] args) {
		model.displayCrossSection();
	}

}
