package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <h1>DisplayMazeWindow</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public class DisplayMazeWindow extends Dialog{

	
	/********************************* Data Members *********************************/
	private String mazeName;
	protected MessageBox messageBox = new MessageBox(getParent());
	Display display;
	int count;
	
	/********************** Constructor of DisplayMazeWindow ************************/
	public DisplayMazeWindow(Shell parent, Display display) {
		super(parent);
		this.display = display;
		// TODO Auto-generated constructor stub
	}
	

	/********************************* Getters & Setters *********************************/

	/**
	 * <h1>getMazeName()</h1>
	 * This method getMazeName() returns the maze3d name.
	 * 
	 * @return mazeName - holds the Maze3d name.
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * <h1>setMazeName()</h1>
	 * This method setMazeName() sets the maze3d name according to the String which is passed to this method.
	 * 
	 * @param String mazeName - holds the maze3d name.
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}
	
	
	
	/********************************* Methods *********************************/

	public void initWidgets()
	{
		// Create the dialog window
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setBounds(350, 300, 400, 400);
	    shell.setText(getText());
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	  }

	 /*********************************** createContents ************************************/
	 /**
	  * The method creates the dialog's contents
	  * 
	  * @param shell the dialog window
	 */
	  private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(1, true));
	    /*******Name*******/
	    // Show the message
	    Label label_name = new Label(shell, SWT.NONE);
	    label_name.setText("Display Maze");
	    GridData data = new GridData();
	    data.horizontalSpan = 2;
	    label_name.setLayoutData(data);
	    
	    
	    // Display the input box
	    final Text text = new Text(shell, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 2;
	    text.setLayoutData(data);
	    
	   
	    /********************** grayed out **********************/
	    text.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
        text.setText("Enter maze name");

        text.addListener(SWT.FocusOut, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {
                if("".equals(text.getText()))
                {
                    text.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
                    text.setText("Enter maze name");
                }
            }
        });

        text.addListener(SWT.FocusIn, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {

                if("Enter maze name".equals(text.getText()))
                {
                    text.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                    text.setText("");
                }
            }
        });

        Label label = new Label(shell, SWT.NONE);
        label.setFocus();
        label.forceFocus();
	    
	    /*******OK*******/
	    // Create the OK button and add a handler
	    // so that pressing it will set input
	    // to the entered value
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(data);
	    
	    ok.addSelectionListener(new SelectionAdapter() {
	      @Override
		public void widgetSelected(SelectionEvent event) {
	        
	    	  if (text.getText().equals(""))
				{

					sendMessage("Please type maze name");
					
				} else 
				{
			    	  mazeName = text.getText();
				      shell.close();
				}

	      }
	    });

	    // Create the cancel button and add a handler
	    // so that pressing it will set input to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(data);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      @Override
		public void widgetSelected(SelectionEvent event) {
	        shell.close();
	      }
	    });

	    // Set the OK button as the default, so
	    // user can type input and press Enter
	    // to dismiss
	    shell.setDefaultButton(ok);
	    Display display = shell.getDisplay();

	}
	  
	  
	  
		public void sendMessage(String s) {
			messageBox.setMessage(s);
			messageBox.open();
		}


		
		
		
		
}
