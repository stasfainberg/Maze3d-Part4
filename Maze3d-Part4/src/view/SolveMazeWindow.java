package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1>SolveMazeWindow</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public class SolveMazeWindow extends Dialog {
	
	/************************ Data Members *********************************/
	String searchAlgo; //The user choose DFS or Best first Search  
	protected MessageBox messageBox = new MessageBox(getParent());
	Display display;

	
	/********************* Constructor of SolveMazeWindow ******************/
	public SolveMazeWindow(Shell parent, Display display) {
		super(parent);
		this.display = display;
		// TODO Auto-generated constructor stub
	}
	
	/********************************* Setters & Getters *********************************/

	public String getSearchAlgo() {
		return searchAlgo;
	}

	public void setSearchAlgo(String searchAlgo) {
		this.searchAlgo = searchAlgo;
	}


	
	/********************************* Methods *********************************/

	  public void initWidgets() {
		    // Create the dialog window
		    Shell shell = new Shell(getParent(), getStyle());
		    shell.setBounds(275, 350, 400, 400);
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

	/************************* createContents ***********************/
	/** The method creates the dialog's contents
	 * 
	 * @param shell the dialog window
	*/
	 private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, false));
//	    Display display = shell.getDisplay();
	    (new Label(shell, SWT.NULL)).setText("Select algorithm to solve the maze: ");

	    final Combo combo = new Combo(shell, SWT.NULL);
	    combo.setText("Choose algorithm");
	    String[] languages = new String[]{"DFS", "BestFirstSearch"};
	    
	    for(int i=0; i<languages.length; i++)
	      combo.add(languages[i]);
	    
	    //combo.add("Perl", 5);
	    //combo.setItem(5, "Perl");
	    
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.addSelectionListener(new SelectionListener() {
	    	@Override
			public void widgetSelected(SelectionEvent e) {

	    		if(combo.getSelectionIndex() == -1){
	    			
					sendMessage("Please choose algorithm");

	    		}else
	    		{
	    			searchAlgo = combo.getItem(combo.getSelectionIndex());
	    			shell.close();
	    		
	    		}
		      }

		      @Override
			public void widgetDefaultSelected(SelectionEvent e) {
		      }
		    });
	    
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.addSelectionListener(new SelectionAdapter() {
	        @Override
			public void widgetSelected(SelectionEvent event) {
	        	
	        	shell.close();
	        }
	      });
	    
	    shell.setDefaultButton(ok);
	   
	  }

	 
		public void sendMessage(String s) {
			messageBox.setMessage(s);
			messageBox.open();
		}
		
		
	 
	}


