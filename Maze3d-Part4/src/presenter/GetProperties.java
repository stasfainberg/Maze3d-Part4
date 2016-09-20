package presenter;


import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1>GetProperties</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * The class make a GUI with the user, and sets the {@link presenter.Properties Properties} 
 */
public class GetProperties implements Runnable{
	
	private Properties prop;
	private MessageBox messageBox;
	Display display ;
	Shell shell ;
	
	/**
	 * Constructor of GetProperties
	 */
	public GetProperties()
	{
		display = new Display();
		shell = new Shell(display);
		
		shell.setText("Maze3d Game Properties");
		shell.setBounds(480, 150, 450, 300);
		
		Image img = new Image(display, "images\\titleImage.jpg");
		shell.setImage(img);
		
		shell.setLayout(new GridLayout(2, false));
		messageBox = new MessageBox(shell,  SWT.ICON_INFORMATION| SWT.OK);
		messageBox.setText("Message");
		prop = new Properties();
	}
	


	/************************* initWidgets ***********************/
	/**
    * The method creates the GUI and there listeners.
	*/
	public void initWidgets() 
	{

		GridData labels = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		GridData texts = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		
		Label ui = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		ui.setLayoutData(labels);
		ui.setText("Choose UI:");
		Combo uiList = new Combo(shell, SWT.READ_ONLY);
		uiList.setLayoutData(texts);
		String[] uis = {"CLI","GUI" };
		uiList.setItems(uis);
		
		Label algorithm = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		algorithm.setLayoutData(labels);
		algorithm.setText("Choose Search Algorithm:");
		Combo algorithmList = new Combo(shell, SWT.READ_ONLY);
		algorithmList.setLayoutData(texts);
		String[] algorithms = {"DFS","BestFirstSearch"};
		algorithmList.setItems(algorithms);
		
		Label generator = new Label(shell, SWT.READ_ONLY);
		generator.setText("Choose Generator's Algorithm:");
		generator.setLayoutData(labels);
		Combo generatorList = new Combo(shell, SWT.READ_ONLY);
		generatorList.setLayoutData(texts);
		String[] generators = {"GrowingTree","Simple"};
		generatorList.setItems(generators);
		
		
		Button okB = new Button(shell, SWT.NONE);
		okB.setText("OK");
		okB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
		okB.addSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				
				
				if(uiList.getSelectionIndex() == -1 || 
						algorithmList.getSelectionIndex() == -1 || 
						generatorList.getSelectionIndex() == -1 )
		    	 {
					sendMessage("Please set all fields first\n"
							   +"         or choose         \n"
							   +"   Default Properties        ");
		    	 }else
		    	 {
		    		 XMLEncoder encoder = null;
						try 
						{
							encoder = new XMLEncoder(new FileOutputStream("files\\properties.xml"));
						}
						catch (FileNotFoundException e)
						{
							sendMessage(e.getMessage());
						}
						prop.setUitype(uiList.getText());
						prop.setAlgoSolve(algorithmList.getText());
						prop.setAlgoCreate(generatorList.getText());

						
						
						encoder.writeObject(prop);
						encoder.flush();
						sendMessage("Properties has been Saved");
						encoder.close();
						shell.close();
						display.close();
		    	 }
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		Button usedef = new Button(shell, SWT.NONE);
		usedef.setText("Use Default Properties");
		usedef.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
		usedef.addSelectionListener(new SelectionListener()
				{

					@Override
					public void widgetSelected(SelectionEvent e) {
						
						XMLEncoder encoder = null;
						try 
						{
							encoder = new XMLEncoder(new FileOutputStream("files\\properties.xml"));
						}
						catch (FileNotFoundException ee)
						{
							sendMessage(ee.getMessage());
						}
						prop.setUitype("GUI");
						prop.setAlgoSolve("BestFirstSearch");
						prop.setAlgoCreate("GrowingTree");

						
						
						encoder.writeObject(prop);
						encoder.flush();
						sendMessage("Default properties will be used");
						encoder.close();
						shell.close();
						display.close();
					
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
				
				});
				
	}
	
	public void sendMessage(String s)
	{		
		messageBox.setMessage(s);
		messageBox.open();
	}
	
	public Properties getproperties()
	{
		return prop;
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
		display.dispose();
	}
		
	}