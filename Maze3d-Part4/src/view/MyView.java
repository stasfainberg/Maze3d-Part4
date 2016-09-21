package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.search.MazeState;
import algorithms.search.Solution;
/**
 * <h1>MyView</h1>
 * @author Stas Fainberg
 * this class displays all the output from the controller to the users session 
 */
public class MyView extends Observable implements View,Observer {

	/********************************* Data Members ***********************/
	private BufferedReader in;
	private Writer out;
	private CLI cli;
	private Maze3dDisplay maze3dDisplay; 
	
	/*********************** Constructor of MyView ************************/
	public MyView(BufferedReader in, Writer out)
	{		
		this.in = in;
		this.out = out;
		this.cli =new CLI(in, out);
		cli.addObserver(this);	
		
	}
	
	/***************************** Setters & Getters ***************************/
	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public Writer getOut() {
		return out;
	}
	public void setOut(Writer out) {
		this.out = out;
	}
	public CLI getCli() {
		return cli;
	}
	public void setCli(CLI cli) {
		this.cli = cli;
	}
	public Maze3dDisplay getMaze3dDisplay() {
		return maze3dDisplay;
	}
	public void setMaze3dDisplay(Maze3dDisplay maze3dDisplay) {
		this.maze3dDisplay = maze3dDisplay;
	}
	
	
	/*************************************** Methods **********************************/
	@Override
	public void displayMessage(String message) 
	{
		try {
			out.write(message+"\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Override
	public void start() 
	{
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {				
				cli.start();
			}
			
		});	
		thread.start();
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
		
	}
	@Override
	public void Display(Maze3d mymaze)
	{
		try {			
			out.write(mymaze.toString());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	@Override
	public Maze3dPosition getplayerPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMazeSolution(Solution sol) {
		ArrayList<MazeState> mysolvedmaze = sol.getStates();

		for (int i = 0; i < mysolvedmaze.size(); i++)
		{
			System.out.println(mysolvedmaze.get(i).getDescription());

		}		
	}

	@Override
	public void displayMazeHint(Maze3dPosition hint1) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}