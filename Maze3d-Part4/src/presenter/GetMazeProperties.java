package presenter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <h1>GetMazeProperties</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 */
public class GetMazeProperties implements Runnable
{
	private MessageBox messageBox;
	Display display ;
	Shell shell ;
	private String stringmaze;
	private String mazename;
	private String myfloor;
	private String myrow;
	private String mycolumn;
	
	/**
	 * Constructor of GetMazeProperties
	 */
	public GetMazeProperties(Display display)
	{
		this.display = display;
		this.shell = new Shell(display);
		this.shell.setBounds(400, 0, 300, 400);
		this.shell.setLayout(new GridLayout(2, false));
	}
	


	/************************* initWidgets ***********************/
	/**
    * The method creates the GUI and there listeners.
	*/
	public void initWidgets() 
	{

		GridData labels = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		GridData texts = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		
		Label name = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		name.setLayoutData(labels);
		name.setText("Maze need a Name:");
		Text mname = new Text(shell, SWT.BORDER);
		mname.setLayoutData(texts);

		
		Label floor  = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		floor.setLayoutData(labels);
		floor.setText("Maze Floors:");
		Text mfloor = new Text(shell, SWT.BORDER);
		mfloor.setLayoutData(texts);
		
		Label column  = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		column.setLayoutData(labels);
		column.setText("Maze Column:");
		Text mcolumn = new Text(shell, SWT.BORDER);
		mcolumn.setLayoutData(texts);
		
		Label row  = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		row.setLayoutData(labels);
		row.setText("Maze Row:");
		Text mrow = new Text(shell, SWT.BORDER);
		mrow.setLayoutData(texts);

		
		
		
		Button okB = new Button(shell, SWT.NONE);
		okB.setText("OK");
		okB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
		okB.addSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				mazename = mname.getText();
				myfloor = mfloor.getText();
				mycolumn=mcolumn.getText();
				myrow=mrow.getText();
				
				stringmaze= mname.getText()+ " " + mrow.getText()+ " " + mcolumn.getText()+ " " + mfloor.getText();
				shell.close();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}
	
	public void sendMessage(String s)
	{		
		messageBox.setMessage(s);
		messageBox.open();
	}
	


	public String getMyfloor() {
		return myfloor;
	}



	public String getMyrow() {
		return myrow;
	}



	public String getMycolumn() {
		return mycolumn;
	}



	@Override
	public void run() {
		initWidgets();

		shell.open();

		// main event loop
		while (!shell.isDisposed()) { // window isn't closed
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
		
	}



	public String getStringmaze() {
		return stringmaze;
	}



	public String getMazename() {
		return mazename;
	}

	
	
}
