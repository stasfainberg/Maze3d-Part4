package view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.MazeState;
import algorithms.search.Solution;

/**
 * <h1>Maze3dDisplay</h1>
 * @author Stas Fainberg
 * @version 1.0
 * 
 */
public class Maze3dDisplay extends MazeDisplay {

	
	protected MazeWindow mazeWindow;
	Timer timer;
	TimerTask task;
	int h;
	int w;

	
	
	/********************* Constructor of Maze3dDisplay ************************/
	public Maze3dDisplay(Composite parent, int style, MazeWindow mw1) {
		super(parent, style, mw1);
		mazeWindow = mw1;


		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.setBackground(new Color(null, 100, 200, 100));

		
		if(this.mazeData == null)
		{
			Image img = new Image(getDisplay(), "images\\titleImage.jpg");
			this.setBackgroundImage(img);
		}
		
		mazeWindow.getMazeNameText();
		
	}

	
	/************************ Methods ***************************/

	@Override
	protected void drawMaze(PaintEvent e) {

		
		
		if (mazeData == null)
			return;

		this.setBackground(new Color(null, 255, 255, 255));
		e.gc.setBackground(new Color(null, 128, 128, 128));

		int width = getSize().x;
		int height = getSize().y;

		w = width / mazeData[0].length;
		h = height / mazeData.length;

		
		
		
		
		for (int i = 0; i < mazeData.length; i++)
		{
			for (int j = 0; j < mazeData[i].length; j++) 
			{
				int x = j * w;
				int y = i * h;
				if (mazeData[i][j] != 0)
				{	
					e.gc.fillRectangle(x, y, w, h);
			
				}
			}
		}
		
		
		
		character.draw(e,w,h);
		
		if(character.getPos().getFloor() == goal.getPos().getFloor()){
			goal.draw(e, w, h);
		}
		
		
		if(myMaze!=null)
		{
			drawGoal(e, w, h, character);
		}



		

	}
	

	
	/************************ Directions ***************************/

	
	@Override
	protected void goLeft() {
		
		Maze3dPosition pos = character.getPos();		
		if(pos.getCols()-1 < 0)
			return;
		if(this.mazeData[pos.getRows()][pos.getCols()-1]==1)
			return;
		
		character.setPos(new Maze3dPosition(pos.getFloor(), pos.getRows(), pos.getCols()-1));

		character.setChangedPosition(true);

		
		this.redraw();
		
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		if(checkIfGoal(character.getPos()))
		{

			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	        messageBox.setMessage("***************************************\n"
	          					  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	        messageBox.open();
	        
	        this.redraw();

	        this.mazeData = null;
	  		if(this.mazeData == null)
			{
				Image img = new Image(getDisplay(), "images\\titleImage.jpg");
				this.setBackgroundImage(img);
				mazeWindow.charPosText.setText("");
				mazeWindow.mazeNameText.setText("");
			}  
		}
	}

	@Override
	protected void goRight() {
		Maze3dPosition pos = character.getPos();	
		if(pos.getCols()+1 >= this.mazeData[0].length)
			return;
		if(this.mazeData[pos.getRows()][pos.getCols()+1]==1)
			return;
		character.setPos(new Maze3dPosition(pos.getFloor(), pos.getRows(), pos.getCols()+1));
		
		character.setChangedPosition(true);

		
		this.redraw();
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		if(checkIfGoal(character.getPos()))
		{
			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	          messageBox.setMessage("***************************************\n"
	        		  			  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	          messageBox.open();

	          this.redraw();
	          
	          this.mazeData = null;
	          if(this.mazeData == null)
				{
					Image img = new Image(getDisplay(), "images\\titleImage.jpg");
					this.setBackgroundImage(img);
					mazeWindow.charPosText.setText("");
					mazeWindow.mazeNameText.setText("");

				}
		}

	}

	@Override
	protected void goForward() {
		Maze3dPosition pos = character.getPos();	
		if(pos.getRows()+1 >= this.mazeData[0].length)
			return;
		if(this.mazeData[pos.getRows()+1][pos.getCols()]==1)
			return;
		character.setPos(new Maze3dPosition(pos.getFloor(), pos.getRows()+1, pos.getCols()));
		
		character.setChangedPosition(true);

		
		
		
		this.redraw();
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		if(checkIfGoal(character.getPos()))
		{

			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	          messageBox.setMessage("***************************************\n"
	        		  			  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	          messageBox.open();

	          this.redraw();

	          this.mazeData = null;
	          if(this.mazeData == null)
				{
					Image img = new Image(getDisplay(), "images\\titleImage.jpg");
					this.setBackgroundImage(img);
					mazeWindow.charPosText.setText("");
					mazeWindow.mazeNameText.setText("");

				}
		}


	}

	@Override
	protected void goBackward() {
		Maze3dPosition pos = character.getPos();	
		if(pos.getRows()-1 < 0)
			return;
		if(this.mazeData[pos.getRows()-1][pos.getCols()]==1)
			return;
		character.setPos(new Maze3dPosition(pos.getFloor(), pos.getRows()-1, pos.getCols()));
		
		character.setChangedPosition(true);


		this.redraw();
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		
		if(checkIfGoal(character.getPos()))
		{

			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	          messageBox.setMessage("***************************************\n"
	        		  			  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	          messageBox.open();

	          this.redraw();

	          this.mazeData = null;
	          if(this.mazeData == null)
				{
					Image img = new Image(getDisplay(), "images\\titleImage.jpg");
					this.setBackgroundImage(img);
					mazeWindow.charPosText.setText("");
					mazeWindow.mazeNameText.setText("");

				}
		}


	}

	@Override
	protected void goUp() {
		Maze3dPosition pos = character.getPos();	
		if(pos.getFloor()+1 >= myMaze.getFloor())
			return;
		int[][]  upFloor = myMaze.getCrossSectionByX(pos.getFloor()+1);
		if(upFloor[pos.getCols()][pos.getRows()]==1)
			return;
		character.setPos(new Maze3dPosition(pos.getFloor()+1, pos.getRows(), pos.getCols()));
		this.setMazeData(upFloor, myMaze);
		
		character.setChangedPosition(true);


		
		this.redraw();
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		if(checkIfGoal(character.getPos()))
		{


			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	          messageBox.setMessage("***************************************\n"
	        		  			  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	          messageBox.open();

	          this.redraw();

	          this.mazeData = null;
	          if(this.mazeData == null)
				{
					Image img = new Image(getDisplay(), "images\\titleImage.jpg");
					this.setBackgroundImage(img);
					mazeWindow.charPosText.setText("");
					mazeWindow.mazeNameText.setText("");

				}
		}


	}

	@Override
	protected void goDown() {
		Maze3dPosition pos = character.getPos();	
		if(pos.getFloor()-1 < 0)
			return;
		int[][]  downFloor = myMaze.getCrossSectionByX(pos.getFloor()-1);
		if(downFloor[pos.getCols()][pos.getRows()]==1)
			return;
		
		character.setPos(new Maze3dPosition(pos.getFloor()-1, pos.getRows(), pos.getCols()));
		this.setMazeData(downFloor, myMaze);
		
		character.setChangedPosition(true);
		
		
		this.redraw();
		if(!character.getPos().equals(goal.getPos()))
		{
			this.update();
		}
		
		
		
		if(checkIfGoal(character.getPos()))
		{

			character.setDoesItFinish(true);
			goal.setDoesItFinish(true);
			
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING);
			messageBox.setText("Finish");
	          messageBox.setMessage("***************************************\n"
	        		  			  + "     You have reached the goal!!! \n"
	          					  + "***************************************");
	          messageBox.open();

	          this.redraw();

	          this.mazeData = null;
	          if(this.mazeData == null)
				{
					Image img = new Image(getDisplay(), "images\\titleImage.jpg");
					this.setBackgroundImage(img);
					mazeWindow.charPosText.setText("");
					mazeWindow.mazeNameText.setText("");

				}

		}


	}
	
	
	
	
	@Override
	protected void goSolution(Solution sol) {

		ArrayList<MazeState> mySolvedMaze = sol.getStates();

		System.out.println("first: ");
		for (int i = 0; i < mySolvedMaze.size(); i++)
			System.out.println(mySolvedMaze.get(i).getDescription());

		int count = 1;
		for (count = 1; count < mySolvedMaze.size(); count++) {
			if (count == mySolvedMaze.size()) {
				System.out.println("counter: " + count);
				return;

			}

			int k = 1;
			long start, finish, generate_time;
			start = System.currentTimeMillis();
			do {
				finish = System.currentTimeMillis();
				generate_time = finish - start;
			} while ((generate_time) < k * 500);

			MazeState nextStep = mySolvedMaze.get(count);
			
	
			
			if (this.getCharacter().getPos().getFloor() < nextStep.getCurrPlayerPosition().getFloor()) {
				System.out.println("up");
				goUp();

			}
			if (this.getCharacter().getPos().getFloor() > nextStep.getCurrPlayerPosition().getFloor()) {
				System.out.println("down");
				goDown();

			}
			if (this.getCharacter().getPos().getRows() < nextStep.getCurrPlayerPosition().getRows()) {
				System.out.println("forward");
				goForward();

			}
			if (this.getCharacter().getPos().getRows() > nextStep.getCurrPlayerPosition().getRows()) {
				System.out.println("backward");
				goBackward();

			}
			if (this.getCharacter().getPos().getCols() < nextStep.getCurrPlayerPosition().getCols()) {
				System.out.println("right");
				goRight();

			}
			if (this.getCharacter().getPos().getCols() > nextStep.getCurrPlayerPosition().getCols()) {
				System.out.println("left");
				goLeft();

			}
			
			
			
			
		

		}

	}


	void drawGoal(PaintEvent e,int cellWidth,int cellHeight,GameCharacter cher)
	{
		if(cher.getPos().equals(myMaze.getGoalPosition()));
		{
			String icon = "images\\goalImage.png";
			Image img = new Image(null, icon);
//			e.gc.drawImage(img, 0, 0, 150, 117, myMaze.getGoalPosition().getRows() * cellWidth, myMaze.getGoalPosition().getCols() * cellHeight, cellWidth, cellHeight);
		
			
		}
	}



	@Override
	public Boolean checkIfGoal(Maze3dPosition pos) {
		if(		(pos.getFloor() == myMaze.getGoalPosition().getFloor()) && 
				(pos.getRows() == myMaze.getGoalPosition().getRows()) && 
				(pos.getCols() == myMaze.getGoalPosition().getCols())	)
		{
			return true;
		}
		return false;
	}


	@Override
	public void winGenerator() {
		MessageBox generateMB = new MessageBox(new Shell());
		generateMB.setMessage("Congratulations you finished the maze");
		generateMB.setText("Congratulations you finished the maze");
		generateMB.open();		
	}


	@Override
	protected void goToHint(Maze3dPosition hint1) {
		
		if (this.getCharacter().getPos().getFloor() < hint1.getFloor()) {
			System.out.println("up");
			goUp();

		}
		if (this.getCharacter().getPos().getFloor() > hint1.getFloor()) {
			System.out.println("down");
			goDown();

		}
		if (this.getCharacter().getPos().getRows() < hint1.getRows()) {
			System.out.println("forward");
			goForward();

		}
		if (this.getCharacter().getPos().getRows() > hint1.getRows()) {
			System.out.println("backward");
			goBackward();

		}
		if (this.getCharacter().getPos().getCols() < hint1.getCols()) {
			System.out.println("right");
			goRight();

		}
		if (this.getCharacter().getPos().getCols() > hint1.getCols()) {
			System.out.println("left");
			goLeft();

		}
		
		
		
	}



	
	


	
	
}
