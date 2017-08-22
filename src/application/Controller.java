package application;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class Controller 
{
	
	private List<TodoItem> todoItems;
	
	@FXML
	private ListView<TodoItem> todoListView;
	
	public void initialize()
	{
		TodoItem item1 = new TodoItem("Medical Examination", "Go to doctor for medical tests", LocalDate.of(2017, Month.SEPTEMBER, 25));
		TodoItem item2 = new TodoItem("Dentist appointment", "Review of teeth", LocalDate.of(2017, Month.OCTOBER, 25));
		TodoItem item3 = new TodoItem("Exam for drivers licence", "theoretical exam for drivers licence", LocalDate.of(2017, Month.SEPTEMBER, 28));
		TodoItem item4 = new TodoItem("Birthday party", "Maria Party", LocalDate.of(2017, Month.JULY, 20));
		TodoItem item5 = new TodoItem("Watch movie", "Superman", LocalDate.of(2017, Month.SEPTEMBER, 1));
		
		
		todoItems = new ArrayList<>();
		todoItems.add(item1);
		todoItems.add(item2);
		todoItems.add(item3);
		todoItems.add(item4);
		todoItems.add(item5);
		
		todoListView.getItems().setAll(todoItems);
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

}
