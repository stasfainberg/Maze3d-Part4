package view;


import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.Solution;
/**
 * <h1>View Interface</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
public interface View {
	
	public Maze3dPosition getplayerPos();
	void displayMessage(String message);
	void Display(Maze3d maze3d);
	void displayMazeSolution(Solution sol);
	void displayMazeHint(Maze3dPosition hint1);
	void setMyMazesNames(ArrayList<String> myArray);
	void start();

	
}
