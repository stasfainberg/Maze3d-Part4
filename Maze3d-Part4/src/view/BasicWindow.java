package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <h1>BasicWindow</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public abstract class BasicWindow extends Observable implements Runnable {

	/*************************** Data Members ************************/
	protected Display display;
	protected Shell shell;
	protected Text charPosText;
	protected Text mazeNameText;


	/****************** Constructor of BasicWindow *******************/
	public BasicWindow() {
		display = new Display();
		shell = new Shell(display);
	}

	/******************************** Methods **************************/
	protected abstract void initWidgets();
	
	@Override
	public void run() {
		initWidgets();
		
		shell.open();
		 while(!shell.isDisposed()){ // window isn't closed
			  if(!display.readAndDispatch()){
			   display.sleep();
			  }
		}
			 display.dispose();
	}
}
