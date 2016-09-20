package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Observable;


/**
 * <h1>CLI</h1>
 * @author Stas Fainberg
 * @version 1.0
 * The class defining the start of the project, implements Runnable.
 */
public class CLI extends Observable {
	private BufferedReader in;
	private Writer out;	
	
	/****************** Constructor of CLI *******************/
	public CLI(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;		
	}
	
	
	public void start() 
	{	
		System.out.println("================================================================================");
		System.out.println("=========================== Welcome to CLI Interface ===========================\n");
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {				 
				try {					
					String line = null;
					do {
						out.write("\n> Enter command or type 'help':"); 
						out.flush();
						line = in.readLine();
						setChanged();
						notifyObservers(line);						
					} while (!(line.equals("exit")));				
											
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}	
				System.out.println("================== CLI interface was terminated ==================");
				System.out.println("==================================================================");
			}
			
		});
		thread.start();
	}
	
	
}
