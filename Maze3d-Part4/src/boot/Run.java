package boot;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.MyModel;
import presenter.GetProperties;
import presenter.Presenter;
import presenter.Properties;
import view.MazeWindow;
import view.MyView;

public class Run {


	public static void main(String[] args) {
		
		/** @author Stas Fainberg */
		
			
		HashMap<String, MyView> views = new HashMap<String, MyView>();

		GetProperties props = new GetProperties();
		props.run();
		XMLDecoder myprop;
		Properties prop = null;
		try {
			myprop = new XMLDecoder(new FileInputStream("files\\properties.xml"));
			prop = (Properties) myprop.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		views.put("CLI", new MyView(in,out));
		views.put("GUI", null);
		
		
		if(prop.getUitype().equals("CLI")){
			ExecutorService executor = Executors.newCachedThreadPool();
			MyModel model = new MyModel(executor);
			MyView view = views.get(prop.getUitype());
			Presenter presenter = new Presenter(model, view, prop);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		}else
		{
			ExecutorService executor = Executors.newCachedThreadPool();
			MyModel model = new MyModel(executor);
			MazeWindow view = new MazeWindow();
			Presenter presenter = new Presenter(model, view, prop);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		}
	}
}
