package application;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import datamodel.TodoData;
import datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class Controller 
{
	
	private List<TodoItem> todoItems;
	
	@FXML
	private ListView<TodoItem> todoListView;
	
	@FXML
	private TextArea itemDetailsTextArea;
	
	@FXML
	private Label deadlineLabel;
	
	@FXML
	private BorderPane mainBorderPane;
	
	public void initialize()
	{		
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
	
	@FXML
	public void showNewItemDialog()
	{
		Dialog<ButtonType> dialog = new Dialog<>();
		//Ensure that when dialog is pop up user can interact only with dialog window
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Todo Item");
		dialog.setHeaderText("Use this dialog to create new todo item.");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
		try
		{
			//Loading the FXML of todoItemDialog
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch(IOException ex)
		{
			System.out.println("Couldn't load the dialog");
			ex.printStackTrace();
			return;
		}
		//Added OK/Cancel buttons
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		Optional<ButtonType> result = dialog.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK)
		{
			//Adding controller and processing results
			DialogController controller = fxmlLoader.getController();
			TodoItem newItem = controller.processResults();
			//Updating the list view to show instantly the new item.
			todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
			//Selecting new Item when created
			todoListView.getSelectionModel().select(newItem);
			System.out.println("OK button pressed.");
		}
		else
		{
			System.out.println("Cancel button pressed.");
		}
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
