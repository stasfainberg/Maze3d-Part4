package model;

import java.io.FileNotFoundException;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.Solution;
import presenter.Command;

/**
 * <h1>Model Interface</h1>
 * @author Stas Fainberg
 * @version 1.0
 */

public interface Model {
	
	void generateMaze(String name, int floor,int rows,int cols, String algo);
	void saveMaze(String name, String fileName);
	void loadMaze(String filename,String name);
	Maze3d getMaze(String name);
	void help(HashMap<String, Command> commands);
	void dir();
	void display(String mazeName);
	void displayCrossSection();
	void Exit();
	void solveMaze(String name, String algorithm, Maze3dPosition pos);
	void displaySolution(String name);
	String getMessage();
	void LoadFromZip(String filename);
	void SaveToZip(String filename);
	public Maze3d getMaze3d();
	void loadFromXml(String name) throws FileNotFoundException;
	Solution getSol();

}







