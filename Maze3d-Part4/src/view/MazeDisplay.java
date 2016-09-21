package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.Solution;


/**
 * <h1>MazeDisplay</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public abstract class  MazeDisplay extends Canvas {
	
	/************************************** Data Members ***************************/
	protected int[][] mazeData;
	protected Maze3d myMaze;
	protected MazeWindow mazeWindow;
	protected GameCharacter character;
	protected GoalImg goal;

	protected String[] directions;
	Solution sol;
	
	/********************* Constructor of MazeDisplay ************************/
	public MazeDisplay(Composite parent, int style, MazeWindow mw) {
		super(parent, style);
		

		
		mazeWindow = mw;
		character = new GameCharacter(this.mazeWindow);
		goal = new GoalImg(this.mazeWindow);
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawMaze(e);
				e.gc.dispose();
			}
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			/**
			 * keypressed handler
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:
					goLeft();
					break;
				case SWT.ARROW_RIGHT:
					goRight();
					break;
				case SWT.ARROW_UP:
					goBackward();
					break;
				case SWT.ARROW_DOWN:
					goForward();
					break;
				case SWT.PAGE_UP:
					goUp();
					break;
				case SWT.PAGE_DOWN:
					goDown();
					break;
				// TODO: Finish this list
				}
				
			}
		});
		
	}
	
	
	
	/******************************************* Methods ***************************************/
	
	protected abstract void drawMaze(PaintEvent e);
	protected abstract void goLeft();
	protected abstract void goRight();
	protected abstract void goForward();
	protected abstract void goBackward();
	protected abstract void goUp();
	protected abstract void goSolution(Solution sol);
	protected abstract void goToHint(Maze3dPosition hint1);

	public abstract Boolean checkIfGoal(Maze3dPosition pos);
	public abstract void winGenerator();
	
	
	/************************* updateMazeDate ***********************/
	/**
	 * the method update the mazedata array when the character go up and down
	 */
	public void updateMazeDate(){
		try {
			this.mazeData = myMaze.getCrossSectionByZ(character.getPos().getCols());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/****************************** Getters & Setters *********************************/
	public GameCharacter getCharacter() {
		return character;
	}

	protected abstract void goDown();

	public void setMaze (Maze3d theMaze){
		this.myMaze = theMaze;
	}
	
	public void setMazeData (int[][] mazeData, Maze3d mymaze2){
		this.mazeData = mazeData;
		this.myMaze = mymaze2;
	}

	public void setCharacterPosition(Maze3dPosition pos) {
		character.setPos(pos);
		}
	
	public Maze3dPosition getCharacterPosition() {
		return character.getPos();
		}
	
	public GoalImg getGoal() {
		return goal;
	}
	
	public void setGoalPosition (Maze3dPosition pos){
		goal.setPos(pos);
	}
	
	public Solution getSol() {
		return sol;
	}



	public void setSol(Solution sol) {
		this.sol = sol;
	}


	

}