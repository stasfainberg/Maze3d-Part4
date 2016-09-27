package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Maze3dPosition;

/**
 * <h1>GameCharacter</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public class GameCharacter {

	/****************************** Data Members *********************************/

	private static final String icon = "images\\characterImage.png";
	private static final String finishIcon = "images\\finishImage.png";

	boolean doesItFinish = false;
	boolean changedPosition = false;



	
	private Maze3dPosition pos;
	protected MazeWindow mazeWindow;
	
	
	/************************ Constructor of GameCharacter ***********************/
	public GameCharacter(MazeWindow mazeWindow2) {
		mazeWindow = mazeWindow2;
	}
	
	/****************************** Getters & Setters *********************************/

	/**
	 * <h1>getPos()</h1>
	 * This method getPos() returns the character's position.
	 * 
	 * @return pos - holds the character's position.
	 */
	public Maze3dPosition getPos() {
		return pos;
	}
	
	/**
	 * <h1>setPos()</h1>
	 * This method setPos() sets the character's position according to the Maze3dPosition
	 * which is passed to this method.
	 * 
	 * @param Maze3dPosition pos1 - holds the new position.
	 */
	public void setPos(Maze3dPosition pos1) {
		pos = new Maze3dPosition(pos1.getFloor(), pos1.getRows(), pos1.getCols());

	}
	
	
	/**
	 * <h1>getDoesItFinish()</h1>
	 * This method getDoesItFinish() returns boolean variable which defines if the character 
	 * reached the goal.
	 * 
	 * @return doesItFinish - holds the true or false if the character reached the goal or not.
	 */
	public boolean getDoesItFinish() {
		return doesItFinish;
	}

	
	/**
	 * <h1>setDoesItFinish()</h1>
	 * This method setDoesItFinish() sets the boolean variable doesItFinish with true or false.
	 * 
	 * @param boolean doesItFinish - holds true or false.
	 */
	public void setDoesItFinish(boolean doesItFinish) {
		this.doesItFinish = doesItFinish;
	}
	
	
	/**
	 * <h1>isChangedPosition()</h1>
	 * This method isChangedPosition() returns a boolean variable 
	 * which defines if the position of the character was changed or not.
	 * 
	 * @return changedPosition - holds true or false if the character position changed or not.
	 */
	public boolean isChangedPosition() {
		return changedPosition;
	}

	
	/**
	 * <h1>setChangedPosition()</h1>
	 * This method setChangedPosition() sets the variable changedPosition to 
	 * true or false according to the boolean variable which is passed to this method.
	 * 
	 * @param boolean changedPosition - holds true or false.
	 */
	public void setChangedPosition(boolean changedPosition) {
		this.changedPosition = changedPosition;
	}
	
	
	/****************************** Methods *********************************/

	public void draw(PaintEvent e, int cellWidth, int cellHeight) 
	{

		
		if(doesItFinish){
			
			
			e.gc.setBackground(new Color(null, 255, 255, 255));
			Image finishImg = new Image(null, finishIcon);
			e.gc.drawImage(finishImg, 0, 0, 128, 128, pos.getCols() * cellWidth, pos.getRows() * cellHeight, cellWidth, cellHeight);
			doesItFinish = false;

		}else if(changedPosition)
		{
			e.gc.setBackground(new Color(null, 255, 255, 255));
			Image sameIimg = new Image(null, icon);
			e.gc.drawImage(sameIimg, 0, 0, 128, 128, pos.getCols() * cellWidth, pos.getRows() * cellHeight, cellWidth, cellHeight);
			mazeWindow.setCharPosText(this.getPos().toString());
			changedPosition = false;
		}else{

			e.gc.setBackground(new Color(null, 255, 255, 255));
			Image img = new Image(null, icon);
			e.gc.drawImage(img, 0, 0, 128, 128, pos.getCols() * cellWidth, pos.getRows() * cellHeight, cellWidth, cellHeight);
			mazeWindow.setCharPosText(this.getPos().toString());

		}
		
		

	}
	
	
	
	
	
	
}
