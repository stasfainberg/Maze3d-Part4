package presenter;

import model.Model;
import view.View;

/**
 * <h1>DisplayMessageCommand</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * @param Model - Model of the Program
 * @param view - View  of the Program
 */
public class DisplayMessageCommand implements Command {

	private View view;
	private Model model;
	
	/**
	 * Constructor of DisplayMessageCommand
	 */
	public DisplayMessageCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String msg = model.getMessage();
		
		view.setMyMazesNames(model.getMazesNames());
		view.displayMessage(msg);
	}
	
	
	

}
