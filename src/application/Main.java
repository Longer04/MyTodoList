package application;



import java.io.IOException;

import datamodel.TodoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
		
		primaryStage.setTitle("Todo List");
		primaryStage.setScene(new Scene(root, 800, 480));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}

	//stop method runs when user is closing app on main window this method saves the items
	@Override
	public void stop() throws Exception {
		try {
			TodoData.getInstance().storeTodoItems();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	//Start method will load items from the file
	@Override
	public void init() throws Exception {
		try {
			TodoData.getInstance().loadTodoItems();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

	
	
	
}
