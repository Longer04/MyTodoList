package application;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import datamodel.TodoData;
import datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

public class Controller 
{
	
	private List<TodoItem> todoItems;
	
	@FXML
	private ListView<TodoItem> todoListView;
	
	@FXML
	private TextArea itemDetailsTextArea;
	
	@FXML
	private Label deadlineLabel;
	
	public void initialize()
	{
		/**
		 * This was initial hard coded information to todo list.
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
		
		TodoData.getInstance().setTodoItems(todoItems);
		
		*/
		
		//Listener of changing items in the list - works for start of program also (no item marked at beginning)
		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
			@Override
			public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
				// TODO Auto-generated method stub
				if(newValue != null)
				{
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getFullDescription());
					DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					deadlineLabel.setText(dtFormatter.format(item.getDeadline()));
				}
			}
		});
		
		//Changed method to get data from the file
		todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);	
		//Select first item when application starts.
		todoListView.getSelectionModel().selectFirst();
	}
	
	//Handler for the click list view
	@FXML
	public void handleClickListView()
	{
		//Selection of list item
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		//Display detailed description 
		itemDetailsTextArea.setText(item.getFullDescription());
		//Display Due date
		deadlineLabel.setText(item.getDeadline().toString());

	}

}
