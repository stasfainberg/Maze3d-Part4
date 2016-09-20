package view;

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
 * <h1>GenerateWindow</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 * The class demonstrates how to create your own dialog classes. 
 * It allows users to input a String
 */
public class GenerateWindow extends Dialog {

	/********************************** Data Members ********************************/
	protected MessageBox messageBox = new MessageBox(getParent());
	private String name;
	private int floor, rows, cols;
	Display display;

	
	
	/******************************* Constructors of GenerateWindow *******************/

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param display
	 *            the display
	 */
	public GenerateWindow(Shell parent, Display display) {
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.display = display;
	}

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public GenerateWindow(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
	}

	
	/********************************** Setters & Getters ********************************/

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	
	
	/********************************** Methods ********************************/

	/******************* initWidgets ******************/
	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public void initWidgets() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText("Generate Maze");
		
		
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

	/******************* createContents ******************/
	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell
	 *            the dialog window
	 */
	private void createContents(final Shell shell) {
		shell.setBounds(350, 200, 600, 600);
		shell.setLayout(new GridLayout(1, true));
		
		
		/******* Name *******/
		// Show the message
		Label label_name = new Label(shell, SWT.NONE);
		label_name.setText("Name: ");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label_name.setLayoutData(data);

		// Display the input box
		final Text text = new Text(shell, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		text.setLayoutData(data);
	
		
		text.addListener(SWT.Verify, new Listener()
		{
		    @Override
		    public void handleEvent(Event e)
		    {
		        // Get the source widget
		        Text source = (Text) e.widget;

		        // Get the text
		        final String oldS = source.getText();
		        final String newS = oldS.substring(0, e.start) + e.text + oldS.substring(e.end);

		    }
		});
		
		/************ Grayed text ************/
		text.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
        text.setText("Enter maze name");

        text.addListener(SWT.FocusOut, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {
                if("".equals(text.getText()))
                {
                    text.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
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
		
		
		
		
		/******* Floor *******/
		Label label_floor = new Label(shell, SWT.NONE);
		label_floor.setText("Floors: ");
		GridData data_floor = new GridData();
		data_floor.horizontalSpan = 2;
		label_floor.setLayoutData(data);

		// Display the input box
		final Text text_floor = new Text(shell, SWT.BORDER);
		data_floor = new GridData(GridData.FILL_HORIZONTAL);
		data_floor.horizontalSpan = 2;
		text_floor.setLayoutData(data);

        
        
        
        /************ Grayed text ************/
        text_floor.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
        text_floor.setText("numbers only");
        text_floor.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));

        text_floor.addListener(SWT.FocusOut, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {
                if("".equals(text_floor.getText()))
                {
                	text_floor.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_floor.setText("numbers only");
                }
            }
        });

        text_floor.addListener(SWT.FocusIn, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {

                if("numbers only".equals(text_floor.getText()))
                {
                	text_floor.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_floor.setText("");
                }
            }
        });

        Label floorLabel = new Label(shell, SWT.NONE);
        floorLabel.setFocus();
        floorLabel.forceFocus();
        
        
		/************* verify numbers **************/
		text_floor.addListener(SWT.Verify, new Listener() {
	      @Override
		public void handleEvent(Event e) {
	        String string = e.text;
	        char[] chars = new char[string.length()];
	        string.getChars(0, chars.length, chars, 0);
	        for (int i = 0; i < chars.length; i++) {
	          if (!('0' <= chars[i] && chars[i] <= '9')) {
	            e.doit = false;
	            return;
	          }
	        }
	      }
	    });
        
		/******* Rows *******/
		Label label_rows = new Label(shell, SWT.NONE);
		label_rows.setText("Rows: ");
		GridData data_rows = new GridData();
		data_rows.horizontalSpan = 2;
		label_rows.setLayoutData(data);

		// Display the input box
		final Text text_rows = new Text(shell, SWT.BORDER);
		data_rows = new GridData(GridData.FILL_HORIZONTAL);
		data_rows.horizontalSpan = 2;
		text_rows.setLayoutData(data);
		
		
		/************ Grayed text ************/
		text_rows.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
		text_rows.setText("numbers only");

		text_rows.addListener(SWT.FocusOut, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {
                if("".equals(text_rows.getText()))
                {
                	text_rows.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_rows.setText("numbers only");
                }
            }
        });

		text_rows.addListener(SWT.FocusIn, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {

                if("numbers only".equals(text_rows.getText()))
                {
                	text_rows.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_rows.setText("");
                }
            }
        });

        Label rowsLabel = new Label(shell, SWT.NONE);
        rowsLabel.setFocus();
        rowsLabel.forceFocus();
        
		/******************* verify numbers ***************/
		text_rows.addListener(SWT.Verify, new Listener() {
		      @Override
			public void handleEvent(Event e) {
		        String string = e.text;
		        char[] chars = new char[string.length()];
		        string.getChars(0, chars.length, chars, 0);
		        for (int i = 0; i < chars.length; i++) {
		          if (!('0' <= chars[i] && chars[i] <= '9')) {
		            e.doit = false;
		            return;
		          }
		        }
		      }
		    });
        
		/******* Cols *******/
		Label label_cols = new Label(shell, SWT.NONE);
		label_cols.setText("Columns: ");
		GridData data_Cols = new GridData();
		data_Cols.horizontalSpan = 2;
		label_cols.setLayoutData(data);

		// Display the input box
		final Text text_cols = new Text(shell, SWT.BORDER);
		data_Cols = new GridData(GridData.FILL_HORIZONTAL);
		data_Cols.horizontalSpan = 2;
		text_cols.setLayoutData(data);
		
		
		/************ Grayed text ************/
		text_cols.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
		text_cols.setText("numbers only");

		text_cols.addListener(SWT.FocusOut, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {
                if("".equals(text_cols.getText()))
                {
                	text_cols.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_cols.setText("numbers only");
                }
            }
        });

		text_cols.addListener(SWT.FocusIn, new Listener()
        {
            @Override
            public void handleEvent(Event arg0)
            {

                if("numbers only".equals(text_cols.getText()))
                {
                	text_cols.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                	text_cols.setText("");
                }
            }
        });

        Label colsLabel = new Label(shell, SWT.NONE);
        rowsLabel.setFocus();
        rowsLabel.forceFocus();
        
		/*********************** verify numbers ***********************/
		text_cols.addListener(SWT.Verify, new Listener() {
		      @Override
			public void handleEvent(Event e) {
		        String string = e.text;
		        char[] chars = new char[string.length()];
		        string.getChars(0, chars.length, chars, 0);
		        for (int i = 0; i < chars.length; i++) {
		          if (!('0' <= chars[i] && chars[i] <= '9')) {
		            e.doit = false;
		            return;
		          }
		        }
		      }
		    });
        
        
		/******* OK *******/
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

				if (text.getText().equals("Enter maze name") || 
						text_floor.getText().equals("numbers only") || 
						text_rows.getText().equals("numbers only") || 
						text_cols.getText().equals("numbers only")) 
				{

					sendMessage("Please fill in all fields");
					
				} else 
				{
					name = text.getText();
					floor = Integer.parseInt(text_floor.getText());
					rows = Integer.parseInt(text_rows.getText());
					cols = Integer.parseInt(text_cols.getText());
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
				
				text.setText(""); 
				text_floor.setText("");
				text_rows.setText("");
				text_cols.setText("");
				shell.dispose();
			}
		});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);

		if (text.getText() == null)
			shell.close();
	}

	public void sendMessage(String s) {
		messageBox.setMessage(s);
		messageBox.open();
	}

	
	
	
}
