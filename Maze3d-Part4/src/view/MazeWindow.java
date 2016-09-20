package view;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.Solution;

/**
 * <h1>MazeWindow</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public class MazeWindow extends BasicWindow implements View {

	/**********************************
	 * Data Members
	 *********************************/
	private MazeDisplay mazeDisplay;
	private Maze3d mymaze;


	protected Solution sol;
	String mazeNameString;
	int count = 0;
	Timer timer;

	TimerTask task;

	/**************************************** Methods **************************************/
	@Override
	protected void initWidgets() {

		/************************* General Settings **************************/
		shell.setSize(600, 500);
//		shell.setBounds(125, 125, 600, 500);
		shell.setText("Maze3d Project created by Stas Fainberg");

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Image img = new Image( display, "images\\titleImage.jpg");
		shell.setImage(img);

		// shell.setBackgroundImage(img);

		/***************************** Menu **************************/

		// Create the bar menu
		Menu menuBar = new Menu(shell, SWT.BAR);
		// Create the File item's dropdown menu
		Menu fileMenu = new Menu(menuBar);
		Menu helpMenu = new Menu(menuBar);

		// Create all the items in the bar menu
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);

		MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
		openItem.setText("Open_properties");

		class Open_properties implements SelectionListener {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);

				setChanged();
				notifyObservers("loadFromXml " + selected);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		}

		openItem.addSelectionListener(new Open_properties());


		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Do you really want to exit?");
				messageBox.setText("Exiting Application");
				int response = messageBox.open();
				if (response == SWT.YES)
					System.exit(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Create all the items in the bar menu
		MenuItem fileItem1 = new MenuItem(menuBar, SWT.CASCADE);
		fileItem1.setText("Help");
		fileItem1.setMenu(helpMenu);

		MenuItem aboutItem = new MenuItem(helpMenu, SWT.NONE);
		aboutItem.setText("About");
		aboutItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);

				messageBox.setText("About");
				messageBox
						.setMessage("***************************************\n" + "This Maze3d project was created by: \n"
								+ "\n                Stas Fainberg\n" + "***************************************");
				messageBox.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});


		shell.setMenuBar(menuBar);

		/****************************** Buttons *****************************/

		// Canvas Display maze
		Composite toolbar = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		toolbar.setLayout(rowLayout);
	
		

		Label firstLabel = new Label(toolbar, SWT.NONE);
		firstLabel.setText("===============\n"
						 + "    To start playing \n"
						 + "  press Generate Maze\n"
						 + "===============\n\n\n");
		
		Button btnGenerateMaze = new Button(toolbar, SWT.PUSH);
		btnGenerateMaze.setText("Generate Maze");
		btnGenerateMaze.setToolTipText("Click to generate maze3d");
		btnGenerateMaze.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GenerateWindow generateWindow = new GenerateWindow(shell, display);
				generateWindow.initWidgets();

				if (generateWindow.getName() != null) {
					setChanged();
					notifyObservers("generate_maze " + generateWindow.getName() + " " + generateWindow.getFloor() + " "
							+ generateWindow.getRows() + " " + generateWindow.getCols());
				} else {
					return;
				}

			}
		});

		Button btnDisplayMaze = new Button(toolbar, SWT.PUSH);
		btnDisplayMaze.setText("Display Maze");
		btnDisplayMaze.setToolTipText("Click to display maze3d");
		btnDisplayMaze.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DisplayMazeWindow displayMazeWindow = new DisplayMazeWindow(shell, display);
				displayMazeWindow.initWidgets();
				
				if(displayMazeWindow.getMazeName() != null){
					setMazeNameText(displayMazeWindow.getMazeName());
					setChanged();
					notifyObservers("display " + displayMazeWindow.getMazeName());
				}else
				{
					return;
				}
				
			}
		});

		
		
		Button btnHintMaze = new Button(toolbar, SWT.PUSH);
		btnHintMaze.setText("Hint");
		btnHintMaze.setToolTipText("Click to get a hint");
		btnHintMaze.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SolveMazeWindow solveMazeWindow = new SolveMazeWindow(shell, display);
				solveMazeWindow.initWidgets();

				if (solveMazeWindow.getSearchAlgo() != null) {
					setChanged();
					notifyObservers("solve " + "" + solveMazeWindow.getSearchAlgo());
				} else {
					return;
				}

			}
		});
		
		
		
		Button btnSolveMaze = new Button(toolbar, SWT.PUSH);
		btnSolveMaze.setText("Solve Maze");
		btnSolveMaze.setToolTipText("Click to solve the maze3d");
		btnSolveMaze.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SolveMazeWindow solveMazeWindow = new SolveMazeWindow(shell, display);
				solveMazeWindow.initWidgets();

				if (solveMazeWindow.getSearchAlgo() != null) {
					setChanged();
					notifyObservers("solve " + "" + solveMazeWindow.getSearchAlgo());
				} else {
					return;
				}

			}
		});


		Button btnExitMaze = new Button(toolbar, SWT.PUSH);
		btnExitMaze.setText("Exit Game");
		btnExitMaze.setToolTipText("Click to exit maze3d");
		btnExitMaze.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Do you really want to exit?");
				messageBox.setText("Exiting Application");
				int response = messageBox.open();
				if (response == SWT.YES)
					System.exit(0);
			}
		});

		Label emptyLabel = new Label(toolbar, SWT.NONE);
		emptyLabel.setText("                 ");
		
		Label mazeLabel = new Label(toolbar, SWT.NONE);
		mazeLabel.setText("Maze Name: ");
		this.mazeNameText = new Text(toolbar, SWT.BORDER);

		Label charLabel = new Label(toolbar, SWT.NONE);
		charLabel.setText("Figure Position: ");
		this.charPosText = new Text(toolbar, SWT.BORDER);

		mazeDisplay = new Maze3dDisplay(shell, SWT.BORDER, this);


	}

	@Override
	public void displayMessage(String message) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage(message);
				msg.open();
			}
		});
	}



	@Override
	public void start() {
		this.run();
	}

	@Override
	public void Display(Maze3d mymaze1) {
		this.mymaze = mymaze1;
		
		Maze3dPosition startPos = mymaze.getStartPosition();
		Maze3dPosition goalPos = mymaze.getGoalPosition();

		int[][] mazeData = mymaze.getCrossSectionByX(startPos.getFloor());

		System.out.println(mymaze.toString());
		
		mazeDisplay.setCharacterPosition(startPos);
		mazeDisplay.setGoalPosition(goalPos);
		mazeDisplay.setMazeData(mazeData, mymaze);
		mazeDisplay.redraw();

	}

	/**************************************
	 * Setters & Getters
	 ****************************/
	public void setCharPosText(String string) {
		this.charPosText.setText(string);
	}

	public Text getCharPosText() {
		return this.charPosText;
	}

	public void setMazeNameText(String string) {
		this.mazeNameText.setText(string);
	}

	public Text getMazeNameText() {
		return this.mazeNameText;
	}

	@Override
	public Maze3dPosition getplayerPos() {

		return mazeDisplay.character.getPos();
	}
	
	public String getMazeNameString() {
		return mazeNameString;
	}

	public void setMazeNameString(String mazeNameString) {
		this.mazeNameString = mazeNameString;
	}
	
	public Maze3d getMymaze() {
		return mymaze;
	}

	public void setMymaze(Maze3d mymaze) {
		this.mymaze = mymaze;
	}

	@Override
	public void displayMazeSolution(Solution sol) {
		
		mazeDisplay.setSol(sol);
		mazeDisplay.setMaze(mymaze);
		mazeDisplay.goSolution(sol);
		
	}
	
	
	

}
