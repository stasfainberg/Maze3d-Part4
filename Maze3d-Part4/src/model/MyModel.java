package model;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.MazeState;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Command;
import presenter.Properties;

/**
 * <h1>MyModel Class</h1>
 * 
 * @author Stas Fainberg 
 * 
 * This class receives commands from the presenter and
 * executes them. when the model is done the message is 
 * sent to the presenter.
 */
public class MyModel extends Observable implements Model {

	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<Maze3d, Solution> mazeToSol = new HashMap<Maze3d, Solution>();
	private NavigableMap<String, Integer> map = new TreeMap<String, Integer>();
	private HashMap<String, CommonMaze3dGenerator> solvealgo = new HashMap<String, CommonMaze3dGenerator>();
	private HashMap<String, CommonSearcher> algorithms = new HashMap<String, CommonSearcher>();
	private HashMap<String, Solution> namesToSolution = new HashMap<String, Solution>();

	Maze3d maze3d;
	String maze3dLastName;
	private String message;
	Solution sol = null;
	ExecutorService executor;
	public static int count = 0;
	private Properties properties;
	Maze3dPosition startTmpPos;
	// private static final int threadsNum = 20;

	/**
	 * Constructor of MyModel
	 */
	public MyModel(ExecutorService executor) {
		this.executor = executor;
		BestFirstSearch BestFirstSearch = new BestFirstSearch();
		DFS DFS = new DFS();

		algorithms.put("BestFirstSearch", BestFirstSearch);
		algorithms.put("DFS", DFS);

		GrowingTreeGenerator growingTreeMaze = new GrowingTreeGenerator();
		SimpleMaze3dGenerator simpleMaze = new SimpleMaze3dGenerator();

		solvealgo.put("GrowingTree", growingTreeMaze);
		solvealgo.put("Simple", simpleMaze);
	}

	@Override
	public Maze3d getMaze(String name) {
		if (mazes.containsKey(name))
			return mazes.get(name);
		else
			return null;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void generateMaze(String name, int floor, int rows, int cols, String algo) {

		if (mazes.containsKey(name)) {
			this.message = "Maze '" + name + "' is already exist";
			setChanged();
			notifyObservers("display_message");
			return;

		}

		Future<Maze3d> mymaze = executor.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception {
				CommonMaze3dGenerator mg = solvealgo.get(algo);
				Maze3d maze = mg.generate(floor, rows, cols);
				mazes.put(name, maze);
				maze3dLastName = name;

				return maze;
			}

		});
		try {
			maze3d = mymaze.get();
			mazes.put(name, maze3d);
			mazeToSol.put(maze3d, null);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setChanged();
		this.message = "Maze '" + name + "' is ready";
		notifyObservers("display_message");

	}

	/************************* save maze ***********************/
	/**
	 * This function saveMaze() saves the maze with the name to a file.
	 * 
	 * @param name - name of the maze.
	 * @param fileName  - name of the file.
	 */
	@Override
	public void saveMaze(String name, String fileName) {

		if (mazes.isEmpty()) {
			message = "> Information>> Maze3d list is empty. No mazes to save. Please generate a maze3d first.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		Maze3d maze = mazes.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			byte[] bytes = maze.toByteArray();
			out.write(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************************* load maze ***********************/
	/**
	 * This function loadMaze() loads a maze from a File.
	 * 
	 * @param name - Name of the maze which should be loaded.
	 * @param filename - Name of the loaded File.
	 * @throws IOException
	 */
	@Override
	public void loadMaze(String filename, String name) {
		if (mazes.containsKey(name)) {
			this.message = "Maze '" + name + "' already exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		byte[] myarry = null;
		try {

			InputStream in = new MyDecompressorInputStream(new FileInputStream(filename));
			int size = in.read();
			int count = in.read();
			if (count > 0) {
				count = count * 255;
				size = size + count;
			}
			myarry = new byte[size];
			in.read(myarry);
			in.close();
		} catch (FileNotFoundException e) {
			this.message = "File Not Found";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IOException e) {
			this.message = "IOEXception";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		mazes.put(name, new Maze3d(myarry));

	}

	/**
	 * help method 
	 * This function help() print all the
	 * commands that the user can enter
	 */
	/************ דוגמת הרצה *********/
	/**
	 * Such as executables (for example): > Please enter command or 'help': help
	 * 
	 * > Please type in command according to the list below: > generate maze >
	 * load Maze > exit > help > display by section > display > file size >
	 * solve maze > dir > save maze > maze size > display solution
	 */
	@Override
	public void help(HashMap<String, Command> commands) {
		message = "\n> Please type in command according to the list below: ";
		setChanged();
		notifyObservers("display_message");

		Set<String> keys = commands.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = i.next();
			message = "> " + key;
			setChanged();
			notifyObservers("display_message");
		}
		message = "";
		setChanged();
		notifyObservers("display_message");
	}

	/**
	 * the function dir() show all the files and directory in the path
	 */
	@Override
	public void dir() {

		Scanner input = new Scanner(System.in);
		String userPath = null;

		do {
			message = "> Type path to display it's content or type 'home': ";
			setChanged();
			notifyObservers("display_message");
			userPath = input.nextLine();
		} while (userPath.length() == 0);

		if (userPath.equals("home")) {
			File current = new File("."); // current directory

			File[] files = current.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					message = "> directory:";
					setChanged();
					notifyObservers("display_message");
				} else {
					message = ">      file:";
					setChanged();
					notifyObservers("display_message");
				}
				try {
					message = file.getCanonicalPath();
					setChanged();
					notifyObservers("display_message");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			File f = new File(userPath.toString());
			if (f.exists() && f.isDirectory()) {

				File[] files = f.listFiles();
				for (File file : files) {
					if (file.isDirectory()) {
						message = "> directory:";
						setChanged();
						notifyObservers("display_message");
					} else {
						message = ">      file:";
						setChanged();
						notifyObservers("display_message");
					}
					try {
						message = file.getCanonicalPath();
						setChanged();
						notifyObservers("display_message");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				message = "> Information>> Path does not exist";
				setChanged();
				notifyObservers("display_message");
			}
		}
		input.close();

	}

	/**
	 * the function display() printing the maze נדפיס למשתמש את המבוך בשם name
	 * 
	 * @param name
	 *            - name of the maze
	 */
	@Override
	public void display(String mazeName) {

		if (mazes.isEmpty()) {
			message = "> Information>> Maze3d list is empty. No mazes to display!!!";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		else {
			if (!mazes.containsKey(mazeName)) {
				message = "> Information>> Maze3d name " + "'" + mazeName + "'" + " was not found.\n";
				setChanged();
				notifyObservers("display_message");
			} else {
				maze3dLastName = mazeName;
				maze3d = mazes.get(mazeName);
				setChanged();
				notifyObservers("display");
			}
		}
	}

	/*************************
	 * displayCroosSection (by x/y/z) method
	 ***********************/
	/**
	 * the function displayCroosSection() display the cross section (x/y/z) by
	 * index as a 2D maze X represents floors Y Represents cols Z Represents
	 * rows
	 * 
	 * @param userLineName
	 * @param userLineSection
	 *            - (X/Y/Z) - floors/cols or rows
	 * @param userLineIndex
	 *            - index
	 */
	@Override
	public void displayCrossSection() {
		if (mazes.isEmpty()) {
			message = "> Information>> Maze3d list is empty. No mazes to display!!!";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		Scanner input = new Scanner(System.in);
		Scanner inputSection = new Scanner(System.in);
		String userLineName = null;
		String userLineSection;
		int userLineIndex = 0;

		do {
			do {
				message = "> Please type maze3d name to display or type 'list': ";
				setChanged();
				notifyObservers("display_message");
				userLineName = input.nextLine();

			} while (userLineName.length() == 0);

			if (userLineName.equals("list")) {
				message = "> Please choose maze3d name according to the list below: ";
				setChanged();
				notifyObservers("display_message");
				Set<String> keys = mazes.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = i.next();
					message = "> " + key;
					setChanged();
					notifyObservers("display_message");
				}

				message = "";
				setChanged();
				notifyObservers("display_message");

			} else {
				if (!mazes.containsKey(userLineName)) {
					message = "> Information>> Maze3d name " + "'" + userLineName + "'" + " was not found.\n";
					setChanged();
					notifyObservers("display_message");
				}
			}

		} while (!mazes.containsKey(userLineName));

		do {
			this.message = "> Please type an index: ";
			setChanged();
			notifyObservers("display_message");
			while (!input.hasNextInt()) {
				input.next();
				this.message = "> Please enter a NUMBER of floors: ";
				setChanged();
				notifyObservers("display_message");
			}
			userLineIndex = input.nextInt();
			if (userLineIndex < 0) {
				this.message = "> Please enter positive number of floors";
				setChanged();
				notifyObservers("display_message");
			}
		} while (userLineIndex < 0);

		message = "> Please type which section to display: 'x' - is floor, 'y' - is rows and 'z' - is cols";
		setChanged();
		notifyObservers("display_message");
		userLineSection = inputSection.nextLine();

		if ((userLineSection.equals("X") || userLineSection.equals("x"))
				|| (userLineSection.equals("Y") || userLineSection.equals("y"))
				|| (userLineSection.equals("Z") || userLineSection.equals("z"))) {
			Maze3d maze = mazes.get(userLineName);
			switch (userLineSection) {
			case "x":
				maze.getCrossSectionByX(userLineIndex);
				break;
			case "y":
				maze.getCrossSectionByY(userLineIndex);
				break;
			case "z":
				maze.getCrossSectionByZ(userLineIndex);
				break;
			case "X":
				maze.getCrossSectionByX(userLineIndex);
				break;
			case "Y":
				maze.getCrossSectionByY(userLineIndex);
				break;
			case "Z":
				maze.getCrossSectionByZ(userLineIndex);
				break;
			default:
				break;
			}
		} else {
			message = "> Information>> Invalid section!";
			setChanged();
			notifyObservers("display_message");
			input.close();
			inputSection.close();
			return;
		}
		input.close();
		inputSection.close();
	}

	/************************* solveMaze method ***********************/
	/**
	 * the function solveMaze() Solving the Maze by the given Algorithm BFS,DFS
	 * or Best First Search
	 * 
	 * @param maze
	 *            - the maze
	 * @param name
	 *            - name of Maze
	 * @param search-
	 *            searching Algorithm
	 * @param sol
	 *            - solution
	 */
	// המטודה תפתור את המבוך בת'רד נפרד באמצעות האלגוריתמים (אחד מהם)
	// כשיהיה פתרון המודל יבקש מה controller
	// להציג
	// “solution for <name> is ready”
	/**
	 * This solveMaze() function solves the maze3d by the given Algorithm DFS or Best First Search.
	 * 
	 * 
	 * @param String name
	 *            - represents the name of the Maze.
	 * @param String algorithm-
	 *            represents the name of the searching Algorithm.
	 * @param Maze3dPosition pos
	 *            - represents the current position of the figure.
	 */
	@Override
	public void solveMaze(String name, String algorithm, Maze3dPosition pos) {

		String curName = name;
		
		
		
		if (curName.equals(""))
			curName = maze3dLastName;

		if (!mazes.containsKey(curName)) {

			this.message = "Maze '" + curName + "' does not exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		String callName = curName;

		Future<Solution> mysol = executor.submit(new Callable<Solution>() {

			@Override
			public Solution call() {
				ArrayList<MazeState> states = new ArrayList<MazeState>();

//				if (!(namesToSolution.containsKey(callName))) {
					Maze3d maze = mazes.get(callName);
					startTmpPos = maze.getStartPosition();

					maze.setStartPosition(pos);
					MazeAdapter adapter = new MazeAdapter(maze);
					Searcher search = null;
					Solution solCall;
					switch (algorithm) {
					case "DFS":
						search = new DFS();
						break;
					case "BestFirstSearch":
						search = new BestFirstSearch();
						break;
					default:
						break;
					}
					solCall = search.search(adapter);
					mazeToSol.put(mazes.get(callName), solCall);
					namesToSolution.put(callName, solCall);
					sol = solCall;
					maze.setStartPosition(startTmpPos);
//				}
				states = sol.getStates();
				return sol;

			}

		});

		this.message = "Solution for maze3d '" + curName + "' is ready \n";
		setChanged();
		notifyObservers("display_message");
		displaySolution(curName);

	}

	/**
	 * the function Exit() we closed all the threads are working and close the
	 * Program
	 */
	@Override
	public void Exit() {
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).stop();
		}
		message = "all Thread are stoped";
		setChanged();
		notifyObservers("display_message");

	}

	@Override
	public void SaveToZip(String filename) {

		try {
			GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(filename));
			zip.write(mazeToSol.size());
			for (Entry<Maze3d, Solution> entry : mazeToSol.entrySet()) {

				Maze3d key = entry.getKey();
				Solution value = entry.getValue();
				for (Entry<String, Maze3d> names : mazes.entrySet()) {
					String name = names.getKey();
					Maze3d mymaze = names.getValue();
					if (key.equals(mymaze)) {
						byte[] temp = name.getBytes();
						zip.write(temp.length);
						zip.write(temp);
					}
				}
				int temp = key.toByteArray().length;
				int count = 0;
				while (temp > 255) {
					temp = temp - 255;
					count++;
				}
				zip.write(temp);
				zip.write(count);
				byte[] mazetemp = key.toByteArray();
				zip.write(mazetemp);

				ArrayList<MazeState> mysolvedmaze = value.getStates();
				temp = 0;
				temp = mysolvedmaze.size();
				count = 0;
				while (temp > 255) {
					temp = temp - 255;
					count++;
				}
				zip.write(temp);
				;
				zip.write(count);
				State state = null;
				String pos = null;
				for (int i = 0; i < mysolvedmaze.size(); i++) {
					state = mysolvedmaze.get(i);
					zip.write(state.toString().getBytes());
					pos = mysolvedmaze.get(i).getDescription();
					byte[] mybyte = pos.getBytes();
					zip.write(mybyte.length);
					zip.write(mybyte);
				}

			}
			this.message = "Map Was Saved";
			setChanged();
			notifyObservers("display_message");
			zip.close();
		} catch (IOException e) {
			this.message = "IOEXception";
			setChanged();
			notifyObservers("display_message");
			return;
		}
	}

	@Override
	public void LoadFromZip(String filename) {
		Maze3d mymaze = null;
		Solution sol = null;
		MazeState st = null;
		MazeState camefrom = null;
		String mazename = null;
		byte[] name = null;
		try {
			GZIPInputStream zip = new GZIPInputStream(new FileInputStream(filename));
			int solvedsize = zip.read();
			for (int j = 0; j < solvedsize; j++) {
				name = new byte[zip.read()];
				zip.read(name);
				mazename = new String(name);
				int size = zip.read();
				int count = zip.read();
				count = count * 255;
				size = size + count;
				byte[] bytemaze = new byte[size];
				zip.read(bytemaze);
				mymaze = new Maze3d(bytemaze);
				sol = new Solution();

				ArrayList<MazeState> mystates = new ArrayList<MazeState>();
				size = 0;
				count = 0;
				size = zip.read();
				count = zip.read();
				count = count * 255;
				size = size + count;
				for (int i = 0; i < size; i++) {
					// int floor = zip.read();
					// int rows = zip.read();
					// int cols = zip.read();

					st = new MazeState((Maze3dPosition) camefrom.getCurrPlayerPosition());
					st.setCost(zip.read());
					st.setCameFrom(camefrom);
					int tempsize = zip.read();
					byte[] temp = new byte[tempsize];
					zip.read(temp);
					String dis = new String(temp);
					st.setDescription(dis);
					camefrom = st;

					mystates.add(st);

				}

				sol.setStates(mystates);
				mazes.put(mazename, mymaze);
				mazeToSol.put(mymaze, sol);

			}
			zip.close();
			this.message = "Map Was Loaded";
			setChanged();
			notifyObservers("display_message");
		} catch (IOException e) {
			this.message = "IOEXception";
			setChanged();
			notifyObservers("display_message");
			return;
		}

	}

	@Override
	public Maze3d getMaze3d() {
		return this.maze3d;
	}

	
	
	
	
	/**
	 * This displaySolution() function prints out a list of all maze3d positions 
	 * leading to the goal position.
	 */
	@Override
	public void displaySolution(String name) {

		if (!mazes.containsKey(name)) {
			this.message = "Maze '" + name + "' does not exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}

		maze3dLastName = name;
		setChanged();
		notifyObservers("display_maze_solution");

	}

	
	
	
	
	
	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	
	
	
	
	
	public void createAlgo() {
		BestFirstSearch myBestFirstSearch = new BestFirstSearch();
		DFS myDFS = new DFS();
		algorithms.put("BestFirstSearch", myBestFirstSearch);
		algorithms.put("DFS", myDFS);
	}

	
	
	
	
	
	@Override
	public void loadFromXml(String name) throws FileNotFoundException {
		XMLDecoder decoder = null;

		try {
			decoder = new XMLDecoder(new FileInputStream(name));
			Properties prop = (Properties) decoder.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
	
	
	@Override
	public Solution getSol() {
		return namesToSolution.get(maze3dLastName);
	}

}
