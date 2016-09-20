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

	public Maze3dPosition getPos() {
		return pos;
	}
	
	
	public void setPos(Maze3dPosition pos1) {
		pos = new Maze3dPosition(pos1.getFloor(), pos1.getRows(), pos1.getCols());

	}
	
	public boolean getDoesItFinish() {
		return doesItFinish;
	}

	public void setDoesItFinish(boolean doesItFinish) {
		this.doesItFinish = doesItFinish;
	}
	
	public boolean isChangedPosition() {
		return changedPosition;
	}

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
